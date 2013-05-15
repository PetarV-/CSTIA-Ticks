package uk.ac.cam.pv273.tick6;

import uk.ac.cam.acr31.life.World;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class TextLife 
{

  public static void main(String[] args) 
	{
		try
		{
			CommandLineOptions c = new CommandLineOptions(args);
			List<Pattern> list;
			if (c.getSource().startsWith("http://"))
				list = PatternLoader.loadFromURL(c.getSource());
			else
				list = PatternLoader.loadFromDisk(c.getSource());
			if (c.getIndex() == null) 
			{
				int i = 0;
				for (Pattern p : list)
					System.out.println((i++)+" "+p.getName()+" "+p.getAuthor());
			} 
			else 
			{
				Pattern p = list.get(c.getIndex());
				World w = null;
				if (c.getWorldType().equals(CommandLineOptions.WORLD_TYPE_AGING)) 
				{
					w = new AgingWorld(p.getWidth(), p.getHeight());
				} 
				else if (c.getWorldType().equals(CommandLineOptions.WORLD_TYPE_ARRAY)) 
				{
					w = new ArrayWorld(p.getWidth(), p.getHeight());
				} 
				else 
				{
					w = new PackedWorld();
				}
				p.initialise(w);
				int userResponse = 0;
				while (userResponse != 'q') 
				{
					w.print(new OutputStreamWriter(System.out));
					try 
					{
						userResponse = System.in.read();
					} 
					catch (IOException e) {}
					w = w.nextGeneration(0);
				}
			}
		}
		catch (CommandLineException ce)
		{
			System.out.println(ce.getMessage());
		}
                catch (PatternFormatException pe)
                {
                	System.out.println(pe.getMessage());
                }
		catch (IOException ie)
                {
                	System.out.println(ie.getMessage());
                }
	}
}
