import java.io.*;

public class Packet implements Serializable {
    private int type; //1- pedido do commando, 2 envio de ficheiro
    private int num; // if type == 2 {if fragmentado numero do framento else 0 } else -1
    private String commando; //if type == 1 isto é o comando else nada.
    private byte[] data; // if type == 2 fragmento do ficheiro, else nada
    private int id; // id do Packet sempre unico.
    private int num_fragmentos;

    public Packet (int type, String commando,int id){
        this.id=id;
        this.type=type;
        this.commando=commando;
        this.num =-1;
        this.data =null;
    }

    public Packet(int type, int id, int num,int num_fragmentos, byte[] data, String commando){
        this.id = id;
        this.type = type;
        this.num = num;
        this.data = data;
        this.commando = commando;
        this.num_fragmentos=num_fragmentos;
    }

    public Packet(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        Packet aux = (Packet) is.readObject();

        this.commando=aux.commando;
        this.type= aux.type;
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

    public int getNum_fragmentos() {
        return num_fragmentos;
    }

    public String getCommando() {
        return commando;
    }
}
