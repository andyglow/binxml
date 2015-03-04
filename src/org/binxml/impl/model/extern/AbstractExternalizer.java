package org.binxml.impl.model.extern;

import org.binxml.impl.CodeContext;
import org.binxml.impl.IExternalizable;
import org.binxml.impl.model.IComposite;


/**
 * @author andy
 * @creationDate on 21.07.2004
 */
public abstract class AbstractExternalizer implements IExternalizable {

    protected IComposite composite;
    protected CodeContext context;
    
    public AbstractExternalizer(IComposite comp, CodeContext context) {
        this.composite = comp;
        this.context= context;
    }

}
