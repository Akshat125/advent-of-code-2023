package day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge {
    public static int hammingDistance(String string1, String string2) {
        int distance = 0;
        for (int i = 0; i < string1.length(); i++) distance += (string1.charAt(i) != string2.charAt(i)) ? 1 : 0;
        return distance;
    }

    // Challenge 1 is same as challenge 2 with hamming distance requirement of 0
    // returns number of rows before the reflection line
    private static int numberOfRowsBeforeReflectionLine(List<String> rows) {
        boolean reflectionFound = false;
        int i = 0;
        int j = rows.size() - 1;
        int iInner = 0;
        int jInner;
        int sumHammingDistances = 0;
        // There are 2 possibilities:
        // the first few columns have nowhere to reflect upon, so we keep travelling till we notice a pattern forming

        while (i < j) {
            sumHammingDistances = hammingDistance(rows.get(i), rows.get(j));
            if (sumHammingDistances <= 1) {
                iInner = i + 1;
                jInner = j - 1;
                reflectionFound = iInner != jInner;
                while (iInner < jInner) {
                    sumHammingDistances += hammingDistance(rows.get(iInner), rows.get(jInner));
                    //if (!rows.get(iInner).equals(rows.get(jInner))) reflectionFound = false;
                    iInner++;
                    jInner--;
                }
                if (reflectionFound && sumHammingDistances == 1) break;
            }
            i++;
        }
        if (reflectionFound && sumHammingDistances == 1) return iInner;

        // 2nd possibility is the exact opposite, last few columns have nowhere to reflect upon
        i = 0;
        j = rows.size() - 1;
        while (j > i) {
            sumHammingDistances = hammingDistance(rows.get(i), rows.get(j));
            if (sumHammingDistances <= 1) {
                iInner = i + 1;
                jInner = j - 1;
                reflectionFound = iInner != jInner;
                while (iInner < jInner) {
                    sumHammingDistances += hammingDistance(rows.get(iInner), rows.get(jInner));
                    //if (!rows.get(iInner).equals(rows.get(jInner))) reflectionFound = false;
                    iInner++;
                    jInner--;
                }

                if (reflectionFound && sumHammingDistances == 1) break;
            }
            j--;
        }

        return reflectionFound && sumHammingDistances == 1 ? iInner : -1;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day13Input.txt"));
        String input;
        List<List<String>> puzzlesByRows = new ArrayList<>();
        List<List<String>> puzzlesByColumns = new ArrayList<>();
        List<String> rows;
        List<String> columns;

        while (scanner.hasNextLine()) {
            rows = new ArrayList<>();
            while (scanner.hasNextLine() && !(input = scanner.nextLine()).equals("")) rows.add(input);
            puzzlesByRows.add(rows);
        }

        StringBuilder currentString;
        for (int i = 0; i < puzzlesByRows.size(); i++) {
            columns = new ArrayList<>();
            for (int j = 0; j < puzzlesByRows.get(i).get(0).length(); j++) {
                currentString = new StringBuilder();
                for (int k = 0; k < puzzlesByRows.get(i).size(); k++) {
                    currentString.append(puzzlesByRows.get(i).get(k).charAt(j));
                }
                columns.add(currentString.toString());
            }
            puzzlesByColumns.add(columns);
        }

        int summaryNotes = 0;
        int numberOfRowsBeforeReflectionLine;

        for (int i = 0; i < puzzlesByRows.size(); i++) {
            numberOfRowsBeforeReflectionLine = numberOfRowsBeforeReflectionLine(puzzlesByRows.get(i));
            if (numberOfRowsBeforeReflectionLine != -1) {
                summaryNotes += 100 * numberOfRowsBeforeReflectionLine;
            }
            numberOfRowsBeforeReflectionLine = numberOfRowsBeforeReflectionLine(puzzlesByColumns.get(i));
            if (numberOfRowsBeforeReflectionLine != -1) summaryNotes += numberOfRowsBeforeReflectionLine;
        }

        System.out.println("The number after summarizing all the notes is: " + summaryNotes);
    }

}
