// Name: j126   Date:  1/27

import java.util.*;
import java.io.*;

public class LunchRoom
{
   public static final int TIME = 1080;  //18 hrs * 60 min
   public static double CHANCE_OF_CUSTOMER = .2;
   public static PrintWriter outfile = null; //file to write your results to
      
   public static int timeToOrderAndBeServed()
   {
      return (int)(Math.random() * 6 + 2);
   }
  
   public static void displayTimeAndQueue(Queue<Customer> q, int min)
   { 
      outfile.println(min + ": " + q);	
      //outfile.println(min + ": " + q);		
   }  
   
   static class Customer implements Comparable<Customer>
   {
    /*****************
       Complete the Customer class.  
       Fields, constructor, accessor methods, 
       compareTo, toString.
    ******************/ 
      private int time;
      private int serve;
      private String grade;
      public Customer(int t, int order, String id)
      {
         time = t;
         serve = order;
         grade = id;
      }
      public int getArrivedAt()
      {
         return time;
      }
      public int getOrder()
      {
         return serve;
      }
      public void setOrder(int o)
      {
         serve = o;
      }
      public String getGrade()
      {
         return grade;
      }
      public void setGrade(String s)
      {
         grade = s;
      }
      public String toString()
      {
         if(grade.equals("D"))
            return "" + time + ": Fr";
         if(grade.equals("C"))
            return "" + time + ": So";
         if(grade.equals("B"))
            return "" + time + ": Ju";
         else
            return "" + time + ": Se";
      
      }
      public int compareTo(Customer c)
      {
         if(grade.compareTo(c.getGrade())==0)
         {
            if(time < c.getArrivedAt())
               return -1;
            else if(time > c.getArrivedAt())
               return 1;
            else
               return 0;
         }     
         else 
            return grade.compareTo(c.getGrade());   
      
      }
   
       
   }
       
   public static void main(String[] args)
   {
    //set up file      
      try
      {
         outfile = new PrintWriter(new FileWriter("LunchRoom Seniors first.txt"));
      }
      catch(IOException e)
      {
         outfile.println("File not created");
         System.exit(0);
      }
      LunchRoom();
      outfile.close();
   }
   
