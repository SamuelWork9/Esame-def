public class Client {
    private static int ID = 0;
    private String name;
    private String message;

    public Client(String name) {
        this.name = name + (++ID);
    }

    public String getName() {
        return name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static void main(String[] args) {
        Client client = new Client("Client");
        System.out.println(client.getName());

        Generatore gen = new Generatore(client.getName());
        client.setMessage(gen.getMessage());
        System.out.println(client.getMessage());
    }
}