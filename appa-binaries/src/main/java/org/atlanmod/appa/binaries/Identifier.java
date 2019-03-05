package org.atlanmod.appa.binaries;

import org.atlanmod.appa.datatypes.Id;
import org.eclipse.emf.ecore.EObject;

import java.util.Map;
import java.util.Set;

public interface Identifier {

    Id idFor(EObject eObject);

    void identify(EObject eObject);

    boolean knows(EObject eObject);

    Set<EObject> eObjects();

    Set<Map.Entry<EObject, Id>> entrySet();
}
