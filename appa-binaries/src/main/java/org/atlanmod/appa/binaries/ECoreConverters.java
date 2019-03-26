package org.atlanmod.appa.binaries;

import org.atlanmod.appa.datatypes.Id;
import org.atlanmod.appa.io.*;
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
import java.util.function.Function;

public class ECoreConverters {

    private Map<Integer, Function<Object, byte[]>> singleObjectConverters = new HashMap<>();
    private Map<Integer, EListToBytes> eListConverters = new HashMap<>();
    private SingleReferenceToBytes singleReferenceConverter;
    private MultipleReferenceToBytes multipleReferenceConverter;

    protected ECoreConverters() {
        this.initialize();
    }

    /**
     * Returns the instance of this class.
     *
     * @return the instance of this class
     */
    @Nonnull
    public static ECoreConverters getInstance() {
        return ECoreConverters.Holder.INSTANCE;
    }

    public static byte[] booleanToBytes(Object object) {
        Preconditions.checkArgument(Boolean.class.isInstance(object));

        return Booleans.toBytes((boolean) object);
    }

    public static byte[] byteToBytes(Object object) {
        Preconditions.checkArgument(Byte.class.isInstance(object));

        byte[] bytes = {(byte) object};
        return bytes;
    }

    public static byte[] charToBytes(Object object) {
        return Chars.toBytes((char) object);
    }

    public static byte[] shortToBytes(Object object) {
        Preconditions.checkArgument(Short.class.isInstance(object));

        return Shorts.toBytes((byte) object);
    }

    public static byte[] intToBytes(Object object) {
        Preconditions.checkArgument(Integer.class.isInstance(object));

        //return Ints.toBytes((int) object);
        return CompressedInts.toBytes((int) object);
    }

    public static byte[] longToBytes(Object object) {
        Preconditions.checkArgument(Long.class.isInstance(object));

        return Longs.toBytes((long) object);
    }

    public static byte[] floatToBytes(Object object) {
        Preconditions.checkArgument(Float.class.isInstance(object));

        return Floats.toBytes((float) object);
    }

    public static byte[] doubleToBytes(Object object) {
        Preconditions.checkArgument(Double.class.isInstance(object));

        return Doubles.toBytes((double) object);
    }

    public static byte[] stringToBytes(Object object) {
        Preconditions.checkInstanceOf(object, String.class);

        byte[] bytes = Strings.toBytes((String) object);
        UnsignedByte length = UnsignedByte.fromInt(bytes.length);

        return MoreArrays.addAll(UnsignedBytes.toBytes(length), bytes);
    }

    public static byte[] dateToBytes(Object object) {
        Preconditions.checkInstanceOf(object, Date.class);

        Date value = (Date) object;
        return Longs.toBytes(value.getTime());
    }

    public static byte[] bigIntegerToBytes(Object object) {
        Preconditions.checkInstanceOf(object, BigInteger.class);

        BigInteger value = (BigInteger) object;
        byte[] values = value.toByteArray();
        return MoreArrays.addAll(Ints.toBytes(values.length), values);
    }

    public static byte[] bigDecimalToBytes(Object object) {
        Preconditions.checkInstanceOf(object, BigDecimal.class);

        BigDecimal value = (BigDecimal) object;
        return MoreArrays.addAll(Ints.toBytes(value.signum()), bigIntegerToBytes(value.unscaledValue()));
    }

    public static byte[] byteArrayToBytes(Object object) {
        Preconditions.checkInstanceOf(object, byte[].class);

        byte[] value = (byte[]) object;
        return MoreArrays.addAll(CompressedInts.toBytes(value.length), value);
    }

    public static byte[] enumLiteralToByes(Object object) {
        Preconditions.checkInstanceOf(object, Enumerator.class);

        Enumerator literal = (Enumerator) object;
        return CompressedInts.toBytes(literal.getValue());
    }

