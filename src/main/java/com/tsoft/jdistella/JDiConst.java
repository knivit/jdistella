package com.tsoft.jdistella;

public class JDiConst {

    public static int M_NONE = 0;
    public static int M_AC = 1;
    public static int M_XR = 2;
    public static int M_YR = 3;
    public static int M_SP = 4;
    public static int M_SR = 5;
    public static int M_PC = 6;
    public static int M_IMM = 7;
    public static int M_ZERO = 8;
    public static int M_ZERX = 9;
    public static int M_ZERY = 10;
    public static int M_ABS = 11;
    public static int M_ABSX = 12;
    public static int M_ABSY = 13;
    public static int M_AIND = 14;
    public static int M_INDX = 15;
    public static int M_INDY = 16;
    public static int M_REL = 17;
    public static int M_FC = 18;
    public static int M_FD = 19;
    public static int M_FI = 20;
    public static int M_FV = 21;
    public static int M_ADDR = 22;
    public static int M_ = 23;

    public static int M_ACIM = 24;	/* Source: AC & IMMED (bus collision) */
    public static int M_ANXR = 25;	/* Source: AC & XR (bus collision) */
    public static int M_AXIM = 26;	/* Source: (AC | #EE) & XR & IMMED (bus collision) */
    public static int M_ACNC = 27;	/* Dest: M_AC and Carry = Negative */
    public static int M_ACXR = 28;	/* Dest: M_AC, M_XR */

    public static int M_SABY = 29;	/* Source: (ABS_Y & SP) (bus collision) */
    public static int M_ACXS = 30;	/* Dest: M_AC, M_XR, M_SP */
    public static int M_STH0 = 31;	/* Dest: Store (src & Addr_Hi+1) to (Addr +0x100) */
    public static int M_STH1 = 32;
    public static int M_STH2 = 33;
    public static int M_STH3 = 34;

    public static int IMPLIED = 0;
    public static int ACCUMULATOR = 1;
    public static int IMMEDIATE = 2;

    public static int ZERO_PAGE = 3;
    public static int ZERO_PAGE_X = 4;
    public static int ZERO_PAGE_Y = 5;

    public static int ABSOLUTE = 6;
    public static int ABSOLUTE_X = 7;
    public static int ABSOLUTE_Y = 8;

    public static int ABS_INDIRECT = 9;
    public static int INDIRECT_X = 10;
    public static int INDIRECT_Y = 11;

    public static int RELATIVE = 12;

    public static int ASS_CODE = 13;

    /* Marked bits
        This is a reference sheet of bits that can be set for a given address, which
        are stored in the labels[] array.
    */

    public static int REFERENCED = 1;	/* code somewhere in the program references it, i.e. LDA $F372 referenced $F372 */
    public static int VALID_ENTRY = 2;	/* addresses that can have a label placed in front of it. A good counterexample
                       would be "FF00: LDA $FE00"; $FF01 would be in the middle of a multi-byte
                       instruction, and therefore cannot be labelled. */
    public static int DATA = 4;
    public static int GFX = 8;
    public static int REACHABLE = 16;	/* disassemble-able code segments */

    /* Boolean definitions for Atari 7800 header presence */
    public static int NO_HEADER = 0;
    public static int YES_HEADER = 1;
}
