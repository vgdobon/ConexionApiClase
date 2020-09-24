import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class MeteoApiConnection {

    URL url ;
    HttpURLConnection conn ;
    ReadableByteChannel inputChannel ;
    public MeteoApiConnection(String lat, String lon) {

        try {
            this.url =new URL("http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon="+ lon + "&appid=3e2d658a45d141292b9ac778c8b2dc32");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        this.url = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon="+ lon + "&appid=3e2d658a45d141292b9ac778c8b2dc32";
//        try {
//            this.inputChannel = Channels.newChannel(new URL(this.url).openStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public String askMeteoApi(){

        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        conn.setRequestProperty("Accept", "application/json");

        BufferedReader br=null;
        try {
            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        int cp;

        try {
            while ((cp = br.read()) != -1) {
                sb.append((char) cp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        return sb.toString();

    }


}
