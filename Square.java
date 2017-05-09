public class Square {
	protected int x;
	protected int y;
	protected Piece p;
	public Square(int uX, int uY) {
		x = uX;
		y = uY;
	}
	
	public void setPiece(Piece other) {
		p = other;
	}
	
	public Piece getPiece() {
		return p;
	}
}
