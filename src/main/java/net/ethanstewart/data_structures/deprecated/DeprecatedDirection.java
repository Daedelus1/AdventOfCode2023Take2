package net.ethanstewart.data_structures.deprecated;

/**
 * @deprecated
 */
@Deprecated
public enum DeprecatedDirection {
    NORTH_WEST,
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST;

    public static DeprecatedDirection parseDirectionFromOrigin(DeprecatedCoordinate point, DeprecatedCoordinate origin) {
        DeprecatedCoordinate sigCoord = point.subtract(origin).toSignCoordinate();
        return switch (sigCoord.x()) {
            case 1 -> switch (sigCoord.y()) {
                case -1 -> NORTH_EAST;
                case 0 -> EAST;
                case 1 -> SOUTH_EAST;
                default -> throw new IllegalStateException("Unexpected value: " + sigCoord.y());
            };
            case 0 -> switch (sigCoord.y()) {
                case -1 -> NORTH;
                case 1 -> SOUTH;
                default -> throw new IllegalStateException("Unexpected value: " + sigCoord.y());
            };
            case -1 -> switch (sigCoord.y()) {
                case -1 -> NORTH_WEST;
                case 0 -> WEST;
                case 1 -> SOUTH_WEST;
                default -> throw new IllegalStateException("Unexpected value: " + sigCoord.y());
            };
            default -> throw new IllegalStateException("Unexpected value: " + sigCoord.x());
        };
    }

    public static DeprecatedDirection parseDirectionFromOrigin(DeprecatedCoordinate point) {
        return parseDirectionFromOrigin(point, new DeprecatedCoordinate(0, 0));
    }

    public DeprecatedDirection inverse() {
        return switch (this) {

            case NORTH_WEST -> SOUTH_EAST;
            case NORTH -> SOUTH;
            case NORTH_EAST -> SOUTH_WEST;
            case EAST -> WEST;
            case SOUTH_EAST -> NORTH_WEST;
            case SOUTH -> NORTH;
            case SOUTH_WEST -> NORTH_EAST;
            case WEST -> EAST;
        };
    }

    public DeprecatedCoordinate toUnitCoordinate() {
        return switch (this) {
            case NORTH_WEST -> new DeprecatedCoordinate(-1, -1);
            case NORTH -> new DeprecatedCoordinate(0, -1);
            case NORTH_EAST -> new DeprecatedCoordinate(1, -1);
            case EAST -> new DeprecatedCoordinate(1, 0);
            case SOUTH_EAST -> new DeprecatedCoordinate(1, 1);
            case SOUTH -> new DeprecatedCoordinate(0, 1);
            case SOUTH_WEST -> new DeprecatedCoordinate(-1, 1);
            case WEST -> new DeprecatedCoordinate(-1, 0);
        };
    }
}
