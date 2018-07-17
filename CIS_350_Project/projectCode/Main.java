package projectCode;

import java.util.ArrayList;

/**
 * 
 * @author Seth
 *
 */
public class Main {

	/**
	 * 
	 * @param args args
	 * @throws Exception
	 *             Bad game type Exception
	 */
	public static void main(final String[] args) throws Exception {
		Game game = new Game("GOFISH");
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("User"));
		players.add(new Player("Computer"));

		for (Player p : players) {
			for (int i = 0; i < 5; i++) {
				game.deal(p);
			}
			p.sortHand();
		}
		System.out.print("Computer:\t");
		players.get(1).printHand();
		System.out.print("User:\t\t");
		players.get(0).printHand();
		System.out.println("Deck: " + game.deck.size());

		while (true) {
			game.tickGoFish(players);
		}
	}
}
