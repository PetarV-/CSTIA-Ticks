package uk.ac.cam.pv273.tick3star;

public class AnimatedLife
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

  public static void main(String[] args) throws Exception
  {
    Pattern p = new Pattern(args[0]);
    int iterations = Integer.parseInt(args[1]);
    String outName = args[2];
    boolean[][] world = new boolean[p.getHeight()][p.getWidth()];
    p.initialise(world);
    OutputAnimatedGif myOut = new OutputAnimatedGif(outName);
    for (int i=0;i<iterations;i++)
    {
      myOut.addFrame(world);
      world = nextGeneration(world);
    }
    myOut.close();
  }
}
