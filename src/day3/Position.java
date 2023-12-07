package day3;

import java.util.Objects;

public class Position {
    int lineNumber;
    int index;

    public Position(int lineNumber, int index) {
        this.lineNumber = lineNumber;
        this.index = index;
    }

    @Override
    public String toString() {
        return "Position{" +
                "lineNumber=" + lineNumber +
                ", index=" + index +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return lineNumber == position.lineNumber && index == position.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNumber, index);
    }
}
