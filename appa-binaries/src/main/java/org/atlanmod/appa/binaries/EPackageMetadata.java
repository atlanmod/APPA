package org.atlanmod.appa.binaries;

import org.atlanmod.commons.collect.MoreArrays;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

class EPackageMetadata {

    private URI uri;
    private String nsURI;

    private EPackageMetadata(EPackage ePackage) {

        uri = EcoreUtil.getURI(ePackage);
        nsURI = ePackage.getNsURI();
    }

    public static EPackageMetadata create(EPackage ePackage) {
        return new EPackageMetadata(ePackage);
    }

    public byte[] toBytes() {

        byte[] uriBytes = ECoreConverters.stringToBytes(uri.toString());
        byte[] nsBytes = ECoreConverters.stringToBytes(nsURI);

        return MoreArrays.addAll(uriBytes, nsBytes);
    }
}
