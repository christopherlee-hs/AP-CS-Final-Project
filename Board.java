public class Board {
	public final static int SIZE = 8;
	protected static Square[][] squares = new Square[SIZE][SIZE];
	
	public Board() {
		for (int i = 0; i < SIZE; i++) 
			for (int j = 0; j < SIZE; j++)
				squares[i][j] = new Square(i, j);

	}
	
}
