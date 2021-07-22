import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

import static java.lang.System.out;

public class Proxy {
	private Socket sock;
	private RegistryData dataClient;
	private Socket serverSocket;
	
	public Proxy(int RegistryPORT_Client, InetAddress address) {
		try {
			this.sock = new Socket(address, RegistryPORT_Client);
			out.println(this.sock);
			dataClient = new RegistryData();
			dataClient.setStr("ECHO SERVICE");
			
			this.serverSocket = new Socket(address, lookup().getPortServer());
			out.println(this.serverSocket);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public RegistryData lookup() throws IOException, ClassNotFoundException {
		ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
		out.writeObject(dataClient);
		ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
		dataClient = (RegistryData) in.readObject();
		return dataClient;
	}
	
	public synchronized void exchangeMessage(Client client) throws InterruptedException, IOException {
		Random gen = new Random();
		while (true) {
			String strClient = client.getName() + "_" + gen.nextInt(1000);
			out.println("Message generated: " + strClient);
			
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
			out.write(strClient);
			out.newLine();
			out.flush();
			
			System.out.println("Message sends to server: " + strClient);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			strClient = in.readLine();
			
			System.out.println("Message from server: " + strClient);
			System.out.println();
			Thread.sleep(5000);
		}
	}
}