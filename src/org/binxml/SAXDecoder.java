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

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.binxml.impl.Data;
import org.binxml.impl.CodeContext;
import org.binxml.impl.DocumentRestorer;
import org.binxml.impl.model.CompositeFactory;
import org.binxml.impl.model.IComposite;
import org.binxml.util.Debug;
import org.binxml.util.XmlUtil;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Default SAX decoder implementation
 * @author Andrey Onistchuk
 * @since on 01.07.2004
 */
public class SAXDecoder extends AbstractDecoder implements ISAXDecoder {

    /**
     * 
     */
    SAXDecoder() {
    }
    
    /* (non-Javadoc)
     * @see org.binxml.ISAXDecoder#decode(java.io.InputStream, org.xml.sax.helpers.DefaultHandler)
     */
    public void decode(InputStream is, DefaultHandler handler) throws IOException, SAXException, ParserConfigurationException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.binxml.IXMLDecoder#decode(java.io.OutputStream, java.io.InputStream)
     */
    public void decode(OutputStream os, InputStream is) throws IOException, SAXException, ParserConfigurationException {
        // TODO Auto-generated method stub
        
    }


}