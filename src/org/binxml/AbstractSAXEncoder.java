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

import org.binxml.util.XmlUtil;

import org.w3c.dom.Document;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Abstract SAX encoder. An utilitary method wrapper.
 * @author Andrey Onistchuk
 * @since on 22.06.2004
 */
public abstract class AbstractSAXEncoder extends AbstractEncoder implements ISAXEncoder {

    /*
     * (non-Javadoc)
     *
     * @see javaz.xml.compressor.IXMLEncoder#encode(byte[])
     */
    public void encode(OutputStream os, byte[] data) throws IOException,
            SAXException, ParserConfigurationException {
        encode(os, new InputSource(new ByteArrayInputStream(data)));
    }

    /*
     * (non-Javadoc)
     *
     * @see javaz.xml.compressor.IXMLEncoder#encode(java.io.InputStream)
     */
    public void encode(OutputStream os, InputStream data) throws IOException,
            SAXException, ParserConfigurationException {
        encode(os, new InputSource(data));
    }

    /*
     * (non-Javadoc)
     *
     * @see javaz.xml.compressor.IXMLEncoder#encode(java.lang.String)
     */
    public void encode(OutputStream os, String data) throws IOException,
            SAXException, ParserConfigurationException {
        encode(os, new InputSource(new StringReader(data)));
    }
    
}