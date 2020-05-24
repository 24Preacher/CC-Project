import java.util.Comparator;

public class PacketNumComparator implements Comparator<Packet> {


    @Override
    public int compare(Packet o1, Packet o2) {
        return o1.getNum()-o2.getNum();
    }
}
