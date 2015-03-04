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

import org.binxml.impl.model.composite.ArrayListComposite;
import org.binxml.impl.model.composite.HashmapComposite;
import org.binxml.impl.model.composite.VectorComposite;


/**
 * Composite factory
 * @author andy
 * @creationDate on 20.07.2004
 */
public class CompositeFactory {

    private static Class sync = HashmapComposite.class;
    private static Class async = HashmapComposite.class;
    
    public static void setSyncClass(Class clazz) {
        sync = clazz;
    }
    
    public static void setASyncClass(Class clazz) {
        async = clazz;
    }
    
    public static IComposite createASync() {
        return create(async); 
    }

    public static IComposite createSync() {
        return create(sync); 
    }
    
    private static IComposite create(Class clazz) {
        IComposite c = null;
        try {
            c = (IComposite)clazz.newInstance();
        } catch (InstantiationException e) {
            ;
        } catch (IllegalAccessException e) {
            ;
        }
        return c;
    }
    
}
