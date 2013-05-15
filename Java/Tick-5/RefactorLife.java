package uk.ac.cam.pv273.tick5;

import uk.ac.cam.acr31.life.World;
import uk.ac.cam.acr31.life.WorldViewer;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.List;

public class RefactorLife
{
  public static void play(World t) throws Exception
  {
    WorldViewer viewer = new WorldViewer();
    int userResponse = 0;
    Writer w = new OutputStreamWriter(System.out);
    while (userResponse != 'q')
    {
      t.print(w);
      viewer.show(t);
      userResponse = System.in.read();
      t = t.nextGeneration(0);
    }
    viewer.close();
  }

  public static void main(String[] args) throws Exception
  {
    World w;
    try
    {
      int state = 0;
      int argSize = 1;
      String addr, str1;
      int ind = -1;
      str1 = args[0];
      if (str1.equals("--array") || str1.equals("--long") || str1.equals("--aging"))
      {
        argSize++;
        if (str1.equals("--long")) state = 1;
        else if (str1.equals("--aging")) state = 2;
        addr = args[1];
      }  
      else 
      {
        if (args.length >= 3) throw new Exception("incorrect format of optional argument");
        addr = args[0];
      }
      if (args.length > argSize) ind = Integer.parseInt(args[argSize]);
      List<Pattern> ret;
      if (addr.startsWith("http://")) ret = PatternLoader.loadFromURL(addr);
      else ret = PatternLoader.loadFromDisk(addr);
      if (ind != -1)
      {  
        Pattern p = ret.get(ind);
        if (state == 0) w = new ArrayWorld(p.getWidth(),p.getHeight());
        else if (state == 1) w = new PackedWorld();
        else w = new AgingWorld(p.getWidth(),p.getHeight());
        p.initialise(w);
        play(w);   
      }
      else
      {
        int i = 0;
        for (Pattern p:ret)
        {
          System.out.println(i + ") "+p.getName()+":"+p.getAuthor()+":"+p.getWidth()+":"+p.getHeight()+":"+p.getStartCol()+":"+p.getStartRow()+":"+p.getCells());
          i++;
        }
      }
    }
    catch (IOException ei2)
    {
      System.out.println("Error: Incorrect URL / File name entered");
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      System.out.println("Error: insufficient arguments");
    }
    catch (NumberFormatException e)
    {
      System.out.println("Error: second argument is not an integer");
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
}
