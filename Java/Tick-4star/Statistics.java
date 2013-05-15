package uk.ac.cam.pv273.tick4star;

import java.util.List;
import java.util.LinkedList;

public class Statistics
{
  private double maximumGrowthRate;
  private double maximumDeathRate;
  private int loopStart;
  private int loopEnd;
  private int maximumPopulation;
  private int H,W;
  private List<boolean[][]> worlds = new LinkedList<boolean[][]>();

  private boolean compare(boolean[][] world1, boolean[][] world2)
  {
    for (int i=0;i<H;i++)
    {
      for (int j=0;j<W;j++)
      {
        if (world1[i][j] != world2[i][j]) return false;
      }
    }
    return true;
  }

  private int countPopulation(boolean[][] world)
  {
    int ret = 0;
    for (int i=0;i<H;i++)
    {
      for (int j=0;j<W;j++) 
      {
        if (world[i][j]) ret++;
      }
    }
    return ret;
  }

  public Statistics(boolean[][] world)
  {
    W = world[0].length;
    H = world.length;
    worlds.add(world);
    maximumGrowthRate = 0.0;
    maximumDeathRate = 0.0;
    loopStart = 0;
    loopEnd = 0;
    int previousPopulation = countPopulation(world);
    int currentPopulation;
    maximumPopulation = previousPopulation;
    int ii = 1;
    boolean done = false;
    while (!done)
    {
      world = StatisticsLife.nextGeneration(world);
      currentPopulation = countPopulation(world);
      double growthRate = ((currentPopulation - previousPopulation)*1.0) / (previousPopulation*1.0);
      double deathRate = -growthRate;
      if (growthRate > maximumGrowthRate) maximumGrowthRate = growthRate;
      if (deathRate > maximumDeathRate) maximumDeathRate = deathRate;
      if (currentPopulation > maximumPopulation) maximumPopulation = currentPopulation;
      int ind = 0;
      for (boolean[][] x : worlds)
      {
        if (compare(x,world))
        {
          loopStart = ind;
          loopEnd = ii - 1;
          done = true;
          break;
        }
        ++ind;
      }
      worlds.add(world);
      ii++; 
      previousPopulation = currentPopulation;
    }
    if (!done) loopStart = -1; //no loops found
  }

  public double getMaximumGrowthRate()
  {
    return maximumGrowthRate;
  }

  public double getMaximumDeathRate()
  {
    return maximumDeathRate;
  }

  public int getLoopStart()
  {
    return loopStart;
  }
  
  public int getLoopEnd()
  {
    return loopEnd;
  }

  public int getMaximumPopulation()
  {
    return maximumPopulation;
  }

}
