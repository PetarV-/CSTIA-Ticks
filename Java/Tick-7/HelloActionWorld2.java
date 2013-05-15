package uk.ac.cam.pv273.tick7;
import javax.swing.JFrame;       
import java.awt.event.ActionListener;
import javax.swing.JLabel;       
import java.awt.event.ActionEvent;
import javax.swing.JButton;      
import javax.swing.BoxLayout;

public class HelloActionWorld2 extends JFrame 
{

  HelloActionWorld2() 
  {
    super("Hello Action");                   //create window & set title text
    setDefaultCloseOperation(EXIT_ON_CLOSE); //close button on window quits app.
    //configure the layout of the pane associated with this window as a "BoxLayout"
    setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
    final JLabel label = new JLabel("Button unpressed");  //create graphical text label
    add(label);                              //associate "label" with window
    JButton button = new JButton("Press me");//create graphical button
    add(button);                             //associated "button" with window
    //create an instance of an anonymous inner class to hand the event
    button.addActionListener(new ActionListener()
    {
      int count = 0;
      public void actionPerformed(ActionEvent e) 
      {
        count++;
        label.setText("Button pressed " + count + " time(s)");
      }
    });
    setSize(320,240);                        //set size of window
  }

  public static void main(String[] args) 
  {
    HelloActionWorld2 hello = new HelloActionWorld2(); //create instance
    hello.setVisible(true);                          //display window to user
  }
}
