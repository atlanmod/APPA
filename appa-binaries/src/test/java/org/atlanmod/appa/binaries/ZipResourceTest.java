package org.atlanmod.appa.binaries;


import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmt.modisco.java.emf.meta.JavaPackage;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

public class ZipResourceTest {
    private static String RESOURCE_FILE = "./src/main/resources/org.eclipse.jdt.source.all.xmi";
    private Resource resource;

    //@BeforeEach
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

    @Test
    void test() {

    }

    //@AfterEach
    void tearDown() throws Exception {
        Resource.Factory zipResourceFactory = new XMIResourceFactoryImpl() {
            @Override
            public Resource createResource(URI uri) {
                XMIResource xmiResource = (XMIResource) super.createResource(uri);
                xmiResource.setUseZip(true);
                return xmiResource;
            }
        };

        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("zip", zipResourceFactory);

        m.put("bin", new ResourceFactoryImpl() {
            @Override
            public Resource createResource(URI uri) {
                return new BinaryResourceImpl(uri);
            }
        });

        ResourceSet resSet = new ResourceSetImpl();
        Resource zipped = resSet.createResource(URI.createFileURI(RESOURCE_FILE + ".zip"));
        Resource binary = resSet.createResource(URI.createURI(RESOURCE_FILE + ".bin"));

        zipped.getContents().addAll(resource.getContents());
        zipped.save(Collections.emptyMap());


        binary.getContents().addAll(zipped.getContents());

        //resource.unload();
        //zipped.unload();
        binary.save(Collections.emptyMap());


    }
}
