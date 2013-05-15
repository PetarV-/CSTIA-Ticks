package uk.ac.cam.pv273.tick1star;

public class InspectDouble
{
  public static void main(String[] args) throws Exception
  {
    double d = Double.parseDouble(args[0]);
    
    // return the bits which represent the floating point number
    long bits = Double.doubleToLongBits(d);
    
    // Sign bit located in bit 63
    // Format 1 => number is negative
    boolean negative = ( bits&0x8000000000000000L ) != 0;

    // Exponent located in bits 52 - 62
    // Format Sum(2^n * e(n)) - 1023 (binary number with bias)
    long exponent = ( (bits&0x7FF0000000000000L) >> 52 ) - 1023;

    // Mantissa located in bits 0 - 51
    // Format 1 + Sum(2^-(n+1)*m(n))
    long mantissabits = ( bits&0x000FFFFFFFFFFFFFL );
    
    double mantissa = mantissaToDecimal(mantissabits);

    System.out.println((negative ? "-" : "") + mantissa + " x 2^" + exponent);
  }

  private static double mantissaToDecimal(long mantissabits)
  {
    long one = 0x0010000000000000L;
    return (double)(mantissabits + one)/(double)one;
  }
}
