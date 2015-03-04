package org.binxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.xml.sax.SAXException;

/**
 * @author andy
 * @creationDate on 19.07.2004
 */
public class DecoderTest extends TestBase {

	public static Test suite() {
		return new TestSuite(DecoderTest.class);
	}
    
    public void test0() {
        IXMLDecoder decoder = createDecoder();
        System.out.println(" "+decoder);
        System.out.println(" decoder performance test");

        
        for (int i = getMin(); i < getMax(); i++) {
            try {
                File input = getOutputFile(i);
                File output = getInputBackFile(i);
                if(!output.exists()) output.createNewFile();
                
                FileInputStream in = new FileInputStream(input);
                FileOutputStream out = new FileOutputStream(output);

                System.out.println("+ input    = "+input.getCanonicalPath());
                System.out.println("+ output   = "+output.getCanonicalPath());

                long time = System.currentTimeMillis();
                decoder.decode(out, in);
                time = System.currentTimeMillis() - time;
                
                long s = time/1000;
                long ms = time - s * 1000;
                
                System.out.println("+ duration = "+s+","+ms);
                
                in.close();
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
                fail(e.getMessage());
            } catch (SAXException e) {
                e.printStackTrace();
                fail(e.getMessage());
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                fail(e.getMessage());
            }
        }
    }

}