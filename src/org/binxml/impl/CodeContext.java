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

/**
 * @author andy
 *
 * @since on 02.07.2004
 */
public class CodeContext {
    /** DOCUMENT ME! */
    private int dictionarySize = 0;

    /** DOCUMENT ME! */
    private int structureSize = 0;

    /** DOCUMENT ME! */
    private int contentSize = 0;
    public int getContentSize() {
        return contentSize;
    }
    public void setContentSize(int contentSize) {
        this.contentSize = contentSize;
    }
    public int getDictionarySize() {
        return dictionarySize;
    }
    public void setDictionarySize(int dictionarySize) {
        this.dictionarySize = dictionarySize;
    }
    public int getStructureSize() {
        return structureSize;
    }
    public void setStructureSize(int structureSize) {
        this.structureSize = structureSize;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer("<code>");
        sb.append("TAGS: "+dictionarySize).append("<br/>");
        sb.append("NODES: "+structureSize).append("<br/>");
        sb.append("TOKENS: "+contentSize);
        return sb.append("</code>").toString();
    }
}
