public class Action {

	private String stringMove;
	private Board resultBoard;
	private int ev;
	
	
	//to make a 'stringMove' resulting on 'resultBoard' with value 'ev'
	public Action(String stringMove, Board resultBoard, int ev) {
		this.stringMove = stringMove;
		this.resultBoard = resultBoard;
		this.ev = ev;
	}
	
	//this constructor is used for the base case in min and max functions.
	//only the ev value is needed
	public Action(int util) {
		stringMove = null;
		resultBoard = null;
		ev = util;
	}
	
	public int getEV() {
		return ev;
	}

	public String getStringMove() {
		return stringMove;
	}
	
	public Board getBoard() {
		return resultBoard;
	}
}
