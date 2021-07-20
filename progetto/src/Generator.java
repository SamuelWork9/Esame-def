public class Generator {
    private static int KEY = 0;
    private String message;

    public Generator(String nome) {
        message = nome + "_" + (++KEY);
        System.out.println("generated message: " + message);
    }

    public String getMessage() {
        return message;
    }
}
