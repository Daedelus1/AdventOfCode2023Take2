package net.ethanstewart.advents.Day10;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.ethanstewart.data_structures.deprecated.DeprecatedCoordinate;
import net.ethanstewart.data_structures.deprecated.DeprecatedDirection;

import java.util.HashMap;

import static net.ethanstewart.data_structures.deprecated.DeprecatedDirection.*;

public enum PipeSegment {
    START,
    VERTICAL,
    HORIZONTAL,
    NORTH_TO_WEST_BEND,
    NORTH_TO_EAST_BEND,
    SOUTH_TO_WEST_BEND,
    SOUTH_TO_EAST_BEND,
    VOID;

    public static final DeprecatedCoordinate EXPANDED_CENTER = new DeprecatedCoordinate(1, 1);
    public static final HashMap<PipeSegment, ImmutableSet<DeprecatedCoordinate>> EXPANSION_TABULATION = new HashMap<>();
    private static final ImmutableMap<PipeSegment, ImmutableSet<DeprecatedDirection>> CONNECTIONS_MAP =
            ImmutableMap.<PipeSegment, ImmutableSet<DeprecatedDirection>>builder()
                    .put(VERTICAL, ImmutableSet.of(NORTH, SOUTH))
                    .put(HORIZONTAL, ImmutableSet.of(EAST, WEST))
                    .put(NORTH_TO_WEST_BEND, ImmutableSet.of(NORTH, WEST))
                    .put(NORTH_TO_EAST_BEND, ImmutableSet.of(NORTH, EAST))
                    .put(SOUTH_TO_WEST_BEND, ImmutableSet.of(WEST, SOUTH))
                    .put(SOUTH_TO_EAST_BEND, ImmutableSet.of(EAST, SOUTH))
                    .put(VOID, ImmutableSet.of())
                    .put(START, ImmutableSet.of())
                    .build();

    public static PipeSegment parsePipeSegment(char pipeSegmentChar) {
        return switch (pipeSegmentChar) {
            case '|' -> VERTICAL;
            case '-' -> HORIZONTAL;
            case 'L' -> NORTH_TO_EAST_BEND;
            case 'J' -> NORTH_TO_WEST_BEND;
            case '7' -> SOUTH_TO_WEST_BEND;
            case 'F' -> SOUTH_TO_EAST_BEND;
            case '.' -> VOID;
            case 'S' -> START;
            default -> throw new IllegalStateException("Unexpected value: " + pipeSegmentChar);
        };
    }

    public ImmutableSet<DeprecatedCoordinate> expansion() {
        if (EXPANSION_TABULATION.containsKey(this)) {
            return EXPANSION_TABULATION.get(this);
        }
        ImmutableSet<DeprecatedDirection> connections = getConnections();
        if (connections.isEmpty()) {
            return ImmutableSet.of();
        }
        ImmutableSet.Builder<DeprecatedCoordinate> builder = ImmutableSet.builder();
        builder.add(EXPANDED_CENTER);
        connections.stream().map(EXPANDED_CENTER::move).forEach(builder::add);
        EXPANSION_TABULATION.put(this, builder.build());
        return builder.build();
    }

    public ImmutableSet<DeprecatedDirection> getConnections() {
        if (CONNECTIONS_MAP.containsKey(this)) {
            return CONNECTIONS_MAP.get(this);
        }
        throw new IllegalStateException("Unexpected value: " + this);
    }

    public String toDisplayString() {
        return switch (this) {
            case VERTICAL -> "|";
            case HORIZONTAL -> "-";
            case NORTH_TO_EAST_BEND -> "L";
            case NORTH_TO_WEST_BEND -> "J";
            case SOUTH_TO_WEST_BEND -> "7";
            case SOUTH_TO_EAST_BEND -> "F";
            case VOID -> ".";
            case START -> "S";
        };
    }
}
