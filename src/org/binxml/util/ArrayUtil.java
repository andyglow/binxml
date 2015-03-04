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

import java.util.Comparator;


/**
 * @author andy
 */
public class ArrayUtil {
    // ----------------------------------------------------------------
    public static boolean contains(Object[] array, Object key) {
        return indexOf(array, key) > -1;
    }

    /**
     * DOCUMENT ME!
     *
     * @param array DOCUMENT ME!
     * @param key DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean contains(char[] array, char key) {
        return indexOf(array, key) > -1;
    }

    /**
     * DOCUMENT ME!
     *
     * @param array DOCUMENT ME!
     * @param key DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int indexOf(char[] array, char key) {
        for (int i = 0; i < array.length; i++) {
            if (key == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * DOCUMENT ME!
     *
     * @param array DOCUMENT ME!
     * @param key DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int indexOf(Object[] array, Object key) {
        if (key == null) {
            return -1;
        }

        for (int i = 0; i < array.length; i++) {
            if (key.equals(array[i])) {
                return i;
            }
        }

        return -1;
    }

    /**
     * DOCUMENT ME!
     *
     * @param array DOCUMENT ME!
     * @param key DOCUMENT ME!
     * @param comp DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int indexOf(Object[] array, Object key, Comparator comp) {
        for (int i = 0; i < array.length; i++) {
            if (comp.compare(array[i], key) == 0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * DOCUMENT ME!
     *
     * @param array DOCUMENT ME!
     * @param o DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Object[] push(Object[] array, Object o) {
        Object[] temp = (Object[]) java.lang.reflect.Array.newInstance(array.getClass()
                                                                            .getComponentType(),
                array.length + 1);
        System.arraycopy(array, 0, temp, 0, array.length);
        temp[array.length] = o;

        return temp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param array DOCUMENT ME!
     * @param o DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static char[] push(char[] array, char o) {
        char[] temp = (char[]) java.lang.reflect.Array.newInstance(array.getClass()
                                                                        .getComponentType(),
                array.length + 1);
        System.arraycopy(array, 0, temp, 0, array.length);
        temp[array.length] = o;

        return temp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param array DOCUMENT ME!
     * @param o DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean[] push(boolean[] array, boolean o) {
        boolean[] temp = (boolean[]) java.lang.reflect.Array.newInstance(array.getClass()
                                                                              .getComponentType(),
                array.length + 1);
        System.arraycopy(array, 0, temp, 0, array.length);
        temp[array.length] = o;

        return temp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param array DOCUMENT ME!
     * @param o DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int[] push(int[] array, int o) {
        int[] temp = (int[]) java.lang.reflect.Array.newInstance(array.getClass()
                                                                      .getComponentType(),
                array.length + 1);
        System.arraycopy(array, 0, temp, 0, array.length);
        temp[array.length] = o;

        return temp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param array DOCUMENT ME!
     * @param o DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static byte[] push(byte[] array, byte o) {
        byte[] temp = (byte[]) java.lang.reflect.Array.newInstance(array.getClass()
                                                                        .getComponentType(),
                array.length + 1);
        System.arraycopy(array, 0, temp, 0, array.length);
        temp[array.length] = o;

        return temp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param array DOCUMENT ME!
     * @param o DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Object[] remove(Object[] array, Object o) {
        int index = indexOf(array, o);

        if (index < 0) {
            return (Object[]) java.lang.reflect.Array.newInstance(array.getClass()
                                                                       .getComponentType(),
                0);
        }

        Object[] temp = (Object[]) java.lang.reflect.Array.newInstance(array.getClass()
                                                                            .getComponentType(),
                array.length - 1);
        System.arraycopy(array, 0, temp, 0, index); // left
        System.arraycopy(array, index + 1, temp, index, array.length - index -
            1);

        // left
        return temp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param array DOCUMENT ME!
     * @param index DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Object[] remove(Object[] array, int index) {
        if (index < 0) {
            return null;
        }

        Object[] temp = (Object[]) java.lang.reflect.Array.newInstance(array.getClass()
                                                                            .getComponentType(),
                array.length - 1);
        System.arraycopy(array, 0, temp, 0, index); // left
        System.arraycopy(array, index + 1, temp, index, array.length - index -
            1);

        // left
        return temp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param array DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Object[] copy(Object[] array) {
        Object[] temp = (Object[]) java.lang.reflect.Array.newInstance(array.getClass()
                                                                            .getComponentType(),
                array.length);
        System.arraycopy(array, 0, temp, 0, array.length); // left

        return temp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param array DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static char[] copy(char[] array) {
        char[] temp = new char[array.length];
        System.arraycopy(array, 0, temp, 0, array.length); // left

        return temp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param chars DOCUMENT ME!
     * @param b DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int indexOf(byte[] chars, byte[] b) {
        if (chars.length < b.length) {
            return -1;
        }

        for (int i = 0; i < (chars.length - b.length + 1); i++) {
            byte[] temp = new byte[b.length];
            System.arraycopy(chars, i, temp, 0, b.length);

            if (equals(temp, b)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * DOCUMENT ME!
     *
     * @param chars DOCUMENT ME!
     * @param b DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int indexOf(char[] chars, char[] b) {
        if (chars.length < b.length) {
            return -1;
        }

        for (int i = 0; i < (chars.length - b.length + 1); i++) {
            char[] temp = new char[b.length];
            System.arraycopy(chars, i, temp, 0, b.length);

            if (equals(temp, b)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * DOCUMENT ME!
     *
     * @param chars DOCUMENT ME!
     * @param b DOCUMENT ME!
     * @param offset DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int indexOf(char[] chars, char[] b, int offset) {
        if ((chars.length - offset) < b.length) {
            return -1;
        }

        for (int i = offset; i < (chars.length - b.length + 1); i++) {
            char[] temp = new char[b.length];
            System.arraycopy(chars, i, temp, 0, b.length);

            if (equals(temp, b)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * DOCUMENT ME!
     *
     * @param chars DOCUMENT ME!
     * @param b DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int lastIndexOf(char[] chars, char[] b) {
        if (chars.length < b.length) {
            return -1;
        }

        for (int i = (chars.length - b.length); i < 0; i--) {
            char[] temp = new char[b.length];
            System.arraycopy(chars, i, temp, 0, b.length);

            if (equals(temp, b)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Method equals.
     *
     * @param temp
     * @param b
     *
     * @return boolean
     */
    private static boolean equals(byte[] a, byte[] a2) {
        if (a == a2) {
            return true;
        }

        if ((a == null) || (a2 == null)) {
            return false;
        }

        int length = a.length;

        if (a2.length != length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (a[i] != a2[i]) {
                return false;
            }
        }

        return true;
    }

