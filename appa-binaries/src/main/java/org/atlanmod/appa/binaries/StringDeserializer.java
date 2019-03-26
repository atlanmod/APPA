package org.atlanmod.appa.binaries;

import org.atlanmod.appa.io.ByteArrayReader;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.CommonUtil;

import java.util.*;

public class StringDeserializer {

    private ByteArrayReader reader;

    private char[] characters;

    private final List<String> segmentedStringsList;
    private final List<String> segmentsList;
    private char[] builder;
    private static final int MAX_DELIMITER = 0xC0;
    private static final String[] DELIMITERS = new String[MAX_DELIMITER];
    private static final List<String> INTRINSIC_STRINGS = new ArrayList<String>();
    private static final Map<String, Integer> INTRINSIC_STRING_TO_ID_MAP = new HashMap<String, Integer>();

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

    public StringDeserializer() {
        segmentedStringsList = new ArrayList<>();
        segmentsList = new ArrayList<>();
        segmentsList.addAll(INTRINSIC_STRINGS);
        builder = new char[200];
    }

    /**
     * @since 2.9
     */
    private String readSegmentedString() {
        if (segmentedStringsList == null) {
            return basicReadString();
        } else {
            int id = readCompressedInt();
            if (id == -1) {
                return null;
            } else if (segmentedStringsList.size() <= id) {
                int segmentCount = readCompressedInt();
                String value;
                if (segmentCount == 0) {
                    value = readString();
                } else {
                    int length = 0;
                    for (int i = 0; i < segmentCount; ++i) {
                        String segment = readString();

                        int segmentLength = segment.length();
                        int newLength = length + segmentLength;
                        if (builder.length < newLength) {
                            char[] newBuilder = new char[Math.max(2 * builder.length, newLength)];
                            System.arraycopy(builder, 0, newBuilder, 0, builder.length);
                            builder = newBuilder;
                        }

                        if (segmentLength == 1) {
                            builder[length] = segment.charAt(0);
                        } else {
                            segment.getChars(0, segmentLength, builder, length);
                        }

                        length = newLength;
                    }
                    value = new String(builder, 0, length);
                }
                segmentedStringsList.add(value);
                return value;
            } else {
                return segmentedStringsList.get(id);
            }
        }
    }

    private String readString() {
        if (segmentsList != null) {
            int id = readCompressedInt();
            if (id == -1) {
                return null;
            } else if (segmentsList.size() <= id) {
                String value = basicReadString();
                segmentsList.add(value);
                return value;
            } else {
                return segmentsList.get(id);
            }
        } else {
            return basicReadString();
        }
    }

    private String basicReadString() {
        return reader.readString();
    }

    private static class StringList extends BasicEList<String> {
        private static final long serialVersionUID = 1L;

        public String[] strings;

        public StringList() {
            super(1000);
        }

        @Override
        protected Object[] newData(int capacity) {
            return strings = new String[capacity];
        }

        @Override
        public final boolean add(String object) {
            if (size == strings.length) {
                grow(size + 1);
            }
            strings[size++] = object;
            return true;
        }
    }

    private int readCompressedInt() {
        assert Objects.nonNull(reader);

        return reader.readCompressedInt();
    }

    private char readChar() {
        assert Objects.nonNull(reader);

        return (char) ((readByte() << 8) & 0xFF00 | readByte() & 0xFF);

    }

    private byte readByte() {
        assert Objects.nonNull(reader);

        return reader.readByte();
    }

    public String readString(ByteArrayReader reader) {
        this.reader = reader;
        return this.readSegmentedString();
    }
}
