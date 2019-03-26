package org.atlanmod.appa.binaries;

import org.atlanmod.appa.io.ByteArrayWriter;
import org.atlanmod.appa.io.CompressedInts;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import java.util.HashMap;
import java.util.Map;

public class WriterMetadata {
    private final ByteArrayWriter writer;
    private Identifier identifier = IdentifierFactory.getInstance().createNewIdentifier();
    private Converters converters = new Converters(identifier);
    private Map<EPackage, EPackageMetadata> ePackageDataMap = new HashMap<EPackage, EPackageMetadata>();
    private Map<EClass, EClassMetadata> eClassDataMap = new HashMap<EClass, EClassMetadata>();
    private Map<URI, Integer> uriToIDMap = new HashMap<URI, Integer>();
    private Header header = new Header();

    public WriterMetadata(ByteArrayWriter writer) {
        this.writer = writer;
    }

    public void register(EObject eObject) {
        if (identifier.knows(eObject)) {
            return;
        }

        identifier.identify(eObject);
        registerEClass(eObject.eClass());
    }

    private void registerEClass(final EClass eClass) {
        if (eClassDataMap.containsKey(eClass)) {
            return;
        }

        this.registerEPackage(eClass.getEPackage());
        EClassMetadata eClassMetadata = EClassMetadata.create(eClass, converters);
        eClassDataMap.put(eClass, eClassMetadata);
    }

    public void registerEPackage(final EPackage ePackage) {
        if (ePackageDataMap.containsKey(ePackage)) {
            return;
        }

        EPackageMetadata metadata = EPackageMetadata.create(ePackage);
        ePackageDataMap.put(ePackage, metadata);
    }


    public void writeHeader() {

    }


    public void writeEPackages() {
        writer.writeInteger(ePackageDataMap.size());
        for (EPackageMetadata each : ePackageDataMap.values()) {
            writer.put(each.toBytes());
        }
    }

    public void writeEObjects() {
        writer.put(CompressedInts.toBytes(this.identifier.eObjects().size()));
        for (Map.Entry<EObject, WritableId> each : this.identifier.entrySet()) {
            writer.put(CompressedInts.toBytes(each.getKey().eClass().getClassifierID()));
            writer.write(each.getValue());
        }
    }

    public void writeEFeatures(EObject eObject) {
        WritableId id = identifier.idFor(eObject);
        EClassMetadata classMetadata = this.eClassDataMap.get(eObject.eClass());
        byte[] eObjectFeatureValues = classMetadata.valuesToBytes(eObject);

        writer.write(id)
                .put(eObjectFeatureValues);
    }




}
