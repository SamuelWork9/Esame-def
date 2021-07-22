import java.io.IOException;
import java.net.Socket;

public interface ServerInterface {
    void echo(String Msg, Socket sock) throws IOException;
}
