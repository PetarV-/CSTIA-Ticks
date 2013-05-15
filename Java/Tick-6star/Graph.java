package uk.ac.cam.pv273.tick6star;

import uk.ac.cam.acr31.life.World;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Graph extends JPanel
{

  private Plot plot;
  private boolean surface;
  private String name;
  private VerticalAxis vAxis;
  private HorizontalAxis hAxis;

  public Graph(String name, boolean isSurface)
  {
    super();
    setLayout(new BorderLayout());
    plot = new Plot(isSurface);
    this.name = name;
    this.add(new JLabel(name),BorderLayout.NORTH);
    this.add(plot,BorderLayout.CENTER);
    this.surface = isSurface;
    vAxis = new VerticalAxis(0.0,0.0);
    this.add(vAxis,BorderLayout.WEST);
    hAxis = new HorizontalAxis(0.0,0.0,vAxis.getWidth());
    this.add(hAxis,BorderLayout.SOUTH);
  }

  public void addDataPoint(Double x)
  {
    plot.addDataPoint(x);
    vAxis.setValues(plot.getMinimumValue(), plot.getMaximumValue());
    hAxis.setValues(plot.getMinGeneration(), plot.getMaxGeneration(),vAxis.getWidth());
  }

}
