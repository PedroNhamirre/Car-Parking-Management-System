package mz.ac.ucm.carparking.gui;

import mz.ac.ucm.carparking.domain.Login;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.sql.*;
import mz.ac.ucm.carparking.dao.LoginDAO;

public class LoginWindow extends JFrame implements ActionListener, FocusListener {

    ClassLoader ldr = this.getClass().getClassLoader();
    
    //Campo de definição de variáveis ou objectos    
    private final JLabel usernameIcon;
    private final JLabel passwordIcon;
    private final JComboBox typeOfUserDROPDOWN;
    private final JLabel loginText;
    private final JLabel carLogo;
    private final JTextField usernameField;
    private final JTextField showPasswordField;
    private final JButton logInButton;
    private final JButton showAndHidePasswordButton;
    private final JPasswordField passwordField;
    private final JPanel leftPanel;
    private final JPanel rightPanel;
    private final Color COLOR = new Color(0, 101, 112);

// criando bordas personalizadas
    private final Border outsideBorder = BorderFactory.createLineBorder(new Color(189, 186, 184), 1);
    private final Border insideBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    private final Border outsideBorderAfter = BorderFactory.createLineBorder(COLOR, 2);
    private final Border BORDER_AFTER = BorderFactory.createCompoundBorder(outsideBorderAfter, insideBorder);
    private final Border BORDER_BEFORE = BorderFactory.createCompoundBorder(outsideBorder, insideBorder);
    private ImageIcon eyeshow;

    //configurando o Construtor
    public LoginWindow() {
        setTitle("WELCOME");  // define o título da janela

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // quando clicado no "X" da janela, fecha  completamente
        setSize(900, 650);   // dimensões da janela
        setLocationRelativeTo(null);    //centraliza a janela
        setLayout(null); // define o layout null, todos itens serão colocados manualmente sem nenhum layout pre-definido
        setResizable(false);
        
        URL programIconURL = ldr.getResource("images/programIcon.png");
        ImageIcon programIcon = new ImageIcon(programIconURL);
        setIconImage(programIcon.getImage());
        
        //configurando os panels
        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 500, 650);
        leftPanel.setBackground(COLOR);

        rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBorder(BorderFactory.createLineBorder(COLOR, 1));
        rightPanel.setBounds(500, 0, 400, 650);
        rightPanel.setBackground(Color.white);

        //configurando ícones e logo
       

        URL userIconURL = ldr.getResource("images/login/user.png");
        ImageIcon userIcon = new ImageIcon(userIconURL);
        usernameIcon = new JLabel(userIcon);
        usernameIcon.setSize(500, 500);
        usernameIcon.setBounds(5, 260, 40, 40);

        URL passIconURL = ldr.getResource("images/login/password.png");
        ImageIcon passIcon = new ImageIcon(passIconURL);
        passwordIcon = new JLabel(passIcon);
        passwordIcon.setSize(500, 500);
        passwordIcon.setBounds(5, 345, 40, 40);

        URL carIconURL = ldr.getResource("images/login/carlogo.png");
        ImageIcon carIcon = new ImageIcon(carIconURL);
        carLogo = new JLabel(carIcon);
        carLogo.setBounds(0, 0, 500, 650);

        //configurando os textos
        loginText = new JLabel("Log In");
        loginText.setBounds(40, 60, 260, 65);
        loginText.setFocusable(true);
        loginText.setFont(new Font("Times New Roman", Font.BOLD, 60));

