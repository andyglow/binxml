/*
 *  Binary XML
 *
 *  Copyright (C) 2004 Andrey Onistchuk <andy@tiff.ru>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  See the LICENSE file located in the top-level-directory of
 *  the archive of this library for complete text of license.
 */
package org.binxml.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.OutputFormat;
import org.binxml.AbstractCoder;
import org.binxml.CoderFactory;
import org.binxml.CompressOptions;
import org.binxml.DOMDecoder;
import org.binxml.DOMEncoder;
import org.binxml.IDOMDecoder;
import org.binxml.IDOMEncoder;
import org.binxml.IXMLDecoder;
import org.binxml.IXMLEncoder;
import org.binxml.SAXDecoder;
import org.binxml.SAXEncoder;
import org.binxml.impl.ICoderListener;
import org.xml.sax.SAXException;

/**
 * Example application. Command line for encoding/decoding routines with simple profiling functions. 
 * @author andy
 * @creationDate on 19.07.2004
 */
public class ProfileBuilder {

    public static void main(String[] args) {
        run(args);
    }

    public static final void usage() {
        System.out.println("BINXML Profile builder");
        System.out.println("Usage:");
        System.out.println(" --help -h /?\tthis help;");
        System.out.println(" --input -i\tinput xml file;");
        System.out.println(" --boutput -bo\toutput binary file;");
        System.out.println(" --xoutput -xo\toutput xml file;");
        System.out.println(" --profile -p\treport html file;");
        System.out.println(" --compress -c\t(gzip default, bzip2, none);");
    }

    public static void run(String[] args) {
        ProfileBuilder builder = new ProfileBuilder();
        builder.runInternal(args);
    }

    private String inputFileName = null;

    private String outputBFileName = null;

    private String outputXFileName = null;

    private int compress = CompressOptions.GZIP_COMPRESSION_METHOD;

    private PrintWriter reportWriter = null;

    private void runInternal(String[] args) {
        // 1. read opts
        parseArgs(args, new OptHandler[] {
                new OptHandler(new String[] { "--input", "-i" }) {

                    public void handle(String s) {
                        inputFileName = s;
                    }
                }, new OptHandler(new String[] { "--boutput", "-bo" }) {

                    public void handle(String s) {
                        outputBFileName = s;
                    }
                }, new OptHandler(new String[] { "--xoutput", "-xo" }) {

                    public void handle(String s) {
                        outputXFileName = s;
                    }
                }, new OptHandler(new String[] { "--compress", "-c" }) {

                    public void handle(String s) {
                        if (s.equalsIgnoreCase("none"))
                            compress = CompressOptions.NO_COMPRESSION_METHOD;
                        else if (s.equalsIgnoreCase("bzip2"))
                                compress = CompressOptions.BZIP_COMPRESSION_METHOD;
                    }
                }, new OptHandler(new String[] { "--profile", "-p" }) {

                    public void handle(String s) {
                        try {
                            reportWriter = new PrintWriter(
                                    new FileOutputStream(new File(s)), true);
                        } catch (FileNotFoundException e) {
                            ;
                        }
                    }
                } });
        if (inputFileName == null || outputXFileName == null
                || outputBFileName == null) {
            usage();
            System.exit(1);
        }
        if (reportWriter == null) {
            reportWriter = new PrintWriter(System.out, true);
        }
        runProfiler();
    }

