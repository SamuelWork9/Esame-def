import java.io.*;
import java.net.*;
import java.util.*;

public abstract class Registry extends RegistryData implements Serializable {
    public static int PORT = 8888;
    private static String IpServer;
    private static int PortServer;
    private static Hashtable<String, Object> RmiRegistry;

    public Registry() {}

    /*public boolean bind(String s, Object obj) {
        if(RmiRegistry.containsKey(s)) return false;
        RmiRegistry.put(s, obj);
        return true;
    }*/

    /*public Object lookup(String s){
        
        return RmiRegistry.get(s);
    }*/
    
    private static void bind(RegistryData data, Socket sock) throws IOException {
        data.setIpServer(IpServer);
        data.setPortServer(PortServer);
        data.setObject(RmiRegistry.get(data.getStr()));
    
        System.out.println("Ip server: " + IpServer);
        System.out.println("Port server: " + PortServer);
        System.out.println("chiave hashtable: " + data.getStr());
        System.out.println("Size: " + RmiRegistry.size());
    
        ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
        out.writeObject(data);
        sock.close();
    }
    
    private static void lookup(RegistryData data, Socket sock) throws IOException {
        IpServer = data.getIpServer();
        PortServer = data.getPortServer();
        RmiRegistry.put(data.getStr(), data.getObject());
        
        System.out.println("Ip server: " + IpServer);
        System.out.println("Port server: " + PortServer);
        System.out.println("chiave hashtable: " + data.getStr());
        System.out.println("Size: " + RmiRegistry.size());
        sock.close();
    }
    
    public void put(String addr, int port){

    }

    public static void main(String[] args) {
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
                if(data.getPortServer() > 0) lookup(data, sock);
                // client
                else { bind(data, sock); }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}