package uk.ac.cam.pv273.tick3;

public class ReferenceTest
{
  public static void main(String[] args)
  {
    int[][] i = new int[2][2];
    int[][] j = {i[1], {1,2,3}, {4,5,6,7}};
    int[][][] k = {i,j};
    System.out.println(k[0][1][0]++);
    System.out.println(++k[1][0][0]);
    System.out.println(i[1][0]);
    System.out.println(--j[0][0]);
  }
}
