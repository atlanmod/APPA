package org.atlanmod.appa.binaries;

import org.atlanmod.appa.io.*;
import org.atlanmod.commons.Preconditions;
import org.atlanmod.commons.collect.MoreArrays;
import org.atlanmod.commons.primitive.Strings;

public class StringConverter implements Converter {

    StringSerializer serializer     = new StringSerializer();
    StringDeserializer deserializer = new StringDeserializer();

    @Override
    public byte[] toBytes(Object object) {
        Preconditions.checkInstanceOf(object, String.class);

        byte[] bytes = Strings.toBytes((String) object);
        UnsignedShort length = UnsignedShort.fromInt(bytes.length);

        return MoreArrays.addAll(UnsignedShorts.toBytes(length), bytes);
        //return serializer.apply(object);
    }

    @Override
    public Object toObject(ByteArrayReader reader) {
        return deserializer.readString(reader);
    }
}
