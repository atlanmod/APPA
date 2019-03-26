package org.atlanmod.appa.binaries;

import org.eclipse.emf.ecore.EReference;

import java.util.function.Function;

public class EReferenceMetadata extends EFeatureMetadata {

    private EReferenceMetadata(Converter converter, EReference reference) {
        super(converter, reference);
    }

    public static EReferenceMetadata create(EReference eReference, Converters converters) {

        Converter converter = eReference.isMany() ? converters.getMultipleReferenceConverter()
                : converters.getSingleReferenceConverter();

        return new EReferenceMetadata(converter, eReference);
    }

}
