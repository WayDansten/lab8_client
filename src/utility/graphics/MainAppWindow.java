package utility.graphics;

import stored_classes.Coordinates;
import stored_classes.Flat;
import stored_classes.House;
import stored_classes.enums.Furnish;
import stored_classes.enums.Transport;
import stored_classes.enums.View;
import utility.auxiliary.Validator;
import utility.management.ClientModule;
import utility.requests.RequestForData;
import utility.requests.RequestWithFlatCreation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainAppWindow extends Window {
    JTextArea consoleOutput;

    JPanel mainPanel;
    ClientModule clientModule = ClientModule.getInstance();
    Font titleFont = new Font("Dialog", Font.BOLD, 24);
    Font labelFont = new Font("Dialog", Font.PLAIN, 16);
    Font buttonFont = new Font("Dialog", Font.PLAIN, 13);
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension dimension = toolkit.getScreenSize();
    @Override
    public void display(JFrame frame) {
        class FlatCreationForm extends JFrame {
            public FlatCreationForm(String opCode) {
                JPanel auxPanel = new JPanel(new GridBagLayout());
                this.add(auxPanel);
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = 0;
                constraints.gridy = 0;
                constraints.gridwidth = 2;
                constraints.gridheight = 1;
                constraints.weightx = 0;
                constraints.weighty = 0;
                JLabel titleLabel = new JLabel("Введите данные");
                titleLabel.setFont(titleFont);
                auxPanel.add(titleLabel, constraints);
                constraints.gridy = 1;
                constraints.gridwidth = 1;
                JLabel nameLabel = new JLabel("Имя:");
                nameLabel.setFont(labelFont);
                auxPanel.add(nameLabel, constraints);
                constraints.gridy = 2;
                JLabel xLabel = new JLabel("Координата по X (X < 600):");
                xLabel.setFont(labelFont);
                auxPanel.add(xLabel, constraints);
                constraints.gridy = 3;
                JLabel yLabel = new JLabel("Координата по Y:");
                yLabel.setFont(labelFont);
                auxPanel.add(yLabel, constraints);
                constraints.gridy = 4;
                JLabel areaLabel = new JLabel("Площадь:");
                areaLabel.setFont(labelFont);
                auxPanel.add(areaLabel, constraints);
                constraints.gridy = 5;
                JLabel norLabel = new JLabel("Кол-во комнат:");
                norLabel.setFont(labelFont);
                auxPanel.add(norLabel, constraints);
                constraints.gridy = 6;
                JLabel furnishLabel = new JLabel("Мебель:");
                furnishLabel.setFont(labelFont);
                auxPanel.add(furnishLabel, constraints);
                constraints.gridy = 7;
                JLabel viewLabel = new JLabel("Вид из окна:");
                viewLabel.setFont(labelFont);
                auxPanel.add(viewLabel, constraints);
                constraints.gridy = 8;
                JLabel transportLabel = new JLabel("Транспортная доступность:");
                transportLabel.setFont(labelFont);
                auxPanel.add(transportLabel, constraints);
                constraints.gridy = 9;
                JLabel hnameLabel = new JLabel("Название дома:");
                hnameLabel.setFont(labelFont);
                auxPanel.add(hnameLabel, constraints);
                constraints.gridy = 10;
                JLabel yearLabel = new JLabel("Год строительства дома:");
                yearLabel.setFont(labelFont);
                auxPanel.add(yearLabel, constraints);
                constraints.gridy = 11;
                JLabel nofLabel = new JLabel("Кол-во этажей в доме:");
                nofLabel.setFont(labelFont);
                auxPanel.add(nofLabel, constraints);
                constraints.gridy = 12;
                JLabel nolLabel = new JLabel("Кол-во лифтов в доме:");
                nolLabel.setFont(labelFont);
                auxPanel.add(nolLabel, constraints);
                constraints.gridy = 1;
                constraints.gridx = 1;
                JTextField nameField = new JTextField();
                nameField.setPreferredSize(new Dimension(100, 20));
                auxPanel.add(nameField, constraints);
                constraints.gridy = 2;
                JTextField xField = new JTextField();
                xField.setPreferredSize(new Dimension(100, 20));
                auxPanel.add(xField, constraints);
                constraints.gridy = 3;
                JTextField yField = new JTextField();
                yField.setPreferredSize(new Dimension(100, 20));
                auxPanel.add(yField, constraints);
                constraints.gridy = 4;
                JTextField areaField = new JTextField();
                areaField.setPreferredSize(new Dimension(100, 20));
                auxPanel.add(areaField, constraints);
                constraints.gridy = 5;
                JTextField norField = new JTextField();
                norField.setPreferredSize(new Dimension(100, 20));
                auxPanel.add(norField, constraints);
                constraints.gridy = 6;
                String[] furnishChoices = new String[]{"NONE", "BAD", "LITTLE", "FINE", "DESIGNER"};
                JComboBox<String> furnishMenu = new JComboBox<>(furnishChoices);
                furnishMenu.setPreferredSize(new Dimension(100, 20));
                auxPanel.add(furnishMenu, constraints);
                constraints.gridy = 7;
                String[] viewChoices = new String[]{"BAD", "YARD", "STREET", "PARK", "NORMAL"};
                JComboBox<String> viewMenu = new JComboBox<>(viewChoices);
                viewMenu.setPreferredSize(new Dimension(100, 20));
                auxPanel.add(viewMenu, constraints);
                constraints.gridy = 8;
                String[] transportChoices = new String[]{"FEW", "LITTLE", "NORMAL", "ENOUGH"};
                JComboBox<String> transportMenu = new JComboBox<>(transportChoices);
                transportMenu.setPreferredSize(new Dimension(100, 20));
                auxPanel.add(transportMenu, constraints);
                constraints.gridy = 9;
                JTextField hnameField = new JTextField();
                hnameField.setPreferredSize(new Dimension(100, 20));
                auxPanel.add(hnameField, constraints);
                constraints.gridy = 10;
                JTextField yearField = new JTextField();
                yearField.setPreferredSize(new Dimension(100, 20));
                auxPanel.add(yearField, constraints);
                constraints.gridy = 11;
                JTextField nofField = new JTextField();
                nofField.setPreferredSize(new Dimension(100, 20));
                auxPanel.add(nofField, constraints);
                constraints.gridy = 12;
                JTextField nolField = new JTextField();
                nolField.setPreferredSize(new Dimension(100, 20));
                auxPanel.add(nolField, constraints);
                constraints.gridx = 0;
                constraints.gridy = 13;
                constraints.gridwidth = 2;
                JButton submitButton = new JButton("Подтвердить");
                submitButton.setFont(buttonFont);
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Flat flat = new Flat(nameField.getText(), new Coordinates(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText())), Double.parseDouble(areaField.getText()),
                                Integer.parseInt(norField.getText()), Furnish.naming.get(furnishMenu.getSelectedItem().toString()), View.naming.get(viewMenu.getSelectedItem().toString()),
                                Transport.naming.get(transportMenu.getSelectedItem().toString()),
                                new House(hnameField.getText(), Long.parseLong(yearField.getText()), Long.parseLong(nofField.getText()), Integer.parseInt(nolField.getText())));
                        if (!Validator.validate(flat)) {
                            consoleOutput.append("Форма имеет невалидные поля!\n");
                        } else {
                            RequestWithFlatCreation request = new RequestWithFlatCreation(opCode, "", clientModule.getUserData());
                            request.addExtraArgument(flat);
                            consoleOutput.append(clientModule.exchange(request));
                            FlatCreationForm.this.dispose();
                        }
                    }
                });
                auxPanel.add(submitButton, constraints);
            }
        }
        class SingleArgForm extends JFrame {
            public SingleArgForm(String opCode, String message) {
                JPanel auxPanel = new JPanel(new GridBagLayout());
                this.add(auxPanel);
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = 0;
                constraints.gridy = 0;
                constraints.gridwidth = 1;
                constraints.gridheight = 1;
                constraints.weightx = 0;
                constraints.weighty = 0;
                JLabel auxLabel = new JLabel(message);
                auxLabel.setFont(labelFont);
                auxPanel.add(auxLabel, constraints);
                constraints.gridy = 1;
                JTextField auxTextField = new JTextField();
                auxTextField.setPreferredSize(new Dimension(50, 20));
                auxPanel.add(auxTextField, constraints);
                constraints.gridy = 2;
                JButton auxButton = new JButton("Подтвердить");
                auxButton.setFont(buttonFont);
                auxButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        consoleOutput.append(clientModule.exchange(new RequestForData(opCode, auxTextField.getText(), clientModule.getUserData())) + "\n");
                        SingleArgForm.this.dispose();
                    }
                });
                auxPanel.add(auxButton, constraints);
            }
        }
        mainPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new GridLayout(13, 1));
        frame.add(mainPanel);
        frame.setBounds(dimension.width / 2 - 700, dimension.height / 2 - 400, 1400, 800);

        JButton addButton = new JButton("Добавить");
        addButton.setBackground(new Color(0, 255, 150));
        addButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 100)));
        addButton.setFont(buttonFont);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FlatCreationForm auxFrame = new FlatCreationForm("add");
                auxFrame.setBounds(dimension.width / 2 - 250,dimension.height / 2 - 250,500,500);
                auxFrame.setVisible(true);
            }
        });
        buttonPanel.add(addButton);

        JButton removeButton = new JButton("Удалить");
        removeButton.setBackground(new Color(0, 255, 150));
        removeButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 100)));
        removeButton.setFont(buttonFont);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SingleArgForm auxFrame = new SingleArgForm("remove_by_id", "Введите ID");
                auxFrame.setBounds(dimension.width / 2 - 150,dimension.height / 2 - 100,300,200);
                auxFrame.setVisible(true);
            }
        });
        buttonPanel.add(removeButton);

        JButton updateButton = new JButton("Обновить");
        updateButton.setBackground(new Color(0, 255, 150));
        updateButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 100)));
        updateButton.setFont(buttonFont);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FlatCreationForm auxFrame = new FlatCreationForm("update");
                auxFrame.setBounds(dimension.width / 2 - 250,dimension.height / 2 - 250,500,500);
                auxFrame.setVisible(true);
            }
        });
        buttonPanel.add(updateButton);

        JButton infoButton = new JButton("Информация");
        infoButton.setBackground(new Color(0, 255, 150));
        infoButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 100)));
        infoButton.setFont(buttonFont);
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consoleOutput.append(clientModule.exchange(new RequestForData("info", "", clientModule.getUserData())) + "\n");
            }
        });
        buttonPanel.add(infoButton);

        JButton helpButton = new JButton("Помощь");
        helpButton.setBackground(new Color(0, 255, 150));
        helpButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 100)));
        helpButton.setFont(buttonFont);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consoleOutput.append(clientModule.exchange(new RequestForData("help", "", clientModule.getUserData())) + "\n");
            }
        });
        buttonPanel.add(helpButton);

        JButton historyButton = new JButton("Лог команд");
        historyButton.setBackground(new Color(0, 255, 150));
        historyButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 100)));
        historyButton.setFont(buttonFont);
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consoleOutput.append(clientModule.exchange(new RequestForData("history", "", clientModule.getUserData())) + "\n");
            }
        });
        buttonPanel.add(historyButton);

        JButton clearButton = new JButton("Очистить");
        clearButton.setBackground(new Color(0, 255, 150));
        clearButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 100)));
        clearButton.setFont(buttonFont);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consoleOutput.append(clientModule.exchange(new RequestForData("clear", "", clientModule.getUserData())) + "\n");
            }
        });
        buttonPanel.add(clearButton);

        JButton filterByNameButton = new JButton("Отфильтровать по имени");
        filterByNameButton.setBackground(new Color(0, 255, 150));
        filterByNameButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 100)));
        filterByNameButton.setFont(buttonFont);
        filterByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SingleArgForm auxFrame = new SingleArgForm("filter_contains_name", "Введите подстроку");
                auxFrame.setBounds(dimension.width / 2 - 150,dimension.height / 2 - 100,300,200);
                auxFrame.setVisible(true);
            }
        });
        buttonPanel.add(filterByNameButton);

        JButton removeLowerButton = new JButton("Удалить меньшие по ID");
        removeLowerButton.setBackground(new Color(0, 255, 150));
        removeLowerButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 100)));
        removeLowerButton.setFont(buttonFont);
        removeLowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SingleArgForm auxFrame = new SingleArgForm("remove_lower", "Введите ID");
                auxFrame.setBounds(dimension.width / 2 - 150,dimension.height / 2 - 100,300,200);
                auxFrame.setVisible(true);
            }
        });
        buttonPanel.add(removeLowerButton);

        JButton removeGreaterButton = new JButton("Удалить большие по ID");
        removeGreaterButton.setBackground(new Color(0, 255, 150));
        removeGreaterButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 100)));
        removeGreaterButton.setFont(buttonFont);
        removeGreaterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SingleArgForm auxFrame = new SingleArgForm("remove_greater", "Введите ID");
                auxFrame.setBounds(dimension.width / 2 - 150,dimension.height / 2 - 100,300,200);
                auxFrame.setVisible(true);
            }
        });
        buttonPanel.add(removeGreaterButton);

        JButton countGreaterThanHouseButton = new JButton("Посчитать большие по House.year");
        countGreaterThanHouseButton.setBackground(new Color(0, 255, 150));
        countGreaterThanHouseButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 100)));
        countGreaterThanHouseButton.setFont(buttonFont);
        countGreaterThanHouseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SingleArgForm auxFrame = new SingleArgForm("count_greater_than_house", "Введите год строительства");
                auxFrame.setBounds(dimension.width / 2 - 150,dimension.height / 2 - 100,300,200);
                auxFrame.setVisible(true);
            }
        });
        buttonPanel.add(countGreaterThanHouseButton);

        JButton filterLessThanFurnishButton = new JButton("Отфильтровать по Furnish меньшим заданного");
        filterLessThanFurnishButton.setBackground(new Color(0, 255, 150));
        filterLessThanFurnishButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 100)));
        filterLessThanFurnishButton.setFont(buttonFont);
        buttonPanel.add(filterLessThanFurnishButton);

        JButton exitButton = new JButton("Закрыть программу");
        exitButton.setBackground(new Color(0, 255, 150));
        exitButton.setFont(buttonFont);
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 100)));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.WEST);

        consoleOutput = new JTextArea(10, 20);
        consoleOutput.setEditable(false);
        consoleOutput.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        consoleOutput.setBackground(Color.lightGray);
        JScrollPane consoleScrollPane = new JScrollPane(consoleOutput);
        mainPanel.add(consoleScrollPane, BorderLayout.SOUTH);

        String[] columnNames = new String[]{"ID", "Name", "Coordinates ID", "Creation Date", "Area", "Number of Rooms", "Furnish", "View", "Transport", "House ID", "User Login"};
        String[][] data = new String[][]{};
        JTable dataTable = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(dataTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
    @Override
    public void turnOff(JFrame frame) {
        frame.setVisible(false);
        frame.remove(mainPanel);
    }
}
