package utility.management;

import utility.auxiliary.UserData;
import utility.requests.MessageRequest;
import utility.requests.Request;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ClientModule {
    private final InputManager inputManager = InputManager.getInstance();
    boolean authorizationSuccessful = false;
    private static ClientModule instance;
    private UserData userData;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket socket;
    private ClientModule() {}
    public static ClientModule getInstance() {
        if (instance == null) {
            instance = new ClientModule();
        }
        return instance;
    }
    public String exchange(Request request) {
        try {
            int port = 5678;
            socket = new Socket("localhost", port);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(out);
            oos.writeObject(request);
            oos.flush();
            socket.getOutputStream().write(out.toByteArray());
            ois = new ObjectInputStream(socket.getInputStream());
            MessageRequest response = (MessageRequest) ois.readObject();
            return response.extract()[0];
        } catch (IOException e) {
            return "Соединение не найдено или сервер временно недоступен";
        } catch (ClassNotFoundException e) {
            return "Тип запроса не найден!";
        }
    }
    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public UserData getUserData() {
        return userData;
    }
}
