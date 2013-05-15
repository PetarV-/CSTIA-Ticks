package uk.ac.cam.pv273.tick4;

public class Repeat
{
  public static void main(String[] args)
  {
    System.out.println(parseAndRep(args));
  }

  /*
   * Return the first string repeated by the number of times
   * specified by the integer in the second string, for example
   * 
   *    parseAndRep(new String[]{"one","3"})
   * 
   * should return "one one one". Adjacent copies of the repeated
   * string should be separated by a single space.
   *
   * Return a suitable error message in a string when there are
   * not enough arguments in "args" or the second argument is
   * not a valid positive integer. For example:
   *
   *  - parseAndRep(new String[]{"one"}) should return
   *    "Error: insufficient arguments"
   *
   *  - parseAndRep(new String[]{"one","five"}) should return
   *    "Error: second argument is not a positive integer"
   *
   */
   public static String parseAndRep(String[] args)
   {
     try
     {
       String str = args[0];
       int iter = Integer.parseInt(args[1]);
       if (iter <= 0) throw new NumberFormatException();
       String ret = "";
       for (int i=0;i<iter;i++)
       {
         ret += str;
         if (i < iter-1) ret += " ";
       }
       return ret;
     }
     catch (ArrayIndexOutOfBoundsException e)
     {
       return "Error: insufficient arguments";
     }
     catch (NumberFormatException e)
     {
       return "Error: second argument is not a positive integer";
     }
   }
}
