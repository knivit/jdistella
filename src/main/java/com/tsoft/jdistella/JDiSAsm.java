package com.tsoft.jdistella;

import java.nio.file.Paths;
import java.util.LinkedList;

import static com.tsoft.jdistella.JDiConst.*;
import static com.tsoft.jdistella.JDiConst.REFERENCED;
import static com.tsoft.jdistella.JDiMaria.*;
import static com.tsoft.jdistella.JDiOut.*;
import static com.tsoft.jdistella.JDiOut.stderr;
import static com.tsoft.jdistella.JDiTable.lookup;
import static com.tsoft.jdistella.JDiVcs.ioregs;
import static com.tsoft.jdistella.JDiVcs.stella;

public class JDiSAsm {

    public static final String APP_VERSION = "\"3.02-SNAPSHOT\"";
    public static final String APP_COMPILE = "\"March 22, 2020\"";

    private int start_adr;
    private int brk_adr;
    private int isr_adr;
    private int pc;
    private int pcend;

    private byte[] mem;
    private LinkedList<Integer> addressq = new LinkedList<>();

    private static final int[] clength = new int[] {1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 2, 2, 2, 0};

    public void start(JDiAppData app_data) {
        mem = app_data.mem;

        /*====================================*/
        /* Allocate memory for "labels" variable */
        app_data.labels = new byte[app_data.length + 1];
        /*====================================*/

        /*-----------------------------------------------------
         The last 3 words of a program are as follows:

        .word	INTERRUPT   (isr_adr)
        .word	START       (start_adr)
        .word	BRKroutine  (brk_adr)

           Since we always process START, move the Program
             Counter 3 bytes back from the final byte.
         -----------------------------------------------------*/
        pc = app_data.end - 3;

        start_adr = read_adr();

        /* 2K case */
        if (app_data.end == 0x7ff) {
        /*============================================
           What is the offset?  Well, it's an address
           where the code segment starts.  For a 2K game,
           it is usually 0xf800, which would then have the
           code data end at 0xffff, but that is not
           necessarily the case.  Because the Atari 2600
           only has 13 address lines, it's possible that
           the "code" can be considered to start in a lot
           of different places.  So, we use the start
           address as a reference to determine where the
           offset is, logically-anded to produce an offset
           that is a multiple of 2K.

           Example:
             Start address = $D973, so therefore
             Offset to code = $D800
             Code range = $D800-$DFFF
         =============================================*/
            app_data.offset = (start_adr & 0xf800);
        }

        /* 4K case */
        else if (app_data.end == 0xfff) {
        /*============================================
           The offset is the address where the code segment
           starts.  For a 4K game, it is usually 0xf000,
           which would then have the code data end at 0xffff,
           but that is not necessarily the case.  Because the
           Atari 2600 only has 13 address lines, it's possible
           that the "code" can be considered to start in a lot
           of different places.  So, we use the start
           address as a reference to determine where the
           offset is, logically-anded to produce an offset
           that is a multiple of 4K.

           Example:
             Start address = $D973, so therefore
             Offset to code = $D000
             Code range = $D000-$DFFF
         =============================================*/
            app_data.offset = (start_adr - (start_adr % 0x1000));
        }

        /* 8K case (7800 mode only-- file size is not supported by file_load function) */
        else if (app_data.end == 0x1fff) {
            app_data.offset = (start_adr & 0xe000);
        }

        /* 16K case (7800 mode only) */
        else if (app_data.end == 0x3fff) {
           /* ============================================
               The offset is the address where the code segment starts.
               For a 16K game (it must be Atari 7800 then), it should
               always be at $C000, creating a code range from $C000-
               $CFFF.

               Data outside of this 16K range (i.e. $8000-$BFFF) would
               probably act as a mirror of $C000-$FFFF, therefore acting
               as if it referenced data within the $C000-$FFFF range.
               It is unknown if any 16K 7800 games access
               this mirror, but if so, the mark() function will
               note that the correct address ($C000-$FFFF) is marked
               accordingly.
               For the purposes of this disassembler, references
               to data from $4000-$7FFF for 16K games are ignored.
            ============================================= */
            app_data.offset = (start_adr & 0xc000);
        }

        /* 32K case (7800 mode only) */
        else if (app_data.end == 0x7fff) {
        /*============================================
           The offset is the address where the code segment starts.
           For a 32K game (it must be Atari 7800 then), it should
           always be at $C000.

           Example:
             Offset to code = $8000
             Code range = $8000-$FFFF

           Data outside of this 32K range (i.e. $4000-$7FFF) for 32K
           games would either be interpreted as $$8000-$CFFF's data,
           or may even be undefined.
           It is unknown if any 32K 7800 games access
           this mirror, but if so, the mark() function will
           note that the correct address ($8000-$CFFF) is marked
           accordingly.
         =============================================*/
            app_data.offset = 0x8000;
        }

        /* 48K case (7800 mode only) */
        else if (app_data.end == 0xbfff) {
        /*=====================================================
          if 48K, the CODE data must ALWAYS start at $4000.
          The CODE range will be $4000-$FFFF, and $0000-$3FFF
          are reserved internal to the 7800 system.
        =====================================================*/
            app_data.offset = 0x4000;

        /*-----------------------------------------------------
           if the r flag is on, we don't need it to be,
           because there is nothing to relocate for the 48K
           case-- all addresses are fixed and known.  The
           lower 16K is system, and the upper 48K are code.
        -----------------------------------------------------*/
            app_data.rflag = 0;

        /*-----------------------------------------------------
           Likewise, the k flag must be off, since 48K games
           cannot support POKEY hardware.  The POKEY hardware
           would be in 16K segment 2, but that's where some code
           is for a 48K situation.  Since they're mutually
           exclusive, POKEY capability with a 48K game is
           not practical.
        -----------------------------------------------------*/
            app_data.kflag = 0;
        }

        if (app_data.cflag != 0) {
            JDiConfig.load_config(Paths.get(app_data.configFile), app_data);
        }

        fprintf(stderr,"PASS 1\n");

        addq(start_adr);

        brk_adr = read_adr();
        if (app_data.intflag == 1 && app_data.a78flag == 0) {
            app_data.bflag = 1;
        }

    /*--------------------------------------------------------
       If Atari 2600 OR Atari 7800 mode,
         if the "-b" option is on, process BRKroutine
         if the "-b" option is off, don't process BRKroutine
    --------------------------------------------------------*/
        if (app_data.bflag != 0) {
            addq(brk_adr);
            mark(app_data, brk_adr, REFERENCED);
        }

    /*--------------------------------------------------------
       If Atari 7800 mode,
         if the "-i" option is on, process ISR routine
         if the "-i" option is off, don't process ISR routine

       To do this, we need to move the Program counter appropriately.
    --------------------------------------------------------*/
        if (app_data.intflag == 1 && app_data.a78flag == 1) {
            pc = app_data.end - 5;
            isr_adr = read_adr();
            addq(isr_adr);
            mark(app_data, isr_adr, REFERENCED);
        }

        if (app_data.dflag != 0) {
            for (int idx = 0; idx < addressq.size(); idx ++) {
                pc = addressq.get(idx);
                int pcbeg = pc;
                //addressq = delq(addressq);
                disasm(app_data, pc, 1);
                for (int k = pcbeg; k <= pcend; k ++) {
                    mark(app_data, k, REACHABLE);
                }
            }

            for (int k = 0; k <= app_data.end; k = k + 1) {
                if (!check_bit(app_data.labels[k], REACHABLE)) {
                    mark(app_data, k + app_data.offset, DATA);
                }
            }
        }

        fprintf(stderr,"PASS 2\n");
        disasm(app_data, app_data.offset,2);

        printf("; Disassembly of %s\n", app_data.file);
        printf("; Using DiStella v%s\n;\n", APP_VERSION);
        /*printf("; Command Line: %s\n;\n", parms);
        if (app_data.cflag != 0) {
            printf("; %s contents:\n;\n", config);
            while (fgets(parms, 79, cfg) != NULL)
                printf(";      %s", parms);
        }
        printf("\n");*/

        if (app_data.pflag != 0) {
            printf("      processor 6502\n");
        }

        /* Print list of used equates onto the screen (TIA) if 2600 mode */
        if (app_data.a78flag == 0) {
            for (int i = 0; i <= 0x3d; i ++)
                if (app_data.reserved[i] == 1) {
                    printf("%-6s", stella[i]);
                    //for(int j = strlen(stella[i]); j<7; j++) {
                    //    printf(" ");
                    //}
                    printf(" =  $%02X\n", i);
                }

            for (int i = 0x280; i <= 0x297; i ++)
                if (app_data.ioresrvd[i - 0x280] == 1) {
                    printf("%-6s", ioregs[i - 0x280]);
                    //for(int j = strlen(ioregs[i-0x280]);j<7;j++) {
                    //    printf(" ");
                    //}
                    printf(" =  $%04X\n", i);
                }
        }
        else {
            /* Print list of used equates onto the screen (MARIA) if 7800 mode */
            for (int i = 0; i <= 0x3f; i ++)
                if (app_data.reserved[i] == 1) {
                    printf("%-6s", maria[i]);
                    //for (int j = strlen(maria[i]); j < 7; j ++) {
                    //    printf(" ");
                    //}
                    printf(" =  $%02X\n", i);
                }

            for (int i = 0x280; i <= 0x283; i ++) {
                if (app_data.ioresrvd[i - 0x280] == 1) {
                    printf("%-6s", mariaio[i - 0x280]);
                    //for (int j = strlen(mariaio[i - 0x280]); j < 7; j++) {
                    //    printf(" ");
                    //}
                    printf(" =  $%04X\n", i);
                }
            }

            if (app_data.kflag == 1) {
                for (int i = 0x4000; i <= 0x400f; i++)
                    if (app_data.pokresvd[i-0x4000] == 1) {
                        printf("%-6s", pokey[i-0x4000]);
                        //for (int j = strlen(pokey[i-0x4000]);j<7;j++) {
                        //    printf(" ");
                        //}
                        printf(" =  $%04X\n",i);
                    }
            }
        }

        /* Print Line equates on screen */
        for (int i = 0; i <= app_data.end; i ++) {
            if ((app_data.labels[i] & (REFERENCED | VALID_ENTRY)) == REFERENCED) {
            /* so, if we have a piece of code referenced somewhere else, but cannot locate the label
               in code (i.e because the address is inside of a multi-byte instruction, then we
               print that address on screen for reference */
                printf("L%04X   =   ", i + app_data.offset);
                printf("$%04X\n", i + app_data.offset);
            }
        }

        printf("\n");
        printf("    %s", app_data.orgmnc);
        printf("$%04X\n", app_data.offset);

        fprintf(stderr,"PASS 3\n");
        disasm(app_data, app_data.offset, 3);
    }

