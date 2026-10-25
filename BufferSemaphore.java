/********************************************************
Author: Patrick Vandergrift 
Date: Sept. 21st, 2026
********************************************************/

import java.util.concurrent.Semaphore;
import java.util.*;

//CLASS BufferSemaphore Description
/********************************************************
Purpose:  Simulate Semaphore buffer for produce and consume
Attributes (Private): counter (int), lockCounter (Semaphore),
  delay (Semaphore)
Methods: BufferSemaphore(), P(), V()
**********************************************************/
public class BufferSemaphore
{
  private int counter=0;
  private Semaphore lockCounter = new Semaphore(1);
  private Semaphore delay = new Semaphore(0);
  
  public BufferSemaphore(int counter)
  {
    this.counter = counter;
  }
  
  //P operation: decrements counter
  public void P()
  {
    try{
      lockCounter.acquire();
      counter--;
      if(counter < 0)
      {
        lockCounter.release();
        delay.acquire(); //Freezes
      }
      else
      {
        lockCounter.release();
      }
    }catch(InterruptedException e)
    {
      Thread.currentThread().interrupt();
    }
  }

  //V operation: increments counter
  public void V()
  {
    try{
      lockCounter.acquire();
      counter++;
      if(counter <= 0)
      {
        delay.release(); //Unlocks
      }
      lockCounter.release();
    }catch(InterruptedException e)
    {
      Thread.currentThread().interrupt();
    }
  }
  
}
