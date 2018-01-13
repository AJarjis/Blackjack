/** ***************************************************************************
 *
 * File        : Hand.java
 *
 * Date        : 14-Dec-2017
 *
 * Description : A class that models a player's card hand.
 *
 * Author      : Ali Jarjis
 *
 ***************************************************************************** */
package question2;

import question2.Card.CompareAscending;
import question2.Card.Rank;
import question2.Card.Suit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Ali Jarjis
 */
public class Hand implements Serializable, Iterable<Card> {

    /**
     * Hand's unique ID for Serialisation
     */
    private static final long serialVersionUID = 102;

    /**
     * Stores a player's hand
     *
     * @serial
     */
    private ArrayList<Card> handCards;

    /**
     * A HashMap which stores the name of each rank as the key, and how many are
     * in the hand as the value
     *
     * @serial
     */
    HashMap<String, Integer> rankCount;

    /**
     * A HashMap which stores the name of each suit as the key, and how many are
     * in the hand as the value
     *
     * @serial
     */
    HashMap<String, Integer> suitCount;

    /**
     * Stores all the possible total values a hand can have
     *
     * @serial
     */
    private int[] totalValues;  //TODO: consider making this resizable for ease

    /**
     * Default constructor, creates an empty hand
     */
    public Hand() {
        this.handCards = new ArrayList<>();
        this.totalValues = new int[5];

        // Initialises the hashtable which stores the count for each rank
        rankCount = new HashMap<>();

        for (Rank r : Rank.values()) {
            rankCount.put(r.name(), 0);
        }

        // Initialises the hashtable which stores the count for each suit
        suitCount = new HashMap<>();

        for (Suit s : Suit.values()) {
            suitCount.put(s.name(), 0);
        }

    }

    /**
     * Constructor which takes an array of cards and adds them to the hand
     *
     * @param cards an array of cards to add to hand
     */
    public Hand(Card[] cards) {
        this();

        for (Card c : cards) {
            Hand.this.add(c);
        }

    }

    /**
     * Constructor which takes a different hand and adds cards to this hand
     *
     * @param hand hand to add
     */
    public Hand(Hand hand) {
        this();

        add(hand);
    }

    /**
     * Updates the total values when a card is added/removed to hand, including
     * counts of ACE as high and low
     *
     * @param card card being added to hand
     * @param increaseValue true if adding a card to hand else false if removing
     */
    public void updateTotalValues(Card card, Boolean increaseValue) {
        String rankKey = card.getRank().name();
        String suitKey = card.getSuit().name();

        if (increaseValue) {
            // Increases the rank's count and totalValue[0]
            rankCount.put(rankKey, rankCount.get(rankKey) + 1);
            suitCount.put(suitKey, suitCount.get(suitKey) + 1);
            totalValues[0] += card.getRank().getVALUE();
        } else {
            // Decreases the rank's count and totalValue[0]
            rankCount.put(rankKey, rankCount.get(rankKey) - 1);
            suitCount.put(suitKey, suitCount.get(suitKey) - 1);
            totalValues[0] -= card.getRank().getVALUE();
        }

        int aces = rankCount.get("ACE");

        // Loops through the totalValues updating other possible values of hand
        for (int i = 1; i < (totalValues.length - 1); i++) {
            // For each ace sets other possible value else default to 0 
            if (i <= aces) {
                totalValues[i] = totalValues[i - 1] - 10;
            } else {
                totalValues[i] = 0;
            }
        }
    }

    /**
     * Retrieves the total possible values of this hand
     *
     * @return total values
     */
    public int[] getTotalValues() {
        return totalValues;
    }

    /**
     * Adds a single card to hand
     *
     * @param card card to add
     */
    public final void add(Card card) {
        this.handCards.add(card);

        updateTotalValues(card, true);
    }

    /**
     * Adds a collection of cards to the hand
     *
     * @param cards collection to add
     */
    public final void add(Collection<Card> cards) {
        for (Card c : cards) {
            add(c);
        }
    }

    /**
     * Adds all the cards in one hand to this hand
     *
     * @param hand hand to add
     */
    public final void add(Hand hand) {
        for (Card c : hand) {
            add(c);
        }
    }

    /**
     * Removes a given card from the deck
     *
     * @param card card to remove
     * @return true if successfully removed, false if not found
     */
    public boolean remove(Card card) {
        if (this.handCards.remove(card)) {
            updateTotalValues(card, false);

            return true;
        }

        return false;
    }

    /**
     * Removes every card from a hand, returns true if successful
     *
     * @param hand the hand to remove cards from
     * @return true if successfully removed all cards, else false if failed to
     * remove on even one card
     */
    public static boolean remove(Hand hand) {
        Iterator i = hand.iterator();

        //Loop through hand removing each card until empty
        while (i.hasNext()) {
            //Retrieve from index 0 as removal keeps shifting cards
            Card currentCard = hand.handCards.get(0);

            if (!hand.remove(currentCard)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Removes a card at a specific position in the hand and returns the card
     *
     * @param index
     * @return
     */
    public Card remove(int index) {
        Card removedCard = this.handCards.get(index);
        this.remove(removedCard);

        return removedCard;
    }

    /**
     * Iterator allows for traversal of the hand in the order they were added
     *
     * @return iterator for the hand
     */
    @Override
    public Iterator<Card> iterator() {
        return handCards.iterator();
    }

    /**
     * Sorts a hand into descending order, first by rank then suit
     */
    public void sortDescending() {
        Comparator descending = new Card.CompareAscending();
        handCards.sort(descending);
        
        Collections.reverse(handCards);
    }

    /**
     * Sorts a hand into ascending order, first by rank then suit
     */
    public void sortAscending() {
        Comparator ascending = new CompareAscending();

        handCards.sort(ascending);
    }

    /**
     * Returns the number of cards of a given suit in the hand
     *
     * @param suit suit to search for
     * @return the amount of cards of a given suit in the hand
     */
    public int countSuit(Suit suit) {
        return suitCount.get(suit.name());
    }

    /**
     * Returns the number of cards of a given rank in the hand
     *
     * @param rank rank to search for
     * @return the amount of cards of a given rank in the hand
     */
    public int countRank(Rank rank) {
        return rankCount.get(rank.name());
    }

    /**
     * Returns a string of the hand in a more readable format.
     *
     * @return String containing each card in hand printed on a separate line
     */
    @Override
    public String toString() {
        StringBuilder handDetails = new StringBuilder();

        // Appends each card in hand to handDetails
        for (Card c : this.handCards) {
            handDetails.append(c.toString());
            handDetails.append("\n");
        }

        return handDetails.toString();
    }

    /**
     * Checks if the lowest possible hand value is greater than a given value
     *
     * Clarification: This simple code works due to the fact that the amount of
     * possible values is determined by the amount of aces + 1, totalValues is
     * also sorted in decreasing order. Therefore totalValues[aces] will always
     * retrieve the lowest possible hand value.
     *
     * @param value integer value to compare hand to
     * @return true if value is less than lowest possible hand value, else false
     */
    public boolean isOver(int value) {
        int aces = rankCount.get("ACE");

        return totalValues[aces] > value;
    }

    /**
     * Returns a hand with the cards in the reverse order
     *
     * @return reversed hand
     */
    public Hand reverseHand() {
        Hand copy = new Hand(this);

        Collections.reverse(copy.handCards);

        return copy;
    }

}
