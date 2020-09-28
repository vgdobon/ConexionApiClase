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
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3333);
        System.out.println("Listening...");

        while (true) {
            Socket socket = serverSocket.accept();

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String coordinatesMsg = dis.readUTF();
            System.out.println("The message from the says: " + coordinatesMsg);

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            if (Checker.checkFormat(coordinatesMsg)) {
                Coordinates coordinates = Utils.parseCoordinates(coordinatesMsg);
                if (Checker.checkRange(coordinates)) {
                    String result = OpenWeatherMap.getCurrentWeather(coordinates);
                    //Temperatura,humedad,tiempoPrincipal, descripcion, velocidad del viento , nombre de poblacion

                    Gson gson = new Gson();
                    JSONInfoClass jsonInfoClass = gson.fromJson(result,JSONInfoClass.class);
                    dos.writeUTF(String.valueOf(jsonInfoClass.toString()));
                    System.out.println("Resultado de openWeather: "+result);
                } else {
                    dos.writeUTF("The range isn't correct.");
                }

            } else {
                dos.writeUTF("The sintax isn't correct, write numbers.");
            }

            dis.close();
            dos.close();
            socket.close();

        }


    }
}
