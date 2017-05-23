import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
    private BufferedImage image;
    public ImagePanel(int piece) throws InvalidFileException {
       try {   
    	   switch(piece)
    	   {
    	   case 1:image = ImageIO.read(new File("blue.jpg"));
    	   break;
    	   case 2:image = ImageIO.read(new File("blue king.jpg"));
    	   break;
    	   case 3:image = ImageIO.read(new File("red.jpg"));
    	   break;
    	   case 4:image = ImageIO.read(new File("red king.jpg"));
    	   	break;
    	   }
          
       } catch (IOException e) {
            throw new InvalidFileException();
       }
     
    }
    
 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);           
    }

}