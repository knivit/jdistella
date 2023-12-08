package com.tsoft.jdistella;

public class JDiAppData {

    String file;
    String configFile;

    int start;
    int load;
    int length;
    int end;
    int disp_data;

    int hdr_exists;
    int aflag;
    int cflag;
    int dflag;
    int fflag;
    int pflag;
    int rflag;
    int hflag;
    int sflag;
    int intflag;
    int a78flag;
    int bflag;
    int kflag;

    int lineno;
    int offset;

    String orgmnc;
    byte[] labels;

    byte[] mem;

    byte[] reserved = new byte[640];
    byte[] ioresrvd = new byte[240];
    byte[] pokresvd = new byte[160];
}
