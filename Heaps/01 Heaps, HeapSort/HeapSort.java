// Name: j126
// Date: 3/23

import java.text.DecimalFormat;
public class HeapSort
{
   public static int SIZE;  //9 or 100
	
   public static void main(String[] args)
   {
      //Part 1: Given a heap, sort it. Do this part first. 
      SIZE = 9;  
      double heap[] = {1, 99, 80, 85, 17, 30, 84, 2, 16};
      /*-1.0, 7.2, 3.4, 6.4, 9.9*/
      
      display(heap);
      sort(heap);
      display(heap);
      System.out.println(isSorted(heap));
      
      //Part 2:  Generate 100 random numbers, make a heap, sort it.
      /*SIZE = 100;
      double[] heap = new double[SIZE + 1];
      heap = createRandom(heap);
      display(heap);
      makeHeap(heap, SIZE);
      display(heap); 
      sort(heap);
      display(heap);
      System.out.println(isSorted(heap));
      */
   }
   
	//******* Part 1 ******************************************
   public static void display(double[] array)
   {
      for(int k = 1; k < array.length; k++)
         System.out.print(array[k] + "    ");
      System.out.println("\n");	
   }
   
   public static void sort(double[] array)
   {
      makeHeap(array, array.length);
      /* enter your code here */
      int tracker = array.length-1;
      while(!isSorted(array) && tracker>0)
      {
         swap(array, 1, tracker);
         heapDown(array, 1, tracker-1);
         tracker--;
      }
   
      if(array[1] > array[2])   //just an extra swap, if needed.
         swap(array, 1, 2);
   }
  
   public static void swap(double[] array, int a, int b)
   {
      double temp;
      temp = array[a];
      array[a] = array[b];
      array[b] = temp;
   }
   
   public static void heapDown(double[] array, int k /*index*/, int size)
   {
      int l = 2 * k; 
      int r = 2 * k+1; 
   
      if(2*k+1>size)
         return;
      
      if (l < size && r < size && array[l] > array[r])
         if (array[l] > array[k]) 
         {
            swap(array, k, l);
            heapDown(array, l, size);
         }
   
      if (r < size && l < size && array[r] > array[l])
         if (array[r] > array[k]) 
         {
            swap(array, k, r);
            heapDown(array, r, size);
         }
         
      if (r >= size)
         if (array[l] > array[k]) 
         {
            swap(array, k, l);
            heapDown(array, l, size);
         }
         
   }
   
   public static boolean isSorted(double[] arr)
   {
      for(int i=1; i<arr.length/2; i++)
         if(i*2 < arr.length && i*2+1 < arr.length)
            if(!(arr[i]<arr[2*i]) || !(arr[i]<arr[2*i+1]))
               return false;
      return true;   
   }
   
   //****** Part 2 *******************************************

   //Generate 100 random numbers (between 1 and 100, formatted to 2 decimal places) 
   public static double[] createRandom(double[] array)
   {  
      array[0] = -1;   //because it will become a heap
      
      for(int i=1; i<array.length; i++)
      {
         DecimalFormat df = new DecimalFormat("#.00");
         array[i] = Double.parseDouble(df.format(Math.random()*100+1));
      }
       
      return array;
   }
   
   //turn the random array into a heap
   public static void makeHeap(double[] array, int size)
   {
      for(int i= size/2; i>0; i--)
         heapDown(array, i, size);
   }
}

