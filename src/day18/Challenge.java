package day18;

import day03.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Challenge {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day18Input.txt"));
        String input;

        // Challenge 1 & 2 (We can reuse the Shoelace formula + Pick's Theorem strategy from challenge 10)
        String[] directions = new String[]{"R", "D", "L", "U"};
        Position position = new Position(0, 0);
        Position newPosition;
        String direction;
        long steps;
        long area = 0;
        long countExternalNodes = 0;

        while (scanner.hasNextLine()) {
            input = scanner.nextLine().split("#")[1];
            direction = directions[Integer.parseInt(input.substring(5, 6))];
            steps = Long.parseLong(input.substring(0, 5), 16);
            countExternalNodes += steps;

            if (direction.equals("U")) {
                for (int i = 0; i < steps; i++) {
                    newPosition = new Position(position.getRow() - 1, position.getColumn());
                    area += position.getRow() * newPosition.getColumn() - newPosition.getRow() * position.getColumn();
                    position = newPosition;
                }
            } else if (direction.equals("D")) {
                for (int i = 0; i < steps; i++) {
                    newPosition = new Position(position.getRow() + 1, position.getColumn());
                    area += position.getRow() * newPosition.getColumn() - newPosition.getRow() * position.getColumn();
                    position = newPosition;
                }
            } else if (direction.equals("L")) {
                for (int i = 0; i < steps; i++) {
                    newPosition = new Position(position.getRow(), position.getColumn() - 1);
                    area += position.getRow() * newPosition.getColumn() - newPosition.getRow() * position.getColumn();
                    position = newPosition;
                }
            } else if (direction.equals("R")) {
                for (int i = 0; i < steps; i++) {
                    newPosition = new Position(position.getRow(), position.getColumn() + 1);
                    area += position.getRow() * newPosition.getColumn() - newPosition.getRow() * position.getColumn();
                    position = newPosition;
                }
            }
        }

        area = Math.abs(area / 2);
        long tilesEnclosedByLoop = area - countExternalNodes / 2 + 1;
        long totalTiles = tilesEnclosedByLoop + countExternalNodes;

        System.out.println("It can hold the following cubic centimeters of lava: " + totalTiles);
    }
}
