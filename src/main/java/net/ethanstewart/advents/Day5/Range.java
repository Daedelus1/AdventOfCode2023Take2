package net.ethanstewart.advents.Day5;

public record Range(long destination, long startIncl, long endIncl) {

    static Range parseRange(String rangeMapperString) {
        String[] items = rangeMapperString.split("\\s+");
        long start = Long.parseLong(items[1]);
        return new Range(Long.parseLong(items[0]), start, start + Long.parseLong(items[2]));
    }

    long convert(long index) {
        return destination + (index - startIncl);
    }

    Range inverse() {
        return new Range(startIncl, destination, destination + (endIncl - startIncl));
    }

    boolean isInRange(long num) {
        return num >= startIncl && num <= endIncl;
    }


}
