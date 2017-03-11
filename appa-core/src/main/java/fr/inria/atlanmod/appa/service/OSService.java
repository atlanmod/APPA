/*
 * Copyright (c) 2016-2017 Atlanmod INRIA LINA Mines Nantes.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Atlanmod INRIA LINA Mines Nantes - initial API and implementation
 */

package fr.inria.atlanmod.appa.service;

import fr.inria.atlanmod.appa.base.Service;

import java.util.Map;
import java.util.Set;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Interface that defines the operation provided by the Object Storage Service,
 * i.e. storage and retrieval of objects and attributes of objects
 *
 * @author Gaetan Gaumer (gaetan.gaumer@univ-nantes.fr)
 */
@ParametersAreNonnullByDefault
public interface OSService extends Service {

    /**
     * Stores an object in the objectStorage according to a key
     *
     * @param key The key of the object to store
     * @param obj The object to store
     */
    void storeObject(String key, Object obj);

    /**
     * Updates the value of the attribute of a previously stored object
     * identified by key
     */
    void updateAttribute(String key, String attributeName, Object value);

    /**
     * Updates the values of a set of attributes of a previously stored object
     * identified by key. The values are stored in a map whom key is the name of the attribute.
     */
    void updateAttributeSet(String key, Map<String, Object> attributeValueMap);

    /**
     * Gets an object identified by a key back from the store.
     */
    Object getObject(String key);

    /**
     * Gets an attribute value from an object stored and identified by a key.
     * <b>WARNING:</b> Only the public attribute can be retrieved.
     */
    Object getAttribute(String key, String attribute);

    /**
     * Gets the values of a set of attributes from an object stored and identified by a key.
     * <b>WARNING:</b> Only the public attributes can be retrieved.
     */
    Map<String, Object> getAttributeSet(String key, Set<String> attributeSet);

    /**
     * Deletes the object inditenfied by key from the store.
     */
    void deleteObject(String key);

    /**
     * Searches for keys of stored objects according to a value of an attribute among :
     * <ul>
     * <li>{@code ObjectStorageService.ClassNameTag} : to search for object of a certain type</li>
     * <li>{@code ObjectStorageService.ManagerPeerIDTag} : to search for object managed by a given peer</li>
     * </ul>
     * Examples:
     * {@code keys = OSService.findKeys(ObjectStorageService.ClassNameTag,"java.lang.String")}
     * or
     * {@code keys = OSService.findKeys(ObjectStorageService.ManagerPeerIDTag,anotherPeerID.toString())}
     *
     * @return a, maybe empty, set of keys corresponding to the parameters
     */
    Set<String> searchKeys(String attribute, String value);
}
