package net.ethanstewart.advents.Day17;

import net.ethanstewart.data_structures.tensors.MatrixAddress;
import org.jetbrains.annotations.NotNull;

record TileInformation(
        MatrixAddress tileLocation,
        Direction directionOfEntry,
        int tailLength,
        int cost
) implements Comparable<TileInformation> {


    @Override
    public int compareTo(@NotNull TileInformation o) {
        return Integer.compare(this.cost, o.cost);
    }


}
