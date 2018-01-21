/** ***************************************************************************
 *
 * File        : BlackjackDealer.java
 *
 * Date        : 14-Dec-2017
 *
 * Description : A class that models a simple blackjack dealer.
 *
 * Author      : Ali Jarjis
 *
 ***************************************************************************** */
package question2;

import java.util.ArrayList;
import java.util.List;
import question1.*; //TODO: Include card, hand and deck in Q2

/**
 *
 * @author Ali Jarjis
 */
public class BlackjackDealer implements Dealer {

    /**
     * The deck of cards the dealer uses
     */
    private Deck dealerDeck;
    
    /**
     * A list of players that the dealer is currently in a game with
     */
    private List<Player> players;
    
    /**
     * The dealer's playing hand
     */
    private Hand dealerHand;
    
    /**
     * Stores the definition of what constitutes as blackjack
     */
    private static final int BLACKJACK = 21;
    
    /**
     * Constructs a blackjack dealer with a shuffled deck of cards
     */
    public BlackjackDealer() {
        this.dealerDeck = new Deck();
        this.dealerDeck.shuffle();
        
        this.dealerHand = new Hand();
        
        this.players = new ArrayList<>();
        
    }
    
    /**
     * Connects a collection of players to this dealer for a game
     * @param p     collection of players
     */
    @Override
    public void assignPlayers(List<Player> p) {
        this.players = p;
    }

    /**
     * Takes the bets for all assigned players
     */
    @Override
    public void takeBets() {
        for (Player p : players) {
            p.makeBet();
        }
    }
    
    /**
     * Re-stocks the dealers deck of cards if there are fewer than 
     * 1/4 of the total
     */
    public void restockDeck() {
        final int DECK_LIMIT = 52/4;
        
        // If deck size is too small creates new deck and shuffles
        if (this.dealerDeck.size() < DECK_LIMIT) {
            this.dealerDeck.newDeck();
            this.dealerDeck.shuffle();
            
            // Alerts all players that deck has been restocked
            for (Player p : players) {
                p.newDeck();
            }
        }
    }

    /**
     * Deals two cards to each player, and one to the dealer
     */
    @Override
    public void dealFirstCards() {
        restockDeck();
        
        for (Player p : players) {
            p.takeCard(this.dealerDeck.deal());
            p.takeCard(this.dealerDeck.deal());
        }
        
        this.dealerHand.add(this.dealerDeck.deal());
    }

    /**
     * Plays the hand of player, asking them if they wish to hit or stick
     * @param p     player to play hand of
     * @return      player's hand final score
     */
    @Override
    public int play(Player p) {
        /*  Gives the player a card if they wish to hit and 
            have not exceeded or totalled 21                 */
        while(p.hit() && p.getHandTotal() < BLACKJACK) {
            p.takeCard(this.dealerDeck.deal());
        }
        
        return p.getHandTotal();
    }

    /**
     * Dealer plays his hand, taking cards until their total is 17 or higher
     * @return      dealer's hand final score
     */
    @Override
    public int playDealer() {
        final int CARD_THRESHOLD = 17;  // Limit to where dealer will stick
        
        ArrayList<Integer> handValues = this.dealerHand.getTotalValues();
        
        // Loops through all possible hand values 
        for (int i = 0; i < handValues.size(); i++) {
            Integer val = handValues.get(i);
            
            if (val < CARD_THRESHOLD) {     // Dealer hits
                this.dealerHand.add(this.dealerDeck.deal());
                i = 0;
            } else if (val < BLACKJACK){    // Dealer sticks
                return val;
            }
        } 
        
        // Returns lowest possible hand value if hand is bust
        return handValues.get(handValues.size() - 1);
    }

    /**
     * Scores the hand of a player
     * @param h     player's hand to score
     * @return      the score of the player's hand
     */
    @Override
    public int scoreHand(Hand h) {
        ArrayList<Integer> handValues = h.getTotalValues();

        // Searches for highest val less than 21
        for (Integer val : handValues) {
            if (val <= BLACKJACK) {
                return val;
            }
        }

        // If no value is less than 21 returns the lowest possible value
        return handValues.get(handValues.size() - 1);
    }

    /**
     * At the end of the hand settles the bets for all players
     */
    @Override
    public void settleBets() {
        int dealerScore = playDealer();  // Dealer plays hand
        
        for (Player p : players) {
            int playerBet = p.getBet();
            int stake = 0;
            
            // Player loses bet 
            if (p.isBust() || dealerScore == BLACKJACK) {   
                stake = -playerBet;
            } else if (p.blackjack()) {  // If player has blackjack wins double
                stake = playerBet *  2;
            } else {
                int playerScore = p.getHandTotal();
                
                /*  Player wins/loses bet depending on score, 
                    tie results in neither a win or loss     */
                if (playerScore > dealerScore) {        // Player wins
                    stake = playerBet;
                } else if (playerScore < dealerScore) { // Player loses
                    stake = -playerBet;
                }
            }
            
            // After settling bet, if player has no funds is removed from game
            if (!p.settleBet(stake)) {
                this.players.remove(p);
            }
        }
    }

}
