public class Board {
	
	private char[][] cells;

	//initial board
	public Board(){
		cells = new char[8][8];
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				cells[i][j] = ' ';
			}
		}
		cells[3][3] = 'O';
		cells[3][4] = 'H';
		cells[4][3] = 'H';
		cells[4][4] = 'O';
	}
	
	//copy a board
	public Board(Board b){
		cells = new char[8][8];
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				cells[i][j] = b.getCellValue(i, j);
			}
		}
	}
	
	//boards of any size
	public Board(int size){
		cells = new char[size][size];
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				cells[i][j] = ' ';
			}
		}
	}
	
	public char getCellValue(int i, int j) {
		return cells[i][j];
	}
	
	public void setCellValue(int i, int j, char c) {
		cells[i][j] = c;
	}

	public void printBoard(){
		System.out.println("    A   B   C   D   E   F   G   H ");
			for (int i = 1; i < 9; i++) {
				System.out.println("  ---------------------------------");
				System.out.print(i + " |");
				for (int j = 1; j < 9; j++) {
					if (cells[i - 1][j - 1] != ' ') System.out.print(" " + cells[i - 1][j - 1] + " |");
					else System.out.print("   |");
				}
				System.out.println();
			}
			System.out.println("  ---------------------------------");
		}
	
	public int count(char player) {
		int cont = 0;
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if (cells[i][j] == player) cont++;
			}
		}
		return cont;
	}

	public String toStringMove(int[] intMove) {
		String s = Integer.toString(intMove[0] + 1);
		switch (intMove[1]){
		case 0:
			s = s + "A";
			break;
		case 1:
			s = s + "B";
			break;
		case 2:
			s = s + "C";
			break;
		case 3:
			s = s + "D";
			break;
		case 4:
			s = s + "E";
			break;
		case 5:
			s = s + "F";
			break;
		case 6:
			s = s + "G";
			break;
		case 7:
			s = s + "H";
			break;
		}
		return s;
	}
	
	public int[] toIntMove(String stringMove) {
		int[] intMove = new int[2];
		intMove[0] = Character.getNumericValue(stringMove.charAt(0)) - 1;
		switch (stringMove.charAt(1)){
		case 'A':
			intMove[1] = 0;
			break;
		case 'B':
			intMove[1] = 1;
			break;
		case 'C':
			intMove[1] = 2;
			break;
		case 'D':
			intMove[1] = 3;
			break;
		case 'E':
			intMove[1] = 4;
			break;
		case 'F':
			intMove[1] = 5;
			break;
		case 'G':
			intMove[1] = 6;
			break;
		case 'H':
			intMove[1] = 7;
			break;
		}
		return intMove;
	}
	
	/*
	 * This method has two functionalities: 
	 * 1 setting variable 'onlyTry' to FALSE. By doing so, if the movement is valid then
	 * returns the new resultant board, and if the movement is not valid then returns null.
	 * 2 setting variable 'onlyTry' to TRUE. By doing so, if the movement is valid then
	 * returns an initial board, and if the movement is not valid then returns null.
	*/
	public Board makeMove(int[] intMove, char c, boolean onlyTry) {
		int row = intMove[0];
		int column = intMove[1];
		if (cells[row][column] != ' ') return null; 
		Board b = null;
		char target = (c == 'H') ? 'O' : 'H';
		//check UP
		if (row != 0){
			if (cells[row - 1][column] == target){
				int cont = 2;
				while ((row - cont) >= 0 && cells[row - cont][column] == target) cont++;
				if ((row - cont) >= 0 && cells[row - cont][column] == c){
					if (onlyTry) return new Board(1); //	when returning tiny board means that the movement is valid
					else {
						if (b == null) b = new Board(this);
						b.setCellValue(row, column, c);
						for (int i = 1; i <= cont - 1; i++) b.setCellValue(row - i, column, c);
					}
				}
			}
		}
		//check UP-RIGHT
		if (row != 0 && column != 7){
			if (cells[row - 1][column + 1] == target){
				int cont = 2;
				while ((row - cont) >= 0 && (column + cont) <= 7 && cells[row - cont][column + cont] == target) cont++;
				if ((row - cont) >= 0 && (column + cont) <= 7 && cells[row - cont][column + cont] == c){
					if (onlyTry) return new Board(1); //	when returning tiny board means that the movement is valid
					else {
						if (b == null) b = new Board(this);
						b.setCellValue(row, column, c);
						for (int i = 1; i <= cont - 1; i++) b.setCellValue(row - i, column + i, c);
					}
				}
			}
		}
		//check RIGHT
		if (column != 7){
			if (cells[row][column + 1] == target){
				int cont = 2;
				while ((column + cont) <= 7 && cells[row][column + cont] == target) cont++;
				if ((column + cont) <= 7 && cells[row][column + cont] == c){
					if (onlyTry) return new Board(1); //	when returning tiny board means that the movement is valid
					else {
						if (b == null) b = new Board(this);
						b.setCellValue(row, column, c);
						for (int i = 1; i <= cont - 1; i++) b.setCellValue(row, column + i, c);
					}
				}
			}
		}
		//check DOWN-RIGHT
		if (row != 7 && column != 7){
			if (cells[row + 1][column + 1] == target){
				int cont = 2;
				while ((row + cont) <= 7 && (column + cont) <= 7 && cells[row + cont][column + cont] == target) cont++;
				if ((row + cont) <= 7 && (column + cont) <= 7 && cells[row + cont][column + cont] == c){
					if (onlyTry) return new Board(1); //	when returning tiny board means that the movement is valid
					else {
						if (b == null) b = new Board(this);
						b.setCellValue(row, column, c);
						for (int i = 1; i <= cont - 1; i++) b.setCellValue(row + i, column + i, c);
					}
				}
			}
		}
		//check DOWN
		if (row != 7){
			if (cells[row + 1][column] == target){
				int cont = 2;
				while ((row + cont) <= 7 && cells[row + cont][column] == target) cont++;
				if ((row + cont) <= 7 && cells[row + cont][column] == c){
					if (onlyTry) return new Board(1); //	when returning tiny board means that the movement is valid
					else {
						if (b == null) b = new Board(this);
						b.setCellValue(row, column, c);
						for (int i = 1; i <= cont - 1; i++) b.setCellValue(row + i, column, c);
					}
				}
			}
		}
		//check DOWN-LEFT
		if (row != 7 && column != 0){
			if (cells[row + 1][column - 1] == target){
				int cont = 2;
				while ((row + cont) <= 7 && (column - cont) >= 0 && cells[row + cont][column - cont] == target) cont++;
				if ((row + cont) <= 7 && (column - cont) >= 0 && cells[row + cont][column - cont] == c){
					if (onlyTry) return new Board(1); //	when returning tiny board means that the movement is valid
					else {
						if (b == null) b = new Board(this);
						b.setCellValue(row, column, c);
						for (int i = 1; i <= cont - 1; i++) b.setCellValue(row + i, column - i, c);
					}
				}
			}
		}
		//check LEFT
		if (column != 0){
			if (cells[row][column - 1] == target){
				int cont = 2;
				while ((column - cont) >= 0 && cells[row][column - cont] == target) cont++;
				if ((column - cont) >= 0 && cells[row][column - cont] == c){
					if (onlyTry) return new Board(1); //	when returning tiny board means that the movement is valid
					else {
						if (b == null) b = new Board(this);
						b.setCellValue(row, column, c);
						for (int i = 1; i <= cont - 1; i++) b.setCellValue(row, column - i, c);
					}
				}
			}
		}
		//check UP-LEFT
		if (row != 0 && column != 0){
			if (cells[row - 1][column - 1] == target){
				int cont = 2;
				while ((row - cont) >= 0 && (column - cont) >= 0 && cells[row - cont][column - cont] == target) cont++;
				if ((row - cont) >= 0 && (column - cont) >= 0 && cells[row - cont][column - cont] == c){
					if (onlyTry) return new Board(1); //	when returning tiny board means that the movement is valid
					else {
						if (b == null) b = new Board(this);
						b.setCellValue(row, column, c);
						for (int i = 1; i <= cont - 1; i++) b.setCellValue(row - i, column - i, c);
					}
				}
			}
		}
		return b;
	}

	public double countCurrentMoves(char player) {
		int cont = 0;
		int[] intMove = new int[2];
		for (int i = 0; i < 8; i++){
			intMove[0] = i;
			for (int j = 0; j < 8; j++){
				intMove[1] = j;
				if (this.makeMove(intMove, player, true) != null) cont++;
			}
		}
		return cont;
	}

	public double countPotentialMoves(char player) {
		char target = (player == 'H') ? 'O' : 'H';
		int cont = 0;
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if (cells[i][j] == target){
					//UP
					if (i != 0 && cells[i - 1][j] == ' ') cont++;
					//UP-RIGHT
					if(i != 0 && j != 7 && cells[i - 1][j + 1] == ' ') cont++;
					//RIGHT
					if(j != 7 && cells[i][j + 1] == ' ') cont++;
					//DOWN-RIGHT
					if(i != 7 && j != 7 && cells[i + 1][j + 1] == ' ') cont++;
					//DOWN
					if(i != 7 && cells[i + 1][j] == ' ') cont++;
					//DOWN-LEFT
					if(i != 7 && j != 0 && cells[i + 1][j - 1] == ' ') cont++;
					//LEFT
					if(j != 0 && cells[i][j - 1] == ' ') cont++;
					//UP-LEFT
					if(i != 0 && j != 0 && cells[i - 1][j - 1] == ' ') cont++;
				}
			}
		}
		return cont;
	}

	public int countStablePieces(char player) {
		int cont = 0;
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if (isStable(i, j, player)) cont++;
			}
		}
		return cont;
	}

	private boolean isStable(int r, int c, char p) {
		//check vertical
		boolean UPstable = isUp_Stable(r, c, p);
		boolean DOWNstable = isDown_Stable(r, c, p);
		boolean verticalStable = UPstable || DOWNstable || isFullColumn(c);
		if (!verticalStable) return false;
		//check horizontal
		boolean RIGHTstable = isRight_Stable(r, c, p);
		boolean LEFTstable = isLeft_Stable(r, c, p);
		boolean horizontalStable = RIGHTstable || LEFTstable || isFullRow(r);
		if (!horizontalStable) return false;
		//check diagonal 1
		boolean UP_RIGHTstable = isUpRight_Stable(r, c, p);
		boolean DOWN_LEFTstable = isDownLeft_Stable(r, c, p);
		boolean diagonal1Stable = UP_RIGHTstable || DOWN_LEFTstable || isFullDiagonal1(r, c);
		if (!diagonal1Stable) return false;
		//check diagonal 2
		boolean UP_LEFTstable = isUpLeft_Stable(r, c, p);
		boolean DOWN_RIGHTstable = isDownRight_Stable(r, c, p);
		boolean diagonal2Stable = UP_LEFTstable || DOWN_RIGHTstable || isFullDiagonal2(r, c);;
		if (!diagonal2Stable) return false;
		//all stable
		return true;
	}

	private boolean isFullColumn(int c) {
		for (int i = 0; i < 8; i++) if (cells[i][c] == ' ') return false;
		return true;
	}

	private boolean isFullRow(int r) {
		for (int j = 0; j < 8; j++) if (cells[r][j] == ' ') return false;
		return true;
	}

	private boolean isFullDiagonal1(int r, int c) {
		int i = r;
		int j = c;
		while(i < 7 && j > 0){
			i++;
			j--;
		}
		while(i > -1 && j < 8){
			if (cells[i][j] == ' ') return false;
			i--;
			j++;
		}
		return true;
	}
	
	private boolean isFullDiagonal2(int r, int c) {
		int i = r;
		int j = c;
		while(i > 0 && j > 0){
			i--;
			j--;
		}
		while(i < 8 && j < 8){
			if (cells[i][j] == ' ') return false;
			i++;
			j++;
		}
		return true;
	}

	private boolean isUp_Stable(int r, int c, char p) {
		//base case
		if (isOutOfBounds(r, c)) return true;
		if (cells[r][c] != p) return false;
		//recursive case
		return isUp_Stable(r - 1, c, p);
	}

	private boolean isDown_Stable(int r, int c, char p) {
		//base case
		if (isOutOfBounds(r, c)) return true;
		if (cells[r][c] != p) return false;
		//recursive case
		return isDown_Stable(r + 1, c, p);
	}

	private boolean isRight_Stable(int r, int c, char p) {
		//base case
		if (isOutOfBounds(r, c)) return true;
		if (cells[r][c] != p) return false;
		//recursive case
		return isRight_Stable(r, c + 1, p);
	}

	private boolean isLeft_Stable(int r, int c, char p) {
		//base case
		if (isOutOfBounds(r, c)) return true;
		if (cells[r][c] != p) return false;
		//recursive case
		return isLeft_Stable(r, c - 1, p);
	}

	private boolean isUpRight_Stable(int r, int c, char p) {
		//base case
		if (isOutOfBounds(r, c)) return true;
		if (cells[r][c] != p) return false;
		//recursive case
		return isUpRight_Stable(r - 1, c + 1, p);
	}

	private boolean isDownLeft_Stable(int r, int c, char p) {
		//base case
		if (isOutOfBounds(r, c)) return true;
		if (cells[r][c] != p) return false;
		//recursive case
		return isDownLeft_Stable(r + 1, c - 1, p);
	}

	private boolean isUpLeft_Stable(int r, int c, char p) {
		//base case
		if (isOutOfBounds(r, c)) return true;
		if (cells[r][c] != p) return false;
		//recursive case
		return isUpLeft_Stable(r - 1, c - 1, p);
	}

	private boolean isDownRight_Stable(int r, int c, char p) {
		//base case
		if (isOutOfBounds(r, c)) return true;
		if (cells[r][c] != p) return false;
		//recursive case
		return isDownRight_Stable(r + 1, c + 1, p);
	}

	private boolean isOutOfBounds(int r, int c) {
		return (r < 0 || r > 7 || c < 0 || c > 7);
	}

	public int countUnstablePieces(char player) {
		char oponent = (player == 'O') ? 'H' : 'O';
		int cont = 0;
		int prevOpoCount = 0;
		int postOpoCount = 0;
		Board b = null;
		int[] intMove = new int[2];
		for (int i = 0; i < 8; i++){
			intMove[0] = i;
			for (int j = 0; j < 8; j++){
				intMove[1] = j;
				b = makeMove(intMove, oponent, false);
				if (b != null){
					prevOpoCount = this.count(oponent);
					postOpoCount = b.count(oponent);
					cont += (postOpoCount - prevOpoCount);
				}
			}
		}
		return cont;
	}
	
	public int countCorner(char player) {
		int cont = 0;
		if(cells[0][0] == player) cont++;
		if(cells[0][7] == player) cont++;
		if(cells[7][0] == player) cont++;
		if(cells[7][7] == player) cont++;
		return cont;
	}

	public int countPotentialCorner(char player) {
		int cont = 0;
		int[] intMove = new int[2];
		intMove[0] = 0;
		intMove[1] = 0;
		if(this.makeMove(intMove, player, true) != null) cont++;
		intMove[0] = 0;
		intMove[1] = 7;
		if(this.makeMove(intMove, player, true) != null) cont++;
		intMove[0] = 7;
		intMove[1] = 0;
		if(this.makeMove(intMove, player, true) != null) cont++;
		intMove[0] = 7;
		intMove[1] = 7;
		if(this.makeMove(intMove, player, true) != null) cont++;
		return cont;
	}
}