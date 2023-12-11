package day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ChallengePart2 {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day6Input.txt"));
        long time = Long.parseLong(scanner.nextLine().replaceAll("\\s+", "").substring(5));
        long distance = Long.parseLong(scanner.nextLine().replaceAll("\\s+", "").substring(9));
        double boatSpeedMin = Math.floor((-time + Math.sqrt(Math.pow(time, 2) - 4.0 * distance)) / -2.0) + 1;
        double boatSpeedMax = Math.ceil((-time - Math.sqrt(Math.pow(time, 2) - 4.0 * distance)) / -2.0) - 1;
        double numberOfWaysToBeatARecord = (boatSpeedMax - boatSpeedMin) + 1;

        System.out.println("The number of ways to beat the record is: " + numberOfWaysToBeatARecord);
    }

}
