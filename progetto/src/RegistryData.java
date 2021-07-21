import java.io.Serializable;

public class RegistryData implements RegistryInterface, Serializable {
    private String Ipserver;
    private int Portserver;
    private Object obj;
    private String str;

    RegistryData() {
        this.Ipserver = "";
        this.Portserver = 0;
        this.obj = null;
        this.str = "";
    }

    public boolean bind(String s, Object obj) {
        return false;
    }

    public Object lookup(String s) {
        return null;
    }

    public void put(String addr, int port) {

    }

    //region getter
    public String getIpServer(){
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
    public void setIpServer(String ipServer) {
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