// Name: J126
// Date:10/20

import java.util.*;
import java.io.*;

public class MazeMaster
{
   public static void main(String[] args)
   {
      Scanner sc = new Scanner(System.in);
      System.out.print("Enter the maze's filename (no .txt): ");
      Maze m = new Maze(sc.next()+".txt");
      // Maze m = new Maze();    //extension
      m.display();      
      System.out.println("Options: ");
      System.out.println("1: Mark all dots.");
      System.out.println("2: Mark all dots and display the number of recursive calls.");
      System.out.println("3: Mark only the correct path.");
      System.out.println("4: Mark only the correct path. If no path exists, say so.");
      System.out.println("5: Mark only the correct path and display the number of steps.\n\tIf no path exists, say so.");
      System.out.print("Please make a selection: ");
      m.solve(sc.nextInt());
      m.display();      //display solved maze
   } 
}

class Maze
{
   //constants
   private final char WALL = 'W';
   private final char DOT = '.';
   private final char START = 'S';
   private final char EXIT = 'E';
   private final char TEMP = 'o';
   private final char PATH = '*';
   //instance fields
   private char[][] maze;
   private int startRow, startCol;
  
   //constructors
	
	/* 
	 * EXTENSION 
	 * This is a no-arg constructor that generates a random maze
	 */
   public Maze()
   {
   
   }
	
	/* 
	 * Copy Constructor  
	 */
   public Maze(char[][] m)  
   {
      maze = m;
      for(int r = 0; r < maze.length; r++)
      {
         for(int c = 0; c < maze[0].length; c++)
         { 
            if(maze[r][c] == START)      //identify start location
            {
               startRow = r;
               startCol = c;
            }
         }
      }
   } 
	
	/* 
	 * Use a try-catch block
	 * Use next(), not nextLine()  
	 */
   public Maze(String filename)    
   {
      Scanner infile = null;
      try
      {
         infile = new Scanner(new File(filename));
      }
      catch (Exception e)
      {
         System.out.println("File not found");
      }
      int rows = infile.nextInt();
      int column = infile.nextInt();
      maze = new char[rows][column];  
      
      infile.nextLine();
      for(int i=0; i<maze.length; i++) //row
      {
         String line = infile.nextLine();
         char[] x = line.toCharArray();
         for(int l = 0; l<x.length; l++)
            maze[i][l]=x[l];
      }
      
      for(int r = 0; r < maze.length; r++)
      {
         for(int c = 0; c < maze[0].length; c++)
         { 
            if(maze[r][c] == START)      
            {
               startRow = r;
               startCol = c;
            }
         }
      }
   }
   
   public char[][] getMaze()
   {
      return maze;
   }
   
   public void display()
   {
      if(maze==null) 
         return;
      for(int a = 0; a<maze.length; a++)
      {
         for(int b = 0; b<maze[0].length; b++)
         {
            System.out.print(maze[a][b]);
         }
         System.out.println();
      }
      System.out.println();
   }
   
   public void solve(int n)
   {
      switch(n)
      {
         case 1:
         {
            markAll(startRow, startCol);
            break;
         }
         case 2:
         {
            int count = markAllAndCountRecursions(startRow, startCol);
            System.out.println("Number of recursions = " + count);
            break;
         }
         case 3:
         {
            markTheCorrectPath(startRow, startCol);
            break;
         }
         case 4:         //use mazeNoPath.txt 
         {
            if( !markTheCorrectPath(startRow, startCol) )
               System.out.println("No path exists."); 
            break;
         }
         case 5:
         {
            if( !markCorrectPathAndCountSteps(startRow, startCol, 0) )
               System.out.println("No path exists."); 
            break;
         }
         default:
            System.out.println("File not found");   
      }
   }
   
	/* 
	 * From handout, #1.
	 * Fill the maze, mark every step.
	 * This is a lot like AreaFill.
	 */ 
   public void markAll(int r, int c)
   {
      if(r<0 || r>= maze.length || c<0 || c>=maze[0].length || maze[r][c] != START && maze[r][c] != DOT)
      {
         return;
      }
      else
      {
         if(maze[r][c]!=START)
            maze[r][c] = PATH;
         markAll(r-1, c);
         markAll(r, c+1);
         markAll(r+1, c);
         markAll(r, c-1);
      }
   }

