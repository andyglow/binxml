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
package org.binxml.impl.model.composite;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

import org.apache.commons.collections.FastArrayList;
import org.binxml.impl.IExternalizable;
import org.binxml.impl.model.IComposite;
import org.binxml.impl.model.NodeView;
import org.binxml.util.ArrayUtil;
import org.binxml.util.Debug;


/**
 * @author andy
 *
 * @since on 22.06.2004
 */
public class VectorComposite implements IComposite {

    private Vector data = new Vector();

    /**
     * Return the index of node if found, will return -1 if not found
     *
     * @param node DOM Node
     *
     * @return index
     */
    public int indexOf(IExternalizable obj) {
        return data.indexOf(obj);
    }

    /**
     * DOCUMENT ME!
     *
     * @param i DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public IExternalizable get(int i) {
        return (IExternalizable)data.get(i);
    }

    /**
     * DOCUMENT ME!
     *
     * @param value DOCUMENT ME!
     */
    public void put(IExternalizable obj) {
        if (obj == null)
            return;

        if (!data.contains(obj)) {
            data.add(obj);
        }
    }
    
    public Collection getAll() {
        return data;
    }
    
    public int size() {
        return data.size();
    }
}
