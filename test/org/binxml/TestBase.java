package org.binxml;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

/**
 * @author andy
 * @creationDate on 19.07.2004
 */
public class TestBase extends TestCase {

    protected IXMLEncoder createEncoder() {
        if(!initialized) init();
        if(prefix=="sax") return new SAXEncoder();
        else if(prefix=="dom") return new DOMEncoder();
        else return null;
    }
    
    protected IXMLDecoder createDecoder() {
        if(!initialized) init();
        if(prefix=="sax") return new SAXDecoder();
        else if(prefix=="dom") return new DOMDecoder();
        else return null;
    }

    public void setUp() {
        checkInputDir();
        checkOutputDir();
    }
    
    protected File getInputBackFile(int index) throws IOException  {
        return new File("data/in", getFileName(index)+"_.xml");
    }

    protected File getInputFile(int index) throws IOException  {
        return new File("data/in", getFileName(index)+".xml");
    }

    protected File getOutputFile(int index) throws IOException {
        File f = new File("data/out", getFileName(index)+".bxml");
        if(!f.exists()) f.createNewFile();
        return f;
    }

    private void checkInputDir() {
        File dir = new File("data", "in");
        if (!dir.exists() || !dir.isDirectory())
                fail("\"data/in\" directory not found");
    }

    private void checkOutputDir() {
        File dir = new File("data", "out");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    private int min = -1;
    private int max = -1;
    private String prefix = null;
    private boolean initialized = false;
    
    protected int getMin() {
        if(!initialized) init();
        return min;
    }
    
    protected int getMax() {
        if(!initialized) init();
        return max;
    }

    private String getFileName(int index) {
        if(!initialized) init();
        if(index>=min && index<=max) return prefix+".test."+index;
        else return null;
    }
    
    private void init() {
        // min
        min = 0;
        String _m = System.getProperty(TestsBase.BUNDLE_PREFIX+TestsBase.MIN);
        if(_m!=null&&_m.length()>0) {
            try {
                min = Integer.parseInt(_m);
            } catch(NumberFormatException ex) { ; }
        }
        // max
        max = 0;
        _m = System.getProperty(TestsBase.BUNDLE_PREFIX+TestsBase.MAX);
        if(_m!=null&&_m.length()>0) {
            try {
                max = Integer.parseInt(_m);
            } catch(NumberFormatException ex) { ; }
        }
        // prefix
        prefix = System.getProperty(TestsBase.BUNDLE_PREFIX+TestsBase.PREFIX);
        initialized = true;
    }

}