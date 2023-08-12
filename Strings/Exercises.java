public class Exercises 
{ 
   public static void main(String[] args) 
   { 
      /*String a = new String("best");
      String b = new String("bee");
      System.out.print(a.compareTo(b));
      System.out.println(a.substring(2,4));
      String c = new String(a+"--"+b);
      System.out.println(c);*/
      
      String a = new String("Hello! my.name.is.lynn,,");
      String punct = "!.,";
      
      for(int i=0;i<a.length();i++)
      {
         if(punct.contains(a.substring(i,i+1)))
         {
            a = a.replace(a.substring(i,i+1), " ");
         }
      }
      System.out.println(a);
      
      String a = "Internet";
      String b = "net";
      System.out.println(b.compareTo(a));
      
      
      
   } 
     
}