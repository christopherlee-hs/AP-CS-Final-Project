import java.awt.Color;
import java.awt.Insets;

import javax.swing.*;

@SuppressWarnings("serial")
public class Square extends JButton { // each square is an JButton to inherit its properties plus add coordinates and a piece
	protected int x;
	protected int y;
	protected Piece p;
	
	public Square(int uX, int uY) {
		super();
		x = uX;
		y = uY;
		this.setBounds(x*Board.tileSize, y*Board.tileSize+Board.topGap, Board.tileSize, Board.tileSize);
		// squares form 400x400 grid in bottom left corner
		if ((x+y) % 2 == 1) // make checker board pattern
			this.setBackground(Color.RED);
		else
			this.setBackground(Color.BLACK);
		
		this.setEnabled(false);
		this.setMargin(new Insets(0, 0, 0, 0));
	}
	
	public void setPiece(Piece other) {
		this.setEnabled(other != null); // if there is no piece on the square, it should be disabled; otherwise enabled
		p = other;
		this.setHorizontalAlignment(SwingConstants.CENTER);
		if (p != null)
			this.setIcon(p.getIcon());
		else
			this.setIcon(null);
	}
	
	public Piece getPiece() {
		return p;
	}
	
	public Icon getIcon() {
		if (p == null)
			return null;
		else
			return p.getIcon();
	}
}
