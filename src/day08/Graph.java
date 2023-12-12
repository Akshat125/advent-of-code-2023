package day08;

import java.util.*;

public class Graph {
    private Map<String, Pair> adjNodes;

    public Graph() {
        this.adjNodes = new HashMap<>();
    }

    public void addEdges(String node, String left, String right) {
        adjNodes.put(node, new Pair(left, right));
    }

    public Pair<String, String> getAdjacentNodes(String node) {
        return adjNodes.get(node);
    }

    public List<String> getNodesEndingWith(String ending) {
        List<String> nodes = new ArrayList<>();
        for (String node : this.adjNodes.keySet()) {
            if (node.endsWith(ending)) {
                nodes.add(node);
            }
        }

        return nodes;
    }
}