    private void parseArgs(String[] args, OptHandler[] ohs) {
        int index = 0;
        while (index < args.length) {
            for (int i = 0; i < ohs.length; i++) {
                OptHandler o = ohs[i];
                if (args.length >= index + 1 && o.accept(args[index]))
                        o.handle(args[index + 1]);
            }
            index += 2;
        }
    }

private void runProfiler() {
        File inputFile = new File(inputFileName);
        File outputBFile = new File(outputBFileName);
        File outputXFile = new File(outputXFileName);
        if (inputFile.exists()) {
            final ProfileReport prof = new ProfileReport();
            ICoderListener cl = new ICoderListener() {

                private long startTime;

                private ProfileReport.Phase phase = null;

                public void start(int work) {
                    startTime = System.currentTimeMillis();
                    phase = prof.newPhase();
                    switch (work) {
                    case (ICoderListener.JAXP_PARSE_WORK): {
                        phase.setResponsibleApi(ProfileReport.Phase.JAXP);
                        phase.setName("parsing");
                    }
                        break;
                    case (ICoderListener.JAXP_SERIALIZE_WORK): {
                        phase.setResponsibleApi(ProfileReport.Phase.JAXP);
                        phase.setName("serializing");
                    }
                        break;
                    case (ICoderListener.BINXML_PARSE_WORK): {
                        phase.setResponsibleApi(ProfileReport.Phase.BINXML);
                        phase.setName("parsing");
                    }
                        break;
                    case (ICoderListener.BINXML_SERIALIZE_WORK): {
                        phase.setResponsibleApi(ProfileReport.Phase.BINXML);
                        phase.setName("serializing");
                    }
                        break;
                    case (ICoderListener.BINXML_STRUCT2XML_WORK): {
                        phase.setResponsibleApi(ProfileReport.Phase.BINXML);
                        phase.setName("structure -> xml");
                    }
                        break;
                    case (ICoderListener.BINXML_XML2STRUCT_WORK): {
                        phase.setResponsibleApi(ProfileReport.Phase.BINXML);
                        phase.setName("xml -> structure");
                    }
                        break;
                    }
                }

                public void done(int work, Object stat) {
                    long duration = System.currentTimeMillis() - startTime;
                    phase.setLapsedLime(duration);
                    phase.setStatistic(stat);
                }
            };
            prof.setFileSize(inputFile.length());
            prof.newRecord().setText("<h3>DOM</h3>");
            prof.newRecord().setText("<h5>Encoding</h5>");
            File f = new File(createFN("dom.",outputBFileName));
            DOMEncoder de = (DOMEncoder) CoderFactory.createDOMEncoder();
            de.getOptions().setMethod(compress);
            perform(de,cl, inputFile, f);
            prof.newRecord().setText("<small>Result file size: "+f.length()+" b</small>");
            prof.newRecord().setText("<h5>Decoding</h5>");
            perform((DOMDecoder) CoderFactory.createDOMDecoder(),cl, new File(createFN("dom.",outputBFileName)), new File(createFN("dom.",outputXFileName)));
            prof.newRecord().setText("<h3>SAX</h3>");
            prof.newRecord().setText("<h5>Encoding</h5>");
            f = new File(createFN("sax.",outputBFileName));
            SAXEncoder se = (SAXEncoder) CoderFactory.createSAXEncoder();
            se.getOptions().setMethod(compress);
            perform(se,cl, inputFile, f);
            prof.newRecord().setText("<small>Result file size: "+f.length()+" b</small>");
            prof.newRecord().setText("<h5>Decoding not implemented yet</h5>");
            // TODO: enable it when SAXDecoder become implemented well
            // perform((SAXDecoder) CoderFactory.createSAXDecoder(),cl, new File(createFN("sax.",outputBFileName)), new File(createFN("sax.",outputXFileName)));

            reportWriter.println(prof.toString());
        } else {
            System.err.println("Input file not found. " + inputFileName);
        }
    }    private String createFN(String prefix, String fn) {
        File f = new File(fn);
        File p = f.getAbsoluteFile().getParentFile();
        return new File(p, prefix + f.getName()).getAbsolutePath();
    }

    private void perform(AbstractCoder coder, ICoderListener cl, File in,
            File out) {
        try {
            FileInputStream is = new FileInputStream(in);
            coder.addListener(cl);

            if (!out.exists()) out.createNewFile();
            FileOutputStream os = new FileOutputStream(out);

            if (coder instanceof IXMLEncoder)
                ((IXMLEncoder) coder).encode(os, is);
            else if (coder instanceof IXMLDecoder)
                    ((IXMLDecoder) coder).decode(os, is);
            
            is.close();
            os.close();

        } catch (SAXException e) {
            ;
        } catch (ParserConfigurationException e) {
            ;
        } catch (FileNotFoundException e) {
            ;
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    abstract class OptHandler {

        private String[] names;

        public OptHandler(String[] names) {
            this.names = names;
        }

        public boolean accept(String opt) {
            for (int i = 0; i < names.length; i++)
                if (names[i].equals(opt)) return true;
            return false;
        }

        abstract public void handle(String param);
    }

}