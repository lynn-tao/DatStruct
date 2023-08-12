// Name: j126 
// Date: 4/20
 
import java.util.*;
import java.io.*;

/* Resource classes and interfaces
 * for use with Graph0 AdjMat_0_Driver,
 *              Graph1 WarshallDriver,
 *          and Graph2 FloydDriver
 */

interface AdjacencyMatrix
{
   void addEdge(int source, int target);
   void removeEdge(int source, int target);
   boolean isEdge(int from, int to);
   String toString();   //returns the grid as a String
   int edgeCount();
   List<Integer> getNeighbors(int source);
   //public List<String> getReachables(String from);  //Warshall extension
}

interface Warshall      //User-friendly functionality
{
   boolean isEdge(String from, String to);
   Map<String, Integer> getVertices();     
   void readNames(String fileName) throws FileNotFoundException;
   void readGrid(String fileName) throws FileNotFoundException;
   void displayVertices();
   void allPairsReachability();  // Warshall's Algorithm
}

interface Floyd
{
   int getCost(int from, int to);
   int getCost(String from, String to);
   void allPairsWeighted(); 
}

public class AdjMat implements AdjacencyMatrix, Warshall, Floyd 
{
   private int[][] grid = null;   //adjacency matrix representation
   private Map<String, Integer> vertices = null;   // name maps to index (for Warshall & Floyd)
   /*for Warshall's Extension*/  ArrayList<String> nameList = null;  //reverses the map, index-->name
	  
   public AdjMat(int nodes)
   {
      grid = new int[nodes][nodes];
      for(int i=0; i<nodes; i++)
         for(int j=0; j<nodes; j++)
            grid[i][j] = 0;
   }
  //Floyd Methods
   public int getCost(int from, int to)
   {
      int cost = grid[from][to];
      return cost;
   }
   public int getCost(String from, String to)
   {
      int cost = grid[vertices.get(from)][vertices.get(to)];
      return cost; 
   }
   //Floyd Alg 
   public void allPairsWeighted()
   {
      for(int v=0; v<grid.length; v++)
         for(int i=0; i<grid.length; i++)
            for(int j=0; j<grid[i].length; j++)
            {
               if(getCost(i, v) + getCost(v, j) < getCost(i, j))
                  grid[i][j] = getCost(i, v) + getCost(v, j);
            }
   } 
   //Warshall Methods
   public boolean isEdge(String from, String to)
   {
      return grid[vertices.get(from)][vertices.get(to)] == 1;   
   }
   public Map<String, Integer> getVertices()
   {
      return vertices;
   }
   public void readNames(String fileName) throws FileNotFoundException
   {
      Scanner sc = new Scanner(new File(fileName));
      int x = sc.nextInt();
      vertices = new TreeMap<String, Integer>();
      for(int i=0; i<grid.length; i++)
      {
         String c = sc.next();
         vertices.put(c, i); 
      }  
   }
   public void readGrid(String fileName) throws FileNotFoundException
   {
      Scanner sc = new Scanner(new File(fileName));
      int size = sc.nextInt();
      grid = new int[size][size];
      for(int i=0; i<size; i++)
         for(int j=0; j<size; j++)
            grid[i][j] = sc.nextInt();
   }
   public void displayVertices()
   {
      for(String str : vertices.keySet())
         System.out.println(""+vertices.get(str)+"-"+str);
      System.out.println();
   }
   //Warshall Alg 
   public void allPairsReachability()
   {
      for(int v=0; v<grid.length; v++)
         for(int i=0; i<grid.length; i++)
            for(int j=0; j<grid[i].length; j++)
            {
               if(isEdge(i, v) && isEdge(v, j))
                  grid[i][j] = 1;
            }
   }
   //Adj Code
   public void addEdge(int source, int target)
   {
      grid[source][target] = 1;
   }
   public void removeEdge(int source, int target)
   {
      grid[source][target] = 0;
   }
   public boolean isEdge(int from, int to)
   {
      return grid[from][to] == 1;
   }
   public String toString()  
   {
      String graph = "";
      for(int i=0; i<grid.length; i++)
      {
         for(int j=0; j<grid[i].length; j++)
         {
            graph += grid[i][j]+" ";
         }
         graph += "\n";
      }
      return graph.trim();
   }   
   /* Floyd edgeCount()
   public int edgeCount()
   {
      System.out.println();
      int edge = 0;
      for(int i=0; i<grid.length; i++)
         for(int j=0; j<grid[i].length; j++)
            if(grid[i][j] != 9999 && grid[i][j] != 0) 
               edge++;
      return edge;
   }*/
   public int edgeCount()
   {
      System.out.println();
      int edge = 0;
      for(int i=0; i<grid.length; i++)
         for(int j=0; j<grid[i].length; j++)
            if(grid[i][j] == 1) 
               edge++;
      return edge;
   }
   public List<Integer> getNeighbors(int source)
   {
      List<Integer> arr = new ArrayList<Integer>();
      for(int j=0; j<grid[source].length; j++)
         if(grid[source][j] ==1) 
            arr.add(j);
               
      return arr;
   }   
}
