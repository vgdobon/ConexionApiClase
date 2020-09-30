package com.tecnara.weather.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    Socket socket;
    DataOutputStream dos;
    DataInputStream dis;
    Scanner sc= new Scanner(System.in);

    public Client()  {

        try {
            socket= new Socket("localhost",3334);
            dos= new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getInputParameters(){

        System.out.println("Give me the latitude");
        float lat= sc.nextFloat();
        System.out.println("Give me the length");
        float lon= sc.nextFloat();
        return "{\"lon\":"+ lon + ", \"lat\":" + lat + "}";
    }

    public void sendRequest(String msg)  {

        try {
            dos.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResponse() throws IOException {
          return dis.readUTF();
    }

    public void closeConnection(){
        try {
            if(dis != null)
                dis.close();
            if(dos != null)
                dos.close();
            if(socket != null)
                socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
