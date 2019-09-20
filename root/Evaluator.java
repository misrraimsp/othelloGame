
public class Evaluator {

	private double[] w;
	
	
	public Evaluator(int option){
		//initial game: mobility + stability
		if (option == 0){
			w = new double[4];
			w[0] = 0.05;	//parity weight
			w[1] = 0.15;	//corner weight
			w[2] = 0.4;		//mobility weight
			w[3] = 0.4;		//stability weight
		}
		//middle game: corner + stability
		else if (option == 1){
			w = new double[4];
			w[0] = 0.05;	//parity
			w[1] = 0.4;		//corner
			w[2] = 0.15;	//mobility
			w[3] = 0.4;		//stability
		}
		//final game: parity
		else {
			w = new double[4];
			w[0] = 1; //parity
			w[1] = 0; //corner
			w[2] = 0; //mobility
			w[3] = 0; //stability
		}
	}
	
	public int getUtil(Board board, char player) {
		char oponent = (player == 'O') ? 'H' : 'O'; 
		int oponentScore = board.count(oponent);
		int playerScore = board.count(player);
		return (playerScore - oponentScore);
	}
	
	//the evaluation process is always made from the point of view of the machine
	public int getEval(Board board) {
		int parity = getParityScore(board);
		int corner = getCornerScore(board);
		int mobility = getMobilityScore(board);
		int stability = getStabilityScore(board);
		return (int) (parity * w[0] + corner * w[1] + mobility * w[2] + stability * w[3]);
	}

	private int getCornerScore(Board board) {
		//count corner captured
		int machineCornerCount = board.countCorner('O');
		int humanCornerCount = board.countCorner('H');
		//count possible corner in next move
		int machinePotentialCornerCount = board.countPotentialCorner('O');
		int humanPotentialCornerCount = board.countPotentialCorner('H');
		//count not possible corner in next move
		int machineUnlikeCornerCount = 4 - (machineCornerCount + machinePotentialCornerCount);
		int humanUnlikeCornerCount = 4 - (humanCornerCount + humanPotentialCornerCount);
		//compute corner heuristic
		int machineCorner = (5) * machineCornerCount + (2) * machinePotentialCornerCount + (0) * machineUnlikeCornerCount;
		int humanCorner = (5) * humanCornerCount + (2) * humanPotentialCornerCount + (0) * humanUnlikeCornerCount;
		int total = machineCorner + humanCorner;
		if (total != 0) return (int) (64 * ((machineCorner - humanCorner) / (total)));
		return 0;
	}

	private int getStabilityScore(Board board) {
		//count total
		int machineCount = board.count('O');
		int humanCount = board.count('H');
		//count stable
		int machineStableCount = board.countStablePieces('O');
		int humanStableCount = board.countStablePieces('H');
		//count unstable
		int machineUnstableCount = board.countUnstablePieces('O');
		int humanUnstableCount = board.countUnstablePieces('H');
		//compute semi-stable
		int machineSemiStableCount = machineCount - machineStableCount - machineUnstableCount;
		int humanSemiStableCount = humanCount - humanStableCount - humanUnstableCount;
		//compute heuristic
		int machineStability = (1 * machineStableCount) + ((-1) * machineUnstableCount) + (0 * machineSemiStableCount);
		int humanStability = (1 * humanStableCount) + ((-1) * humanUnstableCount) + (0 * humanSemiStableCount);
		int total = machineStability + humanStability;
		if (total != 0) return (int) (64 * ((machineStability - humanStability) / (total)));
		return 0;
	}

	private int getMobilityScore(Board board) {
		double machineMobility = 0.5 * board.countCurrentMoves('O') + 0.5 * board.countPotentialMoves('O');
		double humanMobility = 0.5 * board.countCurrentMoves('H') + 0.5 * board.countPotentialMoves('H');
		double total = machineMobility + humanMobility;
		if (total != 0) return (int) (64 * ((machineMobility - humanMobility) / (total)));
		return 0;
	}

	private int getParityScore(Board board) {
		int machinePieces = board.count('O');
		int humanPieces = board.count('H');
		return 64 * ((machinePieces - humanPieces) / (machinePieces + humanPieces));
	}
}
