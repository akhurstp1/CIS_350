package projectCode;

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
	 * @throws Exception
	 *             Bad game type Exception
	 */
	public Game(final String gameName) throws Exception {
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
	public void deal(final Player p) {
		p.hand.add(this.deck.get(0));
		this.deck.remove(0);
	}

	/**
	 * Initializes GO FISH game.
	 * 
	 * @throws Exception
	 *             Bad game type Expection
	 */
	public void startGoFish() throws Exception {
		this.type = new GameType("GOFISH", 2);
		this.fillDeck(this.type.deckMin, this.type.deckMax);
	}

	/**
	 * Playing function for GO FISH.
	 * 
	 * @param players
	 *            ArrayList of players
	 */
	public void tickGoFish(final ArrayList<Player> players) {
		String request;
		Player user = players.get(0); // human player
		Player comp = players.get(1); // computer player
		Card card;

		Random rand = new Random();
		int r = rand.nextInt(comp.hand.size());
		Card rCard = comp.hand.get(r); // rand card from computer's hand

		if (turn == 0) { // user turn
			System.out.print("Enter card request (A for Ace): ");
			request = scan.nextLine();

			for (int i = 0; i < comp.hand.size(); i++) {
				card = comp.hand.get(i);
				if (card.face.equals(request)) {
					user.hand.add(card);
					comp.hand.remove(i);
					System.out.println("Opponent has the " 
					+ card.toString());
					System.out.println("Added to hand.");
					break;
				}

				if (i == comp.hand.size() - 1) {
					System.out.println("Opponent does not " 
							+ "have that card. " 
							+ "Draw a card...");
					this.deal(user);
				}
			}
			user.sortHand();
			user.printHand();
			turn = 1;
		} else { // computer turn
			for (int i = 0; i < user.hand.size(); i++) {
				card = user.hand.get(i);
				if (card.compareTo(rCard) == 0) {
					System.out.println("Opponent has taken "
							+ card.toString());
					user.hand.remove(i);
					comp.hand.add(card);
					break;
				}

				if (i == user.hand.size() - 1) {
					System.out.println("Opponent "
							+ "draws a card...");
					this.deal(comp);
				}
			}
			turn = 0;
		}
	}
}
