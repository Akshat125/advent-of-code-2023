package day8;

import java.util.*;

public class Graph {
    class Pair {
        String left;
        String right;

        public Pair(String left, String right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "left='" + left + '\'' +
                    ", right='" + right + '\'' +
                    '}';
        }
    }

    private Map<String, Pair> adjNodes;

    public Graph() {
        this.adjNodes = new HashMap<>();
    }

    public void addEdges(String node, String left, String right) {
        adjNodes.put(node, new Pair(left, right));
    }

    public Pair getAdjacentNodes(String node) {
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
