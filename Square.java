import java.awt.Color;
import java.awt.Insets;

import javax.swing.*;

public class Square extends JButton {
	protected int x;
	protected int y;
	protected Piece p;
	
	public Square(int uX, int uY) {
		super();
		x = uX;
		y = uY;
		this.setBounds(x*Board.TILE_SIZE, y*Board.TILE_SIZE+100, Board.TILE_SIZE, Board.TILE_SIZE);
		if ((x+y) % 2 == 1)
			this.setBackground(Color.RED);
		else
			this.setBackground(Color.WHITE);
		this.setEnabled(false);
		this.setMargin(new Insets(0, 0, 0, 0));
	}
	
	public void setPiece(Piece other) {
		this.setEnabled(other != null);
		p = other;
		if (p != null)
			this.setText(p.toString());
		else
			this.setText("");
	}
	
	public Piece getPiece() {
		return p;
	}
	
	public String toString() {
		if (p == null)
			return ".";
		return p.toString();
	}
}
