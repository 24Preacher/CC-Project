import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class GatewayListner extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[4*1024+224];
    private Buffer buffer;

    public GatewayListner(Buffer buffer) throws SocketException {
        socket = new DatagramSocket(6666);
        this.buffer=buffer;
    }

    public void run() {
        running = true;

        while (running) {
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                Packet pacote = new Packet(packet.getData());
                if(pacote.getType()==1){
                    //cenas
                    DatagramSocket resposta = new DatagramSocket();
                    String commando = pacote.getCommando();
                    System.out.println(commando);
                    Process p = Runtime.getRuntime().exec(commando);
                    p.waitFor();
                    File file = new File(commando.substring(commando.lastIndexOf("/")+1));
                    if (file.length()>4*1024){
                        long tamanho = file.length();
                        long trasferido =0;
                        FileInputStream fs = new FileInputStream(file);
                        BufferedInputStream bs = new BufferedInputStream(fs);
                        int num=1;
                        int num_framentos= (int) ((tamanho/(4*1024))+(tamanho%(4*1024)));
                        while(tamanho>trasferido){
                            if(trasferido+4*1024 > tamanho){
                                byte[] mb = new byte[(int) (tamanho - trasferido)];
                                bs.read(mb,0,mb.length);
                                Packet pacotefile = new Packet(2,1,num,num_framentos,mb,null);
                                byte[]bt = pacotefile.tobytes();
                                DatagramPacket dp = new DatagramPacket(bt,bt.length,address,6666);
                                resposta.send(dp);
                                trasferido+=4*1024;
                                num++;
                            }
                            byte[] mb = new byte[4*1024];
                            bs.read(mb,0,mb.length);
                            Packet pacotefile = new Packet(2,1, num,num_framentos,mb,null);
                            byte[]bt = pacotefile.tobytes();
                            DatagramPacket dp = new DatagramPacket(bt,bt.length,address,6666);
                            resposta.send(dp);
                            trasferido+=4*1024;
                            num++;
                        }

                        //lol
                    }else{
                        byte[] mb = new byte[(int)file.length()];
                        FileInputStream fs = new FileInputStream(file);
                        BufferedInputStream bs = new BufferedInputStream(fs);
                        bs.read(mb,0,mb.length);
                        Packet pacotefile = new Packet(2,1,0,1,mb,null);
                        byte[]bt = pacotefile.tobytes();
                        DatagramPacket dp = new DatagramPacket(bt,bt.length,address,6666);
                        resposta.send(dp);


                    }


                    resposta.close();
                }
                else if (pacote.getType()==2){
                    if(pacote.getNum()==0){
                        if(!buffer.isReady()) {
                            buffer.addPacket(pacote);
                        }
                        buffer.sort();
                        notifyAll();
                    }
                    else{
                       if(!buffer.isReady()) {
                            buffer.addPacket(pacote);
                            if (buffer.getsize()==pacote.getNum_fragmentos()){
                                buffer.sort();
                                notifyAll();
                           }
                        }

                        //o pacota fragmentado
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            //String received = new String(packet.getData(), 0, packet.getLength());
            //System.out.println(received);

            Arrays.fill(buf,(byte) 0 );

        }
        socket.close();
    }
}
