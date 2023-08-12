// Name: j126
// Date: 1/5/21

import java.util.*;

public class ParenMatch
{
   public static final String LEFT  = "([{<";
   public static final String RIGHT = ")]}>";
   
   public static void main(String[] args)
   {
      System.out.println("Parentheses Match");
      ArrayList<String> parenExp = new ArrayList<String>();
      /* enter test cases here */
      //parenExp.add("5 + 7");
      parenExp.add(" ( ) [ ]");
   
      for( String s : parenExp )
      {
         boolean good = checkParen(s);
         if(good)
            System.out.println(s + "\t good!");
         else
            System.out.println(s + "\t BAD");
      }
   }
     
   //returns the index of the left parentheses or -1 if is not
   public static int isLeftParen(String p)
   {
      return LEFT.indexOf(p);
   }
  
   //returns the index of the right parentheses or -1 if is not
   public static int isRightParen(String p)
   {
      return RIGHT.indexOf(p);
   }
   
   public static boolean checkParen(String exp)
   {
      Stack<String> s = new Stack<String>();
      for(int i=0; i<exp.length(); i++)
      {
         if(LEFT.contains(exp.substring(i,i+1)))
            s.push(exp.substring(i,i+1));
         if(RIGHT.contains(exp.substring(i,i+1)))
            if(s.isEmpty() || !(isLeftParen(s.pop()) == isRightParen(exp.substring(i,i+1))))
               return false;
      }
      if(s.isEmpty()==true)
         return true;
      else
         return false;
   }
}

/*****************************************

Parentheses Match
5 + 7		 good!
( 15 + -7 )		 good!
) 5 + 7 (		 BAD
( ( 5.0 - 7.3 ) * 3.5 )		 good!
< { 5 + 7 } * 3 >		 good!
[ ( 5 + 7 ) * ] 3		 good!
( 5 + 7 ) * 3		 good!
5 + ( 7 * 3 )		 good!
( ( 5 + 7 ) * 3		 BAD
[ ( 5 + 7 ] * 3 )		 BAD
[ ( 5 + 7 ) * 3 ] )		 BAD
( [ ( 5 + 7 ) * 3 ]		 BAD
( ( ( ) $ ) )		 good!
( ) [ ]		 good!
 
 *******************************************/
