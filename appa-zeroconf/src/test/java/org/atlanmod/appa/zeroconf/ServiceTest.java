/**
 *
 */
package org.atlanmod.appa.zeroconf;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.atlanmod.appa.zeroconf.QualifiedName.Fields;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ServiceTest {

    @Test
    public void testDecodeQualifiedNameMap() {
        String domain = "test.com";
        String protocol = "udp";
        String application = "ftp";
        String name = "My Service";
        String subtype = "printer";

        String type = "_" + application + "._" + protocol + "." + domain + ".";


        /*

        Map<Fields, String> map = Service.decodeQualifiedNameMap(type, names, subtype);

        assertEquals( domain, map.get(QualifiedName.Fields.Domain),"We did not get the right domain:");
        assertEquals( protocol, map.get(QualifiedName.Fields.Protocol),"We did not get the right protocol:");
        assertEquals( application, map.get(QualifiedName.Fields.Application),"We did not get the right application:");
        assertEquals( names, map.get(QualifiedName.Fields.Instance),"We did not get the right names:");
        assertEquals( subtype, map.get(QualifiedName.Fields.Subtype),"We did not get the right subtype:");

        */
    }

    @Test
    public void testDecodeQualifiedNameMapDefaults() {
        String domain = "local";
        String protocol = "tcp";
        String application = "ftp";
        String name = "My Service";
        String subtype = "";

        /*
        Map<Fields, String> map = ServiceInfoImpl.decodeQualifiedNameMap(application, names, subtype);

        assertEquals( domain, map.get(QualifiedName.Fields.Domain),"We did not get the right domain:");
        assertEquals( protocol, map.get(QualifiedName.Fields.Protocol),"We did not get the right protocol:");
        assertEquals( application, map.get(QualifiedName.Fields.Application),"We did not get the right application:");
        assertEquals( names, map.get(QualifiedName.Fields.Instance),"We did not get the right names:");
        assertEquals( subtype, map.get(QualifiedName.Fields.Subtype),"We did not get the right subtype:");
        */
    }


    /*
    @Test
    public void testDecodeServiceType() {
        String type = "_home-sharing._tcp.local.";

        Map<Fields, String> map = ServiceInfoImpl.decodeQualifiedNameMapForType(type);

        assertEquals( "local", map.get(QualifiedName.Fields.Domain),"We did not get the right domain:");
        assertEquals( "tcp", map.get(QualifiedName.Fields.Protocol),"We did not get the right protocol:");
        assertEquals( "home-sharing", map.get(QualifiedName.Fields.Application),"We did not get the right application:");
        assertEquals( "", map.get(QualifiedName.Fields.Instance),"We did not get the right names:");
        assertEquals( "", map.get(QualifiedName.Fields.Subtype),"We did not get the right subtype:");



    }

    @Test
    public void testDecodeServiceWithUnderscoreType() {
        String type = "_x_lumenera_mjpeg1._udp.local.";

        Map<Fields, String> map = ServiceInfoImpl.decodeQualifiedNameMapForType(type);

        assertEquals( "local", map.get(QualifiedName.Fields.Domain),"We did not get the right domain:");
        assertEquals( "udp", map.get(QualifiedName.Fields.Protocol),"We did not get the right protocol:");
        assertEquals( "x_lumenera_mjpeg1", map.get(QualifiedName.Fields.Application),"We did not get the right application:");
        assertEquals( "", map.get(QualifiedName.Fields.Instance),"We did not get the right names:");
        assertEquals( "", map.get(QualifiedName.Fields.Subtype),"We did not get the right subtype:");


    }

    @Test
    public void testDecodeServiceTCPType() {
        String type = "_afpovertcp._tcp.local.";

        Map<Fields, String> map = ServiceInfoImpl.decodeQualifiedNameMapForType(type);

        assertEquals( "local", map.get(QualifiedName.Fields.Domain),"We did not get the right domain:");
        assertEquals( "tcp", map.get(QualifiedName.Fields.Protocol),"We did not get the right protocol:");
        assertEquals( "afpovertcp", map.get(QualifiedName.Fields.Application),"We did not get the right application:");
        assertEquals( "", map.get(QualifiedName.Fields.Instance),"We did not get the right names:");
        assertEquals( "", map.get(QualifiedName.Fields.Subtype),"We did not get the right subtype:");
    }

    @Test
    public void testDecodeServiceTypeWithSubType() {
        String type = "_00000000-0b44-f234-48c8-071c565644b3._sub._home-sharing._tcp.local.";

        Map<Fields, String> map = ServiceInfoImpl.decodeQualifiedNameMapForType(type);

        assertEquals( "local", map.get(QualifiedName.Fields.Domain),"We did not get the right domain:");
        assertEquals( "tcp", map.get(QualifiedName.Fields.Protocol),"We did not get the right protocol:");
        assertEquals( "home-sharing", map.get(QualifiedName.Fields.Application),"We did not get the right application:");
        assertEquals( "", map.get(QualifiedName.Fields.Instance),"We did not get the right names:");
        assertEquals( "00000000-0b44-f234-48c8-071c565644b3", map.get(QualifiedName.Fields.Subtype),"We did not get the right subtype:");
    }

    @Test
    public void testDecodeServiceName() {
        String type = "My New Itunes Service._home-sharing._tcp.local.";

        Map<Fields, String> map = ServiceInfoImpl.decodeQualifiedNameMapForType(type);

        assertEquals( "local", map.get(QualifiedName.Fields.Domain),"We did not get the right domain:");
        assertEquals( "tcp", map.get(QualifiedName.Fields.Protocol),"We did not get the right protocol:");
        assertEquals( "home-sharing", map.get(QualifiedName.Fields.Application),"We did not get the right application:");
        assertEquals( "My New Itunes Service", map.get(QualifiedName.Fields.Instance),"We did not get the right names:");
        assertEquals( "", map.get(QualifiedName.Fields.Subtype),"We did not get the right subtype:");
    }

    @Test
    public void testDecodeServiceNameWithSpecialCharacter() {
        String type = "&test._home-sharing._tcp.local.";

        Map<Fields, String> map = ServiceInfoImpl.decodeQualifiedNameMapForType(type);

        assertEquals( "local", map.get(QualifiedName.Fields.Domain),"We did not get the right domain:");
        assertEquals( "tcp", map.get(QualifiedName.Fields.Protocol),"We did not get the right protocol:");
        assertEquals( "home-sharing", map.get(QualifiedName.Fields.Application),"We did not get the right application:");
        assertEquals( "&test", map.get(QualifiedName.Fields.Instance),"We did not get the right names:");
        assertEquals( "", map.get(QualifiedName.Fields.Subtype),"We did not get the right subtype:");
    }

    @Test
    public void testDecodeDNSMetaQuery() {
        String type = "_services._dns-sd._udp.local.";

        Map<Fields, String> map = ServiceInfoImpl.decodeQualifiedNameMapForType(type);

        assertEquals( "local", map.get(QualifiedName.Fields.Domain),"We did not get the right domain:");
        assertEquals( "udp", map.get(QualifiedName.Fields.Protocol),"We did not get the right protocol:");
        assertEquals( "dns-sd", map.get(QualifiedName.Fields.Application),"We did not get the right application:");
        assertEquals( "_services", map.get(QualifiedName.Fields.Instance),"We did not get the right names:");
        assertEquals( "", map.get(QualifiedName.Fields.Subtype),"We did not get the right subtype:");
    }

    @Test
    public void testReverseDNSQuery() {
        String type = "100.50.168.192.in-addr.arpa.";

        Map<Fields, String> map = ServiceInfoImpl.decodeQualifiedNameMapForType(type);

        assertEquals( "in-addr.arpa", map.get(QualifiedName.Fields.Domain), "We did not get the right domain:");
        assertEquals( "", map.get(QualifiedName.Fields.Protocol), "We did not get the right protocol:");
        assertEquals( "", map.get(QualifiedName.Fields.Application),"We did not get the right application:");
        assertEquals( "100.50.168.192", map.get(QualifiedName.Fields.Instance),"We did not get the right names:");
        assertEquals("", map.get(QualifiedName.Fields.Subtype),"We did not get the right subtype:");
    }

    @Test
    public void testAddress() {
        String type = "panoramix.local.";

        Map<Fields, String> map = ServiceInfoImpl.decodeQualifiedNameMapForType(type);

        assertEquals( "local", map.get(QualifiedName.Fields.Domain),"We did not get the right domain:");
        assertEquals( "", map.get(QualifiedName.Fields.Protocol),"We did not get the right protocol:");
        assertEquals( "", map.get(QualifiedName.Fields.Application),"We did not get the right application:");
        assertEquals( "panoramix", map.get(QualifiedName.Fields.Instance),"We did not get the right names:");
        assertEquals( "", map.get(QualifiedName.Fields.Subtype),"We did not get the right subtype:");
    }

    @Test
    public void testCasePreserving() {
        String type = "My New Itunes Service._Home-Sharing._TCP.Panoramix.local.";

        Map<Fields, String> map = ServiceInfoImpl.decodeQualifiedNameMapForType(type);

        assertEquals( "Panoramix.local", map.get(QualifiedName.Fields.Domain),"We did not get the right domain:");
        assertEquals( "TCP", map.get(QualifiedName.Fields.Protocol),"We did not get the right protocol:");
        assertEquals( "Home-Sharing", map.get(QualifiedName.Fields.Application),"We did not get the right application:");
        assertEquals( "My New Itunes Service", map.get(QualifiedName.Fields.Instance), "We did not get the right names:");
        assertEquals( "", map.get(QualifiedName.Fields.Subtype),"We did not get the right subtype:");
    }

    @Test
    public void testDecodeServiceTypeMissingDomain() {
        String type = "myservice._ftp._tcp.";

        Map<Fields, String> map = ServiceInfoImpl.decodeQualifiedNameMapForType(type);

        assertEquals( "", map.get(QualifiedName.Fields.Domain),"We did not get the right domain:");
        assertEquals( "tcp", map.get(QualifiedName.Fields.Protocol),"We did not get the right protocol:");
        assertEquals( "ftp", map.get(QualifiedName.Fields.Application),"We did not get the right application:");
        assertEquals( "myservice", map.get(QualifiedName.Fields.Instance),"We did not get the right names:");
        assertEquals( "", map.get(QualifiedName.Fields.Subtype),"We did not get the right subtype:");

    }

    @Test
    public void testEncodeDecodeProperties() {

        String service_type = "_ros-master._tcp.local.";
        String service_name = "RosMaster";
        int service_port = 8888;
        String service_key = "description"; // Max 9 chars
        String service_text = "Hypothetical ros master";
        Map<String, byte[]> properties = new HashMap<String, byte[]>();
        properties.put(service_key, service_text.getBytes());
        ServiceInfo service_info = null;

        //service_info = ServiceInfo.create(service_type, service_name, service_port, ""); // case 1, no text
        service_info = ServiceInfo.builder()
                .type(service_type)
                .names(service_name)
                .port(service_port)
                .text("")
                .build();
        assertEquals( null, service_info.getPropertyString(service_key),"We should have got the same properties (Empty):");

        //service_info = ServiceInfo.create(service_type, service_name, service_port, 0, 0, true,service_key + "=" + service_text); // case 2, textual description
        service_info = ServiceInfo.builder()
                .type(service_type)
                .names(service_name)
                .port(service_port)
                .text(service_key + "=" + service_text)
                .persistent()
                .build();
        assertEquals( service_text, service_info.getPropertyString(service_key),"We should have got the same properties (String):");


        //service_info = ServiceInfo.create(service_type, service_name, service_port, 0, 0, true, properties); // case 3, properties assigned textual description
        service_info = ServiceInfo.builder()
                .type(service_type)
                .names(service_name)
                .port(service_port)
                .persistent()
                .properties(properties)
                .build();
        assertEquals( service_text, service_info.getPropertyString(service_key),"We should have got the same properties (Map):");

    }

    @Test
    public void testDecodePropertiesWithoutEqualsSign() {
        String service_type = "_ros-master._tcp.local.";
        String service_name = "RosMaster";
        int service_port = 8888;
        ServiceInfo service_info = null;
        // Represents TXT records "a" "b=c"
        byte[] txt = {1, 97, 3, 98, 61, 99};
        //service_info = ServiceInfo.create(service_type, service_name, service_port, 0, 0, txt);
        service_info = ServiceInfo.builder()
                .type(service_type)
                .names(service_name)
                .port(service_port)
                .text(txt)
                .build();

        Set<String> expectedKeys = new HashSet<String>();
        expectedKeys.add("a");
        expectedKeys.add("b");

        Enumeration<String> enumeration = service_info.getPropertyNames();
        Set<String> keys = new HashSet<String>();
        while (enumeration.hasMoreElements()) {
            keys.add(enumeration.nextElement());
        }
        assertEquals(expectedKeys, keys);
        assertEquals("c", service_info.getPropertyString("b"));
    }

    @Test
    void testGetType() {

        ServiceInfo service = ServiceInfo.builder()
                .application("master")
                .protocol("tcp")
                .domain("local")
                .build();

        assertEquals("_master._tcp.local.", service.getType());
    }

    @Test
    void testGetTypeBis() {

        ServiceInfo service = ServiceInfo.builder()
                .type("_master._tcp.local.")
                .build();

        assertEquals("_master._tcp.local.", service.getType());
    }

    */

}
