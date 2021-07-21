public interface RegistryInterface {

    boolean bind(String s, Object obj);

    Object lookup(String s);

    void put(String addr, int port);

    String getIpServer();

    int getPortServer();
}
