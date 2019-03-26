package org.atlanmod.appa.binaries;

import org.atlanmod.widespread.PrimitiveAttributes;
import org.atlanmod.widespread.WidespreadFactory;
import org.atlanmod.widespread.WidespreadPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PackageSerializationTest {

    private EPackage.Registry registry = EPackage.Registry.INSTANCE;
    private WidespreadPackage wPackage = WidespreadPackage.eINSTANCE;
    private WidespreadFactory factory = wPackage.getWidespreadFactory();
    private ByteBuffer byteBuffer;
    private Resource input;
    private Resource output;

    @BeforeEach
    void setup() {
        wPackage.eClass();
        registry.put(WidespreadPackage.eINSTANCE.getNsURI(), WidespreadPackage.eINSTANCE);
        byteBuffer = ByteBuffer.allocate(400);
        input = new ResourceImpl();
        output = new ResourceImpl();

        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("xmi", new XMIResourceFactoryImpl());
        m.put("bin", new ResourceFactoryImpl() {
            @Override
            public Resource createResource(URI uri) {
                return new BinaryResourceImpl(uri);
            }
        });
    }


    @Test
    void storeOneElement() {
        PrimitiveAttributes primitive = factory.createPrimitiveAttributes();
        primitive.setAFloat(1255.0f);

        input.getContents().add(primitive);
        BinaryWriter writer = new BinaryWriter(input, byteBuffer);
        writer.save();

        Resource output = new ResourceImpl();
        BinaryReader reader = new BinaryReader(output, ByteBuffer.wrap(byteBuffer.array()));
        reader.load();

        PrimitiveAttributes actual = (PrimitiveAttributes) output.getContents().get(0);

        assertThat(actual.getAFloat()).isEqualTo(primitive.getAFloat());
    }

    //@Test
    void storeSeveralSimpleElements() {
        int[] expected = {1, 3, 5};
        int[] actual = new int[expected.length];
        Resource resource = new ResourceImpl();

        for (int each : expected) {
            PrimitiveAttributes primitive = factory.createPrimitiveAttributes();
            primitive.setAnInt(each);
            resource.getContents().add(primitive);
        }


        BinaryWriter writer = new BinaryWriter(resource, byteBuffer);
        writer.save();

        Resource output = new ResourceImpl();
        BinaryReader reader = new BinaryReader(output, ByteBuffer.wrap(byteBuffer.array()));
        reader.load();
        for (int i = 0; i < actual.length; i++) {
            PrimitiveAttributes element = (PrimitiveAttributes) output.getContents().get(i);
            actual[i] = element.getAnInt();
        }

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void compareWithEMF() throws IOException {
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] actual = new int[expected.length];
        Resource resource = new ResourceImpl();

        for (int each : expected) {
            PrimitiveAttributes primitive = factory.createPrimitiveAttributes();
            primitive.setAnInt(Integer.MAX_VALUE);
            primitive.setAString("String: " + each);
            resource.getContents().add(primitive);
        }

        File file = new File("compare-appa.bin");
        FileChannel channel = new FileOutputStream(file, false).getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1000000000);
        BinaryWriter writer = new BinaryWriter(resource, buffer);
        writer.save();
        buffer.flip();
        channel.write(buffer);
        channel.close();

        ResourceSet rset = new ResourceSetImpl();
        Resource emfResource = rset.createResource(URI.createURI("compare-emf.bin"));
        emfResource.getContents().addAll(resource.getContents());

        emfResource.save(Collections.emptyMap());
    }
}
