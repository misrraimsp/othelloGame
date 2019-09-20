
/*
 * Main Bibliography:
 * 		-	An Analysis of Heuristics in Othello
 * 			[Vaishnavi Sannidhanam, Muthukaruppan Annamalai]
 * 
 * Complementary Bibliography:
 *  	-	“IAgo Vs Othello”: An artificial intelligence agent playing Reversi
 * 			[Jacopo Festa, Stanislao Davino]
 * 		-	Applications of Artificial Intelligence and Machine Learning in Othello
 * 			[Jack Chen]
 *  	-	Playing Othello with Artificial Intelligence
 * 			[Michael J. Korman]
 * 
 * Principal Features:
 * 		- parity heuristic
 * 		- corner heuristic
 * 		- stability heuristic
 * 		- mobility heuristic
 * 		- alpha_beta heuristic
 * 		- dynamic heuristic weights
 */


public class Main {
	
	public static int maxDepth;
	private static char initialPlayer;

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: main initialPlayer{H, O} maxDepth");
			return;
		}
		else {
			maxDepth = Integer.parseInt(args[1]);
			initialPlayer = args[0].charAt(0);
			OthelloGame game = new OthelloGame(initialPlayer, maxDepth);
			game.run();
		}
	}
}
