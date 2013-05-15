package uk.ac.cam.pv273.tick7star;

import uk.ac.cam.acr31.life.World;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class GenerationsPanel extends JPanel
{

  private GameImage generations[] = new GameImage[10];

  public Dimension getPreferredSize() 
  {
    return new Dimension(1100,80);
  }

  public GenerationsPanel()
  {
    super();
    setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
    for (int i=0;i<10;i++) 
    {
      generations[i] = new GameImage();
      this.add(generations[i]);
      this.add(Box.createHorizontalStrut(10));
    }
  }

  public void redraw(World w)
  {
    for (int i=0;i<10;i++)
    {
      generations[i].display(w);
      w = w.nextGeneration(0);
    }
  }

}
