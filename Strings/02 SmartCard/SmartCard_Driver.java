// Name: J1-26 
// Date: 09/10/2020
 
import java.text.DecimalFormat;
public class SmartCard_Driver
{
   public static void main(String[] args) 
   {
      Station downtown = new Station("Downtown", 1);
      Station center = new Station("Center City", 1);
      Station uptown = new Station("Uptown", 2);
      Station suburbia = new Station("Suburb", 4);
   
      SmartCard jimmy = new SmartCard(20.00); 
      jimmy.board(center);                    //Boarded at Center City.  SmartCard has $20.00
      jimmy.exit(suburbia);              //From Center City to Suburb costs $2.75.  SmartCard has $17.25
      jimmy.exit(uptown);				//Error:  did not board?!
      System.out.println();
   			
      SmartCard susie = new SmartCard(1.00); 
      susie.board(uptown);            		//Boarded at Uptown.  SmartCard has $1.00
      susie.exit(suburbia);				//Insuffient funds to exit. Please add more money.
      System.out.println();
   
      SmartCard kim = new SmartCard(.25);    
      kim.board(uptown);            		    //Insuffient funds to board. Please add more money.
      System.out.println();
   
      SmartCard b = new SmartCard(10.00);   
      b.board(center);            		    //Boarded at Center City.  SmartCard has $10.00
      b.exit(downtown);					//From Center City to Downtown costs $0.50.  SmartCard has $9.50
      System.out.println();
        
      SmartCard mc = new SmartCard(10.00);  
      mc.board(suburbia);            		    //Boarded at Suburbia.  SmartCard has $10.00
      mc.exit(downtown);					//From Suburbia to Downtown costs $2.75.  SmartCard has $7.25
      System.out.println();
    
      
      /*SmartCard lynn = new SmartCard(20.00); 
      lynn.board(center);                           
      lynn.exit(center);		
      System.out.println();*/
   }
} 	
//Note SmartCard is not denoted as public.  Why?
class SmartCard 
{
   public final static DecimalFormat df = new DecimalFormat("$0.00");
   public final static double MIN_FARE = 0.5;
   
   private double balance;
   private int number;
   private Station station;
   private String name;
   private boolean board;
   
   public SmartCard(double b)
   {
      balance = b;
      number = 0;
      station = null;
      name = "";
      board = false;
   }
   void addMoney(double d)
   {
      balance+=d;
   }
   String getBalance()
   {
      return df.format(balance);
   }
   boolean isBoarded()
   {
      return board;
   }
   void board(Station s)
   {
      if(board==true)
      {
         System.out.println("Error: already boarded?!");
      }
      else if(balance<MIN_FARE)
      {
         station = s; 
         System.out.println("Insufficient funds to board. Please add more money.");
      }
      else
      {
         station = s; 
         System.out.println("Boarded at " + s.getName() +". SmartCard has " + getBalance() + ".");
      } 
      board=true;
   }
   double cost(Station s)
   {
      int a = station.getZone();
      int b = s.getZone();
      double cost = 0;
      if(a==b)
      {
         cost+=0.5;
      }
      else
      {
         cost = 0.5+ Math.abs(a-b)*.75; 
      }
      return cost;
   }
   void exit(Station s)
   { 
      double cost = cost(s);
      if(board==false)
      {
         System.out.println("Error: Did not board?!");
      }
      else if(cost > balance)
      {
         System.out.println("Insufficient funds to exit. Please add more money.");
      }
      else
      {
         balance -= cost;
         number = s.getZone();
         name = s.getName(); 
         System.out.println("From " + station.getName() +" to " + name + " costs " + df.format(cost) + ". Smartcard has "+ getBalance() + ".");
         board = false;
         if(station==s)
         {
            station=null;
         }
      } 
   }
   //the next 3 methods are for use ONLY by Grade-It
   //these accessor methods only return your private data
   //they do not make any changes to your data
   double getMoneyRemaining()
   {
      return balance;
   }
   Station getBoardedAt()
   {
      return station;   
   }
   boolean getIsOnBoard()
   {
      return board;
   }
}
   
//Note Station is not a public class.  Why?
class Station
{
   private String name;
   private int zone;
     
   public Station()
   {
      name = "Not a station.";
      zone = 0;
   }
   public Station(String n, int z)
   {
      name = n;
      zone = z;
   }
   String getName()
   {
      return name;
   } 
   int getZone()
   {
      return zone;
   }
   void setName(String s)
   {
      name = s;
   }
   void setZone(int number)
   {
      zone = number;
   }
}

 
 
 
 
 
 /*******************  Sample Run ************

 Boarded at Center City.  SmartCard has $20.00
 From Center City to Suburb costs $2.75.  SmartCard has $17.25
 Error: did not board?!
 
 Boarded at Uptown.  SmartCard has $1.00
 Insufficient funds to exit. Please add more money.
 
 Insufficient funds to board. Please add more money.
 
 Boarded at Center City.  SmartCard has $10.00
 From Center City to Downtown costs $0.50.  SmartCard has $9.50
 
 Boarded at Suburb.  SmartCard has $10.00
 From Suburb to Downtown costs $2.75.  SmartCard has $7.25
 
 ************************************************/