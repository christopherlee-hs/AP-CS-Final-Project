import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel {
	public final static int SIZE = 8;
	public final static int TILE_SIZE = 400/SIZE;
	protected static Square[][] squares = new Square[SIZE][SIZE];
	protected static Piece pieceToMove;
	
	public Board() {
		this.setVisible(true);
		this.setPreferredSize(new Dimension(600, 500));
		this.setLayout(null);
		
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Square s = (Square) e.getSource();
				if (Game.click++ == 0 || s.getPiece() != null) {
					pieceToMove = s.getPiece();
					for (int i = 0; i < SIZE; i++) {
						for (int j = 0; j < SIZE; j++) {
							if (squares[i][j].getPiece() == null)
								squares[i][j].setEnabled(pieceToMove.canMoveTo(j, i));
							else
								squares[i][j].setEnabled(squares[i][j].getPiece().player == pieceToMove.player || pieceToMove.canMoveTo(j, i));
						}
					}
				}
				else {
					Game.click = 0;
					move(pieceToMove, s.x, s.y);
					for (int i = 0; i < SIZE; i++) {
						for (int j = 0; j < SIZE; j++) {
							if (squares[i][j].getPiece() != null) {
								squares[i][j].setEnabled(squares[i][j].getPiece().player != pieceToMove.player);
							}
						}
					}
				}
					
			}
		};
		
		for (int i = 0; i < SIZE; i++) 
			for (int j = 0; j < SIZE; j++) {
				squares[i][j] = new Square(j, i);
				this.add(squares[i][j]);
				squares[i][j].addActionListener(al);
			}
	}
	
	public void move(Piece p, int x, int y) {
		if (Math.abs(p.x - x) == 2) {
			Game.players[(p.player + 1)%2].pieces.remove(Board.squares[(p.y+y)/2][(p.x+x)/2]);
			Board.squares[(p.y+y)/2][(p.x+x)/2].setPiece(null);
		}
		Board.squares[p.y][p.x].setPiece(null);
		Board.squares[y][x].setPiece(p);
		p.move(x, y);
	}
	
	public String toString() {
		String s = "";
		for (Square[] a: squares) {
			for (Square b: a)
				s += b;
			s += "\n";
		}
		return s;
	}
}
