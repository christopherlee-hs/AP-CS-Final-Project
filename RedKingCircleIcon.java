import java.awt.*;

import javax.swing.Icon;

public class RedKingCircleIcon implements Icon {

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setColor(Color.RED);
		int gap = (Board.tileSize - getIconWidth())/2;
		g2.fillOval(gap, gap, getIconWidth(), getIconHeight());
		g2.setColor(Color.BLACK);
		g2.drawString("K", 22, 30);
		g2.dispose();
	}

	@Override
	public int getIconWidth() {
		return (int) (0.8 * Board.tileSize);
	}

	@Override
	public int getIconHeight() {
		return (int) (0.8 * Board.tileSize);
	}

}
