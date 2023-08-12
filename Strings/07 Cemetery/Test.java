import java.util.Scanner;
import java.io.*;
public class Test
{
   public static void main (String[] args)
   {
      /*String str = "hello lynn you will do fine on the quiz today yes you will";
      String[] done = breakWord(str);
      System.out.println(done.length);
      for(String string : done)
         System.out.print(string + "");*/
      
      /*String s1,s2;
      s1 = "abcd".substring(1,4);
      s2=s1;
      s1="ab";
      System.out.println(s1+ " " +s2);*/
      
      /*String str1 = "abcd".substring(3,3);
      str1 += "yy";
      System.out.println(str1);*/
      
      /*String str = new String("helbobthebobbuilder");
      System.out.println(str.replaceFirst("bob", ""));
      System.out.println(str.replace("bob", ""));
      System.out.println(str.replaceAll("bob", ""));*/
      
      /*while(true)
      {
      System.out.print("Your request, my Liege:  ");
      Scanner sc = new Scanner(System.in);
      String line = sc.nextLine();
      String rev ="";
      for(int i=line.length()-1; i>=0; i--)
      {
         rev+=line.substring(i,i+1);
      }
      System.out.println(rev);
      }*/
      
      try{
         Scanner sc = new Scanner(new File("test.txt"));
         PrintWriter outfile = new PrintWriter(new FileWriter("outfile.txt"));
      
         String z = sc.nextLine();
         System.out.println(z);
         String[] split = z.split(" ");
         int num = 0;
         for(int i=0; i<split.length; i++)
         {
            if(!split[i].equals(" "))
               num++;
         }
         String[] result = new String[num];
         int index=0;
         for(int j=0; j<split.length; j++)
         {
            if(!split[j].equals(" "))
               result[index]=split[j];
            System.out.print(result[index]);
            index++;
         }
        
      
      }
      catch(IOException e)
      {}
      
      
   
      
   }
   public static boolean contains(String str)
   {
      for(int i=0; i<str.length(); i++)
      {
         if(str.substring(i,i+1).equals("x"))
         {
            return true;
         }
      }
      return false;
   }
   public static int count(String str)
   {
      int count=0;
      for(int i=0; i<str.length(); i++)
      {
         if(str.substring(i,i+1).equalsIgnoreCase("x"))
         {
            count++;
         }
      }
      return count;
   }
   public static String[] breakWord(String str)
   {
      String[] breakstring = str.split(" ");      
      return breakstring;
   }
}