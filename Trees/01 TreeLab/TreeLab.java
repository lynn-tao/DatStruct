// Name: j126
// Date: 2/4

import java.util.*;

public class TreeLab
{
   public static TreeNode root = null;
   public static String s = "PQR-AB/*+";
	//public static String s = "XThomasJeffersonHighSchool"; 
	//public static String s = "XAComputerScienceTreeHasItsRootAtTheTop";
	//public static String s = "XA";   //comment out lines 44-46 below
	//public static String s = "XAF";  //comment out lines 44-46 below
	//public static String s = "XAFP";  //comment out lines 44-46 below
	//public static String s = "XDFZM";  //comment out lines 44-46 below 

   public static void main(String[] args)
   {
      root = buildTree( root, s );
      System.out.print( display( root, 0) );
   
      System.out.print("\nPreorder: " + preorderTraverse(root));
      System.out.print("\nInorder: " + inorderTraverse(root));
      System.out.print("\nPostorder: " + postorderTraverse(root));
   
      System.out.println("\n\nNodes = " + countNodes(root));
      System.out.println("Leaves = " + countLeaves(root));
      System.out.println("Only children = " + countOnlys(root));
      System.out.println("Grandparents = " + countGrandParents(root));
   
      System.out.println("\nHeight of tree = " + height(root));
      System.out.println("Longest path = " + longestPath(root));
      System.out.println("Min = " + min(root));
      System.out.println("Max = " + max(root));	
   
      System.out.println("\nBy Level: ");
      System.out.println(displayLevelOrder(root));
   }

   public static TreeNode buildTree(TreeNode root, String s)
   {
      root = new TreeNode("" + s.charAt(1), null, null);
      for(int pos = 2; pos < s.length(); pos++)
         insert(root, "" + s.charAt(pos), pos, 
            	(int)(1 + Math.log(pos) / Math.log(2)));
      insert(root, "A", 17, 5); 
      insert(root, "B", 18, 5); 
      insert(root, "C", 37, 6); //B's right child
      return root;
   }

   public static void insert(TreeNode t, String s, int pos, int level)
   {
      TreeNode p = t;
      for(int k = level - 2; k > 0; k--)
      {
         if((pos & (1 << k)) == 0)
            p = p.getLeft();
         else
            p = p.getRight();
      }
      if((pos & 1) == 0)
         p.setLeft(new TreeNode(s, null, null));
      else
         p.setRight(new TreeNode(s, null, null));
   }

