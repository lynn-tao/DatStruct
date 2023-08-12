// Name: j126
// Date: 1/7

import java.util.*;

public class PostfixEval
{
   public static final String operators = "+ - * / % ^ !";
   
   public static void main(String[] args)
   {
      System.out.println("Postfix  -->  Evaluate");
      ArrayList<String> postfixExp = new ArrayList<String>();
      /*  build your list of expressions here  */
   
      //postfixExp.add("3 4 5 * + ");
      postfixExp.add("2 5 - 4 3 + *");
   
      
      for( String pf : postfixExp )
      {
         System.out.println(pf + "\t\t" + eval(pf));
      }
   }
   
   public static double eval(String pf)
   {
      List<String> postfixParts = new ArrayList<String>(Arrays.asList(pf.split(" ")));
      /*  enter your code here  */
      Stack<String> s = new Stack<String>();
      
      int size = postfixParts.size();
      for(int i=0; i<size; i++)
      {
         if(!isOperator(postfixParts.get(i)))
            s.push(postfixParts.get(i));
         else if(isOperator(postfixParts.get(i)))
         {
            if(postfixParts.get(i).equals("!"))
               s.push(eval(0,Double.parseDouble(s.pop()), postfixParts.get(i))+"");
            else
               s.push(eval(Double.parseDouble(s.pop()),Double.parseDouble(s.pop()),postfixParts.get(i))+"");
         }
      } 
      return Double.parseDouble(s.pop());
   }
   
   public static double eval(double a, double b, String ch)
   {
      if(ch.equals("+"))
         return a+b;
      if(ch.equals("-"))
         return b-a;
      if(ch.equals("*"))
         return a*b;
      if(ch.equals("/"))
         return b/a;
      if(ch.equals("%"))
         return b%a;
      if(ch.equals("^"))
         return Math.pow(b,a);
      if(ch.equals("!"))
      {
         int s = 1;
         for(int i=(int)b; i>0; i--)
            s = s*i;
         return s;
      }
      return 0;
   }
   
   public static boolean isOperator(String op)
   {
      return operators.contains(op);
   }
}

/**********************************************
Postfix  -->  Evaluate
 3 4 5 * +		23
 3 4 * 5 +		17
 10 20 + -6 6 * +		-6
 3 4 + 5 6 + *		77
 3 4 5 + * 2 - 5 /		5
 8 1 2 * + 9 3 / -		7
 2 3 ^		8
 20 3 %		2
 21 3 %		0
 22 3 %		1
 23 3 %		2
 5 !		120
 1 1 1 1 1 + + + + !		120
 
 
 *************************************/