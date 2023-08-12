import java.io.*;
import java.util.*;
public class IteratorLab
{
   public static void main(String[] args) throws IOException
   {
   
      ArrayList<String> x = new ArrayList<String>();
      x.add("a");
      x.add("z");
      x.add("e");
      removeVowels(x);
      System.out.println(x);
    
        
   }
   public static void removeVowels(ArrayList ral) 
   {
      String vowels = "aeiou";
      ListIterator li = ral.listIterator();
      for(String s : ral)
      {
         if(vowels.contains(li.next()))
            li.remove();
      }
   
   }       
}