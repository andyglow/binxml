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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.binxml.impl.CodeContext;
import org.binxml.impl.DocumentRestorer;
import org.binxml.impl.ICoderListener;
import org.binxml.impl.TraverseContext;
import org.binxml.util.XmlUtil;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Default DOM decoder implementation
 * @author Andrey Onistchuk
 * @since on 01.07.2004
 */
public class DOMDecoder extends AbstractDecoder implements IDOMDecoder {
    
    
    /**
     * 
     */
    DOMDecoder() {
    }

    /**
     * Decode input stream and construct an dom model
     *
     * @param is encoded data
     *
     * @return dom model
     *
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public Document decode(InputStream is) throws IOException, SAXException,
            ParserConfigurationException {

        fireStart(ICoderListener.BINXML_PARSE_WORK);
        CodeContext ctx = read(is);
        fireDone(ICoderListener.BINXML_PARSE_WORK, ctx);

        TraverseContext tctx = new TraverseContext();
        
        fireStart(ICoderListener.BINXML_STRUCT2XML_WORK);
        DocumentRestorer r = new DocumentRestorer(dict, struct, content, tctx); 
        Document doc = r.getDocument();
        fireDone(ICoderListener.BINXML_STRUCT2XML_WORK, tctx);
        
        return doc;
    }

    public void decode(OutputStream os, InputStream is) throws IOException, SAXException,
            ParserConfigurationException {
        Document doc = decode(is);
        fireStart(ICoderListener.JAXP_SERIALIZE_WORK);
        XmlUtil.serialize(doc, os);
        fireDone(ICoderListener.JAXP_SERIALIZE_WORK, null);
    }
}