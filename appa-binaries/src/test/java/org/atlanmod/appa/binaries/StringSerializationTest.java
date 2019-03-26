package org.atlanmod.appa.binaries;

import org.atlanmod.appa.io.ByteArrayReader;
import org.atlanmod.appa.io.ByteArrayWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import static org.assertj.core.api.Assertions.assertThat;

public class StringSerializationTest {


    @BeforeEach
    void setup() {

    }

    @Test
    void testReadWriteSimpleString() {
        String expected = "AAAA";

        StringSerializer serializer = new StringSerializer();
        StringDeserializer unserializer = new StringDeserializer();

        byte[] bytes = serializer.apply(expected);

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        ByteArrayReader reader = new ByteArrayReader(buffer);

        String actual = unserializer.readString(reader);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testReadWriteMultipleValues() {
        String[] expected = {"aaaa", "bbb", "cccc", "ddddd"};
        String[] actual = new String[expected.length];

        StringSerializer serializer = new StringSerializer();
        StringDeserializer unserializer = new StringDeserializer();

        ByteBuffer buffer = ByteBuffer.allocate(100);
        for (int i = 0; i < expected.length; i++) {
            byte[] bytes = serializer.apply(expected[i]);
            buffer.put(bytes);
        }
        ByteArrayReader reader = new ByteArrayReader(ByteBuffer.wrap(buffer.array()));
        for (int i = 0; i < actual.length; i++) {
            actual[i] = unserializer.readString(reader);
        }

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testReadWriteSameValue() {
        String[] expected = {"aaaa", "aaaa", "aaaa", "aaaa"};
        String[] actual = new String[expected.length];

        StringSerializer serializer = new StringSerializer();
        StringDeserializer unserializer = new StringDeserializer();

        ByteBuffer buffer = ByteBuffer.allocate(100);
        for (int i = 0; i < expected.length; i++) {
            byte[] bytes = serializer.apply(expected[i]);
            buffer.put(bytes);
        }

        System.out.println(buffer.position());

        ByteArrayReader reader = new ByteArrayReader(ByteBuffer.wrap(buffer.array()));
        for (int i = 0; i < actual.length; i++) {
            actual[i] = unserializer.readString(reader);
        }

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testReadWriteValuesWithSegments() {
        String[] expected = {"AAAAA/AAAAA.BBBB.CCCC", "BBBB"};
        String[] actual = new String[expected.length];

        StringSerializer serializer = new StringSerializer();
        ByteBuffer buffer = ByteBuffer.allocate(100);
        ByteArrayWriter writer = new ByteArrayWriter(buffer);
        for (int i = 0; i < expected.length; i++) {
            writer.writeString(expected[i]);
        }

        ByteArrayReader reader = new ByteArrayReader(ByteBuffer.wrap(buffer.array()));

        for (int i = 0; i < actual.length; i++) {
            actual[i] = reader.readString();
        }

        assertThat(actual).isEqualTo(expected);
    }

}
