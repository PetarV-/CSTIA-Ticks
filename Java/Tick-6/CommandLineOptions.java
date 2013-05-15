package uk.ac.cam.pv273.tick6;

public class CommandLineOptions 
{

  public static String WORLD_TYPE_LONG = "--long";
  public static String WORLD_TYPE_AGING = "--aging";
  public static String WORLD_TYPE_ARRAY = "--array";
  private String worldType = null;
  private Integer index = null;
  private String source = null;
 
  public CommandLineOptions(String[] args) throws CommandLineException 
  {
    if (args.length == 0) throw new CommandLineException("Error: No arguments found");
    if (args[0].equals(WORLD_TYPE_LONG) || args[0].equals(WORLD_TYPE_AGING) || args[0].equals(WORLD_TYPE_ARRAY))
    {
      worldType = args[0];
      if (args.length == 1) throw new CommandLineException("Error: Insufficient arguments");
      source = args[1];
      if (args.length == 3)
      {
        try { index = Integer.parseInt(args[2]); }
        catch (Exception e) { throw new CommandLineException("Error: Index out of bounds"); }
        if (index < 0) throw new CommandLineException("Error: Index out of bounds");
      }
    }
    else 
    {
      source = args[0];
      if (args.length == 2)
      {
        try { index = Integer.parseInt(args[1]); }
        catch (Exception e) { throw new CommandLineException("Error: Index out of bounds"); }
        if (index < 0) throw new CommandLineException("Error: Index out of bounds");
      }
    }
  }

  public String getWorldType() {return worldType;}
  public Integer getIndex() {return index;}
  public String getSource() {return source;}

}
