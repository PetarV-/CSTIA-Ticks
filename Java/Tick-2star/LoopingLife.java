package uk.ac.cam.pv273.tick2star;

public class LoopingLife
{
  public static boolean getCell(long world, int col, int row)
  {
    if (col < 0 || col > 7 || row < 0 || row > 7) return false;
    return PackedLong.get(world,row*8 + col);
  }
  
  public static long setCell(long world, int col, int row, boolean value)
  {
    if (col < 0 || col > 7 || row < 0 || row > 7) return world;
    return PackedLong.set(world,row*8 + col,value);
  }

  public static int countNeighbours(long world, int col, int row)
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

  public static boolean computeCell(long world, int col, int row)
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

  public static long nextGeneration(long world)
  {
    long retWorld = world;
    for (int i=0;i<8;i++)
    {
      for (int j=0;j<8;j++)
      {
        retWorld = setCell(retWorld,j,i,computeCell(world,j,i));
      }  
    }
    return retWorld;
  }

  public static void findLoop(long world) throws Exception
  {
    long[] previousVal = new long[100];
    previousVal[0] = world;
    int iter = 1;
    boolean done = false;
    while (!done && iter < 100)
    {
      previousVal[iter] = nextGeneration(previousVal[iter-1]);
      for (int i=0;i<iter;i++)
      {
        if (previousVal[i] == previousVal[iter])
        {
          done = true;
          System.out.println(i + " to " + (iter-1));
          break;
        }
      }
      iter++;
    }
    if (!done) System.out.println("No loops found");
  }

  public static void main(String[] args) throws Exception
  {
    findLoop(Long.decode(args[0]));
  }
}
