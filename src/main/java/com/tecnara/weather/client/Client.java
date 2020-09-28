package com.tecnara.weather.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) throws IOException {
        Scanner sc= new Scanner(System.in);
        System.out.println("Give me the latitude");
        float lat= sc.nextFloat();
        System.out.println("Give me the length");
        float lon= sc.nextFloat();
        String coordinates= "{\"lon\":"+ lon + ", \"lat\":" + lat + "}";


        Socket socket= new Socket("localhost",3333);

        DataOutputStream dos= new DataOutputStream(socket.getOutputStream());
        dos.writeUTF(coordinates);

        DataInputStream dis= new DataInputStream(socket.getInputStream());
        System.out.println("The server says: " + dis.readUTF());

        dis.close();
        dos.close();
        socket.close();
    }
}
