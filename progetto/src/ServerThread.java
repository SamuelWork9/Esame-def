import java.io.*;
import java.net.Socket;

import static java.lang.System.out;

public class ServerThread extends Thread implements ServerInterface {
	private final Socket sock;
	
	public ServerThread(Socket sock) {
		this.sock = sock;
	}
	
	public synchronized void echo(String msg, Socket sock) throws IOException {
		out.println(Thread.currentThread().getName() + " | server has received: " + msg); // todo era senza getname
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			out.write(msg + "\n");
			out.newLine();
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		String MsgC;
		BufferedReader in;
		
		System.err.println(", i'm running");
		while (true) {
			try {
				in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				MsgC = in.readLine();
				
				out.println("Message received from the client: " + MsgC); //todo era messaggio from server
				echo(MsgC, sock);
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.println("echo performed");
			out.println();
		}
	}
}