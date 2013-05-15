package uk.ac.cam.pv273.tick6star;

import uk.ac.cam.acr31.life.World;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VerticalAxis extends JPanel
{

  private double minVal;
  private double maxVal;

  private JLabel minValLabel;
  private JLabel maxValLabel;

  protected void paintComponent(Graphics g) 
  {
    super.paintComponent(g);
    g.drawLine(this.getWidth()-1,0,this.getWidth()-1,this.getHeight());
  }
  
  public VerticalAxis(double minVal, double maxVal)
  {
    super();
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    minValLabel = new JLabel(new Double(minVal).toString());
    maxValLabel = new JLabel(new Double(maxVal).toString());
    this.add(maxValLabel);
    this.add(Box.createHorizontalStrut(this.getHeight() - 20));
    this.add(minValLabel);
    repaint();
  }

  public void setValues(double minVal, double maxVal)
  {
    minValLabel.setText(new Double(Math.round(minVal*10.0)/10.0).toString());
    maxValLabel.setText(new Double(Math.round(maxVal*10.0)/10.0).toString());
    repaint();
  }

}
