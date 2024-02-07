package day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static day08.Challenge.lcm;

public class Challenge {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day20Input.txt"));
        String[] broadcastOutputs = null;
        LinkedHashMap<String, Module> modules = new LinkedHashMap<>();
        String rxInputModuleName = null;

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String left = input.split(" -> ")[0];
            String right = input.split(" -> ")[1];
            String[] outputs = right.split(", ");
            if (left.equals("broadcaster")) {
                broadcastOutputs = outputs;
            } else {
                char type = left.charAt(0);
                String name = left.substring(1);
                if (right.contains("rx")) rxInputModuleName = name;

                if (type == '%') {
                    modules.put(name, new Module<>(type, name, "off", outputs));
                } else if (type == '&') {
                    modules.put(name, new Module<>(type, name, new HashMap<String, Boolean>(), outputs));
                }
            }
        }

        // initialize memory for conjunction modules
        for (Map.Entry<String, Module> entry : modules.entrySet()) {
            for (String output : entry.getValue().outputs) {
                if (modules.containsKey(output) && modules.get(output).type == '&') {
                    if (modules.get(output).memory instanceof HashMap<?, ?>) {
                        Map<String, Boolean> memory = (HashMap<String, Boolean>) modules.get(output).memory;
                        memory.put(entry.getKey(), false);
                    }
                }
            }
        }

        int lowPulses = 0;
        int highPulses = 0;
        Queue<Pulse> pulsesQueue = new LinkedList<>();

        long buttonPresses = 0;
        String finalRxInputModuleName = rxInputModuleName;
        Set<String> conjunctionInputs = modules.values().stream().filter(x -> Arrays.asList(x.outputs).contains(finalRxInputModuleName)).map(x -> x.name).collect(Collectors.toSet());
        Set<String> seenInputs = new HashSet<>();
        List<Long> conjunctionInputCycleLengths = new ArrayList<>();
        boolean foundCycles = false;

        while (!foundCycles) {
            // low pulse sent to broadcaster
            lowPulses += 1;
            buttonPresses++;

            // Pulses sent by broadcaster
            for (String output : broadcastOutputs) pulsesQueue.add(new Pulse("broadcaster", output, false));

            while (!pulsesQueue.isEmpty()) {
                Pulse pulse = pulsesQueue.poll();
                lowPulses += pulse.isHigh ? 0 : 1;
                highPulses += pulse.isHigh ? 1 : 0;

                if (modules.containsKey(pulse.to)) {
                    Module module = modules.get(pulse.to);
                    boolean pulseIsHigh;

                    // Challenge Part 2
                    if (module.name == rxInputModuleName && pulse.isHigh && !seenInputs.contains(pulse.from)) {
                        conjunctionInputCycleLengths.add(buttonPresses);
                        seenInputs.add(pulse.from);
                        if (seenInputs.size() == conjunctionInputs.size()) {
                            foundCycles = true;
                            break;
                        }
                    }

                    // Challenge Part 1
                    if (module.type == '%' && !pulse.isHigh) {
                        if ((module.memory).equals("off")) {
                            module.memory = "on";
                            pulseIsHigh = true;
                        } else {
                            module.memory = "off";
                            pulseIsHigh = false;
                        }
                    } else if (module.type == '&' && module.memory instanceof HashMap<?, ?>) {
                        Map<String, Boolean> memory = (HashMap<String, Boolean>) module.memory;
                        memory.put(pulse.from, pulse.isHigh);
                        pulseIsHigh = !memory.values().stream().reduce((acc, x) -> acc & x).get();
                    } else {
                        continue;
                    }

                    for (String output : module.outputs) pulsesQueue.add(new Pulse(module.name, output, pulseIsHigh));
                }
            }
        }

        buttonPresses = lcm(conjunctionInputCycleLengths.stream().mapToLong(Long::longValue).toArray());
        System.out.println("Number of button presses required to deliver a low pulse to rx is: " + buttonPresses);
    }
}
