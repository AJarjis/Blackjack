/** ****************************************************************************
 *
 * File        : HumanPlayer.java
 *
 * Date        : 14-Dec-2017
 *
 * Description : A class that models a human player in blackjack, allowing the
 *               user to play the game.
 *
 * Author      : Ali Jarjis
 *
 ***************************************************************************** */
package question2;

import java.util.List;
import java.util.Scanner;
import question1.Card;

/**
 *
 * @author Ali Jarjis
 */
public class HumanPlayer extends BasicPlayer {

    /**
     * The minimum bet a human player can make
     */
    private static final int MIN_BET = 1;

    /**
     * Constructs a human player
     */
    public HumanPlayer() {
        super();
    }

    /**
     * Asks the player what bet they would like to make
     *
     * @return the player's bet
     */
    @Override
    public int makeBet() {
        Scanner userScanner = new Scanner(System.in);

        bet = 0;
        
        System.out.println("Your Balance: £" + this.getBalance());
        
        // Keeps asking the player for their bet if they have money 
        // and have yet to make a legal bet
        while (this.balance >= MIN_BET && bet <= 0) {
            System.out.print("How much would you like to bet? £");

            if (userScanner.hasNextInt()) { //TODO: perform check if int
                bet = userScanner.nextInt();
            }
        };

        System.out.println("");
        
        return bet;
    }

    /**
     * Asks the player if they wish to take a card or not, adding the card to
     * the player's hand
     *
     * @return true if player wants another card, else false
     */
    @Override
    public boolean hit() {
        Scanner userScanner = new Scanner(System.in);

        String userChoice = "N";

        System.out.println("Your Hand: " + getHandTotal() + " Points");
        System.out.println(this.playerHand.toString());
        
        // If bust or blackjack player will stick
        if (!isBust() && !blackjack()) {
            System.out.print("Would you like to hit? (Y/N)");

            userChoice = userScanner.next();
            userChoice = userChoice.toUpperCase();
        }
        
        System.out.println("");
        
        return userChoice.equals("Y");
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
        
        // Prints new balance if change made to balance
        if (p > 0) {
            System.out.println("You won £" + p 
                    + "! Your New Balance: £" + this.balance);
        } else if (p < 0) {
            System.out.println("You lost £" + (p * -1) 
                    + "! Your New Balance: £" + this.balance);
        }

        return this.balance >= MIN_BET;    // Checks if user has funds left
    }

    /**
     * Shows the player the dealer's card
     *
     * @param c dealer's first card
     */
    @Override
    public void viewDealerCard(Card c) {
        System.out.println("Dealer's Card: " + c.toString() + "\n");
    }

    /**
     * Shows the player all the cards that were played after a hand is finished
     *
     * @param cards list of cards that were played
     */
    @Override
    public void viewCards(List<Card> cards) {
        System.out.println("Cards played in this round:");

        for (Card c : cards) {
            System.out.println(c.toString());
        }

        System.out.println("");
    }

    /**
     * Tells the player that the deck has been reshuffled
     */
    @Override
    public void newDeck() {
        System.out.println("Dealer has re-shuffled the deck!\n");
    }
}
