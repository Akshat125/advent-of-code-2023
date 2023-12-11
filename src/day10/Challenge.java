package day10;

import day3.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge {
    // Shoelace formula
    private static long calculateArea(List<Position> visitedPositions) {
        long area = 0;
        for (int i = 0; i < visitedPositions.size() - 1; i++) {
            area += visitedPositions.get(i).getRow() * visitedPositions.get(i + 1).getColumn() - visitedPositions.get(i + 1).getRow() * visitedPositions.get(i).getColumn();
        }

        return area / 2;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day10Input.txt"));
        List<List<Character>> map = new ArrayList<>();
        List<Character> mapRow;
        Position currentPosition = new Position();
        char[] input;

        int row = 0;
        while (scanner.hasNextLine()) {
            mapRow = new ArrayList<>();
            input = scanner.nextLine().toCharArray();
            for (int j = 0; j < input.length; j++) {
                mapRow.add(input[j]);
                if (input[j] == 'S') {
                    // Initializing our start position
                    currentPosition.setRow(row);
                    currentPosition.setColumn(j);
                }
            }
            map.add(mapRow);
            row++;
        }

        // Challenge 1
        long stepsNeeded;
        int column;
        char currentChar;
        List<Position> visitedPositions = new ArrayList<>();
        visitedPositions.add(currentPosition);
        Position startPosition = currentPosition;
        Position nextPosition;

        // Traverse counter-clockwise
        do {
            row = currentPosition.getRow();
            column = currentPosition.getColumn();
            currentChar = map.get(row).get(column);

            // Check if we can move north
            nextPosition = new Position(row - 1, column);
            if (row > 0 && !visitedPositions.contains(nextPosition) && (currentChar == '|' || currentChar == 'L' || currentChar == 'J' || currentChar == 'S')
                    && (map.get(row - 1).get(column) == '|' || map.get(row - 1).get(column) == '7' || map.get(row - 1).get(column) == 'F')) {
                visitedPositions.add(nextPosition);
                currentPosition = nextPosition;
                continue;
            }

            // Check if we can move south
            nextPosition = new Position(row + 1, column);
            if (row < map.size() - 1 && !visitedPositions.contains(nextPosition) && (currentChar == '|' || currentChar == '7' || currentChar == 'F' || currentChar == 'S')
                    && (map.get(row + 1).get(column) == '|' || map.get(row + 1).get(column) == 'L' || map.get(row + 1).get(column) == 'J')) {
                visitedPositions.add(nextPosition);
                currentPosition = nextPosition;
                continue;
            }

            // Check if we can move east
            nextPosition = new Position(row, column + 1);
            if (column < map.get(0).size() - 1 && !visitedPositions.contains(nextPosition) && (currentChar == '-' || currentChar == 'L' || currentChar == 'F' || currentChar == 'S')
                    && (map.get(row).get(column + 1) == '-' || map.get(row).get(column + 1) == '7' || map.get(row).get(column + 1) == 'J')) {
                visitedPositions.add(nextPosition);
                currentPosition = nextPosition;
                continue;
            }

            // Check if we can move west
            nextPosition = new Position(row, column - 1);
            if (column > 0 && !visitedPositions.contains(nextPosition) && (currentChar == '-' || currentChar == 'J' || currentChar == '7' || currentChar == 'S')
                    && (map.get(row).get(column - 1) == '-' || map.get(row).get(column - 1) == 'L' || map.get(row).get(column - 1) == 'F')) {
                visitedPositions.add(nextPosition);
                currentPosition = nextPosition;
                continue;
            }

            break;
        } while (true);

        visitedPositions.add(startPosition); // To close the polygon
        stepsNeeded = visitedPositions.size() / 2;

        // Challenge 2 (I used Shoelace formula and Pick's theorem)
        long area = Math.abs(calculateArea(visitedPositions));
        long tilesEnclosedByLoop = area - visitedPositions.size() / 2 + 1;

        System.out.println("The number of steps needed to get from starting position to the farthest in main loop is: " + stepsNeeded);
        System.out.println("The number of tiles enclosed by the main loop is: " + tilesEnclosedByLoop);
    }

}
