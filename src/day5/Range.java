package day5;

public class Range {
    long start;
    long length;

    public Range(long start, long length) {
        this.start = start;
        this.length = length;
    }

    @Override
    public String toString() {
        return "Range{" +
                "start=" + start +
                ", length=" + length +
                '}';
    }
}
