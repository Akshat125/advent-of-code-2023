package day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Challenge {

    static List<long[]> generateSequences(long[] history) {
        List<long[]> sequences = new ArrayList<>();
        sequences.add(history);
        long[] previousSequence = history;
        long[] nextSequence;
        long nextValue;
        boolean allZeroes = false;

        while (!allZeroes) {
            allZeroes = true;
            nextSequence = new long[previousSequence.length - 1];
            for (int i = 0; i < nextSequence.length; i++) {
                nextValue = previousSequence[i+1] - previousSequence[i];
                if (nextValue != 0) allZeroes = false;
                nextSequence[i] = nextValue;
            }

            sequences.add(nextSequence);
            previousSequence = nextSequence;
        }

        return sequences;
    }

    static long sumExtrapolatedValues(List<long[]> sequencesPerHistory, boolean isChallenge1) {
        long extrapolatedValue = 0;

        if (isChallenge1) {
            for (int i = sequencesPerHistory.size() - 2; i >= 0; i--) {
                extrapolatedValue += sequencesPerHistory.get(i)[sequencesPerHistory.get(i).length - 1];
            }
        } else {
            extrapolatedValue = 0;
            for (int i = sequencesPerHistory.size() - 2; i >= 0; i--) {
                extrapolatedValue = sequencesPerHistory.get(i)[0] - extrapolatedValue;
            }
        }

        return extrapolatedValue;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day9Input.txt"));
        String input;
        List<long[]> histories = new ArrayList<>();
        long[] currentHistory;

        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            currentHistory = Arrays.stream(input.split(" ")).mapToLong(Long::parseLong).toArray();
            histories.add(currentHistory);
        }

        int sumExtrapolatedValuesChallenge1 = 0;
        int sumExtrapolatedValuesChallenge2 = 0;

        for (long[] history : histories) {
            sumExtrapolatedValuesChallenge1 += sumExtrapolatedValues(generateSequences(history), true);
            sumExtrapolatedValuesChallenge2 += sumExtrapolatedValues(generateSequences(history), false);
        }

        System.out.println("Sum of extrapolated values in challenge 1 is: " + sumExtrapolatedValuesChallenge1);
        System.out.println("Sum of extrapolated values in challenge 2 is: " + sumExtrapolatedValuesChallenge2);
    }
}
