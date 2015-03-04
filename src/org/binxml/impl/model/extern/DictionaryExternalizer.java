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
package org.binxml.impl.model.extern;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.binxml.impl.CodeContext;
import org.binxml.impl.model.IComposite;
import org.binxml.impl.model.Tag;
import org.binxml.util.BinUtil;
import org.binxml.util.Debug;

/**
 * @author andy
 *
 * @since on 22.06.2004
 */
public class DictionaryExternalizer extends AbstractExternalizer {

    /**
     * @param comp
     */
    public DictionaryExternalizer(IComposite comp, CodeContext ctx) {
        super(comp, ctx);
    }

    /* (non-Javadoc)
     * @see javaz.xml.compressor.impl.IBinaryExternalizable#writeTo(java.io.OutputStream)
     */
    public void writeTo(OutputStream os) throws IOException {
        if (Debug.debug) {
            Debug.log.println("! write dictionary {");
        }
        BinUtil.writeTo(os, composite.getAll());
        if (Debug.debug) {
            Debug.log.println("! }");
        }
    }

    /* (non-Javadoc)
     * @see javaz.xml.compressor.impl.IBinaryExternalizable#readFrom(java.io.InputStream)
     */
    public void readFrom(InputStream is) throws IOException {
        int tagReads = 0;
        int i = 0;

        if (Debug.debug) {
            Debug.log.println("! read dictionary {");
        }

        while (tagReads < context.getDictionarySize()) {
            Tag tag = new Tag();
            tag.readFrom(is);
            composite.put(tag);
            if (Debug.debug) {
                Debug.log.println("+ tag: " + "[" + (composite.size() - 1) + "]"
                        + tag);
            }
            tagReads++;
        }

        if (Debug.debug) {
            Debug.log.println("! }");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     * @deprecated
     */
    public int sizeOf() {
        int size = 0;

        for (int i = 0; i < composite.size(); i++)
            size += ((Tag)composite.get(i)).sizeOf();

        return size;
    }

}