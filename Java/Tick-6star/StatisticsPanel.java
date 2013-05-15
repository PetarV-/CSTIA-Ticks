package uk.ac.cam.pv273.tick6star;

import uk.ac.cam.acr31.life.World;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatisticsPanel extends JPanel
{

  private int maxPopulation;
  private int minPopulation;
  private int currGeneration;
  private int currPopulation;
  private double maxGrowthRate;
  private double maxDeathRate;

  private Graph population;
  private Graph popChange;
  private Graph growDeathRate;

  private JLabel genLabel;
  private JLabel popLabel;
  private JLabel maxPopLabel;
  private JLabel minPopLabel;
  private JLabel maxGrowthLabel;
  private JLabel maxDeathLabel;

  protected void drawString(Graphics g, String text, int x, int y, int halign, int valign) 
  {
    FontMetrics m = g.getFontMetrics();
    Rectangle2D r = m.getStringBounds(text, g);
    x -= r.getWidth() * halign / 2;
    y += r.getHeight() * valign / 2;
    g.drawString(text,x, y);
  }

  protected void paintComponent(Graphics g) 
  {
    super.paintComponent(g);
  }

  public void update(World w)
  {
    repaint();
    currGeneration = w.getGeneration();
    int nxtPopulation = w.getPopulation();
    double growthRate = 0.0, deathRate = 0.0;
    if (currGeneration == 0)
    {
      maxPopulation = nxtPopulation;
      minPopulation = nxtPopulation;
    }
    else
    {
      growthRate = ((nxtPopulation - currPopulation)*1.0) / (currPopulation*1.0);
      deathRate = -growthRate;
      if (nxtPopulation > maxPopulation) maxPopulation = nxtPopulation;
      if (nxtPopulation < minPopulation) minPopulation = nxtPopulation;
      if (growthRate > maxGrowthRate) maxGrowthRate = growthRate;
      if (deathRate > maxDeathRate) maxDeathRate = deathRate;
    }

    //updates start here
    genLabel.setText("Generation: " + currGeneration);
    popLabel.setText("Population: " + currPopulation);
    maxPopLabel.setText("Maximum population: " + maxPopulation);
    minPopLabel.setText("Minimum population: " + minPopulation);
    maxGrowthLabel.setText("Maximum growth rate: " + Math.round(maxGrowthRate*100000.00)/100000.00);
    maxDeathLabel.setText("Maximum death rate: " + Math.round(maxDeathRate*100000.00)/100000.00);
   
    population.addDataPoint((double)nxtPopulation);
    popChange.addDataPoint((double)nxtPopulation - currPopulation);
    growDeathRate.addDataPoint(Math.max(growthRate,deathRate));

    currPopulation = nxtPopulation;
  }

  public StatisticsPanel()
  {
    super();
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    population = new Graph("Population", true);
    popChange = new Graph("Population Change", false);
    growDeathRate = new Graph("Growth/Death Rate", false);

    genLabel = new JLabel("Generation:");
    popLabel = new JLabel("Population:");
    maxPopLabel = new JLabel("Maximum population:");
    minPopLabel = new JLabel("Minimum population:");
    maxGrowthLabel = new JLabel("Maximum growth rate:");
    maxDeathLabel = new JLabel("Maximum death rate:");

    this.add(genLabel);
    this.add(popLabel);
    this.add(maxPopLabel);
    this.add(minPopLabel);
    this.add(maxGrowthLabel);
    this.add(maxDeathLabel);

    this.add(Box.createVerticalStrut(10));
    this.add(population);
    this.add(Box.createVerticalStrut(10));
    this.add(popChange);
    this.add(Box.createVerticalStrut(10));
    this.add(growDeathRate);
  }

}
