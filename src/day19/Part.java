package day19;

public class Part {
    int x;
    int m;
    int a;
    int s;
    String workflowId;

    public Part(int x, int m, int a, int s, String workflowId) {
        this.x = x;
        this.m = m;
        this.a = a;
        this.s = s;
        this.workflowId = workflowId;
    }

    public int rating() {
        return x + m + a + s;
    }

    @Override
    public String toString() {
        return "Part{" +
                "x=" + x +
                ", m=" + m +
                ", a=" + a +
                ", s=" + s +
                '}';
    }
}
