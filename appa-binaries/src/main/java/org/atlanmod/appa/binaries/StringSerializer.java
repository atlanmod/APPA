package org.atlanmod.appa.binaries;

import org.atlanmod.appa.io.CompressedInts;
import org.eclipse.emf.common.util.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class StringSerializer implements Function<Object, byte[]> {
    private String[] segments;
    private Map<String, Integer> segmentedStringToIDMap;
    protected static final int MAX_DELIMITER = 0xC0;
    protected static final String[] DELIMITERS = new String[MAX_DELIMITER];
    protected char[] characters;
    private Map<String, Integer> segmentToIDMap;
    protected static final Map<String, Integer> INTRINSIC_STRING_TO_ID_MAP = new HashMap<String, Integer>();
    static final List<String> INTRINSIC_STRINGS = new ArrayList<String>();

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
                            ensureSegmentCapacity(segmentCount + 2);
                            if (start < i) {
                                segments[segmentCount++] = value.substring(start, i);
                            }
                            segments[segmentCount++] = delimiter;
                            start = i + 1;
                        }
                    }
                }
                if (start == 0 || segmentCount == 1 && start == end) {
                    writeCompressedInt(bytes,0);
                    writeString(bytes, value);
                } else {
                    if (start < end) {
                        ensureSegmentCapacity(segmentCount + 1);
                        segments[segmentCount++] = value.substring(start, end);
                    }

                    writeCompressedInt(bytes, segmentCount);
                    for (int i = 0; i < segmentCount; ++i) {
                        writeString(bytes, segments[i]);
                    }
                }
            } else {
                writeCompressedInt(bytes, id);
            }
        }
    }

    private void writeString(List<Byte> bytes, String value) {
        if (value == null) {
            writeCompressedInt(bytes,-1);
        } else {
            if (segmentToIDMap != null) {
                Integer id = segmentToIDMap.get(value);
                if (id != null) {
                    writeCompressedInt(bytes, id);
                    return;
                } else {
                    int idValue = segmentToIDMap.size();
                    segmentToIDMap.put(value, idValue);
                    writeCompressedInt(bytes, idValue);
                }
            }

            int length = value.length();
            writeCompressedInt(bytes, length);
            if (characters == null || characters.length < length) {
                characters = new char[length];
            }
            value.getChars(0, length, characters, 0);
            LOOP: for (int i = 0; i < length; ++i) {
                char character = characters[i];
                if (character == 0 || character > 0xFF) {
                    writeByte(bytes, (byte) 0);
                    writeChar(bytes, character);
                    while (++i < length) {
                        writeChar(bytes, characters[i]);
                    }
                    break LOOP;
                } else {
                    writeByte(bytes, (byte) character);
                }
            }
        }
    }

    private void ensureSegmentCapacity(int capacity) {
        if (segments == null) {
            segments = new String[Math.max(20, capacity)];
        } else if (segments.length < capacity) {
            String[] newSegments = new String[Math.max(2 * segments.length, capacity)];
            System.arraycopy(segments, 0, newSegments, 0, segments.length);
            segments = newSegments;
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
