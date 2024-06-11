package utility.graphics;

import utility.auxiliary.UserData;
import utility.management.ClientModule;
import utility.requests.AuthorizationRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthorizationWindow extends Window{
    private String login;
    private String password;
    Label messageLabel = new Label();
    JPanel panel = new JPanel();
    private boolean registrationFlag;
    ClientModule clientModule = ClientModule.getInstance();
    @Override
    public void display(JFrame frame) {
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        frame.add(panel);

        Font titleFont = new Font("Dialog", Font.BOLD, 24);
        Font labelFont = new Font("Dialog", Font.PLAIN, 16);
        Font buttonFont = new Font("Dialog", Font.PLAIN, 13);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        frame.setBounds(dimension.width / 2 - 300, dimension.height / 2 - 200, 600, 400);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.gridwidth = 2;
        Label label = new Label("Авторизация");
        label.setFont(titleFont);
        panel.add(label, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        Label loginLabel = new Label("Ваш логин:");
        loginLabel.setFont(labelFont);
        panel.add(loginLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        JTextField loginField = new JTextField();
        loginField.setPreferredSize(new Dimension(100, 20));
        panel.add(loginField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        Label passwordLabel = new Label("Ваш пароль:");
        passwordLabel.setFont(labelFont);
        panel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(100, 20));
        panel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridheight = 1;
        constraints.gridwidth = 2;
        messageLabel.setFont(labelFont);
        messageLabel.setPreferredSize(new Dimension(300, 20));
        panel.add(messageLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        JButton submitRegistrationButton = new JButton("Зарегистрироваться");
        submitRegistrationButton.setFont(buttonFont);
        submitRegistrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrationFlag = true;
                login = loginField.getText();
                password = new String(passwordField.getPassword());
            }
        });
        panel.add(submitRegistrationButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        JButton submitLoginButton = new JButton("Войти");
        submitLoginButton.setFont(buttonFont);
        submitLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrationFlag = false;
                login = loginField.getText();
                password = new String(passwordField.getPassword());
                clientModule.setUserData(new UserData(login, password));
                messageLabel.setText(clientModule.exchange(new AuthorizationRequest(login, password, registrationFlag)));
                if (messageLabel.getText().contains("Авторизация успешна!")) {
                    turnOff(frame);
                    new MainAppWindow().display(frame);
                }
            }
        });
        panel.add(submitLoginButton, constraints);

        frame.setVisible(true);
    }
    @Override
    public void turnOff(JFrame frame) {
        frame.setVisible(false);
        frame.remove(panel);
    }
}
