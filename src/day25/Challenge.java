package day25;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Challenge {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day25Input.txt"));
        String input;

        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
        }

    }
}
