package day21;

import day03.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Challenge {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day21Input.txt"));
        List<List<Character>> map = new ArrayList<>();
        List<Character> mapRow;
        Position startPosition = new Position();

        int row = 0;
        char[] input;
        while (scanner.hasNextLine()) {
            mapRow = new ArrayList<>();
            input = scanner.nextLine().toCharArray();
            for (int j = 0; j < input.length; j++) {
                if (input[j] == 'S') {
                    // Initializing our start position
                    startPosition.setRow(row);
                    startPosition.setColumn(j);
                    input[j] = '.';
                }
                mapRow.add(input[j]);
            }
            map.add(mapRow);
            row++;
        }


        // Challenge 1
        Set<Position> gardenPlots = new HashSet<>();
        Set<Position> nextGardenPlots;
        gardenPlots.add(startPosition);
        int column;
        for (int i = 0; i < 64; i++) {
            nextGardenPlots = new HashSet<>();
            for (Position position : gardenPlots) {
                row = position.getRow();
                column = position.getColumn();

                // Check UP -> DOWN -> LEFT -> RIGHT
                if (row - 1 >= 0 && map.get(row - 1).get(column) == '.')
                    nextGardenPlots.add(new Position(row - 1, column));
                if (row + 1 < map.size() && map.get(row + 1).get(column) == '.')
                    nextGardenPlots.add(new Position(row + 1, column));
                if (column + 1 < map.get(0).size() && map.get(row).get(column + 1) == '.')
                    nextGardenPlots.add(new Position(row, column + 1));
                if (column - 1 >= 0 && map.get(row).get(column - 1) == '.')
                    nextGardenPlots.add(new Position(row, column - 1));
            }
            gardenPlots = nextGardenPlots;
        }
        int numberOfGardenPlots = gardenPlots.size();
        System.out.println("The number of garden plots in challenge 1 is: " + numberOfGardenPlots);

        // Challenge 2
        // Observation 26501365 = 202300 * map.length + 0.5 * map.length
        // Quadratic formula for garden plots for every (i * map.length + 0.5 * map.length) step
        // Quadratic formula can be found using https://www.wolframalpha.com/input/?i=quadratic+fit+calculator
        // This formula can be used to find the garden plots for 202301 (Since x = 1 for i = 0)
        int steps;
        for (int i = 0; i <= 2; i++) {
            steps = (int) (i * map.size() + Math.floor(0.5 * map.get(0).size()));
            gardenPlots = new HashSet<>();
            gardenPlots.add(startPosition);
            for (int j = 0; j < steps; j++) {
                nextGardenPlots = new HashSet<>();
                for (Position position : gardenPlots) {
                    row = position.getRow();
                    column = position.getColumn();

                    if (map.get(Math.floorMod(row - 1, map.size())).get(Math.floorMod(column, map.get(0).size())) == '.')
                        nextGardenPlots.add(new Position(row - 1, column));
                    if (map.get(Math.floorMod(row + 1, map.size())).get(Math.floorMod(column, map.get(0).size())) == '.')
                        nextGardenPlots.add(new Position(row + 1, column));
                    if (map.get(Math.floorMod(row, map.size())).get(Math.floorMod(column + 1, map.get(0).size())) == '.')
                        nextGardenPlots.add(new Position(row, column + 1));
                    if (map.get(Math.floorMod(row, map.size())).get(Math.floorMod(column - 1, map.get(0).size())) == '.')
                        nextGardenPlots.add(new Position(row, column - 1));
                }
                gardenPlots = nextGardenPlots;
            }

            numberOfGardenPlots = gardenPlots.size();
            System.out.println(steps + ": " + numberOfGardenPlots);
        }

        // For a * x^2 + b * x + c:
        // long numberOfGardenPlotsChallenge2 = a * (long) Math.pow(202301, 2) - (long) b * 202301 + c;
        // System.out.println("The number of garden plots in challenge 2 is: " + numberOfGardenPlotsChallenge2);
        // Another alternative is to extrapolate 202301st value using approach such as in day 9
    }
}
