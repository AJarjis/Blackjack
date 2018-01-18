/** ***************************************************************************
 *
 * File        : BasicPlayer.java
 *
 * Date        : 14-Dec-2017
 *
 * Description : A class that models a basic blackjack player.
 *
 * Author      : Ali Jarjis
 *
 ***************************************************************************** */
package question2;

import java.util.List;
import question1.*; 

/**
 *
 * @author Ali Jarjis
 */
public class BasicPlayer implements Player {

    /**
     * Stores the player's hand
     */
    private Hand playerHand;

    /**
     * Stores the cash balance the player currently has
     */
    private int balance;

    /**
     * Stores the most recent bet made by the player
     */
    private int bet;

    /**
     * The default bet a basic player will always make
     */
    private static final int DEFAULT_BET = 10;

    /**
     * Stores the definition of what constitutes as blackjack
     */
    private static final int BLACKJACK = 21;

    /**
     * Constructor for a BasicPlayer
     */
    public BasicPlayer() {
        final int DEFAULT_BALANCE = 200;

        this.playerHand = new Hand();
        this.balance = DEFAULT_BALANCE;
        this.bet = 0;
    }

    /**
     * Clears the player's hand and returns the old hand
     *
     * @return player's previous hand
     */
    @Override
    public Hand newHand() {
        Hand oldHand = new Hand(this.playerHand);

        Hand.remove(this.playerHand);

        return oldHand;
    }

    /**
     * Makes a bet for the player, if possible else return 0
     *
     * @return      the player's bet, 0 if can't make bet
     */
    @Override
    public int makeBet() {
        // Checks if player can make this bet
        if (this.balance >= DEFAULT_BET) {
            bet = DEFAULT_BET;
        } else {
            bet = 0;
        }

        return bet;
    }

    /**
     * Retrieves the bet for this current hand
     *
     * @return player's current bet
     */
    @Override
    public int getBet() {
        return this.bet;
    }

    /**
     * Retrieves the player's cash balance
     *
     * @return player's cash balance
     */
    @Override
    public int getBalance() {
        return this.balance;
    }

    /**
     * Determines whether the player wishes to take a card or not, adding the
     * card to the player's hand
     *
     * @return true if player wants another card, else false
     */
    @Override
    public boolean hit() {
        final int CARD_THRESHOLD = 17;  // Limit to where player will stick

        int[] handValues = this.playerHand.getTotalValues();
        int acesInHand = this.playerHand.countRank(Card.Rank.ACE);

        // Loops through possible handValues checking if 
        // value is over 17 but under (or equal to) 21
        for (int i = 0; i < acesInHand + 1; i++) {
            if (handValues[i] < BLACKJACK) {
                if (handValues[i] < CARD_THRESHOLD) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;      // Returns false if no card in hand is less than 21
    }

    /**
     * Adds a card to the player's hand
     *
     * @param c card to add to hand
     */
    @Override
    public void takeCard(Card c) {
        this.playerHand.add(c);
    }

    /**
     * Settles the bet a player made by increasing or decreasing their balance
     *
     * @param p the player's wins/losses, positive if player won, negative if
     * player lost
     * @return true if player has enough funds for another game, else false
     */
    @Override
    public boolean settleBet(int p) {
        this.balance += (p);  // Adds winnings/subtract losses from bet

        return this.balance >= DEFAULT_BET;    // Checks if user has funds left
    }

    /**
     * Retrieves the highest hand total that is less than 21, if exists else the
     * lowest possible value
     *
     * @return highest hand total less than 21
     */
    @Override
    public int getHandTotal() {
        int[] handValues = this.playerHand.getTotalValues();
        int acesInHand = this.playerHand.countRank(Card.Rank.ACE);

        /*  For any aces in the hand checks for an alternative 
            value less than 21, initial loop checks for possiblity 
            of no aces                                           */
        for (int i = 0; i < acesInHand + 1; i++) {
            if (handValues[i] <= BLACKJACK) {
                return handValues[i];
            }
        }

        // If no value is less than 21 returns the lowest possible value
        return handValues[acesInHand];
    }

    /**
     * Checks if the player's hand is equal to 21 (Blackjack)
     *
     * @return true if hand is a blackjack else false
     */
    @Override
    public boolean blackjack() {
        return this.getHandTotal() == BLACKJACK;
    }

    /**
     * Checks if the player's hand has exceeded 21
     *
     * @return true if hand is a bust, else false
     */
    @Override
    public boolean isBust() {
        return this.playerHand.isOver(BLACKJACK);
    }

    /**
     * Retrieves the player's current hand
     *
     * @return the player's current hand
     */
    @Override
    public Hand getHand() {
        return this.playerHand;
    }

    // TODO: Think of something for these functions
    /**
     * Shows the player the dealer's card
     *
     * @param c dealer's first card
     */
    @Override
    public void viewDealerCard(Card c) {

    }

    /**
     * Shows the player all the cards that were played after a hand is finished
     *
     * @param cards list of cards that were played
     */
    @Override
    public void viewCards(List<Card> cards) {

    }

    /**
     * Tells the player that the deck has been reshuffled
     */
    @Override
    public void newDeck() {

    }
}
