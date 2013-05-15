package uk.ac.cam.pv273.tick7star;

import uk.ac.cam.acr31.life.World;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileReader;

public class Editor extends JFrame
{
  //Panels used
  GamePanel gamePanel;
  PatternPanel patternPanel;
  GenerationsPanel generationsPanel;

  //Fields to be updated
  private World world;
  private Pattern pattern;

  public Editor() 
  {
    super("GuiLife");
    setSize(800, 600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    patternPanel = createPatternPanel();
    add(patternPanel, BorderLayout.WEST);
    gamePanel = createGamePanel();
    JPanel holder = new JPanel();
    addBorder(holder,"Game Board Editor");
    holder.add(gamePanel);
    add(new JScrollPane(holder), BorderLayout.CENTER);
    add(createFilmStripPanel(), BorderLayout.SOUTH);
  }

  private void addBorder(JComponent component, String title) 
  {
    Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    Border tb = BorderFactory.createTitledBorder(etch,title);
    component.setBorder(tb);
  }
   
  private GamePanel createGamePanel() 
  {
    GamePanel ret = new GamePanel()
    {
      protected void onMouseClick(int x, int y, int zoom)
      {
        world.setCell(x/zoom,y/zoom,!world.getCell(x/zoom,y/zoom));
        pattern.setPattern(world);
        display(world);
        patternPanel.update(pattern);
        generationsPanel.redraw(world);
      }
    };
    return ret;
  }

  private void Copy(World w, int startX, int startY)
  {
    for (int i=startY;i<world.getHeight();i++)
    {
      for (int j=startX;j<world.getWidth();j++)
      {
        w.setCell(j,i,world.getCell(j-startX+pattern.getStartCol(),i-startY+pattern.getStartRow()));
      }
    }
  }

  private PatternPanel createPatternPanel() 
  { 
    PatternPanel ret = new PatternPanel()
    {
      protected void onPatternStringChange(String patternString)
      {
        try
        {
          pattern = new Pattern(patternString);
          world = new ArrayWorld(pattern.getWidth(),pattern.getHeight());
          pattern.initialise(world);
          gamePanel.display(world);
          patternPanel.update(pattern);
          generationsPanel.redraw(world);
        }
        catch (PatternFormatException pe) {}
      }  
 
      protected void onNameChange(String name)
      {
        try
        {
          pattern = new Pattern(name+":"+pattern.getAuthor()+":"+pattern.getWidth()+":"+pattern.getHeight()+":"+pattern.getStartCol()+":"+pattern.getStartRow()+":"+pattern.getCells());
          patternPanel.update(pattern);
        }
        catch (PatternFormatException pe) {}
      }

      protected void onAuthorChange(String author)
      {
        try
        {
          pattern = new Pattern(pattern.getName()+":"+author+":"+pattern.getWidth()+":"+pattern.getHeight()+":"+pattern.getStartCol()+":"+pattern.getStartRow()+":"+pattern.getCells());
          patternPanel.update(pattern);
        }
        catch (PatternFormatException pe) {}
      }

      protected void onWidthChange(int width)
      {
        World nextWorld = new ArrayWorld(width,world.getHeight());
        Copy(nextWorld, pattern.getStartCol(), pattern.getStartRow());
        world = nextWorld;
        pattern.setPattern(world);
        gamePanel.display(world);
        patternPanel.update(pattern);
        generationsPanel.redraw(world);
      }
     
      protected void onHeightChange(int height)
      {
        World nextWorld = new ArrayWorld(world.getWidth(),height);
        Copy(nextWorld, pattern.getStartCol(), pattern.getStartRow());
        world = nextWorld;
        pattern.setPattern(world);
        gamePanel.display(world);
        patternPanel.update(pattern);
        generationsPanel.redraw(world);
      }

      protected void onStartXChange(int startX)
      {
        World nextWorld = new ArrayWorld(world.getWidth(),world.getHeight());
        Copy(nextWorld, startX, pattern.getStartRow());
        world = nextWorld;
        pattern.setPattern(world);
        gamePanel.display(world);
        patternPanel.update(pattern);
        generationsPanel.redraw(world);
      }

      protected void onStartYChange(int startY)
      {
        World nextWorld = new ArrayWorld(world.getWidth(),world.getHeight());
        Copy(nextWorld, pattern.getStartCol(), startY);
        world = nextWorld;
        pattern.setPattern(world);
        gamePanel.display(world);
        patternPanel.update(pattern);
        generationsPanel.redraw(world);
      }

      protected void onRowsChange(String rows)
      {
        try
        {
          pattern = new Pattern(pattern.getName()+":"+pattern.getAuthor()+":"+pattern.getWidth()+":"+pattern.getHeight()+":"+pattern.getStartCol()+":"+pattern.getStartRow()+":"+rows);
          world = new ArrayWorld(pattern.getWidth(),pattern.getHeight());
          pattern.initialise(world);
          gamePanel.display(world);
          patternPanel.update(pattern);
          generationsPanel.redraw(world);
        }
        catch (PatternFormatException pe) {}
      }

    };
    addBorder(ret,"Pattern");
    return ret;
  }
 
  private JComponent createFilmStripPanel() 
  {
    JPanel holder = new JPanel();
    addBorder(holder, "Future Generations");
    generationsPanel = new GenerationsPanel();
    holder.add(generationsPanel);
    JScrollPane sp = new JScrollPane(holder);
    sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    return sp;
  }

  public static void main(String[] args) 
  {
    try
    {
      Editor gui = new Editor();
      gui.world = new ArrayWorld(8,8);
      gui.pattern = new Pattern("::8:8:8:8: ");  
      gui.gamePanel.display(gui.world);
      gui.generationsPanel.redraw(gui.world);
      gui.setVisible(true);
    }
    catch (PatternFormatException pe)
    {
      System.out.println(pe.getMessage());
    }
  }

}
