import java.io.*;
import java.net.*;
import java.util.Hashtable;

public class Server implements Serializable, ServerInterface {
    public static final int PORTSERVER = 8887;
    //public List<ClientInterface> cliList;
    private static Hashtable<String, MsgClient> cliList;
    //public static List<MsgClient> cliList;

    public Server() {
        cliList = new Hashtable<>();
        // cliList = new ArrayList<>();
    }

    public static synchronized void echo(String Msg, Socket sock) throws IOException {
        //String nomeCli = Msg;
        System.out.println(Thread.currentThread() + ": Server: ha ricevuto " + Msg);

        cliList.forEach((k, v) -> {
            v.setEchoMessaggio(Msg);
            System.out.println("nome: " + v.getNome());
            System.out.println("echomess: " + v.getEchoMessaggio());
            try {
                ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
                out.writeObject(v);
            } catch (Exception e) {
                System.out.println("Qualcuno e' uscito");
                if(!cliList.isEmpty()) cliList.remove(v);
            }
        });
    }

    public synchronized static void aggiornaLista(MsgClient cl){
        if (!cliList.containsKey(cl.getNome())) cliList.put(cl.getNome(), cl);
        System.out.println("dimensione della lista: " + cliList.size());
    }

    public static void main(String[] args) {
        ServerSocket serverSock;
        Socket sock;
        try {
            Server srv = new Server();
            InetAddress addr = InetAddress.getByName(null);
            serverSock = new ServerSocket(PORTSERVER);
            sock = new Socket(addr, Registry.PORT); // connection required

            RegistryData dataServer = new RegistryData();
            dataServer.setIpServer(addr.toString());
            dataServer.setPortServer(PORTSERVER);
            dataServer.setObject(srv);
            dataServer.setStr("ECHO SERVICE");

            ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
            out.writeObject(dataServer);

            sock = serverSock.accept(); // waiting for connection

            while (true) {
                System.out.println("Connessione accettata from client: " + sock);

                ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
                MsgClient MsgC = (MsgClient) in.readObject();

                aggiornaLista(MsgC);
                echo(MsgC.getMessage(), sock);
                System.out.println("Echo eseguito");
            }

        } catch (Exception e) { e.printStackTrace(); }
    }
}
