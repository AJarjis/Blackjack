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

    /**
     * Dealer associated with the table
     */
    private BlackjackDealer dealer;

    /**
     * Players currently playing on this table
     */
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

        // Creates and populates a list of basic players for the table
        List<Player> basicPlayers = new ArrayList<>();
        for (int i = 0; i < AMOUNT_OF_PLAYERS; i++) {
            Player p = new BasicPlayer();
            basicPlayers.add(p);
        }

        BlackjackTable table = new BlackjackTable(basicPlayers);

        // User input code
        Scanner userScanner = new Scanner(System.in);
        boolean playAgain = false;
        int rounds = 1;

        do {
            playGame(rounds, table);

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
        // Players for human game
        List<Player> players = new ArrayList<>();
        Player humanPlayer = new HumanPlayer();
        Player basicPlayer = new BasicPlayer();
        players.add(humanPlayer);
        players.add(basicPlayer);

        BlackjackTable table = new BlackjackTable(players);

        // User input code
        Scanner userScanner = new Scanner(System.in);
        boolean playAgain = false;
        int rounds = 1;

        do {
            playGame(rounds, table);

            // User decides whether to continue, load, save or quit game
            System.out.println("What would you like to do?");
            System.out.println("1) Continue Playing");
            System.out.println("2) Load Game");
            System.out.println("3) Save Game");
            System.out.println("4) Quit");

            int userChoice = userScanner.nextInt();

            switch (userChoice) {
                case 1:   // Continue playing
                    playAgain = true;
                    break;
                case 2:   // Load game

                    break;
                case 3:   // Save game

                    break;
                case 4:   // Quit playing
                default:
                    playAgain = false;
                    break;
            }

        } while (playAgain);
    }

    /**
     * Simulates a game of blackjack with intermediate players
     */
    public static void intermediateGame() {
        final int AMOUNT_OF_PLAYERS = 4;

        // Creates and populates a list of intermediate players for the table
        List<Player> intermediatePlayers = new ArrayList<>();
        for (int i = 0; i < AMOUNT_OF_PLAYERS; i++) {
            Player p = new IntermediatePlayer();
            intermediatePlayers.add(p);
        }

        BlackjackTable table = new BlackjackTable(intermediatePlayers);

        // User input code
        Scanner userScanner = new Scanner(System.in);
        boolean playAgain = false;
        int rounds = 1;

        do {
            playGame(rounds, table);

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
                case 2:   // Load game

                    break;
                case 3:   // Save game

                    break;
                case 4:   // Quit playing
                default:
                    playAgain = false;
                    break;
            }

        } while (playAgain);
    }

    /**
     * Simulates a game of blackjack with advanced players
     */
    public static void advancedGame() {

    }

    /**
     * Plays a given amount of rounds of blackjack
     *
     * @param rounds amount of rounds to play
     * @param table blackjack table to play on
     */
    public static void playGame(int rounds, BlackjackTable table) {
        Scanner userScanner = new Scanner(System.in);

        // Loops through a round of blackjack until all rounds are complete
        for (int i = 0; i < rounds; i++) {
            System.out.println("-----------------------------------------");
            System.out.println("BlackJack!\n");

            // Displays current players with their current balance
            System.out.println("Current Players:");
            Iterator<Player> playerIt = table.players.iterator();
            while (playerIt.hasNext()) {
                Player p = playerIt.next();

                // Removes players who have no money
                if (p.settleBet(0)) {
                    System.out.println("Player: £" + p.getBalance());
                } else {
                    playerIt.remove();
                }
            }
            System.out.println("");

            // Gives user the choice of carrying on if all players are gone
            if (table.players.isEmpty()) {
                System.out.println("There are no players left in the game!");
                System.out.println("Would you like to continue the game "
                        + "with newly created players? (Y/N)");

                String emptyPlayersChoice = userScanner.next();
                emptyPlayersChoice = emptyPlayersChoice.toUpperCase();

                // Creates new players or exits game depending on user choice
                if (emptyPlayersChoice.equals("Y")) {
                    //table.players = originalPlayers;    // TODO: Create new players
                } else {
                    return;
                }
            }

            // Players join the game
            table.dealer.assignPlayers(table.players);

            // All players make their bets before round begins
            table.dealer.takeBets();    //TODO: check MAXBET & MINBET when taking bets

            // Cards are dealt to all players and the dealer
            table.dealer.dealFirstCards();

            // Each player plays their hand, displaying outcome
            for (Player p : table.players) {
                System.out.println("Player Score: " + table.dealer.play(p));
                System.out.println(p.getHand().toString());
            }

            // Dealer plays hand, displaying outcome
            System.out.println("Dealer's Score: " + table.dealer.playDealer());
            System.out.println(table.dealer.getHand().toString());

            // All bets are settled, preparing the table for the next round
            table.dealer.settleBets();

            System.out.println("-----------------------------------------");
        }
    }

    /**
     * Main method for testing methods of the blackjackTable class
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //basicGame();
        humanGame();
        //intermediateGame();
    }

}
