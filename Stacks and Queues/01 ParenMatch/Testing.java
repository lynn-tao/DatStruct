import java.util.*;

public class Testing
{
   public static final String LEFT  = "([{<";
   public static final String RIGHT = ")]}>";
   
   public static void main(String[] args)
   {
   
      Queue<Integer> q = new LinkedList<Integer> ();
      Stack<Integer> stk = new Stack<Integer>();
      for(int i=1;i<=10;i++)
      {
         q.add(i);
      }
        
      for(int i=1; i<=10;i++){
         int x = q.remove();
         if (x%2==0)
            stk.push(x);
         else
            q.add(x);
      }
        
      while(! stk.isEmpty())
      {
         q.add(stk.pop());
      }
      System.out.println(q);
   }
         
}