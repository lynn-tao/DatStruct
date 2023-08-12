// Name: j126
// Date: 1/11
//uses PostfixEval

import java.util.*;
public class InfixPostfixEval
{
   public static final String LEFT  = "([{<";
   public static final String RIGHT = ")]}>";
   public static final String operators = "+ - * / % ^ !";
   
   public static void main(String[] args)
   {
      System.out.println("Infix  \t-->\tPostfix\t\t-->\tEvaluate");
      ArrayList<String> infixExp = new ArrayList<String>();
      
      infixExp.add("P + ( Q - R ) * A / B ");
      
         
         
         
         
         
         
      for( String infix : infixExp )
      {
         String pf = infixToPostfix(infix);  //get the conversion to work first
         //System.out.println(infix + "\t\t\t" + pf );  
         System.out.println(infix + "\t\t\t" + pf + "\t\t\t"  /*PostfixEval.eval(pf)*/);  //PostfixEval must work!
      }
   }
   
   public static String infixToPostfix(String infix)
   {
      List<String> nums = new ArrayList<String>(Arrays.asList(infix.split(" ")));
      Stack<String> s = new Stack<String>();
      String result = "";
      
      int size = nums.size();
      for(int i=0; i<size; i++)
      {
         //If the token is an operand, then append it to the postfix string. 
         if(!operators.contains(nums.get(i)) && !LEFT.contains(nums.get(i)) && !RIGHT.contains(nums.get(i)))
            result += nums.get(i).replace(" ", "") + " ";
         //If the token is a left parenthesis, then push it on the stack. 
         else if(nums.get(i).equals("("))
            s.push(nums.get(i));  
         //If the token is a right parenthesis, then continue popping operators off the stack and appending them to the postfix string until you pop a left parenthesis. Discard both the left and right parentheses. 
         else if(nums.get(i).equals(")"))
         {
            while(!s.isEmpty() && !s.peek().equals("("))
               result += s.pop() + " ";
            s.pop();
         }
         //operator
         else
         {
            //issue here
            if(s.isEmpty() || s.peek().equals("(") || isLower(nums.get(i).charAt(0), s.peek().charAt(0))==false)
               s.push(nums.get(i));
            else
            {
               result += s.pop() + " "; 
               while(!s.isEmpty() && !s.peek().contains("(") && !isLower(nums.get(i).charAt(0), s.peek().charAt(0))==false)
               {
                  result += s.pop() + " ";                 
               }
               s.push(nums.get(i));
            }
          
         }
      }  
      while(!s.isEmpty())
         result += s.pop() + " ";
      return result;
   }
   
   //returns true if c1 has strictly lower precedence than c2
   public static boolean isLower(char c1, char c2)
   {
      if((c1=='+' || c1=='-') && (c2=='+' || c2=='-' || c2=='*' || c2=='/' || c2=='%'))
         return true;
      else if((c1=='*' || c1=='/' || c1=='%') && (c2=='*' || c2=='/' || c2=='%' || c2=='^' || c2=='!'))
         return true;
      else 
         return false;
   }
}


/********************************************

 Infix  	-->	Postfix		-->	Evaluate
 3 + 4 * 5			3 4 5 * + 			23.0
 3 * 4 + 5			3 4 5 + * 			27.0
 1.3 + 2.7 + -6 * 6			1.3 2.7 + -6 6 * + 			-32.0
 ( 33 + -43 ) * ( -55 + 65 )			33 -43 + -55 65 + * 			-100.0
 3 * 4 + 5 / 2 - 5			3 4 5 2 5 - / + * 			6.999999999999999
 8 + 1 * 2 - 9 / 3			8 1 2 9 3 / - * + 			7.0
 3 * ( 4 * 5 + 6 )			3 4 5 6 + * * 			132.0
 3 + ( 4 - 5 - 6 * 2 )			3 4 5 - 6 2 * - + 			-10.0
 2 + 7 % 3			2 7 3 % + 			3.0
 ( 2 + 7 ) % 3			2 7 + 3 % 			0.0
     
***********************************************/
