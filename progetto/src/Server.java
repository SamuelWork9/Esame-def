import java.io.*;
import java.net.*;

public class Server implements Serializable, ServerInterface {
    public static final int PORTSERVER = 8887;

    public Server() {}

    public static void main(String[] args) {
        ServerSocket serverSock;
        Socket sock;
        try {
            Server srv = new Server();
            InetAddress addr = InetAddress.getByName(null);
            serverSock = new ServerSocket(PORTSERVER);
            sock = new Socket(addr, Registry.PORT); // connection required

            RegistryData dataServer = new RegistryData();
            dataServer.setIpServer(addr);
            dataServer.setPortServer(PORTSERVER);
            dataServer.setObject(srv);
            dataServer.setStr("ECHO SERVICE");

            ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
            out.writeObject(dataServer);

            while (true) {
                ServerThread srvThr = new ServerThread(srv, serverSock.accept());
                srvThr.start();
                System.err.println("Thread started");
            }

        } catch (Exception e) { e.printStackTrace(); }
    }
}
