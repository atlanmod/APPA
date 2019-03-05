package org.atlanmod.appa.binaries;

import org.atlanmod.commons.primitive.Bytes;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmt.modisco.java.emf.meta.JavaPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BinaryWriterTest {

    private static String RESOURCE_FILE = "./src/main/resources/org.eclipse.jdt.source.all.xmi";
    private Resource resource;

    @BeforeEach
    void setup() {
        // Initialize the model
        JavaPackage.eINSTANCE.eClass();

        // Register the XMI resource factory for the .xmi extension
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("xmi", new XMIResourceFactoryImpl());

        // Obtain a new resource set
        ResourceSet resSet = new ResourceSetImpl();

        // Get the resource
        resource = resSet.getResource(URI.createFileURI(RESOURCE_FILE), true);
    }

    //@Test
    void testConvertInt() {
        Function<Object, byte[]> converter = ECoreConverters.getInstance()
                .getPrimitiveTypeConverter(EcorePackage.EINT);

        byte[] actual = converter.apply(10000000);
        assertTrue(actual.length > 0);
    }

    //@Test
    void testConvertString() {
        Function<Object, byte[]> converter = ECoreConverters.getInstance()
                .getPrimitiveTypeConverter(EcorePackage.ESTRING);

        String expected = "./src/main/resources/fr.inria.atlanmod.neo4emf.neo4jresolver.xmi";
        byte[] bytes = converter.apply(expected);

        String actual = Bytes.toString(bytes);

        System.out.println(actual);
        assertEquals(expected, actual);

    }

    @Test
    void testRun() throws IOException {
        File file = new File("out.bin");
        FileChannel channel = new FileOutputStream(file, false).getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1000000000);
        BinaryWriter writer = new BinaryWriter(resource, buffer);
        writer.run();
        buffer.flip();
        channel.write(buffer);
        channel.close();
    }

}