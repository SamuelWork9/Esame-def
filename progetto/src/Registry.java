import java.io.*;
import java.net.*;
import java.util.*;

public abstract class Registry extends RegistryData implements Serializable {
    public static int PORT = 8888;
    private static InetAddress Ipserver;
    private static int Portserver;
    private static Hashtable<String, Object> RmiRegistry;

    public Registry() throws UnknownHostException {
        super();
    }

    public boolean bind(String s, Object obj) {
        if(RmiRegistry.containsKey(s)) return false;
        RmiRegistry.put(s, obj);
        return true;
    }

    public Object lookup(String s){
        return RmiRegistry.get(s);
    }

    public void put(String addr, int port){

    }

    public static void main(String[] args) throws Exception {
        try {
            RmiRegistry = new Hashtable<>();
            ServerSocket serverSock = new ServerSocket(PORT);
            Socket sock;
            System.err.println("Registry waiting");

            while (true) {
                sock = serverSock.accept(); // waiting for connection
                System.out.println("Connection accepted from: " + sock);
                ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
                RegistryData data = (RegistryData) in.readObject();
                // server
                if(data.getPortServer() > 0) {
                    Ipserver = data.getIpServer();
                    Portserver = data.getPortServer();
                    RmiRegistry.put(data.getStr(), data.getObject());

                    System.out.println("Ip server: " + Ipserver.toString());
                    System.out.println("Port server: " + Portserver);
                    System.out.println("chiave hashtable: " + data.getStr());
                    System.out.println("Size: " + RmiRegistry.size());
                    sock.close();
                }
                // client
                else {
                    data.setIpServer(Ipserver);
                    data.setPortServer(Portserver);
                    data.setObject(RmiRegistry.get(data.getStr()));

                    System.out.println("Ip server: " + Ipserver);
                    System.out.println("Port server: " + Portserver);
                    System.out.println("chiave hashtable: " + data.getStr());
                    System.out.println("Size: " + RmiRegistry.size());

                    ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
                    out.writeObject(data);
                    sock.close();
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}