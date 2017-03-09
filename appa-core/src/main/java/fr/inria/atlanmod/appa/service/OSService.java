/*
 * APPA -- Atlas P2P Architecture 
 * Copyright (c) 2005 INRIA
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 * Created on 14 avr. 2005 by Ga�tan Gaumer (gaetan.gaumer@univ-nantes.fr) 
 */

package fr.inria.atlanmod.appa.service;

import java.util.Map;
import java.util.Set;

import fr.inria.atlanmod.appa.base.Service;
import fr.inria.atlanmod.appa.exception.IncompatibleOperandeException;
import fr.inria.atlanmod.appa.exception.NoSuchFieldException;
import fr.inria.atlanmod.appa.exception.ObjectNotFoundException;
import fr.inria.atlanmod.appa.exception.OperationFailedException;


/**
 * Interface that defines the operation provided by the Object Storage Service,
 * i.e. storage and retrieval of objects and attributes of objects
 * @author Gaetan Gaumer (gaetan.gaumer@univ-nantes.fr)
 *
 */

public interface OSService extends Service {

    
    /**
     * Store an object in the objectStorage according to a key
     * @param key The key of the object to store 
     * @param obj The Object to store
     */
    public void storeObject(String key, Object obj) throws OperationFailedException;
    

    /**
     * Update the value of the attribute of a previously stored object 
     * identified by key
     * 
     * @param key
     * @param attributeName
     * @param value
     */
    public void updateAttribute(String key, String attributeName, Object value) throws OperationFailedException, ObjectNotFoundException, NoSuchFieldException, IncompatibleOperandeException;
    

    /**
     * Update the values of a set of attributes of a previously stored object 
     * identified by key. The values are stored in a map whom key is the name of the attribute.
     * 
     * @param key
     * @param attributeValueMap
     */
    public void updateAttributeSet(String key, Map<String,Object> attributeValueMap) throws OperationFailedException, ObjectNotFoundException, NoSuchFieldException, IncompatibleOperandeException;

    /**
     * Get an object identified by a key back from the store.
     * 
     * @param key
     * @return
     */
    public Object getObject(String key) throws OperationFailedException, ObjectNotFoundException;
    
    /**
     * Get an attribute value from an object stored and identified by a key.
     * WARNING : only a public attribute can be retrieved.
     * 
     * @param key
     * @param attribute
     * @return
     */
    public Object getAttribute(String key, String attribute) throws OperationFailedException, ObjectNotFoundException, NoSuchFieldException;
    
    /**
     * Get the values of a set of attributes from an object stored and identified by a key.
     * WARNING : only the public attributes can be retrieved.
     *
     * @param key
     * @param attributeSet
     * @return
     * @throws  
     */
    public Map<String,Object> getAttributeSet(String key, Set<String> attributeSet) throws OperationFailedException, ObjectNotFoundException, NoSuchFieldException;
    
    /**
     * Delete the object inditenfied by key from the store.
     * @param key
     */
    public void deleteObject(String key) throws OperationFailedException, ObjectNotFoundException;

    /**
     * Search for keys of stored objects according to a value of an attribute among :<br>
     * <code>ObjectStorageService.ClassNameTag</code> : to search for object of a certain type<br>
     * <code>ObjectStorageService.ManagerPeerIDTag</code> : to search for object managed by a given peer<br>
     * <br>
     * examples :<br>
     * <code>keys = OSService.findKeys(ObjectStorageService.ClassNameTag,"java.lang.String")</code><br>
     * or<br>
     * keys = OSService.findKeys(ObjectStorageService.ManagerPeerIDTag,anotherPeerID.toString())</code><br>
     * @param attribute
     * @param value
     * @return a, maybe empty, set of keys corresponding to the parameters
     * @throws ObjectNotFoundException 
     */
    
    public Set<String> searchKeys(String attribute,String value) throws OperationFailedException, ObjectNotFoundException;

}
