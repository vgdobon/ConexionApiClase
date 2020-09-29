package com.tecnara.weather.client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Client client = new Client();
        client.sendRequest(client.getInputParameters());
        System.out.println(client.getResponse());
        client.closeConnection();

    }
}
