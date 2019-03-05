package org.atlanmod.appa.binaries;

import org.atlanmod.commons.Preconditions;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import java.util.function.Function;

public abstract class EFeatureMetadata {
    protected static final byte[] EMPTY_ARRAY = new byte[0];
    protected final Function<Object, byte[]> converter;
    protected final EStructuralFeature feature;

    protected EFeatureMetadata(Function<Object, byte[]> converter, EStructuralFeature feature) {
        Preconditions.checkNotNull(converter);
        Preconditions.checkNotNull(feature);

        this.converter = converter;
        this.feature = feature;
    }

    public byte[] toBytes(EObject eObject) {
        if (eObject.eIsSet(feature)) {
            byte[] bytes = converter.apply(eObject.eGet(feature));
            return bytes;
        } else {
            return EMPTY_ARRAY;
        }
    }
}
