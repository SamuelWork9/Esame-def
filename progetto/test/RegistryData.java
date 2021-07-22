import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class RegistryData implements Serializable {
    private InetAddress Ipserver;
    private int Portserver;
    private Object obj;
    private String str;

    RegistryData() throws UnknownHostException {
        this.Ipserver = InetAddress.getByName(null);
        this.Portserver = 0;
        this.obj = null;
        this.str = "";
    }

    //region getter
    public InetAddress getIpServer(){
        return this.Ipserver;
    }

    public int getPortServer(){
        return this.Portserver;
    }

    public Object getObject() {
        return this.obj;
    }

    public String getStr(){
        return this.str;
    }
    //endregion

    //region setter
    public void setIpServer(InetAddress ipServer) {
        this.Ipserver = ipServer;
    }

    public void setPortServer(int portServer) {
        this.Portserver = portServer;
    }
    
    public void setObject(Object obj) {
        this.obj = obj;
    }

    public void setStr(String str) {
        this.str = str;
    }
    //endregion
}