package uk.ac.cam.pv273.tick5star;

import uk.ac.cam.acr31.life.World;
import uk.ac.cam.acr31.life.WorldViewer;
import uk.ac.cam.acr31.sound.AudioSequence;
import uk.ac.cam.acr31.sound.SineWaveSound;
import java.io.*;
import java.lang.Math;
import java.util.List;
import java.util.Random;
import java.net.URL;
import java.net.URLConnection;

public class SoundLife
{

  // 0 - Bass, 1 - Drums, 2 - Synths
  public static String[][] samples = {{"http://naruto-kun.com/petarv/WD_Born_001_Bass_144BPM_Em.wav","http://naruto-kun.com/petarv/WD_Gone_001_Bass_144BPM_Gm.wav","http://naruto-kun.com/petarv/WD_So_001_Bass_144BPM_Gm.wav"},{"http://naruto-kun.com/petarv/WD_Array_001_Full_144BPM.wav","http://naruto-kun.com/petarv/WD_Baba_001_Full_144BPM.wav","http://naruto-kun.com/petarv/WD_Wo_001_Full_144BPM.wav"},{"http://naruto-kun.com/petarv/WD_Baps_001_Synth_144BPM_Ebm.wav","http://naruto-kun.com/petarv/WD_Bitz_001_Synth_144BPM_Ebm.wav","http://naruto-kun.com/petarv/WD_Old_001_Synth_144BPM_Gm.wav"}};

  public static void insertMergedSample(int idBass, int idDrums, int idSynths, WavFile wF) throws Exception
  {
    URL destination;
    URLConnection conn;

    destination = new URL(samples[0][idBass]);
    conn = destination.openConnection();
    WavFile diBass = WavFile.openWavFile(conn);
    //diBass.display();
    int numChannelsBass = diBass.getNumChannels();
    double[] bufferBass = new double[100*numChannelsBass];
    int framesReadBass;

    destination = new URL(samples[1][idDrums]);
    conn = destination.openConnection();
    WavFile diDrums = WavFile.openWavFile(conn);
    //diDrums.display();
    int numChannelsDrums = diDrums.getNumChannels();
    double[] bufferDrums = new double[100*numChannelsDrums];
    int framesReadDrums;

    destination = new URL(samples[2][idSynths]);
    conn = destination.openConnection();
    WavFile diSynths = WavFile.openWavFile(conn);
    //diSynths.display();
    int numChannelsSynths = diSynths.getNumChannels();
    double[] bufferSynths = new double[100*numChannelsSynths];
    int framesReadSynths;

    double[] totalBuffer = new double[100*500];
    int ii = 0;
    do 
    {
      framesReadBass = diBass.readFrames(bufferBass,100);
      framesReadDrums = diDrums.readFrames(bufferDrums,100);
      framesReadSynths = diSynths.readFrames(bufferSynths,100);

      if (framesReadBass != 100 || framesReadDrums != 100 || framesReadSynths != 100) break; 
      
      for (int i=0;i<bufferBass.length;i++) 
      {
        totalBuffer[i] = (bufferBass[i] + bufferDrums[i] + bufferSynths[i])/3.0;
      }
      wF.writeFrames(totalBuffer,framesReadBass);
    } while (framesReadBass != 0);

    diBass.close();
    diDrums.close();
    diSynths.close();
  }

  public static void play(World t, int gCount, String wavName) throws Exception
  {
    int sampleRate = 44100;
    double duration = 48.0;
    long numFrames = (long)(sampleRate * duration);
    WavFile out = WavFile.newWavFile(new File(wavName), 2, numFrames, 16, sampleRate);
    long frameCounter = 0;
    Random r = new Random(214013);
    int x=0, y=0;
    for (int iter=0;iter<gCount;iter++)
    {
      for (int i=0;i<t.getHeight();i++)
      {
        for (int j=0;j<t.getWidth();j++) 
        {
    if (!t.getCell(j,i)) continue;
          x += i + j + r.nextInt(212);
          y += Math.abs(t.getWidth()-j) + Math.abs(t.getHeight()-i) + t.getGeneration() + r.nextInt(150);
          x %= samples[0].length;
          y %= samples[1].length;
        }
      }
      insertMergedSample(x%samples[0].length,y%samples[1].length,(x*y)%samples[2].length,out);
      t = t.nextGeneration(0);
    }
    out.close();
  }

  public static void play(World t, int gCount, String wavName, AudioSequence as) throws Exception
  {
    Random r = new Random(214013);
    int x=0, y=0;
    for (int iter=0;iter<gCount;iter++)
    {
      for (int i=0;i<t.getHeight();i++)
      {
        for (int j=0;j<t.getWidth();j++) 
        {
	  if (!t.getCell(j,i)) continue;
          x += i + j + r.nextInt(212);
          y += Math.abs(t.getWidth()-j) + Math.abs(t.getHeight()-i) + t.getGeneration() + r.nextInt(150);
          x %= samples[0].length;
          y %= samples[1].length;
        }
      }
      double x1 = (x*2.0 + y*3.0 + (x*y)*4.0)/5.0 + 1.0;
      while (x1 > 1.0) x1 /= 10.0;
      as.addSound(new SineWaveSound(x1,x1/2.0));
      as.advance();
      t = t.nextGeneration(0);
    }
    as.write(new FileOutputStream(wavName));
  }

  public static void main(String[] args) throws Exception
  {
    Random r = new Random();
    World w;

    Pattern p;
    int numGenerations;
    String wavName;

    p = new Pattern(args[0]);
    numGenerations = Integer.parseInt(args[1]);
    wavName = args[2];

    w = new AgingWorld(p.getWidth(), p.getHeight());
    p.initialise(w);

    try
    {
      play(w, numGenerations, wavName);
    }

    catch (Exception e)
    {
      AudioSequence as = new AudioSequence(0.1);
      play(w, numGenerations, wavName, as);
    }
  }
}
