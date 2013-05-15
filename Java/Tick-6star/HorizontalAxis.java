package uk.ac.cam.pv273.tick6star;

import uk.ac.cam.acr31.life.World;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HorizontalAxis extends JPanel
{

  private Component c, c1;
  private double minVal;
  private double maxVal;

  private JLabel minValLabel;
  private JLabel maxValLabel;
  private int W1; 
  private boolean hasSet = false;

  protected void paintComponent(Graphics g) 
  {
    super.paintComponent(g);
    g.drawLine(W1,1,this.getWidth(),1);
  }
  
  public HorizontalAxis(double minVal, double maxVal, int W1)
  {
    super();
    this.W1 = W1;

    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    minValLabel = new JLabel(new Double(minVal*10.0).toString());
    maxValLabel = new JLabel(new Double(maxVal).toString());
    
    c = Box.createHorizontalStrut(W1);
    c1 = Box.createHorizontalStrut(228);
    this.add(c);
    this.add(minValLabel);
    this.add(c1);
    this.add(maxValLabel);
    repaint();
  }

  public void setValues(double minVal, double maxVal, int W1)
  {
    minValLabel.setText(new Double(Math.round(minVal*10.0)/10.0).toString());
    maxValLabel.setText(new Double(Math.round(maxVal*10.0)/10.0).toString());
    this.W1 = W1;
    this.removeAll();
    c = Box.createHorizontalStrut(W1);
    c1 = Box.createHorizontalStrut(338-W1);
    this.add(c);
    this.add(minValLabel);
    this.add(c1);
    this.add(maxValLabel,BorderLayout.LINE_END);
    repaint();
  }

}
