package day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Challenge {
    private static void printDish(List<String> dish) {
        for (int i = 0; i < dish.size(); i++) {
            for (int j = 0; j < dish.get(i).length(); j++) {
                System.out.print(dish.get(i).charAt(j));
            }
            System.out.println();
        }
    }

    private static int calculateTotalLoad(List<String> dish) {
        int totalLoad = 0;
        int countOs;
        for (int i = 0; i < dish.size(); i++) {
            countOs = 0;
            for (int j = 0; j < dish.size(); j++) countOs += dish.get(i).charAt(j) == 'O' ? 1 : 0;
            totalLoad += (dish.size() - i) * countOs;
        }

        return totalLoad;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day14Input.txt"));
        List<String> dish = new ArrayList<>();
        Set<List<String>> seenDishes = new HashSet<>();
        List<List<String>> dishesArray = new ArrayList<>();

        while (scanner.hasNextLine()) {
            dish.add(scanner.nextLine());
        }

        int k;
        int iterations = 0;
        // Challenge 2
        // Idea is to keep rolling in all 4 directions until a dish is repeated, a cycle is detected then

        do {
            iterations++;
            seenDishes.add(new ArrayList<>(dish));
            dishesArray.add(new ArrayList<>(dish));
            // North (This is also part of the challenge 1)
            for (int i = 0; i < dish.size(); i++) {
                for (int j = 0; j < dish.get(i).length(); j++) {
                    if (dish.get(i).charAt(j) == 'O') {
                        // in-place modification
                        k = i - 1;
                        while (k >= 0 && dish.get(k).charAt(j) == '.') k--;
                        dish.set(i, dish.get(i).substring(0, j) + "." + dish.get(i).substring(j + 1));
                        dish.set(k + 1, dish.get(k + 1).substring(0, j) + "O" + dish.get(k + 1).substring(j + 1));
                    }
                }
            }

            // West
            for (int i = 0; i < dish.get(0).length(); i++) {
                for (int j = 0; j < dish.size(); j++) {
                    if (dish.get(j).charAt(i) == 'O') {
                        k = i - 1;
                        while (k >= 0 && dish.get(j).charAt(k) == '.') k--;
                        dish.set(j, dish.get(j).substring(0, i) + "." + dish.get(j).substring(i + 1));
                        dish.set(j, dish.get(j).substring(0, k + 1) + "O" + dish.get(j).substring(k + 2));
                    }
                }
            }

            // South
            for (int i = dish.size() - 1; i >= 0; i--) {
                for (int j = 0; j < dish.get(i).length(); j++) {
                    if (dish.get(i).charAt(j) == 'O') {
                        k = i + 1;
                        while (k < dish.size() && dish.get(k).charAt(j) == '.') k++;
                        dish.set(i, dish.get(i).substring(0, j) + "." + dish.get(i).substring(j + 1));
                        dish.set(k - 1, dish.get(k - 1).substring(0, j) + "O" + dish.get(k - 1).substring(j + 1));
                    }
                }
            }

            // East
            for (int i = dish.get(0).length() - 1; i >= 0; i--) {
                for (int j = 0; j < dish.size(); j++) {
                    if (dish.get(j).charAt(i) == 'O') {
                        k = i + 1;
                        while (k < dish.get(0).length() && dish.get(j).charAt(k) == '.') k++;
                        dish.set(j, dish.get(j).substring(0, i) + "." + dish.get(j).substring(i + 1));
                        dish.set(j, dish.get(j).substring(0, k - 1) + "O" + dish.get(j).substring(k));
                    }
                }
            }
        } while (!seenDishes.contains(dish));

        int cycleStart = dishesArray.indexOf(dish);
        dish = dishesArray.get((1000000000 - cycleStart) % (iterations - cycleStart) + cycleStart);
        int totalLoad = calculateTotalLoad(dish);
        System.out.println("The total load on the north support beams is: " + totalLoad);
    }
}
