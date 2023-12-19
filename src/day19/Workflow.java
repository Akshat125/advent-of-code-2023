package day19;

import java.util.List;

public class Workflow {
    List<Rule> rules;
    String id;

    public Workflow(String id, List<Rule> rules) {
        this.id = id;
        this.rules = rules;
    }

    @Override
    public String toString() {
        return "Workflow{" +
                "rules=" + rules +
                ", id='" + id + '\'' +
                '}';
    }
}
