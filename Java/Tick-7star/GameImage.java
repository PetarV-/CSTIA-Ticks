package uk.ac.cam.pv273.tick7star;

import uk.ac.cam.acr31.life.World;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class GameImage extends JPanel
{

  private int width = 100; //Width of game board in pixels
  private int height = 80;//Height of game board in pixels
  private World current = new ArrayWorld(8,8);

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
  }

  public void display(World w) 
  {
    current = w;
    repaint();
  }

}
