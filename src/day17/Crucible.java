package day17;

import day03.Position;

import java.util.Objects;

public class Crucible implements Comparable<Crucible> {
    int heatLoss;
    Position position;
    // Direction vectors for x and y-axis
    int rowDirection;
    int columnDirection;
    int numbersOfBlocks;

    public Crucible(int heatLoss, Position position, int rowDirection, int columnDirection, int numbersOfBlocks) {
        this.heatLoss = heatLoss;
        this.position = position;
        this.rowDirection = rowDirection;
        this.columnDirection = columnDirection;
        this.numbersOfBlocks = numbersOfBlocks;
    }

    @Override
    public int compareTo(Crucible other) {
        return Integer.compare(this.heatLoss, other.heatLoss);
    }

    @Override
    public String toString() {
        return "Crucible{" +
                "heatLoss=" + heatLoss +
                ", position=" + position +
                ", rowDirection=" + rowDirection +
                ", columnDirection=" + columnDirection +
                ", numbersOfBlocks=" + numbersOfBlocks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crucible crucible = (Crucible) o;
        return heatLoss == crucible.heatLoss && rowDirection == crucible.rowDirection && columnDirection == crucible.columnDirection && numbersOfBlocks == crucible.numbersOfBlocks && Objects.equals(position, crucible.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heatLoss, position, rowDirection, columnDirection, numbersOfBlocks);
    }
}
