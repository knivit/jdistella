package com.tsoft.jdistella;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static com.tsoft.jdistella.JDiConst.YES_HEADER;
import static com.tsoft.jdistella.JDiOut.printf;
import static java.lang.System.exit;

public class JDiLoader {

    public void file_load(Path file, JDiAppData app_data) {
        int loop_counter; /* For looping through a 7800 header to make sure its valid */

        byte[] data;
        try {
            data = Files.readAllBytes(file);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }

        if (app_data.length == 0) {
            app_data.length = data.length;
        }

        if (app_data.a78flag == 0) {
            if (app_data.length == 2048) {
                app_data.end = 0x7ff;
            } else if (app_data.length == 4096) {
                app_data.end = 0xfff;
            } else {
                printf("Error: .bin file must be 2048 or 4096 bytes\n");
                printf(" for 2600 games; For 7800 games, .bin file must be\n");
                printf(" 16384, 32768 or 49152 bytes (+128 bytes if header appended)\n");
                printf(" Also, the -7 option must be set or unset appropriately\n");
                exit(1);
            }
        }
        else /* (a78flag == 1) */
        {
            switch (app_data.length)
            {
                /* No 8k 7800 roms exist, so there is no 8K support at this time */
	    /* case 8320:
	        hdr_exists = YES_HEADER;
	    case 8192:
                app_data.end = 0x1fff;
	        break; */
                case 16512:
                    app_data.hdr_exists = YES_HEADER;
                case 16384:
                    app_data.end = 0x3fff;
                    break;
                case 32896:
                    app_data.hdr_exists = YES_HEADER;
                case 32768:
                    app_data.end = 0x7fff;
                    break;
                case 49280:
                    app_data.hdr_exists = YES_HEADER;
                case 49152:
                    app_data.end = 0xbfff;
                    break;
                default:
                    printf("Error: .bin file must be 2048 or 4096 bytes\n");
                    printf(" for 2600 games; For 7800 games, .bin file must be\n");
                    printf(" 16384, 32768 or 49152 bytes (+128 bytes if header appended)\n");
                    printf(" Also, the -7 option must be set or unset appropriately\n");
                    exit(1);
                    break;
            }
        }

        /* If it's got a 7800 header, get some info from it */
        byte[] hdr78;
        if (app_data.hdr_exists == YES_HEADER) {
            /* Dynamically allocate memory for 7800 header (if applicable) */
            hdr78 = Arrays.copyOfRange(data, 0, 128);

            String hdr_string = "ACTUAL CART DATA STARTS HERE";

            /* Exit if the header text string does not exist */
            for (loop_counter = 0; loop_counter < 28; loop_counter++) {
                if (hdr_string.charAt(loop_counter) != hdr78[100+loop_counter]) {
                    printf("a78 file has incorrect header\n");
                    exit(1);
                }
            }

            /* Header is correct, so check for POKEY support */
            if ((hdr78[54] & 0x01) == 1) {
                /* then it's a POKEY cart, so we turn on POKEY equates */
                app_data.kflag = 1;
            } else {
                /* NOT a POKEY cart, so disable POKEY equates */
                app_data.kflag = 0;
            }

            /* read in the rest of the file (i.e. the data) */
            app_data.mem = Arrays.copyOfRange(data, 128, data.length - 128);
        }

        /* if no header exists, just read in the file data */
        else {
            app_data.mem = data;
        }

        if (app_data.start == 0) {
            app_data.start = app_data.load;
        }
    }
}
