package day12;

import day08.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Challenge {
    private static HashMap<Pair<String, List<Integer>>, BigInteger> dp = new HashMap<>();

    private static BigInteger countArrangements(String springConditions, List<Integer> damagedSpringCounts) {
        if (springConditions.equals("")) return damagedSpringCounts.size() != 0 ? BigInteger.ZERO : BigInteger.ONE;
        if (damagedSpringCounts.size() == 0) return springConditions.contains("#") ? BigInteger.ZERO : BigInteger.ONE;

        BigInteger count = BigInteger.ZERO;
        char firstSpringState = springConditions.charAt(0);
        String springConditionsSubstring;

        Pair<String, List<Integer>> key = new Pair<>(springConditions, damagedSpringCounts);
        if (dp.containsKey(key)) {
            return dp.get(key);
        }

        if (firstSpringState == '.' || firstSpringState == '?') {
            springConditionsSubstring = 1 < springConditions.length() ? springConditions.substring(1) : "";
            count = count.add(countArrangements(springConditionsSubstring, damagedSpringCounts));
        }

        if ((firstSpringState == '#' || firstSpringState == '?') && damagedSpringCounts.get(0) <= springConditions.length()
                && (damagedSpringCounts.get(0) == springConditions.length() || springConditions.charAt(damagedSpringCounts.get(0)) != '#')
                && !springConditions.substring(0, damagedSpringCounts.get(0)).contains(".")) {
            springConditionsSubstring = damagedSpringCounts.get(0) + 1 < springConditions.length() ? springConditions.substring(damagedSpringCounts.get(0) + 1) : "";
            count = count.add(countArrangements(springConditionsSubstring, damagedSpringCounts.subList(1, damagedSpringCounts.size())));
        }

        dp.put(key, count);
        return dp.get(key);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day12Input.txt"));
        String input;
        String springConditions;
        String springConditions2;
        List<Integer> damagedSpringCounts;
        List<Integer> damagedSpringCounts2;
        int sumCounts = 0;
        BigInteger sumCounts2 = BigInteger.ZERO;


        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            springConditions = input.split(" ")[0];
            damagedSpringCounts = Arrays.stream(input.split(" ")[1].split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            springConditions2 = springConditions + "?" + springConditions + "?" + springConditions + "?" + springConditions + "?" + springConditions;
            damagedSpringCounts2 = new ArrayList<>();

            for (int i = 0; i < 5; i++) damagedSpringCounts2.addAll(damagedSpringCounts);
            sumCounts += countArrangements(springConditions, damagedSpringCounts).intValue();
            sumCounts2 = sumCounts2.add(countArrangements(springConditions2, damagedSpringCounts2));
        }

        System.out.println("The sum of the counts in part 1 is: " + sumCounts);
        System.out.println("The sum of the counts in part 2 is: " + sumCounts2);

    }
}
