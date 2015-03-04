package org.binxml;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;


/**
 * @author andy
 * @creationDate on 19.07.2004
 */
public abstract class TestsBase {

    public static final String PREFIX = "prefix";
    public static final String MIN = "min";
    public static final String MAX = "max";
    
    public static final String BUNDLE_PREFIX = "org.binxml.tests.";
    
    protected static void parseArgs(String[] args) {
        int index = 0;
        while(index<args.length) {
            handleOpt(PREFIX, args, index);
            handleOpt(MIN, args, index);
            handleOpt(MAX, args, index);
            index++;
        }
    }
    
    private static void handleOpt(String opt, String[] args, int index) {
        if(args[index].equals("-"+opt) && args.length>=index+1) {
            System.setProperty(BUNDLE_PREFIX+opt, args[index+1]);
        }
    }
    
}
