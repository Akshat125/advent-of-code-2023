package day11;

import day3.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Challenge {
    /* Useful for visualization in part 1 for the new map:
    private static List<List<Character>> expandMap(List<List<Character>> map, Set<Integer> emptyRowIndices, Set<Integer> emptyColumnIndices) {
        List<List<Character>> newMap = new ArrayList<>();
        List<Character> newMapRow;
        char currentChar;

        for (int i = 0; i < map.size(); i++) {
            newMapRow = new ArrayList<>();
            for (int j = 0; j < map.get(0).size(); j++) {
                currentChar = map.get(i).get(j);
                newMapRow.add(currentChar);
                if (emptyColumnIndices.contains(j)) {
                    newMapRow.add('.');
                }
            }

            newMap.add(newMapRow);
            if (emptyRowIndices.contains(i)) {
                newMap.add(newMapRow);
            }
        }
        return newMap;
    }
    */

    private static List<Position> findGalaxies(List<List<Character>> map) {
        List<Position> galaxies = new ArrayList<>();
        char currentChar;

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(0).size(); j++) {
                currentChar = map.get(i).get(j);
                if (currentChar == '#') galaxies.add(new Position(i, j));
            }
        }

        return galaxies;
    }

    private static long findDistance(Position galaxy1, Position galaxy2, Set<Integer> emptyRowIndices, Set<Integer> emptyColumnIndices, int scalingFactor) {
        long distance;
        long emptyRowsInBetween = 0;
        long emptyColumnsInBetween = 0;

        for (int row : emptyRowIndices) {
            if (galaxy1.getRow() < galaxy2.getRow()) {
                if (galaxy1.getRow() < row && galaxy2.getRow() > row) emptyRowsInBetween++;
            }
            if (galaxy2.getRow() < galaxy1.getRow()) {
                if (galaxy2.getRow() < row && galaxy1.getRow() > row) emptyRowsInBetween++;
            }
        }
        for (int column : emptyColumnIndices) {
            if (galaxy1.getColumn() < galaxy2.getColumn()) {
                if (galaxy1.getColumn() < column && galaxy2.getColumn() > column) emptyColumnsInBetween++;
            }
            if (galaxy2.getColumn() < galaxy1.getColumn()) {
                if (galaxy2.getColumn() < column && galaxy1.getColumn() > column) emptyColumnsInBetween++;
            }
        }

        distance = Math.abs(galaxy2.getRow() - galaxy1.getRow()) + Math.abs(galaxy2.getColumn() - galaxy1.getColumn());
        distance += (scalingFactor - 1) * (emptyRowsInBetween + emptyColumnsInBetween);
        return distance;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day11Input.txt"));
        char[] input;
        List<List<Character>> map = new ArrayList<>();
        List<Character> mapRow;
        Set<Integer> emptyRowIndices = new HashSet<>();
        Set<Integer> emptyColumnIndices = new HashSet<>();

        boolean isEmpty;
        int row = 0;

        while (scanner.hasNextLine()) {
            mapRow = new ArrayList<>();
            isEmpty = true;
            input = scanner.nextLine().toCharArray();
            for (int j = 0; j < input.length; j++) {
                if (input[j] == '#') isEmpty = false;
                mapRow.add(input[j]);
            }
            if (isEmpty) emptyRowIndices.add(row);
            map.add(mapRow);
            row++;
        }

        // Add empty columns indices
        for (int i = 0; i < map.get(0).size(); i++) {
            isEmpty = true;
            for (int j = 0; j < map.size(); j++) {
                if (map.get(j).get(i) == '#') isEmpty = false;
            }
            if (isEmpty) emptyColumnIndices.add(i);
        }

        // map = expandMap(map, emptyRowIndices, emptyColumnIndices);
        List<Position> galaxies = findGalaxies(map);

        // Challenge 1, 2 (This approach works for any scaling factor of the universe)
        long sumDistances = 0;
        Set<Position> visitedPairs = new HashSet<>();
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = 0; j < galaxies.size(); j++) {
                if (j != i && !visitedPairs.contains(new Position(j, i))) {
                    sumDistances += findDistance(galaxies.get(i), galaxies.get(j), emptyRowIndices, emptyColumnIndices, 1000000);
                    visitedPairs.add(new Position(i, j));
                }
            }
        }

        System.out.println("The sum of shortest distance between all the galaxies is: " + sumDistances);
    }
}
