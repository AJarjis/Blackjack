/** ***************************************************************************
 *
 * File        : Deck.java
 *
 * Date        : 14-Dec-2017
 *
 * Description : A class that models a deck of 52 cards.
 *
 * Author      : Ali Jarjis
 *
 ***************************************************************************** */
package question1;

import question1.Card.Rank;
import question1.Card.Suit;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Ali Jarjis
 */
public class Deck implements Iterable<Card>, Serializable {

    /**
     * Deck's unique ID for Serialisation
     */
    private static final long serialVersionUID = 112;

    /**
     * Stores a deck of cards
     */
    private LinkedList<Card> deckCards;

    /**
     * Constructs a deck of cards with all possible 52 cards
     */
    public Deck() {
        this.deckCards = new LinkedList<>();

        newDeck();
    }

    /**
     * Shuffles a deck of cards by looping through the entire deck swapping the
     * card with another random card, implemented from the Fisher-Yates shuffle
     * algorithm
     */
    public void shuffle() {
        Random randNum = new Random();

        //Loops through entire deck, swapping cards
        for (int i = deckCards.size() - 1; i > 0; i--) {
            int randomIndex = randNum.nextInt(i + 1);

            // Swaps the two cards
            Card temp = deckCards.get(i);
            deckCards.set(i, deckCards.get(randomIndex));
            deckCards.set(randomIndex, temp);
        }
    }

    /**
     * Removes the top card from deck and returns it
     *
     * @return card from top of deck
     */
    public Card deal() {
        return deckCards.removeFirst();
    }

    /**
     * Returns the amount of cards currently in this deck
     *
     * @return deck size
     */
    public int size() {
        return deckCards.size();
    }

    /**
     * Reinitialises the deck of cards
     */
    public final void newDeck() {
        deckCards.clear();

        //Loop through each suit
        for (Suit s : Suit.values()) {
            //Loop through each rank
            for (Rank r : Rank.values()) {
                Card newCard = new Card(r, s);

                deckCards.add(newCard);
            }
        }
    }

    /**
     * Iterator class for traversing every second card
     */
    private class SecondCardIterator implements Iterator<Card> {

        private int position = 0;

        /**
         * Checks if there is at least two more cards to traverse in the deck
         *
         * @return true if there is a card after the next, false if not
         */
        @Override
        public boolean hasNext() {
            return position < (deckCards.size() - 1);
        }

        /**
         * Retrieves the card after the next
         *
         * @return the card after the next if it exists else null
         */
        @Override
        public Card next() {
            if (hasNext()) {
                int index = position;

                position = position + 2;

                return deckCards.get(index);
            }
            return null;
        }

    }

    /**
     * Iterator allows for traversal of the deck in the order they will be dealt
     *
     * @return iterator for the deck
     */
    @Override
    public Iterator<Card> iterator() {
        return deckCards.iterator();
    }

    /**
     * Iterator allows for traversal of the deck in the order they will be dealt
     *
     * @return iterator for the deck
     */
    public Iterator<Card> secondCardIterator() {
        return new SecondCardIterator();
    }

    /**
     * De-serialises the deck of cards
     *
     * @param stream deck to de-serialise
     * @throws ClassNotFoundException   if class of serialised object could
     *                                  not be found
     * @throws IOException              if an I/O error occurs
     */
    private void readObject(ObjectInputStream stream)
            throws ClassNotFoundException, IOException {
        stream.defaultReadObject();

        deckCards = (LinkedList<Card>) stream.readObject();
    }

    /**
     * Serialises the deck of cards in the SecondCardIterator order
     *
     * @param stream deck to serialise
     * @throws IOException if an I/O error occurs
     */
    private void writeObject(ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();

        LinkedList<Card> secondCardDeck = new LinkedList<>();
        Iterator<Card> secondCardIterator = this.secondCardIterator();
        
        // Adds every second card to the new list to be saved
        while (secondCardIterator.hasNext()) {
            Card c = secondCardIterator.next();
            secondCardDeck.add(c);
        }
        
        stream.writeObject(secondCardDeck);

    }

    /**
     * Main method for testing methods of the deck class
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Testing for constructor */
        Deck myDeck = new Deck();

        // Iterator testing to print out new deck
        System.out.println("Constructed myDeck:");
        for (Card c : myDeck) {
            System.out.println(c);
        }

        System.out.println("\n");

        /* Testing for shuffle method*/
        myDeck.shuffle();

        System.out.println("Shuffled myDeck:");
        for (Card c : myDeck) {
            System.out.println(c);
        }

        System.out.println("\n");

        /* Testing for deal method */
        System.out.println("Card Dealt: " + myDeck.deal());

        /* Testing for size method, also proves that deal() removed the card */
        System.out.println("myDeck size: " + myDeck.size());

        /* Testing for newDeck method */
        myDeck.newDeck();
        System.out.println("New myDeck size: " + myDeck.size());

        System.out.println("\n");

        /* Testing for Serializable and SecondCardIterator */
        String file = "deck.ser";

        writeToFile(myDeck, file);

        Deck storedDeck = (Deck) readFromFile(file);

        System.out.println("Stored Deck in SecondCardIterator Order:");
        for (Card c : storedDeck) {
            System.out.println(c);
        }
    }

    /**
     * Writes data to a file
     *
     * @param data data to write
     * @param filename file where data will be written
     */
    public static void writeToFile(Serializable data, String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(data);
            objectOut.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Reads data from a file
     *
     * @param filename name of file to read from
     * @return the data read from file stored as an object
     */
    public static Object readFromFile(String filename) {
        Object data = null;

        try {
            FileInputStream fileInput = new FileInputStream(filename);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            data = objectInput.readObject();
            objectInput.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return data;

    }
}
