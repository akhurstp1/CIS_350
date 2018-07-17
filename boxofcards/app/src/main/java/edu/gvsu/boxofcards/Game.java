package edu.gvsu.boxofcards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author seth
 * @author andrew
 *
 */
public class Game {

	/**
	 * GameType.
	 */
	GameType type;
	/**
	 * Deck of cards for game.
	 */
	ArrayList<Card> deck;
	/**
	 * Scanner.
	 */
	Scanner scan = new Scanner(System.in);
	/**
	 * Turn tracking.
	 */
	int turn;

	/**
	 * Game constructor.
	 * 
	 * @param gameName
	 *            Name of game
	 */
	public Game(final String gameName){
		this.deck = new ArrayList<Card>();
		this.turn = 0;
		if (gameName.equals("GOFISH")) {
			startGoFish();
		}
	}

	/**
	 * Fills deck based on type of game.
	 * 
	 * @param min
	 *            minimum value for deck
	 * @param max
	 *            maximum value for deck
	 */
	private void fillDeck(final int min, final int max) {
		int suit, value;
		for (suit = 0; suit < 4; suit++) {
			for (value = min; value <= max; value++) {
				this.deck.add(new Card(value, suit));
			}
		}
		Collections.shuffle(this.deck);
	}

	/**
	 * Deal card from deck to player.
	 * 
	 * @param p
	 *            Player to be dealt
	 */
	public Card deal(final Player p) {
		Card c = this.deck.get(0);
		p.hand.add(c);
		this.deck.remove(0);
		return c;
	}

	/**
	 * Initializes GO FISH game.
	 * 
	 * @throws Exception
	 *             Bad game type Expection
	 */
	public void startGoFish() {
		this.type = new GameType("GOFISH", 2);
		this.fillDeck(this.type.deckMin, this.type.deckMax);
	}

	/**
	 * Playing function for GO FISH.
	 * 
	 * @param players
	 *            ArrayList of players
	 */
	public int tickGoFish(final ArrayList<Player> players, final int cardID) {
		Player user = players.get(0); // human player
		Player comp = players.get(1); // computer player
		Card card, request = getCardFromInt(cardID);
		int valid = 0;

		Random rand = new Random();
		int r = rand.nextInt(comp.hand.size());
		Card rCard = comp.hand.get(r); // rand card from computer's hand

		if (turn == 0) { // user turn
			for (int i = 0; i < comp.hand.size(); i++) {
				card = comp.hand.get(i);
				if (card.compareTo(request)==0) {
					valid = 1;
					user.hand.add(card);
					comp.hand.remove(i);
					break;
				}

				if (i == comp.hand.size() - 1) {
					this.deal(user);
				}
			}
			user.sortHand();
			turn = 1;
		} else { // computer turn
			for (int i = 0; i < user.hand.size(); i++) {
				card = user.hand.get(i);
				if (card.compareTo(rCard) == 0) {
					valid = 1;
					user.hand.remove(i);
					comp.hand.add(card);
					break;
				}

				if (i == user.hand.size() - 1) {
					this.deal(comp);
				}
			}
			turn = 0;
		}
		return valid;
	}

	private Card getCardFromInt(int c){
		int suit = c/13;
		int value = c%13;

		value++;
		return new Card(value, suit);
	}
}
