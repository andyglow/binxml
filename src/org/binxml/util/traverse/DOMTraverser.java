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
package org.binxml.util.traverse;

import org.binxml.impl.TraverseContext;
import org.binxml.impl.model.DOMNodeView;
import org.binxml.impl.model.NodeView;
import org.binxml.util.Debug;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * @author andy
 *
 * @since on 22.06.2004
 */
public class DOMTraverser {

    private ArrayList visitors = new ArrayList();
    private TraverseContext ctx;

    public DOMTraverser(TraverseContext ctx) {
        this.ctx = ctx;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param v DOCUMENT ME!
     */
    public void addVisitor(INodeVisitor v) {
        visitors.add(v);
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     */
    public void traverse(Node node) {
        visit(node);

        // attr
        if ((node.getNodeType() == Node.ELEMENT_NODE) && node.hasAttributes()) {
            NamedNodeMap attrs = ((Element) node).getAttributes();

            for (int i = 0; i < attrs.getLength(); i++) {
                Node attr = attrs.item(i);
                visit(attr);
            }
        }

        // child
        if (node.hasChildNodes()) {
            NodeList children = node.getChildNodes();

            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                traverse(child);
            }
        }
    }

    private void visit(Node n) {
        if(Debug.profile) {
            ctx.inc(n.getNodeType());
        }
        for (int i = 0; i < visitors.size(); i++) {
            INodeVisitor l = (INodeVisitor) visitors.get(i);
            NodeView view = new DOMNodeView(n);
            if (n.getNodeType() == Node.ATTRIBUTE_NODE) {
                Attr attr = (Attr)n;
                view.setParentNode(new NodeView(attr.getOwnerElement()));
            } else {
                if (n.getParentNode() != null)
                        view.setParentNode(new NodeView(n.getParentNode()));
            }

            if (n.getPreviousSibling() != null)
                    view
                            .setPreviousSibling(new NodeView(n
                                    .getPreviousSibling()));
            l.visit(view);
        }
    }
}