package day23;

import day03.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day23Input.txt"));
        List<List<Character>> map = new ArrayList<>();
        List<Character> mapRow;
        Position start;
        Position end;

        char[] input;
        while (scanner.hasNextLine()) {
            mapRow = new ArrayList<>();
            input = scanner.nextLine().toCharArray();
            for (int j = 0; j < input.length; j++) {
                mapRow.add(input[j]);
            }
            map.add(mapRow);
        }
    }
}
