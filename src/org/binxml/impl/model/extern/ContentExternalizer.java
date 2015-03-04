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
package org.binxml.impl.model.extern;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.binxml.impl.CodeContext;
import org.binxml.impl.model.IComposite;
import org.binxml.impl.model.Value;
import org.binxml.util.Debug;

/**
 * @author andy
 *
 * @since on 22.06.2004
 */
public class ContentExternalizer extends AbstractExternalizer  {

    /**
     * @param comp
     */
    public ContentExternalizer(IComposite comp, CodeContext ctx) {
        super(comp, ctx);
    }

    /*
     * (non-Javadoc)
     *
     * @see javaz.xml.compressor.impl.IBinaryExternalizable#writeTo(java.io.OutputStream)
     */
    public void writeTo(OutputStream os) throws IOException {
        if (Debug.debug) {
            Debug.log.println("! write content {");
        }

        DataOutputStream dos = new DataOutputStream(os);
        for (int i = 0; i < composite.size(); i++) {
            String text = ((Value) composite.get(i)).getValue();
            writeTo(dos, text);
            if (Debug.debug) {
                Debug.log.println("+ " + text);
            }
        }
        if (Debug.debug) {
            Debug.log.println("! }");
        }
        dos.flush();
        dos.close();
    }

    /*
     * (non-Javadoc)
     *
     * @see javaz.xml.compressor.impl.IBinaryExternalizable#readFrom(java.io.InputStream)
     */
    public void readFrom(InputStream is) throws IOException {
        if (Debug.debug) {
            Debug.log.println("! read content {");
        }
        DataInputStream dis = new DataInputStream(is);
        // StringBuffer sb = new StringBuffer();

        int i = 0;
        do {
            // int c = dis.readUTF();
            try {

                String text = dis.readUTF();

                if (Debug.debug) {
                    Debug.log.println("+ " + (i++) + " " + text);
                }

                composite.put(new Value(text));
            } catch (EOFException ex) {
                break;
            }

            // if (c < 0) {
            //    break;
            //}

            //if (c == 0x07) {
            //    put(sb.toString());
            //    sb.setLength(0); // clean, FIXME if it's wrong way to reset

            // string buffer
            //    continue;
            // }

            // sb.append((char) c);
        } while (true);

        // put(sb.toString());
        dis.close();

        if (Debug.debug) {
            Debug.log.println("! }");
        }

    }

    private void writeTo(DataOutputStream os, String text) throws IOException {
        if (text != null) {
            //            for (int i = 0; i < text.length(); i++) {
            //                os.write(text.charAt(i));
            //            }
            os.writeUTF(text);
        }
    }
}