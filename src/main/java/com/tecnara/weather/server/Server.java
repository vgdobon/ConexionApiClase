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
            serverSocket = new ServerSocket(3333);
            socket = serverSocket.accept();
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("Listening...");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getRequest(){

        String coordinatesMsg = null;
        try {
            coordinatesMsg = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coordinatesMsg;
    }

    public boolean checkFormat(String coordinatesMsg){

        return Checker.checkFormat(coordinatesMsg);
    }

    public boolean checkRange(Coordinates coordinates){

        return Checker.checkRange(coordinates);
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

    public static void main(String[] args) throws IOException {

        Server server = new Server();
        String coordinatesMsg = server.getRequest();


        while (true) {
            if (server.checkFormat(coordinatesMsg)) {
                Coordinates coordinates = server.toCoordinates(coordinatesMsg);
                if (server.checkRange(coordinates)) {
                    String result = OpenWeatherMap.getCurrentWeather(coordinates);
                    //Temperatura,humedad,tiempoPrincipal, descripcion, velocidad del viento , nombre de poblacion

                    Gson gson = new Gson();
                    JSONInfoClass jsonInfoClass = gson.fromJson(result,JSONInfoClass.class);
                    server.dos.writeUTF(jsonInfoClass.toString());
                    System.out.println("Resultado de openWeather: "+result);
                } else {
                    server.dos.writeUTF("The range isn't correct.");
                }

            } else {
                server.dos.writeUTF("The sintax isn't correct, write numbers.");
            }



        }
        server.closeConnection();

    }
}
