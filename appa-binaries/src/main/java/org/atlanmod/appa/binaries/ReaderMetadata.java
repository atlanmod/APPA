package org.atlanmod.appa.binaries;

import org.atlanmod.appa.io.ByteArrayReader;
import org.atlanmod.commons.log.Log;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import java.util.HashMap;
import java.util.Map;

public class ReaderMetadata {
    private Map<EPackage, EPackageMetadata> packages = new HashMap<>();
    private Map<Integer, EClassMetadata> classes = new HashMap<>();
    private Identifier identifier = IdentifierFactory.getInstance().createNewIdentifier();
    private Converters converters = new Converters(identifier);

    public void register(final EPackage ePackage) {
        if (packages.containsKey(ePackage)) {
            return;
        }

        EPackageMetadata metadata = EPackageMetadata.create(ePackage);
        packages.put(ePackage, metadata);

        for (EClassifier each: ePackage.getEClassifiers()) {
            if (each instanceof EClass) {
                this.registerEClass((EClass) each);
            } else {
                Log.warn("Found a EClassifier that is not an instance of EClass: " +
                        each.getInstanceClassName());
            }
        }
    }

    private void registerEClass(final EClass eClass) {
        Log.info("Registering class: " + eClass.getName() + " Id: " + eClass.getClassifierID());
        if (classes.containsKey(eClass)) {
            return;
        }

        EClassMetadata eClassMetadata = EClassMetadata.create(eClass, converters);
        classes.put(Integer.valueOf(eClass.getClassifierID()), eClassMetadata);
    }

    public EObject instantiate(Integer classId, WritableId id) {
        EClassMetadata metadata = this.classes.get(classId);
        EObject newObject = metadata.instantiate();
        identifier.put(id, newObject);
        return newObject;
    }

    public void readEFeatures(ByteArrayReader reader) {
        WritableId id = new IntegerId(reader.readCompressedInt());
        EObject eObject = identifier.eObjectFor(id);
        EClassMetadata classMetadata = classes.get(eObject.eClass().getClassifierID());
        classMetadata.bytesToValues(reader, eObject);
    }
}
