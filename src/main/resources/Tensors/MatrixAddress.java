package net.ethanstewart.tensors;

import static com.google.common.base.Preconditions.checkArgument;

public class MatrixAddress extends Addressable<MatrixAddress> {
    private final long x;
    private final long y;
    
    public MatrixAddress(long x, long y) {
        super(2);
        this.x = x;
        this.y = y;
    }
    
    public long getX() {
        return x;
    }
    
    public long getY() {
        return y;
    }
    
    @Override
    long getItemAtDimensionIndex(int dimensionIndex) {
        return switch (dimensionIndex) {
            case 0 -> x;
            case 1 -> y;
            default -> throw new IllegalStateException("Unexpected value: " + dimensionIndex);
        };
    }
    
    
    @Override
    MatrixAddress makeNewAddress(long... values) {
        checkArgument(values.length == 2);
        return new MatrixAddress(values[0], values[1]);
    }
    
    public MatrixAddress getOrigin() {
        return new MatrixAddress(0, 0);
    }
    
    MatrixAddress add(MatrixAddress addend) {
        return new MatrixAddress(this.x + addend.x, this.y + addend.y);
    }
    
    MatrixAddress subtract(MatrixAddress subtrahend) {
        return new MatrixAddress(this.x - subtrahend.x, this.y - subtrahend.y);
    }
    
    MatrixAddress difference(MatrixAddress other) {
        return this.subtract(other).abs();
    }
    
    MatrixAddress abs() {
        return new MatrixAddress(Math.abs(this.x), Math.abs(this.y));
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof MatrixAddress && this.x == ((MatrixAddress) obj).x && this.y == ((MatrixAddress) obj).y;
    }
    
    @Override
    public String toString() {
        return String.format("MatrixAddress[x=%sL, y=%sL]", x, y);
    }
}