   public static void LunchRoom()
   {
      PriorityQueue<Customer> queue = new PriorityQueue<Customer>();
      /*  write code for the simulation   */
      outfile.println("LunchRoom(arrival chance is 0.2) with 1 Pqueue / 1 service areas");
      Queue<Customer> service = new LinkedList<Customer>(); 
      //count each type of student
      Queue<Integer> sen = new LinkedList<Integer>(); Queue<Integer> jun = new LinkedList<Integer>(); Queue<Integer> soph = new LinkedList<Integer>(); Queue<Integer> fresh = new LinkedList<Integer>();
   
      for(int i=0; i<=TIME; i++)
      {
         // if customer comes
         if(Math.random()<0.2)
         {
            //instantiate new customer
            Customer c = new Customer(i, timeToOrderAndBeServed(), null);
            int x  = (int)(Math.random()*4)+1;
            if(x==1)
               c.setGrade("A");            
            else if(x==2)
               c.setGrade("B");               
            else if(x==3)
               c.setGrade("C");               
            else
               c.setGrade("D");               
            outfile.println("\n new customer " + c.toString());
            queue.add(c);
                              
            //if service area is empty
            if(service.isEmpty())
            {
               Customer first = queue.remove();
               service.add(first);
               displayTimeAndQueue(queue, i);
               outfile.println("   ServiceArea#1  "+ service.peek().getArrivedAt()+":"+service.peek().getOrder());
            }
            else 
            {
               Customer placeholder = service.remove();
               //if customer leaves service area
               if(placeholder.getOrder()==1)
               {
                  displayTimeAndQueue(queue, i);
                  outfile.println("    Customer# " + placeholder.toString() + " leaves and his total wait time is " + (i - placeholder.getArrivedAt()));
                  
                  if(c.getGrade().equals("A"))
                     sen.add(i - placeholder.getArrivedAt());
                  if(c.getGrade().equals("B"))
                     jun.add(i - placeholder.getArrivedAt());
                  if(c.getGrade().equals("C"))
                     soph.add(i - placeholder.getArrivedAt());
                  if(c.getGrade().equals("D"))
                     fresh.add(i - placeholder.getArrivedAt());    
               
                  if(!service.isEmpty())
                     service.remove();
                  if(!queue.isEmpty())
                     service.add(queue.remove());
               }
               //decrease minutes of wait time by 1
               else
               {
                  placeholder.setOrder(placeholder.getOrder()-1);
                  service.add(placeholder);
                  displayTimeAndQueue(queue, i);
                  outfile.println("   ServiceArea#1  "+ service.peek().getArrivedAt()+":"+service.peek().getOrder());
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
                  displayTimeAndQueue(queue, i);
                  outfile.println("    Customer# " + placeholder.toString() + " leaves and his total wait time is " + (i - placeholder.getArrivedAt()));
                  if(placeholder.getGrade().equals("A"))
                     sen.add(i - placeholder.getArrivedAt());
                  if(placeholder.getGrade().equals("B"))
                     jun.add(i - placeholder.getArrivedAt());
                  if(placeholder.getGrade().equals("C"))
                     soph.add(i - placeholder.getArrivedAt());
                  if(placeholder.getGrade().equals("D"))
                     fresh.add(i - placeholder.getArrivedAt());    
               
                  if(!queue.isEmpty())
                     service.add(queue.remove());
               }
               else
               {
                  placeholder.setOrder(placeholder.getOrder()-1);
                  service.add(placeholder);
                  displayTimeAndQueue(queue, i);
                  outfile.println("   ServiceArea#1  "+ service.peek().getArrivedAt()+":"+service.peek().getOrder());
               }
            }
            else
            {
               displayTimeAndQueue(queue, i);
               outfile.println("   ServiceArea#1  -1:-1");
            }
         }
      }
      
      
      int i = 1080;
      while(!queue.isEmpty() || !service.isEmpty())
      {        
         i++;
         if(!service.isEmpty())
         {
            Customer placeholder = service.remove();
            if(placeholder.getOrder()==1)
            {
               displayTimeAndQueue(queue, i);
               outfile.println("    Customer# " + placeholder.getArrivedAt() + " leaves and his total wait time is " + (i - placeholder.getArrivedAt()));
            
               if(placeholder.getGrade().equals("A"))
                  sen.add(i - placeholder.getArrivedAt());
               if(placeholder.getGrade().equals("B"))
                  jun.add(i - placeholder.getArrivedAt());
               if(placeholder.getGrade().equals("C"))
                  soph.add(i - placeholder.getArrivedAt());
               if(placeholder.getGrade().equals("D"))
                  fresh.add(i - placeholder.getArrivedAt());  
                     
               if(!queue.isEmpty())
                  service.add(queue.remove());
            }
            else
            {
               placeholder.setOrder(placeholder.getOrder()-1);
               service.add(placeholder);
               displayTimeAndQueue(queue, i);
               outfile.println("   ServiceArea#1  "+ service.peek().getArrivedAt()+":"+service.peek().getOrder());
            }
         }
         else
         {
            if(!queue.isEmpty())
               service.add(queue.remove());
            displayTimeAndQueue(queue, i);
         } 
      }
      
      //calculate statistics
      int time = 0;
      int longestWaitTime = 0;
      int sensize = sen.size();
      while(!sen.isEmpty())
      {
         int x = sen.remove();
         time+=x;
         longestWaitTime = Math.max(longestWaitTime, x);
      }
      
      int t = 0;
      int l = 0;
      int jsize = jun.size();
      while(!jun.isEmpty())
      {
         int x = jun.remove();
         t += x;
         l = Math.max(l, x);
      }
      
      int ti = 0;
      int lo = 0;
      int size = soph.size();
      while(!soph.isEmpty())
      {
         int x = soph.remove();
         ti+=x;
         lo = Math.max(lo, x);
      }
      
      int tt = 0;
      int ll = 0;
      int fsize = fresh.size();
      while(!fresh.isEmpty())
      {
         int x = fresh.remove();
         tt +=x;
         ll = Math.max(ll, x);
      }
      
   
      String[] classes = new String[4];
      classes[0] = "Senior";
      classes[1] = "Junior";
      classes[2] = "Sophomore";
      classes[3] = "Freshman";
      
      int[] served = new int[4];
      served[0] = sensize;
      served[1] = jsize;
      served[2] = size;
      served[3] = fsize;
   
      int[] lWait = new int[4];
      lWait[0] = longestWaitTime;
      lWait[1] = l;
      lWait[2] = lo;
      lWait[3] = ll;
   
      double[] aWait = new double[4];
      aWait[0] = time;
      aWait[1] = t;
      aWait[2] = ti;
      aWait[3] = tt;
   
     
      /*  report the results   */  
      System.out.println("Customer\t\tTotal\t\tLongest\t\tAverage Wait");
      outfile.println("Customer\t\tTotal\t\tLongest\t\tAverage Wait");
      for(int x = 0; x < 4; x++)
      {
         System.out.println(classes[x] + "\t\t\t" + served[x] + "\t\t\t" + lWait[x] + "\t\t\t" + ((double)aWait[x]/served[x]));
         outfile.println(classes[x] + "\t\t\t" + served[x] + "\t\t\t" + lWait[x] + "\t\t\t" + ((double)aWait[x]/served[x]));
      }
   }
}