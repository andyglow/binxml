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

import org.binxml.impl.model.DOMNodeView;
import org.binxml.impl.model.IComposite;
import org.binxml.impl.model.NodeRef;
import org.binxml.impl.model.NodeView;
import org.binxml.impl.model.Tag;
import org.binxml.impl.model.Value;
import org.binxml.util.NodeBranch;
import org.binxml.util.XmlUtil;
import org.binxml.util.traverse.INodeVisitor;
import org.w3c.dom.Node;

/**
 * @author andy
 *
 * @since on 22.06.2004
 */
public class StructBuilder implements INodeVisitor {

    private IComposite dict;

    private IComposite struct;

    private IComposite content;

    private NodeView prev = null;

    /**
     * Creates a new StructBuilder object.
     *
     * @param dict DOCUMENT ME!
     * @param struct DOCUMENT ME!
     * @param content DOCUMENT ME!
     */
    public StructBuilder(IComposite dict, IComposite struct, IComposite content) {
        this.dict = dict;
        this.struct = struct;
        this.content = content;
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     */
    public void visit(NodeView node) {
        int rel = computeRelation(prev, node);
        
        NodeRef info = new NodeRef(dict.indexOf(new Tag(node)), rel, content
                .indexOf(new Value(node.getContent())));
        
        struct.put(info);
        prev = node;
    }

    private int computeRelation(NodeView prev, NodeView curr) {

        if ((prev == null) || (curr == null) || curr.getParentNode() == null
                || (prev.getNodeType() == Node.DOCUMENT_NODE)
                || prev.equals(curr.getOwnerDocument().getDocumentElement())) {
            return INodeRelationsheep.CHILD;
        } else if (curr.getNodeType() == Node.ATTRIBUTE_NODE) {

            if (curr.getParentNode().hashCode() == prev.hashCode()) {
                return INodeRelationsheep.CHILD;
            } else {
                return INodeRelationsheep.SIBLING;
            }
        } else if (curr.getParentNode().equals(prev)) {
            return INodeRelationsheep.CHILD;
        } else if ((curr.getPreviousSibling() != null)
                && curr.getPreviousSibling().equals(prev)) {
            return INodeRelationsheep.SIBLING;
        } else {
            // we will determine the count of steps to generally parent
            int appendix = 0;

            if ((curr.getNodeType() != Node.ATTRIBUTE_NODE)
                    && (prev.getNodeType() != Node.ATTRIBUTE_NODE)
                    && !prev.getParentNode().equals(curr.getParentNode())) {
                // let's find parent of both
                // hire we know that the nodes are not siblings because the parents are different
                // so let's builld the branches for two our nodes
                // so, parent are found

                if (prev instanceof DOMNodeView && curr instanceof DOMNodeView) {
                    NodeBranch pnb = NodeBranch.buildFor((DOMNodeView) prev);
                    NodeBranch nnb = NodeBranch.buildFor((DOMNodeView) curr);

                    appendix = pnb.getDepth() - nnb.getDepth();
                }
                // TODO: compute depth in SAX mode

            }

            return INodeRelationsheep.SIBLING_OF_PARENT + appendix;
        }
    }
}