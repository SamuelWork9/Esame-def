import java.io.Serializable;

public class MsgClient implements Serializable, ClientInterface {
    private static int contaMsg;
    private String nome;
    private String message;
    private String echoMessaggio;

    public MsgClient(){}

    public MsgClient(String name){
        contaMsg = 0;
        this.nome = name;
        this.message = "";
        this.echoMessaggio = "";
    }

    public synchronized void generaMessaggio() {
        this.message = nome + ": messaggio numero " + (++contaMsg);
        System.out.println("generated message: " + message);
    }

    public String getMessage() {
        return this.message;
    }

    public String getNome() {
        return this.nome;
    }

    public void setEchoMessaggio(String msg) {
        this.echoMessaggio = msg;
    }
    public String getEchoMessaggio(){
        return this.echoMessaggio;
    }
}
