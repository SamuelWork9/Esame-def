import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Proxy {
    private int PORTCLIENT;
    private InetAddress addr;
    private Socket sock;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private RegistryData dataClient;

    public Proxy(int PORTCLIENT, InetAddress addr) throws IOException, ClassNotFoundException {
        this.PORTCLIENT = PORTCLIENT;
        this.addr = addr;
        this.sock = new Socket(this.addr, this.PORTCLIENT);
        this.dataClient = new RegistryData();
        this.dataClient.setStr("ECHO SERVICE");
        this.sock = new Socket(addr, this.lookup(addr, PORTCLIENT).getPortServer());
    }

    //lookup
    public RegistryData lookup(InetAddress addr, int PORTCLIENT) throws IOException, ClassNotFoundException {
        this.out = new ObjectOutputStream(this.sock.getOutputStream());
        this.out.writeObject(this.dataClient);
        this.in = new ObjectInputStream(this.sock.getInputStream());
        this.dataClient = (RegistryData)this.in.readObject();
        return this.dataClient;
    }

    public synchronized void exchangeMessage(Client cli) throws InterruptedException, IOException, ClassNotFoundException {
        MsgClient MsgC = new MsgClient(cli.getName());
        while(true) {
            MsgC.generaMessaggio();
            this.out = new ObjectOutputStream(this.sock.getOutputStream());
            this.out.writeObject(MsgC);
            System.out.println("Messaggio spedito al server!");
            this.in = new ObjectInputStream(this.sock.getInputStream());
            MsgC = (MsgClient)this.in.readObject();
            System.out.println("messaggio dal server: " + MsgC.getEchoMessaggio());
            Thread.sleep(10000);
        }
    }
}