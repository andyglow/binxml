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

import org.w3c.dom.Document;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

/**
 * DOM Decoder
 * @author Andrey Onistchuk
 * @since on 22.06.2004
 */
public interface ISAXDecoder extends IXMLDecoder {

    /**
     * Decode encoded data usinx SAX2 DefaultHandler helper
     *
     * @param is encoded data
     *
     * @return dom model
     *
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public void decode(InputStream is, DefaultHandler handler) throws IOException, SAXException,
            ParserConfigurationException;
}