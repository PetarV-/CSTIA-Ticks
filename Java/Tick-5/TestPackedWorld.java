package uk.ac.cam.pv273.tick5;

import uk.ac.cam.acr31.life.World;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.awt.Graphics;

public class TestPackedWorld implements World
{
  private int generation;
  private int width;
  private int height;
  private long cells;
  
  public TestPackedWorld()
  {
    width = 8;
    height = 8;
    generation = 0;
    cells = 0L;
  }

  protected TestPackedWorld(TestPackedWorld prev)
  {
    width = prev.width;
    height = prev.height;
    generation = prev.generation + 1;
    cells = prev.cells;
  }

  public boolean getCell(int col, int row) 
  { 
    if (col < 0 || col > 7 || row < 0 || row > 7) return false;
    return PackedLong.get(cells,row*8 + col);
  }

  public void setCell(int col, int row, boolean alive) 
  { 
    if (col < 0 || col > 7 || row < 0 || row > 7) return;
    cells = PackedLong.set(cells,row*8 + col,alive);
  }
  
  public int getWidth() 
  {
    return width;
  }

  public int getHeight() 
  { 
    return height;
  }

  public int getGeneration() 
  { 
    return generation;
  }

  public int getPopulation() 
  { 
    return 0;
  }

  public void print(Writer w) 
  { 
    PrintWriter pw = new PrintWriter(w);
    pw.println("-");
    for (int i=0;i<height;i++) 
    {
      for (int j=0;j<width;j++)
      {
        pw.print(this.getCell(j,i) ? "#" : "_");
      }
      pw.println();
    }
    pw.flush();
  }

  public void draw(Graphics g, int width, int height)
  { /* Leave empty */ 

  }

  private TestPackedWorld nextGeneration()
  {
    //Construct a new TestArrayWorld object to hold the next generation:
    TestPackedWorld world = new TestPackedWorld(this);
    for (int i=0;i<height;i++)
    {
      for (int j=0;j<width;j++)
      {
        world.setCell(j,i,computeCell(j,i));
      }  
    }
    return world;
  } 

  public World nextGeneration(int log2StepSize)
  {
    TestPackedWorld world = this;
    for (int i=0;i<(1<<log2StepSize);i++)
    {
      world = world.nextGeneration();
    }
    return world;
  }

  private int countNeighbours(int col, int row)
  {
    int dx[] = {1, 0, -1, 0, 1, -1, 1, -1};
    int dy[] = {0, 1, 0, -1, 1, -1, -1, 1};
    int ret = 0;
    for (int i=0;i<8;i++)
    {
      int rowNew = row + dx[i];
      int colNew = col + dy[i];
      if (getCell(colNew,rowNew)) ret++;
    }
    return ret;
  }

  private boolean computeCell(int col, int row)
  {
    // liveCell is true if the cell at position (col,row) in world is live
    boolean liveCell = getCell(col, row);
    
    // neighbours is the number of live neighbours to cell (col,row)
    int neighbours = countNeighbours(col, row);
    
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

}
