import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    public ConnectionHandler(Socket s) throws IOException {
        this.clientSocket=s;
        in =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(),true);
    }
    @Override
    public void run() {
        System.out.println("client connected");
        String str = null;
        try{
        while (true) {
            str = in.readLine();
            System.out.println("cliente disse : " + str);
            if (str.equals("quit"))
                break;
        }
        }catch (Exception e){
            System.err.println(e);
        }finally {
            out.close();
            try {
                clientSocket.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
