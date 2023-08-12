// Name: J1-26
// Date: 9/24/20
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
//here any additional imports that you may need

public class Cemetery
{
   public static void main (String [] args)
   {
      File file = new File("cemetery_short.txt");
      //File file = new File("cemetery.txt");
      int numEntries = countEntries(file);
      
      Person[] cemetery = readIntoArray(file, numEntries); 
      //see what you have
      for (int i = 0; i < cemetery.length; i++) 
         System.out.println(cemetery[i]);
         
      int min = locateMinAgePerson(cemetery);
      int max = locateMaxAgePerson(cemetery); 
      System.out.println("\nIn the St. Mary Magdelene Old Fish Cemetery --> ");
      System.out.println("Name of youngest person: " + cemetery[min].getName());
      System.out.println("Age of youngest person: " + cemetery[min].getAge());    
      System.out.println("Name of oldest person: " + cemetery[max].getName());
      System.out.println("Age of oldest person: " + cemetery[max].getAge()); 
      //you may create other testing cases here
      //comment them out when you submit your file to Grade-It    
   }
   
   /* Counts and returns the number of entries in File f. 
      Returns 0 if the File f is not valid.
      Uses a try-catch block.   
      @param f -- the file object
   */
   public static int countEntries(File f)
   {
      try
      {
         int entries=0;
         Scanner sc = new Scanner(f);
         
         while(sc.hasNextLine())
         {
            sc.nextLine();
            entries++;
         }
         return entries;
      }
      catch(FileNotFoundException e)
      {
         return 0;
      }
     
   }

   /* Reads the data from file f (you may assume each line has same allignment).
      Fills the array with Person objects. If File f is not valid return null.
      @param f -- the file object 
      @param num -- the number of lines in the File f  
   */
   public static Person[] readIntoArray (File f, int num)
   {      
      try
      {
         Scanner sc = new Scanner(f);
         Person[] deadpeople = new Person[num];
         for(int i=0; i<num; i++)
         {
            String person = sc.nextLine();
            deadpeople[i] = makeObjects(person);
         }
         return deadpeople;
         
      }
      catch(FileNotFoundException e)
      {
         return null;
      }
   }
   
   /* A helper method that instantiates one Person object.
      @param entry -- one line of the input file.
      This method is made public for gradeit testing purposes.
      This method should not be used in any other class!!!
   */
   public static Person makeObjects(String entry)
   {
      String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ";
      String numbers = "1234567890";
     
      /* 
      String name = "";
      name=entry.substring(0, 24);
       
      String burial = "";
      burial = entry.substring(25, 36);
      
      String age = "";
      age = entry.substring(37, 41);*/
      
      String name = "";
      int index=0;
      while(!numbers.contains(entry.substring(index, index+1)) && !entry.substring(index, index+2).equals("xx"))
      {
         index++;
      }
      name = entry.substring(0,index).trim();
      
      String burial = "";
      burial = entry.substring(index, index+11);
      
      int agestart = index+11;
      String age = "";
      age= entry.substring(agestart, agestart+5).trim();
        
      Person p = new Person(name, burial, age);
      return p;
   }  
   
   /* Finds and returns the location (the index) of the Person
      who is the youngest. (if the array is empty it returns -1)
      If there is a tie the lowest index is returned.
      @param arr -- an array of Person objects.
   */
   public static int locateMinAgePerson(Person[] arr)
   {
      double min = arr[0].getAge();
      for(int i=0; i<arr.length;i++)
      {
         min = Math.min(min, arr[i].getAge());
      }
      for(int i=0; i<arr.length;i++)
      {
         if(arr[i].getAge() == min)
         {
            return i;
         }
      }
      return -1;
   }   
   
   /* Finds and returns the location (the index) of the Person
      who is the oldest. (if the array is empty it returns -1)
      If there is a tie the lowest index is returned.
      @param arr -- an array of Person objects.
   */
   public static int locateMaxAgePerson(Person[] arr)
   {
      double max = arr[0].getAge();
      for(int i=0; i<arr.length;i++)
      {
         max = Math.max(max, arr[i].getAge());
      }
      for(int i=0; i<arr.length;i++)
      {
         if(arr[i].getAge() == max)
         {
            return i;
         }
      }
      return -1;
   }        
} 

class Person
{
   //constant that can be used for formatting purposes
   private static final DecimalFormat df = new DecimalFormat("0.0000");
   /* private fields */
   private String identity;
   private String bury;
   private String deathAge;
      
   /* a three-arg constructor  
    @param name, burialDate may have leading or trailing spaces
    It creates a valid Person object in which each field has the leading and trailing
    spaces eliminated*/
   public Person(String name, String burialDate, String age)
   {
      identity = name.trim();
      bury = burialDate.trim();
      deathAge = age;
   }
   /* any necessary accessor methods (at least "double getAge()" and "String getName()" )
   make sure your get and/or set methods use the same data type as the field  */
   
   public double getAge()
   {
      return calculateAge(deathAge);
   }
   public String getName()
   {
      return identity;
   }
   public String getDate()
   {
      return bury;
   }
   
   /*handles the inconsistencies regarding age
     @param a = a string containing an age from file. Ex: "12", "12w", "12d"
     returns the age transformed into year with 4 decimals rounding
   */
   public double calculateAge(String a)
   {
      double x=0;
      String s ="";
      for(int i=0; i<a.length(); i++)
      {
         if(a.substring(i,i+1).equals("d"))
         {
            x = Double.parseDouble(a.substring(0, i))/365.0; 
            return Double.parseDouble(df.format(x));
         }
         else if(a.substring(i,i+1).equals("w"))
         {
            x = Double.parseDouble(a.substring(0, i))/52.0; 
            return Double.parseDouble(df.format(x));
         }
         else
         {
            s += a.substring(i, i+1);
            x = Double.parseDouble(s);
         }
      }
      return x;
   }
   public String toString()//overriding
   {
      return identity + ", " +  bury + ",  " + getAge();
   
   }
}

/******************************************

 John William ALLARDYCE, 17 Mar 1844, 2.9
 Frederic Alex. ALLARDYCE, 21 Apr 1844, 0.17
 Philip AMIS, 03 Aug 1848, 1.0
 Thomas ANDERSON, 06 Jul 1845, 27.0
 Edward ANGEL, 20 Nov 1842, 22.0
 Lucy Ann COLEBACK, 23 Jul 1843, 0.2685
 Thomas William COLLEY, 08 Aug 1833, 0.011
 Joseph COLLIER, 03 Apr 1831, 58.0
 
 In the St. Mary Magdelene Old Fish Cemetery --> 
 Name of youngest person: Thomas William COLLEY
 Age of youngest person: 0.011
 Name of oldest person: Joseph COLLIER
 Age of oldest person: 58.0
 
 **************************************/