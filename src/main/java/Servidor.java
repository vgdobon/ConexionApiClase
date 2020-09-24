import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    static Checker checker;
    static MeteoApiConnection meteoApiConnection;

    private static void startServer() {

        ServerSocket ss = null;
        try {
            ss = new ServerSocket(3333);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Escuchando peticiones...");

        Socket s = null;
        try {
            s = ss.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Petición escuchada");

        DataInputStream din = null;
        try {
            din = new DataInputStream(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String str = null;
        try {
            str = din.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] coordenadas = str.split(",");

        checker = new Checker();
        if(checker.checkCoordinates(coordenadas)){
            meteoApiConnection=new MeteoApiConnection(coordenadas[0],coordenadas[1]);
        }else{
            System.out.println("Las coordenadas no son correctas");
        }

        DataOutputStream dout = null;
        try {
            dout = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dout.writeUTF(meteoApiConnection.askMeteoApi());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            din.close();
            dout.close();
            s.close();
            ss.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public static void main(String[] args) {

        startServer();
    }

}
