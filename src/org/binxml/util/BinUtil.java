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
package org.binxml.util;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UTFDataFormatException;
import java.util.Collection;
import java.util.Iterator;

import org.binxml.impl.IExternalizable;

/**
 * @author andy
 *
 * @since on 02.07.2004
 */
public class BinUtil {

    public static void writeTo(OutputStream os, IExternalizable[] bins)
            throws IOException {
        for (int i = 0; i < bins.length; i++) {
            if (Debug.debug) {
                Debug.log.println("+ b: " + "[" + (i) + "]" + bins[i]);
            }
            bins[i].writeTo(os);
        }
    }

    public static void writeTo(OutputStream os, Collection bins)
            throws IOException {

        int i = 0;
        for (Iterator iter = bins.iterator(); iter.hasNext();) {
            Object b = iter.next();
            if (b instanceof IExternalizable) {
                IExternalizable bin = (IExternalizable) b;
                if (Debug.debug) {
                    Debug.log.println("+ b: " + "[" + (i++) + "]" + bin);
                }
                bin.writeTo(os);
            }
        }
    }

    /**
     * Reads from the
     * stream <code>in</code> a representation
     * of a Unicode  character string encoded in
     * Java modified UTF-8 format; this string
     * of characters  is then returned as a <code>String</code>.
     * The details of the modified UTF-8 representation
     * are  exactly the same as for the <code>readUTF</code>
     * method of <code>DataInput</code>.
     *
     * @param      in   a data input stream.
     * @return     a Unicode string.
     * @exception  EOFException            if the input stream reaches the end
     *               before all the bytes.
     * @exception  IOException             if an I/O error occurs.
     * @exception  UTFDataFormatException  if the bytes do not represent a
     *               valid Java modified UTF-8 encoding of a Unicode string.
     * @see        java.io.DataInputStream#readUnsignedShort()
     */
    public final static String readUTF(InputStream in) throws IOException {
        int utflen = in.read();
        StringBuffer str = new StringBuffer(utflen);
        byte bytearr[] = new byte[utflen];
        int c, char2, char3;
        int count = 0;

        readFully(in, bytearr, 0, utflen);

        while (count < utflen) {
            c = (int) bytearr[count] & 0xff;
            switch (c >> 4) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                /* 0xxxxxxx*/
                count++;
                str.append((char) c);
                break;
            case 12:
            case 13:
                /* 110x xxxx   10xx xxxx*/
                count += 2;
                if (count > utflen) throw new UTFDataFormatException();
                char2 = (int) bytearr[count - 1];
                if ((char2 & 0xC0) != 0x80) throw new UTFDataFormatException();
                str.append((char) (((c & 0x1F) << 6) | (char2 & 0x3F)));
                break;
            case 14:
                /* 1110 xxxx  10xx xxxx  10xx xxxx */
                count += 3;
                if (count > utflen) throw new UTFDataFormatException();
                char2 = (int) bytearr[count - 2];
                char3 = (int) bytearr[count - 1];
                if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80))
                        throw new UTFDataFormatException();
                str
                        .append((char) (((c & 0x0F) << 12)
                                | ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0)));
                break;
            default:
                /* 10xx xxxx,  1111 xxxx */
                throw new UTFDataFormatException();
            }
        }
        // The number of chars produced may be less than utflen
        return new String(str);
    }

    public static void writeUTF(OutputStream out, String str)
            throws IOException {
        int strlen = str.length();
        int utflen = 0;
        char[] charr = new char[strlen];
        int c, count = 0;

        str.getChars(0, strlen, charr, 0);

        for (int i = 0; i < strlen; i++) {
            c = charr[i];
            if ((c >= 0x0001) && (c <= 0x007F)) {
                utflen++;
            } else if (c > 0x07FF) {
                utflen += 3;
            } else {
                utflen += 2;
            }
        }

        if (utflen > 65535) throw new UTFDataFormatException();

        byte[] bytearr = new byte[utflen + 2];
        bytearr[count++] = (byte) ((utflen >>> 8) & 0xFF);
        bytearr[count++] = (byte) ((utflen >>> 0) & 0xFF);
        for (int i = 0; i < strlen; i++) {
            c = charr[i];
            if ((c >= 0x0001) && (c <= 0x007F)) {
                bytearr[count++] = (byte) c;
            } else if (c > 0x07FF) {
                bytearr[count++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
                bytearr[count++] = (byte) (0x80 | ((c >> 6) & 0x3F));
                bytearr[count++] = (byte) (0x80 | ((c >> 0) & 0x3F));
            } else {
                bytearr[count++] = (byte) (0xC0 | ((c >> 6) & 0x1F));
                bytearr[count++] = (byte) (0x80 | ((c >> 0) & 0x3F));
            }
        }
        out.write(bytearr);
    }

    public static final void readFully(InputStream in, byte b[], int off,
            int len) throws IOException {
        if (len < 0) throw new IndexOutOfBoundsException();
        int n = 0;
        while (n < len) {
            int count = in.read(b, off + n, len - n);
            if (count < 0) throw new EOFException();
            n += count;
        }
    }

}