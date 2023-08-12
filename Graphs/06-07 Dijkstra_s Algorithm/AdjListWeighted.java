// Name:  j126 
// Date:  5/20
 
import java.util.*;
import java.io.*;

/* Resource classes and interfaces
 * for use with Graphs6: Dijkstra
 *              Graphs7: Dijkstra with Cities
 */

class Edge 
{
   public final wVertex target;  //if it's public, you don't need accessor methods
   public final double weight;   //if it's public, you don't need accessor methods
  
   public Edge(wVertex argTarget, double argWeight) 
   {
      target = argTarget;
      weight = argWeight;
   }
}

interface wVertexInterface 
{
   String getName();
   double getMinDistance();
   void setMinDistance(double m);
   //wVertex getPrevious();   //for Dijkstra 7
   //void setPrevious(wVertex v);  //for Dijkstra 7
   ArrayList<Edge> getAdjacencies();
   void addEdge(wVertex v, double weight);   
   int compareTo(wVertex other);
}

class wVertex implements Comparable<wVertex>, wVertexInterface
{
   private final String name;
   private ArrayList<Edge> adjacencies;
   private double minDistance = Double.POSITIVE_INFINITY;
   //private wVertex previous;  //for building the actual path in Dijkstra 7
   
   public wVertex(String s)
   {
      name = s;
      adjacencies = new ArrayList<Edge>(); 
   }
   public double getMinDistance()
   {
      return minDistance;
   }
   public void setMinDistance(double m)
   {
      minDistance = m;
   }
   public String getName()
   {
      return name; 
   }
   public void addEdge(wVertex v, double weight)
   {
      adjacencies.add(new Edge(v, weight));
   }  
   public ArrayList<Edge> getAdjacencies()
   {
      return adjacencies;
   }
   public int compareTo(wVertex other)
   {
      if(getMinDistance() <  other.getMinDistance())
         return -1;
      else if (getMinDistance() == other.getMinDistance())
         return 0;
      else
         return 1;
   }  
   public String toString()
   {
      String s = "";
      /*for(int i=0; i<adjacencies.size(); i++)
         s+= adjacencies.get(i).target + "|" + adjacencies.get(i).weight + " ";*/
      
      return ""+name+" "+ s;
   }   
}

interface AdjListWeightedInterface 
{
   List<wVertex> getVertices();
   Map<String, Integer> getNameToIndex();
   wVertex getVertex(int i);   
   wVertex getVertex(String vertexName);
   void addVertex(String v);
   void addEdge(String source, String target, double weight);
   void minimumWeightPath(String vertexName);   //Dijkstra's
}

/* Interface for Graphs 7:  Dijkstra with Cities 
 */

interface AdjListWeightedInterfaceWithCities 
{       
   List<String> getShortestPathTo(wVertex v);
   AdjListWeighted graphFromEdgeListData(File vertexNames, File edgeListData) throws FileNotFoundException ;
}
 

public class AdjListWeighted implements AdjListWeightedInterface //,AdjListWeightedInterfaceWithCities
{
   private List<wVertex> vertices = new ArrayList<wVertex>();
   private Map<String, Integer> nameToIndex = new HashMap<String, Integer>();
   //the constructor is a no-arg constructor 
  
   /*  enter your code for Graphs 6 */ 
   public List<wVertex> getVertices()
   {
      return vertices;
   }
   public Map<String, Integer> getNameToIndex()
   {
      return nameToIndex;
   }
   public wVertex getVertex(int i)
   {
      return vertices.get(i);
   }
   public wVertex getVertex(String vertexName)
   {
      return vertices.get(nameToIndex.get(vertexName));
   }
   public void addVertex(String v)
   {
      if(!nameToIndex.containsKey(v))
      {
         nameToIndex.put(v, vertices.size());
         vertices.add(new wVertex(v));
      }
   }
   public void addEdge(String source, String target, double weight)
   {
      vertices.get(nameToIndex.get(source)).addEdge(vertices.get(nameToIndex.get(target)), weight);
   }
   public void minimumWeightPath(String vertexName)
   {
      wVertex source = getVertex(vertexName);
      source.setMinDistance(0);
               
      PriorityQueue<wVertex> sort = new PriorityQueue<wVertex>();
      sort.add(source); 
      
      while(!sort.isEmpty())
      {
         wVertex curr = sort.remove();
         ArrayList<Edge> copy = curr.getAdjacencies();
         for(int i=0; i<copy.size(); i++)
            if(copy.get(i).weight < copy.get(i).target.getMinDistance())
            {
               copy.get(i).target.setMinDistance(curr.getMinDistance() + copy.get(i).weight);
               
               if(sort.peek() == copy.get(i).target)
                  sort.remove();
               
               sort.add(copy.get(i).target);
               System.out.println(copy.get(i).target.getMinDistance());
            }
       
      }   
      
   } 
   
  
   
   
   /*  enter your code for two new methods in Graphs 7 */
   
   
}   


