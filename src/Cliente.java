package src;
import java.net.InetAddress;

public class Cliente {

    private InetAddress ip;

    public Cliente(InetAddress ip) {
        this.ip = ip;
    }

    public String toString() {
        return "Cliente{" +
                "ip=" + ip +
                '}';
    }

}
