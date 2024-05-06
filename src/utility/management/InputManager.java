package utility.management;

import exceptions.ErrorInFunctionException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * Класс, отвечающий за ввод
 */

public class InputManager {
    private static InputManager instance;
    private final Deque<String> openedScripts = new ArrayDeque<>();
    private final Deque<BufferedInputStream> unfinishedStreams = new ArrayDeque<>();
    private Scanner receiver = new Scanner(System.in);
    private boolean inScript = false;
    private InputManager(){}
    /**
     * Переводит приложение в режим интерактивного ввода с консоли
     */
    public void setInteractiveMode() {
        receiver = new Scanner(System.in);
        receiver.useDelimiter("\n");
        inScript = false;
    }
    /**
     * Переводит приложение в режим считывания данных из файла
     * @param bis Поток входных данных
     */
    public void setFileMode(BufferedInputStream bis) {
        unfinishedStreams.add(bis);
        receiver = new Scanner(bis);
        receiver.useDelimiter("\n");
        inScript = true;
    }
    public void setPreviousMode() {
        if (openedScripts.isEmpty()) {
            setInteractiveMode();
        } else {
            setFileMode(unfinishedStreams.getLast());
            unfinishedStreams.removeLast();
            openedScripts.removeLast();
        }
    }
    public Scanner getReceiver() {
        return receiver;
    }
    public boolean getInScriptState() {
        return inScript;
    }
    public static InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }
    public Deque<String> getOpenedScripts() {
        return openedScripts;
    }
    public Deque<BufferedInputStream> getUnfinishedStreams() {
        return unfinishedStreams;
    }
}
