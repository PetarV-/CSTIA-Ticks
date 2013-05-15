package uk.ac.cam.pv273.tick4star;

import java.io.IOException;
import java.util.List;

public class StatisticsLife
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

  public static Statistics analyse(Pattern p) throws PatternFormatException
  {
    System.out.println("Analysing "+p.getName());
    boolean world[][] = new boolean[p.getHeight()][p.getWidth()];
    p.initialise(world);
    return new Statistics(world);
  }

  public static void main(String[] args) throws Exception
  {
    int longestStart = -1;
    String longestStartName = "";
    int longestCycle = -1;
    String longestCycleName = "";
    double biggestGrowth = -1.0;
    String biggestGrowthName = "";
    double biggestDeath = -1.0;
    String biggestDeathName = "";
    int largestPopulation = -1;
    String largestPopulationName = "";
    try
    {
      String addr;
      addr = args[0];
      try
      {
        List<Pattern> ret = PatternLoader.loadFromURL(addr);
        longestStart = -1;
        longestCycle = -1;
        biggestGrowth = -1.0;
        biggestDeath = -1.0;
        largestPopulation = 0;
        for (Pattern p:ret)
        {
          Statistics s = analyse(p);
          if (s.getMaximumGrowthRate() > biggestGrowth) 
          {
            biggestGrowth = s.getMaximumGrowthRate();
            biggestGrowthName = p.getName();
          }
          if (s.getMaximumDeathRate() > biggestDeath)
          {
            biggestDeath = s.getMaximumDeathRate();
            biggestDeathName = p.getName();
          }
          if (s.getMaximumPopulation() > largestPopulation)
          {
            largestPopulation = s.getMaximumPopulation();
            largestPopulationName = p.getName();
          }
          if (s.getLoopStart() != -1)
          {
            if (s.getLoopStart() > longestStart)
            {
              longestStart = s.getLoopStart();
              longestStartName = p.getName();
            }
            if (s.getLoopEnd() - s.getLoopStart() > longestCycle)
            {
              longestCycle = s.getLoopEnd() - s.getLoopStart();
              longestCycleName = p.getName();
            }
          }
        }
        System.out.println();
        System.out.println("Longest start: "+longestStartName+" ("+longestStart+")");
        System.out.println("Longest cycle: "+longestCycleName+" ("+longestCycle+")");
        System.out.println("Biggest growth rate: "+biggestGrowthName+" ("+biggestGrowth+")");
        System.out.println("Biggest death rate: "+biggestDeathName+" ("+biggestDeath+")");
        System.out.println("Largest population: "+largestPopulationName+" ("+largestPopulation+")");
      }
      catch (IOException ei1)
      {
        try
        {
          List<Pattern> ret = PatternLoader.loadFromDisk(addr);
          longestStart = -1;
          longestCycle = -1;
          biggestGrowth = -1.0;
          biggestDeath = -1.0;
          largestPopulation = 0;
          for (Pattern p:ret)
          {
            Statistics s = analyse(p);
            if (s.getMaximumGrowthRate() > biggestGrowth) 
            {
              biggestGrowth = s.getMaximumGrowthRate();
              biggestGrowthName = p.getName();
            }
            if (s.getMaximumDeathRate() > biggestDeath)
            {
              biggestDeath = s.getMaximumDeathRate();
              biggestDeathName = p.getName();
            }
            if (s.getMaximumPopulation() > largestPopulation)
            {
              largestPopulation = s.getMaximumPopulation();
              largestPopulationName = p.getName();
            }
            if (s.getLoopStart() != -1)
            {
              if (s.getLoopStart() > longestStart)
              {
                longestStart = s.getLoopStart();
                longestStartName = p.getName();
              }
              if (s.getLoopEnd() - s.getLoopStart() > longestCycle)
              {
                longestCycle = s.getLoopEnd() - s.getLoopStart();
                longestCycleName = p.getName();
              }
            }
          }
          System.out.println();
          System.out.println("Longest start: "+longestStartName+" ("+longestStart+")");
          System.out.println("Longest cycle: "+longestCycleName+" ("+longestCycle+")");
          System.out.println("Biggest growth rate: "+biggestGrowthName+" ("+biggestGrowth+")");
          System.out.println("Biggest death rate: "+biggestDeathName+" ("+biggestDeath+")");
          System.out.println("Largest population: "+largestPopulationName+" ("+largestPopulation+")");
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
