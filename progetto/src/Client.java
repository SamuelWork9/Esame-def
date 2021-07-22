import java.io.Serializable;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

public class Client implements Serializable {
	private static final int RegistryPORT_Client = 8888;
	private static AtomicInteger count = new AtomicInteger(0);
	private final String name;
	
	public Client() { // TODO non funziona il sequenziale, tutti client1
		name = "Client" + count.incrementAndGet();
	}
	
	public static void main(String[] args) {
		try {
			Client client = new Client();
			out.println(client.getName());
			
			InetAddress address = InetAddress.getByName(null);
			Proxy proxy = new Proxy(RegistryPORT_Client, address);
			
			out.println("I connected to the REGISTRY");
			out.println("I connected to the SERVER");
			out.println("I add myself to the client list!");
			out.println();
			
			proxy.exchangeMessage(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return this.name;
	}
}