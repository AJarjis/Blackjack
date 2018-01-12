/** ***************************************************************************
 *
 * File        : Card.java
 *
 * Date        : 14-Dec-2017
 *
 * Description : A class to model a playing card.
 *
 * Author      : Ali Jarjis
 *
 ***************************************************************************** */
package question2;

import question1.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
            return VALUE;
        }
    };

    /**
     * Stores the rank of a card
     * 
     * @serial
     */
    private Rank rank;
    
    /**
     * Stores the suit of a card
     * 
     * @serial 
     */
    private Suit suit;

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
     * @return 0 if cards are equal, positive number if card is greater than the
     * card passed, negative number if card is less than the card passed
     */
    @Override
    public int compareTo(Card card) {
        int card1Rank = this.rank.ordinal();
        int card1Suit = this.suit.ordinal();

        int card2Rank = card.rank.ordinal();
        int card2Suit = card.suit.ordinal();

        int rankDiff = card1Rank - card2Rank;

        //Compare suit if rank equal
        if (rankDiff == 0) {
            return card1Suit - card2Suit;
        }

        return rankDiff;
    }

    /**
     * Retrieves the rank of a card.
     *
     * @return rank Card rank
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Retrieves the suit of a card.
     *
     * @return suit Card suit
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Returns the card value in a printable format, e.g. ACE OF SPADES, THREE
     * OF DIAMONDS, etc
     *
     * @return Card Value in printable format
     */
    @Override
    public String toString() {
        return rank + " OF " + suit;
    }

    /**
     * Sums the value of two cards.
     *
     * @param card1 Card to be added
     * @param card2 Second card to be added
     * @return Sum value of the two cards
     */
    public static int sum(Card card1, Card card2) {
        return card1.rank.getVALUE() + card2.rank.getVALUE();
    }

    /**
     * Checks if sum value of two cards equal 21(blackjack)
     *
     * @param card1 Card to be added
     * @param card2 Second card to be added
     * @return - true if cards equal 21, - false otherwise
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
            return card1.compareTo(card2);
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

    public static void main(String[] args) {
        /* 
            Practice cards for testing, also have the added purpose of
            testing the card and enum constructors.
         */
        Rank myRank1 = Rank.QUEEN;
        Suit mySuit1 = Suit.CLUBS;
        Card myCard1 = new Card(myRank1, mySuit1);

        Rank myRank2 = Rank.QUEEN;
        Suit mySuit2 = Suit.SPADES;
        Card myCard2 = new Card(myRank2, mySuit2);

        /* Test for suit enums */
        for (Suit s : Suit.values()) {
            System.out.println(s);
        }

        /* Test for rank enums with values */
        for (Rank r : Rank.values()) {
            System.out.println(r + "(" + r.getVALUE() + ")");
        }

        /* Test for retrieving previous rank */
        System.out.println(myRank1.getPrevious());

        /* Test for comparing two cards */
        System.out.println(myCard1.compareTo(myCard2));

        /* Test for accessor methods*/
        System.out.println(myCard1.getRank());
        System.out.println(myCard1.getSuit());

        /* Test for toString method */
        System.out.println(myCard1.toString());

        /* Test for sum method */
        System.out.println(sum(myCard1, myCard2));

        /* Test for isBlackjack method */
        System.out.println(isBlackjack(myCard1, myCard2));

        /* 
            The following generates a deck of cards to allow for better 
            testing of the two comparator classes. 
         */
        List<Card> myDeck = new ArrayList<>();

        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                Card newCard = new Card(r, s);

                myDeck.add(newCard);
            }
        }

        /* Test for CompareAscending method */
        Comparator compAsc = new CompareAscending();
        myDeck.sort(compAsc);

        for (Card c : myDeck) {
            System.out.println(c.toString());
        }

        /* Test for CompareSuit method */
        Comparator compSuit = new CompareSuit();

        myDeck.sort(compSuit);

        for (Card c : myDeck) {
            System.out.println(c.toString());
        }
    }
}
