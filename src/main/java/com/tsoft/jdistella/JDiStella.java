package com.tsoft.jdistella;

import java.nio.file.Paths;

public class JDiStella {

    public static void main(String[] args) {
        JDiCmdLine cmdLine = new JDiCmdLine();
        JDiAppData app_data = cmdLine.parse(args);

        JDiLoader loader = new JDiLoader();
        loader.file_load(Paths.get(app_data.file), app_data);

        JDiSAsm disAsm = new JDiSAsm();
        disAsm.start(app_data);
    }
}
