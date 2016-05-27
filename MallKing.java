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
import java.util.EventObject;
import java.awt.AWTEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.io.InputStream;

public class MallKing extends JPanel
{
  private BufferedImage img = null;
  private static int mouseX;
  private static int mouseY;
  private static boolean playGame;
  private static boolean loadGame;
  private static boolean newGame;
  private static boolean settings;
  private static int cash;
  private static int year;
  private static int month;
  private static int day;
  private static int intro;
  private static Boolean isMuted;
  private static double profit;
  private static double balance;
  private static double expenses;
  private Boolean paused=true;
  Store[] store = new Store[48];
  public static int clickX;
  public static int clickY;
  int[][] mallstore = new int[23][13];
  public static int daylength;
  public static int daymod;
  
  public MallKing()
  {
    addMouseListener(new MouseAdapter() { 
      public void mousePressed(MouseEvent me) 
      {
        mouseX=me.getX();
        mouseY=me.getY();
        clickX=mouseX/60+1;
        clickY=mouseY/60+1;
        if((mouseX>=362&&mouseX<=622&&mouseY>=530&&mouseY<=600)&&!playGame&&!loadGame&&intro==60)
        {
          newGame=true;
        }
        if((mouseX>=659&&mouseX<=919&&mouseY>=530&&mouseY<=600)&&!playGame&&!loadGame&&intro==60)
        {
          loadGame=true;
        } 
        if (mallstore[clickX][clickY]==810&&playGame==true)
        {
          playGame=false;
          loadGame=false;
          newGame=false;
        }
        if(mallstore[clickX][clickY]==805&&playGame==true)
        {
          if(daymod==15){}
          else
          {
            daylength=daylength/2;
            day=day/2;
            daymod=daymod/2;
          }
        }
        if(mallstore[clickX][clickY]==806&&playGame==true)
        {
          if(daymod==960){}
          else
          {
            daylength=daylength*2;
            day=day*2;
            daymod=daymod*2;
          }
        }
        if(mallstore[clickX][clickY]==807&&playGame==true)
        {
          paused=!paused;
        }
        if(mallstore[clickX][clickY]==808&&playGame==true)
        {
          save();
        }
        if(mallstore[clickX][clickY]==809&&playGame==true)
        {
          settings=!settings;
        }
      }       
    }); 
    try { 
      FileReader fr = new FileReader("StoreList.txt"); 
      BufferedReader br = new BufferedReader(fr); 
      for(int i=0;i<48; i++)
      {
        String name=br.readLine();
        int size = Integer.parseInt(br.readLine());
        int stars = Integer.parseInt(br.readLine());
        int profitability = Integer.parseInt(br.readLine());
        int cost = Integer.parseInt(br.readLine());
        store[i]=(new Store(name, size, stars, profitability, cost));
      }
    }
    catch(IOException e){}
  }
  
