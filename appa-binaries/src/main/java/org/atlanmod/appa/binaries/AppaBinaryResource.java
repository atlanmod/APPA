package org.atlanmod.appa.binaries;


import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class AppaBinaryResource extends ResourceImpl {

    public AppaBinaryResource() {
        super();
    }

    public AppaBinaryResource(URI uri) {
        super(uri);
    }

    @Override
    protected void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {

    }

    protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {

    }

}
