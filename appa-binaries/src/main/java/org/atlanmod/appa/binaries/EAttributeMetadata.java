package org.atlanmod.appa.binaries;

import org.atlanmod.commons.log.Log;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EStructuralFeature;

import java.util.Objects;
import java.util.function.Function;

class EAttributeMetadata extends EFeatureMetadata {

    private EAttributeMetadata(Converter converter, EStructuralFeature feature) {
        super(converter, feature);
    }

    public static EAttributeMetadata create(EAttribute attribute, Converters converters) {
        EDataType type = attribute.getEAttributeType();
        int id = type.getClassifierID();
        Converter converter;

        if (type instanceof EEnum) {
            converter = converters.getEnumConverter();
        } else {
            converter = attribute.isMany() ? converters.getEListConverter(id)
                    : converters.getPrimitiveTypeConverter(id);
        }

        if (Objects.isNull(converter)) {

            Log.error("Could not find converter to Id: " + id);
            Log.error("EDataType: " + type);
            Log.error("EDataType: Instance Class Name: " + type.getInstanceClassName());
        }

        return new EAttributeMetadata(converter, attribute);
    }
}
