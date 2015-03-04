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
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.binxml.impl.IExternalizable;


/**
 * Compression options
 * @author Andrey Onistchuk
 * @since on 02.07.2004
 */
public class CompressOptions implements IExternalizable {
    
    private static final String ANT_NOT_AVAILABLE_MESSAGE = "BZIP compression method not available. org.apache.tools.bzip2.CBZip2InputStream not found or can't be instantiated";
    
    /** No compression method */
    public final static int NO_COMPRESSION_METHOD = 0;

    /** GZIP compression method */
    public final static int GZIP_COMPRESSION_METHOD = 1;

    /** BZIP2 compression method */
    public final static int BZIP_COMPRESSION_METHOD = 2;
    
    /* default is NO */
    private int cmethod = NO_COMPRESSION_METHOD;

    CompressOptions() {
    }

    /**
     * @return compression method index
     */
    public int getMethod() {
        return cmethod;
    }

    /**
     * @param setup compression method index
     */
    public void setMethod(int cmethod) {
        this.cmethod = cmethod;
    }

    /* (non-Javadoc)
     * @see javaz.xml.compressor.impl.IBinaryExternalizable#writeTo(java.io.OutputStream)
     */
    public void writeTo(OutputStream os) throws IOException {
        os.write(cmethod);
    }

    /* (non-Javadoc)
     * @see javaz.xml.compressor.impl.IBinaryExternalizable#readFrom(java.io.InputStream)
     */
    public void readFrom(InputStream is) throws IOException {
        setMethod(is.read());
    }

    // package
    public InputStream configureStream(InputStream is) throws IOException {
        InputStream newIs = is;

        if (getMethod() == CompressOptions.GZIP_COMPRESSION_METHOD) {
            newIs = new GZIPInputStream(is);
        } else if (getMethod() == CompressOptions.BZIP_COMPRESSION_METHOD) {
            try {
                newIs = (InputStream)createWrapper(is, InputStream.class, "org.apache.tools.bzip2.CBZip2InputStream");
            } catch (Throwable e) {
                IOException ex = new IOException(ANT_NOT_AVAILABLE_MESSAGE);
                ex.initCause(e);
                throw ex;
            }
        }

        System.out.println("IS: "+newIs.getClass());
        return newIs;
    }

    // package
    public OutputStream configureStream(OutputStream os) throws IOException {
        OutputStream newOs = os;

        if (getMethod() == CompressOptions.GZIP_COMPRESSION_METHOD) {
            newOs = new GZIPOutputStream(os);
        } else if (getMethod() == CompressOptions.BZIP_COMPRESSION_METHOD) {
            try {
                newOs = (OutputStream)createWrapper(os, OutputStream.class, "org.apache.tools.bzip2.CBZip2OutputStream");
            } catch (Throwable e) {
                IOException ex = new IOException(ANT_NOT_AVAILABLE_MESSAGE);
                ex.initCause(e);
                throw ex;
            }
        }

        System.out.println("OS: "+newOs.getClass());
        
        return newOs;
    }
    
    private Object createWrapper(Object s, Class arg, String className) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        Class clazz = Class.forName(className);
        Constructor c = clazz.getConstructor(new Class[] {arg});
        return c.newInstance(new Object[] {s});
    }
}
