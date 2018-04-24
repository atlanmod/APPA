package org.atlanmod.appa.zeroconf.builders;

public interface IPublish {

    void publish();

    IPublish subtype(String str);

    IPublish persistent();
}
