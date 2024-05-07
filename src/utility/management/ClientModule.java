package utility.management;

import exceptions.ErrorInFunctionException;
import utility.requests.Request;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ClientModule {
    private final InputManager inputManager = InputManager.getInstance();
    private final RequestManager requestManager = RequestManager.getInstance();
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket socket;
    public void launch() throws IOException{
        while (true) {
            int port = 5678;
            socket = new Socket("localhost", port);
            try {
                if (inputManager.getInScriptState() && !inputManager.getReceiver().hasNext()) {
                    inputManager.setPreviousMode();
                }
                String[] data = inputManager.getReceiver().nextLine().split(" ");
                if (data[0].equalsIgnoreCase("exit")) {
                    System.out.println("Завершение работы программы...");
                    closeConnection();
                    break;
                } else {
                    Request newRequest;
                    try {
                        if (data.length > 1) {
                            newRequest = requestManager.selectRequest(data);
                        } else {
                            newRequest = requestManager.selectRequest(data[0], "");
                        }
                    if (newRequest.extract()[0].equals("execute_script")) {
                        try {
                            if (!inputManager.getOpenedScripts().contains(data[1])) {
                                inputManager.setFileMode(new BufferedInputStream(new FileInputStream(newRequest.extract()[1])));
                            } else {
                                System.err.println("Обнаружена рекурсия! Дальнейшие скрипты выполнены не будут!");
                                inputManager.setInteractiveMode();
                                inputManager.getOpenedScripts().clear();
                                inputManager.getUnfinishedStreams().clear();
                            }
                            continue;
                        } catch (IOException e) {
                            System.err.println("Файл не найден!");
                        }
                    }
                    sendRequest(newRequest);
                    receiveAndExecuteRequest();
                    } catch (ClassNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Соединение не найдено!");
            } catch (NoSuchElementException e) {
                System.out.println("Завершение работы программы...");
                closeConnection();
                break;
            } catch (ErrorInFunctionException e) {
                System.err.println("Ошибка во время выполнения скрипта!");
                inputManager.setInteractiveMode();
            }
        }
    }
    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Соединение не найдено!");
        }
    }
    public void receiveAndExecuteRequest() throws IOException {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());
            Request request = (Request) ois.readObject();
            System.out.println(Arrays.toString(request.extract()));
        } catch (ClassNotFoundException e) {
            System.err.println("Класс не найден!");
        }
    }
    public void sendRequest(Request request) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(out);
        oos.writeObject(request);
        oos.flush();
        socket.getOutputStream().write(out.toByteArray());
    }
}
