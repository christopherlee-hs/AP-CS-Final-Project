import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

@SuppressWarnings("serial")
public class Board extends JPanel { // the board is the panel that contains the board of squares and everything else
	public final static int SIZE = 8; // make the game board any size (even numbers only so both players have same # pieces)
	protected static int boardSize;
	protected static int tileSize; // the board is 400x400
	protected static int topGap;
	protected static Square[][] squares = new Square[SIZE][SIZE];
	protected static ArrayList<Piece> capturedPieces;
	protected static Piece pieceToMove;
	protected JLabel text;
	protected JLabel[] capturedSquares;
	
	public Board(int width, int height) {
		this.setVisible(true);
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(null);
		
		boardSize = (int) (0.8 * height); // scales board size based on JFrame size
		tileSize = boardSize / SIZE;
		boardSize = tileSize * SIZE;
		topGap = height - boardSize;
		
		text = new JLabel(); // Checkers sign at the top; changes to win message
		text.setBounds(0, 0, width, topGap);
		text.setText("CHECKERS");
		text.setVisible(true);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setVerticalAlignment(SwingConstants.CENTER);
		text.setFont(new Font("Helvetica", Font.PLAIN, (int) ((double) text.getWidth() / text.getFontMetrics(text.getFont()).stringWidth(text.getText()) * text.getFont().getSize() - 40)));
		this.add(text);
		
		capturedSquares = new JLabel[23]; // JLabels on right side that will house the captured pieces
		for (int i = 0; i < capturedSquares.length; i++) {
			capturedSquares[i] = new JLabel();
			capturedSquares[i].setBounds(boardSize+tileSize*(i%4), topGap+tileSize*(i/4), tileSize, tileSize);
			capturedSquares[i].setVisible(true);
			this.add(capturedSquares[i]);
		}
		
		capturedPieces = new ArrayList<Piece>(); // keeps track of the pieces that have been captured
		
		ActionListener al = new ActionListener() { // ActionListener for all the squares
			public void actionPerformed(ActionEvent e) {
				Square s = (Square) e.getSource();
				if (Game.click++ == 0 || s.getPiece() != null) { // if the player is clicking their own piece
					s.setBackground(Color.GREEN); // chosen square is green
					pieceToMove = s.getPiece();
					for (int i = 0; i < SIZE; i++) { // set all pieces to enabled/disabled based on where the piece can move
													 // also, the player's own pieces are enabled if player changes their mind
						for (int j = 0; j < SIZE; j++) {
							// sets each square's color and whether it is enabled
							boolean temp = pieceToMove.canMoveTo(j, i);
							if (squares[i][j].getPiece() == null) {
								squares[i][j].setEnabled(temp);
								if (temp) // squares you can jump to are black
									squares[i][j].setBackground(Color.YELLOW);
								else if ((i+j)%2 == 0) // all of the other possible squares are black
									squares[i][j].setBackground(Color.BLACK);
							}
							else { // if the player picks another piece to move
								squares[i][j].setEnabled(squares[i][j].getPiece().player == pieceToMove.player || temp);
								if (temp)
									squares[i][j].setBackground(Color.YELLOW); // rechoose the squares to be yellow
								else if (!(j==s.x && i==s.y))
									squares[i][j].setBackground(Color.BLACK);
							}
						}
					}
				}
				else { // if the player is clicking the square to which to move
					boolean temp = (Math.abs(pieceToMove.x - s.x) == 2) && !(((s.y == 0 && pieceToMove.player == 0) || (s.y == 7 && pieceToMove.player == 1)) && !pieceToMove.king); // if player has just jumped
					move(pieceToMove, s.x, s.y);
					if (!(temp && pieceToMove.canJump())) { // if no double/triple/etc. jumps are possible, change to other 
															// player and set all of other player's pieces to enabled
						Game.click = 0; // reset the click counter
						for (int i = 0; i < SIZE; i++) {
							for (int j = 0; j < SIZE; j++) {
								if (squares[i][j].getPiece() != null) // change whose turn it is
									squares[i][j].setEnabled(squares[i][j].getPiece().player != pieceToMove.player);
								if ((i+j)%2 == 0) // reset all the black squares
									squares[i][j].setBackground(Color.BLACK);
							}
						}
					}
					else { // if double/triple/etc. jumps are possible, force player to jump again
						for (int i = 0; i < SIZE; i++) {
							for (int j = 0; j < SIZE; j++) {
								boolean temp2 = pieceToMove.canJumpTo(j, i);
								squares[i][j].setEnabled(temp2);
								if (temp2)
									squares[i][j].setBackground(Color.YELLOW);
								else if (j == pieceToMove.x && i == pieceToMove.y)
									squares[i][j].setBackground(Color.GREEN);
								else if ((i+j)%2 == 0)
									squares[i][j].setBackground(Color.BLACK);
							}
						}
					}
					// win messages
					if (Game.players[0].hasLost()) {
						text.setText("Black wins!");
						for (Square[] row: squares)
							for (Square a: row)
								a.setEnabled(false);
					}
					else if (Game.players[1].hasLost()) {
						text.setText("Red wins!");
						for (Square[] row: squares)
							for (Square a: row)
								a.setEnabled(false);
					}
				}
					
			}
		};
		
		for (int i = 0; i < SIZE; i++) // add the ActionListener to all the squares
			for (int j = 0; j < SIZE; j++) {
				squares[i][j] = new Square(j, i);
				this.add(squares[i][j]);
				squares[i][j].addActionListener(al);
			}
	}
	
	public void move(Piece p, int x, int y) { // move the piece to the given coordinates
		if (Math.abs(p.x - x) == 2) { // if the player jumped, remove jumped piece
			Piece jumped = Board.squares[(p.y+y)/2][(p.x+x)/2].getPiece();
			Game.players[(p.player + 1)%2].pieces.remove(jumped);
			capturedPieces.add(jumped);
			updateJumped();
			Board.squares[(p.y+y)/2][(p.x+x)/2].setPiece(null);
		}
		Board.squares[p.y][p.x].setPiece(null); // remove square from previous square, move to new square
		Board.squares[y][x].setPiece(p);
		
		p.move(x, y);
		if ((y == 0 && p.player == 0) || (y == 7 && p.player == 1)) { // if the player is at the end of the board, KING
			p.king = true;
			//Board.squares[y][x].setIcon(p.getImage());
			Board.squares[y][x].setIcon(p.getIcon());
		}
	}
	
	public void updateJumped() {
		//TODO: finish this function
		Collections.sort(capturedPieces);
		for (int i = 0; i < capturedPieces.size(); i++) {
			capturedSquares[i].setHorizontalAlignment(SwingConstants.LEFT);
			capturedSquares[i].setVerticalAlignment(SwingConstants.TOP);
			capturedSquares[i].setIcon(capturedPieces.get(i).getIcon());
		}
		
		//this.add(comp)
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
	
	public static boolean canMove(int player) {
		for (Piece p: Game.players[player].pieces)
			for (Square[] row: squares)
				for (Square s: row)
					if (p.canMoveTo(s.x, s.y)) 
						return true;
		return false;
	}
}
