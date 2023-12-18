package day16;

import day03.Position;
import day08.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Challenge {
    private static List<List<String>> grid = new ArrayList<>();

    private static boolean isInBounds(Position position) {
        return position.getRow() >= 0 && position.getColumn() >= 0 && position.getRow() < grid.size() && position.getColumn() < grid.get(0).size();
    }

    private static int countEnergizedTiles(Queue<Pair<Position, Direction>> queue) {
        Set<Pair<Position, Direction>> visited = new HashSet<>();
        Pair<Position, Direction> positionDirectionPair;
        Position position;
        Direction direction;
        int coord;
        String obstacle;

        while (!queue.isEmpty()) {
            positionDirectionPair = queue.poll();
            position = positionDirectionPair.getLeft();
            direction = positionDirectionPair.getRight();
            positionDirectionPair = new Pair<>(position, direction);
            if (isInBounds(position) && !visited.contains(positionDirectionPair)) {
                visited.add(positionDirectionPair);
            } else {
                continue;
            }

            obstacle = grid.get(position.getRow()).get(position.getColumn());
            while (obstacle.equals(".") || ((direction == Direction.LEFT || direction == Direction.RIGHT) ? obstacle.equals("-") : obstacle.equals("|"))) {
                position = new Position(position.getRow(), position.getColumn());

                if (direction == Direction.UP) {
                    coord = position.getRow() - 1;
                    if (coord >= 0) {
                        position.setRow(coord);
                        visited.add(new Pair<>(position, direction));
                    } else {
                        break;
                    }
                } else if (direction == Direction.DOWN) {
                    coord = position.getRow() + 1;
                    if (coord < grid.size()) {
                        position.setRow(coord);
                        visited.add(new Pair<>(position, direction));
                    } else {
                        break;
                    }
                } else if (direction == Direction.LEFT) {
                    coord = position.getColumn() - 1;
                    if (coord >= 0) {
                        position.setColumn(coord);
                        visited.add(new Pair<>(position, direction));
                    } else {
                        break;
                    }
                } else if (direction == Direction.RIGHT) {
                    coord = position.getColumn() + 1;
                    if (coord < grid.get(0).size()) {
                        position.setColumn(coord);
                        visited.add(new Pair<>(position, direction));
                    } else {
                        break;
                    }
                }

                obstacle = grid.get(position.getRow()).get(position.getColumn());
            }

            if (direction == Direction.UP) {
                if (obstacle.equals("/") || obstacle.equals("-")) {
                    queue.add(new Pair<>(new Position(position.getRow(), position.getColumn() + 1), Direction.RIGHT));
                }
                if (obstacle.equals("\\") || obstacle.equals("-")) {
                    queue.add(new Pair<>(new Position(position.getRow(), position.getColumn() - 1), Direction.LEFT));
                }
            } else if (direction == Direction.DOWN) {
                if (obstacle.equals("/") || obstacle.equals("-")) {
                    queue.add(new Pair<>(new Position(position.getRow(), position.getColumn() - 1), Direction.LEFT));
                }
                if (obstacle.equals("\\") || obstacle.equals("-")) {
                    queue.add(new Pair<>(new Position(position.getRow(), position.getColumn() + 1), Direction.RIGHT));
                }
            } else if (direction == Direction.LEFT) {
                if (obstacle.equals("/") || obstacle.equals("|")) {
                    queue.add(new Pair<>(new Position(position.getRow() + 1, position.getColumn()), Direction.DOWN));
                }
                if (obstacle.equals("\\") || obstacle.equals("|")) {
                    queue.add(new Pair<>(new Position(position.getRow() - 1, position.getColumn()), Direction.UP));
                }
            } else if (direction == Direction.RIGHT) {
                if (obstacle.equals("/") || obstacle.equals("|")) {
                    queue.add(new Pair<>(new Position(position.getRow() - 1, position.getColumn()), Direction.UP));
                }
                if (obstacle.equals("\\") || obstacle.equals("|")) {
                    queue.add(new Pair<>(new Position(position.getRow() + 1, position.getColumn()), Direction.DOWN));
                }
            }
        }

        /* Visualizing
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                System.out.print(visited.contains(new Position(i, j)) ? '#' : '.');
            }
            System.out.println();
        }
        */

        int numberOfTilesEnergized = new HashSet<>(visited.stream().map(x -> x.getLeft()).collect(Collectors.toSet())).size();
        return numberOfTilesEnergized;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day16Input.txt"));
        String input;
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            grid.add(Arrays.stream(input.split("")).toList());
        }

        // Challenge 1
        Queue<Pair<Position, Direction>> queue = new LinkedList<>();
        queue.add(new Pair<>(new Position(0, 0), Direction.RIGHT));
        int numberOfTilesEnergized = countEnergizedTiles(queue);
        System.out.println("The number of tiles energized is: " + numberOfTilesEnergized);
        int max = -1;

        // Challenge 2
        for (int i = 0; i < grid.get(0).size(); i++) {
            queue = new LinkedList<>();
            queue.add(new Pair<>(new Position(0, i), Direction.DOWN));
            numberOfTilesEnergized = countEnergizedTiles(queue);
            max = Math.max(max, numberOfTilesEnergized);

            queue.poll();
            queue.add(new Pair<>(new Position(grid.size() - 1, i), Direction.UP));
            numberOfTilesEnergized = countEnergizedTiles(queue);
            max = Math.max(max, numberOfTilesEnergized);
        }
        for (int i = 0; i < grid.size(); i++) {
            queue = new LinkedList<>();
            queue.add(new Pair<>(new Position(i, 0), Direction.RIGHT));
            numberOfTilesEnergized = countEnergizedTiles(queue);
            max = Math.max(max, numberOfTilesEnergized);

            queue.poll();
            queue.add(new Pair<>(new Position(i, grid.get(0).size() - 1), Direction.LEFT));
            numberOfTilesEnergized = countEnergizedTiles(queue);
            max = Math.max(max, numberOfTilesEnergized);
        }

        System.out.println("The largest number of tiles energized is: " + max);
    }
}
