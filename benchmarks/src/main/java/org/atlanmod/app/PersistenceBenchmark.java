package org.atlanmod.app;


import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmt.modisco.java.emf.meta.JavaPackage;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 1)
@Measurement(iterations = 3)
public class PersistenceBenchmark {
    private static String XMI_FILE = "./src/main/resources/org.eclipse.jdt.source.all.xmi";
    private static String ZIP_FILE = "./src/main/resources/org.eclipse.jdt.source.all.zip";
    private static String BIN_FILE = "./src/main/resources/org.eclipse.jdt.source.all.bin";

    @Benchmark
    public void loadSaveBinaryResource() throws IOException {
        JavaPackage.eINSTANCE.eClass();
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();

        m.put("bin", new ResourceFactoryImpl() {
            @Override
            public Resource createResource(URI uri) {
                BinaryResourceImpl newResource = new BinaryResourceImpl(uri);
                return newResource;
            }
        });
        ResourceSet resSet = new ResourceSetImpl();
        Resource resource = resSet.getResource(URI.createFileURI(BIN_FILE), true);
        TreeIterator<EObject> it = resource.getAllContents();
        while (it.hasNext()) {
            it.next();
        }

        resource.save(Collections.emptyMap());
        resource.unload();
    }

    @Benchmark
    public void loadSaveXMIResource() throws IOException {

        JavaPackage.eINSTANCE.eClass();
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("xmi", new XMIResourceFactoryImpl());
        ResourceSet resSet = new ResourceSetImpl();
        Resource resource = resSet.getResource(URI.createFileURI(XMI_FILE), true);
        TreeIterator<EObject> it = resource.getAllContents();
        while (it.hasNext()) {
            it.next();
        }

        resource.save(Collections.emptyMap());
        resource.unload();
    }

    @Benchmark
    public void loadSaveZippedResource() throws IOException {
        JavaPackage.eINSTANCE.eClass();
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();

        Resource.Factory zipResourceFactory = new XMIResourceFactoryImpl() {
            @Override
            public Resource createResource(URI uri) {
                XMIResource xmiResource = (XMIResource) super.createResource(uri);
                xmiResource.setUseZip(true);
                return xmiResource;
            }
        };
        m.put("zip", zipResourceFactory);
        ResourceSet resSet = new ResourceSetImpl();
        Resource resource = resSet.getResource(URI.createFileURI(ZIP_FILE), true);
        TreeIterator<EObject> it = resource.getAllContents();
        while (it.hasNext()) {
            it.next();
        }

        resource.save(Collections.emptyMap());
        resource.unload();

    }

}
