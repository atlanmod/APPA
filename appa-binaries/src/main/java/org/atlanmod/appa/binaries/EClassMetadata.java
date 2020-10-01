package org.atlanmod.appa.binaries;

import org.atlanmod.appa.io.ByteArrayReader;
import org.atlanmod.appa.io.UnsignedByte;
import org.atlanmod.appa.io.UnsignedBytes;
import org.atlanmod.commons.collect.MoreArrays;
import org.atlanmod.commons.log.Log;
import org.eclipse.emf.ecore.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

class EClassMetadata {
    private final List<EFeatureMetadata> features;
    private final EClass eClass;
    private final Converters converters;

    public EClassMetadata(EClass eClass, Converters converters) {
        this.eClass = eClass;
        this.features = new ArrayList<>(eClass.getFeatureCount());
        this.converters = converters;
    }

    public static EClassMetadata create(EClass eClass, Converters converters) {
        EClassMetadata eClassMetadata = new EClassMetadata(eClass, converters);
        eClassMetadata.registerFeatures();
        return eClassMetadata;
    }

    private void registerFeatures() {
        for (int i = 0; i < eClass.getFeatureCount(); i++) {
            EStructuralFeature.Internal feature = (EStructuralFeature.Internal) eClass.getEStructuralFeature(i);
            boolean shouldBePersisted = !(feature.isTransient() |
                    feature.isDerived() | feature.isVolatile() |
                    feature.isContainer());
            if (shouldBePersisted) {
                this.registerFeature(feature);
            }
        }
    }

    private void registerFeature(EStructuralFeature feature) {
        if (feature instanceof EAttribute) {
            this.registerAttribute((EAttribute) feature);
        } else {
            assert feature instanceof EReference;
            this.registerReference((EReference) feature);
        }
    }

    private void registerAttribute(EAttribute eAttribute) {
        EAttributeMetadata metadata = EAttributeMetadata.create(eAttribute, converters);
        this.features.add(metadata);
    }

    private void registerReference(EReference reference) {
        EReferenceMetadata metadata = EReferenceMetadata.create(reference, converters);
        this.features.add(metadata);
    }


    public byte[] valuesToBytes(EObject eObject) {

        byte[] accumulation = new byte[0];
        BitSet flags = new BitSet(features.size());
        for (EFeatureMetadata each : this.features) {
            if (eObject.eIsSet(each.feature)) {
                byte[] attributeBytes = each.toBytes(eObject);
                flags.set(each.feature.getFeatureID());
                accumulation = MoreArrays.addAll(accumulation, attributeBytes);
                Log.info("Attribute " + each.feature.getName() + " is set");
            } else {
                //Log.warn("Feature " + each.feature.getName() + " will not be written");
            }
        }

        byte[] bytes = flags.toByteArray();
        UnsignedByte size = UnsignedByte.fromInt(bytes.length);
        byte[] serializedValues = Converters.merge(UnsignedBytes.toBytes(size), bytes, accumulation);
        Log.debug("Values: " + Arrays.toString(serializedValues));
        return serializedValues;
    }

    public void bytesToValues(ByteArrayReader reader, EObject eObject) {
        UnsignedByte size = reader.readUnsignedByte();
        byte[] bytes = new byte[size.byteValue()];
        reader.get(bytes);

        BitSet flags = BitSet.valueOf(bytes);
        for (EFeatureMetadata each : this.features) {
            if (flags.get(each.feature.getFeatureID())) {
                //System.out.println("Feature " + each.feature.getName() + " is set");
                Object value = each.toValue(reader);
                eObject.eSet(each.feature, value);
            } else {
                //System.out.println("Feature " + each.feature.getName() + " is NOT set");
            }
        }
    }

    public EObject instantiate() {
        return eClass.getEPackage().getEFactoryInstance().create(eClass);
    }

}
