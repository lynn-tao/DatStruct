// Name: j126
// Date: 4/29
 
import java.util.*;
import java.io.*;

/* Resource classes and interfaces
 * for use with Graphs3: EdgeList,
 *              Graphs4: DFS-BFS
 *          and Graphs5: EdgeListCities
 */

/* Graphs 3: EdgeList 
 */
interface VertexInterface
{
   String toString(); // Don't use commas in the list.  Example: "C [C D]"
   String getName();
   ArrayList<Vertex> getAdjacencies();
   void addAdjacent(Vertex v);
} 

class Vertex implements VertexInterface 
{
   private final String name;
   private ArrayList<Vertex> adjacencies;
  
   public Vertex(String s)
   {
      name = s;
      adjacencies = new ArrayList<Vertex>();
   }
   public String toString() // Don't use commas in the list.  Example: "C [C D]"
   {
      String s = "[";
      for(int i=0; i<adjacencies.size(); i++)
         s+= adjacencies.get(i).getName() + " ";
      if(!s.substring(s.length()-1,s.length()).equals("["))
         s = s.substring(0, s.length()-1);
      s += "]";
      
      return ""+name+" "+ s;
   } 
   public String getName()
   {
      return name; 
   }
   public ArrayList<Vertex> getAdjacencies()
   {
      return adjacencies;
   }
   public void addAdjacent(Vertex v)
   {
      adjacencies.add(v);
   } 
}   

interface AdjListInterface 
{ 
   List<Vertex> getVertices();
   Vertex getVertex(int i) ;
   Vertex getVertex(String vertexName);
   Map<String, Integer> getVertexMap();
   void addVertex(String v);
   void addEdge(String source, String target);
   String toString();  //returns all vertices with their edges (omit commas)
}

  
/* Graphs 4: DFS and BFS 
 */
interface DFS_BFS
{
   List<Vertex> depthFirstSearch(String name);
   List<Vertex> depthFirstSearch(Vertex v);
   List<Vertex> breadthFirstSearch(String name);
   List<Vertex> breadthFirstSearch(Vertex v);
   /*  three extra credit methods */
   List<Vertex> depthFirstRecur(String name);
   List<Vertex> depthFirstRecur(Vertex v);
   void depthFirstRecurHelper(Vertex v, ArrayList<Vertex> reachable);
}

/* Graphs 5: Edgelist with Cities 
 */
interface EdgeListWithCities
{
   void graphFromEdgeListData(String fileName) throws FileNotFoundException;
   int edgeCount();
   int vertexCount(); //count the vertices in the object
   boolean isReachable(String source, String target);
   boolean isConnected();
}


public class AdjList implements AdjListInterface, DFS_BFS, EdgeListWithCities
{
   private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
   private Map<String, Integer> nameToIndex = new TreeMap<String, Integer>();
   
  
   public AdjList()
   {
      super();
   }
   public List<Vertex> getVertices()
   {
      return vertices;
   }
   public Vertex getVertex(int i)
   {
      return vertices.get(i);
   }
   public Vertex getVertex(String vertexName)
   {
      return vertices.get(nameToIndex.get(vertexName));
   }
   public Map<String, Integer> getVertexMap()
   {
      return nameToIndex;
   }
   public void addVertex(String v)
   {
      if(!nameToIndex.containsKey(v))
      {
         nameToIndex.put(v, vertices.size());
         vertices.add(new Vertex(v));
      }
   }
   public void addEdge(String source, String target)
   {
      if(!nameToIndex.containsKey(source))
      {
         Vertex ver = new Vertex(source);
         nameToIndex.put(source, vertices.size());
         vertices.add(ver);
      }
      if(!nameToIndex.containsKey(target))
      {
         Vertex ver = new Vertex(target);
         nameToIndex.put(target, vertices.size());
         vertices.add(ver);
      }
      vertices.get(nameToIndex.get(source)).addAdjacent(vertices.get(nameToIndex.get(target)));
   }
   public String toString()
   {
      String s = "";
      for(int i=0; i<vertices.size(); i++)
         s+= vertices.get(i) + "\n";
      
      return s;
   }
   public List<Vertex> depthFirstSearch(String name)
   {
      return depthFirstSearch(vertices.get(nameToIndex.get(name)));
   }
   public List<Vertex> depthFirstSearch(Vertex v)
   {
      List<Vertex> reachable = new ArrayList<Vertex>();
      Stack<Vertex> temp = new Stack<Vertex>();
      
      temp.push(v);
      reachable.add(temp.pop());
      
     
      for(int i = 0; i < v.getAdjacencies().size(); i++)
         temp.push(v.getAdjacencies().get(i));
      
      while(!temp.isEmpty())
      {
         Vertex copy = temp.pop();
         if(!reachable.contains(copy))
         {
            reachable.add(copy);
            for(int i = 0; i < copy.getAdjacencies().size(); i++)
               temp.push(copy.getAdjacencies().get(i));
         }
      } 
      return reachable;
   }
   public List<Vertex> breadthFirstSearch(String name)
   {
      return breadthFirstSearch(vertices.get(nameToIndex.get(name)));
   }
   public List<Vertex> breadthFirstSearch(Vertex v)
   {
      List<Vertex> reachable = new ArrayList<Vertex>();
      Queue<Vertex> temp = new LinkedList<Vertex>();
      
      temp.add(v);
      reachable.add(temp.remove());
      
      for(int i = 0; i < v.getAdjacencies().size(); i++)
         temp.add(v.getAdjacencies().get(i));
      
      while(!temp.isEmpty())
      {
         Vertex copy = temp.remove();
         if(!reachable.contains(copy))
         {
            reachable.add(copy);
            for(int i = 0; i < copy.getAdjacencies().size(); i++)
               temp.add(copy.getAdjacencies().get(i));
         }
      } 
        
   
      return reachable;
   }
   public void graphFromEdgeListData(String fileName) throws FileNotFoundException
   {
      Scanner s = new Scanner(new File(fileName));
      while(s.hasNext())
      {
         String str = s.next();
         addVertex(str);
         addEdge(str, s.next());
      }
   }
   public int edgeCount()
   {
      int edges = 0;
      for(int i=0; i<vertices.size(); i++)
         edges+=vertices.get(i).getAdjacencies().size();
      return edges;
   }
   public int vertexCount()
   {
      return vertices.size();
   }
   public boolean isReachable(String source, String target)
   { 
      List<Vertex> copy = depthFirstSearch(vertices.get(nameToIndex.get(source)));
      return copy.contains(vertices.get(nameToIndex.get(target)));
   }
   public boolean isConnected()
   {
      boolean connect = true;
      for(int i=0; i<vertices.size(); i++)
         for(int j=i; j<vertices.size(); j++)
            if(!isReachable(vertices.get(i).getName(), vertices.get(j).getName()))
               connect = false;
             
   
      return connect;
   }
   
   
   
   
   
     /*  three extra credit methods, recursive version  */
   public List<Vertex> depthFirstRecur(String name)
   {
      return null;
   }
   
   public List<Vertex> depthFirstRecur(Vertex v)
   {
      return null;
   }
   
   public void depthFirstRecurHelper(Vertex v, ArrayList<Vertex> reachable)
   {
      
   }   
}


