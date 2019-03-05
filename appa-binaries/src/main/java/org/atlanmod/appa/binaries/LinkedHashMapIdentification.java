package org.atlanmod.appa.binaries;

import org.atlanmod.appa.datatypes.Id;
import org.eclipse.emf.ecore.EObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LinkedHashMapIdentification implements Identifier {
    private Map<EObject, Id> idMap = new LinkedHashMap<>();

    @Override
    public Id idFor(EObject eObject) {
        return idMap.get(eObject);
    }

    @Override
    public void identify(EObject eObject) {
        Id id = new IntegerId(idMap.size());
        idMap.put(eObject, id);
    }

    @Override
    public boolean knows(EObject eObject) {
        return idMap.containsKey(eObject);
    }

    @Override
    public Set<EObject> eObjects() {
        return idMap.keySet();
    }

    @Override
    public Set<Map.Entry<EObject, Id>> entrySet() {
        return idMap.entrySet();
    }
}