    private void initialize() {
        singleObjectConverters.put(EcorePackage.EBOOLEAN, ECoreConverters::booleanToBytes);
        singleObjectConverters.put(EcorePackage.EBYTE, ECoreConverters::byteToBytes);
        singleObjectConverters.put(EcorePackage.ECHAR, ECoreConverters::charToBytes);
        singleObjectConverters.put(EcorePackage.ESHORT, ECoreConverters::shortToBytes);
        singleObjectConverters.put(EcorePackage.EINT, ECoreConverters::intToBytes);
        singleObjectConverters.put(EcorePackage.ELONG, ECoreConverters::longToBytes);
        singleObjectConverters.put(EcorePackage.EFLOAT, ECoreConverters::floatToBytes);
        singleObjectConverters.put(EcorePackage.EDOUBLE, ECoreConverters::doubleToBytes);
        //singleObjectConverters.put(EcorePackage.ESTRING, ECoreConverters::stringToBytes);
        singleObjectConverters.put(EcorePackage.ESTRING, new StringSerializer());
        singleObjectConverters.put(EcorePackage.EDATE, ECoreConverters::dateToBytes);
        singleObjectConverters.put(EcorePackage.EBIG_INTEGER, ECoreConverters::bigIntegerToBytes);
        singleObjectConverters.put(EcorePackage.EBIG_DECIMAL, ECoreConverters::bigDecimalToBytes);
        singleObjectConverters.put(EcorePackage.EBYTE_ARRAY, ECoreConverters::byteArrayToBytes);
        singleObjectConverters.put(EcorePackage.EBOOLEAN_OBJECT, ECoreConverters::booleanToBytes);
        singleObjectConverters.put(EcorePackage.EBYTE_OBJECT, ECoreConverters::byteToBytes);
        singleObjectConverters.put(EcorePackage.ECHARACTER_OBJECT, ECoreConverters::charToBytes);
        singleObjectConverters.put(EcorePackage.EDOUBLE_OBJECT, ECoreConverters::doubleToBytes);
        singleObjectConverters.put(EcorePackage.EFLOAT_OBJECT, ECoreConverters::floatToBytes);
        singleObjectConverters.put(EcorePackage.EINTEGER_OBJECT, ECoreConverters::intToBytes);
        singleObjectConverters.put(EcorePackage.ELONG_OBJECT, ECoreConverters::longToBytes);
        singleObjectConverters.put(EcorePackage.ESHORT_OBJECT, ECoreConverters::shortToBytes);
        singleObjectConverters.put(EcorePackage.EJAVA_OBJECT, null); // Java Object is not Serializable.
        singleObjectConverters.put(EcorePackage.EJAVA_CLASS, null);


        for (Map.Entry<Integer, Function<Object, byte[]>> each : this.singleObjectConverters.entrySet()) {
            eListConverters.put(each.getKey(), new EListToBytes(each.getValue()));
        }

        Identifier identifier = IdentifierFactory.getInstance().createNewIdentifier();
        this.singleReferenceConverter = new SingleReferenceToBytes(identifier);
        this.multipleReferenceConverter = new MultipleReferenceToBytes(identifier);

    }

    public Function<Object, byte[]> getPrimitiveTypeConverter(Integer i) {
        return this.singleObjectConverters.get(i);
    }

    public Function<Object, byte[]> getEListConverter(Integer i) {
        return this.eListConverters.get(i);
    }

    public Function<Object, byte[]> getSingleReferenceConverter() {
        return this.singleReferenceConverter;
    }

    public Function<Object, byte[]> getMultipleReferenceConverter() {
        return this.multipleReferenceConverter;
    }

    public Function<Object, byte[]> getEnumConverter() {
        return ECoreConverters::enumLiteralToByes;
    }

    private static class EListToBytes implements Function<Object, byte[]> {

        private final Function<Object, byte[]> converter;

        public EListToBytes(Function<Object, byte[]> converter) {
            this.converter = converter;
        }

        @Override
        public byte[] apply(Object o) {
            Preconditions.checkInstanceOf(o, EList.class);
            EList<Object> elements = (EList<Object>) o;

            byte[] serialized = CompressedInts.toBytes(elements.size());
            for (Object each : elements) {
                serialized = MoreArrays.addAll(serialized, converter.apply(each));
            }

            return serialized;
        }
    }

    private static class SingleReferenceToBytes implements Function<Object, byte[]> {
        private final Identifier identifier;

        public SingleReferenceToBytes(Identifier identifier) {
            this.identifier = identifier;
        }

        @Override
        public byte[] apply(Object o) {
            Preconditions.checkInstanceOf(o, EObject.class);

            EObject eObject = (EObject) o;
            Id id = identifier.idFor(eObject);
            return id.toBytes();
        }
    }

    private static class MultipleReferenceToBytes implements Function<Object, byte[]> {
        private final Identifier identifier;

        public MultipleReferenceToBytes(Identifier identifier) {
            this.identifier = identifier;
        }

        @Override
        public byte[] apply(Object o) {

            Preconditions.checkInstanceOf(o, EList.class);
            EList<EObject> references = (EList) o;
            byte[] referenceBytes = CompressedInts.toBytes(references.size());
            for (EObject each : references) {
                Id id = identifier.idFor(each);
                referenceBytes = MoreArrays.addAll(referenceBytes, id.toBytes());
            }
            return referenceBytes;
        }
    }


    /**
     * The initialization-on-demand holder of the singleton of this class.
     */
    @Static
    private static final class Holder {

        /**
         * The instance of the outer class.
         */
        private static final ECoreConverters INSTANCE = new ECoreConverters();
    }
}
