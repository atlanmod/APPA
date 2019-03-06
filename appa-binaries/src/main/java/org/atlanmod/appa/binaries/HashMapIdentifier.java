package org.atlanmod.appa.binaries;

import org.atlanmod.appa.datatypes.Id;
import org.eclipse.emf.ecore.EObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapIdentifier implements Identifier {
    private Map<EObject, WritableId> idMap = new HashMap<>();

    @Override
    public WritableId idFor(EObject eObject) {
        return idMap.get(eObject);
    }

    @Override
    public void identify(EObject eObject) {
        IntegerId id = new IntegerId(idMap.size());
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
