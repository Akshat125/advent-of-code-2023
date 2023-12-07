package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static day5.ChallengePart1.populateMap;

public class ChallengePart2 {
    // Instead of finding the lowest location per individual seed, a range of seed is used
    static long getLowestLocationForSeeds(Map<Range, Range>[] maps, List<Range> seeds) {
        long intersectionStart;
        long intersectionEnd;
        boolean matchFound;
        List<Range> newSeeds;
        Range source;

        for (int j = 0; j < maps.length; j++) {
            newSeeds = new ArrayList<>();
            while (!seeds.isEmpty()) {
                source = seeds.get(0);
                seeds.remove(0);
                matchFound = false;
                for (Range mapRange : maps[j].keySet()) {
                    intersectionStart = Math.max(source.start, mapRange.start);
                    intersectionEnd = Math.min(source.start + source.length, mapRange.start + mapRange.length);
                    if (intersectionStart < intersectionEnd) {
                        newSeeds.add(new Range(maps[j].get(mapRange).start + (intersectionStart - mapRange.start), intersectionEnd - intersectionStart));
                        if (intersectionStart > source.start) {
                            seeds.add(new Range(source.start, intersectionStart - source.start));
                        }
                        if (source.start + source.length > intersectionEnd) {
                            seeds.add(new Range(intersectionEnd, source.start + source.length - intersectionEnd));
                        }
                        matchFound = true;
                        break;
                    }
                }

                if (!matchFound) {
                    newSeeds.add(new Range(source.start, source.length));
                }
            }
            seeds = newSeeds;
        }

        return seeds.stream().map(x -> x.start).min(Long::compareTo).get();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day5Input.txt"));
        String input;
        String[] params;
        String[] initialSeedsString = scanner.nextLine().substring(7).split(" ");
        List<Range> initialSeeds = new ArrayList<>();

        for (int i = 0; i < initialSeedsString.length; i += 2) {
            initialSeeds.add(new Range(Long.parseLong(initialSeedsString[i]), Long.parseLong(initialSeedsString[i + 1])));
        }

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

        long lowestLocation = getLowestLocationForSeeds(maps, initialSeeds);

        System.out.println("The lowest location number is: " + lowestLocation);
    }
}
