 //Name: j1-26     
 //Date: 10/29

import java.util.*;
import java.io.*;

public class InsertionSort_Driver
{
   public static void main(String[] args) throws Exception
   {
      //Part 1, for doubles
      /*int n = (int)(Math.random()*100)+20;
      double[] array = new double[n];
      for(int k = 0; k < array.length; k++)
         array[k] = Math.random()*100;	*/
      double x = 160;
      
      double[] array = new double[7];
      for(int k = 0; k < array.length; k++)
         array[k] = (int)(Math.random()*9) + 1;
   
      Insertion.sort(array);
      print(array);
      
      if( isAscending(array) )
         System.out.println("In order!");
      else
         System.out.println("Out of order  :-( ");
      System.out.println();
      
      //Part 2, for Strings
      int size = 100;
      Scanner sc = new Scanner(new File("declaration.txt"));
      Comparable[] arrayStr = new String[size];
      for(int k = 0; k < arrayStr.length; k++)
         arrayStr[k] = sc.next();	
   
      Insertion.sort(arrayStr);
      print(arrayStr);
      System.out.println();
      
      if( isAscending(arrayStr) )
         System.out.println("In order!");
      else
         System.out.println("Out of order  :-( ");
   }
   
   public static void print(double[] a)
   {
      for(double d: a)         // for-each loop
         System.out.print(d+" ");
      System.out.println();
   }
   
   public static void print(Object[] papaya)
   {
      for(Object abc : papaya)    
         System.out.print(abc+" ");
   }
   
   public static boolean isAscending(double[] a)
   {
      for(int i=1; i<a.length-1; i++)
      {
         if(a[i]<a[i-1])
            return false;
      }
      return true;
   }
   
   @SuppressWarnings("unchecked")//this removes the warning for Comparable
   public static boolean isAscending(Comparable[] a)
   {
      for(int i=1; i<a.length-1; i++)
      {
         if(a[i].compareTo(a[i+1])<0)
            return false;
      }
      return true;
   }
}

//**********************************************************

class Insertion
{
   public static void sort(double[] array)
   { 
      for(int i=1; i<array.length; i++)
      {
         double val = array[i];
         int x = shift(array, i, val);
         array[x] = val; 
      }
   }
 
   private static int shift(double[] array, int index, double value)
   {
      while(index>0 && array[index-1]>value)
      { 
         array[index]=array[index-1];
         index--;
      }
      return index;
   }
 
   @SuppressWarnings("unchecked")
   public static void sort(Comparable[] array)
   { 
      for(int i=1; i<array.length; i++)
      {
         Comparable val = array[i];
         int x = shift(array, i-1, val);
         array[x] = val;
      }
   }
   
   @SuppressWarnings("unchecked")
   private static int shift(Comparable[] array, int index, Comparable value)
   {
      while(array[index].compareTo(value)<0 && index>0)
      {
         array[index+1]=array[index];
         index--;
      }
      return index;
   }
}
