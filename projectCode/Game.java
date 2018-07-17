package projectCode;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
	
	GameType type;
	ArrayList<Card> deck;
	
	public Game(GameType g){
		this.type = g;
		this.deck = new ArrayList<Card>();
		this.fillDeck();
	}
	
	private void fillDeck(){
		int suit, value;
		for(suit = 0; suit < 4; suit++){
			for(value = 2; value <= 14; value++){
				this.deck.add(new Card(value, suit));
			}
		}
		Collections.shuffle(this.deck);
	}

}