    private static boolean equals(char[] a, char[] a2) {
        if (a == a2) {
            return true;
        }

        if ((a == null) || (a2 == null)) {
            return false;
        }

        int length = a.length;

        if (a2.length != length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (a[i] != a2[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Method push.
     */
    public static byte[] push(byte[] buffer, byte[] data, int length) {
        if (data.length == 0) {
            return buffer;
        }

        byte[] temp = new byte[buffer.length + length];

        if (buffer.length > 0) {
            System.arraycopy(buffer, 0, temp, 0, buffer.length);
        }

        System.arraycopy(data, 0, temp, buffer.length, length);

        return temp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param buffer DOCUMENT ME!
     * @param data DOCUMENT ME!
     * @param length DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static char[] push(char[] buffer, char[] data, int length) {
        if (data.length == 0) {
            return buffer;
        }

        char[] temp = new char[buffer.length + length];

        if (buffer.length > 0) {
            System.arraycopy(buffer, 0, temp, 0, buffer.length);
        }

        System.arraycopy(data, 0, temp, buffer.length, length);

        return temp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param buffer DOCUMENT ME!
     * @param data DOCUMENT ME!
     * @param offset DOCUMENT ME!
     * @param length DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static char[] push(char[] buffer, char[] data, int offset, int length) {
        if (data.length == 0) {
            return buffer;
        }

        char[] temp = new char[buffer.length + length];

        if (buffer.length > 0) {
            System.arraycopy(buffer, 0, temp, 0, buffer.length);
        }

        System.arraycopy(data, offset, temp, buffer.length, length);

        return temp;
    }

    /**
     * Method push.
     */
    public static int[] push(int[] buffer, int[] data, int length) {
        if (data.length == 0) {
            return buffer;
        }

        int[] temp = new int[buffer.length + length];

        if (buffer.length > 0) {
            System.arraycopy(buffer, 0, temp, 0, buffer.length);
        }

        System.arraycopy(data, 0, temp, buffer.length, length);

        return temp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param buffer DOCUMENT ME!
     * @param data DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int[] push(int[] buffer, int[] data) {
        return push(buffer, data, data.length);
    }

    /**
     * DOCUMENT ME!
     *
     * @param buffer DOCUMENT ME!
     * @param data DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static byte[] push(byte[] buffer, byte[] data) {
        return push(buffer, data, data.length);
    }

    /**
     * DOCUMENT ME!
     *
     * @param buffer DOCUMENT ME!
     * @param data DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static char[] push(char[] buffer, char[] data) {
        return push(buffer, data, data.length);
    }

    /**
     * DOCUMENT ME!
     *
     * @param array1 DOCUMENT ME!
     * @param array2 DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Object[] push(Object[] array1, Object[] array2) {
        if (!array1.getClass().getComponentType().equals(array2.getClass()
                                                                   .getComponentType())) {
            throw new ArrayStoreException();
        }

        Object[] temp = (Object[]) java.lang.reflect.Array.newInstance(array1.getClass()
                                                                             .getComponentType(),
                array1.length + array2.length);
        System.arraycopy(array1, 0, temp, 0, array1.length);
        System.arraycopy(array2, 0, temp, array1.length, array2.length);

        return temp;
    }
}
