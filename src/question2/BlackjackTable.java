/** ***************************************************************************
 *
 * File        : BlackjackTable.java
 *
 * Date        : 14-Dec-2017
 *
 * Description : The main class which simulates a game of the card game blackjack.
 *
 * Author      : Ali Jarjis
 *
 ***************************************************************************** */
package question2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ali Jarjis
 */
public class BlackjackTable implements Serializable {

    /**
     * BlackjackTable's unique ID for Serialisation
     */
    private static final long serialVersionUID = 102;

    private BlackjackDealer dealer;
    private List<Player> players;

    /**
     * Maximum amount of players allowed at this table
     */
    private final int MAX_PLAYERS = 8;

    /**
     * Maximum bet a player can make
     */
    private final int MAX_BET = 500;

    /**
     * Minimum bet a player can make
     */
    private final int MIN_BET = 1;

    /**
     * Constructs a blackjack table with a dealer
     */
    public BlackjackTable() {
        this.dealer = new BlackjackDealer();
    }

    /**
     * Constructs a blackjack table with a dealer and a list of players
     */
    public BlackjackTable(List<Player> players) {
        this();

        this.players = players;
    }

    /**
     * Simulates a game of blackjack with basic players
     */
    public static void basicGame() {
        final int AMOUNT_OF_PLAYERS = 4;
        
        Scanner userScanner = new Scanner(System.in);
        boolean playAgain = false;
        int rounds = 1;

        // Creates and populates a list of basic players for the table
        List<Player> basicPlayers = new ArrayList<>();
        for (int i = 0; i < AMOUNT_OF_PLAYERS; i++) {
            Player p = new BasicPlayer();
            basicPlayers.add(p);
        }

        BlackjackTable table = new BlackjackTable(basicPlayers);

        gameLoop:
        do {
            // Plays a given amount of rounds of blackjack
            for (int i = 0; i < rounds; i++) {
                table.dealer.assignPlayers(table.players);

                table.dealer.takeBets();

                table.dealer.dealFirstCards();

                // Play each player's hand
                for (Player p : table.players) {
                    System.out.println("Basic Player Final Score: " 
                            + table.dealer.play(p));
                    System.out.println(p.getHand().toString());
                }

                // Play dealer's hand
                System.out.println("Dealer's Final Score: "
                        + table.dealer.playDealer());
                System.out.println(table.dealer.getHand().toString());

                table.dealer.settleBets();
                
                // Prints out outcome of round
                System.out.println("Current Players:");
                Iterator<Player> playerIt = table.players.iterator();
                while (playerIt.hasNext()) {
                    Player p = playerIt.next();
                    
                    System.out.println("Basic Player: £" + p.getBalance());
                    
                    // Removes players who have no more money
                    if (!p.settleBet(0)) {
                        playerIt.remove();
                    }
                }
                
                System.out.println("-----------------------------------------");
                
                if(table.players.isEmpty()) {
                    System.out.println("All players have run out of money!");
                    System.out.println("Would you like to continue the game "
                            + "with newly created players? (Y/N)");
                    
                    String emptyPlayersChoice = userScanner.next();
                    emptyPlayersChoice = emptyPlayersChoice.toUpperCase();
                    
                    if (emptyPlayersChoice.equals("Y")) {
                        // TODO: create players 
                    } else {
                        i = rounds;
                        break gameLoop;
                    }
                }
                
            }

            // User decides whether to continue, load, save or quit game
            System.out.println("What would you like to do?");
            System.out.println("1) Continue Playing");
            System.out.println("2) Load Game");
            System.out.println("3) Save Game");
            System.out.println("4) Quit");

            int userChoice = userScanner.nextInt();

            switch (userChoice) {
                case 1:   // Continue playing
                    do {
                        System.out.println("How many rounds would you "
                                + "like to play?");

                        if (userScanner.hasNextInt()) {
                            rounds = userScanner.nextInt();
                        }
                    } while (rounds < 0);

                    playAgain = true;

                    break;
                case 2:   // TODO: Load game

                    break;
                case 3:   // TODO: Save game

                    break;
                case 4:   // Quit playing
                default:
                    playAgain = false;
                    break;
            }

        } while (playAgain);
    }

    /**
     * Plays a game of blackjack with the user
     */
    public static void humanGame() {

    }

    /**
     * Simulates a game of blackjack with intermediate players
     */
    public static void intermediateGame() {

    }

    /**
     * Simulates a game of blackjack with advanced players
     */
    public static void advancedGame() {

    }

    //TODO: error checking for non integers
    /**
     * Performs a check on string to check if it is a number
     *
     * @param userInput string to test if is integer
     * @return true if is integer, else false
     */
    public Boolean isInteger(String userInput) {
        try {
            Integer.parseInt(userInput);
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

    /**
     * Main method for testing methods of the blackjackTable class
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        basicGame();
    }

}