	/* 
	 * From handout, #2.
	 * Fill the maze, mark and count every recursive call as you go.
	 * Like AreaFill's counting without a static variable.
	 */ 
   public int markAllAndCountRecursions(int r, int c)
   {
      if(r<0 || r>= maze.length || c<0 || c>=maze[0].length || maze[r][c] != START && maze[r][c] != DOT)
      {
         return 1;
      }
      else
      {
         if(maze[r][c]!=START)
            maze[r][c] = PATH;
         return 1+ markAllAndCountRecursions(r-1, c) + markAllAndCountRecursions(r, c+1)+ markAllAndCountRecursions(r+1, c) + markAllAndCountRecursions(r, c-1);
      }
   }

   /* 
	 * From handout, #3.
	 * Solve the maze, OR the booleans, and mark the path through it with
	 * Recur until you find E, then mark the True path.
	 */ 	
   public boolean markTheCorrectPath(int r, int c)
   {
      if(r<0 || r>= maze.length || c<0 || c>=maze[0].length || maze[r][c] == WALL || maze[r][c] == TEMP)
      {
         return false;
      }
      if(maze[r][c] == EXIT)
         return true;
      else
      {
         if(maze[r][c]!=START)
            maze[r][c] = TEMP;
         if(markTheCorrectPath(r-1, c) == false && markTheCorrectPath(r, c+1) == false && markTheCorrectPath(r+1, c) == false && markTheCorrectPath(r, c-1) == false)
         {
            if(maze[r][c]!=START)
               maze[r][c]=DOT;
         }
         else
         {
            if(maze[r][c]!=START)
               maze[r][c]='*';
            return true;
         }
      }
      return false;
   }
	
	
   /*  4   Mark only the correct path. If no path exists, say so.
           Hint:  the method above returns the boolean that you need. */
      

   /* 
	 * From handout, #5.
	 * Solve the maze, mark the path, count the steps. 	 
	 * Mark only the correct path and display the number of steps.
	 * If no path exists, say so.
	 */ 	
   public boolean markCorrectPathAndCountSteps(int r, int c, int count)
   {
      if(r<0 || r>= maze.length || c<0 || c>=maze[0].length || maze[r][c] == WALL || maze[r][c] == TEMP)
      { 
         return false;
      }
      if(maze[r][c] == EXIT)
      {
         System.out.println(count);
         return true;
      }
      else
      {
         ++count;
         if(maze[r][c]!=START)
            maze[r][c] = TEMP;
         if(markCorrectPathAndCountSteps(r-1, c, count) == false && markCorrectPathAndCountSteps(r, c+1, count) == false && markCorrectPathAndCountSteps(r+1, c, count) == false && markCorrectPathAndCountSteps(r, c-1, count) == false)
         {
            if(maze[r][c]!=START)
               maze[r][c]=DOT;
         }
         else
         {
            if(maze[r][c]!=START)
            {
               maze[r][c]=PATH;
            }
            return true;
         }
      }
      return false;
   }
}

/*****************************************
 
 ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, say so.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, say so.
 Please make a selection: 1
 WWWWWWWW
 W****W*W
 WW*WW**W
 W****W*W
 W*W*WW*E
 S*W*WW*W
 WW*****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, say so.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, say so.
 Please make a selection: 2
 Number of recursions = 105
 WWWWWWWW
 W****W*W
 WW*WW**W
 W****W*W
 W*W*WW*E
 S*W*WW*W
 WW*****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, say so.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, say so.
 Please make a selection: 3
 WWWWWWWW
 W....W.W
 WW.WW..W
 W***.W.W
 W*W*WW*E
 S*W*WW*W
 WW.****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
     
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): mazeNoPath
 WWWWWWWW
 W....W.W
 WW.WW..E
 W..WW.WW
 W.W.W..W
 S.W.WW.W
 WWW....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, say so.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, say so.
 Please make a selection: 4
 No path exists.
 WWWWWWWW
 W....W.W
 WW.WW..E
 W..WW.WW
 W.W.W..W
 S.W.WW.W
 WWW....W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, say so.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, say so.
 Please make a selection: 5
 Number of steps = 14
 WWWWWWWW
 W....W.W
 WW.WW..W
 W***.W.W
 W*W*WW*E
 S*W*WW*W
 WW.****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
  ********************************************/