// Name:  j126            Date: 4/6
import java.util.*;
import java.io.*;
public class deHuffman
{
   public static void main(String[] args) throws IOException
   {
      Scanner keyboard = new Scanner(System.in);
      System.out.print("\nWhat binary message (middle part)? ");
      String middlePart = keyboard.next();
      Scanner sc = new Scanner(new File("message."+middlePart+".txt")); 
      String binaryCode = sc.next();
      Scanner huffLines = new Scanner(new File("scheme."+middlePart+".txt")); 
      	
      TreeNode root = huffmanTree(huffLines);
      String message = dehuff(binaryCode, root);
      System.out.println(message);
      	
      sc.close();
      huffLines.close();
   }
   public static TreeNode huffmanTree(Scanner huffLines)
   {
      TreeNode huff = new TreeNode("");
      TreeNode copy = huff;
   
      while(huffLines.hasNext())
      {
         String dir = huffLines.nextLine();
         String letter = dir.substring(0,1);
         for(int i=1; i<dir.length(); i++)
         {
            if(dir.substring(i,i+1).equals("0") && i!=dir.length()-1)
            {
               if(copy.getLeft()!=null)
                  copy = copy.getLeft();
               else
               {
                  TreeNode zero = new TreeNode("");
                  copy.setLeft(zero);
                  copy = zero;
               }
            }
            else if(dir.substring(i,i+1).equals("1") && i!=dir.length()-1)
            {
               if(copy.getRight()!=null)
                  copy = copy.getRight();
               else
               {
                  TreeNode one = new TreeNode("");
                  copy.setRight(one);
                  copy = one;
               }
            }
            else 
            {
               if(dir.substring(i,i+1).equals("0"))
               {
                  TreeNode zero = new TreeNode(letter);
                  copy.setLeft(zero);
                  copy = zero;
               }
               else if(dir.substring(i,i+1).equals("1"))
               {
                  TreeNode one = new TreeNode(letter);
                  copy.setRight(one);
                  copy = one;
               }   
            }
            
         }
         copy = huff;     
      }
      return huff;
   }
   public static String dehuff(String text, TreeNode root)
   {
      TreeNode copy = root;
      String dehuff = "";
      for(int i=0; i<text.length(); i++)
      {
         if(text.substring(i,i+1).equals("0"))
            copy = copy.getLeft();
         else if(text.substring(i,i+1).equals("1"))
            copy = copy.getRight();
         if(copy.getValue()!="")
         {
            dehuff+=copy.getValue();
            copy = root;
         }
      }
      
      return dehuff;
   }
}

 /* TreeNode class for the AP Exams */
class TreeNode
{
   private Object value; 
   private TreeNode left, right;
   
   public TreeNode(Object initValue)
   { 
      value = initValue; 
      left = null; 
      right = null; 
   }
   
   public TreeNode(Object initValue, TreeNode initLeft, TreeNode initRight)
   { 
      value = initValue; 
      left = initLeft; 
      right = initRight; 
   }
   
   public Object getValue()
   { 
      return value; 
   }
   
   public TreeNode getLeft() 
   { 
      return left; 
   }
   
   public TreeNode getRight() 
   { 
      return right; 
   }
   
   public void setValue(Object theNewValue) 
   { 
      value = theNewValue; 
   }
   
   public void setLeft(TreeNode theNewLeft) 
   { 
      left = theNewLeft;
   }
   
   public void setRight(TreeNode theNewRight)
   { 
      right = theNewRight;
   }
}
