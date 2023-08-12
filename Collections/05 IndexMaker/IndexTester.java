import java.util.*;
import java.io.*;

public class IndexTester
{
   public static void main(String[] args) throws IOException
   {
      ArrayList<Integer> list = new ArrayList<Integer>();
      //30, 20, 10, 60, 50, 40
      /*list.add(-4);list.add(16);list.add(9); 
      list.add(1); list.add(64); list.add(25);*/
      list.add(-1); list.add(3); list.add(28);
      list.add(17); list.add(9); list.add(33); 
      mystery2(list);
   }
   public static void mystery1(ArrayList<Integer> list) { 
      for (int i = list.size() - 1; i > 0; i--) { 
         if (list.get(i) < list.get(i - 1)) { 
            int element = list.get(i); 
            list.remove(i); 
            list.add(0, element); 
         } 
      } 
      System.out.println(list); 
   }
   public static void mystery2(ArrayList<Integer> list) {
      for (int i = list.size() - 1; i >= 0; i--) {
         if (i % 2 == 0) {
            list.add(list.get(i));
         } else {
            list.add(0, list.get(i));
         }
      }
      System.out.println(list);
   }
}