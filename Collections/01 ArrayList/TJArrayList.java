// Name: j1-26
// Date: 12/07

/**
 * Implements the cheat sheet's List interface.  Implements generics.
 * The backing array is an array of (E[]) new Object[10];
 * @override toString()
 */ 
public class TJArrayList<E>
{
   private int size;							//stores the number of objects
   private E[] myArray;
   public TJArrayList()                //default constructor makes 10 cells
   {
      myArray = (E[]) new Object[10];
      size = 0;
   }
   public int size()
   {
      return size;
   }
 	/* appends obj to end of list; increases size;
      must be an O(1) operation when size < array.length, 
         and O(n) when it doubles the length of the array.
	  @return true  */
   public boolean add(E obj)
   {
      if(size<myArray.length)
      {
         myArray[size] = obj;
         size++;
         return true;
      }
      else
      {
         E[] array = (E[]) new Object[2*size];
         int i=0;
         for(i=0; i<myArray.length; i++)
         {
            array[i] = myArray[i];
         }
         array[i] = obj;
         size++;
         myArray = array;
         return true;
      }
   }
   /*inserts obj at position index.  increments size. 
		*/
   public void add(int index, E obj) throws IndexOutOfBoundsException  //this the way the real ArrayList is coded
   {
      if(index > size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      E[] array = (E[]) new Object[2*size];
      for(int i=0; i<index; i++)
      {
         array[i] = myArray[i];
      }
      array[index] = obj;
      int i = index+1;
      while(myArray[i-1]!=null) 
      {
         array[i] = myArray[i-1];
         i++;
      }
      size++;
      myArray = array;
   }

   /*return obj at position index.  
		*/
   public E get(int index) throws IndexOutOfBoundsException
   {
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      return myArray[index];
   }
   /**
    * Replaces obj at position index. 
    * @return the object is being replaced.
    */
   public E set(int index, E obj) throws IndexOutOfBoundsException  
   { 
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      E old = myArray[index];
      myArray[index] = obj;
      return old;
   }
 /*  removes the node from position index. shifts elements 
     to the left.   Decrements size.
	  @return the object at position index.
	 */
   public E remove(int index) throws IndexOutOfBoundsException  
   {
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      size--;
      E old = myArray[index];
      for(int i = index; i<myArray.length-1; i++)
      {
         myArray[i] = myArray[i+1];
      }
      return old;
   }
	   /*
		   This method compares objects.
         Must use .equals(), not ==
     	*/
   public boolean contains(E obj)
   {
      for(int i=0; i<size; i++)
      {
         if(myArray[i].equals(obj))
            return true;
      }
      return false;
   }
	 /*returns a String of E objects in the array with 
       square brackets and commas.
     */	
   public String toString()
   {
      String s = "[";
      for(int i=0; i<size; i++)
      {
         if(i!=size-1)
            s+= myArray[i] +", ";
         else
            s+= myArray[i] + "]";
      }
      return s;
   }
}