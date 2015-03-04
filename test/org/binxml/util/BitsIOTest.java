package org.binxml.util;

import junit.framework.TestCase;

import org.binxml.util.BitSet;

import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author andy
 *
 * @since on 01.07.2004
 */
public class BitsIOTest extends TestCase {
    /**
     * DOCUMENT ME!
     */
    public void test0() {
        try {
            FileOutputStream fos = new FileOutputStream("buisIOtest.fos");

            BitSet bits = new BitSet();
            bits.set(0);
            bits.clear(1);
            bits.set(2);
            bits.set(3);
            bits.set(18);

            System.out.println(bits.length());

            fos.write(bits.toByteArray());

            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
