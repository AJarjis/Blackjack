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
     */
    private ArrayList<Card> handCards;
    
    /**
     * Stores a duplicate of the player's hand in the original order cards were
     * added to the hand
     */
    private ArrayList<Card> originalHand;

    /**
     * A HashMap which stores the name of each rank as the key, and how many are
     * in the hand as the value
     */
    HashMap<String, Integer> rankCount;

    /**
     * A HashMap which stores the name of each suit as the key, and how many are
     * in the hand as the value
     */
    HashMap<String, Integer> suitCount;

    /**
     * Stores all the possible total values a hand can have
     */
    private ArrayList<Integer> totalValues;

    /**
     * Default constructor, creates an empty hand
     */
    public Hand() {
        this.handCards = new ArrayList<>();
        this.originalHand = new ArrayList<>();
        this.totalValues = new ArrayList<>();
        this.totalValues.add(0);

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
        int newTotal;
        int changeVal;

        if (increaseValue) {
            // Increases the rank/suit count and totalValue
            changeVal = 1;
            newTotal = totalValues.get(0) + card.getRank().getVALUE();
        } else {
            // Decreases the rank/suit count and totalValue
            changeVal = -1;
            newTotal = totalValues.get(0) - card.getRank().getVALUE();
        }
        
        // Update rank/suit count
        rankCount.put(rankKey, rankCount.get(rankKey) + (changeVal));
        suitCount.put(suitKey, suitCount.get(suitKey) + (changeVal));
        
        // Reset total values for new value
        totalValues.clear();
        totalValues.add(newTotal);

        int acesFound = rankCount.get("ACE");

        // For each ace in hand sets other possible value
        for (int i = 1; i < acesFound + 1; i++) {
            int otherVal = totalValues.get(i-1) - 10;
            totalValues.add(i, otherVal);
        }
    }

    /**
     * Retrieves the total possible values of this hand
     *
     * @return total values
     */
    public ArrayList<Integer> getTotalValues() {
        return totalValues;
    }

    /**
     * Adds a single card to hand
     *
     * @param card card to add
     */
    public final void add(Card card) {
        this.handCards.add(card);
        this.originalHand.add(card);

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
            this.originalHand.remove(card);
            
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
     * @param index position where to remove card from
     * @return      removed card
     */
    public Card remove(int index) {
        Card removedCard = this.handCards.get(index);
        this.remove(removedCard);

        return removedCard;
    }
    
    /**
     * Iterator for traversing the hand in the initial order it was added
     */
    private class standardIterator implements Iterator<Card> {
        private int pos = 0;

        /**
         * Checks if there is a card after the current card
         * @return true if card exists, else false
         */
        @Override
        public boolean hasNext() {
            return pos < originalHand.size();
        }

        /**
         * Retrieves the card following this card
         * @return next card
         */
        @Override
        public Card next() {
            if (hasNext()) {
                return originalHand.get(pos++);
            }
            return null;
        }
        
    }

    /**
     * Iterator allows for traversal of the hand in the order they were added
     *
     * @return iterator for the hand
     */
    @Override
    public Iterator<Card> iterator() {
        return new standardIterator();
    }

    /**
     * Sorts a hand into descending order, first by rank then suit
     */
    public void sortDescending() {
        Collections.sort(handCards);
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
     * @param value integer value to compare hand to
     * @return true if value is less than lowest possible hand value, else false
     */
    public boolean isOver(int value) {
        int lastValue = totalValues.size() - 1;

        return totalValues.get(lastValue) > value;
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
    
    /**
     * Retrieves a card from the hand at a given position
     * 
     * @param i position of card to get
     * @return  card at position given
     */
    public Card getCard(int i) {
        return handCards.get(i);
    }
    
    /**
     * Retrieves all cards from the hand
     * 
     * @return  a list of cards in the hand
     */
    public ArrayList<Card> getAllCards() {
        return handCards;
    }
}
