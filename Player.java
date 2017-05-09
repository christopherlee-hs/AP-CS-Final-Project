import java.util.ArrayList;

public class Player {
	protected static ArrayList<Piece> pieces = new ArrayList<Piece>();
	
	public Player(int x) {
		if (x==0)
			for (int i = 0; i < Board.SIZE/2 - 1; i++)
				for (int j = 0; j < Board.SIZE/2; j++)
					pieces.add(new Piece(0, i, j*2+i%2));
		else
			for (int i = Board.SIZE - 1; i > Board.SIZE/2; i--)
				for (int j = 0; j < Board.SIZE/2; j++)
					pieces.add(new Piece(1, i, j*2+i%2));

	}
}
