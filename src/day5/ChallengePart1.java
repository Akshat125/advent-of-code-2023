package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChallengePart1 {
    public static Map<Range, Range> populateMap(Map<Range, Range> map, long destinationRangeStart, long sourceRangeStart, long rangeLength) {
        map.put(new Range(sourceRangeStart, rangeLength), new Range(destinationRangeStart, rangeLength));
        return map;
    }

    static long getLowestLocationForSeed(Map<Range, Range>[] maps, long lowestLocation, long seed) {
        for (int j = 0; j < maps.length; j++) {
            // If no element is found, destination and source seed remain equal
            for (Range range : maps[j].keySet()) {
                if (seed >= range.start && seed < range.start + range.length) {
                    seed = maps[j].get(range).start + (seed - range.start);
                    break;
                }
            }
        }

        lowestLocation = Math.min(seed, lowestLocation);
        return lowestLocation;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day5Input.txt"));
        String input;
        String[] params;
        long[] initialSeeds = Arrays.stream(scanner.nextLine().substring(7).split(" ")).mapToLong(x -> Long.parseLong(x)).toArray();
        scanner.nextLine();
        Map<Range, Range>[] maps = new Map[7];

        for (int i = 0; i < maps.length; i++) {
            maps[i] = new HashMap<>();
            scanner.nextLine();

            while (scanner.hasNextLine() && !(input = scanner.nextLine()).equals("")) {
                params = input.split(" ");
                maps[i] = populateMap(maps[i], Long.parseLong(params[0]), Long.parseLong(params[1]), Long.parseLong(params[2]));
            }
        }

        long lowestLocation = Long.MAX_VALUE;
        long seed;
        for (int i = 0; i < initialSeeds.length; i++) {
            seed = initialSeeds[i];
            lowestLocation = getLowestLocationForSeed(maps, lowestLocation, seed);
        }

        System.out.println("The lowest location number is: " + lowestLocation);
    }
}
