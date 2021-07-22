import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Serializable {
	private static final int ServerPORT = 8887;
	private static final int RegistryPORT = 8888;
	
	public Server() {
	}
	
	public static void main(String[] args) {
		Server srv = new Server();
		
		try {
			ServerSocket serverSocket = new ServerSocket(ServerPORT);
			InetAddress address = InetAddress.getByName(null);
			Socket socket = new Socket(address, RegistryPORT);
			
			RegistryData dataServer = new RegistryData();
			dataServer.setIpServer(address);
			dataServer.setPortServer(ServerPORT);
			dataServer.setObject(srv);
			dataServer.setStr("ECHO SERVICE");
			
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(dataServer);
			
			while (true) {
				ServerThread serverT = new ServerThread(srv, serverSocket.accept());
				serverT.start();
				System.err.println("Thread started");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
