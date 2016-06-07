import javax.swing.*; 
import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.lang.Object;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JComponent;
import javax.swing.JOptionPane;


//encrypt cheat code 


public class Store
{
  private int size;
  private int stars;
  private int profitability;
  public int cost;
  public String name;
  public boolean isPlaced;
  public int counter;
  public int x;
  public int y;
  public int locsize;
  public int location;
  public boolean isBurned;
  public Store()
  {
  }
  public Store(String Name, int size, int stars, int profitability, int cost,int counter, boolean isPlaced)
  {
    this.size=size;
    this.stars=stars;
    this.profitability=profitability;
    this.cost=cost;
    this.name=Name;
    this.isPlaced=false;
    this.counter=counter;
  }
  public boolean getIsPlaced()
  {
    return isPlaced;
  }
  public double calculateRevenue()// Add special money making events (read from list, person visited mall boosted proft)santa always on 24th example
  {
    int random = (int)(Math.random()*40);
    //System.out.println(name);
    if(stars==1)
    {
      //System.out.println(profitability*stars*40*random-500);
      return profitability*stars*40*random-500;
    }
    else if(stars==2)
    {
      //System.out.println(profitability*stars*50*random-2000*(5-stars));
      return profitability*stars*50*random-2000*(5-stars);
    }
    else if(stars==3)
    {
      //System.out.println(profitability*stars*60*random-3000*(5-stars));
      return profitability*stars*60*random-3000*(5-stars);
    }
    else if(stars==4)
    {
      //System.out.println(profitability*stars*70*random-4000*(5-stars));
      return profitability*stars*70*random-4000*(5-stars);
    }
    else
    {
      return 0;
    }
  }
  public double calculateExpenses()
  {
    JFrame frame = new JFrame("Mall");
    int a = (int)(Math.random()*10);
    if(a==1)
    {
      JOptionPane.showMessageDialog(frame,name+" Has burned down!!!!\n     Store has been removed","OH NOSE",JOptionPane.WARNING_MESSAGE);
      isBurned=true;
      isPlaced=false;
    }
    
    int rand = (int)(Math.random()*100000);
    if(size==1)
    {
      if(rand==1)
      {
        JOptionPane.showMessageDialog(frame,"There has been a robbery  in "+name+"!!!!\n-$20000","OH NOSE",JOptionPane.WARNING_MESSAGE);
        return -20000;
      }
    }
    else if(size==2)
    {
      if(rand==1)
      {
        JOptionPane.showMessageDialog(frame,"There has been a robbery  in "+name+"!!!!\n-$40000","OH NOSE",JOptionPane.WARNING_MESSAGE);
        return -40000;
      }
    }
    else if(size==3)
    {
      if(rand==1)
      {
        JOptionPane.showMessageDialog(frame,"There has been a robbery  in "+name+"!!!!\n-$60000","OH NOSE",JOptionPane.WARNING_MESSAGE);
        return -60000;
      }
    }
    else if(size==4)
    {
      if(rand==1)
      {
        JOptionPane.showMessageDialog(frame,"There has been a robbery  in "+name+"!!!!\n-$80000","OH NOSE",JOptionPane.WARNING_MESSAGE);
        return -80000;
      }
    }
    return 0;
  }
}