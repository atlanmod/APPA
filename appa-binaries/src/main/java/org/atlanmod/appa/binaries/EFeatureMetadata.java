package org.atlanmod.appa.binaries;

import org.atlanmod.appa.io.ByteArrayReader;
import org.atlanmod.commons.Preconditions;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;


public abstract class EFeatureMetadata {
    protected final Converter converter;
    protected final EStructuralFeature feature;

    protected EFeatureMetadata(Converter converter, EStructuralFeature feature) {
        Preconditions.checkNotNull(converter);
        Preconditions.checkNotNull(feature);

        this.converter = converter;
        this.feature = feature;
    }

    public byte[] toBytes(EObject eObject) {
        byte[] bytes = converter.toBytes(eObject.eGet(feature));
        return bytes;
    }

    public Object toValue(ByteArrayReader reader) {
        Object object = converter.toObject(reader);
        return object;
    }


}
