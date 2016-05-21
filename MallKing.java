import javax.swing.*; 
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.Object;
import  java.util.EventObject;
import java.awt.AWTEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

public class MallKing extends JPanel
{
  private TitleScreen t =new TitleScreen();
  private Mall ma =new Mall();
  private BufferedImage img = null;
  private static int mouseX;
  private static int mouseY;
  private static boolean playGame;
  private static boolean loadGame;
  private static int cash;
  private static int year;
  private static int month;
  private static int day;
  private static Boolean isMuted;
  private static double profit;
  private static double balance;
  private static double expenses;
  private Boolean paused=false;
  Store[] store = new Store[48];
  
  public MallKing()
    //after load set back to false
  {
    addMouseListener(new MouseAdapter() { 
      public void mousePressed(MouseEvent me) 
      {
        mouseX=me.getX();
        mouseY=me.getY();
        //System.out.println(mouseX+" , "+ mouseY);
        if((mouseX>=215&&mouseX<=475&&mouseY>=530&&mouseY<=600)&&!playGame&&!loadGame)
        {
          playGame=true;
          //System.out.println("PLAY");
        }
        if((mouseX>=510&&mouseX<=770&&mouseY>=530&&mouseY<=600)&&!playGame&&!loadGame)
        {
          loadGame=true;
          //System.out.println("LOAD");
        }
        if((mouseX>=805&&mouseX<=1070&&mouseY>=530&&mouseY<=600)&&!playGame&&!loadGame)
        {
          //System.out.println("OPTIONS");
        }
        //System.out.println((mouseX/38)+1);
        //System.out.println((mouseY/38)+1);
        //System.out.println(stores[(mouseX/38)+1][(mouseY/38)+1]);
        
      }}); 
    for(int i=0;i<49; i++)
    {
      
    }
    
  }
  
  public void loading()
  {
    try { 
      FileReader fr = new FileReader("save.txt"); 
      BufferedReader br = new BufferedReader(fr); 
      cash = Integer.parseInt(br.readLine());
      year = Integer.parseInt(br.readLine());
      month = Integer.parseInt(br.readLine());
      day  = Integer.parseInt(br.readLine());
      profit = Double.parseDouble((br.readLine()));
      balance = Double.parseDouble(br.readLine());
      expenses = Double.parseDouble(br.readLine());
      br.close(); 
    } catch(IOException e) 
    {
    }
    loadGame=false;
    playGame=true;
    
  }
  
  public void newGame(int cash, int day, int month, int year, double profit, double balance, double expenses)
  {
    calendar(year, month, day);
  }
  
  public void calendar(int year, int month, int day)
  {
    this.year=year;
    this.month=month;
    this.day=day;
    if(!paused)
    {// change variables for day and division for slow/fast
      if(day==3600)
      {
        month++;
        this.day=1;
      }
      if(month==12)
      {
        year++;
        this.month=1;
      }
      if(!paused)
      {
        this.day++;
      }
      //System.out.println((this.day/120));
    }
  }
  
  @Override public void paint(Graphics g) 
  {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;    
    t.paint(g2d);
    if(playGame)
    {
      ma.paint(g2d);
    }
  }
  
  
  public static void main(String[]args) throws InterruptedException
  { 
    JFrame frame = new JFrame("Mall King");
    MallKing m = new MallKing();
    frame.add(m); 
    frame.setSize(1280, 760);
    frame.setVisible(true); 
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    while (true) {
      {
        if(playGame)
        {
          m.newGame(cash,day,month,year,profit,balance,expenses);
        }
        else if(loadGame)
        {
          m.loading();
        }
        m.repaint(); 
        Thread.sleep(10); 
      }
    }
  }
}
