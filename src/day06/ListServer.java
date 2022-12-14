package day06;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListServer {
  public static void main(String[] args) {
    Integer PORT = Integer.parseInt(args[0]);
    // System.out.println(args[0]);
    try {
      ServerSocket server = new ServerSocket(PORT);
      System.out.printf("Listening on PORT %s\n",PORT);

      //Start Threads
      ExecutorService threadpool = Executors.newFixedThreadPool(3);

      while(true){
        //wait for a connection
        System.out.println("Waiting for connections.");
        Socket socket = server.accept();
        System.out.printf("New Connection on PORT:%s\n",socket.getPort());
        // Open input and output - ORDER IS VERY IMPORTANT(Client and Server must be opposite!! In/Out, Out/In)
        //Input
        DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        //Output
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dos.writeUTF("connected");
        dos.flush();
        
        //Recieves Num and Limit input from Client
        String incoming = dis.readUTF();
        if(incoming!=null){
          String[] fromClient = incoming.split(" ");
          Integer n = Integer.parseInt(fromClient[0]);
          Integer limit = Integer.parseInt(fromClient[1]);

          System.out.printf("Recieved: %d, %d\n",n,limit);

          List<Integer> randomNumbers = new LinkedList<Integer>();
          // Send RandomNumList to Client by assigning thread to run, pass in socket so that the run method can do the output sending
          RandomNumList thr = new RandomNumList(n, limit, randomNumbers, socket);
          threadpool.submit(thr);

          System.out.println("Sending Random List to Client.");
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
   


  }
  
  public static List<Integer> getRandomNumbers(Integer n, Integer limit){
    List<Integer> randomNumbers = new LinkedList<Integer>();
    for(int i=0;i<n;i++){
      Random ran = new Random();
      Integer randNo = ran.nextInt(limit);
      randomNumbers.add(randNo);
    }
    return randomNumbers;
  }



}


//java day06.ListServer 8080
//"Listening on port 8080"

// Convert the server to multithreaded???