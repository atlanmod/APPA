package org.atlanmod.appa.binaries;

import org.atlanmod.appa.datatypes.Id;
import org.atlanmod.appa.io.ByteArrayWriter;
import org.atlanmod.appa.io.CompressedInts;
import org.atlanmod.appa.io.UnsignedShort;
import org.atlanmod.appa.io.UnsignedShorts;
import org.atlanmod.commons.collect.MoreArrays;
import org.atlanmod.commons.primitive.Ints;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Metadata {
    private final ByteArrayWriter writer;
    private Identifier identifier = IdentifierFactory.getInstance().getDefaultIndentifier();
    private Map<EPackage, EPackageMetadata> ePackageDataMap = new HashMap<EPackage, EPackageMetadata>();
    private Map<EClass, EClassMetadata> eClassDataMap = new HashMap<EClass, EClassMetadata>();
    private Map<URI, Integer> uriToIDMap = new HashMap<URI, Integer>();
    private Header header = new Header();

    public Metadata(ByteArrayWriter writer) {
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

        Integer id = Integer.valueOf(eClassDataMap.size());
        EClassMetadata eClassMetadata = EClassMetadata.create(id, eClass);
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
        writer.writeInteger(this.identifier.eObjects().size());
        for (Map.Entry<EObject, WritableId> each : this.identifier.entrySet()) {

            writer.put(CompressedInts.toBytes(each.getKey().eClass().getClassifierID()));
            writer.write(each.getValue());
        }
    }

    public void writeEFeatures(EObject eObject) {
        WritableId id = identifier.idFor(eObject);
        EClassMetadata classMetadata = this.eClassDataMap.get(eObject.eClass());
        byte[] eObjectFeatureValues = classMetadata.featuresToBytes(eObject);

        writer.write(id)
                .put(eObjectFeatureValues);
    }


    public static byte[] merge(byte[] ...arrays) {
        int length = 0;
        for (int i = 0; i < arrays.length; i++) {
            length += arrays[i].length;
        }

        byte[] concatenation = new byte[length];
        int position = 0;
        for (int i = 0; i < arrays.length; i++){
            System.arraycopy(arrays[i], 0, concatenation, position, arrays[i].length);
            position = arrays[i].length;
        }
        return  concatenation;
    }

}
