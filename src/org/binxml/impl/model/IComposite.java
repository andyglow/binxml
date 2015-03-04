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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import org.binxml.impl.IExternalizable;
import org.binxml.impl.model.NodeView;
import org.binxml.util.ArrayUtil;
import org.binxml.util.Debug;


/**
 * Composite interface is some kind of collection with indexOf implementation
 * @author andy
 *
 * @since on 22.06.2004
 */
public interface IComposite {

    /**
     * Return index of object
     * @return index of found object or -1 if any
     */
    public int indexOf(IExternalizable obj);

    /**
     * @return DOCUMENT ME!
     */
    public IExternalizable get(int i);
    /**
     * @param value DOCUMENT ME!
     */
    public void put(IExternalizable obj);    
    public Collection getAll();
    
    public int size();
    
}
