package uk.ac.cam.pv273.tick4;

import java.io.IOException;
import java.util.List;

public class LoaderLife
{
  public static boolean getCell(boolean[][] world, int col, int row)
  {
    if (row < 0 || row > world.length - 1) return false;
    if (col < 0 || col > world[row].length - 1) return false;
    return world[row][col];
  }
  
  public static void setCell(boolean[][] world, int col, int row, boolean value)
  {
    if (row < 0 || row > world.length - 1) return;
    if (col < 0 || col > world[row].length - 1) return;
    world[row][col] = value;
  }

  public static int countNeighbours(boolean[][] world, int col, int row)
  {
    int dx[] = {1, 0, -1, 0, 1, -1, 1, -1};
    int dy[] = {0, 1, 0, -1, 1, -1, -1, 1};
    int ret = 0;
    for (int i=0;i<8;i++)
    {
      int rowNew = row + dx[i];
      int colNew = col + dy[i];
      if (getCell(world,colNew,rowNew)) ret++;
    }
    return ret;
  }

  public static boolean computeCell(boolean[][] world, int col, int row)
  {
    // liveCell is true if the cell at position (col,row) in world is live
    boolean liveCell = getCell(world, col, row);
    
    // neighbours is the number of live neighbours to cell (col,row)
    int neighbours = countNeighbours(world, col, row);
    
    // we will return this value at the end of the method to indicate whether
    // cell (col,row) should be live in the next generation
    boolean nextCell = false;
   
    // A live cell with less than two neighbours dies (underpopulation)
    if (neighbours < 2) nextCell = false;
    
    // A live cell with two or three neighbours lives (a balanced population)
    if (liveCell && (neighbours == 2 || neighbours == 3)) nextCell = true;
 
    // A live cell with more than three neighbours dies (overcrowding)
    if (liveCell && neighbours > 3) nextCell = false;
    
    // A dead cell with exactly three live neighbours comes alive
    if (!liveCell && neighbours == 3) nextCell = true;
    
    return nextCell;
  }

  public static boolean[][] nextGeneration(boolean[][] world)
  {
    boolean[][] retWorld = new boolean[world.length][world[0].length];
    for (int i=0;i<world.length;i++)
    {
      for (int j=0;j<world[i].length;j++)
      {
        setCell(retWorld,j,i,computeCell(world,j,i));
      }  
    }
    return retWorld;
  }

  public static void print(boolean[][] world)
  {
    System.out.println("-");
    for (int row=0;row<world.length;row++) 
    {
      for (int col=0;col<world[row].length;col++)
      {
        System.out.print(getCell(world,col,row) ? "#" : "_");
      }
      System.out.println();
    }
  }

  public static void play(boolean[][] world) throws Exception
  {
    int userResponse = 0;
    while (userResponse != 'q')
    {
      print(world);
      userResponse = System.in.read();
      world = nextGeneration(world);
    }
  }

  public static void main(String[] args) throws Exception
  {
    try
    {
      String addr;
      int ind = -1;
      addr = args[0];
      if (args.length > 1) ind = Integer.parseInt(args[1]);
      try
      {
        List<Pattern> ret = PatternLoader.loadFromURL(addr);
        int i = 0;
        for (Pattern p:ret)
        {
          if (ind != -1) 
          {
            if (i == ind) 
            {
              try
              {
                boolean world[][] = new boolean[p.getHeight()][p.getWidth()];
                p.initialise(world);
                play(world);
              }
              catch (ArrayIndexOutOfBoundsException e)
            {
      	        System.out.println("Error: No pattern entered");
              }
              catch (PatternFormatException e)
              {
                System.out.println(e.getMessage()); 
              }
            }
          }
          else System.out.println(i + ") "+p.getName()+":"+p.getAuthor()+":"+p.getWidth()+":"+p.getHeight()+":"+p.getStartCol()+":"+p.getStartRow()+":"+p.getCells());
          i++;
        }
      }
      catch (IOException ei1)
      {
        try
        {
          List<Pattern> ret = PatternLoader.loadFromDisk(addr);
          int i = 0;
          for (Pattern p:ret)
          {
            if (ind != -1) 
            {
              if (i == ind) 
              {
                try
                {
                  boolean world[][] = new boolean[p.getHeight()][p.getWidth()];
                  p.initialise(world);
                  play(world);
                }
                catch (ArrayIndexOutOfBoundsException e)
      	        {
      	          System.out.println("Error: No pattern entered");
                }
                catch (PatternFormatException e)
                {
                  System.out.println(e.getMessage()); 
                }
              }
            }
            else System.out.println(i + ") "+p.getName()+":"+p.getAuthor()+":"+p.getWidth()+":"+p.getHeight()+":"+p.getStartCol()+":"+p.getStartRow()+":"+p.getCells());
            i++;
          }
        }
        catch (IOException ei2)
        {
          System.out.println("Error: Incorrect URL / File name entered");
        }
      }
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      System.out.println("Error: insufficient arguments");
    }
    catch (NumberFormatException e)
    {
      System.out.println("Error: second argument is not an integer");
    }
  }
}