   private static String display(TreeNode t, int level)
   {
   	// turn your head towards left shoulder visualize tree
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

   public static String preorderTraverse(TreeNode t)
   { 
      String toReturn = "";
      if(t == null)
         return "";
      toReturn += t.getValue() + " ";              //preorder visit
      toReturn += preorderTraverse(t.getLeft());   //recurse left
      toReturn += preorderTraverse(t.getRight());  //recurse right
      return toReturn;
   }

   public static String inorderTraverse(TreeNode t)
   {
      String toReturn = "";
      if(t == null)
         return "";
      toReturn += inorderTraverse(t.getLeft());  
      toReturn += t.getValue() + " ";               
      toReturn += inorderTraverse(t.getRight());  
      return toReturn; 
   }

   public static String postorderTraverse(TreeNode t)
   {
      String toReturn = "";
      if(t == null)
         return "";
      toReturn += postorderTraverse(t.getLeft());  
      toReturn += postorderTraverse(t.getRight());  
      toReturn += t.getValue() + " ";               
      return toReturn;       
   }      
   public static int countNodes(TreeNode t)
   {
      if(t == null) 
         return 0;
      else 
         return 1+ countNodes(t.getLeft()) + countNodes(t.getRight());
   }

   public static int countLeaves(TreeNode t)
   {
      if(t == null) 
         return 0;
      else if(t.getLeft()==null && t.getRight()==null)
         return 1;
      else
         return countLeaves(t.getLeft()) + countLeaves(t.getRight());
   }

	/*  there are clever ways and hard ways to count grandparents  */ 
   public static int countGrandParents(TreeNode t)
   {
      int h = height(t);
      if (h <= 1) 
         return 0;
      return 1 + countGrandParents(t.getLeft()) + countGrandParents(t.getRight());
   }

   public static int countOnlys(TreeNode t)
   {
      if(t == null) 
         return 0;  
      if (t.getLeft() != null && t.getRight() != null)
      {
         return countOnlys(t.getLeft()) + countOnlys(t.getRight());
      } 
      else if (t.getRight() != null)
      {
         return 1+ countOnlys(t.getRight());
      }
      else if (t.getLeft() != null)
      {
         return 1+ countOnlys(t.getLeft());
      }
      else
         return 0;
   }

	/* returns the max of the heights to the left and the heights to the right  
     returns -1 in case the tree is null
	 */
   public static int height(TreeNode t)
   {
      if(t==null)
         return -1;
      else
      {
         int left = height(t.getLeft());
         int right = height(t.getRight());
         return Math.max(left, right) + 1;
      }
   }

	/* return the max of the sum of the heights to the left and the heights to the right  
	 */
   public static int longestPath(TreeNode t)
   {
      if(t==null)
         return -1;
      if (t.getLeft() == null && t.getRight() == null) 
         return 0;
   	
      int left = height(t.getLeft());
      int right = height(t.getRight());
      int sum = left + right + 2;
   	
      int leftPath = longestPath(t.getLeft());
      int rightPath = longestPath(t.getRight());
   	
      return Math.max(sum, Math.max(leftPath, rightPath));
   }

	/*  Object must be cast to Comparable in order to call .compareTo  
	 */
   @SuppressWarnings("unchecked")
   public static Object min(TreeNode t)
   {
      Object min;
      if (t == null) 
         return "z";  
      Comparable a = (Comparable) t.getValue();
      Comparable b = (Comparable) min(t.getLeft());
      Comparable c = (Comparable) min(t.getRight());
   
      if(b.compareTo(a)<0 && b.compareTo(c) < 0)
         min = b;
      else if(c.compareTo(a)<0 && c.compareTo(b)<0)
         min=c;
      else
         min=a;
      return min;
   }

	/*  Object must be cast to Comparable in order to call .compareTo  
	 */
   @SuppressWarnings("unchecked")
   public static Object max(TreeNode t)
   {
      Object max;
      if (t == null) 
         return "A";
   	
      Comparable a = (Comparable) t.getValue();
      Comparable b = (Comparable)max(t.getLeft());
      Comparable c = (Comparable)max(t.getRight());
   
      if(b.compareTo(a)>0 && b.compareTo(c) > 0)
         max = b;
      else if(c.compareTo(a)>0 && c.compareTo(b)>0)
         max=c;
      else
         max=a;
      return max;
   }

	/* This method is not recursive.  Use a local queue
	 * to store the children of the current TreeNode.
	 */
   public static String displayLevelOrder(TreeNode t)
   {
      String ret = "";
   
      Queue<TreeNode> nodes = new LinkedList<TreeNode>();
      if (t != null) nodes.add(t);
      while (!nodes.isEmpty()) 
      {
         TreeNode currNode = nodes.remove();
         ret += currNode.getValue();
      
         if (currNode.getLeft() != null) {
            nodes.add(currNode.getLeft());
         }
      
         if (currNode.getRight() != null) {
            nodes.add(currNode.getRight());
         }
      }
      return ret;
   }
}

/***************************************************
    ----jGRASP exec: java TreeLab
 		  	E
 		E
 			C
 	M
 			N
 		T
 			E
 C
 			I
 		U
 			C
 	O
 			S
 					C
 				B
 		P
 				A
 			R

 Preorder: C O P R A S B C U C I M T E N E C E 
 Inorder: R A P B C S O C U I C E T N M C E E 
 Postorder: A R C B S P C I U O E N T C E E M C 

 Nodes = 18
 Leaves = 8
 Only children = 3
 Grandparents = 5

 Height of tree = 5
 Longest path = 8
 Min = A
 Max = U

 By Level: 
 COMPUTERSCIENCEABC    
 *******************************************************/
