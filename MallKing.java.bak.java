import javax.swing.*; 
import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class MallKing extends JPanel
{
  private BufferedImage img = null;
  private static int mouseX;
  private static int mouseY;
  private static boolean playGame;
  private static boolean loadGame;
  private static boolean newGame;
  private static boolean settings=false;
  private static Boolean isMuted=false;
  private static Boolean changeStore=false;
  private static Boolean storeMenu=false;
  private static Boolean pickStore=false;
  private static Boolean confirm=false;
  private static Boolean overpriced=false;
  private static int cash;
  private static int year;
  private static int month;
  private static int day;
  private static int intro=59;
  private static int menu=0;
  private static int choice=0;
  private static double profit;
  private static double balance;
  private static double expenses;
  private Boolean paused=true;
  Store[] store = new Store[48];
  public static int clickX;
  public static int clickY;
  int[] locX = new int[33];
  int[] locY = new int[33];
  int[] locsize = new int[33];
  int[][] clickGrid = new int[23][13];
  Store[] choicelist = new Store[9];
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
        System.out.println(mouseX+":"+mouseY);
        //System.out.println(clickGrid[clickX][clickY]);
        if((mouseX>=362&&mouseX<=622&&mouseY>=530&&mouseY<=600)&&!playGame&&!loadGame&&intro==60)
        {
          newGame=true;
        }
        if((mouseX>=659&&mouseX<=919&&mouseY>=530&&mouseY<=600)&&!playGame&&!loadGame&&intro==60)
        {
          loadGame=true;
        } 
        if (clickGrid[clickX][clickY]==810)
        {
          loadgrid("gridClear.txt");
          playGame=false;
          loadGame=false;
          newGame=false;
        }
        if(clickGrid[clickX][clickY]==805)
        {
          if(daymod==15){}
          else
          {
            daylength=daylength/2;
            day=day/2;
            daymod=daymod/2;
          }
        }
        if(clickGrid[clickX][clickY]==806)
        {
          if(daymod==960){}
          else
          {
            daylength=daylength*2;
            day=day*2;
            daymod=daymod*2;
          }
        }
        if(clickGrid[clickX][clickY]==807)
        {
          paused=!paused;
        }
        if(clickGrid[clickX][clickY]==808)
        {
          save();
        }
        if(clickGrid[clickX][clickY]==809&&changeStore==false)
        {
          settings=!settings;
        }
        else if(clickGrid[clickX][clickY]<=133&&changeStore==false)
        {
          if (storeMenu==true&&menu==clickGrid[clickX][clickY])
          {
            storeMenu=false;
            menu=0;
          }
          else
          {
            storeMenu=true;
            menu=clickGrid[clickX][clickY];
          }  
        }
        else if(clickGrid[clickX][clickY]==811&&storeMenu==true&&changeStore==false)
        {
          storeMenu=false;
          menu=0;
        }
        else if(clickGrid[clickX][clickY]==812&&storeMenu==true&&changeStore==false&&confirm==false)
        {
          savetemp();
          loadgrid("gridPicker.txt");
          choices();
          changeStore=true;
          settings=false;
        }
        else if(clickGrid[clickX][clickY]==813&&storeMenu==true&&changeStore==false)
        {
//Promotions
        } 
        else if(clickGrid[clickX][clickY]==818)
        {
          storeMenu=false;
          for (int a=1;a<23;a++)
          {
            for (int b=1;b<13;b++)
            {
              if(clickGrid[a][b]==store[menu].counter)
              {
                clickGrid[a][b]=store[menu].location;
              }
            }
          }  
          store[menu].placed=false;
        } 
        else if(changeStore==true&&clickGrid[clickX][clickY]==817)
        {
          loadgrid("gridTemp.txt");
          changeStore=false;
        }
        else if(clickGrid[clickX][clickY]>900&&clickGrid[clickX][clickY]<999)
        {
          choice=clickGrid[clickX][clickY]-901;
          if(choicelist[choice].cost>cash)
          {
            overpriced=true;
            loadgrid("gridOverpriced.txt");
          }
          else
          {
            loadgrid("gridConfirm.txt");
            confirm=true;         
          }
        }
        else if(clickGrid[clickX][clickY]==1001)
        {
          loadgrid("gridTemp.txt");
          confirm=false;
          changeStore=false;
          storeMenu=false;
          choicelist[choice].location=menu;
          for (int a=1;a<23;a++)
          {
            for (int b=1;b<13;b++)
            {
              if(clickGrid[a][b]==menu)
              {
                clickGrid[a][b]=choicelist[choice].counter;
              }
            }
          }  
          choicelist[choice].placed=true;
          cash-=choicelist[choice].cost;
        }
        else if(clickGrid[clickX][clickY]==1002)
        {
          loadgrid("gridPicker.txt");
          confirm=false;
        }
        else if(clickGrid[clickX][clickY]==1100)
        {
          loadgrid("gridPicker.txt");
          overpriced=false;
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
        store[i]=(new Store(name, size, stars, profitability, cost,i));
      }
      br.close(); 
    }
    catch(IOException e){}
    try { 
      FileReader fr = new FileReader("Picture.txt"); 
      BufferedReader br = new BufferedReader(fr);
      for(int i=0;i<33; i++)
      {
        locsize[i]=Integer.parseInt(br.readLine());
        locX[i]=Integer.parseInt(br.readLine());
        locY[i]=Integer.parseInt(br.readLine());
      }
      br.close(); 
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
    storeMenu=false;
    paused=true;
    changeStore=false;
  }
  
  public void choices()
  {
    //store[9].placed=true;
    int counter=0;
    for(int i=0;i<48; i++)
    {
      if(store[i].placed==false&&counter<9)
      {
        choicelist[counter]=store[i];
        counter++;
      }
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
      daymod = Integer.parseInt(br.readLine());
      daylength  = Integer.parseInt(br.readLine());
      storeMenu=false;
      changeStore=false;
      paused=true;
      br.close(); 
    } catch(IOException e){}
    loadgrid("gridSave.txt");
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
      FileWriter fw = new FileWriter("gridSave.txt"); 
      PrintWriter pw = new PrintWriter(fw);
      for (int a=1;a<23;a++)
      {
        for (int b=1;b<13;b++)
        {
          pw.println(clickGrid[a][b]);
        }
      }      
      pw.close(); 
    } catch(IOException e){}
  }
  
  public void loadgrid(String file)
  {
    try { 
      FileReader fr = new FileReader(file); 
      BufferedReader br = new BufferedReader(fr); 
      for (int a=1;a<23;a++)
      {
        for (int b=1;b<13;b++)
        {
          clickGrid[a][b] = Integer.parseInt(br.readLine());
        }
      }      
      br.close(); 
    } catch(IOException e){}
  }
  
  public void savetemp()
  {
    try { 
      FileWriter fw = new FileWriter("gridTemp.txt"); 
      PrintWriter pw = new PrintWriter(fw);
      for (int a=1;a<23;a++)
      {
        for (int b=1;b<13;b++)
        {
          pw.println(clickGrid[a][b]);
        }
      }      
      pw.close(); 
    } catch(IOException e){}
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
        
        img = ImageIO.read(new File("Mall.png"));
        g.drawImage(img, 0, 0, null);
        Color fontcolor = new Color(33,33,33);
        g.setColor (fontcolor); 
        g.drawString("$"+String.valueOf(cash),65,107);
        g.drawString(String.valueOf(month)+"/"+String.valueOf(day/daymod)+"/"+String.valueOf(year),65,45);
      } 
      catch (IOException e){} catch (FontFormatException e){}
      
      for (int a=0;a<34;a++)
      {
        if (store[a].placed==true)
        {
          String location="";
          if (locsize[store[a].location-101]==1)
          {
            location=(store[a].counter+1)+"s.png";
          }
          else
          {
            location+=(store[a].counter+1)+".png";
          }
          try
          {
            img = ImageIO.read(new File(location));
          } catch (IOException e){}
          g.drawImage(img, locX[store[a].location-101], locY[store[a].location-101], null);
        }
      }
      
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
      if (storeMenu==true)
      { 
        try
        {
          img = ImageIO.read(new File("Store.png"));
        } catch (IOException e){}
        g.drawImage(img, 0, 0, null);
        Color fontcolor = new Color(222,222,222);
        g.setColor (fontcolor); 
        try {
          Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("A.ttf"));
          font = font.deriveFont(36F);
          g.setFont(font);
        }
        catch (IOException e){} catch (FontFormatException e){}
        if (menu<100)
        {
          g.drawString(store[menu].name,5,160);
          g.drawString("Replace Store",64,285);
          try
          {
            img = ImageIO.read(new File("Remove.png"));
            g.drawImage(img, 0, 0, null);
            img = ImageIO.read(new File("Promotions.png"));
            g.drawImage(img, 0, 0, null);
          }catch (IOException e){}
        }
        else
        {
          g.drawString("No Store",5,160);
          g.drawString("Add Store",64,285);
        }
      }   
      if (changeStore==true&&storeMenu==true)
      { 
        try
        {
          img = ImageIO.read(new File("ChangeStore.png"));
          g.drawImage(img, 0, 0, null);
          for(int i=0;i<9; i++)
          {
            g.drawString(choicelist[i].name,128, i*60+166);
            g.drawString(String.valueOf(choicelist[i].cost),400, i*60+166);
          }
        }      
        catch (IOException e){}
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
      if (overpriced==true)
      { 
        try
        {
          img = ImageIO.read(new File("Overpriced.png"));
        } catch (IOException e){}
        g.drawImage(img, 0, 0, null);
        try
        {
          Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("A.ttf"));
          font = font.deriveFont(48F);
          g.setFont(font);
          Color fontcolor = new Color(96,125,139);
          g.setColor (fontcolor);
        }
        catch (IOException e){} catch (FontFormatException e){}
        g.drawString(String.valueOf(choicelist[choice].name),480,400);
      }
      if (confirm==true)
      { 
        try
        {
          img = ImageIO.read(new File("Confirm.png"));
        } catch (IOException e){}
        g.drawImage(img, 0, 0, null);
        try
        {
          Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("A.ttf"));
          font = font.deriveFont(48F);
          g.setFont(font);
        }
        catch (IOException e){} catch (FontFormatException e){}
        Color fontcolor = new Color(96,125,139);
        g.setColor (fontcolor);
        g.drawString(String.valueOf(choicelist[choice].name),480,400);
      }
    }
  }
  
  public void reset()
  {
    changeStore=false;
    settings=false;
    storeMenu=false;
    pickStore=false;
    confirm=false;
    isMuted=false;
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
          m.loadgrid("gridLoad.txt");
          loadGame=false;
          playGame=true;   
          newGame=false;
          m.reset();
        }
        else if(playGame)
        {
          m.newGame(cash,day,month,year,profit,balance,expenses);
        }
        else if(loadGame)
        {
          m.loading(); 
          m.reset();
        }
        m.repaint(); 
        Thread.sleep(10); 
      }
    }  
  }
}