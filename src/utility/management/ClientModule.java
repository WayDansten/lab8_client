package utility.management;

import exceptions.ErrorInFunctionException;
import utility.auxiliary.UserData;
import utility.requests.AuthorizationRequest;
import utility.requests.MessageRequest;
import utility.requests.Request;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ClientModule {
    private final InputManager inputManager = InputManager.getInstance();
    private final RequestManager requestManager = RequestManager.getInstance();
    private UserData userData;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket socket;
    public void launch(){
        while (true) {
            try {
                int port = 5678;
                socket = new Socket("localhost", port);
            } catch (IOException e) {
                System.err.println("Сервер временно недоступен. Пожалуйста, повторите попытку позже");
                break;
            }
            try {
                if (inputManager.getInScriptState() && !inputManager.getReceiver().hasNext()) {
                    inputManager.setPreviousMode();
                }
                if (userData == null) {
                    boolean authorizationSuccessful = false;
                    while (!authorizationSuccessful) {
                        boolean registrationFlag;
                        while (true) {
                            System.out.println("Необходима авторизация. Введите '1' для регистрации или любое другое число для входа в систему:");
                            try {
                                registrationFlag = Integer.parseInt(inputManager.getReceiver().nextLine().strip()) == 1;
                                break;
                            } catch (NumberFormatException e) {
                                System.err.println("Самый умный?");
                            }
                        }
                        System.out.println("Введите логин:");
                        String login = inputManager.getReceiver().nextLine();
                        System.out.println("Введите пароль:");
                        String password = inputManager.getReceiver().nextLine();
                        MessageDigest md;
                        try {
                            md = MessageDigest.getInstance("SHA-224");
                            String pepper = "e>1H:x0@Zm";
                            byte[] hash = md.digest((password + pepper).getBytes(StandardCharsets.UTF_8));
                            String hashedPassword = String.format("%032x", new BigInteger(1, hash));
                            AuthorizationRequest authRequest = new AuthorizationRequest(login, hashedPassword, registrationFlag);
                            userData = authRequest.getUserData();
                            sendRequest(authRequest);
                            authorizationSuccessful = checkAuthorization();
                        } catch (NoSuchAlgorithmException e) {
                            System.out.println("Несуществующий алгоритм хеширования");
                        }
                    }
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
                            newRequest = requestManager.selectRequest(data[0], data[1], userData.login(), userData.password());
                        } else {
                            newRequest = requestManager.selectRequest(data[0], "", userData.login(), userData.password());
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
                System.err.println("Соединение не найдено или сервер временно недоступен");
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
    public boolean checkAuthorization() throws IOException{
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());
            MessageRequest request = (MessageRequest) ois.readObject();
            if (request.extract()[0].contains("Авторизация успешна!")) {
                System.out.println(request.extract()[0]);
                return true;
            }
            System.out.println(request.extract()[0]);
            return false;
        } catch (ClassNotFoundException e) {
            System.err.println("Класс не найден!");
            return false;
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
