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

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.binxml.CompressOptions;
import org.binxml.impl.model.IComposite;
import org.binxml.impl.model.extern.ContentExternalizer;
import org.binxml.impl.model.extern.DictionaryExternalizer;
import org.binxml.impl.model.extern.StructureExternalizer;
import org.binxml.util.Debug;

/**
 * @author andy
 * @creationDate on 19.07.2004
 */
public class Data {

    private IComposite dict;

    private IComposite struct;

    private IComposite content;

    private CompressOptions options;

    public Data(IComposite dict, IComposite struct, IComposite content, 
            CompressOptions options) {
        this.dict = dict;
        this.content = content;
        this.struct = struct;
        this.options = options;
    }

    public CodeContext write(OutputStream os) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);

        CodeContext ctx = new CodeContext(); 
        ctx.setDictionarySize(dict.size());
        ctx.setContentSize(content.size());
        ctx.setStructureSize(struct.size());
        
        ByteArrayOutputStream os1 = new ByteArrayOutputStream();
        write(os1, new DictionaryExternalizer(dict, ctx));
        os1.flush();

        ByteArrayOutputStream os2 = new ByteArrayOutputStream();
        write(os2, new StructureExternalizer(struct, ctx));
        os2.flush();

        ByteArrayOutputStream os3 = new ByteArrayOutputStream();
        write(os3, new ContentExternalizer(content, ctx));
        os3.flush();

        // write compression used info
        // write header info
        options.writeTo(dos);

        if (Debug.debug) {
            Debug.log.print("Compression: ");
            int cm = options.getMethod();
            String method = (cm == CompressOptions.BZIP_COMPRESSION_METHOD) ? "bzip2"
                    : (cm == CompressOptions.GZIP_COMPRESSION_METHOD) ? "gzip"
                            : "none";
            Debug.log.println(method);
        }

        //write dictionary length
        dos.writeInt(dict.size());
        //write structure length
        dos.writeInt(struct.size());

        if (Debug.debug) {
            Debug.log.println("Dictionary size: " + dict.size());
            Debug.log.println("Structure size: " + struct.size());
            Debug.log.println("Content size: " + content.size());
        }

        // write data
        OutputStream cos = options.configureStream(dos);
        os1.writeTo(cos);
        os2.writeTo(cos);
        os3.writeTo(cos);
        cos.flush();
        cos.close();
        dos.close();
        return ctx;
    }

    private void write(OutputStream os, IExternalizable bin) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);
        bin.writeTo(dos);
        dos.close();
        os.close();
    }

    public CodeContext read(InputStream is) throws IOException {
        CodeContext ctx = new CodeContext(); 

        // read compression used info
        options.readFrom(is);

        if (Debug.debug) {
            Debug.log.print("Compression: ");
            int cm = options.getMethod();
            String method = (cm == CompressOptions.BZIP_COMPRESSION_METHOD) ? "bzip2"
                    : (cm == CompressOptions.GZIP_COMPRESSION_METHOD) ? "gzip"
                            : "none";
            Debug.log.println(method);
        }

        long time = System.currentTimeMillis();

        DataInputStream dis = new DataInputStream(is);
        ctx.setDictionarySize( dis.readInt() );
        ctx.setStructureSize ( dis.readInt() );

        if (Debug.debug) {
            Debug.log.println("Dictionary size: "
                    + ctx.getDictionarySize());
            Debug.log.println("Structure size: " + ctx.getStructureSize());
        }

        InputStream cis = options.configureStream(dis);
        new DictionaryExternalizer(dict, ctx).readFrom(cis);
        new StructureExternalizer(struct, ctx).readFrom(cis );
        new ContentExternalizer(content, ctx).readFrom(cis);
        cis.close();
        
        ctx.setContentSize( content.size() );
        return ctx;
    }

}