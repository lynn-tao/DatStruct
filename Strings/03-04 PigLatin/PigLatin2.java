// Name:   
// Date: 
import java.util.*;
import java.io.*;
public class PigLatin2
{
   public static void main(String[] args) 
   {
      part_1_using_pig();
      // part_2_using_piglatenizeFile();
      
      /*  extension only    */
      // String pigLatin = pig("What!?");
      // System.out.print(pigLatin + "\t\t" + pigReverse(pigLatin));   //Yahwta!?
      // pigLatin = pig("{(Hello!)}");
      // System.out.print("\n" + pigLatin + "\t\t" + pigReverse(pigLatin)); //{(Yaholle!)}
      // pigLatin = pig("\"McDonald???\"");
      // System.out.println("\n" + pigLatin + "  " + pigReverse(pigLatin));//"YaDcmdlano???"
   }

   public static void part_1_using_pig()
   {
      Scanner sc = new Scanner(System.in);
      while(true)
      {
         System.out.print("\nWhat word? ");
         String s = sc.next();
         if(s.equals("-1"))
         {
            System.out.println("Goodbye!"); 
            System.exit(0);
         }
         String p = pig(s);
         System.out.println( p );
      }		
   }

   public static final String punct = ",./;:'\"?<>[]{}|`~!@#$%^&*()";
   public static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
   public static final String vowels = "AEIOUaeiou";
   public static String pig(String s)
   {
      if(s.length() == 0)
         return "";
      String b = begin(s);
      String e = end(s); 
      s = s.replace( b, ""); 
      s = s.replace( e, "");
      String pigLatin = pigLatin(s);
      pigLatin = b+pigLatin+e;
      return pigLatin;
      
   }
   public static String begin(String s)
   {
      String begin = "";
      int k=0;
      while(punct.contains(s.substring(k,k+1)))
      {
         begin += s.substring(k,k+1);
         k++;
      }      
      return begin;
   }
   public static String end(String s)
   {
      String end = "";
      String endrev = "";
      int j=s.length();
      while(punct.contains(s.substring(j-1,j)))
      {
         end += s.substring(j-1, j);
         j--;
      }
      for(int m=end.length()-1; m>=0; m--)
      {
         endrev = endrev + end.charAt(m);
      }
      return endrev;
   }
   public static int pigLatinIndex(String s)
   {
      for(int i=0;i<s.length();i++)
      {
         if(vowels.contains(s.substring(i,i+1)))
         {
            return i;   
         }
         else if(i>0 && s.substring(i,i+1).equals("y"))
         {
            return i;
         }    
         if(i<s.length()-2 && s.substring(i,i+2).equalsIgnoreCase("qu")&&vowels.contains(s.substring(i+2,i+3)))
         {
            return i+2;
         }
      }
      return -1;
   }
   public static String pigLatin(String s)
   {
      int index = pigLatinIndex(s);
      if(index==-1)
      {
         return "**** NO VOWEL ****";
      }
      if(index==0)
      {
         return s+"way";
      }
      String pigLatin = "";
      String first = new String(s.substring(0,index));
      String middle = s.substring(index,s.length());
      pigLatin = middle+first+"ay"; 
      
      if(Character.isUpperCase(s.charAt(0))==true)
      {
         s = Character.toLowerCase(s.charAt(0)) + s.substring(1);
         System.out.println(s);
      
         String f = new String(s.substring(0,index));
         String m = s.substring(index,s.length());
      
         String newMiddle = Character.toUpperCase(m.charAt(0)) + m.substring(1);
         System.out.println(newMiddle);
         pigLatin = newMiddle + f + "ay";
      }   
      return pigLatin;
   }
   public static void part_2_using_piglatenizeFile() 
   {
      Scanner sc = new Scanner(System.in);
      System.out.print("input filename including .txt: ");
      String fileNameIn = sc.next();
      System.out.print("output filename including .txt: ");
      String fileNameOut = sc.next();
      piglatenizeFile( fileNameIn, fileNameOut );
      System.out.println("Piglatin done!");
   }

/****************************** 
*  piglatinizes each word in each line of the input file
*    precondition:  both fileNames include .txt
*    postcondition:  output a piglatinized .txt file 
******************************/
   public static void piglatenizeFile(String fileNameIn, String fileNameOut) 
   {
      Scanner infile = null;
      try
      {
         infile = new Scanner(new File(fileNameIn));  
      }
      catch(IOException e)
      {
         System.out.println("oops");
         System.exit(0);   
      }
   
      PrintWriter outfile = null;
      try
      {
         outfile = new PrintWriter(new FileWriter(fileNameOut));
      }
      catch(IOException e)
      {
         System.out.println("File not created");
         System.exit(0);
      }
   	//process each word in each line
      
      
      
   
      outfile.close();
      infile.close();
   }
   
   /** EXTENSION: Output each PigLatin word in reverse, preserving before-and-after 
       punctuation.  
   */
   /*public static String pigReverse(String s)
   {
      if(s.length() == 0)
         return "";
         
         
   }*/
}
