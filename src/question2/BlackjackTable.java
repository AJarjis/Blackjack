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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import question1.Card;

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
    private List<Player> tablePlayers;

    /**
     * A register of players who originally played at this table
     */
    private List<Player> playerRegister;

    /**
     * Maximum amount of players allowed at this table
     */
    private final static int MAX_PLAYERS = 8;

    /**
     * Maximum bet a player can make
     */
    private final static int MAX_BET = 500;

    /**
     * Minimum bet a player can make
     */
    private final static int MIN_BET = 1;

    /**
     * Constructs a blackjack table with a dealer
     */
    public BlackjackTable() {
        this.dealer = new BlackjackDealer(MIN_BET, MAX_BET);
        this.tablePlayers = new ArrayList<>();
        this.playerRegister = new ArrayList<>();
    }

    /**
     * Constructs a blackjack table with a dealer and a list of players
     *
     * @param players a list of players playing at this table
     */
    public BlackjackTable(List<Player> players) {
        this();
        this.tablePlayers = players;
        this.playerRegister.addAll(players);
    }

    /**
     * Brings back all players found in the register to the table, all players
     * are reconstructed to start with their default values
     *
     * @throws InstantiationException if player cannot be instantiated
     * @throws IllegalAccessException if unable to access player's constructor
     * method
     */
    public void reinstatePlayers() throws InstantiationException,
            IllegalAccessException {

        // Loops through register, creating the players as new for the table
        for (Player p : this.playerRegister) {
            Class<? extends Player> playerClass = p.getClass();
            Player playerObject = playerClass.newInstance();

            this.tablePlayers.add(playerObject);
        }

    }

    /**
     * Saves a game (table, which includes players and dealer) for future play
     * @param table     table to save
     */
    public static void saveGame(BlackjackTable table) {
        String file = "recentGameFile.ser";

        try {
            writeToFile(table, file);
            System.out.println("Game saved!");
        } catch (IOException ex) {
            System.out.println("Unable to save game. Please try again.");
        }
    }

    /**
     * Loads a game to play
     * @param table     original table which is returned in case failed to 
     *                  load table
     * @return          loaded table
     */
    public static BlackjackTable loadGame(BlackjackTable table) {
        String file = "recentGameFile.ser";

        try {
            table = (BlackjackTable) readFromFile(file);
            System.out.println("Loaded game!");
        } catch (ClassNotFoundException | IOException ex) {
            System.out.println("Unable to load game. Please try again.");
        }
        
        return table;
    }
    
    /**
     * Displays a menu for the user to decide how many rounds a game to play or
     * if they wish to save/load a game or even quit entirely
     * 
     * @param table     table that will be played at
     */
    public static void gameMenu(BlackjackTable table) {
        Scanner userScanner = new Scanner(System.in);
        boolean playAgain = true;
        int rounds = 1;

        do {
            // User decides whether to continue, load, save or quit game
            System.out.println("What would you like to do?");
            System.out.println("1) Play Game");
            System.out.println("2) Load Game");
            System.out.println("3) Save Game");
            System.out.println("4) Quit to Menu");

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

                    playGame(rounds, table);
                    break;
                case 2:   // Load Game
                    table = loadGame(table);
                    break;
                case 3:   // Save Game
                    saveGame(table);
                    break;
                case 4:   // Quit playing
                default:
                    playAgain = false;
                    break;
            }

        } while (playAgain);
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

        gameMenu(table);
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

        // User input code:
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
            System.out.println("4) Quit to Menu");

            int userChoice = userScanner.nextInt();

            switch (userChoice) {
                case 1:   // Continue playing
                    playAgain = true;
                    break;
                case 2:   // Load game
                    table = loadGame(table);
                    break;
                case 3:   // Save game
                    saveGame(table);
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

        gameMenu(table);
    }

    /**
     * Simulates a game of blackjack with advanced players
     */
    public static void advancedGame() {
        // Players for advanced game
        List<Player> players = new ArrayList<>();
        Player basicPlayer = new BasicPlayer();
        Player intermediatePlayer = new IntermediatePlayer();
        Player advancedPlayer = new AdvancedPlayer();

        players.add(basicPlayer);
        players.add(intermediatePlayer);
        players.add(advancedPlayer);

        BlackjackTable table = new BlackjackTable(players);

        gameMenu(table);
        // TODO: Print average profit/loss per deck
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
            List<Card> cardsPlayed = new ArrayList<>();

            System.out.println("-----------------------------------------");
            System.out.println("BlackJack Round " + (i + 1) + "\n");

            // Displays current players with their current balance
            System.out.println("Current Players:");
            Iterator<Player> playerIt = table.tablePlayers.iterator();
            while (playerIt.hasNext()) {
                Player p = playerIt.next();

                // Removes players who have no money
                if (p.settleBet(0)) {
                    System.out.println(p.getClass().getSimpleName()
                            + ": £" + p.getBalance());
                } else {
                    playerIt.remove();
                }
            }
            System.out.println("");

            // Gives user the choice of carrying on if all players are gone
            if (table.tablePlayers.isEmpty()) {
                System.out.println("There are no players left in the game!");
                System.out.println("Would you like to continue the game "
                        + "with newly created players? (Y/N)");

                String emptyPlayersChoice = userScanner.next();
                emptyPlayersChoice = emptyPlayersChoice.toUpperCase();

                // Creates new players or exits game depending on user choice
                if (emptyPlayersChoice.equals("Y")) {
                    /* Attempts to create new players, on failure alerts user 
                       and ends current game */
                    try {
                        table.reinstatePlayers();
                    } catch (IllegalAccessException
                            | InstantiationException ex) {
                        System.out.println("Unable to create new players. "
                                + "Game has ended!");
                        return;
                    }
                } else {
                    return;
                }
            }

            // Players join the game
            table.dealer.assignPlayers(table.tablePlayers);

            // All players make their bets before round begins
            table.dealer.takeBets();

            // Cards are dealt to all players and the dealer
            table.dealer.dealFirstCards();

            // Each player plays their hand, displaying outcome
            for (Player p : table.tablePlayers) {
                System.out.println(p.getClass().getSimpleName() + " Score: "
                        + table.dealer.play(p));
                System.out.println(p.getHand().toString());
                cardsPlayed.addAll(p.getHand().getAllCards());
            }

            // Dealer plays hand, displaying outcome
            System.out.println("Dealer's Score: " + table.dealer.playDealer());
            System.out.println(table.dealer.getHand().toString());
            cardsPlayed.addAll(table.dealer.getHand().getAllCards());

            // Shows all players the cards played this round
            for (Player p : table.tablePlayers) {
                p.viewCards(cardsPlayed);
            }

            // All bets are settled, preparing the table for the next round
            table.dealer.settleBets();

            System.out.println("-----------------------------------------");
        }
    }

    /**
     * Main method for testing methods of the blackjackTable class, testing can
     * be done while running the program
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner userScanner = new Scanner(System.in);
        boolean playAgain = true;

        do {
            // User decides what game type they'd like to play or quit
            System.out.println("\nWelcome to Blackjack!");
            System.out.println("Please select a game type:");
            System.out.println("1) Basic Game");
            System.out.println("2) Human Game");
            System.out.println("3) Intermediate Game");
            System.out.println("4) Advanced Game");
            System.out.println("5) Quit Game");

            int userChoice = userScanner.nextInt();

            switch (userChoice) {
                case 1:   // Play Basic Game
                    basicGame();
                    break;
                case 2:   // Play Human Game
                    humanGame();
                    break;
                case 3:   // Play Intermediate Game
                    intermediateGame();
                    break;
                case 4:   // Play Advanced Game
                    advancedGame();
                    break;
                case 5:   // Quit Game
                default:
                    System.out.println("Thank you for playing!");
                    playAgain = false;
                    break;
            }

        } while (playAgain);
    }

    /**
     * Writes data to a file
     *
     * @param data data to write
     * @param filename file where data will be written
     *
     * @throws java.io.FileNotFoundException
     */
    public static void writeToFile(Serializable data, String filename)
            throws FileNotFoundException, IOException {

        FileOutputStream fileOut = new FileOutputStream(filename);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(data);
        objectOut.close();
    }

    /**
     * Reads data from a file
     *
     * @param filename name of file to read from
     * @return the data read from file stored as an object
     */
    public static Object readFromFile(String filename)
            throws FileNotFoundException, IOException, ClassNotFoundException {

        FileInputStream fileInput = new FileInputStream(filename);
        ObjectInputStream objectInput = new ObjectInputStream(fileInput);
        Object data = objectInput.readObject();
        objectInput.close();

        return data;

    }

}
