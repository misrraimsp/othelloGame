import java.util.ArrayList;
import java.util.Iterator;

public class State {
	
	private Evaluator evaluator;
	private Board board;
	private char player; //	'H' human, 'O' machine
	private ArrayList<Action> availableActions;
	private boolean availableMove;
	
	
	
	//build initial state
	public State(char player, Evaluator e){
		evaluator = e;
		board = new Board();
		this.player = player;
		availableMove = true;
		availableActions = getActions();
	}

	//build new state from previous one plus a valid movement.
	//as the movement is a valid one the new board is already stored in available actions array
	public State(State prevState, String stringMove, Evaluator e){
		evaluator = e;
		Iterator<Action> ite = prevState.getAvailableActions().iterator();
		Action a = null;
		do {
			a = ite.next();
		} while (!a.getStringMove().equals(stringMove));
		board = a.getBoard();
		player = (prevState.getPlayer() == 'H') ? 'O' : 'H';
		availableActions = getActions();
		availableMove = (!availableActions.isEmpty());
	}

	//build new state from previous one, without any movement, just switching turn
	public State(State prevState, Evaluator e) {
		evaluator = e;
		board = new Board(prevState.getBoard());
		player = (prevState.getPlayer() == 'H') ? 'O' : 'H';
		availableActions = getActions();
		availableMove = (!availableActions.isEmpty());
	}

	//return, for the state board and player, the list of possible Action objects.
	//if the player is the machine then actions are in increasing order with EV (evaluation value)
	private ArrayList<Action> getActions() {
		ArrayList<Action> actions = new ArrayList<Action>();
		Board newB = null;
		int[] intMove = new int[2];
		for (int i = 0; i < 8; i++){
			intMove[0] = i;
			for (int j = 0; j < 8; j++){
				intMove[1] = j;
				newB = board.makeMove(intMove, player, false);
				if (newB != null){
					Action act;
					int index = 0;
					if (player == 'O'){ //	evaluate and find proper position in array
						act = new Action(board.toStringMove(intMove), newB, evaluator.getEval(newB));
						Iterator<Action> ite = actions.iterator();
						while(ite.hasNext() && ite.next().getEV() >= act.getEV()) index++;
					}
					else act = new Action(board.toStringMove(intMove), newB, 0);
					actions.add(index, act);
				}
			}
		}
		return actions;
	}
	
	public void printAvailableAction() {
		System.out.println("Available Moves:");
		Iterator<Action> ite = availableActions.iterator();
		while (ite.hasNext()) System.out.println(ite.next().getStringMove());
	}

	public boolean isPermited(String stringMove) {
		Iterator<Action> ite = availableActions.iterator();
		while (ite.hasNext()) if (ite.next().getStringMove().equals(stringMove)) return true;
		return false;
	}

	public boolean hasAvailableMove() {
		return availableMove;
	}

	public char getPlayer() {
		return player;
	}
	
	public ArrayList<Action> getAvailableActions(){
		return availableActions;
	}

	public boolean isLeaf() {
		if (availableMove) return false;
		State next = new State(this, evaluator);
		return !next.hasAvailableMove();
	}

	public int getEval() {
		return evaluator.getEval(board);
	}

	public int getUtil(char player) {
		return evaluator.getUtil(board, player);
	}

	public void printBoard() {
		board.printBoard();
	}
	
	public Board getBoard() {
		return board;
	}
}