package projectCode;

public class Card {
	String suit;
	int value;
	char face;
	
	public Card(int value, int suit){
		this.value = value;
		this.face = this.getFaceValue();
		this.suit = this.getSuit(suit);
	}
	
	
	private char getFaceValue(){
		switch(this.value){
		case 2:
			return '2';
		case 3:
			return '3';
		case 4:
			return '4';
		case 5:
			return '5';
		case 6:
			return '6';
		case 7:
			return '7';
		case 8:
			return '8';
		case 9:
			return '9';
		case 10:
			return '0';
		case 11:
			return 'J';
		case 12:
			return 'Q';
		case 13:
			return 'K';
		case 14:
			return 'A';
		default:
			//how did you get here?
			return 'm';
		
		}
	}
	
	private String getSuit(int suit){
		switch(suit){
		case 0:
			return "HEARTS";
		case 1:
			return "DIAMONDS";
		case 2:
			return "CLUBS";
		case 3:
			return "SPADES";
		default:
			return "AHHHHHHH";
		}
	}
	

}
