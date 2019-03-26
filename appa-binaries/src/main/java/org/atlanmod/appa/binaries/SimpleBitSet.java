package org.atlanmod.appa.binaries;

import org.atlanmod.commons.Preconditions;

import java.util.Arrays;

/**
 * Simple replacement for Java default BitSet for serialization purposes.
 * The main difference is that the size of the exported byte array is predictable.
 *
 *
 * @ java.util.BitSet
 */
public class SimpleBitSet {
    private final static int ADDRESS_BITS_PER_WORD = 6;
    private long[] words;
    private transient int wordsInUse = 0;


    public SimpleBitSet(int size) {
        Preconditions.checkGreaterThanOrEqualTo(size, 0);

        final int wordsLength = wordIndex(size - 1) + 1;
        words = new long[wordsLength];
    }

    public void set(int bitIndex) {
        Preconditions.checkGreaterThanOrEqualTo(bitIndex, 0);

        int wordIndex = wordIndex(bitIndex);
        expandTo(wordIndex);

        words[wordIndex] |= (1L << bitIndex); // Restores invariants

        checkInvariants();
    }

    public boolean get(int bitIndex) {
        Preconditions.checkGreaterThanOrEqualTo(bitIndex, 0);

        checkInvariants();

        int wordIndex = wordIndex(bitIndex);
        return (wordIndex < wordsInUse)
                && ((words[wordIndex] & (1L << bitIndex)) != 0);
    }

    private void expandTo(int wordIndex) {
        int wordsRequired = wordIndex+1;
        if (wordsInUse < wordsRequired) {
            ensureCapacity(wordsRequired);
            wordsInUse = wordsRequired;
        }
    }

    /**
     * Every public method must preserve these invariants.
     */
    private void checkInvariants() {
        assert(wordsInUse == 0 || words[wordsInUse - 1] != 0);
        assert(wordsInUse >= 0 && wordsInUse <= words.length);
        assert(wordsInUse == words.length || words[wordsInUse] == 0);
    }

    /**
     * Given a bit index, return word index containing it.
     */
    private static int wordIndex(int bitIndex) {
        return bitIndex >> ADDRESS_BITS_PER_WORD;
    }

    /**
     * Ensures that the BitSet can hold enough words.
     * @param wordsRequired the minimum acceptable number of words.
     */
    private void ensureCapacity(int wordsRequired) {
        if (words.length < wordsRequired) {
            // Allocate larger of doubled size or required size
            int request = Math.max(2 * words.length, wordsRequired);
            words = Arrays.copyOf(words, request);
        }
    }
}