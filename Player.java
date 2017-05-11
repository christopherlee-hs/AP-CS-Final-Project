import java.util.ArrayList;

public class Player {
	protected ArrayList<Piece> pieces = new ArrayList<Piece>();
	
	public Player(int x) {
		if (x==1)
			for (int i = 0; i < Board.SIZE/2 - 1; i++)
				for (int j = 0; j < Board.SIZE/2; j++) {
					Piece p = new Piece(1, j*2+i%2, i);
					pieces.add(p);
					Square s = Board.squares[i][j*2+i%2];
					s.setPiece(p);
					s.setText(s.toString());
					s.setEnabled(false);
				}
		else
			for (int i = Board.SIZE - 1; i > Board.SIZE/2; i--)
				for (int j = 0; j < Board.SIZE/2; j++) {
					Piece p = new Piece(0, j*2+i%2, i);
					pieces.add(p);
					Square s = Board.squares[i][j*2+i%2];
					s.setPiece(p);
					s.setText(s.toString());
					s.setEnabled(true);
				}
	}
	
	public boolean hasLost() {
		return pieces.size() == 0;
	}
}