    int read_adr() {
        int d1 = ubyte(mem[pc ++]);
        int d2 = ubyte(mem[pc ++]);
        return ((d2 << 8) + d1);
    }

    public static void check_range(JDiAppData app_data, int beg, int end) {
        app_data.lineno ++;
        if (beg > end) {
            fprintf(stderr, "Beginning of range greater than End in config file in line %d\n", app_data.lineno);
            exit(1);
        }

        if (beg > app_data.end + app_data.offset) {
            fprintf(stderr,"Beginning of range out of range in line %d\n", app_data.lineno);
            exit(1);
        }

        if (beg < app_data.offset) {
            fprintf(stderr,"Beginning of range out of range in line %d\n", app_data.lineno);
            exit(1);
        }
    }

    void disasm(JDiAppData app_data, int distart, int pass) {
        int op;
        int d1, opsrc;
        int ad;
        int amode;
        int i,labfound,addbranch = -1;
        int arg1, arg2;
        long argaddr;

        String linebuff = "";
        String nextline = "";

        /*    pc=app_data.start; */
        pc = distart - app_data.offset;
        while (pc <= app_data.end) {
            argaddr = pc + app_data.offset;
            arg1 = -1;
            arg2 = -1;
            if (pass == 3) {
                if (pc + app_data.offset == start_adr) {
                    print("\nSTART:\n");
                }
                if ((pc + app_data.offset == brk_adr) && (app_data.bflag != 0)) {
                    print("\nBRK_ROUTINE:\n");
                }
                if ((pc + app_data.offset == isr_adr) && ((app_data.a78flag == 1) && (app_data.intflag == 1))) {
                    print("\nINTERRUPT_ROUTINE:\n");
                }
            }

            if (check_bit(app_data.labels[pc], GFX)) {
                /*         && !check_bit(labels[pc],REACHABLE)) { */
                if (pass == 2) {
                    mark(app_data, pc + app_data.offset, VALID_ENTRY);
                }

                if (pass == 3) {
                    if (check_bit(app_data.labels[pc], REFERENCED)) {
                        print("L%04X: ", pc + app_data.offset);
                    } else {
                        print("       ", pc + app_data.offset);
                    }
                    print(".byte $%02X ; ", ubyte(mem[pc]));
                    showgfx(ubyte(mem[pc]));
                    print(" $%04X\n",pc + app_data.offset);
                }

                pc++;
            } else
            if (check_bit(app_data.labels[pc], DATA) && !check_bit(app_data.labels[pc], GFX)) {
                /*            && !check_bit(labels[pc],REACHABLE)) {  */
                mark(app_data, pc + app_data.offset, VALID_ENTRY);

                int bytes = 0;
                if (pass == 3) {
                    bytes = 1;
                    print("L%04X: .byte ", pc + app_data.offset);
                    print("$%02X", ubyte(mem[pc]));
                }
                pc ++;

                while (check_bit(app_data.labels[pc], DATA) && !check_bit(app_data.labels[pc], REFERENCED)
                    && !check_bit(app_data.labels[pc], GFX) && pass == 3 && pc <= app_data.end) {
                    if (pass == 3) {
                        bytes ++;
                        if (bytes == 17) {
                            print("\n       .byte $%02X", ubyte(mem[pc]));
                            bytes = 1;
                        } else {
                            print(",$%02X", ubyte(mem[pc]));
                        }
                    }
                    pc ++;
                }

                if (pass == 3) {
                    print("\n");
                }
            } else {
                op = ubyte(mem[pc]);

                /* version 2.1 bug fix */
                if (pass == 2) {
                    mark(app_data, pc + app_data.offset, VALID_ENTRY);
                }

                if (pass == 3) {
                    if (check_bit(app_data.labels[pc], REFERENCED)) {
                        print("L%04X: ", pc + app_data.offset);
                    } else {
                        print("       ");
                    }
                }

                amode = lookup[op].addr_mode;
                if (app_data.disp_data != 0) {
                    for (i=0; i < clength[amode]; i++) {
                        if (pass == 3) {
                            print("%02X ", ubyte(mem[pc + i]));
                        }
                    }
                    if (pass == 3) {
                        print("  ");
                    }
                }

                pc ++;

                if (lookup[op].mnemonic.charAt(0) == '.') {
                    amode = IMPLIED;
                    if (pass == 3) {
                        linebuff = sprintf(".byte $%02X ;", op);
                        nextline += linebuff;
                    }
                }

                if (pass == 1) {
                    opsrc = lookup[op].source;
                /* M_REL covers BPL, BMI, BVC, BVS, BCC, BCS, BNE, BEQ
                   M_ADDR = JMP $NNNN, JSR $NNNN
                   M_AIND = JMP Abs, Indirect */
                    if ((opsrc == M_REL) || (opsrc == M_ADDR) || (opsrc == M_AIND)) {
                        addbranch = 1;
                    } else {
                        addbranch = 0;
                    }
                } else if (pass == 3) {
                    linebuff = sprintf("%s", lookup[op].mnemonic);
                    nextline += linebuff;
                }

                if (pc >= app_data.end) {
                    if (amode == ABSOLUTE || amode == ABSOLUTE_X || amode == ABSOLUTE_Y || amode == INDIRECT_X || amode == INDIRECT_Y || amode == ABS_INDIRECT) {
                        if (pass == 3) {
                            /* Line information is already printed; append .byte since last instruction will
                               put recompilable object larger that original binary file */
                            print(".byte $%02X\n", op);

                            if (pc == app_data.end) {
                                if (check_bit(app_data.labels[pc], REFERENCED)) {
                                    print("L%0.4X: ", pc + app_data.offset);
                                } else {
                                    print("       ");
                                }
                                op = ubyte(mem[pc ++]);
                                print(".byte $%02X\n", op);
                            }
                        }
                        pcend = app_data.end + app_data.offset;
                        return;
                    } else if (amode == ZERO_PAGE || amode == IMMEDIATE || amode == ZERO_PAGE_X || amode == ZERO_PAGE_Y || amode == RELATIVE) {
                        if (pc > app_data.end) {
                            if (pass == 3) {
                                /* Line information is already printed, but we can remove the
                                   Instruction (i.e. BMI) by simply clearing the buffer to print */
                                nextline = "";
                                linebuff = sprintf(".byte $%02X", op);
                                nextline += linebuff;

                                print("%s", nextline);
                                print("\n");
                                nextline = "";
                            }

                            pc++;
                            pcend = app_data.end + app_data.offset;
                            return;
                        }
                    }
                }

                /* Version 2.1 added the extensions to mnemonics */
                /*              case IMPLIED: {
                    if (op == 0x40 || op == 0x60)
                            if (pass == 3) {
                                sprintf(linebuff,"\n");
                                strcat(nextline,linebuff);
                            }
                            break;
                }
*/
                if (amode == ACCUMULATOR) {
                    if (pass == 3) {
                        if (app_data.aflag != 0) {
                            linebuff = sprintf("    A");
                            nextline += linebuff;
                        }
                    }
                } else if (amode == ABSOLUTE) {
                    ad = read_adr();
                    arg1 = ad & 255;
                    arg2 = ad / 256;
                    labfound = mark(app_data, ad, REFERENCED);
                    if (pass == 1) {
                        if ((addbranch != 0) && !check_bit(app_data.labels[ad & app_data.end], REACHABLE)) {
                            if (ad > 0xfff) {
                                addq((ad & app_data.end) + app_data.offset);
                            }
                            mark(app_data, ad, REACHABLE);
                        }
                    } else if (pass == 3) {
                        if (ad < 0x100 && app_data.fflag != 0) {
                            linebuff = sprintf(".w  ");
                            nextline += linebuff;
                        } else {
                            linebuff = sprintf("    ");
                            nextline += linebuff;
                        }

                        if (labfound == 1) {
                            linebuff = sprintf("L%04X", ad);
                            nextline += linebuff;
                        } else if (labfound == 3) {
                            if (app_data.a78flag == 0) {
                                linebuff = sprintf("%s", ioregs[ad - 0x280]);
                            } else {
                                linebuff = sprintf("%s", mariaio[ad - 0x280]);
                            }
                            nextline += linebuff;
                        } else if (labfound == 5) {
                            linebuff = sprintf("%s", pokey[ad - 0x4000]);
                            nextline += linebuff;
                        } else if ((labfound == 4) && app_data.rflag != 0) {
                            linebuff = sprintf("L%04X", (ad & app_data.end) + app_data.offset);
                            nextline += linebuff;
                        } else {
                            linebuff = sprintf("$%04X", ad);
                            nextline += linebuff;
                        }
                    }
                } else if (amode == ZERO_PAGE) {
                    d1 = ubyte(mem[pc ++]);
                    arg1 = d1;
                    labfound = mark(app_data, d1, REFERENCED);
                    if (pass == 3) {
                        if (labfound == 2) {
                            if (app_data.a78flag == 0) {
                                linebuff = sprintf("    %s", stella[d1]);
                            } else {
                                linebuff = sprintf("    %s", maria[d1]);
                            }
                            nextline += linebuff;
                        } else {
                            linebuff = sprintf("    $%02X ", d1);
                            nextline += linebuff;
                        }
                    }
                } else if (amode == IMMEDIATE) {
                    d1 = ubyte(mem[pc ++]);
                    arg1 = d1;
                    if (pass == 3) {
                        linebuff = sprintf("    #$%02X ", d1);
                        nextline += linebuff;
                    }
                } else if (amode == ABSOLUTE_X) {
                    ad = read_adr();
                    arg1 = ad & 255;
                    arg2 = ad / 256;
                    labfound = mark(app_data, ad, REFERENCED);
                    if (pass == 3) {
                        if (ad < 0x100 && app_data.fflag != 0) {
                            linebuff = sprintf(".wx ");
                            nextline += linebuff;
                        } else {
                            linebuff = sprintf("    ");
                            nextline += linebuff;
                        }

                        if (labfound == 1) {
                            linebuff = sprintf("L%04X,X", ad);
                            nextline += linebuff;
                        } else if (labfound == 3) {
                            if (app_data.a78flag == 0) {
                                linebuff = sprintf("%s,X", ioregs[ad - 0x280]);
                            } else {
                                linebuff = sprintf("%s,X", mariaio[ad - 0x280]);
                            }
                            nextline += linebuff;
                        } else if (labfound == 5) {
                            linebuff = sprintf("%s,X", pokey[ad - 0x4000]);
                            nextline += linebuff;
                        } else if ((labfound == 4) && app_data.rflag != 0) {
                            linebuff = sprintf("L%04X,X", (ad & app_data.end) + app_data.offset);
                            nextline += linebuff;
                        } else {
                            linebuff = sprintf("$%04X,X", ad);
                            nextline += linebuff;
                        }
                    }
                } else if (amode == ABSOLUTE_Y) {
                    ad = read_adr();
                    arg1 = ad & 255;
                    arg2 = ad / 256;
                    labfound = mark(app_data, ad, REFERENCED);
                    if (pass == 3) {
                        if (ad < 0x100 && app_data.fflag != 0) {
                            linebuff = sprintf(".wy ");
                            nextline += linebuff;
                        } else {
                            linebuff = sprintf("    ");
                            nextline += linebuff;
                        }
                        if (labfound == 1) {
                            linebuff = sprintf("L%04X,Y", ad);
                            nextline += linebuff;
                        } else if (labfound == 3) {
                            if (app_data.a78flag == 0) {
                                linebuff = sprintf("%s,Y", ioregs[ad - 0x280]);
                            } else {
                                linebuff = sprintf("%s,Y", mariaio[ad - 0x280]);
                            }
                            nextline += linebuff;
                        } else if (labfound == 5) {
                            linebuff = sprintf("%s,Y", pokey[ad - 0x4000]);
                            nextline += linebuff;
                        } else if ((labfound == 4) && app_data.rflag != 0) {
                            linebuff = sprintf("L%04X,Y", (ad & app_data.end) + app_data.offset);
                            nextline += linebuff;
                        } else {
                            linebuff = sprintf("$%04X,Y", ad);
                            nextline += linebuff;
                        }
                    }
                } else if (amode == INDIRECT_X) {
                    d1 = ubyte(mem[pc ++]);
                    arg1 = d1;
                    if (pass == 3) {
                        linebuff = sprintf("    ($%02X,X)", d1);
                        nextline += linebuff;
                    }
                } else if (amode == INDIRECT_Y) {
                    d1 = ubyte(mem[pc ++]);
                    arg1 = d1;
                    if (pass == 3) {
                        linebuff = sprintf("    ($%02X),Y", d1);
                        nextline += linebuff;
                    }
                } else if (amode == ZERO_PAGE_X) {
                    d1 = ubyte(mem[pc ++]);
                    arg1 = d1;
                    labfound = mark(app_data, d1, REFERENCED);
                    if (pass == 3)
                        if (labfound == 2) {
                            if (app_data.a78flag == 0) {
                                linebuff = sprintf("    %s,X", stella[d1]);
                            } else {
                                linebuff = sprintf("    %s,X", maria[d1]);
                            }
                            nextline += linebuff;
                        } else {
                            linebuff = sprintf("    $%02X,X", d1);
                            nextline += linebuff;
                        }
                } else if (amode == ZERO_PAGE_Y) {
                    d1 = ubyte(mem[pc ++]);
                    arg1 = d1;
                    labfound = mark(app_data, d1, REFERENCED);
                    if (pass == 3) {
                        if (labfound == 2) {
                            if (app_data.a78flag == 0) {
                                linebuff = sprintf("    %s,Y", stella[d1]);
                            } else {
                                linebuff = sprintf("    %s,Y", maria[d1]);
                            }
                            nextline += linebuff;
                        } else {
                            linebuff = sprintf("    $%02X,Y", d1);
                            nextline += linebuff;
                        }
                    }
                } else if (amode == RELATIVE) {/* SA - 04-06-2010: there seemed to be a bug here, where
                       wraparound occurred on a 32-bit int, and subsequent
                       indexing into the labels array caused a crash
                     */
                    d1 = ubyte(mem[pc ++]);
                    arg1 = d1;
                    if (app_data.a78flag == 0) {
                        ad = ((pc + signed_char(d1)) & 0xfff) + app_data.offset;
                    } else {
                        ad = ((pc + signed_char(d1)) & 0xffff) + app_data.offset;
                    }

                    labfound = mark(app_data, ad, REFERENCED);
                    if (pass == 1) {
                        if ((addbranch != -1) && !check_bit(app_data.labels[ad - app_data.offset], REACHABLE)) {
                            addq(ad);
                            mark(app_data, ad, REACHABLE);
                            /*       addressq=addq(addressq,pc+offset); */
                        }
                    } else if (pass == 3)
                        if (labfound == 1) {
                            linebuff = sprintf("    L%04X", ad);
                            nextline += linebuff;
                        } else {
                            linebuff = sprintf("    $%04X", ad);
                            nextline += linebuff;
                        }
                } else if (amode == ABS_INDIRECT) {
                    ad = read_adr();
                    arg1 = ad & 255;
                    arg2 = ad / 256;
                    labfound = mark(app_data, ad, REFERENCED);
                    if (pass == 3) {
                        if (ad < 0x100 && app_data.fflag != 0) {
                            linebuff = sprintf(".ind ");
                            nextline += linebuff;
                        } else {
                            linebuff = sprintf("    ");
                            nextline += linebuff;
                        }
                        if (labfound == 1) {
                            linebuff = sprintf("(L%04X)", ad);
                            nextline += linebuff; // REVENG: was overflowing "nextline" on PASS 1, with at least one rom. Added parenthesis to limit this block to PASS 3, which seemed to be the intent if the indentation is any indication.
                        } else if (labfound == 3) {
                            if (app_data.a78flag == 0) {
                                linebuff = sprintf("(%s)", ioregs[ad - 0x280]);
                            } else {
                                linebuff = sprintf("(%s)", mariaio[ad - 0x280]);
                            }
                            nextline += linebuff;
                        } else if (labfound == 5) {
                            linebuff = sprintf("(%s)", pokey[ad - 0x4000]);
                            nextline += linebuff;
                        } else {
                            linebuff = sprintf("($%04X)", ad);
                            nextline += linebuff;
                        }
                    }
                }

                if (pass == 1) {
                    if (lookup[op].mnemonic.equals("RTS") ||
                        lookup[op].mnemonic.equals("JMP") ||
                        /*                    !strcmp(lookup[op].mnemonic,"BRK") || */
                        lookup[op].mnemonic.equals("RTI")) {
                        pcend = (pc-1) + app_data.offset;
                        return;
                    }
                } else if (pass == 3) {
                    print("%-15s", nextline);
                    //if (strlen(nextline) <= 15) {
                    //    /* Print spaces to align cycle count data */
                    //    for (int charcnt = 0; charcnt < 15 - strlen(nextline); charcnt++) {
                    //        print(" ");
                    //    }
                    //}

                    if (app_data.sflag != 0) {
                        print(";%d", lookup[op].cycles);
                    }

                    if (app_data.hflag != 0) {
                        print("; %0.4X ", argaddr);
                        print("%0.2X ", op);
                        if (arg1 >= 0) {
                            print("%0.2X ", arg1);
                        }
                        if (arg2 >= 0) {
                            print("%0.2X ", arg2);
                        }
                    }
                    print("\n");

                    if (op == 0x40 || op == 0x60) {
                        print("\n");
                    }

                    nextline = "";
                }
            }
        }    /* while loop */

        /* Just in case we are disassembling outside of the address range, force the pcend to EOF */
        pcend = app_data.end + app_data.offset;
    }

