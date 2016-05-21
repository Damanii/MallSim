import javax.swing.*; 
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class Options
{
  private BufferedImage img = null;
  public Options()
  { 
    try
      {
        img = ImageIO.read(new File("StoreCard.png"));
      } catch (IOException e)
      {
        System.out.println("NO IMG");
      }
  }
  public void paint(Graphics2D g2d)
  {
      g2d.drawImage(img, 0, 0, null);        
  }
}
  