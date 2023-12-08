package com.tsoft.jdistella;

import static com.tsoft.jdistella.JDiConst.*;
import static com.tsoft.jdistella.JDiOut.*;
import static com.tsoft.jdistella.JDiSAsm.APP_COMPILE;
import static com.tsoft.jdistella.JDiSAsm.APP_VERSION;

public class JDiCmdLine {

    public JDiAppData parse(String[] argv) {
        JDiAppData app_data = new JDiAppData();

        app_data.start = 0x0;
        app_data.load = 0x0000;
        app_data.length = 0;
        app_data.end = 0x0FFF;
        app_data.disp_data = 0;
        app_data.intflag = 0;

        app_data.file = "";

        /* Flag defaults */
        app_data.aflag = 1;
        app_data.bflag = 0;
        app_data.cflag = 0;
        app_data.fflag = 0;
        app_data.kflag = 0;
        app_data.pflag = 0;
        app_data.sflag = 0;
        app_data.hflag = 0;
        app_data.rflag = 0;
        app_data.dflag = 1;
        app_data.a78flag = 0;

        app_data.hdr_exists = NO_HEADER;    /* until we open the file, we don't know if it has an a78 header */

        app_data.orgmnc = "   ORG ";

        int argc = 0;
        while (argc < argv.length && (argv[argc].charAt(0) =='-')) {
            int n = 1;
            while (argv[argc].length() > n) {
                char c = argv[argc].charAt(n ++);
                switch (c) {
                    case 'a':
                        app_data.aflag = 0;
                        break;
                    case 'c':
                        app_data.cflag = 1;
                        break;
                    case 'd':
                        app_data.dflag = 0;
                        break;
                    case 'o':
                        char oflag = argv[argc].charAt(n ++);
                        switch (oflag) {
                            case '1':
                                app_data.orgmnc = "   ORG ";
                                break;
                            case '2':
                                app_data.orgmnc = "   *=";
                                break;
                            case '3':
                                app_data.orgmnc = "   .OR ";
                                break;
                            default:
                                fprintf(stderr, "Illegal org type %c\n", oflag);
                                break;
                        }
                        break;
                    case 'p':
                        app_data.pflag = 1;
                        break;
                    case 'h':
                        app_data.hflag = 1;
                        break;
                    case 's':
                        app_data.sflag = 1;
                        break;
                    case 'i':
                        app_data.intflag = 1;
                        break;
                    case 'r':
                        app_data.rflag = 1;
                        break;
                    case 'f':
                        app_data.fflag = 1;
                        break;
                    case '7':
                        app_data.a78flag = 1;
                        break;
                    case 'b':
                        app_data.bflag = 1;
                        break;
                    case 'k':
                        app_data.kflag = 1;
                        break;
                    default:
                        fprintf(stderr, "DiStella: illegal option %c\n", c);
                        exit(1);
                }
            }

            argc ++;
        }

        if (argc != argv.length - 1) {
            fprintf(stderr, "JDiStella v%s - %s\n", APP_VERSION, APP_COMPILE);
            fprintf(stderr, "\nUse: JDiStella [options] file\n");
            fprintf(stderr, " options:\n");
            fprintf(stderr, "   -7  Use Atari 7800 MARIA equates and file sizes\n");
            fprintf(stderr, "   -a  Turns 'A' off in accumulator instructions\n");
            fprintf(stderr, "   -c  Defines optional config file to use (file.cfg will be used)\n");
            fprintf(stderr, "   -d  Disables automatic code determination\n");
            fprintf(stderr, "   -f  Forces correct address length\n");
            fprintf(stderr, "   -i  Process DMA interrupt Vector (7800 mode)\n");
            fprintf(stderr, "       If 2600 mode, enables -b option\n");
            fprintf(stderr, "   -b  Process BRK interrupt Vector (2600 and 7800 mode)\n");
            fprintf(stderr, "   -k  Enable POKEY equates (7800 mode only, auto-detected if a78 file)\n");
            fprintf(stderr, "   -o# ORG variation: # = 1- ORG $XXXX  2- *=$XXXX  3- .OR $XXXX\n");
            fprintf(stderr, "   -p  Insert pseudo-mnemonic 'processor 6502'\n");
            fprintf(stderr, "   -r  Relocate calls out of address range\n");
            fprintf(stderr, "   -s  Cycle count\n");
            fprintf(stderr, "\n Example: JDiStella -pafs pacman.bin > pacman.s\n");
            fprintf(stderr, " Example: JDiStella -paf7ikscball.cfg ballblaz.bin > ballblaz.asm\n");
            fprintf(stderr, "\n Bugs: Raise issues at https://github.com/johnkharvey/distella/issues\n");
            exit(1);
        }

        app_data.file = argv[argc];
        app_data.configFile = changeExt(app_data.file, ".cfg");

        return app_data;
    }

    private String changeExt(String fileName, String ext) {
        int n = fileName.lastIndexOf('.');
        return fileName.substring(0, n) + ext;
    }
}
