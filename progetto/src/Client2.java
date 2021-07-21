import java.io.Serializable;
import java.net.InetAddress;
import java.util.Random;

public class Client2 extends MsgClient implements Serializable {
    private static final int PORTCLIENT = 8889;
    private String name;
    //private String message;

    public Client2() {
        Random gen = new Random();
        int a = gen.nextInt(1000);
        this.name = "Cliente_" + a;
        System.out.println("io sono il " + this.name);
    }

    public String getName() {
        return this.name;
    }

    public static void main(String[] args) {
        try {
            Client cli = new Client();
            InetAddress addr = InetAddress.getByName((String)null);
            Proxy proxy = new Proxy(PORTCLIENT, addr);

            System.out.println("Mi sono collegato al registry");
            System.out.println("Mi sono collegato al server");
            System.out.println("ora mi aggiungo alla lista dei client!");

            proxy.exchangeMessage(cli);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}