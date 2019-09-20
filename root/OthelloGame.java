import java.util.Scanner;

public class OthelloGame {
	
	private int maxDepth;
	private char initialPlayer;
	private State currentState;
	private Scanner reader;
	private int i; 	//for measure the game state in heuristics with dynamic weights
	private Evaluator evaluator; //0:initial game, 1:middle game, 2:final game
	
	
	public OthelloGame(char iniPlayer, int depth){
		evaluator = new Evaluator(0);
		initialPlayer = iniPlayer;
		maxDepth = depth;
		currentState = new State(initialPlayer, evaluator);
		reader = new Scanner(System.in);
		i = 0;
	}
	
	public void run(){
		System.out.println("Wellcome to Othello Game!");
		System.out.println("Ready...Set...GO!!");
		currentState.printBoard();
		String stringMove;
		int cont = 0; //number of consecutive skips
		while(cont < 2){
			if (!currentState.hasAvailableMove()){
				cont++;
				System.out.println("No possible move. Skip: " + cont + "/2");
				currentState = new State(currentState, evaluator);
				continue;
			}
			cont = 0;
			///////	HUMAN TURN
			if(currentState.getPlayer() == 'H'){
				currentState.printAvailableAction();
				System.out.println("Waiting for human move...");
				stringMove = reader.nextLine().toUpperCase();
				while (!currentState.isPermited(stringMove)){
					System.out.println("Wrong input. Please, enter one of the below valid moves");
					currentState.printAvailableAction();
					System.out.println("Waiting for human move...");
					stringMove = reader.nextLine().toUpperCase();
				}
				currentState = new State(currentState, stringMove, evaluator);
			}
			///////	MACHINE TURN
			else {
				System.out.println("AI thinking...");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String machineStringMove = alpha_beta(currentState);
				System.out.println("Machine Move: " + machineStringMove);
				currentState = new State(currentState, machineStringMove, evaluator);
			}
			currentState.printBoard();
			//update AI priorities
			i++;
			if(i == 10) evaluator = new Evaluator(1);
			if(i == 50) evaluator = new Evaluator(2);
		}
		System.out.println("GAME OVER");
		int humanScore = currentState.getBoard().count('H');
		int machineScore = currentState.getBoard().count('O');
		System.out.println("Human points: " + humanScore);
		System.out.println("AI points: " + machineScore);
		if (humanScore > machineScore) System.out.println("YOU WIN!!");
		else if (humanScore < machineScore) System.out.println("AI WIN!!");
		else System.out.println("DRAW!");
	}
	
	private String alpha_beta(State state) {
		return max(state, Integer.MIN_VALUE, Integer.MAX_VALUE, 1).getStringMove();
	}

	private Action max(State state, int a, int b, int level) {
		//	base case
		if(state.isLeaf()) return new Action(state.getUtil('O'));
		if(level == maxDepth) return new Action(state.getEval());
		
		//	recursive case
		int alpha = a;
		int beta = b;
		Action bestAction = null;
		for (Action avac : state.getAvailableActions()){
			Action act = min(new State(state, avac.getStringMove(), evaluator), alpha, beta, level + 1);
			if (act != null && act.getEV() > alpha){
				alpha = act.getEV();
				bestAction = avac;
			}
			if (alpha > beta) return bestAction;
		}
		return bestAction;
	}

	private Action min(State state, int a, int b, int level) {
			//	base case
			if(state.isLeaf()) return new Action(state.getUtil('O'));
			if(level == maxDepth) return new Action(state.getEval());
			
			//	recursive case
			int alpha = a;
			int beta = b;
			Action bestAction = null;
			for (Action avac : state.getAvailableActions()){
				Action act = max(new State(state, avac.getStringMove(), evaluator), alpha, beta, level + 1);
				if (act != null && act.getEV() < beta){
					beta = act.getEV();
					bestAction = avac;
				}
				if (beta < alpha) return bestAction;
			}
			return bestAction;
	}
}