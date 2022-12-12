package day06;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class RandomNumList implements Runnable  {
  private Integer count;
  private Integer limit;
  private List<Integer> numList;
  private Socket socket;
  
  // Constructor - take in socket obj as an argument for run() to send the output
  public RandomNumList(Integer count, Integer limit, List<Integer> numList, Socket socket){
    this.count = count;
    this.limit = limit;
    this.numList = numList;
    this.socket = socket;
  }

  //  Override run method
  @Override
  public void run(){
    String threadName = Thread.currentThread().getName();
    System.out.printf("Thread #%s\n",threadName);
    for(int i=0;i<count;i++){
      Random ranNo = new SecureRandom();
      numList.add(ranNo.nextInt(limit));
    }
    System.out.printf("From class: %s\n",numList.toString());
    try {
      DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
      dos.writeUTF(numList.toString());
      dos.flush();
      dos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    
    
  }
}
