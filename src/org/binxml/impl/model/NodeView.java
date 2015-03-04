package org.binxml.impl.model;

import org.binxml.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @author andy
 * @creationDate on 19.07.2004
 */
public class NodeView {

    private short nodeType;

    private String nodeName;

    private Document ownerDocument;

    private String content;

    private NodeView parent;

    private NodeView previousSibling;

    public NodeView(short type, String name) {
        this.nodeType = type;
        this.nodeName = name;
    }

    public NodeView(Node node) {
        this.nodeType = node.getNodeType();
        this.nodeName = node.getNodeName();
        this.ownerDocument = node.getOwnerDocument();
        this.content = XmlUtil.contentOf(node);
        //        if(node.getParentNode()!=null)
        //            this.parent = new NodeView(node.getParentNode());
        //        if(node.getPreviousSibling()!=null)
        //            this.previousSibling = new NodeView(node.getPreviousSibling());
    }

    public NodeView getParentNode() {
        return parent;
    }

    public void setParentNode(NodeView parent) {
        this.parent = parent;
    }

    public NodeView getPreviousSibling() {
        return previousSibling;
    }

    public void setPreviousSibling(NodeView previousSibling) {
        this.previousSibling = previousSibling;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public short getNodeType() {
        return nodeType;
    }

    public void setNodeType(short nodeType) {
        this.nodeType = nodeType;
    }

    public Document getOwnerDocument() {
        return ownerDocument;
    }

    public void setOwnerDocument(Document ownerDocument) {
        this.ownerDocument = ownerDocument;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
        return nodeType + "[" + nodeName + "]{"+content+"}#"+hashCode();
    }

    public boolean equals(Object otherObject) {
        if (otherObject == this) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        NodeView other = (NodeView) otherObject;
        return ((nodeType == other.nodeType)
                && (nodeName == other.nodeName || (nodeName != null && nodeName
                        .equals(other.nodeName)))
                && (ownerDocument == other.ownerDocument || (ownerDocument != null && ownerDocument
                        .equals(other.ownerDocument))) && (content == other.content || (content != null && content
                .equals(other.content))));
    }

    public int hashCode() {
        int hashCode = 37;
        hashCode = hashCode * 17 + (int) nodeType;
        hashCode = hashCode * ((nodeName == null) ? 17 : nodeName.hashCode());
        hashCode = hashCode
                * ((ownerDocument == null) ? 17 : ownerDocument.hashCode());
        hashCode = hashCode * ((content == null) ? 17 : content.hashCode());
        return hashCode;
    }

}