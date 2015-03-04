package org.binxml.impl.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.binxml.impl.IExternalizable;
import org.binxml.util.BinUtil;


/**
 * @author andy
 * @creationDate on 20.07.2004
 */
public class Value implements IExternalizable {

    private String value;
    
    public Value(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }

    /* (non-Javadoc)
     * @see org.binxml.impl.IBinaryExternalizable#writeTo(java.io.OutputStream)
     */
    public void writeTo(OutputStream os) throws IOException {
        if(value!=null)
            BinUtil.writeUTF(os, value); 
    }

    /* (non-Javadoc)
     * @see org.binxml.impl.IBinaryExternalizable#readFrom(java.io.InputStream)
     */
    public void readFrom(InputStream is) throws IOException {
        value = BinUtil.readUTF(is);
    }

    public int hashCode() {
        int hashCode = 37;
        hashCode = hashCode * ((value == null) ? 17 : value.hashCode());
        return hashCode;
    }
    
    public boolean equals(Object otherObject) {
        if (otherObject == this) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Value other = (Value) otherObject;
        return ((value == other.value || (value != null && value
                .equals(other.value))));
    }    
}
