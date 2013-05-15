package uk.ac.cam.pv273.tick7star;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class PatternPanel extends JPanel 
{

  private JTextField patternString;
  private JTextField name;
  private JTextField author;
  private JSpinner width;
  private JSpinner height;
  private JSpinner startX;
  private JSpinner startY;
  private JTextField rows;

  private final DocumentListener patternStringListener = new DocumentListener()
  {
    public void changedUpdate(DocumentEvent e) 
    {
      onPatternStringChange(patternString.getText());
    }
    public void removeUpdate(DocumentEvent e)
    {
      onPatternStringChange(patternString.getText());
    }
    public void insertUpdate(DocumentEvent e)
    {
      onPatternStringChange(patternString.getText());
    } 
  };

  private final DocumentListener nameListener = new DocumentListener()
  {
    public void changedUpdate(DocumentEvent e) 
    {
      onNameChange(name.getText());
    }
    public void removeUpdate(DocumentEvent e)
    {
      onNameChange(name.getText());
    }
    public void insertUpdate(DocumentEvent e)
    {
      onNameChange(name.getText());
    }
  };

  private final DocumentListener authorListener = new DocumentListener()
  {
    public void changedUpdate(DocumentEvent e) 
    {
      onAuthorChange(author.getText());
    }
    public void removeUpdate(DocumentEvent e)
    {
      onAuthorChange(author.getText());
    }
    public void insertUpdate(DocumentEvent e)
    {
      onAuthorChange(author.getText());
    }
  };

  private final DocumentListener rowsListener = new DocumentListener()
  {
    public void changedUpdate(DocumentEvent e) 
    {
      onRowsChange(rows.getText());
    }
    public void removeUpdate(DocumentEvent e)
    {
      onRowsChange(rows.getText());
    }
    public void insertUpdate(DocumentEvent e)
    {
      onRowsChange(rows.getText());
    }
  };

  private final ChangeListener widthListener = new ChangeListener()
  {
    public void stateChanged(ChangeEvent e) 
    {
      onWidthChange((Integer)width.getValue());
    }
  };

  private final ChangeListener heightListener = new ChangeListener()
  {
    public void stateChanged(ChangeEvent e) 
    {
      onHeightChange((Integer)height.getValue());
    }
  };

  private final ChangeListener startXListener = new ChangeListener()
  {
    public void stateChanged(ChangeEvent e) 
    {
      onStartXChange((Integer)startX.getValue());
    }
  };

  private final ChangeListener startYListener = new ChangeListener()
  {
    public void stateChanged(ChangeEvent e) 
    {
      onStartYChange((Integer)startY.getValue());
    }
  };

  public Dimension getPreferredSize() 
  {
    return new Dimension(240,300);
  }
 
  public PatternPanel() 
  {
    super();
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    patternString = new JTextField("::8:8:8:8:");
    name = new JTextField("");
    author = new JTextField("");
    width = new JSpinner(new SpinnerNumberModel(8,8,1024,1));
    height = new JSpinner(new SpinnerNumberModel(8,8,1024,1));
    startX = new JSpinner(new SpinnerNumberModel(8,0,1024,1)); 
    startY = new JSpinner(new SpinnerNumberModel(8,0,1024,1));
    rows = new JTextField("");
  
    patternString.setMaximumSize(new Dimension(500,20));
    name.setMaximumSize(new Dimension(500,20));
    author.setMaximumSize(new Dimension(500,20));
    width.setMaximumSize(new Dimension(500,20));
    height.setMaximumSize(new Dimension(500,20));
    startX.setMaximumSize(new Dimension(500,20));
    startY.setMaximumSize(new Dimension(500,20));
    rows.setMaximumSize(new Dimension(500,20));

    this.add(new JLabel("Pattern String"));
    this.add(patternString);
    this.add(new JLabel("Name"));
    this.add(name);
    this.add(new JLabel("Author"));
    this.add(author);
    this.add(new JLabel("Width"));
    this.add(width);
    this.add(new JLabel("Height"));
    this.add(height);
    this.add(new JLabel("StartX"));
    this.add(startX);
    this.add(new JLabel("StartY"));
    this.add(startY);
    this.add(new JLabel("Rows"));
    this.add(rows); 

    patternString.getDocument().addDocumentListener(patternStringListener);
    name.getDocument().addDocumentListener(nameListener);
    author.getDocument().addDocumentListener(authorListener);
    width.addChangeListener(widthListener);
    height.addChangeListener(heightListener);
    startX.addChangeListener(startXListener);
    startY.addChangeListener(startYListener);
    rows.getDocument().addDocumentListener(rowsListener);

  }

  public void update(Pattern p)
  {
    patternString.getDocument().removeDocumentListener(patternStringListener);
    name.getDocument().removeDocumentListener(nameListener);
    author.getDocument().removeDocumentListener(authorListener);
    width.removeChangeListener(widthListener);
    height.removeChangeListener(heightListener);
    startX.removeChangeListener(startXListener);
    startY.removeChangeListener(startYListener);
    rows.getDocument().removeDocumentListener(rowsListener);

    if (!p.getString().equals(patternString.getText())) patternString.setText(p.getString());
    if (!p.getName().equals(name.getText())) name.setText(p.getName());
    if (!p.getAuthor().equals(author.getText())) author.setText(p.getAuthor());
    if (p.getWidth() != (Integer)width.getValue()) width.setValue(p.getWidth());
    if (p.getHeight() != (Integer)height.getValue()) height.setValue(p.getHeight());
    if (p.getStartCol() != (Integer)startX.getValue()) startX.setValue(p.getStartCol());
    if (p.getStartRow() != (Integer)startY.getValue()) startY.setValue(p.getStartRow());
    if (!p.getCells().equals(rows.getText())) rows.setText(p.getCells());

    patternString.getDocument().addDocumentListener(patternStringListener);
    name.getDocument().addDocumentListener(nameListener);
    author.getDocument().addDocumentListener(authorListener);
    width.addChangeListener(widthListener);
    height.addChangeListener(heightListener);
    startX.addChangeListener(startXListener);
    startY.addChangeListener(startYListener);
    rows.getDocument().addDocumentListener(rowsListener);
  }

  protected abstract void onPatternStringChange(String patternString);
  protected abstract void onNameChange(String name); 
  protected abstract void onAuthorChange(String author);
  protected abstract void onWidthChange(int width);
  protected abstract void onHeightChange(int height);
  protected abstract void onStartXChange(int startX);
  protected abstract void onStartYChange(int startY);
  protected abstract void onRowsChange(String rows);
    
}
