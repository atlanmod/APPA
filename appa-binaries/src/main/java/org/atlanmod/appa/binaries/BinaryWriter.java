package org.atlanmod.appa.binaries;

import org.atlanmod.appa.io.ByteArrayWriter;
import org.atlanmod.commons.log.Log;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import java.nio.ByteBuffer;

public class BinaryWriter {
    private final WriterMetadata metadata;
    private Resource resource;

    public BinaryWriter(Resource resource, ByteBuffer buffer) {
        this.resource = resource;
        this.metadata = new WriterMetadata(new ByteArrayWriter(buffer));
    }

    public void save() {

        this.extractMetadata();
        this.writeHeader();
        this.writeEPackages();
        this.writeEObjects();
        this.writeEFeatures();
        //
    }

    private void extractMetadata() {
        TreeIterator<EObject> it = resource.getAllContents();
        int size = 0;
        while (it.hasNext()) {
            size++;
            EObject eObject = it.next();
            metadata.register(eObject);
        }
        Log.info("Found " + size + " EObjects");
    }

    private void writeHeader() {
        Log.info("Writting Header");
        metadata.writeHeader();

    }

    private void writeEPackages() {
        Log.info("Writting EPackages");
        metadata.writeEPackages();
    }

    private void writeEObjects() {
        Log.info("Writting EObjects");
        metadata.writeEObjects();
    }

    private void writeEFeatures() {
        Log.info("Writting Features");
        TreeIterator<EObject> it = resource.getAllContents();
        while (it.hasNext()) {
            metadata.writeEFeatures(it.next());
        }
    }

}
