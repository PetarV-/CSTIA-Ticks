package uk.ac.cam.pv273.tick6;

public class PatternFormatException extends Exception
{
  private String message;
  public PatternFormatException(String Message)
  {
    message = Message;
  }
  public String getMessage()
  {
    return message;
  }
}
