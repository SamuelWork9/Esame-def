import java.io.IOException;
import java.rmi.Remote;

public interface ServerInterface extends Remote {
    public static final int PORT = 8888;
    public String echo(String string) throws IOException;
}
