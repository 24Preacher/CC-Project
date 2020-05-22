package src;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String target_server;
        ArrayList<String> overlay_peers = new ArrayList<String>();
        String port;
        for (int i=0;i< args.length;i++){
            if(args[i].equals("target-server")){
                target_server = args[++i];
            }
            else if(args[i].equals("port")){
                port = args[++i];
            }
            else if(args[i].equals("overlay_peers")){

            }
            else if (args[i].equals("-help")){
                System.out.println("java Main target_server <serverIp> port <portNum> overlay_peers <Sequence of Ips>");
            }
            else{
                overlay_peers.add(args[i]);
            }
        }
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
