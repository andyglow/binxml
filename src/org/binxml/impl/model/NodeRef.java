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
import java.io.PushbackInputStream;

import org.binxml.impl.IExternalizable;
import org.binxml.util.BitSet;
import org.binxml.util.io.LittleEndianDataInputStream;
import org.binxml.util.io.LittleEndianDataOutputStream;


/**
 * @author andy
 *
 * @since on 30.06.2004
 */
public class NodeRef implements IExternalizable {
    private int kind;
    private int relation;
    private int offset = -1;

    public NodeRef() {
    }

    /**
     * Creates a new NodeRef object.
     *
     * @param kindIndex DOCUMENT ME!
     * @param relationToPrevious DOCUMENT ME!
     * @param dataOffset DOCUMENT ME!
     */
    public NodeRef(int kindIndex, int relationToPrevious, int dataOffset) {
        super();
        this.kind = kindIndex;
        this.relation = relationToPrevious;
        this.offset = dataOffset;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getTagIndex() {
        return kind;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getContentIndex() {
        return offset;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getRelation() {
        return relation;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        return kind + ":" + relation + ":" + offset;
    }

    /*
     * (non-Javadoc)
     *
     * @see javaz.xml.compressor.algo.Binary#writeTo(java.io.OutputStream)
     */
    public void writeTo(OutputStream os) throws IOException {
        BitSet data = new BitSet();

        if (offset < 0) {
            data.clear(0);
        } else {
            data.set(0);
            data.set(offset, 16, 16);
        }

        data.set(kind, 1, 8);
        data.set(relation, 9, 7);

        LittleEndianDataOutputStream dos = new LittleEndianDataOutputStream(os);

        if (offset >= 0) {
            dos.writeInt((int) data.getPossibleLong(0, 32));
        } else {
            dos.writeShort((short) data.getPossibleLong(0, 16));
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see javaz.xml.compressor.algo.Binary#readFrom(java.io.InputStream)
     */
    public void readFrom(InputStream is) throws IOException {
        PushbackInputStream pbis = new PushbackInputStream(is);
        LittleEndianDataInputStream dis = new LittleEndianDataInputStream(pbis);
        int b1 = pbis.read();
        pbis.unread(b1);

        BitSet data = new BitSet();

        if ((b1 & 1) != 1) {
            // first bit set 
            short b = dis.readShort();
            data.set(b, 0, 16);
        } else {
            int b = dis.readInt();
            data.set(b, 0, 32);
        }

        if (data.get(0)) {
            offset = (int) data.getPossibleLong(16, 16);
        }

        kind = (int) data.getPossibleLong(1, 8);
        relation = (int) data.getPossibleLong(9, 7);

//        System.out.println("<<" + this + " " + data.toBinaryString() + " " +
//            ((offset <= 0) ? 2 : 4) + " " + data.getPossibleLong(0, 32));
    }

    /**
     * @deprecated
     */
    public int sizeOf() {
        return (offset != 0) ? 16 : 32;
    }
}
