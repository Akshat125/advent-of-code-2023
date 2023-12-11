package day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static day07.CardType.*;

public class Challenge {
    private static Integer cardRank(String hand, boolean isChallenge1) {
        Map<Character, Integer> cardOccurrences = new HashMap<>();
        for (Character card : hand.toCharArray()) {
            if (cardOccurrences.containsKey(card)) {
                cardOccurrences.put(card, cardOccurrences.get(card) + 1);
            } else {
                cardOccurrences.put(card, 1);
            }
        }
        int occurrencesOfJ = cardOccurrences.containsKey('J') ? cardOccurrences.get('J') : 0;
        if (occurrencesOfJ == 0) isChallenge1 = true;

        boolean threeOfAKind = false;
        boolean twoOfAKind = false;
        char twoOfAKindWith = 0;
        int occurrences;
        for (Character card : cardOccurrences.keySet()) {
            occurrences = cardOccurrences.get(card);
            if (occurrences == 5) {
                return FIVE_OF_A_KIND.ordinal();
            } else if (occurrences == 4) {
                if (isChallenge1) return FOUR_OF_A_KIND.ordinal();
                return FIVE_OF_A_KIND.ordinal();
            } else if (occurrences == 3) {
                if (twoOfAKind) {
                    if (isChallenge1) return FULL_HOUSE.ordinal();
                    return FIVE_OF_A_KIND.ordinal();
                }
                threeOfAKind = true;
            } else if (occurrences == 2) {
                if (twoOfAKind) {
                    if (isChallenge1) return TWO_PAIR.ordinal();
                    if (twoOfAKindWith == 'J' || card == 'J') return FOUR_OF_A_KIND.ordinal();
                    return FULL_HOUSE.ordinal();
                }
                if (threeOfAKind) {
                    if (isChallenge1) return FULL_HOUSE.ordinal();
                    return FIVE_OF_A_KIND.ordinal();
                }
                twoOfAKind = true;
                twoOfAKindWith = card;
            }
        }

        if (isChallenge1)
            return threeOfAKind ? THREE_OF_A_KIND.ordinal() : (twoOfAKind ? ONE_PAIR.ordinal() : HIGH_CARD.ordinal());
        if (threeOfAKind) return FOUR_OF_A_KIND.ordinal();
        if (twoOfAKind) return THREE_OF_A_KIND.ordinal();

        return ONE_PAIR.ordinal();
    }

    private static int compareHands(String hand1, String hand2, DeckOfCards cards, boolean isChallenge1) {
        int hand1Rank = cardRank(hand1, isChallenge1);
        int hand2Rank = cardRank(hand2, isChallenge1);

        if (hand1Rank > hand2Rank) {
            return 1;
        } else if (hand1Rank < hand2Rank) {
            return -1;
        } else {
            for (int i = 0; i < hand1.length(); i++) {
                if (hand1.charAt(i) != hand2.charAt(i)) {
                    return cards.compareCards(hand1.charAt(i), hand2.charAt(i)) > 0 ? 1 : -1;
                }
            }
        }

        return 0;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/resources/day7Input.txt"));
        String input;
        Bid bid;
        List<Bid> bidsChallenge1 = new ArrayList<>();
        List<Bid> bidsChallenge2 = new ArrayList<>();
        DeckOfCards deckOfCards1 = new DeckOfCards(true);
        DeckOfCards deckOfCards2 = new DeckOfCards(false);

        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            bid = new Bid(input.split(" ")[0], Long.parseLong(input.split(" ")[1]));
            bidsChallenge1.add(bid);
            bidsChallenge2.add(bid);
        }

        bidsChallenge1.sort((x, y) -> compareHands(x.hand, y.hand, deckOfCards1, true));
        bidsChallenge2.sort((x, y) -> compareHands(x.hand, y.hand, deckOfCards2, false));

        long totalWinningsChallenge1 = 0;
        long totalWinningsChallenge2 = 0;
        for (int rank = 1; rank <= bidsChallenge1.size(); rank++) {
            totalWinningsChallenge1 += rank * bidsChallenge1.get(rank - 1).bid;
            totalWinningsChallenge2 += rank * bidsChallenge2.get(rank - 1).bid;
        }

        System.out.println("The total winnings for challenge 1 are: " + totalWinningsChallenge1);
        System.out.println("The total winnings for challenge 2 are: " + totalWinningsChallenge2);
    }
}
