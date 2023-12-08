package com.tsoft.jdistella;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JDiLookupTag {

    final String mnemonic;	/* Selfdocumenting? */
    final int addr_mode;
    final int source;
    final int destination;
    final int cycles;
    final int pbc_fix;	/* Cycle for Page Boundary Crossing */
}
