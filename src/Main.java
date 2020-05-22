import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(80);
            while (true){
                Socket s = ss.accept();
                Cliente c = new Cliente(s.getInetAddress());
                System.out.println("Aceitou");
                System.out.println(c.toString());
                Thread t = new ConnectionHandler(s);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
