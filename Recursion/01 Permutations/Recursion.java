public class Recursion
{
   public static void main (String[] args)
   {
   
fun(3);      }
   public static void p(int n)
   { 
      p(n-1);
      System.out.print(n);
      p(n-1);
   }
   public static void  displayDigits(String  strDigits, int i)
   {
      if(i==9)
      {
         System.out.println(strDigits + i);
         return;
      }
      displayDigits(strDigits+i,  i+1);
   }
   public static String  displayDigits(int i)
   {
      if(i==9)
      {
         return "" + 9;
      
      }
      return "" +  i + displayDigits(i+1);
   }
   public static String foo(String s){
   
      return helper(s,0);
   
   }

   public static String helper(String s, int i){
   
      if(i==s.length())
      
         return "";
      
      else
      
         return s.charAt(i) + helper(s,i+1) +  s.charAt(i) ;
   
   }  
   public static void fun(int x)
   {
      if(x>=1)
      {
         System.out.print(x);
         fun(x-1);
      
      }
   }
}