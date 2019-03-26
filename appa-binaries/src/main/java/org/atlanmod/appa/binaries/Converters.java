package org.atlanmod.appa.binaries;

import org.atlanmod.appa.datatypes.Id;
import org.atlanmod.appa.io.ByteArrayReader;
import org.atlanmod.appa.io.CompressedInts;
import org.atlanmod.commons.Preconditions;
import org.atlanmod.commons.annotation.Static;
import org.atlanmod.commons.collect.MoreArrays;
import org.atlanmod.commons.primitive.*;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Converters {
    private Map<Integer, Converter> converters = new HashMap<>();
    private Map<Integer, Converter> eListConverters = new HashMap<>();
    private SingleReferenceConverter singleReferenceConverter;
    private MultipleReferenceConverter multipleReferenceConverter;
    private ENumConverter eNumConverter;
    private final Identifier identifier;


    public Converters(Identifier identifier) {
        this.identifier = identifier;
        this.initialize();
    }

    private static BigInteger toBigInteger(ByteArrayReader reader) {
        Integer size = reader.readInteger();
        byte[] bytes = new byte[size];
        reader.get(bytes);
        return new BigInteger(bytes);
    }

    private void initialize() {
        converters.put(EcorePackage.EBOOLEAN,       new BooleanConverter());
        converters.put(EcorePackage.EBYTE,          new ByteConverter());
        converters.put(EcorePackage.ECHAR,          new CharConverter());
        converters.put(EcorePackage.ESHORT,         new ShortConverter());
        converters.put(EcorePackage.EINT,           new IntConverter());
        converters.put(EcorePackage.ELONG,          new LongConverter());
        converters.put(EcorePackage.EFLOAT,         new FloatConverter());
        converters.put(EcorePackage.EDOUBLE,        new DoubleConverter());
        converters.put(EcorePackage.ESTRING,        new StringConverter());
        converters.put(EcorePackage.EDATE,          new DateConverter());
        converters.put(EcorePackage.EBIG_INTEGER,   new BigIntegerConverter());
        converters.put(EcorePackage.EBIG_DECIMAL,   new BigDecimalConverter());
        converters.put(EcorePackage.EBYTE_ARRAY,    new ByteArrayConverter());
        converters.put(EcorePackage.EBOOLEAN_OBJECT,new BooleanConverter());
        converters.put(EcorePackage.EBYTE_OBJECT,   new ByteConverter());
        converters.put(EcorePackage.ECHARACTER_OBJECT,new ByteConverter());
        converters.put(EcorePackage.EDOUBLE_OBJECT, new DoubleConverter());
        converters.put(EcorePackage.EFLOAT_OBJECT,  new FloatConverter());
        converters.put(EcorePackage.EINTEGER_OBJECT,new IntConverter());
        converters.put(EcorePackage.ELONG_OBJECT,   new LongConverter());
        converters.put(EcorePackage.ESHORT_OBJECT,  new ShortConverter());
        converters.put(EcorePackage.EJAVA_OBJECT, null); // Java Object is not Serializable.
        converters.put(EcorePackage.EJAVA_CLASS, null);

        for (Map.Entry<Integer, Converter> each : this.converters.entrySet()) {
            eListConverters.put(each.getKey(), new EListConverter(each.getValue()));
        }

        this.singleReferenceConverter = new SingleReferenceConverter(identifier);
        this.multipleReferenceConverter = new MultipleReferenceConverter(identifier);
        this.eNumConverter = new ENumConverter();
    }

    public Converter getPrimitiveTypeConverter(Integer i) {
        return this.converters.get(i);
    }

    public Converter getEListConverter(Integer i) {
        return this.eListConverters.get(i);
    }

    public Converter getSingleReferenceConverter() {
        return this.singleReferenceConverter;
    }

    public Converter getMultipleReferenceConverter() {
        return this.multipleReferenceConverter;
    }

    public Converter getEnumConverter() {
        return eNumConverter;
    }

    static class BooleanConverter implements Converter {
        @Override
        public byte[] toBytes(Object object) {
            Preconditions.checkArgument(Boolean.class.isInstance(object));
            return Booleans.toBytes((boolean) object);
        }

        @Override
        public Object toObject(ByteArrayReader reader) {
            return reader.readBoolean().booleanValue();
        }
    }

    static class ByteConverter implements  Converter {
        @Override
        public byte[] toBytes(Object object) {
            Preconditions.checkArgument(Byte.class.isInstance(object));
            byte[] bytes = {(byte) object};
            return bytes;
        }

        @Override
        public Object toObject(ByteArrayReader reader) {
            return reader.readByte().byteValue();
        }
    }

    static class CharConverter implements Converter {
        @Override
        public byte[] toBytes(Object object) {
            return Chars.toBytes((char) object);
        }

        @Override
        public Object toObject(ByteArrayReader reader) {
            return reader.readChar().charValue();
        }
    }

    static class ShortConverter implements Converter {
        @Override
        public byte[] toBytes(Object object) {
            Preconditions.checkArgument(Short.class.isInstance(object));
            return Shorts.toBytes((byte) object);
        }

        @Override
        public Object toObject(ByteArrayReader reader) {
            return reader.readShort().shortValue();
        }
    }

    static class IntConverter implements Converter {
        @Override
        public byte[] toBytes(Object object) {
            Preconditions.checkArgument(Integer.class.isInstance(object));

            return Ints.toBytes((int) object);
            //return CompressedInts.toBytes((int) object);
        }

        @Override
        public Object toObject(ByteArrayReader reader) {
            return reader.readInteger().intValue();
        }
    }

    static class LongConverter implements Converter {
        @Override
        public byte[] toBytes(Object object) {
            Preconditions.checkArgument(Long.class.isInstance(object));

            return Longs.toBytes((long) object);
        }

        @Override
        public Object toObject(ByteArrayReader reader) {
            return reader.readLong().longValue();
        }
    }

    static class FloatConverter implements Converter {
        @Override
        public byte[] toBytes(Object object) {
            Preconditions.checkArgument(Float.class.isInstance(object));
            return Floats.toBytes((float) object);
        }

        @Override
        public Object toObject(ByteArrayReader reader) {
            return reader.readFload();
        }
    }

    static class DoubleConverter implements Converter {
        @Override
        public byte[] toBytes(Object object) {
            Preconditions.checkArgument(Double.class.isInstance(object));
            return Doubles.toBytes((double) object);
        }

        @Override
        public Object toObject(ByteArrayReader reader) {
            return reader.readDouble();
        }
    }

    static class DateConverter implements Converter {
        @Override
        public byte[] toBytes(Object object) {
            Preconditions.checkInstanceOf(object, Date.class);

            Date value = (Date) object;
            return Longs.toBytes(value.getTime());
        }

        @Override
        public Object toObject(ByteArrayReader reader) {
            Long time = reader.readLong();
            return new Date(time);
        }
    }

    static class BigIntegerConverter implements Converter {
        @Override
        public byte[] toBytes(Object object) {
            Preconditions.checkInstanceOf(object, java.math.BigInteger.class);

            java.math.BigInteger value = (java.math.BigInteger) object;
            byte[] values = value.toByteArray();
            return MoreArrays.addAll(Ints.toBytes(values.length), values);
        }

        @Override
        public BigInteger toObject(ByteArrayReader reader) {
            return Converters.toBigInteger(reader);
        }
    }

    static class BigDecimalConverter implements Converter {
        @Override
        public byte[] toBytes(Object object) {
            Preconditions.checkInstanceOf(object, BigDecimal.class);

            BigDecimal value = (BigDecimal) object;
            return MoreArrays.addAll(Ints.toBytes(value.scale()), ECoreConverters.bigIntegerToBytes(value.unscaledValue()));
        }

        @Override
        public Object toObject(ByteArrayReader reader) {
            int scale = reader.readInteger();
            BigInteger unscaled = Converters.toBigInteger(reader);
            return new BigDecimal(unscaled, scale);
        }
    }

    static class ByteArrayConverter implements Converter {
        @Override
        public byte[] toBytes(Object object) {
            Preconditions.checkInstanceOf(object, byte[].class);
            byte[] value = (byte[]) object;
            return MoreArrays.addAll(CompressedInts.toBytes(value.length), value);
        }

        @Override
        public Object toObject(ByteArrayReader reader) {
            int length = reader.readInteger();
            byte[] bytes = new byte[length];
            return reader.get(bytes);
        }
    }

    private static class EListConverter implements Converter {
        private final Converter converter;

        public EListConverter(Converter converter) {
            this.converter = converter;
        }

        @Override
        public byte[] toBytes(Object object) {
            Preconditions.checkInstanceOf(object, EList.class);
            EList<Object> elements = (EList<Object>) object;
            byte[] serialized = CompressedInts.toBytes(elements.size());
            for (Object each : elements) {
                serialized = MoreArrays.addAll(serialized, converter.toBytes(each));
            }
            return serialized;
        }

        @Override
        public Object toObject(ByteArrayReader reader) {
            int size = reader.readCompressedInt();
            List<Object> list = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                list.add(converter.toObject(reader));
            }
            return list;
        }
    }

    static class SingleReferenceConverter implements Converter {
        private final Identifier identifier;

        public SingleReferenceConverter(Identifier identifier) {
            this.identifier = identifier;
        }

        @Override
        public byte[] toBytes(Object object) {
            Preconditions.checkInstanceOf(object, EObject.class);

            EObject eObject = (EObject) object;
            Id id = identifier.idFor(eObject);
            return id.toBytes();
        }

        @Override
        public Object toObject(ByteArrayReader reader) {
            WritableId id = new IntegerId(reader.readCompressedInt());
            return identifier.eObjectFor(id);
        }
    }

    static class MultipleReferenceConverter implements Converter {
        private final Identifier identifier;

        public MultipleReferenceConverter(Identifier identifier) {
            this.identifier = identifier;
        }

        @Override
        public byte[] toBytes(Object object) {
            Preconditions.checkInstanceOf(object, EList.class);
            EList<EObject> references = (EList) object;
            byte[] referenceBytes = CompressedInts.toBytes(references.size());
            for (EObject each : references) {
                Id id = identifier.idFor(each);
                referenceBytes = MoreArrays.addAll(referenceBytes, id.toBytes());
            }
            return referenceBytes;
        }

        @Override
        public Object toObject(ByteArrayReader reader) {
            int size = reader.readCompressedInt();
            List<EObject> list = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                WritableId id = new IntegerId(reader.readCompressedInt());
                list.add(identifier.eObjectFor(id));
            }
            return list;
        }
    }

    static class ENumConverter implements Converter {
        @Override
        public byte[] toBytes(Object object) {
            Preconditions.checkInstanceOf(object, Enumerator.class);

            Enumerator literal = (Enumerator) object;
            return CompressedInts.toBytes(literal.getValue());
        }

        @Override
        public Object toObject(ByteArrayReader reader) {
            int literal = reader.readCompressedInt();
            return literal;
        }
    }


    public static byte[] merge(byte[] ...arrays) {
        // TODO: Move to another class.
        int length = 0;
        for (int i = 0; i < arrays.length; i++) {
            length += arrays[i].length;
        }

        byte[] concatenation = new byte[length];
        int position = 0;
        for (int i = 0; i < arrays.length; i++){
            System.arraycopy(arrays[i], 0, concatenation, position, arrays[i].length);
            position += arrays[i].length;
        }
        return  concatenation;
    }
}
