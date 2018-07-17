package projectCode;

public class Main {

	public static void main(String[] args) {
		GameType gType = new GameType("EUCHRE", 4);
		Game g = new Game(gType);
		for(int i = 0; i < 52; i++){
			System.out.println(g.deck.get(i).face + " of " + g.deck.get(i).suit);
		}

	}

}
