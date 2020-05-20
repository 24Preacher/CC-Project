package src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(4444);
        while(true) {
            Socket s = ss.accept();
            ConnectionHandler con = (new ConnectionHandler(s));
            pool.execute(con);
        }
    }
}
