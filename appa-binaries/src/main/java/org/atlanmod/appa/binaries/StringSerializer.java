package org.atlanmod.appa.binaries;

import org.atlanmod.appa.io.CompressedInts;
import org.atlanmod.commons.Preconditions;
import org.atlanmod.commons.log.Log;
import org.eclipse.emf.common.util.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class StringSerializer implements Function<Object, byte[]> {
    private List<String> segments = new ArrayList<>();
    private Map<String, Integer> segmentedStringToIDMap;
    private static final int MAX_DELIMITER = 0xC0;
    private static final String[] DELIMITERS = new String[MAX_DELIMITER];
    private Map<String, Integer> segmentToIDMap;
    private static final Map<String, Integer> INTRINSIC_STRING_TO_ID_MAP = new HashMap<String, Integer>();
    private static final List<String> INTRINSIC_STRINGS = new ArrayList<String>();

    static {
        INTRINSIC_STRING_TO_ID_MAP.put("", 0);
        INTRINSIC_STRINGS.add("");

        for (char c = 0; c < MAX_DELIMITER; ++c) {
            if (Character.isDigit(c)) {
                String string = CommonUtil.javaIntern(Character.toString(c));
                INTRINSIC_STRING_TO_ID_MAP.put(string, INTRINSIC_STRINGS.size());
                INTRINSIC_STRINGS.add(string);
            } else if (!Character.isLetter(c)) {
                String delimiter = CommonUtil.javaIntern(Character.toString(c));
                DELIMITERS[c] = delimiter;
                INTRINSIC_STRING_TO_ID_MAP.put(delimiter, INTRINSIC_STRINGS.size());
                INTRINSIC_STRINGS.add(delimiter);
            }
        }
    }


    public StringSerializer() {
        segmentedStringToIDMap = new HashMap<String, Integer>();
        segmentToIDMap = new HashMap<String, Integer>(INTRINSIC_STRING_TO_ID_MAP);
    }

    private void writeSegmentedString(List<Byte> bytes, String value)  {
        if (segmentedStringToIDMap == null) {

            writeString(bytes, value);
        } else if (value == null) {
            bytes.add((byte) -1);
        } else {
            Integer id = segmentedStringToIDMap.get(value);
            if (id == null) {
                int idValue = segmentedStringToIDMap.size();
                segmentedStringToIDMap.put(value, idValue);

                writeCompressedInt(bytes, idValue);

                int segmentCount = 0;
                int start = 0;
                int end = value.length();
                for (int i = 0; i < end; ++i) {
                    char c = value.charAt(i);
                    if (c < MAX_DELIMITER) {
                        String delimiter = DELIMITERS[c];
                        if (delimiter != null) {
                            if (start < i) {
                                segments.add(value.substring(start, i));
                            }
                            segments.add(delimiter);
                            start = i + 1;
                        }
                    }
                }
                if (start == 0 || segmentCount == 1 && start == end) {
                    writeCompressedInt(bytes,0);
                    writeString(bytes, value);
                } else {
                    if (start < end) {
                        segments.add(value.substring(start, end));
                    }

                    writeCompressedInt(bytes, segmentCount);
                    for (String each: segments) {
                        writeString(bytes, each);
                    }
                }
            } else {
                writeCompressedInt(bytes, id);
            }
        }
    }

    protected void writeString(List<Byte> bytes, String value) {
        Preconditions.checkNotNull(bytes);
        Preconditions.checkNotNull(segmentedStringToIDMap);
        Preconditions.checkNotNull(value);

        Integer id = segmentToIDMap.get(value);
        if (id != null) {
            this.writeCompressedInt(bytes, id);
            return;
        } else {
            int idValue = segmentToIDMap.size();
            segmentToIDMap.put(value, idValue);
            writeCompressedInt(bytes, idValue);
        }

        byte[] serialized = ECoreConverters.stringToBytes(value);
        for (byte each: serialized) {
            bytes.add(each);
        }
    }

    private void writeCompressedInt(List<Byte> bytes, int idValue) {
        byte[] serialized = CompressedInts.toBytes(idValue);
        for (byte each: serialized) {
            bytes.add(each);
        }
    }

    private void writeChar(List<Byte> bytes, int value)  {
        bytes.add((byte) (value >> 8 & 0xFF));
        bytes.add((byte) (value & 0xFF));
    }

    private void writeByte(List<Byte> bytes, byte value) {
        bytes.add(value);
    }

    @Override
    public byte[] apply(Object o) {
        List<Byte> bytes = new ArrayList<>();
        this.writeSegmentedString(bytes, (String)o);

        byte[] unboxed = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            unboxed[i] = bytes.get(i).byteValue();
        }

        return unboxed;
    }
}
