package day25;

import org.jgrapht.Graph;
import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Challenge {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day25Input.txt"));
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        String input;

        String from;
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            from = input.split(": ")[0];
            if (!graph.vertexSet().contains(from)) graph.addVertex(from);
            for (String to : input.split(": ")[1].split(" ")) {
                if (!graph.vertexSet().contains(to)) graph.addVertex(to);
                graph.addEdge(from, to);
            }
        }

        StoerWagnerMinimumCut<String, DefaultEdge> minCutAlgorithm = new StoerWagnerMinimumCut<>(graph);
        int group1Length = minCutAlgorithm.minCut().size();
        int group2Length = graph.vertexSet().size() - group1Length;
        System.out.println("Multiplying size of the groups gives: " + (group1Length * group2Length));
    }
}
