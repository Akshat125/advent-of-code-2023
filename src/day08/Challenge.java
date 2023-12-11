package day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Challenge {

    private static long gcd(long num1, long num2) {
        if (num2 == 0) return num1;
        return gcd(num2, num1 % num2);
    }

    private static long lcm(long num1, long num2) {
        if (num1 == 0 || num2 == 0) return 0;
        return Math.abs(num1 * num2) / gcd(num1, num2);
    }

    private static long lcm(long[] nums) {
        long result = 1;
        for (int i = 0; i < nums.length; i++) {
            result = lcm(nums[i], result);
        }
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day8Input.txt"));
        Graph graph = new Graph();
        String input;
        char[] directions = scanner.nextLine().toCharArray();
        scanner.nextLine();

        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            graph.addEdges(input.substring(0, 3), input.substring(7, 10), input.substring(12, 15));
        }

        // Challenge 1
        int stepsChallenge1 = 0;
        String currentNode = "AAA";
        Graph.Pair adjNodes = graph.getAdjacentNodes(currentNode);

        for (int i = 0; i < directions.length; i++) {
            if (adjNodes == null) {
                stepsChallenge1 = -1; // No solution found
                break;
            }
            if (directions[i] == 'R') {
                currentNode = adjNodes.right;
                adjNodes = graph.getAdjacentNodes(currentNode);
            } else if (directions[i] == 'L') {
                currentNode = adjNodes.left;
                adjNodes = graph.getAdjacentNodes(currentNode);
            }

            stepsChallenge1++;
            if (currentNode.equals("ZZZ")) break;
            if (i == directions.length - 1) i = -1;
        }

        // Challenge 2
        List<String> nodes = graph.getNodesEndingWith("A");
        long[] stepsToFinalNode = new long[nodes.size()];
        long stepsChallenge2;

        // For each start we find the number of steps to a node ending with Z
        // This step can also happen concurrently
        for (int i = 0; i < nodes.size(); i++) {
            currentNode = nodes.get(i);
            stepsChallenge2 = 0;

            for (int j = 0; j < directions.length; j++) {
                if (graph.getAdjacentNodes(currentNode) == null) stepsChallenge2 = -1;
                if (directions[j] == 'R') {
                    currentNode = graph.getAdjacentNodes(currentNode).right;
                } else if (directions[j] == 'L') {
                    currentNode = graph.getAdjacentNodes(currentNode).left;
                }

                stepsChallenge2++;
                if (currentNode.endsWith("Z")) break;
                if (j == directions.length - 1) j = -1;
            }

            stepsToFinalNode[i] = stepsChallenge2;
        }

        stepsChallenge2 = lcm(stepsToFinalNode);
        System.out.println("The total number of steps required in challenge 1 is: " + stepsChallenge1);
        System.out.println("The total number of steps required in challenge 2 is: " + stepsChallenge2);
    }
}