        //configurando os campos username e password
        usernameField = new JTextField();
        usernameField.setBounds(50, 260, 300, 40);
        usernameField.setText("Username");
        usernameField.setBorder(BORDER_BEFORE);
        usernameField.setForeground(Color.gray);
        usernameField.setFont(new Font("Arial", Font.BOLD, 12));

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 345, 300, 40);
        passwordField.setText("Insert your password here!");
        passwordField.setBorder(BORDER_BEFORE);
        passwordField.setForeground(Color.gray);
        passwordField.setFont(new Font("Arial", Font.BOLD, 12));

        showPasswordField = new JTextField();
        showPasswordField.setBounds(50, 345, 300, 40);
        showPasswordField.setText("Insert your password here!");
        showPasswordField.setBorder(BORDER_BEFORE);
        showPasswordField.setVisible(false);
        showPasswordField.setForeground(Color.gray);
        showPasswordField.setFont(new Font("Arial", Font.BOLD, 12));

        //configurando os botões
        logInButton = new JButton("Log In");
        logInButton.setBackground(COLOR);
        logInButton.setForeground(Color.white);
        logInButton.setFont(new Font("Arial", Font.BOLD, 18));
        logInButton.setFocusable(false);
        logInButton.setBorder(BORDER_AFTER);
        logInButton.setBounds(50, 420, 300, 50);

        URL eyeshowURL = ldr.getResource("images/login/eyeshow.png");
        eyeshow = new ImageIcon(eyeshowURL);
        showAndHidePasswordButton = new JButton("Show", eyeshow);
        showAndHidePasswordButton.setBorder(null);
        showAndHidePasswordButton.setBackground(Color.white);
        showAndHidePasswordButton.setForeground(COLOR);
        showAndHidePasswordButton.setFocusable(false);
        showAndHidePasswordButton.setFont(new Font("Arial", Font.BOLD, 14));
        showAndHidePasswordButton.setBounds(250, 320, 100, 25);

        String[] userType = {"Standard User", "Administrator"};
        typeOfUserDROPDOWN = new JComboBox(userType);
        typeOfUserDROPDOWN.setFocusable(false);
        typeOfUserDROPDOWN.setBounds(50, 210, 150, 35);
        typeOfUserDROPDOWN.setBorder(null);
        typeOfUserDROPDOWN.setBackground(Color.white);
        typeOfUserDROPDOWN.setForeground(Color.gray);
        typeOfUserDROPDOWN.setFont(new Font("Arial", Font.BOLD, 14));

        //adicionando itens ao painel
        leftPanel.add(carLogo);

        rightPanel.add(usernameIcon);
        rightPanel.add(passwordIcon);
        rightPanel.add(showAndHidePasswordButton);
        rightPanel.add(loginText);
        rightPanel.add(showPasswordField);
        rightPanel.add(usernameField);
        rightPanel.add(passwordField);
        rightPanel.add(typeOfUserDROPDOWN);
        rightPanel.add(logInButton);

        //adicionando eventos quando clicado nos campos e botoes
        typeOfUserDROPDOWN.addActionListener(this);
        showAndHidePasswordButton.addActionListener(this);
        logInButton.addActionListener(this);
        usernameField.addFocusListener(this);
        passwordField.addFocusListener(this);
        showPasswordField.addFocusListener(this);

        add(leftPanel);
        add(rightPanel);
        setVisible(true); //torna a janela visível
    }

    // métodos para configurar os eventos 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showAndHidePasswordButton) {
            if (showAndHidePasswordButton.getText().equalsIgnoreCase("Show")) {
                URL eyeshowURL = ldr.getResource("images/login/eyehide.png");
                eyeshow = new ImageIcon(eyeshowURL);
                showAndHidePasswordButton.setText("Hide");
                showAndHidePasswordButton.setIcon(eyeshow);

                passwordField.setVisible(false);
                showPasswordField.setVisible(true);
                showPasswordField.setText(String.valueOf(passwordField.getPassword()));

            } else if (showAndHidePasswordButton.getText().equalsIgnoreCase("Hide")) {
                URL eyeshowURL = ldr.getResource("images/login/eyeshow.png");
                eyeshow = new ImageIcon(eyeshowURL);
                showAndHidePasswordButton.setText("Show");
                showAndHidePasswordButton.setIcon(eyeshow);

                showPasswordField.setVisible(false);
                passwordField.setVisible(true);

                passwordField.setText(showPasswordField.getText());
            }

        }

        if (e.getSource() == logInButton) {
            autenticar();
        }

    }

    @Override
    public void focusGained(FocusEvent e) {

        if (e.getSource() == usernameField) {
            usernameField.setBorder(BORDER_AFTER);
            usernameField.setForeground(Color.BLACK);

            if (usernameField.getText().equalsIgnoreCase("Username")) {
                usernameField.setText("");
            }
        }

        if (e.getSource() == passwordField) {
            passwordField.setBorder(BORDER_AFTER);
            passwordField.setForeground(Color.BLACK);

            String GET_PASSWORD = String.valueOf(passwordField.getPassword());
            if (GET_PASSWORD.equalsIgnoreCase("Insert your password here!")) {
                passwordField.setText("");
            }
        }

        if (e.getSource() == showPasswordField) {
            showPasswordField.setBorder(BORDER_AFTER);
            showPasswordField.setForeground(Color.BLACK);

            String GET_PASSWORD = String.valueOf(passwordField.getPassword());
            if (GET_PASSWORD.equalsIgnoreCase("Insert your password here!")) {
                passwordField.setText("");
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == usernameField) {
            usernameField.setBorder(BORDER_BEFORE);
            usernameField.setText(usernameField.getText());
        }

        if (e.getSource() == passwordField) {
            passwordField.setBorder(BORDER_BEFORE);
            passwordField.setText(String.valueOf(passwordField.getPassword()));
        }

        if (e.getSource() == showPasswordField) {
            showPasswordField.setBorder(BORDER_BEFORE);
            showPasswordField.setText(showPasswordField.getText());
        }
    }

    private void autenticar() {
        String username = usernameField.getText();
        String password;
        String tipo = String.valueOf(typeOfUserDROPDOWN.getSelectedItem());

        if (showAndHidePasswordButton.getText().equalsIgnoreCase("hide")) {
            password = showPasswordField.getText();
        } else {
            password = String.valueOf(passwordField.getPassword());
        }

        try {
            Login user1 = new Login();
            user1.setUsername(username);
            user1.setU_password(password);
            user1.setTipo(tipo);

            LoginDAO daoUser = new LoginDAO();
            ResultSet resultUserDAO = daoUser.authUser(user1);

            if (resultUserDAO.next()) {

                if (tipo.equalsIgnoreCase("Standard User")) {
                    new PrincipalWindow();
                    PrincipalWindow.userButton.setEnabled(false);
                    this.dispose();
                }else{
                    new PrincipalWindow();
                    PrincipalWindow.userButton.setEnabled(true);
                    this.dispose();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Usuário ou senha errada", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "LOGIN: " + erro, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

}