  public void loadNew()
  {
    cash = 500000;
    year = 1;
    month = 1;
    day  = 120;
    daymod=120;
    daylength=3600;
    loadGame=false;
    playGame=true;  
    newGame=false;   
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
      daymod = Integer.parseInt(br.readLine());
      daylength  = Integer.parseInt(br.readLine());
      br.close(); 
    } catch(IOException e){}
    try { 
      FileReader fr = new FileReader("mallSave.txt"); 
      BufferedReader br = new BufferedReader(fr); 
      for (int a=1;a<23;a++)
      {
        for (int b=1;b<13;b++)
        {
          mallstore[a][b] = Integer.parseInt(br.readLine());
        }
      }      
      br.close(); 
    } catch(IOException e){}
    loadGame=false;
    playGame=true;    
  }
  
  public void save()
  {
    try { 
      FileWriter fw = new FileWriter("save.txt"); 
      PrintWriter pw = new PrintWriter(fw);
      pw.println(cash);
      pw.println(year);
      pw.println(month);
      pw.println(day);
      pw.println(profit);
      pw.println(balance);
      pw.println(expenses);
      pw.println(daymod);
      pw.println(daylength);
      pw.close(); 
    } catch(IOException e){}
    try { 
      FileWriter fw = new FileWriter("mallSave.txt"); 
      PrintWriter pw = new PrintWriter(fw);
      for (int a=1;a<23;a++)
      {
        for (int b=1;b<13;b++)
        {
          pw.println(mallstore[a][b]);
        }
      }      
      pw.close(); 
    } catch(IOException e){}
  }
  
  public void loadstores()
  {
    try { 
      FileReader fr = new FileReader("mallLoad.txt"); 
      BufferedReader br = new BufferedReader(fr); 
      for (int a=1;a<23;a++)
      {
        for (int b=1;b<13;b++)
        {
          mallstore[a][b] = Integer.parseInt(br.readLine());
        }
      }      
      br.close(); 
    } catch(IOException e){}
    loadGame=false;
    playGame=true;   
    newGame=false;
  }
  
  public void newGame(int cash, int day, int month, int year, double profit, double balance, double expenses)
  {
    calendar(year, month, day);
  }
  
  public void calendar(int year, int month, int day)
  {
    if(!paused)
    {
      if(day>=daylength)
      {
        this.month++;
        this.day=daymod;
      }
      if(month>12)
      {
        this.year++;
        this.month=1;
      }
      if(!paused)
      {
        this.day++;
      }
    }
  }
  
  @Override public void paint(Graphics g) 
  {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;    
    if(!playGame)
    {
      try
      {
        if(intro<60)
        {
          img = ImageIO.read(new File("Simoo.png"));
          g.drawImage(img, 0, 0, null); 
          intro++;
        }
        else
        {
          img = ImageIO.read(new File("Title.png"));
          g.drawImage(img, 0, 0, null);  
          
        }
      } catch (IOException e){}
    }
    if(playGame)
    {
      try {
        Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("A.ttf"));
        font = font.deriveFont(48F);
        g.setFont(font);
        Color fontcolor = new Color(33,33,33);
        g.setColor (fontcolor); 
        img = ImageIO.read(new File("Mall.png"));
        g.drawImage(img, 0, 0, null);
        g.drawString("$"+String.valueOf(cash),65,107);
        g.drawString(String.valueOf(month)+"/"+String.valueOf(day/daymod)+"/"+String.valueOf(year),65,45);
      } 
      catch (IOException e){} catch (FontFormatException e){}
      if (settings==true)
      { 
        try
        {
          img = ImageIO.read(new File("Settings.png"));
          g.drawImage(img, 0, 0, null);
          img = ImageIO.read(new File("Increase.png"));
          g.drawImage(img, 0, 0, null);
          img = ImageIO.read(new File("Decrease.png"));
          g.drawImage(img, 0, 0, null);
          img = ImageIO.read(new File("Mute.png"));
          g.drawImage(img, 0, 0, null);
        } catch (IOException e){}
        
      }        
      if (daymod!=15)
      { 
        try
        {
          img = ImageIO.read(new File("Up.png"));
        } catch (IOException e){}
        g.drawImage(img, 0, 0, null);
      }     
      if (daymod!=960)
      { 
        try
        {
          img = ImageIO.read(new File("Down.png"));
        } catch (IOException e){}
        g.drawImage(img, 0, 0, null);
      } 
      if (paused==true)
      { 
        try
        {
          img = ImageIO.read(new File("Play.png"));
        } catch (IOException e){}
        g.drawImage(img, 0, 0, null);
      }
      if (paused==false)
      { 
        try
        {
          img = ImageIO.read(new File("Pause.png"));
        } catch (IOException e){}
        g.drawImage(img, 0, 0, null);
      }
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
        if(newGame)
        {
          m.loadNew();
          m.loadstores();
        }
        else if(playGame)
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