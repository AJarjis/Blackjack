/*****************************************************************************

File        : IntermediatePlayer.java

Date        : 14-Dec-2017

Description : A class that models an intermediate blackjack player.

Author      : Ali Jarjis

******************************************************************************/

package question2;

import java.util.ArrayList;
import question1.Card;

/**
 *
 * @author Ali Jarjis
 */
public class IntermediatePlayer extends BasicPlayer {
    
    /**
     * Stores the dealer's card for the intermediate player to remember
     */
    private Card dealersCard;
    
    /**
     * Constructs an intermediate player
     */
    public IntermediatePlayer() {
        super();
    }
    
    /**
     * Determines whether the player wishes to take a card or not, adding the
     * card to the player's hand
     *
     * @return true if player wants another card, else false
     */
    @Override
    public boolean hit() {
        int cardThreshold = 17;

        ArrayList<Integer> handValues = this.playerHand.getTotalValues();
        int amountOfAces = this.playerHand.countRank(Card.Rank.ACE);
        
        // Change tactics if ace in hand
        if (amountOfAces > 0) {
            int softTotal = handValues.get(handValues.size() - 1);
            
            // Stick if soft total equals nine or ten
            if (softTotal == 9 || softTotal == 10) {
                return false;
            // Hit immediately if less than eight
            } else if (softTotal < 8) {
                return true;
            }
        }
        
        int dealerCardVal = this.dealersCard.getRank().getVALUE();
        final int DEALER_CARD_LIMIT = 7;
        
        // Lower threshold if dealer has low card
        if (dealerCardVal < DEALER_CARD_LIMIT) {
            cardThreshold = 12;
        }

        // Finds best value to stick on
        for (Integer val : handValues) {
            // Jumps to next value if bust
            if (val < BLACKJACK) {
                // Hit if threshold not reached
                return val < cardThreshold;
            }
        }

        return false;      // Returns false if no card in hand is less than 21
    }
    
    /**
     * Shows the player the dealer's card
     *
     * @param c dealer's first card
     */
    @Override
    public void viewDealerCard(Card c) {
        this.dealersCard = c;
    }
}
