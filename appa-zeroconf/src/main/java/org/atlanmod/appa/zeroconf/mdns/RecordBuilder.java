package org.atlanmod.appa.zeroconf.mdns;

import org.atlanmod.appa.zeroconf.io.UnsignedInt;

public interface RecordBuilder<T> {

    T build(NameArray name, RecordType type, QClass qclass, UnsignedInt ttl);

}
