// Name: j126
// Date: 2/9
/*  Represents a binary expression tree.
 *  The BXT builds itself from postorder expressions. It can
 *  evaluate and print itself.  Also prints inorder and postorder strings. 
 */
 
import java.util.*;

public class BXT
{
   private TreeNode root; 
   public static final String operators = "+ - * / % ^ !";
  
   
   public BXT()
   {
      root = null;
   }
   public TreeNode getRoot()   //for Grade-It
   {
      return root;
   }
    
   public void buildTree(String str)
   {
      List<String> postfix = new ArrayList<String>(Arrays.asList(str.split(" ")));
      Stack<TreeNode> s = new Stack<TreeNode>();
   
      TreeNode t = null;
      for( String pf : postfix )
      {
         if(operators.contains(pf))
         {
            t = new TreeNode(pf);
            
            TreeNode a = s.pop();
            TreeNode b = s.pop();  
            t.setRight(a);
            t.setLeft(b);
            s.push(t);
         }
         else
         {
            t = new TreeNode(pf);
            s.push(t);
         } 
      }
      root = s.pop();
   }
   
   public double evaluateTree()
   {
      return evaluateNode(root);
   }
   
   private double evaluateNode(TreeNode t)  //recursive
   {
      if(t==null) 
         return 0;
      if(operators.contains(t.getValue()+""))
      {
         return computeTerm(t.getValue()+"", evaluateNode(t.getLeft()) , evaluateNode(t.getRight()));
      }
      else
         return Double.parseDouble(t.getValue()+"");  
   }
   
   private double computeTerm(String s, double a, double b)
   {
      if(s.equals("+"))
         return a + b;
      if(s.equals("*"))
         return a * b;
      if(s.equals("-"))
         return a - b;
      if(s.equals("/"))
         return a / b;
      if(s.equals("^"))
         return Math.pow(a,b);
      if(s.equals("%"))
         return a%b;
      if(s.equals("!"))
      {
         int f = 1;
         for(int i = 1; i<=b; i++)
            f *= i;
         return f;
      }
      else
         return 0;   
     
   } 
   private boolean isOperator(String s)
   {
      return operators.contains(s);
   
   }
   
   public String display()
   {
      return display(root, 0);
   }
   
   private String display(TreeNode t, int level)
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
    
   public String inorderTraverse()
   {
      return inorderTraverse(root);
   }
   
   private  String inorderTraverse(TreeNode t)
   {
      String toReturn = "";
      if(t == null)
         return "";
      toReturn += inorderTraverse(t.getLeft());  
      toReturn += t.getValue() + " ";               
      toReturn += inorderTraverse(t.getRight());  
      return toReturn; 
   }
   
   public String preorderTraverse()
   {
      return preorderTraverse(root);
   }
   
   private String preorderTraverse(TreeNode root)
   {
      String toReturn = "";
      if(root == null)
         return "";
      toReturn += root.getValue() + " ";              //preorder visit
      toReturn += preorderTraverse(root.getLeft());   //recurse left
      toReturn += preorderTraverse(root.getRight());  //recurse right
      return toReturn;
   
   }
   
  
   public String inorderTraverseWithParentheses()
   {
      return inorderTraverseWithParentheses(root);
   }
    
   private String inorderTraverseWithParentheses(TreeNode t)
   {
      return "";
   }
}