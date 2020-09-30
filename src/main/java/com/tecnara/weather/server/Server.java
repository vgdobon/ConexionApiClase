package com.tecnara.weather.server;

import com.google.gson.Gson;
import com.tecnara.weather.server.domain.Coordinates;
import com.tecnara.weather.server.services.meteo.JSONInfoClass;
import com.tecnara.weather.server.services.meteo.OpenWeatherMap;
import com.tecnara.weather.server.utils.Checker;
import com.tecnara.weather.server.utils.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;

    public Server(){
        try {
            serverSocket = new ServerSocket(3334);
            System.out.println("Listening...");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Coordinates getRequestCoordinates(){
        try {
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Coordinates coordinates = null;
        String coordinatesMsg;

        try {

            coordinatesMsg = dis.readUTF();
            if (Checker.checkFormat(coordinatesMsg)){
                coordinates = toCoordinates(coordinatesMsg);
                if (!Checker.checkRange(coordinates)) {
                    dos.writeUTF("The range isn't correct.");
                }
            } else {
                dos.writeUTF("The sintax isn't correct, write numbers.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return coordinates;
    }

    public JSONInfoClass sendResponse(String jsonApiWeather){


        Gson gson = new Gson();
        JSONInfoClass jsonInfoClass = gson.fromJson(jsonApiWeather,JSONInfoClass.class);
        return jsonInfoClass;
    }


    public Coordinates toCoordinates(String coordinatesMsg){
        return Utils.parseCoordinates(coordinatesMsg);
    }

    public void closeConnection(){
        try {
            dis.close();
            dos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run(){
        while(true){
            try {
                socket = serverSocket.accept();
                Coordinates coordinates = getRequestCoordinates();
                String result = OpenWeatherMap.getCurrentWeather(coordinates);
                dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF(sendResponse(result).toString());
                closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ;
        }

    }

    public static void main(String[] args) throws IOException {

        Server server = new Server();

        server.run();


        }


}
