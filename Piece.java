public class Piece {
	protected int player;
	protected int x;
	protected int y;
	protected boolean king;
	
	public Piece(int uPlayer, int uX, int uY) {
		player = uPlayer;
		x = uX;
		y = uY;
		king = false;
	}
	
	public void move(int uX, int uY) {
		x = uX;
		y = uY;
	}
	public boolean canMoveTo(int uX, int uY) {
		if (Board.squares[uX][uY].getPiece() != null)
			return false;
		if (Math.abs(uX-x) == 1 && Math.abs(uY-y) == 1){
			if (uX-x == 1)
				return true;
			else if (king)
				return true;
		}
		if (Math.abs(uX-x) == 2 && Math.abs(uY-y) == 2) {
			if (uX-x == 2) {
				Square curr = Board.squares[x+1][(uY+y)/2];
				if (curr == null)
					return false;
				else 
					return curr.getPiece().player != this.player;
			}
			else {
				if (!king)
					return false;
				Square curr = Board.squares[x-1][(uY+y)/2];
				if (curr == null)
					return false;
				else
					return curr.getPiece().player != this.player;
			}
		}
		return false;
	}
	
	public String toString() {
		return player + " " + x + " " + y + "\n";
	}

}
