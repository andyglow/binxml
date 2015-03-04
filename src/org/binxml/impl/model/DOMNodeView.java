package org.binxml.impl.model;

import org.w3c.dom.Node;


/**
 * @author andy
 * @creationDate on 20.07.2004
 */
public class DOMNodeView extends NodeView {

    private transient Node node;
    
    public DOMNodeView(Node node) {
        super(node);
        this.node = node;
    }
    
    public Node getUnderlying() {
        return node;
    }
    
}
