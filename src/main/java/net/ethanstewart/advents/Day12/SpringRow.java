package net.ethanstewart.advents.Day12;

import com.google.common.collect.ImmutableList;
import net.ethanstewart.BinaryLogic;
import net.ethanstewart.data_structures.ImmutableBitmap;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.IntStream;

public record SpringRow(ImmutableBitmap stateIsNotKnownBitmap, ImmutableBitmap isBrokenBitmap,
                        ImmutableList<Integer> lengthsOfContiguousBrokenSprings, int numSprings) {

    public static SpringRow parseSpringRow(String rowString) {
        String[] parts = rowString.trim().split("\\s+");
        ImmutableList<Integer> lengths = Arrays.stream(parts[1].split(",")).map(Integer::parseInt).collect(ImmutableList.toImmutableList());
        return new SpringRow(
                ImmutableBitmap.create(parts[0].length(), i -> rowString.charAt(parts[0].length() - i - 1) == '?'),
                ImmutableBitmap.create(parts[0].length(), i -> rowString.charAt(parts[0].length() - i - 1) == '#'),
                lengths, parts[0].length());
    }

    public static SpringRow parseAndUnfoldSpringRow(String foldedRowString) {
        String[] parts = foldedRowString.trim().split("\\s+");
        parts[0] = parts[0] + "?" + parts[0] + "?" + parts[0] + "?" + parts[0] + "?" + parts[0];
        parts[1] = parts[1] + "," + parts[1] + "," + parts[1] + "," + parts[1] + "," + parts[1];
        System.out.println(foldedRowString);
        return new SpringRow(
                ImmutableBitmap.create(parts[0].length(), i -> parts[0].charAt(parts[0].length() - i - 1) == '?'),
                ImmutableBitmap.create(parts[0].length(), i -> parts[0].charAt(parts[0].length() - i - 1) == '#'),
                Arrays.stream(parts[1].split(",")).map(Integer::parseInt).collect(ImmutableList.toImmutableList()),
                parts[0].length());
    }

    private static boolean bitmapIsValid(ImmutableBitmap fullMap, int numSprings, ImmutableList<Integer> checksum) {
        int[] counts = new int[checksum.size()];
        int stage = 0;
        int count = 0;
        for (int i = numSprings - 1; i >= 0; i--) {
            if (fullMap.getBit(i)) {
                count++;
            } else if (count > 0) {
                if (stage >= checksum.size() || count != checksum.get(stage)) {
                    return false;
                }
                counts[stage] = count;
                stage++;
                count = 0;
            }
        }
        if (count > 0) {
            if (stage >= checksum.size()) {
                return false;
            }
            counts[stage] = count;
            stage++;
        }
        if (stage < checksum.size() - 1) {
            return false;
        }
        return checksum.get(checksum.size() - 1) == counts[checksum.size() - 1];
    }

    public long getPossibleArrangementCounts() {
        long count;
        long numUnknownSprings = IntStream.range(0, numSprings + 1).filter(stateIsNotKnownBitmap::getBit).count();
        count = IntStream.range(0, 1 << numUnknownSprings)
                .parallel()
                .filter(this::permutationIsValid)
                .count();
        System.out.println(count + " | " + this);
        return count;
    }

    boolean permutationIsValid(int index) {
        return bitmapIsValid(generateBitmap(index), numSprings, lengthsOfContiguousBrokenSprings);
    }

    private ImmutableBitmap generateBitmap(int index) {
        BigInteger data = isBrokenBitmap.data();
        int offset = 0;
        for (int i = 0; i + offset < numSprings; i++) {
            while (i + offset < numSprings && !stateIsNotKnownBitmap.getBit(i + offset)) {
                offset++;
            }
            if (BinaryLogic.getBit(index, i)) {
                data = data.setBit(i + offset);
            }
        }
//        System.out.println(new ImmutableBitmap(data) + " | " + bitmapIsValid(new ImmutableBitmap(data), numSprings, lengthsOfContiguousBrokenSprings));
        return new ImmutableBitmap(data);
    }
}
