import java.net.*;
import java.io.*;

class ServerProxy implements ServerInterface {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ServerProxy() throws Exception {
        InetAddress addr = InetAddress.getByName(null);
        System.out.println("addr = " + addr);

        socket = new Socket(addr, ServerInterface.PORT);
        System.out.println("socket = " + socket);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

    public String echo(String string) throws IOException {
        return null;
    }

    public void close() throws IOException {
        System.out.println("closing");
        out.println("<end>");
        socket.close();
    }
}

