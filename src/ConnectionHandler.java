package src;
import java.io.*;
import java.net.Socket;

public class ConnectionHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private OutputStream out;
    public ConnectionHandler(Socket s) throws IOException {
        this.clientSocket=s;
        in =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = s.getOutputStream();
    }
    @Override
    public void run() {
        System.out.println("client connected");
        try{
            int i = 1;
            String st;
            String[] array;
            st = in.readLine();
            array = st.split(" ");
            System.out.println(i + st);
            i++;
            while (i < 6){
                st = in.readLine();
                System.out.println(i + st);
                i++;
            }
            Process p = Runtime.getRuntime().exec("wget http://10.3.3.1" + array[1]);
            p.waitFor();
            File file = new File(array[1].substring(1));
            byte[] mb = new byte[(int)file.length()];
            FileInputStream fs = new FileInputStream(file);
            BufferedInputStream bs = new BufferedInputStream(fs);
            bs.read(mb,0,mb.length);
            out.write(mb,0, mb.length);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}