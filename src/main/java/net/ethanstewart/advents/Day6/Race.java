package net.ethanstewart.advents.Day6;

import com.google.common.collect.ImmutableSet;

import java.util.stream.IntStream;

public record Race(long totalTimeMS, long recordDistanceMM) {
    private static final double EPSILON = .00001;

    public static ImmutableSet<Race> parseRaces(String racesString) {
        String[] timeRow = racesString.trim().split("\\n")[0].split("\\s+");
        String[] distanceRow = racesString.trim().split("\\n")[1].split("\\s+");
        return IntStream.range(0, distanceRow.length).skip(1)
                .mapToObj(n -> new Race(Long.parseLong(timeRow[n]), Long.parseLong(distanceRow[n])))
                .collect(ImmutableSet.toImmutableSet());
    }

    public static Race parseRacePart2(String raceString) {
        return new Race(Long.parseLong(raceString.trim().split("\\n")[0].split(":")[1].replaceAll("\\s", "")),
                Long.parseLong(raceString.trim().split("\\n")[1].split(":")[1].replaceAll("\\s", "")));
    }

    public double optimalPressingTimeMS() {
        return totalTimeMS / 2.0;
    }

    private double maxPressingTimeToBeatRecordMS() {
        return (totalTimeMS + Math.sqrt((totalTimeMS * totalTimeMS) - (4 * recordDistanceMM))) / 2;
    }

    private double minPressingTimeToBeatRecordMS() {
        return (totalTimeMS - Math.sqrt((totalTimeMS * totalTimeMS) - (4 * recordDistanceMM))) / 2;
    }

    public long numIntegerPressingTimesThatBeatRecord() {
        return (long) (maxPressingTimeToBeatRecordMS() - EPSILON)
                - (long) Math.ceil(minPressingTimeToBeatRecordMS() + EPSILON) + 1;
    }
}
