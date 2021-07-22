import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread implements ServerInterface {
    private  Server srv;
    private Socket sock;

    public ServerThread(Server srv, Socket sock) {
        this.srv = srv;
        this.sock = sock;
    }

    public synchronized void echo(String msg, Socket sock) throws IOException {
        System.out.println(Thread.currentThread() + ": Server: ha ricevuto " + msg);
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            out.write(msg + "\n");
            out.newLine();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        System.err.println("I'm running");
        BufferedReader in = null;
        String MsgC = null;
        while (true) {
            try {
                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                MsgC = in.readLine();
                System.out.println("messaggio ricevuto dal server: " + MsgC);
                echo(MsgC, sock);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Echo eseguito");
        }
    }
}
