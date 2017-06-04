import javax.swing.*;

public class Piece implements Comparable<Piece> { // Each game piece with variables for: coordinates, which player, and whether or not it is a king
	protected int player; // 0 for bottom player (currently x's), 1 for top player (currently y's)
	protected int x; // x-coordinate, starting at 0 and counting up when moving right
	protected int y; // y-coordinate, starting at 0 and counting up when moving down
	protected boolean king; // false if not a king, true if it is a king
	
	public Piece(int uPlayer, int uX, int uY) {
		player = uPlayer;
		x = uX;
		y = uY;
		king = false;
	}
	
	public void move(int uX, int uY) { // moves the piece to the new coordinates
		x = uX;
		y = uY;
	}
	
	// for both canMoveTo and canJumpTo, when using row major order with i denoting rows and j denoting columns,
	// make sure to switch i and j (e.g. squares[j][i] will denote the square at (i, j))
	public boolean canMoveTo(int uX, int uY) { // sees if you can move to a given square
		if (!(uX >= 0 && uX < Board.SIZE && uY >= 0 && uY < Board.SIZE) || Board.squares[uY][uX].getPiece() != null) 
			// tests if out of range or if the given square is already filled
			return false;
		if (Math.abs(uX-x) == 1 && Math.abs(uY-y) == 1) { // if player moves one diagonally
			if ((uY-y == 1 && player == 1) || (uY-y == -1 && player == 0)) // if piece moves forward w.r.t the player
				return true;
			else if (king) // if the player is a king, can move in any direction diagonally
				return true;
		}
		if (Math.abs(uX-x) == 2 && Math.abs(uY-y) == 2) { // if player moves two diagonally, test using canJumpTo method
			return canJumpTo(uX, uY);
		}
		return false; // if the move does not fit any of the above criteria, then it is not valid
	}
	
	public boolean canJumpTo(int uX, int uY) { // tests if player can jump to a given square
		if (!(uX >= 0 && uX < Board.SIZE && uY >= 0 && uY < Board.SIZE) || Board.squares[uY][uX].getPiece() != null)
			// tests if out of range or if the given square is already filled
			return false;
		if (Math.abs(uX-x) == 2 && Math.abs(uY-y) == 2) { // make sure the move is two diagonally
			if ((uY-y == 2 && player == 1) || (uY-y == -2 && player == 0)) { // see if piece is moving forward w.r.t. the player
				Square curr = Board.squares[(uY+y)/2][(uX+x)/2]; // the jumped square
				if (curr.getPiece() == null) // make sure there is a piece to jump
					return false;
				else {
					return curr.getPiece().player != this.player; // make sure the piece to jump is for the opposite player
				}
			}
			else {
				if (!king) // if the piece is not a king, cannot jump backwards
					return false;
				else {
					Square curr = Board.squares[(uY+y)/2][(uX+x)/2]; // the jumped square
					if (curr.getPiece() == null) // make sure there is a piece to jump
						return false;
					else
						return curr.getPiece().player != this.player; // make sure the piece to jump is for the opposite player
				}
			}
		}
		return false; // if the piece does not fit any of the above criteria, the piece cannot jump
	}
	
	public boolean canJump() { // test if the piece can jump to any square two diagonally away from current square
		return canJumpTo(x-2, y-2) || canJumpTo(x+2, y-2) || canJumpTo(x-2, y+2) || canJumpTo(x+2, y+2);
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

	public Icon getIcon() {
		if (player == 0) {
			if (king)
				return new RedKingCircleIcon();
			return new RedCircleIcon();
		}
		if (king)
			return new BlackKingCircleIcon();
		return new BlackCircleIcon();
	}

	@Override
	public int compareTo(Piece o) {
		if (o.player == this.player) {
			if (o.king)
				return -1;
			else if (this.king)
				return 1;
			else
				return 0;
		}
		else
			return -1 * (int) ((o.player - 0.5) * 2);
	}
}
