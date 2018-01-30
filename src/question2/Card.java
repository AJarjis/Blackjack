/******************************************************************************
 *
 * File        : Card.java
 *
 * Date        : 14-Dec-2017
 *
 * Description : A class to model a playing card.
 *
 * Author      : Ali Jarjis
 *
 ******************************************************************************/
package question2;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Ali Jarjis
 */
public class Card implements Serializable, Comparable<Card> {
    
    /**
     * Card's unique ID for Serialisation
     */
    private static final long serialVersionUID = 111;

    /**
     * Enum class for a card's suit
     */
    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES
    };

    /**
     * Enum class for a card's rank
     */
    public enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
        NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        /**
         * Value equated with a rank
         */
        private final int VALUE;

        /**
         * Creates an instance of a rank with the given value.
         *
         * @param value The value equated with that rank.
         */
        Rank(int value) {
            this.VALUE = value;
        }

        /**
         * Retrieves the previous rank, i.e. FOUR returns THREE
         *
         * @return The previous rank
         */
        public Rank getPrevious() {
            int preIndex = Math.floorMod((ordinal() - 1), values().length);

            return values()[preIndex];
        }

        /**
         * Retrieves the value of a rank.
         *
         * @return Equated value of the rank.
         */
        public int getVALUE() {
            return this.VALUE;
        }
    };

    /**
     * Stores the rank of a card
     */
    private final Rank rank;
    
    /**
     * Stores the suit of a card
     */
    private final Suit suit;

    /**
     * Constructor for the card class which creates a card with a rank & suit
     *
     * @param rank the rank of the card
     * @param suit the suit of the card
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Compares two cards by rank and suit
     *
     * @param card card to compare to
     * @return 0 if cards are equal, positive number if card is less than the
     * card passed, negative number if card is greater than the card passed
     */
    @Override
    public int compareTo(Card card) {
        int card1Rank = this.rank.ordinal();
        int card1Suit = this.suit.ordinal();

        int card2Rank = card.rank.ordinal();
        int card2Suit = card.suit.ordinal();

        int rankDiff = card2Rank - card1Rank;

        //Compare suit if rank equal
        if (rankDiff == 0) {
            return card2Suit - card1Suit;
        }

        return rankDiff;
    }

    /**
     * Retrieves the rank of a card.
     *
     * @return Card rank
     */
    public Rank getRank() {
        return this.rank;
    }

    /**
     * Retrieves the suit of a card.
     *
     * @return Card suit
     */
    public Suit getSuit() {
        return this.suit;
    }

    /**
     * Returns the card value in a printable format, e.g. ACE OF SPADES, THREE
     * OF DIAMONDS, etc
     *
     * @return Card Value in printable format
     */
    @Override
    public String toString() {
        return this.rank + " OF " + this.suit;
    }

    /**
     * Sums the value of two cards.
     *
     * @param card1 Card to be added
     * @param card2 Second card to be added
     * @return      Sum value of the two cards
     */
    public static int sum(Card card1, Card card2) {
        return card1.rank.getVALUE() + card2.rank.getVALUE();
    }

    /**
     * Checks if sum value of two cards equal 21(blackjack)
     *
     * @param card1 Card to be added
     * @param card2 Second card to be added
     * @return      true if cards equal 21, false otherwise
     */
    public static boolean isBlackjack(Card card1, Card card2) {
        final int BLACKJACK = 21;

        return sum(card1, card2) == BLACKJACK;
    }

    /**
     * Comparator class for sorting cards in ascending order by rank then suit
     */
    public static class CompareAscending implements Comparator<Card> {

        /**
         * Compares two cards by rank then suit.
         *
         * @param card1 Card to compare
         * @param card2 Second card to compare
         * @return 0 if cards are equal, positive number if card1 is greater
         * than the card2, negative number if card1 is less than the card2
         */
        @Override
        public int compare(Card card1, Card card2) {
            int card1Rank = card1.rank.ordinal();
            int card1Suit = card1.suit.ordinal();

            int card2Rank = card2.rank.ordinal();
            int card2Suit = card2.suit.ordinal();

            int rankDiff = card1Rank - card2Rank;

            //Compare suit if rank equal
            if (rankDiff == 0) {
                return card1Suit - card2Suit;
            }

            return rankDiff;
        }
    }

    
    /**
     * Comparator class for sorting cards in ascending order by suit then rank
     */
    public static class CompareSuit implements Comparator<Card> {

        /**
         * Compares two cards by suit then rank.
         *
         * @param card1 Card to compare
         * @param card2 Second card to compare
         * @return 0 if cards are equal, positive number if card1 is greater
         * than the card2, negative number if card1 is less than the card2
         */
        @Override
        public int compare(Card card1, Card card2) {
            int card1Rank = card1.rank.ordinal();
            int card1Suit = card1.suit.ordinal();

            int card2Rank = card2.rank.ordinal();
            int card2Suit = card2.suit.ordinal();

            int suitDiff = card1Suit - card2Suit;

            //Compare rank if suit equal
            if (suitDiff == 0) {
                return card1Rank - card2Rank;
            }

            return suitDiff;
        }
    }
}
