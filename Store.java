import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Store
{
  public int size;
  public int stars;
  public int profitability;
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
  public int m=1;
  public double money;
  public Store()
  {
  }
  public Store(String name, int stars, int profitability, int cost, int counter, boolean isPlaced, int x, int y, int location, int locsize, boolean isBurned)
  {
    this.size=size;
    this.stars=stars;
    this.profitability=profitability;
    this.cost=cost;
    this.name=name;
    this.isPlaced=isPlaced;
    this.counter=counter;
    this.x=x;
    this.y=y;
    this.location=location;
    this.locsize=locsize;
    this.isBurned=isBurned;
  }
  public boolean getIsPlaced()
  {
    return isPlaced;
  }
  public boolean getIsBurned()
  {
    return isBurned;
  }
  public double calculateRevenue()
  {
    timeRun--;
    if(timeRun>0)
    {
      m=3;
    }
    else
    {
      m=1;
    }
    System.out.println(m);
    int random = (int)(Math.random()*45);
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
      if(!runTime.equals(null))
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
        promotionCost=(cost*0.05*(timeRun));
      }
    }while(promotionCost>cash);
    
    String promo=String.valueOf(promotionCost);
    Object[] options = {"Run Promo","Cancel"};
    int n = JOptionPane.showOptionDialog(frame,"This promotion will cost $"+ promo,"Promotion",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
    if(n==0)
    {
      timeRun++;
      m=3;
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
  public void increaseProfitability()
  {
  }
  
  public double calculateExpenses()
  {
    JFrame frame = new JFrame("Mall");
    int a = (int)(Math.random()*1000000);
    if(a==0&&isBurned==false)
    {
      JOptionPane.showMessageDialog(frame,name+" Has burned down!!\n     No revenue will be made.","OH NOSE",JOptionPane.WARNING_MESSAGE);
      isBurned=true;
    }
    
    int rand = (int)(Math.random()*100000);
    if (rand==0)
    {
      double loss = 0.05*cost;
      JOptionPane.showMessageDialog(frame,"There has been a robbery  in "+name+"!!\n"+loss+" was stolen.","OH NOSE",JOptionPane.WARNING_MESSAGE);
      return loss;
    }
    return 0;
  }
}
