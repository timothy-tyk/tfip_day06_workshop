package day06;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ListClient {
  public static void main(String[] args) {
    Integer num = Integer.parseInt(args[0]);
    Integer limit = Integer.parseInt(args[1]);
    String host = args[2];
    Integer PORT = Integer.parseInt(args[3]);

    try {
      Socket client = new Socket(host, PORT);
      //Output
      DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
      //Input
      DataInputStream dis = new DataInputStream(new BufferedInputStream(client.getInputStream()));
      String line;
      line = dis.readUTF();
        if(line.equals("connected")){
        System.out.printf("Connected to %s:%d\n",host,PORT);
        // Send number and Limit to Server
        dos.writeUTF(String.format("%d %d",num,limit));
        dos.flush();
        System.out.printf("Outgoing to Server: %d %d\n",num,limit);
      }
        //Get Random List from Server
        String fromServer = dis.readUTF();
        System.out.println(fromServer);

        fromServer = fromServer.replace("[", "").replace("]", "").trim();
        String[] numbs = fromServer.split(",");
        //Find Average of Random List
        Integer total = 0;
        for(String n:numbs){
          n=n.strip(); // Remove whitespace
          Integer number = Integer.parseInt(n);
          total+=number;
        }
        System.out.printf("\nThe average is %d\n",total/numbs.length);

        
    
      dis.close();
      
      


    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
