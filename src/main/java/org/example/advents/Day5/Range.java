package org.example.advents.Day5;

public record Range(long destination, long startIncl, long endIncl) {
    
    public static Range parseRange(String rangeMapperString) {
        String[] items = rangeMapperString.split("\\s+");
        long start = Long.parseLong(items[1]);
        return new Range(Long.parseLong(items[0]), start, start + Long.parseLong(items[2]));
    }
    
    public long convert(long index) {
        return destination + (index - startIncl);
    }
    
    public Range inverse() {
        return new Range(startIncl, destination, destination + (endIncl - startIncl));
    }
    public boolean isInRange(long num) {
        return num >= startIncl && num <= endIncl;
    }
    
    public enum ReverseMode {
        STANDARD, REVERSE
    }
}
