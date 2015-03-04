package org.binxml.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.zip.GZIPOutputStream;


/**
 * @author andy
 *
 * @since on 02.07.2004
 */
public class GZipTest {
    /**
     * DOCUMENT ME!
     *
     * @param args DOCUMENT ME!
     */
    public static void main(String[] args) {
        try {
            ByteArrayOutputStream os1 = new ByteArrayOutputStream();

            File f = new File("tezzt.bin");

            if (!f.exists()) {
                f.createNewFile();
            }

            FileOutputStream os2 = new FileOutputStream(f);

            OutputStream gos1 = new GZIPOutputStream(os1);
            OutputStream gos2 = new GZIPOutputStream(os2);

            gos1.write("blah-blah".getBytes());
            gos2.write("blah-blah".getBytes());
            os1.close();

            gos1.close(); // close gzip
            gos2.close(); // close gzip

            System.out.println(os1.size());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
