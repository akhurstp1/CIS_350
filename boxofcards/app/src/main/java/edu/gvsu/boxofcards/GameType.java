package edu.gvsu.boxofcards;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author seth
 *
 */
public class GameType {
	/**
	 * Scanner.
	 */
	Scanner s;
	/**
	 * Game type name.
	 */
	String type;
	/**
	 * Number of players.
	 */
	int playerNum;
	/**
	 * Size of hand to be dealt.
	 */
	int handSize;
	/**
	 * Lowest value of deck.
	 */
	int deckMin;
	/**
	 * Highest value of deck.
	 */
	int deckMax;
	/**
	 * Number of cards in deck.
	 */
	int deckSize;

	/**
	 * GameType Constructor.
	 * 
	 * @param gameName
	 *            Name of game
	 * @param playerNum
	 *            Number of Players
	 */
	public GameType(final String gameName, final int playerNum) {
		this.type = gameName;
		this.playerNum = playerNum;
		s = new Scanner(System.in);
		gameInit();
	}

	/**
	 * Initialize game type variables.
	 */
	private void gameInit() {
		switch (type) {
		case "GOFISH":
			this.deckMin = 2;
			this.deckMax = 14;
			break;
		case "EUCHRE":
			this.deckMin = 9;
			this.deckMax = 14;
			break;
		default:
			//meh
		}
	}

	// TODO different game-mode logic

	/**
	 * Checks the given players hand to determine if they have a set 
	 * of 4 in therehand. It then removes those cards and puts them 
	 * in the players discard pile and gives them a point.
	 * 
	 * @param p
	 *            The player who's hand is being checked
	 * @param g
	 *            The game variable for the deck
	 */
	public void score(final Player p, final Game g) {
		ArrayList<Integer> a;
		a = new ArrayList<Integer>();
		for (int i = 0; i < p.hand.size(); i++) {
			a.add(i);
			for (int k = i + 1; k < p.hand.size(); k++) {
				if (p.hand.get(i).face == p.hand.get(k).face) {
					a.add(k);
				}
			}
			if (a.size() == 4) {
				i = 0;
				// cardsLost += 4;
				p.score++;
				for (int j = 0; j < 4; j++) {
					p.discard.add(p.hand.get(j));
				}
				for (int j = 0; j < 4; j++) {
					p.hand.remove(a.get(p.hand.size() - j));
				}
			}
		}
	}

	/**
	 * Checks for a winner at the end of the game by comparing scores.
	 * 
	 * @param players
	 *            The ArrayList of players to check
	 * @return Returns the winning player
	 */
	public Player declareWinner(final ArrayList<Player> players) {
		int[] a;
		a = new int[this.playerNum];
		int count = 0;
		for (Player y : players) {
			a[count++] = y.score;
		}
		int maxAt = 0;

		for (int i = 0; i < a.length; i++) {
			if (a[i] > a[maxAt]) {
				maxAt = i;
			}
		}

		return players.get(maxAt);
	}
}
