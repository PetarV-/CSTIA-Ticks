package uk.ac.cam.pv273.tick6;

import uk.ac.cam.acr31.life.World;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GamePanel extends JPanel
{

  private int zoom = 10; //Number of pixels used to represent a cell
  private int width = 1; //Width of game board in pixels
  private int height = 1;//Height of game board in pixels
  private World current = null;

  public Dimension getPreferredSize() 
  {
    return new Dimension(width, height);
  }

  protected void paintComponent(Graphics g) 
  {
    if (current == null) return;
    g.setColor(java.awt.Color.WHITE);
    g.fillRect(0, 0, width, height);
    current.draw(g, width, height);
    if (zoom > 4) 
    {
      g.setColor(java.awt.Color.LIGHT_GRAY);
      for (int i=0;i<=height;i+=zoom) g.drawLine(0, i, width, i);
      for (int j=0;j<=width;j+=zoom) g.drawLine(j, 0, j, height);
    }
  }

  private void computeSize() 
  {
    if (current == null)  return;
    int newWidth = current.getWidth() * zoom;
    int newHeight = current.getHeight() * zoom;
    if (newWidth != width || newHeight != height) 
    {
      width = newWidth;
      height = newHeight;
      revalidate(); //trigger the GamePanel to re-layout its components
    }
  }

  public void display(World w) 
  {
    current = w;
    computeSize();
    repaint();
  }

}
