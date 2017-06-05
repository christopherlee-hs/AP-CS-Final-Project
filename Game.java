import java.awt.*;
import javax.swing.*;

public class Game {
	protected static Player[] players;
	protected Board board;
	protected static int click;
	protected static int width;
	protected static int height;
	public final static double SCALE_WIDTH = 600.0/1366;
	public final static double ASPECT_RATIO = 5.0/6;
	
	public Game() { // sets everything up, runs the program
		Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		width = (int) (r.getWidth() * SCALE_WIDTH); // scales the frame based on the screen resolution
		height = (int) (ASPECT_RATIO * width);
		board = new Board(width, height);
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(board);
		frame.pack();
		frame.setResizable(false);
		double screenWidth = r.getWidth();
		double screenHeight = r.getHeight();
		frame.setLocation((int) ((screenWidth - width) / 2), (int) ((screenHeight - height) / 2));
		players = new Player[2];
		players[0] = new Player(0); // black player
		players[1] = new Player(1); // red player
		
	}
	
	public static void main(String[] args) {
		new Game();
	}
	
}
