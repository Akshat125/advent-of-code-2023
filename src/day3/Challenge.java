package day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Challenge {
    private static HashMap<Position, List<Integer>> numsPerAsterisk = new HashMap<>();

    private static boolean isSymbol(char[] currentLine, int i) {
        return currentLine[i] != '.';
    }

    private static void updateNumbersPerAsterisk(Position currentPosition, int num) {
        if (numsPerAsterisk.containsKey(currentPosition)) {
            List<Integer> nums = numsPerAsterisk.get(currentPosition);
            nums.add(num);
            numsPerAsterisk.put(currentPosition, nums);
        } else {
            numsPerAsterisk.put(currentPosition, new ArrayList<>(Arrays.asList(num)));
        }
    }

    private static boolean isAdjacentToSymbol(StringBuilder stringBuilder, boolean isAdjacentToSymbol, char[] adjacentLine, char[] currentLine, int currentLineNumber, int num, int i) {
        if (adjacentLine != null) {
            int j = i - stringBuilder.length() - 1;
            j = j < 0 ? j + 1 : j;
            while (j <= i && j < currentLine.length) {
                if (isSymbol(adjacentLine, j)) {
                    isAdjacentToSymbol = true;
                    if (adjacentLine[j] == '*') updateNumbersPerAsterisk(new Position(currentLineNumber, j), num);
                }
                j++;
            }
        }
        return isAdjacentToSymbol;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day3Input.txt"));
        StringBuilder stringBuilder = new StringBuilder();
        int num;
        int sumPartNums = 0;
        int sumGearRatios = 0;
        boolean isAdjacentToSymbol;
        char[] aboveLine = null;
        char[] currentLine = scanner.hasNextLine() ? scanner.nextLine().toCharArray() : null;
        char[] belowLine = null;
        int currentLineNumber = 0;

        while (currentLine != null) {
            belowLine = scanner.hasNextLine() ? scanner.nextLine().toCharArray() : null;

            for (int i = 0; i < currentLine.length; i++) {
                isAdjacentToSymbol = false;

                if (Character.isDigit(currentLine[i])) {
                    stringBuilder.append(currentLine[i]);
                    while (++i < currentLine.length && Character.isDigit(currentLine[i])) {
                        stringBuilder.append(currentLine[i]);
                    }
                    num = Integer.parseInt(stringBuilder.toString());

                    if (i - stringBuilder.length() - 1 >= 0 && isSymbol(currentLine, i - stringBuilder.length() - 1)) {
                        isAdjacentToSymbol = true;
                        if (currentLine[i - stringBuilder.length() - 1] == '*') {
                            updateNumbersPerAsterisk(new Position(currentLineNumber, i - stringBuilder.length() - 1), num);
                        }
                    }

                    if (i != currentLine.length && isSymbol(currentLine, i)) {
                        isAdjacentToSymbol = true;
                        if (currentLine[i] == '*') updateNumbersPerAsterisk(new Position(currentLineNumber, i), num);
                    }

                    isAdjacentToSymbol = isAdjacentToSymbol(stringBuilder, isAdjacentToSymbol, aboveLine, currentLine, currentLineNumber - 1, num, i);
                    isAdjacentToSymbol |= isAdjacentToSymbol(stringBuilder, isAdjacentToSymbol, belowLine, currentLine, currentLineNumber + 1, num, i);

                    if (isAdjacentToSymbol) sumPartNums += num;
                    stringBuilder.delete(0, stringBuilder.length());
                }
            }

            aboveLine = currentLine;
            currentLine = belowLine;
            currentLineNumber++;
        }

        // Challenge 2
        List<Integer> nums;
        for (Position position : numsPerAsterisk.keySet()) {
            nums = numsPerAsterisk.get(position);
            if (nums.size() == 2) sumGearRatios += nums.get(0) * nums.get(1);
        }

        //numsPerAsterisk.forEach((x, y) -> System.out.println("(" + x.toString() + "," + y.toString() + ")"));
        System.out.println("The sum of all part numbers is: " + sumPartNums);
        System.out.println("The sum of all gear ratios is: " + sumGearRatios);
    }

}