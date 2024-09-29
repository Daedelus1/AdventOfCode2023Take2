package net.ethanstewart.data_structures.deprecated;

/**
 * @deprecated
 * @param width
 * @param height
 */
@Deprecated
public record DeprecatedDimension(int width, int height) {
    public DeprecatedRegion toRegion() {
        return new DeprecatedRegion(new DeprecatedCoordinate(0, 0), new DeprecatedCoordinate(width - 1, height - 1));
    }

    public boolean isSmallerThan(DeprecatedDimension other) {
        return this.height < other.height || this.width < other.width;
    }
}
