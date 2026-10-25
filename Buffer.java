/********************************************************
Author: Patrick Vandergrift
Date: Sept. 21, 2026
********************************************************/

import java.util.concurrent.Semaphore;
import java.util.Arrays;

//CLASS Buffer Description
/********************************************************
Purpose:  Makes the buffer and produce buffers
Attributes (Private): bufferSize (int), buffer (int[]),
  pIndex (int), cIndex (int), emptySlot (BufferSemaphore),
  filledSlot (BufferSemaphore), mutex (Semaphore)
Methods: Buffer(), produce(), consume()
**********************************************************/
public class Buffer
{
  private int bufferSize=0;
  private int[] buffer;
  private int pIndex = 0;
  private int cIndex = 0;
  private BufferSemaphore emptySlot;
  private BufferSemaphore filledSlot;
  private Semaphore mutex = new Semaphore(1);
  
  public Buffer(int size)
  {
    this.bufferSize = size;
    this.buffer = new int[bufferSize];
    this.emptySlot = new BufferSemaphore(bufferSize);
    this.filledSlot = new BufferSemaphore(0);
  }

  public void produce() throws InterruptedException
  {
    emptySlot.P();
    mutex.acquire();
    buffer[pIndex]++; //Filled Slot
    System.out.println("Producer Filled Slot: " + pIndex);
    pIndex = (pIndex + 1) % bufferSize;
    mutex.release();
    filledSlot.V();
  }
  
  public void consume() throws InterruptedException
  {
    filledSlot.P();
    mutex.acquire();
    buffer[cIndex]--; //Empty Slot
    System.out.println("Consumer Emptied Slot: " + cIndex);
    cIndex = (cIndex + 1) % bufferSize;
    mutex.release();
    emptySlot.V();
  }
}
