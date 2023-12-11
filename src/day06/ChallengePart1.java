package day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class ChallengePart1 {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day6Input.txt"));
        int[] times = Arrays.stream(scanner.nextLine().replaceAll("\\s+", " ").substring(6).split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] distances = Arrays.stream(scanner.nextLine().replaceAll("\\s+", " ").substring(10).split(" ")).mapToInt(Integer::parseInt).toArray();
        int numberOfWaysToBeatARecord = 1;
        double boatSpeedMin;
        double boatSpeedMax;

        for (int i = 0; i < times.length; i++) {
            // We can find the boat speed (equal to waiting time) using the following quadratic formula:
            // -b^2 + bt - d > 0
            boatSpeedMin = Math.floor((-times[i] + Math.sqrt(Math.pow(times[i], 2) - 4.0 * distances[i])) / -2.0) + 1;
            boatSpeedMax = Math.ceil((-times[i] - Math.sqrt(Math.pow(times[i], 2) - 4.0 * distances[i])) / -2.0) - 1;
            numberOfWaysToBeatARecord *= (boatSpeedMax - boatSpeedMin) + 1;
        }

        System.out.println("The product of different ways to beat the record is: " + numberOfWaysToBeatARecord);
    }

}
