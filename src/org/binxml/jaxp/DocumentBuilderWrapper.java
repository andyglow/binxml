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
package org.binxml.jaxp;

import org.binxml.CoderFactory;
import org.binxml.DOMDecoder;
import org.binxml.IDOMDecoder;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;


/**
 * @author andy
 *
 * @since on 19.07.2004
 */
public class DocumentBuilderWrapper extends DocumentBuilder {
    private DocumentBuilder db = null;

    /**
     * Creates a new DocumentBuilderWrapper object.
     *
     * @param db DOCUMENT ME!
     */
    public DocumentBuilderWrapper(DocumentBuilder db) {
        this.db = db;
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.xml.parsers.DocumentBuilder#isNamespaceAware()
     */
    public boolean isNamespaceAware() {
        return db.isNamespaceAware();
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.xml.parsers.DocumentBuilder#isValidating()
     */
    public boolean isValidating() {
        return db.isValidating();
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.xml.parsers.DocumentBuilder#getDOMImplementation()
     */
    public DOMImplementation getDOMImplementation() {
        return db.getDOMImplementation();
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.xml.parsers.DocumentBuilder#newDocument()
     */
    public Document newDocument() {
        return db.newDocument();
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.xml.parsers.DocumentBuilder#setEntityResolver(org.xml.sax.EntityResolver)
     */
    public void setEntityResolver(EntityResolver er) {
        db.setEntityResolver(er);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.xml.parsers.DocumentBuilder#setErrorHandler(org.xml.sax.ErrorHandler)
     */
    public void setErrorHandler(ErrorHandler eh) {
        db.setErrorHandler(eh);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.xml.parsers.DocumentBuilder#parse(org.xml.sax.InputSource)
     */
    public Document parse(InputSource is) throws SAXException, IOException {
        IDOMDecoder dd = CoderFactory.createDOMDecoder();

        if (is.getByteStream() != null) {
            try {
                return dd.decode(is.getByteStream());
            } catch (ParserConfigurationException pce) {
                // will never thrown
            }
        } else if (is.getSystemId() != null) {
            try {
                return dd.decode(new FileInputStream(is.getSystemId()));
            } catch (ParserConfigurationException pce) {
                // will never thrown
            }
        }

        throw new SAXException(
            "No valid input source was defined. Use setSystemId() or setByteStream() methods.");
    }
}
