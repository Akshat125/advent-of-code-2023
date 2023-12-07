package day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Challenge {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day4Input.txt"));
        String input;
        String[] allNums;
        Set<Integer> winningNums;
        Set<Integer> myNums;
        List<Integer> similaritiesPerCard = new ArrayList<>();
        List<Integer> instancesPerCard;
        int totalPoints = 0;
        int totalScratchCards;
        int roundPoints;
        int roundSimilarities;

        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            roundPoints = 0;
            roundSimilarities = 0;
            allNums = input.split(":")[1].split("\\|");
            winningNums = Arrays.asList(allNums[0].split(" ")).stream().filter(x -> x != "").mapToInt(x -> Integer.parseInt(x)).boxed().collect(Collectors.toSet());
            myNums = Arrays.asList(allNums[1].split(" ")).stream().filter(x -> x != "").mapToInt(x -> Integer.parseInt(x)).boxed().collect(Collectors.toSet());

            for (int num : myNums) {
                if (winningNums.contains(num)) {
                    roundPoints = roundPoints == 0 ? 1 : roundPoints * 2;
                    roundSimilarities++;
                }
            }

            totalPoints += roundPoints;
            similaritiesPerCard.add(roundSimilarities);
        }

        // Challenge 2
        instancesPerCard = new ArrayList<>(Collections.nCopies(similaritiesPerCard.size(), 1));
        for (int i = 0; i < instancesPerCard.size(); i++) {
            for (int j = i + 1; j <= i + similaritiesPerCard.get(i); j++) {
                instancesPerCard.set(j, instancesPerCard.get(j) + instancesPerCard.get(i));
            }
        }

        totalScratchCards = instancesPerCard.stream().reduce((x, y) -> x + y).get();
        System.out.println("Total numbers of points is: " + totalPoints);
        System.out.println("Total numbers of scratch cards is: " + totalScratchCards);
    }

}
