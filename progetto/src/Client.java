import java.io.Serializable;
import java.net.InetAddress;
import java.util.Random;

public class Client implements Serializable {
    private static final int PORTCLIENT = 8888;
    private String name;

    public Client() {
        Random gen = new Random();
        int a = gen.nextInt(1000);
        this.name = "Cliente_" + a;
    }

    public String getName() {
        return this.name;
    }

    public static void main(String[] args) {
        try {
            Client cli = new Client();
            System.out.println(cli.getName());
            InetAddress addr = InetAddress.getByName(null);
            Proxy proxy = new Proxy(PORTCLIENT, addr);   //PORTCLIENT AL POSTO DI 8888.

            System.out.println("Mi sono collegato al registry");
            System.out.println("Mi sono collegato al server");
            System.out.println("ora mi aggiungo alla lista dei client!");

            proxy.exchangeMessage(cli);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
