import utility.management.ClientModule;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ClientModule clientModule = new ClientModule();
        try {
            clientModule.launch();
        } catch (IOException e) {
            System.err.println("Ошибка подключения к серверу!");
        }
    }
}