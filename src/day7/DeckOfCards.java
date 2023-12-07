package day7;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class DeckOfCards {
    Map<Character, Integer> cardStrengths;

    public DeckOfCards(boolean isChallenge1) {
        this.cardStrengths = new HashMap<>();

        cardStrengths.put('A', 12);
        cardStrengths.put('K', 11);
        cardStrengths.put('Q', 10);

        if (isChallenge1) {
            cardStrengths.put('J', 9);
            cardStrengths.put('T', 8);
            for (int i = 2; i < 10; i++) cardStrengths.put(Character.forDigit(i, 10), i - 2);
        } else {
            cardStrengths.put('T', 9);
            for (int i = 2; i < 10; i++) cardStrengths.put(Character.forDigit(i, 10), i - 1);
            cardStrengths.put('J', 0);
        }
    }

    public int compareCards(Character card1, Character card2) {
        if (!cardStrengths.containsKey(card1) || !cardStrengths.containsKey(card2)) throw new NoSuchElementException();

        return Integer.compare(cardStrengths.get(card1), cardStrengths.get(card2));
    }
}
