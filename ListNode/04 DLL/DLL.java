// Name: j126
// Date: 12/1

//  implements some of the List and LinkedList interfaces: 
//	 	  size(), add(i, o), remove(i);  addFirst(o), addLast(o); 
//  This class also overrides toString().
//  the list is zero-indexed.
//  Uses DLNode.

public class DLL        //DoubleLinkedList
{
   private int size;
   private DLNode head = new DLNode(); //dummy node--very useful--simplifies the code
   
   public int size()
   {
      return size;
   }
   
   /* appends obj to end of list; increases size;
   	  @return true  */
   public boolean add(Object obj)
   {
      addLast(obj);
      return true;   
   }
   
   /* inserts obj at position index.  increments size. 	*/
   public void add(int index, Object obj) throws IndexOutOfBoundsException  //this the way the real LinkedList is coded
   {
      if( index > size || index < 0 )
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      /* enter your code below  */
      DLNode first = head;
      while(index>0)
      {
         first = first.getNext();
         index--;
      }  
      DLNode addThisNode = new DLNode(obj, first, first.getNext());
      first.setNext(addThisNode);
      first.getNext().getNext().setPrev(addThisNode);
      size++;                   
   }
   
   /* return obj at position index. 	*/
   public Object get(int index)
   { 
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      /* enter your code below  */
      DLNode first = head.getNext();
      if(index==0)
         return first.getValue();
      else
      {
         while(index>0)
         {
            first = first.getNext();
            index--;
         }  
         return first.getValue();
      }
   }
   
   /* replaces obj at position index. 
        returns the obj that was replaced*/
   public Object set(int index, Object obj)
   {
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      /* enter your code below  */
      DLNode first = head;
      while(index>=0)
      {
         first = first.getNext();
         index--;
      } 
      Object replace = first.getValue();
      first.setValue(obj);
      return replace;
   }
   
   /*  removes the node from position index (zero-indexed).  decrements size.
       @return the object of the node that was removed.    */
   public Object remove(int index)
   {
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      /* enter your code below  */
      DLNode first = head;
      while(index>0)
      {
         first = first.getNext();
         index--;
      }  
      DLNode remove = first.getNext();
      first.setNext(first.getNext().getNext());
      first.getNext().setPrev(first);     
      
      remove.setPrev(null);
      remove.setNext(null);
      size--; 
      return remove.getValue();  
   }
   
   /* inserts obj at front of list, increases size   */
   public void addFirst(Object obj)
   {
      if(head == null)
         head.getNext().setValue(obj);
      else
      {
         DLNode first = new DLNode(obj, head, head.getNext());
         head.setNext(first);
         head.getNext().getNext().setPrev(first);
         size++;
         
      }
   }
   
   /* appends obj to end of list, increases size    */
   public void addLast(Object obj)
   {
      if(size == 0)
      {
         DLNode e = new DLNode(obj, head, head);
         head.setNext(e);
         size++;
      }
      else
      {
         DLNode first = head;
         int count = size;
         while(count>0)
         {
            first = first.getNext();
            count--;
         }
         DLNode end = new DLNode(obj, first, head); 
         first.setNext(end);
         head.setPrev(end);
         size++;
      }
   }
   
   /* returns the first element in this list  */
   public Object getFirst()
   {
      return head.getNext().getValue();
   }
   
   /* returns the last element in this list  */
   public Object getLast()
   {
      if(head.getNext() == null)
         return null;
      else
      {
         DLNode first = head;
         int count = size;
         while(count>0)
         {
            first = first.getNext();
            count--;
         }
         return first.getValue();
      }
   }
   
   /* returns and removes the first element in this list, or
       returns null if the list is empty  */
   public Object removeFirst()
   {
      if(head.getNext() == null)
         return null;
      else
      {
         DLNode traverse = head.getNext();
         
         head.setNext(head.getNext().getNext());
         traverse.setPrev(null);
         traverse.setNext(null);
         head.getNext().setPrev(head);
         size--;
         return traverse.getValue();
         
      }
   }
   
   /* returns and removes the last element in this list, or
       returns null if the list is empty  */
   public Object removeLast()
   {
      if(head.getNext() == null)
         return null;
      DLNode first = head;
      int count = size;
      while(count>1)
      {
         first = first.getNext();
         count--;
      }
      DLNode last = first.getNext();
      first.setNext(first.getNext().getNext());
      first.getNext().setPrev(first);
      last.setNext(null);
      last.setPrev(null);
   
      size--;
      return last.getValue();
   }
   
   /*  returns a String with the values in the list in a 
       friendly format, for example   [Apple, Banana, Cucumber]
       The values are enclosed in [], separated by one comma and one space.
    */
   public String toString()
   {
      String s = "";
      s += "[";
      int count = size;
      DLNode first = head;
      while(count>0)
      {
         if(count>1)
         {
            first = first.getNext();
            s += first.getValue() + ", ";
         }
         else
         {
            first = first.getNext();
            s += first.getValue();
         }
         count--;
      }
      s += "]";
      return s;
   }
}