/**
 * Author: Brandon B.
 * Date: 9-16-15
 * Description: Allows a user to play a slot machine game.
 */

import java.util.Random;
import java.util.Scanner;
public class slotMachine {
	// Set variables here that need to be accessed by more than one method.

	// The total amount of money the user has along with how much they're betting for the round.
	private static double initialCash, currentCash, betCash;
    private static Random numberGenerator = new Random();
   	private static Scanner kbReader = new Scanner(System.in);

    public static void main(String[] args) {
    	// This is our introduction.
    	System.out.println("Welcome to the slot machine game!");
    	System.out.println("You can bet a minimum of $1, and a maximum of $50.");
    	System.out.println("You will start out by entering the TOTAL amount of cash you're spending.");

		// Go to the first function!
		requestMoney();
    }
    private static void requestMoney() {
    	// How depressed is our player looking to get tonight?
		System.out.print("Please enter the total amount of cash you would like to spend: ");
		initialCash = kbReader.nextDouble();
		currentCash = initialCash;

		if (initialCash < 1) {
			System.out.println("You need to at least $1 to start!");

			// Let's restart.
			requestMoney();
		}

		// We've got the money the user is spending total. Let's ask them for their first bet now!
		requestBet();
    }
    private static void requestBet() {
    	final int maxBet = 50, minBet = 1, // The maximum and minimum bets permitted.
    	endGame = 0;                       // The number we want users to enter to end the game.

    	// Request the betting cash for this round.
    	System.out.printf("You currently have $%.2f. Type 0 to end the game.%n", currentCash);
    	System.out.print("How much would you like to bet? ");
    	betCash = kbReader.nextInt();

    	if (betCash == endGame) {
    		// The user entered 0. We're ending the game for them.
    		endGame(false);
    	}
		else if (currentCash - betCash < 0) {
			// Betting this amount would put them in a negative amount of cash!
			System.out.println("You do not have enough cash to bet this amount.");

			// Let's restart.
			requestBet();
		}
    	else if ((betCash > maxBet) || (betCash < minBet)) {
    		System.out.println("You've entered an invalid bet.");
    		System.out.printf("Bets must be whole dollars, and between $%d and $%d.", minBet, maxBet);

    		// Let's restart.
    		requestBet();
    	}
    	currentCash -= betCash;
    	roll();
    }
    private static void roll() {
    	// Give us a nice clean little box :o)
    	final String slotMachineBorderHorizontal = "=========", slotMachineBorderVertical = "|";

    	// Esablish our multipliers.
    	final double mp_threeKind = 3, mp_pair = 1.5, mp_7 = 1, mp_straight = 4, mp_three7 = 10;
    	double roundCash = 0; // The cash won this round.
    	int roll1, roll2, roll3; // Set our numbers that were rolled.

    	// Roll us 3 random numbers!
    	roll1 = numberGenerator.nextInt(10);
    	roll2 = numberGenerator.nextInt(10);
    	roll3 = numberGenerator.nextInt(10);

		// Lets check if they won!

		if ((roll1 == roll2) && (roll2 == roll3)) {
			// We had a three of a kind!
			roundCash = betCash * mp_threeKind;
		}

		System.out.println("Rolling...");
    	System.out.printf("%s%n%s %d %d %d %s%n%s%n", slotMachineBorderHorizontal, slotMachineBorderVertical,
    	roll1, roll2, roll3, slotMachineBorderVertical, slotMachineBorderHorizontal);


    }
    private static void endGame(boolean isBroke) {
    	// The user has either requested to end the game, or went bankrupt.
    	String playAgain;
    	double cashDifference;
    	if (isBroke) {
    		System.out.println("You went bankrupt!");
    		System.out.printf("You lost $%.2f tonight.", initialCash);
    	}
    	else {
    		cashDifference = currentCash - initialCash;
    		System.out.println("You requested to end the game.");
    		System.out.printf("You cashed out with a total of $%.2f left. (A $%.2f difference!)%n", currentCash, initialCash);
    	}

    	System.out.print("Would you like to play again? (y/n) ");
    	playAgain = kbReader.next();
    	playAgain = playAgain.toLowerCase();

    	if (playAgain.equals("y")) {
    		// The user wants to play again!
    		System.out.println("Restarting...");
    		requestMoney();
    	}
    	else if (playAgain.equals("n")) {
    		// The user doesn't want to play anymore!
    		System.out.println("Thanks for playing!");
    		System.out.println("Exiting...");
    	}
    	else {
    		// What?
    		System.out.println("Unexpected input received.");
    		System.out.println("Exiting...");
    	}
    }
}
