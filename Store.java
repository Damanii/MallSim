public class Store
{
  private int size;
  private int stars;
  private int profitability;
  private int cost;
  public String name;
  public Store()
  {
  }
  public Store(String Name, int size, int stars, int profitability, int cost)
  {
    this.size=size;
    this.stars=stars;
    this.profitability=profitability;
    this.cost=cost;
    this.name=Name;
    
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
    return 0;
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
    return 0;
  }
}