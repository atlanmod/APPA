package org.atlanmod.appa.binaries;

import org.eclipse.emf.ecore.EReference;

import java.util.function.Function;

public class EReferenceMetadata extends EFeatureMetadata {

    private EReferenceMetadata(Function<Object, byte[]> converter, EReference reference) {
        super(converter, reference);
    }

    public static EReferenceMetadata create(EReference eReference) {
        Function<Object, byte[]> converter;
        ECoreConverters converters = ECoreConverters.getInstance();

        converter = eReference.isMany() ? converters.getMultipleReferenceConverter()
                : converters.getSingleReferenceConverter();

        return new EReferenceMetadata(converter, eReference);
    }

}
