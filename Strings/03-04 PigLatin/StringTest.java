public class StringTest
{

   public static void main(String[] args)
   {
      String test = "xihexiloxilo";
      String str = "xi";
      int count = 0;
      while(test.indexOf(str) != -1)
      {
         test = test.substring(test.indexOf(str), test.length());
         count++;
      }
      System.out.print(count);
   }
}