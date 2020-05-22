package src;

import java.io.*;
import java.net.InetAddress;

public class Packet implements Serializable {
    private int type;
    private InetAddress sender;
    private int num;
    private String commando;
    private byte[] data;
    private int id;

    public Packet(int type, int id, int num, byte[] data, InetAddress sender, String commando){
        this.id = id;
        this.type = type;
        this.num = num;
        this.sender = sender;
        this.data = data;
        this.commando = commando;
    }

    public Packet(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        Packet aux = (Packet) is.readObject();

        this.commando=aux.commando;
        this.type= aux.type;
        this.sender= aux.sender;
        this.num=aux.num;
        this.data=aux.data;
        this.id=aux.id;

    }

    public byte[] tobytes() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        oos.flush();
        return bos.toByteArray();
    }

    public int getType() {
        return type;
    }

    public int getNum() {
        return num;
    }

    public int getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

    public InetAddress getSender() {
        return sender;
    }

    public String getCommando() {
        return commando;
    }
}
