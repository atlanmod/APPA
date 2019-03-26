package org.atlanmod.appa.binaries;

import org.atlanmod.appa.io.ByteArrayReader;
import org.atlanmod.commons.log.Log;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;

import java.nio.ByteBuffer;
import java.util.Objects;

public class BinaryReader {
    private ByteArrayReader reader;
    private Resource resource;
    private ReaderMetadata metadata = new ReaderMetadata();
    private int contentSize = 0;

    public BinaryReader(Resource resource, ByteBuffer buffer) {
        this.resource = resource;
        this.reader = new ByteArrayReader(buffer);
    }

    public void load() {
        this.readHeader();
        this.readEPackages();
        this.readEObjects();
        this.readEFeatures();
    }

    private void readHeader() {

    }

    private void readEPackages() {
        Log.info("Reading EPackages");
        EPackage.Registry registry = EPackage.Registry.INSTANCE;
        int size = reader.readInteger();
        for (int i = 0; i < size; i++) {
            String uri = reader.readString();
            String nsURI = reader.readString();
            EPackage ePackage = registry.getEPackage(nsURI);
            if (Objects.isNull(ePackage)){
                throw new RuntimeException("EPackage " + nsURI + " not found, resource could not be loaded");
            } else {
                metadata.register(ePackage);
                Log.info("Found EPackage: " + ePackage);
            }
        }
    }

    private void readEObjects() {
        Log.info("Reading EObjects");
        contentSize = reader.readCompressedInt();
        Log.info("Found " + contentSize + " EObjects");
        for (int i = 0; i < contentSize; i++) {
            Integer classifierId = Integer.valueOf(reader.readCompressedInt());
            WritableId id = new IntegerId(reader.readCompressedInt());
            Log.info("Class id: " + classifierId + " Object id: " + id);
            EObject eObject = metadata.instantiate(classifierId, id);
            resource.getContents().add(eObject);
        }
    }

    private void readEFeatures() {
        Log.info("Reading EFeatures");
        for (int i = 0; i < contentSize; i++) {
            metadata.readEFeatures(reader);
        }
    }
}
