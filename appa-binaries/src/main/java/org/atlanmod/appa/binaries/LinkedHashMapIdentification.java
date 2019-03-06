package org.atlanmod.appa.binaries;

import org.eclipse.emf.ecore.EObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LinkedHashMapIdentification implements Identifier {
    private Map<EObject, WritableId> idMap = new LinkedHashMap<>();

    @Override
    public WritableId idFor(EObject eObject) {
        return idMap.get(eObject);
    }

    @Override
    public void identify(EObject eObject) {
        WritableId id = new IntegerId(idMap.size());
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
    public Set<Map.Entry<EObject, WritableId>> entrySet() {
        return idMap.entrySet();
    }
}
