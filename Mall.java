import javax.swing.*; 
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class Mall
{
  private BufferedImage img = null;
  public Mall()
  {
    try {
      img = ImageIO.read(new File("MALL1.png"));
    } catch (IOException e) 
    {
      System.out.println("NO IMG");
    }
  }
  
  public void allocateStoreSlot()
  {
    
  }
  
  public void paint(Graphics2D g2d)
  {
    g2d.drawString("Click the mouse inside the window.",40,45);
    g2d.drawImage(img, 0, 0, null);        
  }
}