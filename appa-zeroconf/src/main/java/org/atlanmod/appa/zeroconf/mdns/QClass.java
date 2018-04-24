package org.atlanmod.appa.zeroconf.mdns;

import org.atlanmod.appa.zeroconf.io.ByteArrayBuffer;

import java.text.ParseException;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

/**
 * The CLASS of resource records being requested e.g. Internet, CHAOS etc.
 * These values are assigned by IANA and a complete list of values may be obtained from them.
 *
 * As noted in [RFC6762], Multicast DNS can only carry DNS records with classes in the
 * range 0-32767.
 * Classes in the range 32768 to 65535 are incompatible with Multicast DNS.
 */
public enum QClass {


    /**
     * Internet
     * @see <a href="https://tools.ietf.org/html/rfc1035">DOMAIN NAMES - IMPLEMENTATION AND SPECIFICATION</a>
     */
    IN(1, "in"),

    /**
     * CSNET (Obsolete)
     */
    CS(2, "cs"),

    /**
     * CHAOS
     * [D. Moon, "Chaosnet", A.I. Memo 628,
     * Massachusetts Institute of Technology Artificial Intelligence Laboratory, June 1981.]
     */
    CH(3, "ch"),

    /**
     * Hesiod
     * [Dyer, S., and F. Hsu, "Hesiod", Project Athena Technical Plan - Name Service, April 1987.]
     */
    HS(4, "hs"),

    /**
     * None
     * @see <a href="https://tools.ietf.org/html/rfc2136">Dynamic Updates in the Domain Name System (DNS UPDATE)</a>
     */
    None(254, "None"),

    /**
     * Any Class
     *
     * @see <a href="https://tools.ietf.org/html/rfc1035">DOMAIN NAMES - IMPLEMENTATION AND SPECIFICATION</a>
     */
    Any(255, "any");

    private static final int CLASS_MASK   = 0x7FFF;
    private int code;
    private String label;

    QClass(int value, String str) {
        this.code = value;
        this.label = str;
    }

    @Override
    public String toString() {
        return "QClass{" +
                "code=" + code +
                ", label='" + label + '\'' +
                '}';
    }

    private final static Map<Integer, QClass> map = stream(QClass.values())
            .collect(toMap(each -> each.code, each -> each));

    public static Optional<QClass> fromCode(int code) {
        return Optional.ofNullable(map.get(code));
    }

    public static QClass fromByteBuffer(ByteArrayBuffer buffer) throws ParseException {
        int code = buffer.getUnsignedShort().toInt();
        code = code & CLASS_MASK;

        Optional<QClass> type = fromCode(code);
        if (! type.isPresent()) {
            throw new ParseException("Parsing error when reading Question Class. Unknown code: " + code, buffer.position());
        } else {
            return type.get();
        }
    }
}