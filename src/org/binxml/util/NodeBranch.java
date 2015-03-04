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

import java.util.ArrayList;

import org.binxml.impl.model.DOMNodeView;
import org.binxml.impl.model.NodeView;
import org.w3c.dom.Node;

/**
 * @author andy
 *
 * @since on 01.07.2004
 */
public class NodeBranch {

    private ArrayList nodes = new ArrayList();

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static NodeBranch buildFor(DOMNodeView node) {
        NodeBranch nb = new NodeBranch();

        for (Node n = node.getUnderlying(); n != null; n = n.getParentNode())
            nb.add(n);

        return nb;
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     */
    public void add(Node node) {
        if (node == null) { return; }

        Node last = null;

        if (nodes.size() > 0) {
            last = (Node) nodes.get(0);
        }

        if (last != null) {
            // if last node on stack are not equals to followed node
            // we must skip in. Or throw an exception :)
            if (!last.getParentNode().equals(node)) {
                // System.out.println("not parent");
                return;
            }
        }

        nodes.add(0, node);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getDepth() {
        return nodes.size();
    }

    /**
     * DOCUMENT ME!
     *
     * @param nb DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Node getForkNode(NodeBranch nb) {
        int len = Math.max(getDepth(), nb.getDepth());

        for (int i = 0; i < len; i++) {
            if (!nodes.get(i).equals(nb.nodes.get(i))) {
                if (i == 0) {
                    return null;
                } else {
                    return (Node) nodes.get(i - 1);
                }
            }
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param nb DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getForkDepth(NodeBranch nb) {
        int len = Math.max(getDepth(), nb.getDepth());

        for (int i = 0; i < len; i++) {
            if (!nodes.get(i).equals(nb.nodes.get(i))) {
                if (i == 0) {
                    return -1;
                } else {
                    return i - 1;
                }
            }
        }

        return -1;
    }
}