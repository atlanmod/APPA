package org.atlanmod.appa.binaries;

import org.atlanmod.graph.*;
import org.atlanmod.widespread.*;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmt.modisco.java.emf.meta.JavaPackage;
import org.omg.smm.SmmFactory;
import org.omg.smm.SmmModel;
import org.omg.smm.SmmPackage;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class MainTest {
    private static String RESOURCE_FILE = "./src/main/resources/fr.inria.atlanmod.neo4emf.neo4jresolver.xmi";
    private static Resource resource;


    static void initialize() {
        GraphFactory graphFactory = GraphPackage.eINSTANCE.getGraphFactory();
        Graph graph = graphFactory.createGraph();
        Edge edge = graphFactory.createEdge();
        graph.getEdges().add(edge);

        Vertex vertex = graphFactory.createVertex();
        vertex.setLabel("label 1");

        SmmFactory smmFactory = SmmPackage.eINSTANCE.getSmmFactory();
        SmmModel model = smmFactory.createSmmModel();
        model.setName("A model");

        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("resource", new XMIResourceFactoryImpl());

        ResourceSet resSet = new ResourceSetImpl();
        resource = resSet.createResource(URI.createURI("heterogene.resource"));

        //resource.getContents().add(graph);
        //resource.getContents().add(vertex);
        resource.getContents().add(model);
    }

    //@BeforeAll
    static void setUp() {
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


    static void initializeWidepread() throws Exception {
        WidespreadPackage pack = WidespreadPackage.eINSTANCE;
        pack.eClass();
        WidespreadFactory factory = pack.getWidespreadFactory();
        MultivaluedAttributes one = factory.createMultivaluedAttributes();
        for (int i = 0; i < 20; i++) {
            one.getMultivaluedInt().add(i);
        }

        MultivaluedAttributes two = factory.createMultivaluedAttributes();
        two.getMultivaluedString().add("AAaaaaaaaaaAAAAAAAAaaaaaaaaaaaAAAAAAAAaaaaaaaAAAAAAAaaaaa");

        MultivaluedAttributes three = factory.createMultivaluedAttributes();
        three.getMultivaluedDouble().add(Double.MIN_VALUE);
        three.getMultivaluedDouble().add(Double.MIN_VALUE);
        three.getMultivaluedDouble().add((double) 0);

        PrimitiveAttributes primitive = factory.createPrimitiveAttributes();
        primitive.setAFloat(1.0f);

        References ref = factory.createReferences();
        ref.setSingleReference(primitive);
        ref.getContainmentMultipleReference().add(primitive);

        ResourceSet rset = new ResourceSetImpl();


        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("xmi", new XMIResourceFactoryImpl());
        m.put("bin", new ResourceFactoryImpl() {
            @Override
            public Resource createResource(URI uri) {
                return new BinaryResourceImpl(uri);
            }
        });


        resource = rset.createResource(URI.createURI("widespread.bin"));

        Resource otherResource = rset.createResource(URI.createURI("widespread.xmi"));

        /*
        resource.getContents().add(one);
        resource.getContents().add(two);
        resource.getContents().add(three);
        resource.getContents().add(primitive);
        */

        Folder folder_a = factory.createFolder();
        Folder folder_b = factory.createFolder();
        Folder folder_c = factory.createFolder();

        folder_a.setName("A");
        folder_b.setName("B");
        folder_c.setName("C");

        folder_a.getContents().add(folder_b);
        folder_b.getContents().add(folder_c);

        resource.getContents().add(folder_c);
        otherResource.getContents().add(folder_a);

        resource.save(Collections.emptyMap());
        otherResource.save(Collections.emptyMap());
    }

    //@Test
    void testSize() {
        Set<EPackage> packages = new HashSet<>();
        int size = 0;
        TreeIterator<EObject> iterator = resource.getAllContents();
        while (iterator.hasNext()) {
            EObject eObject = iterator.next();
            packages.add(eObject.eClass().getEPackage());
            size++;
        }
        System.out.println("Packages: " + packages);
        System.out.println("Size: " + size);
    }

    //@Test
    void other() {
        Main binaryWriter = new Main(resource);
        binaryWriter.initialize();
    }
}