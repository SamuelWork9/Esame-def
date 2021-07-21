import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class Proxy {
    private int PORTCLIENT;
    private InetAddress addr;
    private Socket sock;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private RegistryData dataClient;
    private Socket serverSocket;

    public Proxy(int PORTCLIENT, InetAddress addr) throws IOException, ClassNotFoundException {
        this.PORTCLIENT = PORTCLIENT;
        this.addr = addr;
        try {
            this.sock = new Socket(this.addr, this.PORTCLIENT);
            System.out.println(this.sock.toString());
            dataClient = new RegistryData();
            dataClient.setStr("ECHO SERVICE");

            this.serverSocket = new Socket(addr, lookup(addr, PORTCLIENT).getPortServer());
            System.out.println(this.serverSocket.toString());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public RegistryData lookup(InetAddress addr, int PORTCLIENT ) throws IOException, ClassNotFoundException {
        out = new ObjectOutputStream(sock.getOutputStream());
        out.writeObject(dataClient);
        in = new ObjectInputStream(sock.getInputStream());
        dataClient = (RegistryData) in.readObject();
        return dataClient;
    }

    public synchronized void exchangeMessage(Client cli) throws InterruptedException, IOException {
        Random gen = new Random();
        while (true){
            String MsgC = cli.getName() + gen.nextInt(100) + "\n";
            System.out.println("Stringa: " + MsgC);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
            out.write(MsgC);
            out.newLine();
            out.flush();

            System.out.println("Messaggio spedito al server! messaggio: " + MsgC);

            //ritorno
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            MsgC = in.readLine();
            System.out.println("messaggio dal server: " + MsgC);
            Thread.sleep(10000);
        }
    }
}

