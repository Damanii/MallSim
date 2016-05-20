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
  public int[][] stores = new int[34][19];
  private BufferedImage img = null;
  public Mall()
  {
    
    stores[0][0] = 123;
    stores[0][1] = 123;
    stores[0][2] = 123;
    System.out.println(stores[(mouseX/38)+1][(mouseY/38)+1]);
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
    g2d.drawImage(img, 0, 0, null);        
  }
}