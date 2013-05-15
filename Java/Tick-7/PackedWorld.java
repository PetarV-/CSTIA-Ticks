package uk.ac.cam.pv273.tick7;

public class PackedWorld extends WorldImpl
{
  private long cells;

  public PackedWorld()
  {
    super(8, 8);
    cells = 0L;
  }

  public PackedWorld(PackedWorld prev)
  {
    super(prev);
    cells = prev.cells;
  } 

  public boolean getCell(int col,int row)
  {
    if (col < 0 || col > 7 || row < 0 || row > 7) return false;
    return PackedLong.get(cells,row*8 + col);
  }

  public void setCell(int col, int row, boolean alive)
  {
    if (col < 0 || col > 7 || row < 0 || row > 7) return;
    cells = PackedLong.set(cells,row*8 + col,alive);
  }

  protected PackedWorld nextGeneration()
  {
    PackedWorld world = new PackedWorld(this);
    int W = getWidth();
    int H = getHeight();
    for (int i=0;i<8;i++)
    {
      for (int j=0;j<8;j++)
      {
        world.setCell(j,i,computeCell(j,i));
      }  
    }
    return world;
  }
}
