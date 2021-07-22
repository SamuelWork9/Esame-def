import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;

public abstract class Registry extends RegistryData implements Serializable {
	private static final int RegistryPORT = 8888;
	private static InetAddress IpServer;
	private static int PortServer;
	private static Hashtable<String, Object> RMIRegistry;
	
	public Registry() throws UnknownHostException {
		super();
	}
	
	public static void main(String[] args) {
		
		try {
			RMIRegistry = new Hashtable<>();
			ServerSocket serverSock = new ServerSocket(RegistryPORT);
			Socket sock;
			
			while (true) {
				System.err.println("Registry waiting");
				sock = serverSock.accept();
				
				System.out.println("Connection accepted from: " + sock);
				ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
				RegistryData data = (RegistryData) in.readObject();
				
				if (data.getPortServer() > 0) {
					// server
					bind(data);
				} else {
					// client
					lookup(data);
					
					ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
					out.writeObject(data);
				}
				sock.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void bind(RegistryData data) {
		IpServer = data.getIpServer();
		PortServer = data.getPortServer();
		RMIRegistry.put(data.getStr(), data.getObject());
		
		printer(data);
	}
	
	public static void lookup(RegistryData data) {
		data.setIpServer(IpServer);
		data.setPortServer(PortServer);
		data.setObject(RMIRegistry.get(data.getStr()));
		
		printer(data);
	}
	
	private static void printer(RegistryData data) {
		System.out.println("Ip server: " + IpServer.toString());
		System.out.println("Port server: " + PortServer);
		System.out.println("Hashtable key: " + data.getStr());
		System.out.println("Size: " + RMIRegistry.size());
	}
}