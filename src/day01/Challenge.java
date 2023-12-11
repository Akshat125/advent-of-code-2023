package day01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Challenge {
    private static String parseSpelledOutNumbers(String input) {
        input = input.replaceAll("one", "o1e");
        input = input.replaceAll("two", "t2");
        input = input.replaceAll("three", "t3");
        input = input.replaceAll("four", "4");
        input = input.replaceAll("five", "5e");
        input = input.replaceAll("six", "6");
        input = input.replaceAll("seven", "7n");
        input = input.replaceAll("eight", "8");
        input = input.replaceAll("nine", "9");
        return input;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day1Input.txt"));
        int sumCalibrationValues = 0;
        String input;
        int inputValue = 0;
        int lastDigit = 0;
        boolean isFirstInteger = true;

        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            input = parseSpelledOutNumbers(input);

            for (char c : input.toCharArray()) {
                if (Character.isDigit(c)) {
                    if (isFirstInteger) {
                        inputValue += 10 * Character.getNumericValue(c);
                        lastDigit = c;
                        isFirstInteger = false;
                    } else {
                        lastDigit = c;
                    }
                }
            }

            inputValue += Character.getNumericValue(lastDigit);
            sumCalibrationValues += inputValue;
            inputValue = 0;
            isFirstInteger = true;
        }

        System.out.println("The sum of calibration values is: " + sumCalibrationValues);
    }

}
