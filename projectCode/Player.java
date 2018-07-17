package projectCode;

import java.util.ArrayList;


public class Player {
	String name;
	ArrayList<Card> hand;
	
	public Player(String name){
		this.name = name;
		hand = new ArrayList<Card>();
		
	}
	
}
