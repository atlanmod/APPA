package org.atlanmod.appa.zeroconf.builders;

public interface SetServiceTransportProtocol {
    SetServiceApplicationProtocol udp();
    SetServiceApplicationProtocol tcp();
}
