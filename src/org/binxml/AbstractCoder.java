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

import java.util.ArrayList;

import org.binxml.impl.ICoderListener;

/**
 * @author andy
 * @creationDate on 23.07.2004
 */
public class AbstractCoder {

    private ArrayList listeners = new ArrayList();

    public void addListener(ICoderListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }

    public void removeListener(ICoderListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }
    
    private static final int START = 0;
    private static final int DONE = 1;
    
    protected void fireStart(int work) {
        fire(START, work, null);
    }
    
    protected void fireDone(int work, Object stat) {
        fire(DONE, work, stat);
    }
    
    private void fire(int state, int work, Object stat) {
        synchronized(listeners) {
            for(int i=0; i<listeners.size(); i++) {
                ICoderListener l = (ICoderListener)listeners.get(i);
                if(state==START)
                    l.start(work);
                else
                    if(state==DONE)
                        l.done(work, stat);
            }
        }
    }
    
}