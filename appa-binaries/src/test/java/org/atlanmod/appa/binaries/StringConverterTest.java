package org.atlanmod.appa.binaries;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

class StringConverterTest {

    Converter converter;

    @BeforeEach
    void setUp() {
        converter = new StringConverter();
    }

    @Test
    void toBytes() {
        String expected = "AAAA";
        byte[] bytes = converter.toBytes(expected);

        System.out.println(Arrays.toString(expected.getBytes(StandardCharsets.UTF_8)));
        //assertThat(bytes[0]).isEqualTo(4);

    }

    @Test
    void toObject() {
    }
}