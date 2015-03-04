package org.binxml.impl;

import junit.framework.TestCase;

import org.binxml.CoderFactory;
import org.binxml.DOMDecoder;
import org.binxml.IDOMDecoder;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileInputStream;


/**
 * @author andy
 *
 * @since on 22.06.2004
 */
public class DecoderTest extends TestCase {
    /**
     * DOCUMENT ME!
     */
    public void test1() {
        try {
            IDOMDecoder decoder = CoderFactory.createDOMDecoder();

            File f = new File("xml.bin");
            FileInputStream fis = new FileInputStream(f);
            Document doc = decoder.decode(fis);
            fis.close();

            XMLTestUtil.serializeDoc(doc);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
