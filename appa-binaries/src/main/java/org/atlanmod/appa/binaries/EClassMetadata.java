package org.atlanmod.appa.binaries;

import org.atlanmod.commons.collect.MoreArrays;
import org.eclipse.emf.ecore.*;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

class EClassMetadata {
    private final int id;
    private final List<EFeatureMetadata> features;
    private final EClass eClass;

    public EClassMetadata(Integer id, EClass eClass) {
        this.id = id;
        this.eClass = eClass;
        this.features = new ArrayList<>(eClass.getFeatureCount());
    }

    public static EClassMetadata create(Integer id, EClass eClass) {
        EClassMetadata eClassMetadata = new EClassMetadata(id, eClass);
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
        EAttributeMetadata metadata = EAttributeMetadata.create(eAttribute);
        this.features.add(metadata);
    }

    private void registerReference(EReference reference) {
        EReferenceMetadata metadata = EReferenceMetadata.create(reference);
        this.features.add(metadata);
    }


    public byte[] featuresToBytes(EObject eObject) {

        byte[] accumulation = new byte[0];
        BitSet flags = new BitSet(features.size());
        int position = 0;
        for (EFeatureMetadata each : this.features) {
            byte[] attributeBytes = each.toBytes(eObject);
            if (attributeBytes.length > 0) {
                flags.set(position);
                accumulation = MoreArrays.addAll(accumulation, attributeBytes);
            }
            position++;
        }
        return MoreArrays.addAll(flags.toByteArray(), accumulation);
    }

}
