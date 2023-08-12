//Name: j126       
//Date: 1/19
import java.util.*;

public class AssemblyLine_Driver
{
   static int NDISKS = 50;
   static int MAXRADIUS = 100;
   public static void main(String[] args)
   {
      AssemblyLine a = new AssemblyLine(NDISKS, MAXRADIUS);
      a.showInput();
      a.process();
      a.showOutput();
   }
}
   
class AssemblyLine
{
   private Queue<Disk> assemblyLineIn;
   private Queue<Pyramid> assemblyLineOut;
   private Pyramid robotArm;
   	/**
   		* initializes this object so the assemblyLineIn contains 
   		* nDisks with random radii;  assemblyLineOut is initialized to * an empty Queue; robotArm is initialized to an empty Pyramid.
   		**/
   	//Part A
   public AssemblyLine( int nDisks, int maxRadius )
   {	
      assemblyLineIn = new LinkedList<Disk>();
      while(nDisks>0)
      {
         int x = (int)(Math.random()*20)+1;
         Disk di = new Disk(x);
         assemblyLineIn.add(di);
         nDisks--;
      }
      assemblyLineOut = new LinkedList<Pyramid>();
      robotArm = new Pyramid();
   }
   
   	/**
   		* "flips over" the pyramid in the robotArm and adds it to the
   		* assemblyLineOut queue.
   		* Precondition:  robotArm is not empty and holds an inverted 
   		*				pyramid of disks
   		**/
   	// Part B
   private void unloadRobot()
   { 
      Pyramid robot = new Pyramid();
      while(!robotArm.isEmpty())
         robot.push(robotArm.pop());
      Queue<Disk> cop = new LinkedList<Disk>();
      while(!robot.isEmpty())
         cop.add(robot.pop());
      while(!cop.isEmpty())
         robot.push(cop.remove());
   
      
      assemblyLineOut.add(robot); 
   }
   
   	/**
   		* processes all disks from assemblyLineIn; a disk is processed
   		* as follows:  if robotArm is not empty and the next disk does
   		* not fit on top of robotArm (which must be an inverted 
   		* pyramid) then robotArm is unloaded first; the disk from
   		* assemblyLineIn is added to robotArm; when all the disks
   		* have been retrieved from assemblyLineIn, robotArm is unloaded.
   		*  Precondition:  robotArm is empty;
   		*				assemblyLineOut is empty
   		**/
   	//Part C
   public void process()
   {
      while(!assemblyLineIn.isEmpty())
      {
         if(robotArm.isEmpty())
            robotArm.add(assemblyLineIn.remove());
         
         else if(assemblyLineIn.peek().compareTo(robotArm.peek()) <= 0)
         {
            unloadRobot();
            robotArm.add(assemblyLineIn.remove());
         }
         else
            robotArm.add(assemblyLineIn.remove());
      }
      unloadRobot();
   }
      
   public void showInput()
   {
      System.out.println(assemblyLineIn);
   }
   public void showOutput()
   {
      System.out.println(assemblyLineOut);
   }
}
   //Disk is standard and straightforward.
class Disk implements Comparable<Disk>
{
   private int radius;
   public Disk(int d)
   {
      radius = d;
   }
   public int getRadius()
   {
      return radius;
   }
   public int compareTo(Disk d)
   {
      if(radius>d.getRadius())
         return 1;
      if(radius==d.getRadius())
         return 0;
      else
         return -1;
   }
   public String toString()
   {
      return ""+radius;
   }
}
   //Pyramid is sly.
class Pyramid extends Stack<Disk>
{
   public Pyramid()
   {
      super();
   }
   public String toString()
   {
      Queue<Integer> cop = new LinkedList<Integer>();
      while(!isEmpty())
      {
         cop.add(pop().getRadius());
      }
      return cop.toString();
   }
}