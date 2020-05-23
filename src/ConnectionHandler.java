package src;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private OutputStream out;
    private ArrayList<InetAddress> overlay_peers;
    private String port;
    private String target_server;
    public ConnectionHandler(Socket s, ArrayList<InetAddress> overlay_peers, String port, String target_server) throws IOException {
        this.clientSocket=s;
        in =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = s.getOutputStream();
        this.port=port;
        this.overlay_peers=overlay_peers;
        this.target_server=target_server;
    }
    @Override
    public void run() {
        System.out.println("client connected");
        try{
            DatagramSocket ds = new DatagramSocket();
            byte[] bt;
            int i = 1;
            String st;
            String[] array;
            st = in.readLine();
            array = st.split(" ");
            System.out.println(i + st);
            i++;
            bt =st.getBytes();
            DatagramPacket dp = new DatagramPacket(bt,bt.length, overlay_peers.get(0),6666);
            ds.send(dp);
            while (i < 6){
                st = in.readLine();
                DatagramPacket dip = new DatagramPacket(bt,bt.length, overlay_peers.get(0),6666);
                ds.send(dip);
                System.out.println(i + st);
                i++;
            }
            //Process p = Runtime.getRuntime().exec("wget http://10.3.3.1" + array[1]);
            //p.waitFor();
            //File file = new File(array[1].substring(1));
            //byte[] mb = new byte[(int)file.length()];
            //FileInputStream fs = new FileInputStream(file);
            //BufferedInputStream bs = new BufferedInputStream(fs);
            //bs.read(mb,0,mb.length);
            //out.write(mb,0, mb.length);
            //out.flush();
            //out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}