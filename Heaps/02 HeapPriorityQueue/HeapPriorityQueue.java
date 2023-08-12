 //Name:   j126
 //Date:   3/25
 
import java.util.*;


/* implement the API for java.util.PriorityQueue
 * test this class with HeapPriorityQueue_Driver.java.
 * test this class with LunchRoom.java.
 * add(E) and remove()  must work in O(log n) time
 */
public class HeapPriorityQueue<E extends Comparable<E>> 
{
   private ArrayList<E> myHeap;
   
   public HeapPriorityQueue()
   {
      myHeap = new ArrayList<E>();
      myHeap.add(null);
   }
   
   public boolean add(E obj)
   {
     //put number at end
     //heapUp
      myHeap.add(obj);
      heapUp(myHeap.size()-1);
      return true;
   }
   
   public E remove()
   {
   //swap first and last (replace first with last)
   //heap down
      swap(1, myHeap.size()-1);
      E rem  = myHeap.remove(myHeap.size()-1);
   
      heapDown(1, myHeap.size()-1);
      return rem;
   }
   
   public E peek()
   {
      if(myHeap.size()==1)
         return null;
      else 
         return myHeap.get(1);
   }
   
   public boolean isEmpty()
   {
      if(myHeap==null || myHeap.size()==1)
         return true;
      else 
         return false;
   }
   
   private void heapUp(int k)
   {
      if(k==0 || k/2<1)
         return;
      
      int par = k/2;
        
      if(myHeap.get(par).compareTo(myHeap.get(k))>0)
         swap(par, k);
      
      heapUp(par);
   }
   
   private void swap(int a, int b)
   {
      E temp;
      temp = myHeap.get(a);
      myHeap.set(a, myHeap.get(b));
      myHeap.set(b, temp);
   }
   
   private void heapDown(int k, int size)
   {
      int l = 2 * k; 
      int r = 2 * k+1; 
   
      if(2*k+1>size)
         return;
      
      if (l < size && r < size && myHeap.get(l).compareTo(myHeap.get(r))<0)
         if (myHeap.get(l).compareTo(myHeap.get(k))<0) 
         {
            swap(k, l);
            heapDown(l, size);
         }
   
      if (r < size && l < size && myHeap.get(r).compareTo(myHeap.get(1))<0)
         if (myHeap.get(r).compareTo(myHeap.get(k))<0) 
         {
            swap(k, r);
            heapDown(r, size);
         }
         
      if (r >= size)
         if (myHeap.get(l).compareTo(myHeap.get(k))<0) 
         {
            swap(k, l);
            heapDown(l, size);
         }
   
   }
   
   public String toString()
   {
      return myHeap.toString();
   }  
}
