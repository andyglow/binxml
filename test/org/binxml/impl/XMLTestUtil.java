package org.binxml.impl;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.Serializer;
import org.apache.xml.serialize.SerializerFactory;

import org.binxml.util.XmlUtil;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.ProcessingInstruction;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;


/**
 * @author andy
 *
 * @since on 01.07.2004
 */
public class XMLTestUtil {
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ParserConfigurationException DOCUMENT ME!
     */
    public static Document createDoc() throws ParserConfigurationException {
        Document doc = XmlUtil.createDocument();
        ProcessingInstruction pi = doc.createProcessingInstruction("target",
                "data");
        doc.appendChild(pi);

        Element root = doc.createElement("test_e");
        doc.appendChild(root);

        Element e = doc.createElement("z_e");
        root.appendChild(e);

        DocumentFragment fragment = doc.createDocumentFragment();

        fragment.appendChild(doc.createTextNode("fufer"));
        fragment.appendChild(doc.createCDATASection("puper"));
        fragment.appendChild(doc.createTextNode("fufer"));

        EntityReference ent = doc.createEntityReference("nbsp");
        fragment.appendChild(ent);

        Element e1 = doc.createElement("val1");
        Element e2 = doc.createElement("val2");
        e1.appendChild(e2);
        fragment.appendChild(e1);
        e2.appendChild(doc.createElement("val3"));

        e.appendChild(fragment);
        e.appendChild(doc.createElement("val4"));

        root.setAttribute("z_a", "x");
        e.setAttribute("z_a", "x1");

        return doc;
    }

    /**
     * DOCUMENT ME!
     *
     * @param writer DOCUMENT ME!
     * @param doc DOCUMENT ME!
     *
     * @throws IOException DOCUMENT ME!
     */
    public static void serializeDoc(Writer writer, Document doc)
        throws IOException {
        SerializerFactory sf = SerializerFactory.getSerializerFactory("xml");
        OutputFormat format = new OutputFormat(doc);
        format.setIndenting(true);
        format.setIndent(5);

        Serializer ser = sf.makeSerializer(format);
        ser.setOutputCharStream(writer);
        ser.setOutputFormat(format);
        ser.asDOMSerializer().serialize(doc);
    }

    /**
     * DOCUMENT ME!
     *
     * @param doc DOCUMENT ME!
     *
     * @throws IOException DOCUMENT ME!
     */
    public static void serializeDoc(Document doc) throws IOException {
        serializeDoc(new PrintWriter(System.out, true), doc);
    }
}
