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
package org.binxml;


/**
 * Coder factory
 * @author andy
 * @creationDate on 23.07.2004
 */
public class CoderFactory {

    public static IDOMDecoder createDOMDecoder() {
        return new DOMDecoder();
    }

    public static IDOMEncoder createDOMEncoder() {
        return new DOMEncoder();
    }
    
    public static ISAXEncoder createSAXEncoder() {
        return new SAXEncoder();
    }

    public static ISAXDecoder createSAXDecoder() {
        return new SAXDecoder();
    }

}
