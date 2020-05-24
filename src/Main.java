import java.net.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        InetAddress target_server = null;
        ArrayList<InetAddress> overlay_peers = new ArrayList<InetAddress>();
        String port ="";
        Buffer buffer = new Buffer();
        for (int i=0;i< args.length;i++){
            if(args[i].equals("target_server")){
                try {
                    target_server = InetAddress.getByName(args[++i]);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
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
                try {
                    overlay_peers.add(InetAddress.getByName(args[i]));
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("target_server: "+ target_server);
        System.out.println("port: "+ port);
        System.out.println("overlay_peers: " + overlay_peers.toString());
        try {
            Thread listner = new GatewayListner(buffer);
            listner.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            ServerSocket ss = new ServerSocket(80);
            while (true){
                Socket s = ss.accept();
                Cliente c = new Cliente(s.getInetAddress());
                System.out.println("Aceitou");
                System.out.println(c.toString());
                Thread t = new ConnectionHandler(s,overlay_peers,port,target_server,buffer);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
