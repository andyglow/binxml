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

import org.binxml.impl.CodeContext;
import org.binxml.impl.Data;
import org.binxml.impl.model.CompositeFactory;
import org.binxml.impl.model.IComposite;
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
 * Abstract encoder. An options holder.
 * @author Andrey Onistchuk
 * @since on 22.06.2004
 */
abstract class AbstractEncoder extends AbstractCoder implements IXMLEncoder {

    protected IComposite dict = CompositeFactory.createSync();

    protected IComposite struct = CompositeFactory.createSync();

    protected IComposite content = CompositeFactory.createSync();

    private CompressOptions options = new CompressOptions();

    /**
     * @return compression options
     */
    public CompressOptions getOptions() {
        return options;
    }

    protected CodeContext write(OutputStream os) throws IOException {
        Data ff = new Data(dict, struct, content, getOptions());
        return ff.write(os);
    }
}