import java.net.InetAddress;

public interface RegistryInterface {

    boolean bind(String s, Object obj);

    Object lookup(String s);

    void put(String addr, int port);

    InetAddress getIpServer();

    int getPortServer();
}
