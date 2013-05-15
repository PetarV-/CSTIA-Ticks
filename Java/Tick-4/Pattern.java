package uk.ac.cam.pv273.tick4;

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

  public Pattern(String format) throws PatternFormatException
  {
    try
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
    catch (ArrayIndexOutOfBoundsException e)
    {
      throw new PatternFormatException("Error: insufficient arguments in format");
    }
    catch (NumberFormatException e)
    {
      throw new PatternFormatException("Error: noninteger argument for width/height/start column/start row");
    }
  }

  public void initialise(boolean[][] world) throws PatternFormatException
  {
    String[] cellParts = cells.split(" ");
    for (int i=0;i<cellParts.length;i++)
    {
      char[] currCells = cellParts[i].toCharArray();
      for (int j=0;j<currCells.length;j++)
      {
        if (currCells[j] != '1' && currCells[j] != '0') throw new PatternFormatException("Error: incorrect format of cell description");
        if (currCells[j] == '1') world[i+startRow][j+startCol] = true;
      }
    }
  }
}
