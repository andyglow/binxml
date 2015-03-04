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
package org.binxml;

import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.binxml.impl.CodeContext;
import org.binxml.impl.Data;
import org.binxml.impl.ICoderListener;
import org.binxml.impl.StructBuilder;
import org.binxml.impl.TraverseContext;
import org.binxml.impl.model.CompositeFactory;
import org.binxml.impl.model.IComposite;
import org.binxml.impl.model.NodeView;
import org.binxml.impl.model.Tag;
import org.binxml.impl.model.Value;
import org.binxml.util.Debug;
import org.binxml.util.DebugUtil;
import org.binxml.util.traverse.DOMTraverser;
import org.binxml.util.traverse.INodeVisitor;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Default DOM encoder
 * @author Andrey Onistchuk
 * @since on 01.07.2004
 */
public class DOMEncoder extends AbstractDOMEncoder {

    /**
     * 
     */
    DOMEncoder() {
    }
    /* (non-Javadoc)
     * @see javaz.xml.compressor.IXMLEncoder#encode(org.w3c.dom.Node)
     */
    public void encode(OutputStream os, Document data) throws IOException,
            SAXException, ParserConfigurationException {

        fireStart(ICoderListener.BINXML_XML2STRUCT_WORK);

        TraverseContext tctx = new TraverseContext();
        DOMTraverser dom = new DOMTraverser(tctx);

        // apply the dictionary builder first
        dom.addVisitor(new INodeVisitor() {

            public void visit(NodeView node) {
                dict.put(new Tag(node));
            }
        });

        // apply content builder at second
        dom.addVisitor(new INodeVisitor() {

            public void visit(NodeView node) {
                String value = node.getContent();
                if (value != null) content.put(new Value(value));
            }
        });

        // add the structure builder at last step
        // because we need dictionary and content fullfiled when traverser call
        // struct builder
        dom.addVisitor(new StructBuilder(dict, struct, content));
        dom.traverse(data);

        fireDone(ICoderListener.BINXML_XML2STRUCT_WORK, tctx);

        fireStart(ICoderListener.BINXML_SERIALIZE_WORK);
        CodeContext ctx = write(os);
        fireDone(ICoderListener.BINXML_SERIALIZE_WORK, ctx);

    }

}