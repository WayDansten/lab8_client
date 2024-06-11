import utility.graphics.AuthorizationWindow;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        AuthorizationWindow authorizationWindow = new AuthorizationWindow();
        authorizationWindow.display(frame);
    }
}