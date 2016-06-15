import javax.swing.*; 
import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class MallKing extends JPanel
{
  private BufferedImage img = null;
  private static int mouseX;
  private static int mouseY;
  private static boolean playGame;
  private static boolean loadGame;
  private static boolean newGame;
  private static boolean repair=false;
  private static boolean remove=false;
  private static boolean exit=false;
  private static boolean settings=false;
  private static Boolean isMuted=false;
  private static Boolean changeStore=false;
  private static Boolean storeMenu=false;
  private static Boolean confirm=false;
  private static Boolean overpriced=false;
  private static Boolean overpricedrep=false;
  private static int cash;
  private static int year;
  private static int month;
  private static int day;
  private static int intro=60;
  private static int menu=0;
  private static int choice=0;
  private Boolean paused=true;
  Store[] store = new Store[48];
  public static int clickX;
  public static int clickY;
  public static int choiceCounter;
  int[] locX = new int[33];
  int[] locY = new int[33];
  int[] locsize = new int[33];
  int[][] clickGrid = new int[23][13];
  Store[] choicelist = new Store[9];
  public static int daylength;
  public static int daymod;
  private String who;
  private double multiplier;
  private Boolean visitor;
  
  
  public double getCash()
  {
    return cash;
  }
  
  public MallKing()
  {
    addMouseListener(new MouseAdapter() { 
      public void mousePressed(MouseEvent me) 
      {
        mouseX=me.getX();
        mouseY=me.getY();
        clickX=mouseX/60+1;
        clickY=mouseY/60+1;
        //System.out.println(clickX+":"+clickY);
        System.out.println(clickGrid[clickX][clickY]);
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
          exit=true;
          saveGrid("gridTemp.txt");
          loadgrid("gridConfirm.txt");
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
          saveGrid("gridTemp.txt");
          loadgrid("gridPicker.txt");
          choices(0);
          choiceCounter=0;
          choices(choiceCounter);
          changeStore=true;
          settings=false;
        }
        else if(clickGrid[clickX][clickY]==813&&storeMenu==true&&changeStore==false&&store[menu].isBurned==false)
        {
          cash-=store[menu].promotion(cash);
        } 
        else if(clickGrid[clickX][clickY]==813&&storeMenu==true&&changeStore==false&&store[menu].isBurned==true)
        {
          //REPAIRS
          if(cash<(store[menu].cost*.1))
          {
            saveGrid("gridTemp.txt");
            loadgrid("gridOverpriced.txt");
            overpricedrep=true;
          }
          else
          {
            repair=true;
            saveGrid("gridTemp.txt");
            loadgrid("gridConfirm.txt");
          }
        }
        else if(clickGrid[clickX][clickY]==818&&storeMenu==true&&changeStore==false&&store[menu].isBurned==false)
        {
          remove=true;
          saveGrid("gridTemp.txt");
          loadgrid("gridConfirm.txt");
        }        
        else if(changeStore==true&&clickGrid[clickX][clickY]==817)
        {
          loadgrid("gridTemp.txt");
          changeStore=false;
        }
        else if(clickGrid[clickX][clickY]>900&&clickGrid[clickX][clickY]<910)
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
        else if(clickGrid[clickX][clickY]==910&&choiceCounter>0)
        {
          choices(choiceCounter-9);
        }
        else if(clickGrid[clickX][clickY]==911&&choiceCounter<=48)
        {
          choices(choiceCounter+9);
        }
        else if(clickGrid[clickX][clickY]==1001&&confirm==true)
        {
          if(menu<101)
          {
            remove();
          }
          choicelist[choice].location=menu-101;
          choicelist[choice].isPlaced=true;
          choicelist[choice].x=locX[menu-101];
          choicelist[choice].y=locY[menu-101];
          cash-=choicelist[choice].cost;
          confirm=false;
          changeStore=false;
          storeMenu=false;
          loadgrid("gridTemp.txt");
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
        }
        else if(clickGrid[clickX][clickY]==1001&&remove==true)
        {
          remove=false;
          loadgrid("gridTemp.txt");
          remove();
        }        
        else if(clickGrid[clickX][clickY]==1001&&exit==true)
        {
          loadgrid("gridClear.txt");
          playGame=false;
          loadGame=false;
          newGame=false;
          exit=false;
        }
        else if(clickGrid[clickX][clickY]==1001&&repair==true)
        {
          loadgrid("gridTemp.txt");
          cash-=store[menu].cost*.1;
          store[menu].isBurned=false;
          repair=false;
        }
        else if(clickGrid[clickX][clickY]==1002&&confirm==true)
        {
          loadgrid("gridPicker.txt");
          confirm=false;
        }
        else if(clickGrid[clickX][clickY]==1002&&remove==true)
        {
          loadgrid("gridTemp.txt");
          remove=false;
        }        
        else if(clickGrid[clickX][clickY]==1002&&exit==true)
        {
          loadgrid("gridTemp.txt");
          exit=false;
        }
        else if(clickGrid[clickX][clickY]==1002&&repair==true)
        {
          loadgrid("gridTemp.txt");
          repair=false;
        }
        else if(clickGrid[clickX][clickY]==1100&&changeStore==true)
        {
          loadgrid("gridPicker.txt");
          overpriced=false;
        }        
        else if(clickGrid[clickX][clickY]==1100&&overpricedrep==true)
        {
          loadgrid("gridTemp.txt");
          overpricedrep=false;
        }
      }       
    }); 
  }
  public void choices(int position)
  {
    choiceCounter=position;
    if (choiceCounter<0)
    {
      choiceCounter=0;
    }
    int counter = 0;
    for(int i=choiceCounter;i<48; i++)
    {
      if(store[i].isPlaced==false&&counter<9)
      {
        choicelist[counter]=store[i];
        counter++;
        choiceCounter=i-8;
        System.out.println(choiceCounter);
      }
      if(i==47&&counter<9)
      {
        for (int b=counter;b<9;b++)
        {
          choicelist[b]=null;
        }
      }
    }
    
  }
  public void remove()
  {
    for (int a=1;a<23;a++)
    {
      for (int b=1;b<13;b++)
      {
        if(clickGrid[a][b]==store[menu].counter)
        {
          clickGrid[a][b]=store[menu].location+101;
        }
      }
    }  
    storeMenu=false;
    store[menu].isPlaced=false;
    menu=store[menu].location+101;
    store[menu].location=0;
    store[menu].locsize=0;
    store[menu].timeRun=0;
    choicelist[choice].x=0;
    choicelist[choice].y=0;
    choicelist[choice].y=0;
  }
  public void loading(String file, String stores, String grid)
  {
    try { 
      FileReader fr = new FileReader(file); 
      BufferedReader br = new BufferedReader(fr); 
      cash = Integer.parseInt(br.readLine());
      year = Integer.parseInt(br.readLine());
      month = Integer.parseInt(br.readLine());
      day  = Integer.parseInt(br.readLine());
      daymod = Integer.parseInt(br.readLine());
      daylength  = Integer.parseInt(br.readLine());
      br.close(); 
    } catch(IOException e){}
    
    try { 
      FileReader fr = new FileReader(stores); 
      BufferedReader br = new BufferedReader(fr); 
      for(int i=0;i<48; i++)
      {
        String name=br.readLine();
        int size = Integer.parseInt(br.readLine());
        int stars = Integer.parseInt(br.readLine());
        int profitability = Integer.parseInt(br.readLine());
        int cost = Integer.parseInt(br.readLine());
        String placed=br.readLine();
        String str="true";
        boolean isPlaced = (str.compareTo(placed)==0);
        int x = Integer.parseInt(br.readLine());
        int y = Integer.parseInt(br.readLine());
        int locsize = Integer.parseInt(br.readLine());
        int location = Integer.parseInt(br.readLine());
        String burned=br.readLine();
        str="true";
        boolean isBurned = (str.compareTo(burned)==0);
        store[i]=(new Store(name, size, stars, profitability, cost, i, isPlaced, x, y, locsize, location, isBurned));
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
    loadgrid(grid);
    loadGame=false;
    playGame=true;  
    newGame=false;   
    storeMenu=false;
    paused=true;
    changeStore=false;   
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
      pw.println(daymod);
      pw.println(daylength);
      pw.close(); 
    } catch(IOException e){}
    
    try { 
      FileWriter fw = new FileWriter("saveStores.txt"); 
      PrintWriter pw = new PrintWriter(fw);
      for(int i=0;i<48; i++)
      {
        pw.println(store[i].name);
        pw.println(store[i].size);
        pw.println(store[i].stars);
        pw.println(store[i].profitability);
        pw.println(store[i].cost);
        pw.println(store[i].isPlaced);
        pw.println(store[i].x);
        pw.println(store[i].y);
        pw.println(store[i].locsize);
        pw.println(store[i].location);
        pw.println(store[i].isBurned);
      }
      pw.close(); 
    } catch(IOException e){}
    
    saveGrid("gridSave.txt");
    
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
  public void saveGrid(String file)
  {
    try { 
      FileWriter fw = new FileWriter(file); 
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
  public void newGame(int cash, int day, int month, int year)
  {
    calendar(year, month, day);
  } 
  public void calendar(int year, int month, int day)
  {
    if(!paused)
    {
      for(int i=0;i<48; i++)
      {
        if(store[i].getIsPlaced())
        {
          store[i].calculateExpenses();
        }
      }
      if(day>=daylength)
      {
        this.month++;
        int r=(int)(Math.random()*0);
        if(r==0)
        {
          visitor=true;
          r=(int)(Math.random()*160);//160
          if(r<80)
          {
            r=(int)(Math.random()*16);
            if(r%2!=0)
            {
              r--;
            }
            try {
              FileReader fr = new FileReader("Celebrities.txt"); 
              BufferedReader br = new BufferedReader(fr); 
              for(int i=0;i<r/2;i++)
              {
                who=br.readLine();
                multiplier = Double.parseDouble(br.readLine())/100;//divide by 100
              }
              System.out.println(who+multiplier);
              br.close(); 
            }
            catch(IOException e){}
          }
          else if(r>79&&r<120)
          {
            r=(int)(Math.random()*10);
            if(r%2!=0)
            {
              r--;
            }            
            try {
              FileReader fr = new FileReader("Celebrities2.txt"); 
              BufferedReader br = new BufferedReader(fr); 
              for(int i=0;i<r/2;i++)
              {
                who=br.readLine();
                multiplier = Double.parseDouble(br.readLine())/100;//divide by 100
              }
              System.out.println(who+multiplier);
              br.close(); 
            }
            catch(IOException e){}
          }
          else if(r>119&&r<140)
          {
            try {
              FileReader fr = new FileReader("Celebrities3.txt"); 
              BufferedReader br = new BufferedReader(fr); 
              who=br.readLine();
              multiplier = Double.parseDouble(br.readLine())/100;//divide by 100
              System.out.println(who+multiplier);
              br.close(); 
            }
            catch(IOException e){}
          }
          else
          {
            try {
              FileReader fr = new FileReader("Celebrities4.txt"); 
              BufferedReader br = new BufferedReader(fr); 
              who=br.readLine();
              multiplier = Double.parseDouble(br.readLine())/100;//divide by 100
              System.out.println(who+multiplier);
              br.close(); 
            }
            catch(IOException e){}
          }
          if(who==null)
          {
            JFrame frame = new JFrame("Visitor");
            JOptionPane.showMessageDialog(frame,"The dreaded Null has visited your mall, making all profit negative from all stores!!!");
            multiplier=-1;
          }
          else
          {
            JFrame frame = new JFrame("Celebrity Visitor!");
            JOptionPane.showMessageDialog(frame,who+" has visited your mall, and shopping has been boosted "+multiplier+" times this month!");
          }
        }
        for(int i=0;i<48; i++)
        {
          if(store[i].getIsPlaced())
          {
            if(visitor==true)
            {
              double income=store[i].calculateRevenue()*multiplier+store[i].calculateExpenses();
                cash+=income;
              store[i].money=income;
              visitor=false;
            }
            else
            {
              double income=store[i].calculateRevenue()+store[i].calculateExpenses();
              cash+=income;
              store[i].money=income;
            }
          }
        }
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
      
      for (int a=0;a<48;a++)
      {
        if (store[a].isPlaced==true)
        {
          String location="";
          if (locsize[store[a].location]==1)
          {
            location=(store[a].counter+1)+"s.png";
          }
          else
          {
            location=(store[a].counter+1)+".png";
          }
          try
          {
            img = ImageIO.read(new File(location));
          } catch (IOException e){}
          g.drawImage(img, store[a].x, store[a].y, null);
          if (store[a].isBurned==true)
          {
            String floc="";
            if (locsize[store[a].location]==1)
            {
              floc="fires.png";
            }          
            else
            {
              floc="fire.png";
            }
            try
            {
              img = ImageIO.read(new File(floc));
            } catch (IOException e){}          
            g.drawImage(img, store[a].x, store[a].y-15, null);
          }
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
          g.drawString(String.valueOf(store[menu].money),65,220);
          g.drawString("Replace Store",64,285);
          try
          {
            img = ImageIO.read(new File("Remove.png"));
            g.drawImage(img, 0, 0, null);
            if(store[menu].isBurned==false)
            {
              img = ImageIO.read(new File("Promotions.png"));
              g.drawImage(img, 0, 0, null);
            }
            else
            {
              img = ImageIO.read(new File("Repair.png"));
              g.drawImage(img, 0, 0, null);
            }
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
            if(choicelist[i]!=null)
            {
              g.drawString(choicelist[i].name,128, i*60+166);
              g.drawString(String.valueOf(choicelist[i].cost),400, i*60+166);
            }
          }
          if(choiceCounter>2)
          {
            img = ImageIO.read(new File("cUp.png"));
            g.drawImage(img, 0, 0, null);
          }
          if(choiceCounter<=38)
          {
            img = ImageIO.read(new File("cDown.png"));
            g.drawImage(img, 0, 0, null);
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
      if (overpricedrep==true)
      { 
        try
        {
          img = ImageIO.read(new File("Overpricedrep.png"));
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
        g.drawString(String.valueOf(store[menu].name),480,400);
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
      if (repair==true)
      { 
        try
        {
          img = ImageIO.read(new File("ConfirmRepair.png"));
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
        g.drawString(String.valueOf(store[menu].name),480,400);
      }
      if (remove==true)
      { 
        try
        {
          img = ImageIO.read(new File("ConfirmRM.png"));
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
      if (exit==true)
      { 
        try
        {
          img = ImageIO.read(new File("ConfirmExit.png"));
        } catch (IOException e){}
        g.drawImage(img, 0, 0, null);
      }
    }
  }
  public void reset()
  {
    changeStore=false;
    settings=false;
    storeMenu=false;
    confirm=false;
    isMuted=false;
  }
  public void cheats()
  {
    JFrame frame = new JFrame("Cheat Code");
    String code = JOptionPane.showInputDialog(frame, "Please Enter the Cheat Code");
  }
  //music
  //http://alvinalexander.com/java/java-audio-example-java-au-play-sound
//  public static void startBGMusic()
//  {
//    AudioPlayer myBackgroundPlayer = AudioPlayer.player;
//    ContinuousAudioDataStream myLoop = null;
//    try
//    {
//      String song="bgmusic.wav";
//      AudioStream myBackgroundMusic = new AudioStream(new FileInputStream(song));
//      AudioData myData = myBackgroundMusic.getData();
//      myLoop = new ContinuousAudioDataStream(myData);
//    }
//    catch(Exception error)
//    {}
//    myBackgroundPlayer.start(myLoop);
//  }
  //music
  public static void main(String[]args) throws InterruptedException
  { 
    JFrame frame = new JFrame("Mall King");   
    MallKing m = new MallKing();
    frame.add(m); 
    frame.setSize(1280, 760);
    frame.setVisible(true); 
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //music 
//    startBGMusic();
    //music
    while (true) {
      {
        if(newGame)
        {
          m.loading("New.txt", "storeList.txt", "gridLoad.txt");
        }
        else if(playGame)
        {
          m.newGame(cash,day,month,year);
        }
        else if(loadGame)
        {
          m.loading("save.txt", "saveStores.txt", "gridSave.txt");
        }
        m.repaint(); 
        Thread.sleep(10); 
      }
    }  
  }
}