package org.atlanmod.appa.zeroconf.builders;

public interface SetApplicationProtocol {

    IPublish http();
    IPublish airplay();
    IPublish application(String str);
}
