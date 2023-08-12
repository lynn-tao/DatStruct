public class Test
{
   public static void main(String args[])
   {
      System.out.println(oopPair("hOOPhellolynnOOP"));
   }
   public static String reverse(String s)
   {
      String rev = "";
      for(int i=s.length(); i>0; i--)
      {
         rev  += s.substring(i-1,i);
      }
      return rev;
   }
   public static String oopPair(String string) {
      int start = string.indexOf("OOP");
      int end = string.lastIndexOf("OOP");
      return string.substring(start+1, end);
   }
   public void printNames(String string) {
      int i = 0;
      while (true) {
         int found = string.indexOf("name:", i);
         if (found == -1) 
            break;
         int start = found + 5; // start of actual name
         int end = string.indexOf(":", start);
         System.out.println(string.substring(start, end));
         i = end + 1;  // advance i to start the next iteration
      }
   }

}