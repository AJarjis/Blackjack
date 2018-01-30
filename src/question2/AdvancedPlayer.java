/** ***************************************************************************
 *
 * File        : AdvancedPlayer.java
 *
 * Date        : 14-Dec-2017
 *
 * Description : A class that models an advanced blackjack player.
 *
 * Author      : Ali Jarjis
 *
 ***************************************************************************** */
package question2;

import java.util.List;

/**
 *
 * @author Ali Jarjis
 */
public class AdvancedPlayer extends IntermediatePlayer {

    /**
     * Keeps track of the advanced player's card count
     */
    private int cardCount;

    /**
     * Constructs an advanced player
     */
    public AdvancedPlayer() {
        super();
        cardCount = 0;
    }

    /**
     * Makes a bet for the player, if possible else return 0
     *
     * @return the player's bet, 0 if can't make bet
     */
    @Override
    public int makeBet() {
        // Checks if player can make this bet
        if (this.balance >= DEFAULT_BET) {
            // Increases bet if game is to the advantage of this player
            if (this.cardCount > 0) {
                this.bet = this.cardCount * DEFAULT_BET;
            } else {
                this.bet = DEFAULT_BET;
            }
        } else {
            this.bet = 0;
        }

        return this.bet;
    }

    /**
     * Shows the player all the cards that were played after a hand is finished
     *
     * @param cards list of cards that were played
     */
    @Override
    public void viewCards(List<Card> cards) {
        final int LOW_CARD_VAL = 6;
        final int HIGH_CARD_VAL = 10;
        
        // Performs a basic form of card counting on each card played
        for (Card c : cards) {
            int cardVal = c.getRank().getVALUE();
            
            if (cardVal < LOW_CARD_VAL) {              // Increase card count
                this.cardCount++;
            } else if (cardVal >= HIGH_CARD_VAL) {     // Decrease card count
                this.cardCount--;
            }
        }
    }

    /**
     * Tells the player that the deck has been reshuffled
     */
    @Override
    public void newDeck() {
        this.cardCount = 0;
    }

}
