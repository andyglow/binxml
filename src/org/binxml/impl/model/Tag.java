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
package org.binxml.impl.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.binxml.impl.IExternalizable;
import org.w3c.dom.Node;


/**
 * @author andy
 *
 * @since on 22.06.2004
 */
public class Tag implements IExternalizable {
    private String name;
    private short type;

    /**
     * Creates a new Tag object.
     *
     * @param node DOCUMENT ME!
     */
    public Tag(NodeView node) {
        this(node.getNodeType(),
            (((node.getNodeType() == Node.ATTRIBUTE_NODE) ||
            (node.getNodeType() == Node.ELEMENT_NODE) ||
            (node.getNodeType() == Node.PROCESSING_INSTRUCTION_NODE)) ||
            (node.getNodeType() == Node.ENTITY_REFERENCE_NODE))
            ? node.getNodeName() : null);
    }

    /**
     * @param type
     */
    public Tag(short type) {
        this(type, null);
    }

    public Tag() {
    }

    /**
     * @param type
     * @param name
     */
    public Tag(short type, String name) {
        super();
        this.type = type;
        this.name = name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getName() {
        return name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public short getType() {
        return type;
    }

    /**
     * DOCUMENT ME!
     *
     * @param otherObject DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean equals(Object otherObject) {
        if (otherObject == this) {
            return true;
        }

        if (otherObject == null) {
            return false;
        }

        if (getClass() != otherObject.getClass()) {
            return false;
        }

        Tag other = (Tag) otherObject;

        return ((type == other.type) &&
        ((name == other.name) || ((name != null) && name.equals(other.name))));
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int hashCode() {
        return (type * 17) + ((name == null) ? 37 : name.hashCode());
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        return "" + type + ((name == null) ? "" : ("[" + name + "]"));
    }

    // --- extern
    public void writeTo(OutputStream os) throws IOException {
        os.write(type);

        if (name != null) {
            os.write(name.length()); // TODO suppose, node names never been

            // greather then 255
            for (int i = 0; i < name.length(); i++)
                os.write(name.charAt(i));
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param is DOCUMENT ME!
     *
     * @throws IOException DOCUMENT ME!
     */
    public void readFrom(InputStream is) throws IOException {
        type = (short) is.read(); // read 1 byte

        if ((type == Node.ATTRIBUTE_NODE) || (type == Node.ELEMENT_NODE) ||
                (type == Node.ENTITY_REFERENCE_NODE) ||
                (type == Node.PROCESSING_INSTRUCTION_NODE)) {
            int len = is.read();
            byte[] buffer = new byte[len];
            is.read(buffer);
            name = new String(buffer);
        }
    }

    /**
     * @deprecated
     */
    public int sizeOf() {
        return 1 + ((name != null) ? name.length() : 0);
    }
}
