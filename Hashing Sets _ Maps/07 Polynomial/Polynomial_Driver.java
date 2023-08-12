 // Name:  j126  
 // Date:  3/16

import java.util.*;

public class Polynomial_Driver
{
   public static void main(String[] args)
   {
      Polynomial poly = new Polynomial();    // 2x^3 + -4x + 2
      poly.makeTerm(1, -4);
      poly.makeTerm(3, 2);
      poly.makeTerm(0, 2);
      System.out.println("Map:  " + poly.getMap());
      System.out.println("String:  " + poly.toString());
      double evaluateAt = 2.0;
      System.out.println("Evaluated at "+ evaluateAt +": " +poly.evaluateAt(evaluateAt));
      
      System.out.println("-----------");
      Polynomial poly2 = new Polynomial();  // 2x^4 + x^2 + -5x + -3
      poly2.makeTerm(1, -5);
      poly2.makeTerm(4, 2);
      poly2.makeTerm(0, -3);
      poly2.makeTerm(2, 1);
      System.out.println("Map:  " + poly2.getMap()); 
      System.out.println("String:  " +poly2.toString());
      evaluateAt = -10.5;
      System.out.println("Evaluated at "+ evaluateAt +": " +poly2.evaluateAt(evaluateAt));
      
      
      System.out.println("-----------");
      System.out.println("Sum: " + poly.add(poly2));
      System.out.println("Product:  " + poly.multiply(poly2));
      
      /*  Another case:   (x+1)(x-1) -->  x^2 + -1    */
      // System.out.println("===========================");
      // Polynomial poly3 = new Polynomial();   // (x+1)
      // poly3.makeTerm(1, 1);
      // poly3.makeTerm(0, 1);
      // System.out.println("Map:  " + poly3.getMap());
      // System.out.println("String:  " + poly3.toString());
   //       
      // Polynomial poly4 = new Polynomial();    // (x-1)
      // poly4.makeTerm(1, 1);
      // poly4.makeTerm(0, -1);
      // System.out.println("Map:  " + poly4.getMap());
      // System.out.println("String:  " + poly4.toString());
      // System.out.println("Product:  " + poly4.multiply(poly3));   // x^2 + -1 
   //    
   //    /*  testing the one-arg constructor  */
      // System.out.println("==========================="); 
      // Polynomial poly5 = new Polynomial("2x^3 + 4x^2 + 6x^1 + -3");
      // System.out.println("Map:  " + poly5.getMap());  
      // System.out.println(poly5);
   
   }
}
interface PolynomialInterface
{
   public void makeTerm(Integer exp, Integer coef); //replace term
   public Map<Integer, Integer> getMap();
   public double evaluateAt(double x);
   public Polynomial add(Polynomial other); //think about merge/zipper sort
   public Polynomial multiply(Polynomial other);
   public String toString();
}

class Polynomial implements PolynomialInterface
{
   private Map<Integer, Integer> poly;
   public Polynomial()
   {
      poly = new TreeMap<Integer, Integer>();
   }
   public void makeTerm(Integer exp, Integer coef)
   {
      poly.put(exp, coef);
   }
   public Map<Integer, Integer> getMap()
   {
      return poly;
   }
   public double evaluateAt(double x)
   {
      double eval = 0.0;
      for(Integer exp: poly.keySet())
      {
         eval += poly.get(exp)*Math.pow(x, exp);
      }
      return eval;
   }
   public Polynomial add(Polynomial other) 
   {
      Polynomial add = new Polynomial();
      Map<Integer,Integer> sum = add.getMap();
      Map<Integer,Integer> oth = other.getMap();
   
      for(Integer exp: poly.keySet())
      {
         if(oth.containsKey(exp)) //both have exponent
            sum.put(exp, poly.get(exp) + oth.get(exp));
         else //only poly has exponent
            sum.put(exp, poly.get(exp));  
      }
      for(Integer exp: oth.keySet())
      {
         if(poly.containsKey(exp))
         {
         //both have alr been handled
         } 
         else //only other has exponent
            sum.put(exp, oth.get(exp));  
      }
      return add;
   }
   public Polynomial multiply(Polynomial other)
   {
      Polynomial mult = new Polynomial();
      Map<Integer,Integer> times = mult.getMap();
      Map<Integer,Integer> oth = other.getMap();
   
      for(Integer exp1: poly.keySet())
      { 
         for(Integer exp2: oth.keySet())
         {
            if(times.containsKey(exp1+exp2)) // repeating
               times.put(exp1 + exp2, times.get(exp1 + exp2) + poly.get(exp1)*oth.get(exp2));
            else
               times.put(exp1 + exp2, poly.get(exp1) * oth.get(exp2));
         }
      }
   
      return mult;
   }
   public String toString()
   {
      String s = "";
      Map<Integer,Integer> print = new TreeMap<Integer,Integer>(Collections.reverseOrder());
      
      for(Integer exp: poly.keySet())
      {
         print.put(exp, poly.get(exp));
      }
      for(Integer exp: print.keySet())
      {
         if(exp == 0)
            s += print.get(exp);
         else if (print.get(exp) == 0)
            s += " ";
         else if (print.get(exp) == 1)
         {
            s += "x^"+exp;
         }
         else if(exp == 1)
         {
            if(print.get(exp) == 1)
               s += "x";
            else
               s += print.get(exp) + "x";
         }
         else
            s += print.get(exp)+"x^"+exp;
         s += " + ";
      }
      s = s.substring(0, s.length()-2);
      return s;
   }

}


/***************************************  
  ----jGRASP exec: java Polynomial_teacher
 Map:  {0=2, 1=-4, 3=2}
 String:  2x^3 + -4x + 2
 Evaluated at 2.0: 10.0
 -----------
 Map:  {0=-3, 1=-5, 2=1, 4=2}
 String:  2x^4 + x^2 + -5x + -3
 Evaluated at -10.5: 24469.875
 -----------
 Sum: 2x^4 + 2x^3 + x^2 + -9x + -1
 Product:  4x^7 + -6x^5 + -6x^4 + -10x^3 + 22x^2 + 2x + -6
 
  ----jGRASP: operation complete.
 ********************************************/