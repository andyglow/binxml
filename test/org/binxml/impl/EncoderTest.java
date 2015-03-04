package org.binxml.impl;

import junit.framework.TestCase;

import org.binxml.CoderFactory;
import org.binxml.CompressOptions;
import org.binxml.DOMEncoder;
import org.binxml.IDOMEncoder;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;


/**
 * @author andy
 *
 * @since on 22.06.2004
 */
public class EncoderTest extends TestCase {
    /**
     * DOCUMENT ME!
     */
    public void test1() {
        try {
            Document doc = XMLTestUtil.createDoc();

            File f = new File("xml.bin");

            if (!f.exists()) {
                f.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(f);
            IDOMEncoder encoder = CoderFactory.createDOMEncoder();
            encoder.getOptions().setMethod(CompressOptions.GZIP_COMPRESSION_METHOD);
            encoder.encode(fos, doc);
            fos.close();

            f = new File("xml.xml");

            FileWriter fw = new FileWriter(f);

            if (!f.exists()) {
                f.createNewFile();
            }

            XMLTestUtil.serializeDoc(fw, doc);
            fw.close();

            //			f = new File("maven-project.bin");
            //			if(!f.exists()) f.createNewFile();
            //			fos = new FileOutputStream(f);
            //			encoder.encode(fos, new FileInputStream("maven-project.xsd"));
            //			fos.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
