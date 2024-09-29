package net.ethanstewart;

public final class BinaryLogic {
    /**
     * @param seed      the number to read
     * @param startIncl the starting bit index H <- L
     * @param endExcl   the ending bit index H <- L
     * @return the bit shifted bit-range
     */
    public static byte getBitRange(byte seed, byte startIncl, byte endExcl) {
        return (byte) ((seed & ((1 << (endExcl - startIncl)) - 1) << startIncl) >> startIncl);
    }

    /**
     * @param seed        the number to alter
     * @param replacement the field that replaces the range
     * @param startIncl   the starting bit index of the range H <- L
     * @param endIncl     the starting bit index of the range H <- L
     * @return the altered number
     */
    public static byte setBitRange(byte seed, byte replacement, byte startIncl, byte endIncl) {
        return (byte) ((seed & ~(((1 << (endIncl - startIncl)) - 1) << startIncl)) | ((replacement & ((1 << (endIncl - startIncl)) - 1)) << startIncl));
    }

    /**
     * @param seed  the seed number
     * @param index the index of the bit to set
     * @return the seed index with the specified bit set
     */
    public static byte setBit(byte seed, byte index) {
        return (byte) (seed | (1 << index));
    }

    /**
     * @param seed  the seed number
     * @param index the index of the bit to reset
     * @return the seed index with the specified bit reset
     */
    public static byte resetBit(byte seed, byte index) {
        return (byte) (seed & ~(1 << index));
    }

    /**
     * @param seed      the number to read
     * @param startIncl the starting bit index H <- L
     * @param endExcl   the ending bit index H <- L
     * @return the bit shifted bit-range
     */
    public static int getBitRange(int seed, int startIncl, int endExcl) {
        return ((seed & ((1 << (endExcl - startIncl)) - 1) << startIncl) >> startIncl);
    }

    /**
     * @param seed        the number to alter
     * @param replacement the field that replaces the range
     * @param startIncl   the starting bit index of the range H <- L
     * @param endIncl     the starting bit index of the range H <- L
     * @return the altered number
     */
    public static int setBitRange(int seed, int replacement, int startIncl, int endIncl) {
        return ((seed & ~(((1 << (endIncl - startIncl)) - 1) << startIncl)) | ((replacement & ((1 << (endIncl - startIncl)) - 1)) << startIncl));
    }

    /**
     * @param seed  the seed number
     * @param index the index of the bit to set
     * @return the seed index with the specified bit set
     */
    public static int setBit(int seed, int index) {
        return seed | (1 << index);
    }

    /**
     * @param seed  the seed number
     * @param index the index of the bit to reset
     * @return the seed index with the specified bit reset
     */
    public static int resetBit(int seed, int index) {
        return seed & ~(1 << index);
    }

    /**
     * @param seed  the seed number
     * @param index the index of the bit to set
     * @return the seed index with the specified bit set
     */
    public static long setBit(long seed, int index) {
        return seed | (1L << index);
    }

    /**
     * @param seed  the seed number
     * @param index the index of the bit to reset
     * @return the seed index with the specified bit reset
     */
    public static long resetBit(long seed, int index) {
        return seed & ~(1L << index);
    }

    public static boolean getBit(long seed, int index) {
        return ((seed & (1L << index)) != 0);
    }

    public static boolean getBit(int seed, int index) {
        return ((seed & (1 << index)) != 0);
    }


    /**
     * @param seed        the number to alter
     * @param replacement the field that replaces the range
     * @param start       the starting bit index of the range H <- L
     * @param end         the starting bit index of the range H <- L
     * @return the altered number
     */
    public static long setBitRange(long seed, int replacement, int start, int end) {
        return ((seed & ~(((1L << (end - start)) - 1) << start)) | ((replacement & ((1L << (end - start)) - 1)) << start));
    }


}