    public int signed_char(int v) {
        return (v < 128) ? v : v - 256;
    }

    public int ubyte(byte b) {
        return (b >= 0) ? b : 256 + b;
    }

    public static int mark(JDiAppData app_data, int address, int bit) {

    /*-----------------------------------------------------------------------
        For any given offset and code range...

	If we're between the offset and the end of the code range, we mark
        the bit in the labels array for that data.  The labels array is an
        array of label info for each code address.  If this is the case,
        return "1", else...

	We sweep for hardware/system equates, which are valid addresses,
        outside the scope of the code/data range.  For these, we mark its
        corresponding hardware/system array element, and return "2", "3", or
        "5" (depending on which system/hardware element was accessed).  If this
        was not the case...

        Next we check if it is a code "mirror".  For the 2600, address ranges
        are limited with 13 bits, so other addresses can exist outside of the
        standard code/data range.  For these, we mark the element in the "labels"
        array that corresponds to the mirrored address, and return "4"

        If all else fails, it's not a valid address, so return 0.

        A quick example breakdown for a 2600 4K cart:
        ===========================================================
        $00-$3d = system equates (WSYNC, etc...); mark the array's element
                      with the appropriate bit; return 2.
        $0280-$0297 = system equates (INPT0, etc...); mark the array's element
                      with the appropriate bit; return 3.
        $1000-$1FFF = CODE/DATA, mark the code/data array for the mirrored address
                      with the appropriate bit; return 4.
        $3000-$3FFF = CODE/DATA, mark the code/data array for the mirrored address
                      with the appropriate bit; return 4.
        $5000-$5FFF = CODE/DATA, mark the code/data array for the mirrored address
                      with the appropriate bit; return 4.
        $7000-$7FFF = CODE/DATA, mark the code/data array for the mirrored address
                      with the appropriate bit; return 4.
        $9000-$9FFF = CODE/DATA, mark the code/data array for the mirrored address
                      with the appropriate bit; return 4.
        $B000-$BFFF = CODE/DATA, mark the code/data array for the mirrored address
                      with the appropriate bit; return 4.
        $D000-$DFFF = CODE/DATA, mark the code/data array for the mirrored address
                      with the appropriate bit; return 4.
        $F000-$FFFF = CODE/DATA, mark the code/data array for the address
                      with the appropriate bit; return 1.
        Anything else = invalid, return 0.
        ===========================================================
    -----------------------------------------------------------------------*/

        if (address >= app_data.offset && address <= app_data.end + app_data.offset) {
            app_data.labels[address - app_data.offset] = or(app_data.labels[address - app_data.offset], bit);
            return 1;
        } else if (address >= 0 && address <= 0x3d && app_data.a78flag == 0) {
            app_data.reserved[address] = 1;
            return 2;
        } else if (address >= 0x280 && address <= 0x297 && app_data.a78flag == 0) {
            app_data.ioresrvd[address-0x280] = 1;
            return 3;
        } else if (address >= 0 && address <=0x3f && app_data.a78flag == 1) {
            app_data.reserved[address] = 1;
            return 2;
        } else if (address >= 0x280 && address <=0x283 && app_data.a78flag == 1) {
            app_data.ioresrvd[address-0x280] = 1;
            return 3;
        } else if (address >= 0x4000 && address <= 0x400f && app_data.a78flag == 1 && app_data.kflag == 1) {
            app_data.pokresvd[address-0x4000] = 1;
            return 5;
        } else if (address > 0x8000 && app_data.a78flag == 1 && app_data.end == 0x3FFF) {
            /* 16K case */
            app_data.labels[address & app_data.end] = or(app_data.labels[address & app_data.end], bit);
            return 4;
        } else if (address > 0x4000 && address <= 0x7fff && app_data.a78flag == 1 && app_data.end == 0x7fff) {
            /* 32K case */
            app_data.labels[address - 0x4000] = or(app_data.labels[address - 0x4000], bit);
            return 4;
        } else if (address > 0x1000 && app_data.a78flag == 0) {
            /* 2K & 4K case */
            app_data.labels[address & app_data.end] = or(app_data.labels[address & app_data.end], bit);
            return 4;
        } else {
            return 0;
        }
    }

    private static byte or(int a, int bit) {
        a = a | bit;
        return (byte)a;
    }

    public static boolean check_bit(int bitflags, int i) {
        return (bitflags & i) != 0;
    }

    private void showgfx(int c) {
        print("|");
        for (int i = 0; i < 8; i ++) {
            if (c > 127) {
                print("X");
            } else {
                print(" ");
            }
            c = (c << 1) & 0xff;
        }
        print("|");
    }


    /* adds q node to tail of p - use form 'p = addq(p,w,t)' */
    private void addq(int address) {
        addressq.add(address);
    }
}
