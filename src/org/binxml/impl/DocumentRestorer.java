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
package org.binxml.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;

import org.binxml.impl.model.IComposite;
import org.binxml.impl.model.NodeRef;
import org.binxml.impl.model.Tag;
import org.binxml.impl.model.Value;
import org.binxml.util.Debug;
import org.binxml.util.XmlUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author andy
 *
 * @since on 02.07.2004
 */
public class DocumentRestorer {

    private IComposite dict;

    private IComposite struct;

    private IComposite content;

    private Document doc = null;
    
    private TraverseContext ctx = null;

    /**
     * @param dict
     * @param struct
     * @param content
     *
     * @throws ParserConfigurationException
     */
    public DocumentRestorer(IComposite dict, IComposite struct,
            IComposite content, TraverseContext ctx) throws ParserConfigurationException {
        super();
        this.dict = dict;
        this.struct = struct;
        this.content = content;
        this.ctx = ctx;
        parse();
    }

    private void parse() throws ParserConfigurationException {
        Stack stack = new Stack();

        int i = 0;

        Collection refs = struct.getAll();

        for (Iterator iter = refs.iterator(); iter.hasNext();) {
            NodeRef ref = (NodeRef) iter.next();
            Node node = restore(ref);

            if (Debug.profile) {
                ctx.inc(node.getNodeType());
            }

            if (Debug.debug) {
                Debug.log.println(" ==> node: " + node);
            }

            if (node != null) {
                switch (ref.getRelation()) {
                case (INodeRelationsheep.CHILD):
                    appendChild(stack, node, 0);

                    break;

                case (INodeRelationsheep.SIBLING):
                    appendChild(stack, node, 1);

                    break;

                default:
                    //                    if(ref.getRelation()==INodeRelationsheep.SIBLING_OF_PARENT) {
                    //                        appendChild(stack, node, 1);
                    //                    } else
                    appendChild(stack, node, ref.getRelation() - 1);

                    break;
                }
            }
        }
    }

    private void appendChild(Stack stack, Node node, int depth) {
        if (Debug.debug) {
            Debug.log.println(" depth " + depth);
        }
        for (int i = 0; i < depth; i++)
            if (!stack.isEmpty()) {
                Object o = stack.pop();
                if (Debug.debug) {
                    Debug.log.println(" pop " + o);
                }
            }

        if (!stack.isEmpty()) {
            Node n = (Node) stack.peek();

            if (Debug.debug) {
                Debug.log.println(" appendChild " + node + " to " + n);
            }

            if (node.getNodeType() == Node.ATTRIBUTE_NODE
                    && n.getNodeType() == Node.ELEMENT_NODE) {
                ((Element) n).setAttribute(node.getNodeName(), node
                        .getNodeValue());
            } else {

                n.appendChild(node);
            }
        }

        if (Debug.debug) {
            Debug.log.println(" push " + node);
        }
        stack.push(node);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Document getDocument() {
        return doc;
    }

    // --------
    private Tag getTag(NodeRef ref) {
        int idx = ref.getTagIndex();
        int size = dict.size();
        if(idx>-1 && idx<size) {
            return (Tag) dict.get(idx);
        } else
            return null;
    }

    private String getContent(NodeRef ref) {
        int size = content.size();
        int idx = ref.getContentIndex();
        if (idx > -1 && idx < size) {
            Value val = (Value) content.get(ref.getContentIndex());
            return val.getValue();
        } else
            return null;
    }

    private Node restore(NodeRef ref) throws ParserConfigurationException {
        if (Debug.debug) {
            Debug.log.print("+ ref: " + ref);
        }

        Tag tag = getTag(ref);

        if (Debug.debug) {
            Debug.log.print(" ==> tag: " + tag);
        }

        if (tag == null) { return null; }

        switch (tag.getType()) {
        case (Node.DOCUMENT_NODE): {
            doc = XmlUtil.createDocument();

            return doc;
        }

        case (Node.PROCESSING_INSTRUCTION_NODE): {
            if (doc != null) { return doc.createProcessingInstruction(tag
                    .getName(), getContent(ref)); }
        }

            break;

        case (Node.ELEMENT_NODE): {
            if (doc != null) { return doc.createElement(tag.getName()); }
        }

            break;

        case (Node.ATTRIBUTE_NODE): {
            if (doc != null) {
                Attr a = doc.createAttribute(tag.getName());
                a.setValue(getContent(ref));

                return a;
            }
        }

            break;

        case (Node.TEXT_NODE): {
            if (doc != null) { return doc.createTextNode(getContent(ref)); }
        }

            break;

        case (Node.CDATA_SECTION_NODE): {
            if (doc != null) { return doc.createCDATASection(getContent(ref)); }
        }

            break;

        case (Node.COMMENT_NODE): {
            if (doc != null) { return doc.createComment(getContent(ref)); }
        }

            break;

        case (Node.ENTITY_REFERENCE_NODE): {
            if (doc != null) { return doc.createEntityReference(tag.getName()); }
        }

            break;
        }

        return null;
    }
}