/***********************************
Author: Patrick Vandergrift
Date: Sept. 21, 2026
Description: Fixes the Bounded Buffer Problem with semaphores
***********************************/
import java.util.*;
import java.util.Random;

public class Main 
{
  static Buffer buffers = new Buffer(2);
  
  public static void main(String[] args) 
  {
    System.out.println("Running Threads");
    Thread producer = new producerThread();
    Thread consumer = new consumerThread();
    producer.start();
    consumer.start();
  }

  static class producerThread extends Thread
  {
    public void run()
    {
      Random rand = new Random();
      for(int i=0; i < 5; i++)
      {
        int k1 = rand.nextInt(4) + 1;
        System.out.println("\nProducing " + k1);
        Coding Process ReportPatrick Vandergrift Page 1 of 17
        try
        {
          for(int j=0; j< k1; j++)
          {
            buffers.produce();
            Thread.sleep(100);
          }
        }catch(InterruptedException e){
          Thread.currentThread().interrupt();
        }
      }
    }
  }

  static class consumerThread extends Thread
  {
    public void run()
    {
      Random rand = new Random();
      for(int i=0; i<5; i++)
      {
        int k2 = rand.nextInt(4)+1;
        System.out.println("\nConsuming " + k2);
        try{
          for(int j=0; j< k2; j++)
          {
            buffers.consume();
            Thread.sleep(120);
          }
        }catch(InterruptedException e){
          Thread.currentThread().interrupt();
        }
      }
    }
  }
  
}
