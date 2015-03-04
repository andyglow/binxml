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

import org.w3c.dom.Node;

/**
 * @author andy
 *
 * @since on 02.07.2004
 */
public class TraverseContext {
    
    public int getAttrCount() {
        return attrCount;
    }
    public void setAttrCount(int attrCount) {
        this.attrCount = attrCount;
    }
    public int getCdataCount() {
        return cdataCount;
    }
    public void setCdataCount(int cdataCount) {
        this.cdataCount = cdataCount;
    }
    public int getElementCount() {
        return elementCount;
    }
    public void setElementCount(int elementCount) {
        this.elementCount = elementCount;
    }
    public int getEntityrefCount() {
        return entityrefCount;
    }
    public void setEntityrefCount(int entityrefCount) {
        this.entityrefCount = entityrefCount;
    }
    public int getNodeCount() {
        return nodeCount;
    }
    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }
    public int getPiCount() {
        return piCount;
    }
    public void setPiCount(int piCount) {
        this.piCount = piCount;
    }
    public int getTextCount() {
        return textCount;
    }
    public void setTextCount(int textCount) {
        this.textCount = textCount;
    }
    private int nodeCount = 0;
    private int elementCount = 0;
    private int attrCount = 0;
    private int textCount = 0;
    private int cdataCount = 0;
    private int entityrefCount = 0;
    private int piCount = 0;
    private int commentCount = 0;
    
    public String toString() {
        StringBuffer sb = new StringBuffer("<code>");
        sb.append("WHOLE NODES: "+nodeCount).append("<blockquote><code>");
        sb.append("ELEMENTS: "+elementCount).append("<br/>");
        sb.append("ATTRIBUTES: "+attrCount).append("<br/>");
        sb.append("TEXT NODES: "+textCount).append("<br/>");
        sb.append("CDATA NODES: "+cdataCount).append("<br/>");
        sb.append("ENTITY REFERENCES: "+entityrefCount).append("<br/>");
        sb.append("PROCESSING INSTRUCTIONS: "+piCount).append("<br/>");
        sb.append("COMMENTS: "+commentCount);
        return sb.append("</code></blockquote></code>").toString();
    }
    
    public void inc(short nodeType) {
        nodeCount++;
        switch(nodeType) {
        	case(Node.ELEMENT_NODE): elementCount++; break;
        	case(Node.TEXT_NODE): textCount++; break;
        	case(Node.CDATA_SECTION_NODE): cdataCount++; break;
        	case(Node.COMMENT_NODE): commentCount++; break;
        	case(Node.ATTRIBUTE_NODE): attrCount++; break;
        	case(Node.ENTITY_NODE): entityrefCount++; break;
        	case(Node.ENTITY_REFERENCE_NODE): entityrefCount++; break; // FIXME
        	case(Node.PROCESSING_INSTRUCTION_NODE): piCount++; break;
        }
    }
}
