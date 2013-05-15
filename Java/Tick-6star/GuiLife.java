package uk.ac.cam.pv273.tick6star;

import uk.ac.cam.acr31.life.World;
import java.awt.BorderLayout;
import java.io.IOException;
import java.lang.InterruptedException;
import java.lang.Thread;
import java.util.List;
import java.util.Random;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

public class GuiLife extends JFrame 
{

  PatternPanel patternPanel;
  ControlPanel controlPanel;
  GamePanel gamePanel;
  StatisticsPanel statisticsPanel;

  public GuiLife() 
  {
    super("GuiLife");
    setSize(1020, 480);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    JComponent optionsPanel = createOptionsPanel();
    add(optionsPanel, BorderLayout.WEST);
    gamePanel = createGamePanel();
    JPanel holder = new JPanel();
    addBorder(holder,Strings.PANEL_GAMEVIEW);
    holder.add(gamePanel);
    add(new JScrollPane(holder), BorderLayout.CENTER);
    statisticsPanel = createStatisticsPanel();
    add(statisticsPanel, BorderLayout.EAST);
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

  private SourcePanel createSourcePanel() 
  {
    SourcePanel result = new SourcePanel();
    addBorder(result,Strings.PANEL_SOURCE);
    return result; 
  }

  private PatternPanel createPatternPanel() 
  { 
    PatternPanel ret = new PatternPanel();
    addBorder(ret,Strings.PANEL_PATTERN);
    patternPanel = ret;
    return ret;
  }
  
  private ControlPanel createControlPanel() 
  { 
    ControlPanel ret = new ControlPanel();
    addBorder(ret,Strings.PANEL_CONTROL);
    controlPanel = ret;
    return ret;
  }

  private StatisticsPanel createStatisticsPanel()
  {
    StatisticsPanel ret = new StatisticsPanel();
    addBorder(ret,Strings.PANEL_STATISTICS);
    return ret;
  }

  public static void main(String[] args) 
  {
    GuiLife gui = new GuiLife();
    Random r = new Random();
    
    try 
    {
      CommandLineOptions co = new CommandLineOptions(args);
      List<Pattern> ret = PatternLoader.loadFromURL(co.getSource());
      gui.patternPanel.setPatterns(ret);
      World w = gui.controlPanel.initialiseWorld(ret.get(co.getIndex()));
      int ii = 0;
      //while (ii++ <= 150)// <- used for testing
      //{// <- used for testing
        gui.gamePanel.display(w);
        gui.statisticsPanel.update(w);
        gui.setVisible(true);
        //Thread.sleep(10); //<- used for testing
        //gui.setVisible(true);
        w = w.nextGeneration(0);
      //}// <- used for testing
    }
    catch (CommandLineException ce)
    {
      System.out.println(ce.getMessage());
    }
    catch (PatternFormatException pe)
    {
      System.out.println(pe.getMessage());
    }
    catch (IOException ie)
    {
      System.out.println(ie.getMessage());
    }
    //catch (InterruptedException iew) {}
  }
}
