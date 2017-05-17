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
		if (!(uX >= 0 && uX < Board.SIZE && uY >= 0 && uY < Board.SIZE) || Board.squares[uY][uX].getPiece() != null)
			return false;
		if (Math.abs(uX-x) == 1 && Math.abs(uY-y) == 1) {
			if ((uY-y == 1 && player == 1) || (uY-y == -1 && player == 0))
				return true;
			else if (king)
				return true;
		}
		if (Math.abs(uX-x) == 2 && Math.abs(uY-y) == 2) {
			return canJumpTo(uX, uY);
		}
		return false;
	}
	
	public boolean canJumpTo(int uX, int uY) {
		if (!(uX >= 0 && uX < Board.SIZE && uY >= 0 && uY < Board.SIZE) || Board.squares[uY][uX].getPiece() != null)
			return false;
		if (Math.abs(uX-x) == 2 && Math.abs(uY-y) == 2) {
			if ((uY-y == 2 && player == 1) || (uY-y == -2 && player == 0)) {
				Square curr = Board.squares[(uY+y)/2][(uX+x)/2];
				if (curr.getPiece() == null)
					return false;
				else {
					return curr.getPiece().player != this.player;
				}
			}
			else {
				if (!king)
					return false;
				Square curr = Board.squares[(uY+y)/2][(uX+x)/2];
				if (curr.getPiece() == null)
					return false;
				else
					return curr.getPiece().player != this.player;
			}
		}
		return false;
	}
	
	public boolean canJump() {
		return canMoveTo(x-2, y-2) || canMoveTo(x+2, y-2) || canMoveTo(x-2, y+2) || canMoveTo(x+2, y+2);
	}
	
	public String toString() {
		if (king) {
			if (player == 0)
				return "xx";
			return "oo";
		}
		if (player == 0)
			return "x";
		return "o";
	}

}
