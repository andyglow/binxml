package org.binxml;


/**
 * @author andy
 * @creationDate on 20.07.2004
 */
public class AllTests {

    public static void main(String[] args) {
        Tests.main(new String[] {"-prefix", "dom", "-min", "0", "-max", "3"});
        // Tests.main(new String[] {"-prefix", "sax", "-min", "0", "-max", "1"});
    }
    
}
