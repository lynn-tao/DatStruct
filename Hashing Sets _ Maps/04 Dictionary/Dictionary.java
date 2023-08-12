// Name: j126
// Date: 3/11

import java.io.*;
import java.util.*;

public class Dictionary
{
   public static void main(String[] args) 
   {
      Scanner infile = null;
      try
      {
         infile = new Scanner(new File("spanglish.txt"));
         System.setOut(new PrintStream(new FileOutputStream("dictionaryOutput.txt")));
      }
      catch(Exception e)
      {
      }
      
      Map<String, Set<String>> eng2spn = makeDictionary( infile );
      System.out.println("ENGLISH TO SPANISH");
      display(eng2spn);
   
      Map<String, Set<String>> spn2eng = reverse(eng2spn);
      System.out.println("SPANISH TO ENGLISH");
      display(spn2eng);
   }
   
   public static Map<String, Set<String>> makeDictionary(Scanner infile)
   {
      Map<String, Set<String>> eng2spn = new TreeMap<String, Set<String>>();
      
      while(infile.hasNext())
         add(eng2spn, infile.next(), infile.next());
      
      return eng2spn;
      
   }
   
   public static void add(Map<String, Set<String>> dictionary, String word, String translation)
   { 
      if(dictionary.containsKey(word))
      {
         dictionary.get(word).add(translation);
      }
      else
      {
         Set<String> set = new TreeSet<String>();
         set.add(translation);
         dictionary.put(word, set);
      }
     
   }
   
   public static void display(Map<String, Set<String>> m)
   {
      for(String s: m.keySet())
      {
         System.out.println("  " + s + " " + m.get(s));
      }
      System.out.println();
   }
   
   public static Map<String, Set<String>> reverse(Map<String, Set<String>> dictionary)
   {
      Map<String, Set<String>> spn2eng = new TreeMap<String, Set<String>>();
   
      for(String str : dictionary.keySet())
      {
         Iterator<String> it = dictionary.get(str).iterator();
         while(it.hasNext())
         {
            String s = it.next();
            if(spn2eng.containsKey(s))
            {
               Set<String> set = spn2eng.get(s);
               set.add(str);
               spn2eng.put(s, set);
            }
            else
            {
               Set<String> set = new TreeSet<String>();
               set.add(str);
               spn2eng.put(s, set);
            }
         }
      }
      
      return spn2eng;
   }
}


   /********************
	INPUT:
   	holiday
		fiesta
		holiday
		vacaciones
		party
		fiesta
		celebration
		fiesta
     <etc.>
  *********************************** 
	OUTPUT:
		ENGLISH TO SPANISH
			banana [banana]
			celebration [fiesta]
			computer [computadora, ordenador]
			double [doblar, doble, duplicar]
			father [padre]
			feast [fiesta]
			good [bueno]
			hand [mano]
			hello [hola]
			holiday [fiesta, vacaciones]
			party [fiesta]
			plaza [plaza]
			priest [padre]
			program [programa, programar]
			sleep [dormir]
			son [hijo]
			sun [sol]
			vacation [vacaciones]

		SPANISH TO ENGLISH
			banana [banana]
			bueno [good]
			computadora [computer]
			doblar [double]
			doble [double]
			dormir [sleep]
			duplicar [double]
			fiesta [celebration, feast, holiday, party]
			hijo [son]
			hola [hello]
			mano [hand]
			ordenador [computer]
			padre [father, priest]
			plaza [plaza]
			programa [program]
			programar [program]
			sol [sun]
			vacaciones [holiday, vacation]

**********************/