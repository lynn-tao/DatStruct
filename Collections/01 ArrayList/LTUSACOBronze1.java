import java.util.*;
import java.io.*;

public class LTUSACOBronze1
{
   public static void main(String[] args) 
   {
      //scan and fill array with 7 integers
      Scanner s = new Scanner(System.in);
      //Object num = s.next();
      List arr = new ArrayList();
      for(int i=0; i<7; i++)
         arr.add(s.nextLong());
      
      Collections.sort(arr);
      long A = (long)arr.get(0);
      long B= (long)arr.get(1);
      long C = (long)arr.get(6)-A-B;
      System.out.println(A + " " + B +" "+C);
          
   }
}