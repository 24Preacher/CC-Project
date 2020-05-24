import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ConnectionHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private OutputStream out;
    private ArrayList<InetAddress> overlay_peers;
    private Buffer buffer;

    private String port;
    private InetAddress target_server;
    public ConnectionHandler(Socket s, ArrayList<InetAddress> overlay_peers, String port, InetAddress target_server, Buffer buffer) throws IOException {
        this.clientSocket=s;
        in =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = s.getOutputStream();
        this.port=port;
        this.overlay_peers=overlay_peers;
        this.target_server=target_server;
        this.buffer=buffer;
    }
    @Override
    public void run() {
        System.out.println("client connected");
        try{
            DatagramSocket ds = new DatagramSocket();
            int i = 1;
            String st;
            String[] array;
            st = in.readLine();
            array = st.split(" ");
            System.out.println(i + st);
            i++;
            Packet pacote = new Packet(1,"wget http:/"+ target_server + array[1],1);
            byte[]bt = pacote.tobytes();
            Random rand = new Random();
            DatagramPacket dp = new DatagramPacket(bt,bt.length, overlay_peers.get(rand.nextInt( overlay_peers.size() )),6666);
            ds.send(dp);
            while (i < 6){
                st = in.readLine();
                System.out.println(i + st);
                i++;
            }

            while (!buffer.isReady()){
                wait();
            }
            while (buffer.isReady()){
                Packet p = buffer.getPacket();
                byte[] mb = p.getData();
                out.write(mb,0, mb.length);

                System.out.println("enviar para cliente :" + p.toString());
            }
            if (!buffer.isReady())
            notifyAll();
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}