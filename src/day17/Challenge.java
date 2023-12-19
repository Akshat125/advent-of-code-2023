package day17;

import day03.Position;
import day08.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Challenge {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day17Input.txt"));
        String input;
        List<List<Integer>> grid = new ArrayList<>();

        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            grid.add(Arrays.stream(input.split("")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList()));
        }

        // Challenge 1 & 2 (Dijkstra's Algorithm)
        PriorityQueue<Crucible> priorityQueue = new PriorityQueue<>();
        Map<Crucible, Integer> settled = new HashMap<>();
        priorityQueue.add(new Crucible(0, new Position(0, 0), 1, 0, 0));
        priorityQueue.add(new Crucible(0, new Position(0, 0), 0, 1, 0));
        settled.put(new Crucible(-1, new Position(0, 0), 1, 0, 0), 0);
        settled.put(new Crucible(-1, new Position(0, 0), 0, 1, 0), 0);
        Crucible crucible;
        Crucible key;
        int rowDirection;
        int columnDirection;
        Position position;
        int nextRow;
        int nextColumn;
        int nextHeatLoss;
        int nextNumberOfBlocks;
        List<Pair<Integer, Integer>> directions;
        int totalHeatLoss = 0;

        while (!priorityQueue.isEmpty()) {
            crucible = priorityQueue.poll();
            rowDirection = crucible.rowDirection;
            columnDirection = crucible.columnDirection;
            position = crucible.position;

            if (position.getRow() == grid.size() - 1 && position.getColumn() == grid.get(0).size() - 1) {
                totalHeatLoss = crucible.heatLoss;
                break;
            }

            key = new Crucible(-1, position, rowDirection, columnDirection, crucible.numbersOfBlocks);
            if (crucible.heatLoss == settled.get(key)) {
                directions = new ArrayList<>();
                if (crucible.numbersOfBlocks > 3) {
                    directions.add(new Pair(-columnDirection, rowDirection));
                    directions.add(new Pair(columnDirection, -rowDirection));
                }

                if (crucible.numbersOfBlocks < 10) {
                    directions.add(new Pair<>(rowDirection, columnDirection));
                }

                for (Pair<Integer, Integer> direction : directions) {
                    nextRow = position.getRow() + direction.getLeft(); // row direction
                    nextColumn = position.getColumn() + direction.getRight(); // column direction

                    if (nextRow >= 0 && nextRow < grid.size() && nextColumn >= 0 && nextColumn < grid.get(0).size()) {
                        nextHeatLoss = crucible.heatLoss + grid.get(nextRow).get(nextColumn);
                        nextNumberOfBlocks = direction.getLeft() == rowDirection && direction.getRight() == columnDirection ? crucible.numbersOfBlocks + 1 : 1;
                        key = new Crucible(-1, new Position(nextRow, nextColumn), direction.getLeft(), direction.getRight(), nextNumberOfBlocks);

                        if (!settled.containsKey(key) || nextHeatLoss < settled.get(key)) {
                            settled.put(key, nextHeatLoss);
                            priorityQueue.add(new Crucible(nextHeatLoss, new Position(nextRow, nextColumn), direction.getLeft(), direction.getRight(), nextNumberOfBlocks));
                        }
                    }
                }
            }
        }

        System.out.println("The total heat loss is: " + totalHeatLoss);
    }
}
