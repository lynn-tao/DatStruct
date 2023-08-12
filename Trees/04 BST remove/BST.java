// Name: j126
// Date: 2/16

interface BSTinterface
{
   public int size();
   public boolean contains(String obj);
   public void add(String obj);
   //public void addBalanced(String obj);
   public void remove(String obj);
   public String min();
   public String max();
   public String display();
   public String toString();
}

/*******************
Copy your BST code.  Implement the remove() method.
Test it with BST_Delete.java
**********************/
public class BST implements BSTinterface
{
   private TreeNode root;
   private int size;
   public static final String num = "1234567890";
  
   public BST()
   {
      root = null;
      size = 0;
   }
   public int size()
   {
      return size;
   }
   public TreeNode getRoot()   //for Grade-It
   {
      return root;
   }
   /***************************************
   @param s -- one string to be inserted
   ****************************************/
   public void add(String s) 
   {
      add(root, s);
      size++;
   }
   private TreeNode add(TreeNode t, String s) //recursive helper method
   {      
      if(t==null) 
      {
         root = new TreeNode(s);
         return root;
      }
      else
      {
         Comparable a = (Comparable) t.getValue();
         Comparable b = (Comparable) s;
         if(b.compareTo(a) <= 0)
            t.setLeft(add(t.getLeft(),s));
         else
            t.setRight(add(t.getRight(),s));
         
         root = t;
         return root;
      }
   }
   
   public String display()
   {
      return display(root, 0);
   }
   private String display(TreeNode t, int level) //recursive helper method
   {
      String toRet = "";
      if(t == null)
         return "";
      toRet += display(t.getRight(), level + 1); //recurse right
      for(int k = 0; k < level; k++)
         toRet += "\t";
      toRet += t.getValue() + "\n";
      toRet += display(t.getLeft(), level + 1); //recurse left
      return toRet;
   }
   
   public boolean contains( String obj)
   {
      return contains(root, obj);
   }
   private boolean contains(TreeNode t, String x) //recursive helper method
   {
      if(t==null)
         return false;
      Comparable a = (Comparable) t.getValue();
      Comparable b = (Comparable) x;
      
      if(b.compareTo(a)<0)
         return contains(t.getLeft(),x);
      else if(b.compareTo(a) > 0)
         return contains(t.getRight(),x); 
      else 
         return true;
   }
   
   public String min()
   {
      return min(root);
   
   }
   private String min(TreeNode t)  //use iteration
   {
      if(t==null) 
         return null;
      while(t.getLeft()!=null)
         t = t.getLeft();
      return t.getValue()+"";
   }
   
   public String max()
   {
      return  max(root);
   }
   private String max(TreeNode t)  //recursive helper method
   {
      if(t==null) 
         return "";
      if(t.getRight()!=null)
         return t.getValue()+"";
      return max(t.getRight());
   }
   
   public String toString()
   {
      return  toString(root);
   }
   private String toString(TreeNode t)  //an in-order traversal.  Use recursion.
   {
      String toReturn = "";
      if(t == null)
         return "";
      toReturn += toString(t.getLeft());  
      toReturn += t.getValue() + " ";               
      toReturn += toString(t.getRight());  
      return toReturn; 
   } 
   public void remove(String target)
   {
      root = remove(root, target);
      size--;
   }
   private TreeNode remove(TreeNode current, String target) //current, target
   {
      TreeNode parent = null;
      TreeNode child = current;       
      
      //find right node
      while(child != null && target.compareTo(child.getValue() + "")!=0)
      {
         parent = child;
      
         if(target.compareTo(child.getValue() + "") < 0)
            child = child.getLeft();
         else
            child = child.getRight();
      
      }
      if(child == null)
         return current;
      
      //Case 1:
      if (child.getLeft() == null && child.getRight() == null)
      {
         //1A
         if(child != current) 
         {
            if (parent.getLeft() == child) 
               parent.setLeft(null);
            else 
               parent.setRight(null);
         }
         //1B
         else 
         {
            current = null;
         }
      }
      // Case 2
      else if((child.getLeft() != null && child.getRight() == null)||(child.getLeft() == null && child.getRight()!=null))
      {         
         if (child != current)
         {
            //2A
            if (child == parent.getLeft() && child.getLeft()!=null) 
            {
               parent.setLeft(child.getLeft());
            } 
            else if(child == parent.getLeft() && child.getRight()!=null)
            {
               parent.setLeft(child.getRight());
            
            } 
            //2B
            else if(child == parent.getRight() && child.getLeft()!=null)
            {
               parent.setRight(child.getLeft());
            
            } 
            else if(child == parent.getRight() && child.getRight()!=null)
            {
               parent.setRight(child.getRight()); 
            } 
         }
         //2C
         else 
         {
            if(child.getLeft()!=null)
               current = child.getLeft();
            else if(child.getRight()!=null)
               current = child.getRight();
         }
      }
          
      //Case 3
      else if (child.getLeft() != null && child.getRight() != null)
      {
         TreeNode maxpar = child;
         TreeNode max = child.getLeft();;       
         if(max == null)
            return null;
         while (max.getRight() != null) 
         {
            maxpar = max;
            max = max.getRight();
         }
         //3A
         if(max.getLeft() == null && max.getRight() == null && child.getLeft()!=max)
         {
            child.setValue(max.getValue());
            maxpar.setRight(null);    
         }
         //3B
         else if(max.getLeft() != null && max.getRight() == null && child.getLeft()!=max)  
         {
            child.setValue(max.getValue());
            maxpar.setRight(max.getLeft());
         }  
         //3C 
         else if(max.getLeft() == null && max.getRight() == null && child.getLeft()==max)
         {
            child.setValue(max.getValue());
            child.setLeft(null); 
         }
         //3D
         else if(max.getLeft() != null && max.getRight() == null && child.getLeft()==max)
         {
            child.setValue(max.getValue());
            child.setLeft(max.getLeft()); 
         }
      }
      return current;
   }
          
   public static TreeNode maxValue(TreeNode curr)
   {
      if(curr==null)
         return null;
      while (curr.getRight() != null) {
         curr = curr.getRight();
      }
      return curr;
   }   
}