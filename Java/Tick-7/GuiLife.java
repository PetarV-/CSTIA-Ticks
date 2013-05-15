package uk.ac.cam.pv273.tick7;

import uk.ac.cam.acr31.life.World;
import uk.ac.cam.acr31.life.hash.HashWorld;
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

public class GuiLife extends JFrame 
{

  PatternPanel patternPanel;
  ControlPanel controlPanel;
  GamePanel gamePanel;

  private World world;
  private int timeDelay = 500; //delay between updates (millisecs)
  private int timeStep = 0;    //progress by (2 ^ timeStep) each time

  private Timer playTimer = new Timer(timeDelay, new ActionListener() 
  {

    public void actionPerformed(ActionEvent e) 
    {
      doTimeStep();
    }

  });

  void doTimeStep() 
  {
    if (world != null) 
    {
      world = world.nextGeneration(timeStep);
      gamePanel.display(world);
    }
  }

  private void resetWorld() 
  {
    Pattern current = patternPanel.getCurrentPattern();
    world = null;
    if (current != null) 
    {
      try 
      {
        world = controlPanel.initialiseWorld(current);
      }  
      catch (PatternFormatException e) 
      {
        JOptionPane.showMessageDialog(this,
          "Error initialising world",
          "An error occurred when initialising the world. "+e.getMessage(),
          JOptionPane.ERROR_MESSAGE);
      }
    }
    gamePanel.display(world);
    repaint();
  }

  public GuiLife() 
  {
    super("GuiLife");
    setSize(640, 480);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    JComponent optionsPanel = createOptionsPanel();
    add(optionsPanel, BorderLayout.WEST);
    gamePanel = createGamePanel();
    JPanel holder = new JPanel();
    addBorder(holder,Strings.PANEL_GAMEVIEW);
    holder.add(gamePanel);
    add(new JScrollPane(holder), BorderLayout.CENTER);
  }

  private JComponent createOptionsPanel() 
  {
    Box result = Box.createVerticalBox();
    result.add(createSourcePanel());
    result.add(createPatternPanel());
    result.add(createControlPanel());
    return result;
  }

  private void addBorder(JComponent component, String title) 
  {
    Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    Border tb = BorderFactory.createTitledBorder(etch,title);
    component.setBorder(tb);
  }

  
  private GamePanel createGamePanel() 
  {
    GamePanel ret = new GamePanel();
    return ret;
  }

  private JPanel createSourcePanel() 
  {
    JPanel result = new SourcePanel()
    {
      protected boolean setSourceFile() 
      {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
          File f = chooser.getSelectedFile();
          try 
          {
            List<Pattern> list = PatternLoader.load(new FileReader(f));
            patternPanel.setPatterns(list);
            resetWorld();
            return true;
          } 
          catch (IOException ioe) {}
        }
        return false;
      }

      protected boolean setSourceNone() 
      {
        world = null;
        patternPanel.setPatterns(null);
        resetWorld();
        return true;
      }
 
      protected boolean setSourceLibrary() 
      {
        String u = "http://www.cl.cam.ac.uk/teaching/current/ProgJava/nextlife.txt";
        return setSourceWeb(u);
      }

      protected boolean setSourceFourStar()
      {
        String u = "http://www.cl.cam.ac.uk/teaching/current/ProgJava/competition.txt";
        return setSourceWeb(u);
      }
 
      private boolean setSourceWeb(String url) 
      {
        try 
        {
          List<Pattern> list = PatternLoader.loadFromURL(url);
          patternPanel.setPatterns(list);
          resetWorld();
          return true;
        } 
        catch (IOException ioe) {}
        return false;
      }

    };
    addBorder(result,Strings.PANEL_SOURCE);
    return result;
  }

  private PatternPanel createPatternPanel() 
  { 
    PatternPanel ret = new PatternPanel()
    {
      protected void onPatternChange()
      {
        resetWorld();
      }
    };
    addBorder(ret,Strings.PANEL_PATTERN);
    patternPanel = ret;
    return ret;
  }
  
  private JComponent createControlPanel() 
  { 
    controlPanel = new ControlPanel()
    {
      protected void onZoomChange(int value)
      {
        gamePanel.setZoom(value);
      }
      protected void onStepChange(int value)
      {
        timeStep = value;
      }
      protected void onSpeedChange(int value) 
      {
        playTimer.setDelay(1+(100-value)*10);
      }
    };
    addBorder(controlPanel,Strings.PANEL_CONTROL);
    return controlPanel;
  }

  public static void main(String[] args) 
  {
    GuiLife gui = new GuiLife();
    gui.playTimer.start();
    gui.resetWorld();
    gui.setVisible(true);
  }
}
