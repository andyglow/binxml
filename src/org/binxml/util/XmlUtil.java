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
package org.binxml.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.Serializer;
import org.apache.xml.serialize.SerializerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * @author andy
 *
 * @since on 22.06.2004
 */
public class XmlUtil {
    private static DocumentBuilderFactory dbf = null;

    public static void serialize(Document doc, OutputStream os) throws IOException {
        SerializerFactory sf = SerializerFactory.getSerializerFactory("xml");
        OutputFormat format = new OutputFormat();
        format.setIndenting(true);
        format.setIndent(2);
        Serializer s = sf.makeSerializer(format);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        s.setOutputByteStream(bos);
        s.asDOMSerializer().serialize(doc);
        bos.flush();
        bos.close();
    }
    
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static DocumentBuilderFactory createXMLDBFactory() {
        if (dbf == null) {
            dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            dbf.setValidating(false);
            dbf.setIgnoringComments(false);
            dbf.setIgnoringElementContentWhitespace(false);
            dbf.setExpandEntityReferences(true);
        }

        return dbf;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ParserConfigurationException DOCUMENT ME!
     */
    public static DocumentBuilder createXMLDocumentBuilder()
        throws ParserConfigurationException {
        return createXMLDBFactory().newDocumentBuilder();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ParserConfigurationException DOCUMENT ME!
     */
    public static Document createDocument() throws ParserConfigurationException {
        return createXMLDocumentBuilder().newDocument();
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static String contentOf(Node node) {
        String value = null;

        switch (node.getNodeType()) {
        case (Node.ATTRIBUTE_NODE):
            value = ((Attr) node).getValue();

            break;

        case (Node.PROCESSING_INSTRUCTION_NODE):
            value = ((ProcessingInstruction) node).getData();

            break;

        case (Node.CDATA_SECTION_NODE):
        case (Node.TEXT_NODE):
            value = ((Text) node).getData();

            break;

        case (Node.COMMENT_NODE):
            value = ((Comment) node).getData();

            break;
        }

        if ((value != null) && (value.length() == 0)) {
            value = null;
        }

        return value;
    }
    
    
}
