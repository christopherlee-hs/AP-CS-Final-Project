import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel {
	public final static int SIZE = 8;
	public final static int TILE_SIZE = 400/SIZE;
	protected static Square[][] squares = new Square[SIZE][SIZE];
	protected static Piece pieceToMove;
	protected JLabel text;
	
	public Board() {
		this.setVisible(true);
		this.setPreferredSize(new Dimension(600, 500));
		this.setLayout(null);
		
		text = new JLabel();
		text.setBounds(0, 0, 600, 100);
		text.setText("CHECKERS");
		text.setVisible(true);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setVerticalAlignment(SwingConstants.CENTER);
		text.setFont(new Font("Helvetica", Font.PLAIN, (int) ((double) text.getWidth() / text.getFontMetrics(text.getFont()).stringWidth(text.getText()) * text.getFont().getSize() - 40)));
		this.add(text);
		
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Square s = (Square) e.getSource();
				s.setBackground(Color.GREEN);
				if (Game.click++ == 0 || s.getPiece() != null) {
					pieceToMove = s.getPiece();
					for (int i = 0; i < SIZE; i++) {
						for (int j = 0; j < SIZE; j++) {
							boolean temp = pieceToMove.canMoveTo(j, i);
							if (squares[i][j].getPiece() == null) {
								squares[i][j].setEnabled(temp);
								if (temp)
									squares[i][j].setBackground(Color.YELLOW);
								else if ((i+j)%2 == 0)
									squares[i][j].setBackground(Color.WHITE);
							}
							else {
								squares[i][j].setEnabled(squares[i][j].getPiece().player == pieceToMove.player || temp);
								System.out.println(i + " " + j);
								if (temp) {
									squares[i][j].setBackground(Color.YELLOW);
								}
								else if (!(j==s.x && i==s.y))
									squares[i][j].setBackground(Color.WHITE);
							}
						}
					}
				}
				else {
					boolean temp = Math.abs(pieceToMove.x - s.x) == 2;
					move(pieceToMove, s.x, s.y);
					System.out.println(temp + " " + pieceToMove.canJump());
					if (!(temp && pieceToMove.canJump())) {
						Game.click = 0;
						for (int i = 0; i < SIZE; i++) {
							for (int j = 0; j < SIZE; j++) {
								if (squares[i][j].getPiece() != null) {
									squares[i][j].setEnabled(squares[i][j].getPiece().player != pieceToMove.player);
								}
								if ((i+j)%2 == 0)
									squares[i][j].setBackground(Color.WHITE);
							}
						}
					}
					else {
						for (int i = 0; i < SIZE; i++) {
							for (int j = 0; j < SIZE; j++) {
								boolean temp2 = pieceToMove.canJumpTo(j, i);
								squares[i][j].setEnabled(temp2);
								if (temp2)
									squares[i][j].setBackground(Color.YELLOW);
								else if (j == pieceToMove.x && i == pieceToMove.y)
									squares[i][j].setBackground(Color.GREEN);
								else if ((i+j)%2 == 0)
									squares[i][j].setBackground(Color.WHITE);
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
		if ((y == 0 && p.player == 0) || (y == 7 && p.player == 1)) {
			p.king = true;
			Board.squares[y][x].setText(p.toString());
		}
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
