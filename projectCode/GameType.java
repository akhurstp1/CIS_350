package projectCode;

public class GameType {
	
	String type;
	int playerNum;
	int handSize;
	int deckMin;
	int deckMax;
	int deckSize;
	
	
	public GameType(String gameName, int playerNum){
		this.type = gameName;
		this.playerNum = playerNum;
		gameInit();
	}
	
	private void gameInit(){
		//TODO big Switch
	}
	
	//TODO different game-mode logic

}
