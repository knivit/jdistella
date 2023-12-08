package com.tsoft.jdistella;

import static com.tsoft.jdistella.JDiConst.*;

public class JDiTable {

    public static JDiLookupTag[] lookup = new JDiLookupTag[] {

        /****  Positive  ****/

        /* 00 */ new JDiLookupTag("BRK",   IMPLIED,      M_NONE, M_PC,   7, 0),  /* Pseudo Absolute */
        /* 01 */ new JDiLookupTag("ORA",   INDIRECT_X,   M_INDX, M_AC,   6, 0),  /* (Indirect,X) */
        /* 02 */ new JDiLookupTag(".JAM",  IMPLIED,      M_NONE, M_NONE, 0, 0),  /* TILT */
        /* 03 */ new JDiLookupTag(".SLO",  INDIRECT_X,   M_INDX, M_INDX, 8, 0),

        /* 04 */ new JDiLookupTag(".NOP",  ZERO_PAGE,    M_NONE, M_NONE, 3, 0),
        /* 05 */ new JDiLookupTag("ORA",   ZERO_PAGE,    M_ZERO, M_AC,   3, 0),  /* Zeropage */
        /* 06 */ new JDiLookupTag("ASL",   ZERO_PAGE,    M_ZERO, M_ZERO, 5, 0),  /* Zeropage */
        /* 07 */ new JDiLookupTag(".SLO",  ZERO_PAGE,    M_ZERO, M_ZERO, 5, 0),

        /* 08 */ new JDiLookupTag("PHP",   IMPLIED,      M_SR,   M_NONE, 3, 0),
        /* 09 */ new JDiLookupTag("ORA",   IMMEDIATE,    M_IMM,  M_AC,   2, 0),  /* Immediate */
        /* 0a */ new JDiLookupTag("ASL",   ACCUMULATOR,  M_AC,   M_AC,   2, 0),  /* Accumulator */
        /* 0b */ new JDiLookupTag(".ANC",  IMMEDIATE,    M_ACIM, M_ACNC, 2, 0),

        /* 0c */ new JDiLookupTag(".NOP",  ABSOLUTE,     M_NONE, M_NONE, 4, 0),
        /* 0d */ new JDiLookupTag("ORA",   ABSOLUTE,     M_ABS,  M_AC,   4, 0),  /* Absolute */
        /* 0e */ new JDiLookupTag("ASL",   ABSOLUTE,     M_ABS,  M_ABS,  6, 0),  /* Absolute */
        /* 0f */ new JDiLookupTag(".SLO",  ABSOLUTE,     M_ABS,  M_ABS,  6, 0),

        /* 10 */ new JDiLookupTag("BPL",   RELATIVE,     M_REL,  M_NONE, 2, 0),
        /* 11 */ new JDiLookupTag("ORA",   INDIRECT_Y,   M_INDY, M_AC,   5, 1),  /* (Indirect),Y */
        /* 12 */ new JDiLookupTag(".JAM",  IMPLIED,      M_NONE, M_NONE, 0, 0),  /* TILT */
        /* 13 */ new JDiLookupTag(".SLO",  INDIRECT_Y,   M_INDY, M_INDY, 8, 0),

        /* 14 */ new JDiLookupTag(".NOP",  ZERO_PAGE_X,  M_NONE, M_NONE, 4, 0),
        /* 15 */ new JDiLookupTag("ORA",   ZERO_PAGE_X,  M_ZERX, M_AC,   4, 0),  /* Zeropage,X */
        /* 16 */ new JDiLookupTag("ASL",   ZERO_PAGE_X,  M_ZERX, M_ZERX, 6, 0),  /* Zeropage,X */
        /* 17 */ new JDiLookupTag(".SLO",  ZERO_PAGE_X,  M_ZERX, M_ZERX, 6, 0),

        /* 18 */ new JDiLookupTag("CLC",   IMPLIED,      M_NONE, M_FC,   2, 0),
        /* 19 */ new JDiLookupTag("ORA",   ABSOLUTE_Y,   M_ABSY, M_AC,   4, 1),  /* Absolute,Y */
        /* 1a */ new JDiLookupTag(".NOP",  IMPLIED,      M_NONE, M_NONE, 2, 0),
        /* 1b */ new JDiLookupTag(".SLO",  ABSOLUTE_Y,   M_ABSY, M_ABSY, 7, 0),

        /* 1c */ new JDiLookupTag(".NOP",  ABSOLUTE_X,   M_NONE, M_NONE, 4, 1),
        /* 1d */ new JDiLookupTag("ORA",   ABSOLUTE_X,   M_ABSX, M_AC,   4, 1),  /* Absolute,X */
        /* 1e */ new JDiLookupTag("ASL",   ABSOLUTE_X,   M_ABSX, M_ABSX, 7, 0),  /* Absolute,X */
        /* 1f */ new JDiLookupTag(".SLO",  ABSOLUTE_X,   M_ABSX, M_ABSX, 7, 0),

        /* 20 */ new JDiLookupTag("JSR",   ABSOLUTE,     M_ADDR, M_PC,   6, 0),
        /* 21 */ new JDiLookupTag("AND",   INDIRECT_X,   M_INDX, M_AC,   6, 0),  /* (Indirect ,X) */
        /* 22 */ new JDiLookupTag(".JAM",  IMPLIED,      M_NONE, M_NONE, 0, 0),  /* TILT */
        /* 23 */ new JDiLookupTag(".RLA",  INDIRECT_X,   M_INDX, M_INDX, 8, 0),

        /* 24 */ new JDiLookupTag("BIT",   ZERO_PAGE,    M_ZERO, M_NONE, 3, 0),  /* Zeropage */
        /* 25 */ new JDiLookupTag("AND",   ZERO_PAGE,    M_ZERO, M_AC,   3, 0),  /* Zeropage */
        /* 26 */ new JDiLookupTag("ROL",   ZERO_PAGE,    M_ZERO, M_ZERO, 5, 0),  /* Zeropage */
        /* 27 */ new JDiLookupTag(".RLA",  ZERO_PAGE,    M_ZERO, M_ZERO, 5, 0),

        /* 28 */ new JDiLookupTag("PLP",   IMPLIED,      M_NONE, M_SR,   4, 0),
        /* 29 */ new JDiLookupTag("AND",   IMMEDIATE,    M_IMM,  M_AC,   2, 0),  /* Immediate */
        /* 2a */ new JDiLookupTag("ROL",   ACCUMULATOR,  M_AC,   M_AC,   2, 0),  /* Accumulator */
        /* 2b */ new JDiLookupTag(".ANC",  IMMEDIATE,    M_ACIM, M_ACNC, 2, 0),

        /* 2c */ new JDiLookupTag("BIT",   ABSOLUTE,     M_ABS,  M_NONE, 4, 0),  /* Absolute */
        /* 2d */ new JDiLookupTag("AND",   ABSOLUTE,     M_ABS,  M_AC,   4, 0),  /* Absolute */
        /* 2e */ new JDiLookupTag("ROL",   ABSOLUTE,     M_ABS,  M_ABS,  6, 0),  /* Absolute */
        /* 2f */ new JDiLookupTag(".RLA",  ABSOLUTE,     M_ABS,  M_ABS,  6, 0),

        /* 30 */ new JDiLookupTag("BMI",   RELATIVE,     M_REL,  M_NONE, 2, 0),
        /* 31 */ new JDiLookupTag("AND",   INDIRECT_Y,   M_INDY, M_AC,   5, 1),  /* (Indirect),Y */
        /* 32 */ new JDiLookupTag(".JAM",  IMPLIED,      M_NONE, M_NONE, 0, 0),  /* TILT */
        /* 33 */ new JDiLookupTag(".RLA",  INDIRECT_Y,   M_INDY, M_INDY, 8, 0),

        /* 34 */ new JDiLookupTag(".NOP",  ZERO_PAGE_X,  M_NONE, M_NONE, 4, 0),
        /* 35 */ new JDiLookupTag("AND",   ZERO_PAGE_X,  M_ZERX, M_AC,   4, 0),  /* Zeropage,X */
        /* 36 */ new JDiLookupTag("ROL",   ZERO_PAGE_X,  M_ZERX, M_ZERX, 6, 0),  /* Zeropage,X */
        /* 37 */ new JDiLookupTag(".RLA",  ZERO_PAGE_X,  M_ZERX, M_ZERX, 6, 0),

        /* 38 */ new JDiLookupTag("SEC",   IMPLIED,      M_NONE, M_FC,   2, 0),
        /* 39 */ new JDiLookupTag("AND",   ABSOLUTE_Y,   M_ABSY, M_AC,   4, 1),  /* Absolute,Y */
        /* 3a */ new JDiLookupTag(".NOP",  IMPLIED,      M_NONE, M_NONE, 2, 0),
        /* 3b */ new JDiLookupTag(".RLA",  ABSOLUTE_Y,   M_ABSY, M_ABSY, 7, 0),

        /* 3c */ new JDiLookupTag(".NOP",  ABSOLUTE_X,   M_NONE, M_NONE, 4, 1),
        /* 3d */ new JDiLookupTag("AND",   ABSOLUTE_X,   M_ABSX, M_AC,   4, 1),  /* Absolute,X */
        /* 3e */ new JDiLookupTag("ROL",   ABSOLUTE_X,   M_ABSX, M_ABSX, 7, 0),  /* Absolute,X */
        /* 3f */ new JDiLookupTag(".RLA",  ABSOLUTE_X,   M_ABSX, M_ABSX, 7, 0),

        /* 40 */ new JDiLookupTag("RTI" ,  IMPLIED,      M_NONE, M_PC,   6, 0),
        /* 41 */ new JDiLookupTag("EOR",   INDIRECT_X,   M_INDX, M_AC,   6, 0),  /* (Indirect,X) */
        /* 42 */ new JDiLookupTag(".JAM",  IMPLIED,      M_NONE, M_NONE, 0, 0),  /* TILT */
        /* 43 */ new JDiLookupTag(".SRE",  INDIRECT_X,   M_INDX, M_INDX, 8, 0),

        /* 44 */ new JDiLookupTag(".NOP",  ZERO_PAGE,    M_NONE, M_NONE, 3, 0),
        /* 45 */ new JDiLookupTag("EOR",   ZERO_PAGE,    M_ZERO, M_AC,   3, 0),  /* Zeropage */
        /* 46 */ new JDiLookupTag("LSR",   ZERO_PAGE,    M_ZERO, M_ZERO, 5, 0),  /* Zeropage */
        /* 47 */ new JDiLookupTag(".SRE",  ZERO_PAGE,    M_ZERO, M_ZERO, 5, 0),

        /* 48 */ new JDiLookupTag("PHA",   IMPLIED,      M_AC,   M_NONE, 3, 0),
        /* 49 */ new JDiLookupTag("EOR",   IMMEDIATE,    M_IMM,  M_AC,   2, 0),  /* Immediate */
        /* 4a */ new JDiLookupTag("LSR",   ACCUMULATOR,  M_AC,   M_AC,   2, 0),  /* Accumulator */
        /* 4b */ new JDiLookupTag(".ASR",  IMMEDIATE,    M_ACIM, M_AC,   2, 0),  /* (AC & IMM) >>1 */

        /* 4c */ new JDiLookupTag("JMP",   ABSOLUTE,     M_ADDR, M_PC,   3, 0),  /* Absolute */
        /* 4d */ new JDiLookupTag("EOR",   ABSOLUTE,     M_ABS,  M_AC,   4, 0),  /* Absolute */
        /* 4e */ new JDiLookupTag("LSR",   ABSOLUTE,     M_ABS,  M_ABS,  6, 0),  /* Absolute */
        /* 4f */ new JDiLookupTag(".SRE",  ABSOLUTE,     M_ABS,  M_ABS,  6, 0),

        /* 50 */ new JDiLookupTag("BVC",   RELATIVE,     M_REL,  M_NONE, 2, 0),
        /* 51 */ new JDiLookupTag("EOR",   INDIRECT_Y,   M_INDY, M_AC,   5, 1),  /* (Indirect),Y */
        /* 52 */ new JDiLookupTag(".JAM",  IMPLIED,      M_NONE, M_NONE, 0, 0),  /* TILT */
        /* 53 */ new JDiLookupTag(".SRE",  INDIRECT_Y,   M_INDY, M_INDY, 8, 0),

        /* 54 */ new JDiLookupTag(".NOP",  ZERO_PAGE_X,  M_NONE, M_NONE, 4, 0),
        /* 55 */ new JDiLookupTag("EOR",   ZERO_PAGE_X,  M_ZERX, M_AC,   4, 0),  /* Zeropage,X */
        /* 56 */ new JDiLookupTag("LSR",   ZERO_PAGE_X,  M_ZERX, M_ZERX, 6, 0),  /* Zeropage,X */
        /* 57 */ new JDiLookupTag(".SRE",  ZERO_PAGE_X,  M_ZERX, M_ZERX, 6, 0),

        /* 58 */ new JDiLookupTag("CLI",   IMPLIED,      M_NONE, M_FI,   2, 0),
        /* 59 */ new JDiLookupTag("EOR",   ABSOLUTE_Y,   M_ABSY, M_AC,   4, 1),  /* Absolute,Y */
        /* 5a */ new JDiLookupTag(".NOP",  IMPLIED,      M_NONE, M_NONE, 2, 0),
        /* 5b */ new JDiLookupTag(".SRE",  ABSOLUTE_Y,   M_ABSY, M_ABSY, 7, 0),

        /* 5c */ new JDiLookupTag(".NOP",  ABSOLUTE_X,   M_NONE, M_NONE, 4, 1),
        /* 5d */ new JDiLookupTag("EOR",   ABSOLUTE_X,   M_ABSX, M_AC,   4, 1),  /* Absolute,X */
        /* 5e */ new JDiLookupTag("LSR",   ABSOLUTE_X,   M_ABSX, M_ABSX, 7, 0),  /* Absolute,X */
        /* 5f */ new JDiLookupTag(".SRE",  ABSOLUTE_X,   M_ABSX, M_ABSX, 7, 0),

        /* 60 */ new JDiLookupTag("RTS",   IMPLIED,      M_NONE, M_PC,   6, 0),
        /* 61 */ new JDiLookupTag("ADC",   INDIRECT_X,   M_INDX, M_AC,   6, 0),  /* (Indirect,X) */
        /* 62 */ new JDiLookupTag(".JAM",  IMPLIED,      M_NONE, M_NONE, 0, 0),  /* TILT */
        /* 63 */ new JDiLookupTag(".RRA",  INDIRECT_X,   M_INDX, M_INDX, 8, 0),

        /* 64 */ new JDiLookupTag(".NOP",  ZERO_PAGE,    M_NONE, M_NONE, 3, 0),
        /* 65 */ new JDiLookupTag("ADC",   ZERO_PAGE,    M_ZERO, M_AC,   3, 0),  /* Zeropage */
        /* 66 */ new JDiLookupTag("ROR",   ZERO_PAGE,    M_ZERO, M_ZERO, 5, 0),  /* Zeropage */
        /* 67 */ new JDiLookupTag(".RRA",  ZERO_PAGE,    M_ZERO, M_ZERO, 5, 0),

        /* 68 */ new JDiLookupTag("PLA",   IMPLIED,      M_NONE, M_AC,   4, 0),
        /* 69 */ new JDiLookupTag("ADC",   IMMEDIATE,    M_IMM,  M_AC,   2, 0),  /* Immediate */
        /* 6a */ new JDiLookupTag("ROR",   ACCUMULATOR,  M_AC,   M_AC,   2, 0),  /* Accumulator */
        /* 6b */ new JDiLookupTag(".ARR",  IMMEDIATE,    M_ACIM, M_AC,   2, 0),  /* ARR isn't typo */

        /* 6c */ new JDiLookupTag("JMP",   ABS_INDIRECT, M_AIND, M_PC,   5, 0),  /* Indirect */
        /* 6d */ new JDiLookupTag("ADC",   ABSOLUTE,     M_ABS,  M_AC,   4, 0),  /* Absolute */
        /* 6e */ new JDiLookupTag("ROR",   ABSOLUTE,     M_ABS,  M_ABS,  6, 0),  /* Absolute */
        /* 6f */ new JDiLookupTag(".RRA",  ABSOLUTE,     M_ABS,  M_ABS,  6, 0),

        /* 70 */ new JDiLookupTag("BVS",   RELATIVE,     M_REL,  M_NONE, 2, 0),
        /* 71 */ new JDiLookupTag("ADC",   INDIRECT_Y,   M_INDY, M_AC,   5, 1),  /* (Indirect),Y */
        /* 72 */ new JDiLookupTag(".JAM",  IMPLIED,      M_NONE, M_NONE, 0, 0),  /* TILT relative? */
        /* 73 */ new JDiLookupTag(".RRA",  INDIRECT_Y,   M_INDY, M_INDY, 8, 0),

        /* 74 */ new JDiLookupTag(".NOP",  ZERO_PAGE_X,  M_NONE, M_NONE, 4, 0),
        /* 75 */ new JDiLookupTag("ADC",   ZERO_PAGE_X,  M_ZERX, M_AC,   4, 0),  /* Zeropage,X */
        /* 76 */ new JDiLookupTag("ROR",   ZERO_PAGE_X,  M_ZERX, M_ZERX, 6, 0),  /* Zeropage,X */
        /* 77 */ new JDiLookupTag(".RRA",  ZERO_PAGE_X,  M_ZERX, M_ZERX, 6, 0),

        /* 78 */ new JDiLookupTag("SEI",   IMPLIED,      M_NONE, M_FI,   2, 0),
        /* 79 */ new JDiLookupTag("ADC",   ABSOLUTE_Y,   M_ABSY, M_AC,   4, 1),  /* Absolute,Y */
        /* 7a */ new JDiLookupTag(".NOP",  IMPLIED,      M_NONE, M_NONE, 2, 0),
        /* 7b */ new JDiLookupTag(".RRA",  ABSOLUTE_Y,   M_ABSY, M_ABSY, 7, 0),

        /* 7c */ new JDiLookupTag(".NOP",  ABSOLUTE_X,   M_NONE, M_NONE, 4, 1),
        /* 7d */ new JDiLookupTag("ADC",   ABSOLUTE_X,   M_ABSX, M_AC,   4, 1),  /* Absolute,X */
        /* 7e */ new JDiLookupTag("ROR",   ABSOLUTE_X,   M_ABSX, M_ABSX, 7, 0),  /* Absolute,X */
        /* 7f */ new JDiLookupTag(".RRA",  ABSOLUTE_X,   M_ABSX, M_ABSX, 7, 0),

        /****  Negative  ****/

        /* 80 */ new JDiLookupTag(".NOP",  IMMEDIATE,    M_NONE, M_NONE, 2, 0),
        /* 81 */ new JDiLookupTag("STA",   INDIRECT_X,   M_AC,   M_INDX, 6, 0),  /* (Indirect,X) */
        /* 82 */ new JDiLookupTag(".NOP",  IMMEDIATE,    M_NONE, M_NONE, 2, 0),
        /* 83 */ new JDiLookupTag(".SAX",  INDIRECT_X,   M_ANXR, M_INDX, 6, 0),

        /* 84 */ new JDiLookupTag("STY",   ZERO_PAGE,    M_YR,   M_ZERO, 3, 0),  /* Zeropage */
        /* 85 */ new JDiLookupTag("STA",   ZERO_PAGE,    M_AC,   M_ZERO, 3, 0),  /* Zeropage */
        /* 86 */ new JDiLookupTag("STX",   ZERO_PAGE,    M_XR,   M_ZERO, 3, 0),  /* Zeropage */
        /* 87 */ new JDiLookupTag(".SAX",  ZERO_PAGE,    M_ANXR, M_ZERO, 3, 0),

        /* 88 */ new JDiLookupTag("DEY",   IMPLIED,      M_YR,   M_YR,   2, 0),
        /* 89 */ new JDiLookupTag(".NOP",  IMMEDIATE,    M_NONE, M_NONE, 2, 0),
        /* 8a */ new JDiLookupTag("TXA",   IMPLIED,      M_XR,   M_AC,   2, 0),
        /****  very abnormal: usually AC = AC | #$EE & XR & #$oper  ****/
        /* 8b */ new JDiLookupTag(".ANE",  IMMEDIATE,    M_AXIM, M_AC,   2, 0),

        /* 8c */ new JDiLookupTag("STY",   ABSOLUTE,     M_YR,   M_ABS,  4, 0),  /* Absolute */
        /* 8d */ new JDiLookupTag("STA",   ABSOLUTE,     M_AC,   M_ABS,  4, 0),  /* Absolute */
        /* 8e */ new JDiLookupTag("STX",   ABSOLUTE,     M_XR,   M_ABS,  4, 0),  /* Absolute */
        /* 8f */ new JDiLookupTag(".SAX",  ABSOLUTE,     M_ANXR, M_ABS,  4, 0),

        /* 90 */ new JDiLookupTag("BCC",   RELATIVE,     M_REL,  M_NONE, 2, 0),
        /* 91 */ new JDiLookupTag("STA",   INDIRECT_Y,   M_AC,   M_INDY, 6, 0),  /* (Indirect),Y */
        /* 92 */ new JDiLookupTag(".JAM",  IMPLIED,      M_NONE, M_NONE, 0, 0),  /* TILT relative? */
        /* 93 */ new JDiLookupTag(".SHA",  INDIRECT_Y,   M_ANXR, M_STH0, 6, 0),

        /* 94 */ new JDiLookupTag("STY",   ZERO_PAGE_X,  M_YR,   M_ZERX, 4, 0),  /* Zeropage,X */
        /* 95 */ new JDiLookupTag("STA",   ZERO_PAGE_X,  M_AC,   M_ZERX, 4, 0),  /* Zeropage,X */
        /* 96 */ new JDiLookupTag("STX",   ZERO_PAGE_Y,  M_XR,   M_ZERY, 4, 0),  /* Zeropage,Y */
        /* 97 */ new JDiLookupTag(".SAX",  ZERO_PAGE_Y,  M_ANXR, M_ZERY, 4, 0),

        /* 98 */ new JDiLookupTag("TYA",   IMPLIED,      M_YR,   M_AC,   2, 0),
        /* 99 */ new JDiLookupTag("STA",   ABSOLUTE_Y,   M_AC,   M_ABSY, 5, 0),  /* Absolute,Y */
        /* 9a */ new JDiLookupTag("TXS",   IMPLIED,      M_XR,   M_SP,   2, 0),
        /*** This is very mysterious command ... */
        /* 9b */ new JDiLookupTag(".SHS",  ABSOLUTE_Y,   M_ANXR, M_STH3, 5, 0),

        /* 9c */ new JDiLookupTag(".SHY",  ABSOLUTE_X,   M_YR,   M_STH2, 5, 0),
        /* 9d */ new JDiLookupTag("STA",   ABSOLUTE_X,   M_AC,   M_ABSX, 5, 0),  /* Absolute,X */
        /* 9e */ new JDiLookupTag(".SHX",  ABSOLUTE_Y,   M_XR,   M_STH1, 5, 0),
        /* 9f */ new JDiLookupTag(".SHA",  ABSOLUTE_Y,   M_ANXR, M_STH1, 5, 0),

        /* a0 */ new JDiLookupTag("LDY",   IMMEDIATE,    M_IMM,  M_YR,   2, 0),  /* Immediate */
        /* a1 */ new JDiLookupTag("LDA",   INDIRECT_X,   M_INDX, M_AC,   6, 0),  /* (indirect,X) */
        /* a2 */ new JDiLookupTag("LDX",   IMMEDIATE,    M_IMM,  M_XR,   2, 0),  /* Immediate */
        /* a3 */ new JDiLookupTag(".LAX",  INDIRECT_X,   M_INDX, M_ACXR, 6, 0),  /* (indirect,X) */

        /* a4 */ new JDiLookupTag("LDY",   ZERO_PAGE,    M_ZERO, M_YR,   3, 0),  /* Zeropage */
        /* a5 */ new JDiLookupTag("LDA",   ZERO_PAGE,    M_ZERO, M_AC,   3, 0),  /* Zeropage */
        /* a6 */ new JDiLookupTag("LDX",   ZERO_PAGE,    M_ZERO, M_XR,   3, 0),  /* Zeropage */
        /* a7 */ new JDiLookupTag(".LAX",  ZERO_PAGE,    M_ZERO, M_ACXR, 3, 0),

        /* a8 */ new JDiLookupTag("TAY",   IMPLIED,      M_AC,   M_YR,   2, 0),
        /* a9 */ new JDiLookupTag("LDA",   IMMEDIATE,    M_IMM,  M_AC,   2, 0),  /* Immediate */
        /* aa */ new JDiLookupTag("TAX",   IMPLIED,      M_AC,   M_XR,   2, 0),
        /* ab */ new JDiLookupTag(".LXA",  IMMEDIATE,    M_ACIM, M_ACXR, 2, 0),  /* LXA isn't a typo */

        /* ac */ new JDiLookupTag("LDY",   ABSOLUTE,     M_ABS,  M_YR,   4, 0),  /* Absolute */
        /* ad */ new JDiLookupTag("LDA",   ABSOLUTE,     M_ABS,  M_AC,   4, 0),  /* Absolute */
        /* ae */ new JDiLookupTag("LDX",   ABSOLUTE,     M_ABS,  M_XR,   4, 0),  /* Absolute */
        /* af */ new JDiLookupTag(".LAX",  ABSOLUTE,     M_ABS,  M_ACXR, 4, 0),

        /* b0 */ new JDiLookupTag("BCS",   RELATIVE,     M_REL,  M_NONE, 2, 0),
        /* b1 */ new JDiLookupTag("LDA",   INDIRECT_Y,   M_INDY, M_AC,   5, 1),  /* (indirect),Y */
        /* b2 */ new JDiLookupTag(".JAM",  IMPLIED,      M_NONE, M_NONE, 0, 0),  /* TILT */
        /* b3 */ new JDiLookupTag(".LAX",  INDIRECT_Y,   M_INDY, M_ACXR, 5, 1),

        /* b4 */ new JDiLookupTag("LDY",   ZERO_PAGE_X,  M_ZERX, M_YR,   4, 0),  /* Zeropage,X */
        /* b5 */ new JDiLookupTag("LDA",   ZERO_PAGE_X,  M_ZERX, M_AC,   4, 0),  /* Zeropage,X */
        /* b6 */ new JDiLookupTag("LDX",   ZERO_PAGE_Y,  M_ZERY, M_XR,   4, 0),  /* Zeropage,Y */
        /* b7 */ new JDiLookupTag(".LAX",  ZERO_PAGE_Y,  M_ZERY, M_ACXR, 4, 0),

        /* b8 */ new JDiLookupTag("CLV",   IMPLIED,      M_NONE, M_FV,   2, 0),
        /* b9 */ new JDiLookupTag("LDA",   ABSOLUTE_Y,   M_ABSY, M_AC,   4, 1),  /* Absolute,Y */
        /* ba */ new JDiLookupTag("TSX",   IMPLIED,      M_SP,   M_XR,   2, 0),
        /* bb */ new JDiLookupTag(".LAS",  ABSOLUTE_Y,   M_SABY, M_ACXS, 4, 1),

        /* bc */ new JDiLookupTag("LDY",   ABSOLUTE_X,   M_ABSX, M_YR,   4, 1),  /* Absolute,X */
        /* bd */ new JDiLookupTag("LDA",   ABSOLUTE_X,   M_ABSX, M_AC,   4, 1),  /* Absolute,X */
        /* be */ new JDiLookupTag("LDX",   ABSOLUTE_Y,   M_ABSY, M_XR,   4, 1),  /* Absolute,Y */
        /* bf */ new JDiLookupTag(".LAX",  ABSOLUTE_Y,   M_ABSY, M_ACXR, 4, 1),

        /* c0 */ new JDiLookupTag("CPY",   IMMEDIATE,    M_IMM,  M_NONE, 2, 0),  /* Immediate */
        /* c1 */ new JDiLookupTag("CMP",   INDIRECT_X,   M_INDX, M_NONE, 6, 0),  /* (Indirect,X) */
        /* c2 */ new JDiLookupTag(".NOP",  IMMEDIATE,    M_NONE, M_NONE, 2, 0),  /* occasional TILT */
        /* c3 */ new JDiLookupTag(".DCP",  INDIRECT_X,   M_INDX, M_INDX, 8, 0),

        /* c4 */ new JDiLookupTag("CPY",   ZERO_PAGE,    M_ZERO, M_NONE, 3, 0),  /* Zeropage */
        /* c5 */ new JDiLookupTag("CMP",   ZERO_PAGE,    M_ZERO, M_NONE, 3, 0),  /* Zeropage */
        /* c6 */ new JDiLookupTag("DEC",   ZERO_PAGE,    M_ZERO, M_ZERO, 5, 0),  /* Zeropage */
        /* c7 */ new JDiLookupTag(".DCP",  ZERO_PAGE,    M_ZERO, M_ZERO, 5, 0),

        /* c8 */ new JDiLookupTag("INY",   IMPLIED,      M_YR,   M_YR,   2, 0),
        /* c9 */ new JDiLookupTag("CMP",   IMMEDIATE,    M_IMM,  M_NONE, 2, 0),  /* Immediate */
        /* ca */ new JDiLookupTag("DEX",   IMPLIED,      M_XR,   M_XR,   2, 0),
        /* cb */ new JDiLookupTag(".SBX",  IMMEDIATE,    M_IMM,  M_XR,   2, 0),

        /* cc */ new JDiLookupTag("CPY",   ABSOLUTE,     M_ABS,  M_NONE, 4, 0),  /* Absolute */
        /* cd */ new JDiLookupTag("CMP",   ABSOLUTE,     M_ABS,  M_NONE, 4, 0),  /* Absolute */
        /* ce */ new JDiLookupTag("DEC",   ABSOLUTE,     M_ABS,  M_ABS,  6, 0),  /* Absolute */
        /* cf */ new JDiLookupTag(".DCP",  ABSOLUTE,     M_ABS,  M_ABS,  6, 0),

        /* d0 */ new JDiLookupTag("BNE",   RELATIVE,     M_REL,  M_NONE, 2, 0),
        /* d1 */ new JDiLookupTag("CMP",   INDIRECT_Y,   M_INDY, M_NONE, 5, 1),  /* (Indirect),Y */
        /* d2 */ new JDiLookupTag(".JAM",  IMPLIED,      M_NONE, M_NONE, 0, 0),  /* TILT */
        /* d3 */ new JDiLookupTag(".DCP",  INDIRECT_Y,   M_INDY, M_INDY, 8, 0),

        /* d4 */ new JDiLookupTag(".NOP",  ZERO_PAGE_X,  M_NONE, M_NONE, 4, 0),
        /* d5 */ new JDiLookupTag("CMP",   ZERO_PAGE_X,  M_ZERX, M_NONE, 4, 0),  /* Zeropage,X */
        /* d6 */ new JDiLookupTag("DEC",   ZERO_PAGE_X,  M_ZERX, M_ZERX, 6, 0),  /* Zeropage,X */
        /* d7 */ new JDiLookupTag(".DCP",  ZERO_PAGE_X,  M_ZERX, M_ZERX, 6, 0),

        /* d8 */ new JDiLookupTag("CLD",   IMPLIED,      M_NONE, M_FD,   2, 0),
        /* d9 */ new JDiLookupTag("CMP",   ABSOLUTE_Y,   M_ABSY, M_NONE, 4, 1),  /* Absolute,Y */
        /* da */ new JDiLookupTag(".NOP",  IMPLIED,      M_NONE, M_NONE, 2, 0),
        /* db */ new JDiLookupTag(".DCP",  ABSOLUTE_Y,   M_ABSY, M_ABSY, 7, 0),

        /* dc */ new JDiLookupTag(".NOP",  ABSOLUTE_X,   M_NONE, M_NONE, 4, 1),
        /* dd */ new JDiLookupTag("CMP",   ABSOLUTE_X,   M_ABSX, M_NONE, 4, 1),  /* Absolute,X */
        /* de */ new JDiLookupTag("DEC",   ABSOLUTE_X,   M_ABSX, M_ABSX, 7, 0),  /* Absolute,X */
        /* df */ new JDiLookupTag(".DCP",  ABSOLUTE_X,   M_ABSX, M_ABSX, 7, 0),

        /* e0 */ new JDiLookupTag("CPX",   IMMEDIATE,    M_IMM,  M_NONE, 2, 0),  /* Immediate */
        /* e1 */ new JDiLookupTag("SBC",   INDIRECT_X,   M_INDX, M_AC,   6, 0),  /* (Indirect,X) */
        /* e2 */ new JDiLookupTag(".NOP",  IMMEDIATE,    M_NONE, M_NONE, 2, 0),
        /* e3 */ new JDiLookupTag(".ISB",  INDIRECT_X,   M_INDX, M_INDX, 8, 0),

        /* e4 */ new JDiLookupTag("CPX",   ZERO_PAGE,    M_ZERO, M_NONE, 3, 0),  /* Zeropage */
        /* e5 */ new JDiLookupTag("SBC",   ZERO_PAGE,    M_ZERO, M_AC,   3, 0),  /* Zeropage */
        /* e6 */ new JDiLookupTag("INC",   ZERO_PAGE,    M_ZERO, M_ZERO, 5, 0),  /* Zeropage */
        /* e7 */ new JDiLookupTag(".ISB",  ZERO_PAGE,    M_ZERO, M_ZERO, 5, 0),

        /* e8 */ new JDiLookupTag("INX",   IMPLIED,      M_XR,   M_XR,   2, 0),
        /* e9 */ new JDiLookupTag("SBC",   IMMEDIATE,    M_IMM,  M_AC,   2, 0),  /* Immediate */
        /* ea */ new JDiLookupTag("NOP",   IMPLIED,      M_NONE, M_NONE, 2, 0),
        /* eb */ new JDiLookupTag(".USBC", IMMEDIATE,    M_IMM,  M_AC,   2, 0),  /* same as e9 */

        /* ec */ new JDiLookupTag("CPX",   ABSOLUTE,     M_ABS,  M_NONE, 4, 0),  /* Absolute */
        /* ed */ new JDiLookupTag("SBC",   ABSOLUTE,     M_ABS,  M_AC,   4, 0),  /* Absolute */
        /* ee */ new JDiLookupTag("INC",   ABSOLUTE,     M_ABS,  M_ABS,  6, 0),  /* Absolute */
        /* ef */ new JDiLookupTag(".ISB",  ABSOLUTE,     M_ABS,  M_ABS,  6, 0),

        /* f0 */ new JDiLookupTag("BEQ",   RELATIVE,     M_REL,  M_NONE, 2, 0),
        /* f1 */ new JDiLookupTag("SBC",   INDIRECT_Y,   M_INDY, M_AC,   5, 1),  /* (Indirect),Y */
        /* f2 */ new JDiLookupTag(".JAM",  IMPLIED,      M_NONE, M_NONE, 0, 0),  /* TILT */
        /* f3 */ new JDiLookupTag(".ISB",  INDIRECT_Y,   M_INDY, M_INDY, 8, 0),

        /* f4 */ new JDiLookupTag(".NOP",  ZERO_PAGE_X,  M_NONE, M_NONE, 4, 0),
        /* f5 */ new JDiLookupTag("SBC",   ZERO_PAGE_X,  M_ZERX, M_AC,   4, 0),  /* Zeropage,X */
        /* f6 */ new JDiLookupTag("INC",   ZERO_PAGE_X,  M_ZERX, M_ZERX, 6, 0),  /* Zeropage,X */
        /* f7 */ new JDiLookupTag(".ISB",  ZERO_PAGE_X,  M_ZERX, M_ZERX, 6, 0),

        /* f8 */ new JDiLookupTag("SED",   IMPLIED,      M_NONE, M_FD,   2, 0),
        /* f9 */ new JDiLookupTag("SBC",   ABSOLUTE_Y,   M_ABSY, M_AC,   4, 1),  /* Absolute,Y */
        /* fa */ new JDiLookupTag(".NOP",  IMPLIED,      M_NONE, M_NONE, 2, 0),
        /* fb */ new JDiLookupTag(".ISB",  ABSOLUTE_Y,   M_ABSY, M_ABSY, 7, 0),

        /* fc */ new JDiLookupTag(".NOP",  ABSOLUTE_X,   M_NONE, M_NONE, 4, 1),
        /* fd */ new JDiLookupTag("SBC",   ABSOLUTE_X,   M_ABSX, M_AC,   4, 1),  /* Absolute,X */
        /* fe */ new JDiLookupTag("INC",   ABSOLUTE_X,   M_ABSX, M_ABSX, 7, 0),  /* Absolute,X */
        /* ff */ new JDiLookupTag(".ISB",  ABSOLUTE_X,   M_ABSX, M_ABSX, 7, 0)
    };
}
