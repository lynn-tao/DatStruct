//Updated on 12.14.2020 v2

//Name: j126   Date: 1/13
import java.util.*;
import java.io.*;
public class McRonald
{
   public static final int TIME = 1080;     //18 hrs * 60 min
   public static double CHANCE_OF_CUSTOMER = .2;
   public static int customers = 0;
   public static int totalMinutes = 0;
   public static int longestWaitTime = 0;
   public static int longestQueue = 0;
   public static int serviceWindow = 0;      // to serve the front of the queue
   public static int thisCustomersTime;
   public static PrintWriter outfile = null; // file to display the queue information
      
   public static int timeToOrderAndBeServed()
   {
      return (int)(Math.random() * 6 + 2);
   }
  
   public static void displayTimeAndQueue(Queue<Customer> q, int min)
   { 
      //Billington's
      outfile.println(min + ": " + q);	
      //Jurj's
      //outfile.println("Customer#" + intServiceAreas[i] + " leaves and his total wait time is " + (intMinute - intServiceAreas[i]));                     	
   }
   
   public static int getCustomers()
   {
      return customers;
   }
   public static double calculateAverage()
   {
      return (int)(1.0 * totalMinutes/customers * 10)/10.0;
   }
   public static int getLongestWaitTime()
   {
      return longestWaitTime;
   }
   public static int getLongestQueue()
   {
      return longestQueue;
   }
            
   public static void main(String[] args)
   {     
    //set up file      
      try
      {
         outfile = new PrintWriter(new FileWriter("McRonald 1 Queue 1 ServiceArea.txt"));
      }
      catch(IOException e)
      {
         System.out.println("File not created");
         System.exit(0);
      }
      
      mcRonald(TIME, outfile);   //run the simulation
      
      outfile.close();	
   }
   
   public static void mcRonald(int TIME, PrintWriter of)
   {
      /***************************************
           Write your code for the simulation   
      **********************************/
      of.println("Mcronald(arrival chance is 0.2) with 1 queue / 1 service areas");
      Queue<Customer> que = new LinkedList<Customer>();
      Queue<Customer> service = new LinkedList<Customer>();
      Queue<Integer> wait = new LinkedList<Integer>();
      Queue<Integer> qsize = new LinkedList<Integer>();
   
      for(int i=0; i<=TIME; i++)
      {
         // if customer comes
         if(Math.random()<0.2)
         {
            Customer c = new Customer(i, timeToOrderAndBeServed());
            of.println("\n new customer min# " + i);
            customers++;
            que.add(c);
            qsize.add(que.size());
            //if service area is empty
            if(service.isEmpty())
            {
               Customer first = que.remove();
               service.add(first);
               displayTimeAndQueue(que, i);
               of.println("   ServiceArea#1  "+ service.peek().getArrivedAt()+":"+service.peek().getOrder());
            }
            else 
            {
               Customer placeholder = service.remove();
               //if customer leaves service areaa
               if(placeholder.getOrder()==1)
               {
                  displayTimeAndQueue(que, i);
                  of.println("    Customer# " + placeholder.getArrivedAt() + " leaves and his total wait time is " + (i - placeholder.getArrivedAt()));
                  wait.add(i - placeholder.getArrivedAt());
                  if(!service.isEmpty())
                     service.remove();
                  if(!que.isEmpty())
                     service.add(que.remove());
               }
               //decrease minutes of wait time by 1
               else
               {
                  placeholder.setOrder(placeholder.getOrder()-1);
                  service.add(placeholder);
                  displayTimeAndQueue(que, i);
                  of.println("   ServiceArea#1  "+ service.peek().getArrivedAt()+":"+service.peek().getOrder());
               }
            }
         }
         else  //no new customer
         {
            if(!service.isEmpty())
            {
               Customer placeholder = service.remove();
               if(placeholder.getOrder()==1)
               {
                  displayTimeAndQueue(que, i);
                  of.println("    Customer# " + placeholder.getArrivedAt() + " leaves and his total wait time is " + (i - placeholder.getArrivedAt()));
                  wait.add(i - placeholder.getArrivedAt());
                  if(!que.isEmpty())
                     service.add(que.remove());
               }
               else
               {
                  placeholder.setOrder(placeholder.getOrder()-1);
                  service.add(placeholder);
                  displayTimeAndQueue(que, i);
                  of.println("   ServiceArea#1  "+ service.peek().getArrivedAt()+":"+service.peek().getOrder());
               }
            }
            else
            {
               displayTimeAndQueue(que, i);
               of.println("   ServiceArea#1  -1:-1");
            }
         }
      }
      
      
      int i = 1080;
      while(!que.isEmpty() || !service.isEmpty())
      {        
         i++;
         if(!service.isEmpty())
         {
            Customer placeholder = service.remove();
            if(placeholder.getOrder()==1)
            {
               displayTimeAndQueue(que, i);
               of.println("    Customer# " + placeholder.getArrivedAt() + " leaves and his total wait time is " + (i - placeholder.getArrivedAt()));
               wait.add(i - placeholder.getArrivedAt());
               if(!que.isEmpty())
                  service.add(que.remove());
            }
            else
            {
               placeholder.setOrder(placeholder.getOrder()-1);
               service.add(placeholder);
               displayTimeAndQueue(que, i);
               of.println("   ServiceArea#1  "+ service.peek().getArrivedAt()+":"+service.peek().getOrder());
            }
         }
         else
         {
            if(!que.isEmpty())
               service.add(que.remove());
            displayTimeAndQueue(que, i);
         } 
      }
      
   
       
      while(!wait.isEmpty())
      {
         int time = wait.remove();
         totalMinutes += time;
         longestWaitTime = Math.max(longestWaitTime, time);
      }
      while(!qsize.isEmpty())
      {
         int size = qsize.remove();
         longestQueue = Math.max(longestQueue, size);
      }
           
           
      /*   report the data to the screen    */  
      System.out.println("1 queue, 1 service window, probability of arrival = "+ CHANCE_OF_CUSTOMER);
      System.out.println("Total customers served = " + getCustomers());
      System.out.println("Average wait time = " + calculateAverage());
      System.out.println("Longest wait time = " + longestWaitTime);
      System.out.println("Longest queue = " + longestQueue);
      
   }
   
   static class Customer      
   {
      private int arrivedAt;
      private int orderAndBeServed;
      
    /**********************************
       Complete the Customer class with  
       constructor, accessor methods, toString.
    ***********************************/
      public Customer(int arrive, int order)
      {
         arrivedAt = arrive;
         orderAndBeServed = order;
      }
      public int getArrivedAt()
      {
         return arrivedAt;
      }
      public int getOrder()
      {
         return orderAndBeServed;
      }
      public void setOrder(int o)
      {
         orderAndBeServed = o;
      }
      public String toString()
      {
         return "" + arrivedAt;
      }
   }
}