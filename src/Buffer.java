import java.util.ArrayList;

public class Buffer {
    private ArrayList<Packet> respostas;
    private boolean isReady;

    public Buffer() {
        this.respostas=new ArrayList<Packet>();
        this.isReady=false;
    }

    public synchronized void addPacket(Packet p){
        this.respostas.add(p);
    }

    public synchronized boolean isReady(){
        return this.isReady;
    }

    public synchronized Packet getPacket(){
        Packet p = this.respostas.get(0);
        this.respostas.remove(0);
        if(this.respostas.size()==0){
            this.isReady=false;
        }
        return p;

    }

    public synchronized int getsize(){
        return this.respostas.size();
    }

    public synchronized void sort(){
        respostas.sort(new PacketNumComparator());
        this.isReady=true;
    }

}
