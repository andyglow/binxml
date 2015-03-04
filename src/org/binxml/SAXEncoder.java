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
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.binxml.impl.CodeContext;
import org.binxml.impl.ICoderListener;
import org.binxml.impl.TraverseContext;
import org.binxml.sax.SAXEncodingHandler;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * SAX Encoder
 * @author andy
 * @creationDate on 19.07.2004
 */
public class SAXEncoder extends AbstractSAXEncoder {

    /**
     * 
     */
    SAXEncoder() {
    }    
    
    private SAXParserFactory spf = null;
    {
        spf = SAXParserFactory.newInstance();
        spf.isValidating();
    }

    public void encode(OutputStream os, InputSource is) throws IOException,
            SAXException, ParserConfigurationException {
        
        fireStart(ICoderListener.BINXML_XML2STRUCT_WORK);
        SAXParser parser = spf.newSAXParser();

        TraverseContext tctx = new TraverseContext();
        SAXEncodingHandler h = new SAXEncodingHandler(tctx);
        parser.setProperty("http://xml.org/sax/properties/lexical-handler", h);
        parser.parse(is, h);
        fireDone(ICoderListener.BINXML_XML2STRUCT_WORK, tctx);
        
        fireStart(ICoderListener.BINXML_SERIALIZE_WORK);
        CodeContext ctx = h.dump(os, getOptions());
        fireDone(ICoderListener.BINXML_SERIALIZE_WORK, ctx);
    }

}