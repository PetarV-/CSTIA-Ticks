package uk.ac.cam.pv273.tick3star;

public class Pattern
{
  private String name;
  private String author;
  private int width;
  private int height;
  private int startCol;
  private int startRow;
  private String cells;

  public String getName()
  {
    return name;
  }

  public String getAuthor()
  {
    return author;
  }

  public int getWidth()
  {
    return width;
  }

  public int getHeight()
  {
    return height;
  }

  public int getStartCol()
  {
    return startCol;
  }

  public int getStartRow()
  {
    return startRow;
  }

  public String getCells()
  {
    return cells;
  }

  public Pattern(String format)
  {
    String[] parts = format.split(":");
    name = parts[0];
    author = parts[1];
    width = Integer.parseInt(parts[2]);
    height = Integer.parseInt(parts[3]);
    startCol = Integer.parseInt(parts[4]);
    startRow = Integer.parseInt(parts[5]);
    cells = parts[6];
  }

  public void initialise(boolean[][] world)
  {
    String[] cellParts = cells.split(" ");
    for (int i=0;i<cellParts.length;i++)
    {
      char[] currCells = cellParts[i].toCharArray();
      for (int j=0;j<currCells.length;j++)
      {
        if (currCells[j] == '1') world[i+startRow][j+startCol] = true;
      }
    }
  }
}
