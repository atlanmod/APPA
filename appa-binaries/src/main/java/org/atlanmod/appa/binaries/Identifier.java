package org.atlanmod.appa.binaries;

import org.eclipse.emf.ecore.EObject;

import java.util.Map;
import java.util.Set;

public interface Identifier {

    WritableId idFor(EObject eObject);

    EObject eObjectFor(WritableId id);

    void put(WritableId id, EObject eObject);

    void identify(EObject eObject);

    boolean knows(EObject eObject);

    Set<EObject> eObjects();

    Set<Map.Entry<EObject, WritableId>> entrySet();
}
