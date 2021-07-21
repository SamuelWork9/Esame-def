import java.io.*;
import java.net.*;
import java.util.*;

public class Client extends MsgClient implements Serializable {
    private final int PORTCLIENT = 8889;
    private String name;
    private String message;

    public Client() {
        Random gen = new Random();
        int a = gen.nextInt(1000);
        this.name = "Cliente_" + a;
        System.out.println("io sono il " + name);
    }

    /*public void setMessage(String message) {
        this.message = message;
    }*/

    public String getName() { return this.name; }

    public static void main(String[] args) {
        Socket sockCli;
        try {
            Client cli = new Client();
            InetAddress addr = InetAddress.getByName(null);
            System.err.println("Registry waiting");
            sockCli = new Socket(addr, Registry.PORT); //connection required

            System.out.println("Mi sono collegato al registry");

            RegistryData dataClient = new RegistryData();
            dataClient.setStr("ECHO SERVICE");

            ObjectOutputStream out = new ObjectOutputStream(sockCli.getOutputStream());
            out.writeObject(dataClient);
            // end request

            System.out.println("Ritorno da registry");

            ObjectInputStream in = new ObjectInputStream(sockCli.getInputStream());
            dataClient = (RegistryData) in.readObject();

            System.out.println("Ip server: " + dataClient.getIpServer());
            System.out.println("Port server: " + dataClient.getPortServer());
            System.out.println("Hashtable key: " + dataClient.getStr());

            sockCli = new Socket(addr, dataClient.getPortServer()); //connection required
            System.out.println("Mi sono collegato al server");

            System.out.println("ora mi aggiungo alla lista dei client!");
            ServerInterface server = (ServerInterface)dataClient.getObject();

            MsgClient MsgC = new MsgClient(cli.getName());

            while (true){
                MsgC.generaMessaggio();
                out = new ObjectOutputStream(sockCli.getOutputStream());
                out.writeObject(MsgC);

                System.out.println("Messaggio spedito al server!");
                //ritorno
                in = new ObjectInputStream(sockCli.getInputStream());
                MsgC = (MsgClient) in.readObject();
                System.out.println("messaggio dal server: " + MsgC.getEchoMessaggio());
                Thread.sleep(10000);
            }

        } catch (Exception e) { e.printStackTrace(); }
    }
}

