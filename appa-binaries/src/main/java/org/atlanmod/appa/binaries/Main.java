package org.atlanmod.appa.binaries;

import org.atlanmod.appa.io.ByteArrayWriter;
import org.atlanmod.graph.Edge;
import org.atlanmod.graph.Graph;
import org.atlanmod.graph.GraphFactory;
import org.atlanmod.graph.GraphPackage;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.omg.smm.SmmFactory;
import org.omg.smm.SmmModel;
import org.omg.smm.SmmPackage;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private final Resource resource;
    private Map<EObject, Integer> idMap = new HashMap<>();


    public Main(Resource resource) {
        this.resource = resource;
    }

    public static void main(String[] args) throws IOException {

        GraphFactory graphFactory = GraphPackage.eINSTANCE.getGraphFactory();
        Graph graph = graphFactory.createGraph();
        Edge edge = graphFactory.createEdge();
        graph.getEdges().add(edge);

        SmmFactory smmFactory = SmmPackage.eINSTANCE.getSmmFactory();
        SmmModel model = smmFactory.createSmmModel();
        model.setName("A model");

        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("res", new XMIResourceFactoryImpl());

        ResourceSet resSet = new ResourceSetImpl();
        Resource res = resSet.createResource(URI.createURI("heterogene.res"));

        res.getContents().add(graph);
        res.getContents().add(model);

        Resource.Internal internal = (Resource.Internal) res;
        EPackage.Registry registry = resSet.getPackageRegistry();
        //System.out.println(resSet.   getURIResourceMap().size());

        res.save(Collections.EMPTY_MAP);


        Main bw = new Main(res);
        bw.initialize();


        /*
        for (EObject each: res.getContents()) {
            String prefix = each.eClass().getEPackage().getNsPrefix();
            System.out.println(prefix);
            System.out.println(resSet.getPackageRegistry().getEPackage(prefix));

            EPackage paquage = each.eClass().getEPackage();
            System.out.println("# Classifiers: " + paquage.getEClassifiers().size());
            for (EClassifier klass : paquage.getEClassifiers()) {
                EClassImpl eci = (EClassImpl) klass;
                for (EAttribute attr :eci.getEAllAttributes()) {
                    System.out.println(attr.getName());

                }

                System.out.println("Id: " + klass.getClassifierID() + " Name: " + klass.getInstanceClassName());
            }
        }

        //ExtendedMetaData modelMetaData = new BasicExtendedMetaData(resSet.getPackageRegistry());
        System.out.println(registry.size());
        for (String each : registry.keySet()) {
            System.out.println("---");
            System.out.println(each);

        }
        */




        /*
        ResourceSet rset = new ResourceSetImpl();
        rset.getResourceFactoryRegistry().getExtensionToFactoryMap().put("bin", new ResourceFactoryImpl() {
            @Override
            public Resource createResource(URI uri) {
                return new BinaryResourceImpl(uri);
            }
        });

        URI uri = URI.createFileURI("mybinresource.bin");
        //Resource res = rset.getResource(uri);

        //Resource res = new BinaryResourceImpl(URI.createFileURI('mybinaryresource'));

        //res.load(null); // On peut passer des options dans une map si besoin
        */
    }

    public void initialize() {
        System.out.println("Size: " + resource.getContents().size());
        for (EObject each : resource.getContents()) {
            final EPackage ePackage = each.eClass().getEPackage();
            System.out.println("Package name: " + ePackage.getName());
            System.out.println("Package class: " + ePackage.getClass().getName());
            System.out.println("Contents size : " + each.eContents().size());
            System.out.println("EClassifiers size : " + ePackage.getEClassifiers().size());

            System.out.println("+ ----------------------------------------------------------------------");
            for (EClassifier c : ePackage.getEClassifiers()) {
                //System.out.println("Classifier Java class: " + c.getClass().getName());
                //this.initializeClassifier(c);
            }
            System.out.println("+ ----------------------------------------------------------------------");
        }

        int id = 0;
        TreeIterator<EObject> iterator = resource.getAllContents();
        while (iterator.hasNext()) {
            EObject eObject = iterator.next();
            idMap.put(eObject, Integer.valueOf(id));
            EClass eClass = eObject.eClass();
            System.out.println("KlassK NMAE: " + eClass.getName() + " Hash: " + eClass.hashCode());
            System.out.println("Java KlassK NMAE: " + eClass.getClass().getName());
            System.out.println("# Features: " + eClass.getFeatureCount());
            for (int i = 0; i < eClass.getFeatureCount(); i++) {
                EStructuralFeature feat = eClass.getEStructuralFeature(i);
                System.out.println("\t feature:" + feat.getName() + " id: " + feat.getFeatureID());
                System.out.println("\t isSet(): " + eObject.eIsSet(feat));

                if (feat instanceof EAttribute) {
                    EAttribute eAttribute = (EAttribute) feat;
                    EDataType eDataType = eAttribute.getEAttributeType();
                    System.out.println("|--------- Instance Class Name: " + eDataType.getInstanceClassName());
                    System.out.println("|--------- Instance Class: " + eDataType.getInstanceClass());
                    System.out.println("|--------- Instance Type Name: " + eDataType.getInstanceTypeName());
                    System.out.println("|--------- Name: " + eDataType.getName());
                    System.out.println("Class ID: " + eDataType.getClassifierID());
                    System.out.println("|--------- Factory: " + ((BinaryResourceImpl.DataConverter.Factory) eDataType.getEPackage()
                            .getEFactoryInstance()).create(eDataType));
                }

                if (feat instanceof EReference) {
                    EReference eReference = (EReference) feat;
                    System.out.println("|-------- ERefence name: " + eReference.getName());
                    System.out.println("|-------- ERefence isResolveProxies: " + eReference.isResolveProxies());

                }
            }
        }

        System.out.println("Map size: " + idMap.size());
    }

    void initializeClassifier(EClassifier eClassifier) {
        System.out.println("Classifier ID: " + eClassifier.getClassifierID());
        System.out.println("Classifier Name: " + eClassifier.getName() + " Hash: " + eClassifier.hashCode());
        System.out.println("Classifier Java class: " + eClassifier.getClass().getName());


        //System.out.println("Contents: " + eClassifier.eContents());


        System.out.println("eClass Name: " + eClassifier.eClass().getName());
        System.out.println("eClass Java class: " + eClassifier.eClass().getClass().getName());

        EClass eClass = eClassifier.eClass();

        System.out.println("# attributes: " + eClass.getEAllAttributes().size());
        System.out.println("# feature count: " + eClass.getFeatureCount());
        System.out.println("# references: " + eClass.getEAllReferences().size());

        for (int i = 0; i < eClass.getFeatureCount(); i++) {
            EStructuralFeature feature = eClass.getEStructuralFeature(i);
            System.out.println("feature id: " + feature.getFeatureID() + " name: " + feature.getName());
        }

        /*
        for (EAttribute attr: eClassifier.eClass().getEAllAttributes()) {
            System.out.println("\t Attr name: " + attr.getName() + " attr ID: " + attr.getFeatureID());
        }

        for (EReference ref: eClassifier.eClass().getEAllReferences()) {
            System.out.println("\t Ref name: " + ref.getName() + " attr ID: " + ref.getFeatureID());
        }
        */
    }

    public void convert(EObject eObject) {
        //ByteArrayWriter writer = new ByteArrayWriter();

    }
}
