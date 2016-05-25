import javax.swing.*; 
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class Store
{
  private int size;
  private int stars;
  private int profitability;
  private int cost;
  public Store()
  {
  }
  public Store(String Name, int size, int stars, int profitability, int cost)
  {
    this.size=size;
    this.stars=stars;
    this.profitability=profitability;
    this.cost=cost;
  }
  public double calculateRevenue()
  {
    if(stars==1)
    {
    }
    else if(stars==2)
    {
    }
    else if(stars==3)
    {
    }
    else if(stars==4)
    {
    }
    else
    {
    }
  }
  public double calculateExpenses()
  {
    if(size==1)
    {
    }
    else if(size==2)
    {
    }
    else if(size==3)
    {
    }
    else
    {
    }
  }
}