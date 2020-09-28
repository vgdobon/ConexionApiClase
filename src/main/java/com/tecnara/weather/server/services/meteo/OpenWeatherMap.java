package com.tecnara.weather.server.services.meteo;

import com.tecnara.weather.server.domain.Coordinates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenWeatherMap {

    final String url= "http://api.openweathermap.org/data/2.5/weather?lat=35.3&lon=139&appid=" +
                      "3e2d658a45d141292b9ac778c8b2dc32";

    public static String getCurrentWeather(Coordinates coordinates) throws IOException {

        String urlApi= "http://api.openweathermap.org/data/2.5/weather?lat=" + coordinates.getLat() +
                        "&lon=" + coordinates.getLon() + "&appid=3e2d658a45d141292b9ac778c8b2dc32";

        URL url = new URL(urlApi);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = bufferedReader.readLine()) != null) {
            content.append(inputLine);
        }
        bufferedReader.close();
        con.disconnect();

        return content.toString();
    }
}
