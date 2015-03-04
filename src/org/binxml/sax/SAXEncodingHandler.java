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
package org.binxml.sax;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;

import org.binxml.CompressOptions;
import org.binxml.impl.CodeContext;
import org.binxml.impl.Data;
import org.binxml.impl.StructBuilder;
import org.binxml.impl.TraverseContext;
import org.binxml.impl.model.CompositeFactory;
import org.binxml.impl.model.IComposite;
import org.binxml.impl.model.NodeView;
import org.binxml.impl.model.Tag;
import org.binxml.impl.model.Value;
import org.binxml.util.Debug;
import org.binxml.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Default SAX encoder
 * @author Andrey Onistchuk
 * @since on 01.07.2004
 */
public class SAXEncodingHandler extends DefaultHandler implements
        LexicalHandler {

    private IComposite dict = null;

    private IComposite struct = null;

    private IComposite content = null;

    private StructBuilder sb = null;

    private Document ownerDocument = null;

    private boolean processing = false;

    private Stack nodes = new Stack();

    private boolean isCDATA = false;

    private boolean isDTD = false;

    private boolean isEntity = false;
    
    private TraverseContext ctx = null;
    
    public SAXEncodingHandler(TraverseContext ctx) {
        this.ctx = ctx;
    }

    public void startDocument() throws SAXException {
        
        if(Debug.debug) {
            Debug.log.println("startDocument()");
        }
        
        dict = CompositeFactory.createSync();
        struct = CompositeFactory.createSync();
        content = CompositeFactory.createSync();
        sb = new StructBuilder(dict, struct, content);
        processing = true;
        try {
            ownerDocument = XmlUtil.createDocument();
            startNode(new NodeView(ownerDocument));
        } catch (ParserConfigurationException e) {
            ;
        }
    }

    public void endDocument() throws SAXException {
        if(Debug.debug) {
            Debug.log.println("endDocument()");
        }
        endNode();
        processing = false;
    }

    public void startElement(String ns, String ln, String qn, Attributes attrs)
            throws SAXException {
        if (!processing) return;

        if(Debug.debug) {
            Debug.log.println("startElement("+qn+")");
        }

        // put element
        NodeView curr = new NodeView(Node.ELEMENT_NODE, qn);
        startNode(curr);

        // put attrs
        for (int i = 0; i < attrs.getLength(); i++) {
            NodeView attr = new NodeView(Node.ATTRIBUTE_NODE, attrs.getQName(i));
            attr.setContent(attrs.getValue(i));
            if(Debug.debug) {
                Debug.log.println("startElement//attr("+attrs.getQName(i)+")");
            }
            startNode(attr);
            endNode();
        }
    }

    public void comment(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch, start, length);
        if(Debug.debug) {
            Debug.log.println("comment(<!--"+content+")-->");
        }
        NodeView curr = new NodeView(Node.COMMENT_NODE, null);
        curr.setContent(content);
        startNode(curr);
        endNode();
    }

    public void processingInstruction(String qn, String data)
            throws SAXException {
        if(Debug.debug) {
            Debug.log.println("processingInstruction(<?"+qn+", "+data+"?>)");
        }
        if(Debug.profile) {
            ctx.inc(Node.PROCESSING_INSTRUCTION_NODE);
        }
        NodeView curr = new NodeView(Node.PROCESSING_INSTRUCTION_NODE, qn);
        curr.setContent(data);
        startNode(curr);
        endNode();
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {

        String content = new String(ch, start, length);
        
        if(Debug.debug) {
            Debug.log.print("characters//");
            if(isCDATA) Debug.log.print("cdata");
            else if(isEntity) Debug.log.print("entity");
            else if(isDTD) Debug.log.print("dtd");
            else Debug.log.print("text");
            Debug.log.println("("+content+")");
        }
        
        NodeView curr = new NodeView(isCDATA ? Node.CDATA_SECTION_NODE
                : isEntity ? Node.ENTITY_REFERENCE_NODE : Node.TEXT_NODE, null); // FIXME
        curr.setContent(content);
        startNode(curr);
        endNode();

    }

    public void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException {
    }

    public void setDocumentLocator(Locator locator) {
        // do nothing
    }

    public void skippedEntity(String name) throws SAXException {
        // do nothing
    }

    public void startPrefixMapping(String prefix, String uri)
            throws SAXException {
        // do nothing
    }

    public void endPrefixMapping(String prefix) throws SAXException {
        // do nothing
    }

    public void endElement(String ns, String ln, String qn) throws SAXException {
        if (processing) {
            if(Debug.debug) {
                Debug.log.println("endElement("+qn+")");
            }
            // will just remove top node view
            NodeView node = (NodeView) nodes.peek();
            if (node.getNodeName().equals(qn)) nodes.pop();
        }
    }

    public void startCDATA() throws SAXException {
        isCDATA = true;
    }

    public void endCDATA() throws SAXException {
        isCDATA = false;
    }

    public void startDTD(String name, String publicId, String systemId)
            throws SAXException {
        isDTD = true;
    }

    public void endDTD() throws SAXException {
        isDTD = false;
    }

    public void startEntity(String name) throws SAXException {
        isEntity = true;
    }

    public void endEntity(String name) throws SAXException {
        isEntity = false;
    }

    // private section -----------------

    private void startNode(NodeView node) {

        if(Debug.profile) {
            ctx.inc(node.getNodeType());
        }

        // setup owner document
        node.setOwnerDocument(ownerDocument);
        // TODO: setup previous sibling
        // setup parent
        if (nodes.size() > 0) node.setParentNode((NodeView) nodes.peek());

        dict.put(new Tag(node));
        if (node.getContent() != null) content.put(new Value(node.getContent()));
        sb.visit(node);
        nodes.push(node);
       
    }

    private void endNode() {
        nodes.pop();
    }
    
    /**
     * Dumps the content of binary data into given utput stream 
     * @param os output stream
     * @param options compression options
     * @throws IOException
     */
    public CodeContext dump(OutputStream os, CompressOptions options) throws IOException {
        Data ff = new Data(dict, struct, content, options);
        return ff.write(os);
    }

}