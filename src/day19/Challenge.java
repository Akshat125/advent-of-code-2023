package day19;

import day08.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Challenge {
    private static Map<String, Workflow> workflowMap = new HashMap<>();

    private static long countAcceptedRatings(Map<Character, Pair<Integer, Integer>> xmasRangeMap, String workflowId) {
        if (workflowId.equals("R")) return 0;
        if (workflowId.equals("A")) {
            long productRanges = 1;
            for (Pair<Integer, Integer> range : xmasRangeMap.values())
                productRanges *= (range.getRight() - range.getLeft() + 1);
            return productRanges;
        }

        Workflow workflow = workflowMap.get(workflowId);
        Pair<Integer, Integer> range;

        long count = 0;
        Pair<Integer, Integer> insideRange;
        Pair<Integer, Integer> outsideRange;
        Map<Character, Pair<Integer, Integer>> xmasRangeMapCopy;

        for (Rule rule : workflow.rules) {
            if (rule.category == null) {
                count += countAcceptedRatings(xmasRangeMap, rule.nextWorkflow);
                break;
            }

            range = xmasRangeMap.get(rule.category.charAt(0));
            if (rule.isLessThanThreshold) {
                insideRange = new Pair<>(range.getLeft(), Math.min(rule.threshold - 1, range.getRight()));
                outsideRange = new Pair<>(Math.max(rule.threshold, range.getLeft()), range.getRight());
            } else {
                insideRange = new Pair<>(Math.max(rule.threshold + 1, range.getLeft()), range.getRight());
                outsideRange = new Pair<>(range.getLeft(), Math.min(rule.threshold, range.getRight()));
            }

            if (insideRange.getLeft() <= insideRange.getRight()) {
                xmasRangeMapCopy = new HashMap<>(xmasRangeMap);
                xmasRangeMapCopy.put(rule.category.charAt(0), insideRange);
                count += countAcceptedRatings(xmasRangeMapCopy, rule.nextWorkflow);
            }
            if (outsideRange.getLeft() <= outsideRange.getRight()) {
                xmasRangeMap.put(rule.category.charAt(0), outsideRange);
            } else {
                break;
            }
        }

        return count;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day19Input.txt"));
        String input;
        String[] inputElements;
        String workflowId;
        List<String> rulesRaw;
        List<Rule> rules;

        while (!(input = scanner.nextLine()).equals("")) {
            input = input.substring(0, input.length() - 1);
            inputElements = input.split("\\{");
            workflowId = inputElements[0];
            rulesRaw = Arrays.stream(inputElements[1].split(",")).toList();
            rules = rulesRaw.stream().map(x -> {
                String category = null;
                Integer threshold = null;
                String nextWorkflow;
                Boolean isLessThanThreshold = null;

                if (x.contains(":")) {
                    if (x.contains("<")) {
                        isLessThanThreshold = true;
                        category = x.split("<")[0];
                        threshold = Integer.parseInt(x.split("<")[1].split(":")[0]);
                    } else {
                        isLessThanThreshold = false;
                        category = x.split(">")[0];
                        threshold = Integer.parseInt(x.split(">")[1].split(":")[0]);
                    }
                    nextWorkflow = x.split(":")[1];
                } else {
                    nextWorkflow = x;
                }

                if (nextWorkflow.equals("A")) {
                    return new Rule(category, isLessThanThreshold, threshold, "A");
                } else if (nextWorkflow.equals("R")) {
                    return new Rule(category, isLessThanThreshold, threshold, "R");
                } else {
                    return new Rule(category, isLessThanThreshold, threshold, nextWorkflow);
                }
            }).collect(Collectors.toList());

            workflowMap.put(workflowId, new Workflow(workflowId, rules));
        }

        // Challenge 1
        List<Part> parts = new ArrayList<>();
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            input = input.substring(1, input.length() - 1);

            parts.add(new Part(Integer.parseInt(input.split(",")[0].split("=")[1]), Integer.parseInt(input.split(",")[1].split("=")[1]), Integer.parseInt(input.split(",")[2].split("=")[1]), Integer.parseInt(input.split(",")[3].split("=")[1]), "in"));
        }

        List<Part> updatedParts;
        int sumRatingAllParts = 0;
        boolean isAcceptedOrRejected;

        while (!parts.isEmpty()) {
            updatedParts = new ArrayList<>();
            for (Part part : parts) {
                isAcceptedOrRejected = false;
                for (Rule rule : workflowMap.get(part.workflowId).rules) {
                    if (rule.category != null) {
                        if (rule.category.equals("x")) {
                            if (rule.isLessThanThreshold) {
                                if (part.x >= rule.threshold) continue;
                            } else {
                                if (part.x <= rule.threshold) continue;
                            }
                        } else if (rule.category.equals("m")) {
                            if (rule.isLessThanThreshold) {
                                if (part.m >= rule.threshold) continue;
                            } else {
                                if (part.m <= rule.threshold) continue;
                            }
                        } else if (rule.category.equals("a")) {
                            if (rule.isLessThanThreshold) {
                                if (part.a >= rule.threshold) continue;
                            } else {
                                if (part.a <= rule.threshold) continue;
                            }
                        } else if (rule.category.equals("s")) {
                            if (rule.isLessThanThreshold) {
                                if (part.s >= rule.threshold) continue;
                            } else {
                                if (part.s <= rule.threshold) continue;
                            }
                        }
                    }

                    if (rule.nextWorkflow.equals("A") || rule.nextWorkflow.equals("R")) {
                        if (rule.nextWorkflow.equals("A")) sumRatingAllParts += part.rating();
                        isAcceptedOrRejected = true;
                    } else {
                        part.workflowId = rule.nextWorkflow;
                    }
                    break;
                }

                if (!isAcceptedOrRejected) updatedParts.add(part);
            }

            parts = updatedParts;
        }

        System.out.println("The sum of rating of all parts in challenge 1 is: " + sumRatingAllParts);

        // Challenge 2 requires us to investigate (4000 x 4000 x 4000 x 4000) different parts.
        Map<Character, Pair<Integer, Integer>> xmasRangeMap = new HashMap<>();
        for (char key : "xmas".toCharArray()) xmasRangeMap.put(key, new Pair(1, 4000));
        long combinationOfAcceptedRatings = countAcceptedRatings(xmasRangeMap, "in");
        System.out.println("The combination of accepted ratings in challenge 2 is: " + combinationOfAcceptedRatings);
    }
}
