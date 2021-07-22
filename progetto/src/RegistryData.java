import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class RegistryData implements Serializable {
	private InetAddress ipServer;
	private int portServer;
	private String str;
	private Object obj;
	
	RegistryData() throws UnknownHostException {
		this.ipServer = InetAddress.getByName(null);
		this.portServer = 0;
		this.str = "";
		this.obj = null;
	}
	
	//region getter
	public InetAddress getIpServer() {
		return this.ipServer;
	}
	
	public int getPortServer() {
		return this.portServer;
	}
	
	public String getStr() {
		return this.str;
	}
	
	public Object getObject() {
		return this.obj;
	}
	//endregion
	
	//region setter
	public void setIpServer(InetAddress ipServer) {
		this.ipServer = ipServer;
	}
	
	public void setPortServer(int portServer) {
		this.portServer = portServer;
	}
	
	public void setStr(String str) {
		this.str = str;
	}
	
	public void setObject(Object obj) {
		this.obj = obj;
	}
	//endregion
}