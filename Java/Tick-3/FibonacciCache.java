package uk.ac.cam.pv273.tick3;

public class FibonacciCache
{
  public static long[] fib = new long[20];

  public static void store()
  {
    if (fib.length > 0)
    {
      fib[0] = 1L;
      if (fib.length > 1)
      {
        fib[1] = 1L;
        for (int i=2;i<fib.length;i++) fib[i] = fib[i-1] + fib[i-2];
      }
    }
  }

  public static void reset()
  {
    for (int i=0;i<fib.length;i++) fib[i] = 0L;
  }

  public static long get(int i)
  {
    if (i < 0 || i > fib.length - 1) return -1L;
    return fib[i];
  }

  public static void main(String[] args)
  {
    store();
    for (int i=0;i<fib.length;i++) System.out.println(get(i));
    reset();
    for (int i=0;i<fib.length;i++) System.out.println(get(i));
  }
}
