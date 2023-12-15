package day15;

import day08.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Challenge {

    private static int hashString(String sequence) {
        int currentValue = 0;

        for (char character : sequence.toCharArray()) {
            currentValue += character;
            currentValue *= 17;
            currentValue %= 256;
        }

        return currentValue;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day15Input.txt"));
        String input = scanner.nextLine();
        String[] initializationSequences = input.split(",");
        List<Pair<String, Long>>[] boxes = new List[256];
        for (int i = 0; i < boxes.length; i++) boxes[i] = new ArrayList<>();

        long sumResults = 0;
        int hash;
        long focalLength;
        String label;
        List<Pair<String, Long>> box;
        List<String> labels;

        for (String sequence : initializationSequences) {
            if (sequence.contains("=")) {
                label = sequence.split("=")[0];
                focalLength = Long.parseLong(sequence.split("=")[1]);
                hash = hashString(label);
                box = boxes[hash];
                labels = box.stream().map(x -> x.getLeft()).collect(Collectors.toList());
                if (labels.contains(label)) {
                    box.get(labels.indexOf(label)).setRight(focalLength);
                } else {
                    box.add(new Pair<>(label, focalLength));
                }
            } else if (sequence.contains("-")) {
                label = sequence.split("-")[0];
                hash = hashString(label);
                box = boxes[hash];
                labels = box.stream().map(x -> x.getLeft()).collect(Collectors.toList());
                if (labels.contains(label)) box.remove(labels.indexOf(label));
            }

            // Challenge 1
            sumResults += hashString(sequence);
        }

        // Challenge 2
        long sumFocusingPowers = 0;
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes[i].size(); j++) {
                sumFocusingPowers += (1 + i) * (j + 1) * boxes[i].get(j).getRight();
            }
        }

        System.out.println("The sum of the results is: " + sumResults);
        System.out.println("The sum of the focusing powers for all the lenses is: " + sumFocusingPowers);

    }
}
