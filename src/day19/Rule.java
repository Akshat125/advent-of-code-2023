package day19;

public class Rule {
    String category;
    Boolean isLessThanThreshold;
    Integer threshold;
    String nextWorkflow;

    public Rule(String category, Boolean isLessThanThreshold, Integer threshold, String nextWorkflow) {
        this.category = category;
        this.isLessThanThreshold = isLessThanThreshold;
        this.threshold = threshold;
        this.nextWorkflow = nextWorkflow;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "category='" + category + '\'' +
                ", isLessThanThreshold=" + isLessThanThreshold +
                ", threshold=" + threshold +
                ", nextWorkflow='" + nextWorkflow + '\'' +
                '}';
    }
}
