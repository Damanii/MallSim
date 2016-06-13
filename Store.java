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
//make user get less money when store is placed early

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
  public int timeRun;
  public int m=1;//used for doubling profitability
  public int dayPlaced;
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
    timeRun--;
    if(timeRun>0)
    {
      m=2;
    }
    else
    {
      m=1;
    }
    System.out.println(m);
    int random = (int)(Math.random()*40);
    if(stars==1)
    {
      return (profitability*stars*40*random-500)*m;
    }
    else if(stars==2)
    {
      return (profitability*m*stars*50*random-2000*(5-stars))*m;
    }
    else if(stars==3)
    {
      return (profitability*m*stars*60*random-3000*(5-stars))*m;
    }
    else if(stars==4)
    {
      return (profitability*m*stars*70*random-4000*(5-stars)*m);
    }
    else
    {
      return 0;
    }
  }
  
  public double promotion(int cash)
  {
    JFrame frame = new JFrame("Promotion");
    if(timeRun-1>0)
    {
      JOptionPane.showMessageDialog(frame,"The promotion still has "+ (timeRun-1) +" month(s) left");
      return 0; 
    }
    double promotionCost=0;
      do
      {
        String runTime = JOptionPane.showInputDialog(frame, "How long would you like to run the promotion for (in Months)?");
        System.out.println(runTime);
        if(runTime!=null)
        {
          timeRun=Integer.parseInt(runTime);
          while(!isInteger(runTime))
          {
            if(isInteger(runTime))
            {
              timeRun=Integer.parseInt(runTime);
            }
            else
            {
              runTime = JOptionPane.showInputDialog(frame, "How long would you like to run the promotion for (in Months)?");
            }
          }
          promotionCost=(((cost*0.05*(timeRun*30))/5.0));
        }
      }while(promotionCost>cash);
      
      String promo=String.valueOf(promotionCost);
      Object[] options = {"Run Promo","Cancel"};
      int n = JOptionPane.showOptionDialog(frame,"This promotion will cost $"+ promo,"Promotion",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
      if(n==0)
      {
        timeRun++;
        m=2;
        return promotionCost;
      }
      return 0;
  }
  
  public boolean isInteger( String input ) {
    try {
      Integer.parseInt( input );
      return true;
    }
    catch( Exception e ) {
      return false;
    }
  }
  
  public double calculateExpenses()
  {
    JFrame frame = new JFrame("Mall");
    int a = (int)(Math.random()*1000000);
    if(a==1)
    {
      JOptionPane.showMessageDialog(frame,name+" Has burned down!!!!\n     Store has been removed","OH NOSE",JOptionPane.WARNING_MESSAGE);
      isPlaced=false;
      isBurned=true;
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