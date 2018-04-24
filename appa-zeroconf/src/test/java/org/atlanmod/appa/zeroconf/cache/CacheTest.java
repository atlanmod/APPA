package org.atlanmod.appa.zeroconf.cache;

import org.atlanmod.appa.zeroconf.io.UnsignedInt;
import org.atlanmod.appa.zeroconf.mdns.ARecord;
import org.atlanmod.appa.zeroconf.mdns.NameArray;
import org.atlanmod.appa.zeroconf.mdns.QClass;
import org.atlanmod.appa.zeroconf.names.HostName;
import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    @Test
    void updateFrom() throws UnknownHostException, ParseException {
        NameArray names = NameArray.fromList("MacBook", "local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{72, 16, 8, 4});
        ARecord record = ARecord.createRecord(names, QClass.IN, UnsignedInt.fromInt(10), address);

        Cache cache = new Cache();
        cache.updateFrom(record);

        assertTrue(cache.getAddresses(HostName.fromNameArray(names)).contains(address));

    }

    @Test
    void getAddresses() {
    }
}