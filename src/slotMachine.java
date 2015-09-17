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
		else {
			requestBet();
		}
    }
    private static void requestBet() {
    	final int maxBet = 50, minBet = 1, // The maximum and minimum bets permitted.
    	endGame = 0;                       // The number we want users to enter to end the game.

        if (currentCash < minBet) {
            endGame(true); // The user has gone bankrupt! Oh noes!
        }

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
			System.out.println("You do not have enough cash to bet this amount!");

			// Let's restart.
			requestBet();
		}
    	else if ((betCash > maxBet) || (betCash < minBet)) {
    		System.out.println("You've entered an invalid bet.");
    		System.out.printf("Bets must be whole dollars, and between $%d and $%d.%n", minBet, maxBet);

    		// Let's restart.
    		requestBet();
    	}
		else {
			currentCash -= betCash;
			roll();
		}
    }
    private static void roll() {
    	// Give us a nice clean little box :o)
    	final String slotMachineBorderHorizontal = "=========", slotMachineBorderVertical = "|";

    	// Establish our multipliers.
    	final double mp_threeKind = 3, mp_pair = 1.5, mp_7 = 1, mp_straight = 4, mp_three7 = 10;
    	double roundCash = 0; // The cash won this round.
    	int roll1, roll2, roll3; // Set our numbers that were rolled.

    	// Roll us 3 random numbers!
    	roll1 = numberGenerator.nextInt(10);
    	roll2 = numberGenerator.nextInt(10);
    	roll3 = numberGenerator.nextInt(10);

		// Lets check if they won!

		if ((roll1 == roll2) && (roll2 == roll3)) {
			// The user had a three of a kind!
            if (roll1 == 7) {
                // HOLY WHACK-A-MOLEY WE HAD 3 7s!!!
                // Since this shows up first the other if statement involving 7s wouldn't get called :o(
                // Also it's safe to check only the 1st role since we established they're all equal.
                roundCash = betCash * mp_three7;
            }
            else {
            	roundCash = betCash * mp_threeKind;
            }
		}
		else if ((roll1 == roll2) || (roll2 == roll3) || (roll1 == roll3)) {
			// The user had a pair!
			roundCash = betCash * mp_pair;
		}
        else if ((roll1 + 1 == roll2) && (roll2 + 1 == roll3)) {
            // The user got a straight!
            roundCash = betCash * mp_straight;
        }
        else if (roll1 == 7 || roll2 == 7 || roll3 == 7) {
            // The user got one or two 7s.
             roundCash = betCash * mp_7;
        }

    	System.out.printf("%n%s%n%s %d %d %d %s%n%s%n", slotMachineBorderHorizontal, slotMachineBorderVertical,
    	roll1, roll2, roll3, slotMachineBorderVertical, slotMachineBorderHorizontal);

        currentCash += roundCash;
        requestBet();
    }
    private static void endGame(boolean isBroke) {
    	// The user has either requested to end the game, or went bankrupt.
    	String playAgain;
    	double cashDifference;
    	if (isBroke) {
    		System.out.println("You ran out of cash!");
    		System.out.printf("You lost $%.2f tonight.%n", initialCash);
    	}
    	else {
    		cashDifference = currentCash - initialCash;
    		System.out.println("\nYou requested to end the game.");
    		System.out.printf("You cashed out with a total of $%.2f left. (A $%.2f difference!)%n%n", currentCash, cashDifference);
    	}

    	System.out.print("Would you like to play again? (y/n) ");
    	playAgain = kbReader.next();
    	playAgain = playAgain.toLowerCase();

    	if (playAgain.equals("y")) {
    		// The user wants to play again!
    		System.out.println("Restarting...");
    		requestMoney();
    	}
    	else {
    		// The user doesn't want to play anymore, or enter something other than y or n.
    		System.out.println("\nThanks for playing!");
    		System.out.println("Exiting...");
			System.exit(0);
    	}
    }
}
