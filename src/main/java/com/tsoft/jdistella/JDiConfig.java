package com.tsoft.jdistella;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.tsoft.jdistella.JDiConst.*;
import static com.tsoft.jdistella.JDiOut.fprintf;
import static com.tsoft.jdistella.JDiOut.stderr;
import static com.tsoft.jdistella.JDiSAsm.check_range;
import static com.tsoft.jdistella.JDiSAsm.mark;

public class JDiConfig {

    public static void load_config(Path file, JDiAppData app_data) {
        int lineno = 0;

        List<String> lines;
        try {
            lines = Files.readAllLines(file);
        } catch (Exception ex) {
            throw new IllegalStateException("File " + file.toFile().getAbsolutePath() + " not found", ex);
        }

        for (String cfg_line : lines) {
            if (cfg_line.trim().isEmpty() || cfg_line.startsWith("#")) {
                continue;
            }

            String[] parts = cfg_line.split(" ");
            String cfg_tok = parts[0];

            if ("DATA".equals(cfg_tok)) {
                int cfg_beg = Integer.parseInt(parts[1], 16);
                int cfg_end = Integer.parseInt(parts[2], 16);
                check_range(app_data, cfg_beg, cfg_end);

                for (; cfg_beg <= cfg_end; ) {
                    mark(app_data, cfg_beg, DATA);
                    if (cfg_beg == cfg_end) {
                        cfg_end = 0;
                    } else {
                        cfg_beg++;
                    }
                }
            } else if ("GFX".equals(cfg_tok)) {
                int cfg_beg = Integer.parseInt(parts[1], 16);
                int cfg_end = Integer.parseInt(parts[2], 16);
                check_range(app_data, cfg_beg, cfg_end);

                for(; cfg_beg <= cfg_end; ) {
                    mark(app_data, cfg_beg, GFX);
                    if (cfg_beg == cfg_end) {
                        cfg_end = 0;
                    } else {
                        cfg_beg++;
                    }
                }
            } else if ("ORG".equals(cfg_tok)) {
                int cfg_beg = Integer.parseInt(parts[1]);
                app_data.offset = cfg_beg;
            } else if ("CODE".equals(cfg_tok)) {
                int cfg_beg = Integer.parseInt(parts[1], 16);
                int cfg_end = Integer.parseInt(parts[2], 16);
                check_range(app_data, cfg_beg, cfg_end);

                for(; cfg_beg <= cfg_end;) {
                    mark(app_data, cfg_beg, REACHABLE);
                    if (cfg_beg == cfg_end) {
                        cfg_end = 0;
                    } else {
                        cfg_beg++;
                    }
                }
            } else {
                fprintf(stderr, "Invalid line in config file - line ignored\n", lineno);
            }
        }
    }
}
