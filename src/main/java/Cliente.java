import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    Scanner sc = new Scanner(System.in);
    DataOutputStream dout;
    DataInputStream dis;
    FileOutputStream fos;
    Socket socket;

    public void setConection(){

        try {
            socket = new Socket("localhost",3333);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dout = new DataOutputStream(socket.getOutputStream());
            dis=new DataInputStream(socket.getInputStream());

//            fos.write(dis.read());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(){
        try {
            StringBuilder sb = createMessage();
            dout.writeUTF(String.valueOf(sb));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createFileData(){
        try {
            DataInputStream dis=new DataInputStream(socket.getInputStream());
//            fos = new FileOutputStream("Descargas/datosMetereológicos.txt");
//            fos.write(dis.read());
            String str=dis.readUTF();
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StringBuilder createMessage(){

        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("Coordenadas(longitud) ");
        stringBuilder.append(sc.next()).append(",");
        System.out.println("Coordenadas(latitud) ");
        stringBuilder.append(sc.next());
        return stringBuilder;
    }

    private void closeConnection() {


        try {
            dout.close();
            dis.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.setConection();
        cliente.sendMessage();

       cliente.createFileData();
       cliente.closeConnection();
    }




}