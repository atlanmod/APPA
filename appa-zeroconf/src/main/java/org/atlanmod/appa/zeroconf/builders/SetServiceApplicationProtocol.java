package org.atlanmod.appa.zeroconf.builders;

public interface SetServiceApplicationProtocol {

    IQuery http();
    IQuery airplay();
    IQuery application(String str);
}
