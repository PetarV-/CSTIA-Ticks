package uk.ac.cam.pv273.tick5;

public class ArrayWorld extends WorldImpl
{
  private boolean[][] cells;

  public ArrayWorld(int width, int height)
  {
    super(width,height);
    cells = new boolean[height][width];
  }

  public ArrayWorld(ArrayWorld prev)
  {
    super(prev);
    int W = getWidth();
    int H = getHeight();
    cells = new boolean[H][W];
    for (int i=0;i<H;i++)
    {
      for (int j=0;j<W;j++)
      {
        cells[i][j] = prev.cells[i][j];
      }
    }
  } 

  public boolean getCell(int col,int row)
  {
    if (row < 0 || row > getHeight() - 1) return false;
    if (col < 0 || col > getWidth() - 1) return false;
    return cells[row][col];
  }

  public void setCell(int col, int row, boolean alive)
  {
    if (row < 0 || row > getHeight() - 1) return;
    if (col < 0 || col > getWidth() - 1) return;
    cells[row][col] = alive;
  }

  protected ArrayWorld nextGeneration()
  {
    ArrayWorld world = new ArrayWorld(this);
    int W = getWidth();
    int H = getHeight();
    for (int i=0;i<H;i++)
    {
      for (int j=0;j<W;j++)
      {
        world.setCell(j,i,computeCell(j,i));
      }  
    }
    return world;
  }
}
