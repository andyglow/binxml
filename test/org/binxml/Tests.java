package org.binxml;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * @author andy
 * @creationDate on 19.07.2004
 */
public class Tests extends TestsBase {

    public static void main(String[] args) {
        parseArgs(args);
        TestRunner.run(suite());
    }

    public static Test suite() {
        TestSuite result = new TestSuite("Encoding/Decoding Tests");
        result.addTest(EncoderTest.suite());
        result.addTest(DecoderTest.suite());
        return result;
    }

}