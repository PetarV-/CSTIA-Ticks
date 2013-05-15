package uk.ac.cam.pv273.tick6star;

import uk.ac.cam.acr31.life.World;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Plot extends JPanel
{
  private ArrayList<Double> values;
  private double maximumValue;
  private double minimumValue;
  private double minGeneration;
  private double maxGeneration;
  private boolean surface;

  public double getMaximumValue() { return maximumValue; }
  public double getMinimumValue() { return minimumValue; }
  public double getMinGeneration() { return minGeneration; }
  public double getMaxGeneration() { return maxGeneration; }

  public Plot(boolean isSurface)
  { 
    super();
    values = new ArrayList<Double>();
    this.surface = isSurface;
    minGeneration = 0.0;
    maxGeneration = 99.0;
  }

  protected void paintComponent(Graphics g) 
  {
    int H = this.getHeight();
    double scaleX = (double)this.getWidth() / 100.0;
    double scaleY = (double)this.getHeight() / (maximumValue - minimumValue);
    g.setColor(Color.WHITE);
    g.fillRect(0,0,this.getWidth(),this.getHeight());
    if (!surface) g.setColor(Color.RED);
    else g.setColor(Color.GREEN);
    for (int i=1;i<values.size();i++)
    {
      if (!surface) g.drawLine((int)((i-1)*scaleX),(int)(H - (values.get(i-1)-minimumValue)*scaleY),(int)(i*scaleX),(int)(H - (values.get(i)-minimumValue)*scaleY));

      else g.fillRect((int)((i-1)*scaleX),(int)(H - (values.get(i-1)-minimumValue)*scaleY),(int)scaleX+1,this.getHeight());
    }
  }

  public void addDataPoint(Double x)
  {
    //System.out.println("Added value: " + x.doubleValue());
    if (values.size() == 100) 
    {
      values.remove(0);
      minGeneration++;
      maxGeneration++;
    }
    values.add(x);
    if (values.size() == 1)
    {
      maximumValue = x.doubleValue();
      minimumValue = x.doubleValue();
    }
    minimumValue = values.get(0);
    maximumValue = values.get(0);
    for (int i=0;i<values.size();i++)
    {
      if (values.get(i).doubleValue() > maximumValue) maximumValue = values.get(i).doubleValue();
      if (values.get(i).doubleValue() < minimumValue) minimumValue = values.get(i).doubleValue();
    }
    repaint();
  }

  

}
