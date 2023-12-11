package day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Challenge {

    static final int MAX_RED_CUBES = 12;
    static final int MAX_GREEN_CUBES = 13;
    static final int MAX_BLUE_CUBES = 14;


    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day2Input.txt"));
        String input;
        int currentId;
        boolean isPossibleConfiguration;
        String[] cubeCounts;
        int fewestNumberOfRedCubes;
        int fewestNumberOfGreenCubes;
        int fewestNumberOfBlueCubes;

        int sumIds = 0;
        int sumPowerSets = 0;

        while (scanner.hasNextLine()) {
            isPossibleConfiguration = true;
            fewestNumberOfRedCubes = 0;
            fewestNumberOfGreenCubes = 0;
            fewestNumberOfBlueCubes = 0;

            input = scanner.nextLine();
            input = input.replaceAll(";|,", "");
            currentId = Integer.parseInt(input.substring(5, input.indexOf(":")));
            cubeCounts = input.substring(input.indexOf(":") + 2).split(" ");

            for (int i = 1; i < cubeCounts.length; i += 2) {
                if (cubeCounts[i].equals("red") && Integer.parseInt(cubeCounts[i - 1]) > MAX_RED_CUBES
                        || cubeCounts[i].equals("green") && Integer.parseInt(cubeCounts[i - 1]) > MAX_GREEN_CUBES
                        || cubeCounts[i].equals("blue") && Integer.parseInt(cubeCounts[i - 1]) > MAX_BLUE_CUBES) {
                    isPossibleConfiguration = false;
                }

                // Challenge 2
                if (cubeCounts[i].equals("red") && Integer.parseInt(cubeCounts[i - 1]) > fewestNumberOfRedCubes) {
                    fewestNumberOfRedCubes = Integer.parseInt(cubeCounts[i - 1]);
                } else if (cubeCounts[i].equals("green") && Integer.parseInt(cubeCounts[i - 1]) > fewestNumberOfGreenCubes) {
                    fewestNumberOfGreenCubes = Integer.parseInt(cubeCounts[i - 1]);
                } else if (cubeCounts[i].equals("blue") && Integer.parseInt(cubeCounts[i - 1]) > fewestNumberOfBlueCubes) {
                    fewestNumberOfBlueCubes = Integer.parseInt(cubeCounts[i - 1]);
                }
            }

            if (isPossibleConfiguration) sumIds += currentId;
            sumPowerSets += fewestNumberOfRedCubes * fewestNumberOfGreenCubes * fewestNumberOfBlueCubes;
        }

        System.out.println("The sum of the IDs of the game is: " + sumIds);
        System.out.println("The sum of the power of cube sets is: " + sumPowerSets);
    }

}
