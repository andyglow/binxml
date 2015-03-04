package org.binxml.util;

import java.io.PrintStream;
import java.io.PrintWriter;


/**
 * @author andy
 */
public class Debug {

    public static final boolean debug = false;
    public static final boolean profile = true;
    
    public static PrintWriter log = null;
    
    static {
        if(debug || profile)
            log = new PrintWriter(System.out, true);
    }
    
}
