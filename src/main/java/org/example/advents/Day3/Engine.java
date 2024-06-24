package org.example.advents.Day3;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import org.checkerframework.checker.units.qual.C;
import org.example.data_structures.Coordinate;
import org.example.data_structures.Dimension;
import org.example.data_structures.Matrix;
import org.example.data_structures.Region;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Function;

public class Engine extends Matrix<Tile> {
    
    public Engine(@NotNull Dimension matrixDimensions, @NotNull Function<Coordinate, Tile> CoordinateItemConverter) {
        super(matrixDimensions, CoordinateItemConverter);
    }
    
    public static Engine parseEngine(String engineString) {
        String adjustedString = engineString.trim().replaceAll("\\n+", "");
        int width = engineString.indexOf("\n");
        // checking that all lines are of the same length
        if (Arrays.stream(engineString.split("\\n")).mapToInt(String::length)
                .anyMatch(len -> len != width)) {
            throw new IllegalArgumentException("ENGINE HAS INCONSISTENT WIDTH");
        }
        int height = adjustedString.length() / width;
        return new Engine(new Dimension(width, height), coordinate ->
                Tile.parseTile(adjustedString.charAt(coordinate.toIndex(width))));
    }
    
    public ImmutableSet<Region> getAllNumberRegions() {
        ImmutableSet.Builder<Region> builder = ImmutableSet.builder();
        for (int row = 0; row < getMatrixDimensions().height(); row++) {
            boolean isReadingNumber = false;
            Coordinate start = null;
            for (int x = 0; x < getMatrixDimensions().width(); x++) {
                if (!isReadingNumber) {
                    if (getItemAtCoordinate(x, row).isNumber()){
                        isReadingNumber = true;
                        start = new Coordinate(x, row);
                    }
                } else if (!getItemAtCoordinate(x, row).isNumber()) {
                    builder.add(new Region(start, new Coordinate(x - 1, row)));
                    isReadingNumber = false;
                }
            }
            if (isReadingNumber) {
                builder.add(new Region(start, new Coordinate(getMatrixDimensions().width() - 1, start.y())));
            }
        }
        return builder.build();
    }
    
    int readNumberInRegion(Region numberRegion) {
        int out = 0;
        int y = numberRegion.startIncl().y();
        for (int x = numberRegion.startIncl().x(); x <= numberRegion.endIncl().x(); x++) {
            out *= 10;
            out += getItemAtCoordinate(x, y).numericize();
        }
        return out;
    }
    
    private boolean coordinateIsInBounds(Coordinate point){
        return getMatrixDimensions().toRegion().contains(point);
    }
    
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (int y = 0; y < getMatrixDimensions().height(); y++) {
            for (int x = 0; x < getMatrixDimensions().width(); x++) {
                out.append(getItemAtCoordinate(new Coordinate(x, y)).toDisplayString());
            }
            out .append("\n");
        }
        return out.toString();
    }
}
