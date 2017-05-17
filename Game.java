import javax.swing.JFrame;

public class Game { // x is vertical, y is horizontal
	protected static Player[] players;
	protected Board board;
	protected static int click;
	
	public Game() {
		board = new Board();
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(board);
		frame.setResizable(false);
		frame.pack();
		
		players = new Player[2];
		players[0] = new Player(0);
		players[1] = new Player(1);
		
		//move(players[0].pieces.get(9), 2, 4);
		
	}
	
	public static void main(String[] args) {
		new Game();
	}
	
}
