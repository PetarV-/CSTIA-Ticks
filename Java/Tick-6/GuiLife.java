package uk.ac.cam.pv273.tick6;

import uk.ac.cam.acr31.life.World;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.List;
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

  public static void main(String[] args) 
  {
    GuiLife gui = new GuiLife();
    
    try 
    {
      CommandLineOptions co = new CommandLineOptions(args);
      List<Pattern> ret = PatternLoader.loadFromURL(co.getSource());
      gui.patternPanel.setPatterns(ret);
      World w = gui.controlPanel.initialiseWorld(ret.get(co.getIndex()));
      gui.gamePanel.display(w);
      gui.setVisible(true);
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

  }
}
