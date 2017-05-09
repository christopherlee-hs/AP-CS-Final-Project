public class Game {
	protected Player[] players;
	protected Board board;
	
	public Game() {
		players = new Player[2];
		players[0] = new Player(0);
		players[1] = new Player(1);
		
		board = new Board();
	}
	public static void main(String[] args) {
		
	}
}
