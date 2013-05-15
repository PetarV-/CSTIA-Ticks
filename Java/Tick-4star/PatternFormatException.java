package uk.ac.cam.pv273.tick4star;

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