package mz.ac.ucm.carparking.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.sql.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import mz.ac.ucm.carparking.connection.ConnectionFactory;
import mz.ac.ucm.carparking.dao.CarroDAO;
import mz.ac.ucm.carparking.dao.ClienteDAO;
import mz.ac.ucm.carparking.dao.FuncionarioDAO;
import mz.ac.ucm.carparking.dao.LoginDAO;
import mz.ac.ucm.carparking.dao.SlotDAO;
import mz.ac.ucm.carparking.domain.Carro;
import mz.ac.ucm.carparking.domain.Cliente;
import mz.ac.ucm.carparking.domain.Funcionario;
import mz.ac.ucm.carparking.domain.Login;
import mz.ac.ucm.carparking.domain.Slot;

public class PrincipalWindow extends JFrame implements ActionListener, MouseListener, FocusListener {

    ClassLoader ldr = this.getClass().getClassLoader();
    
    private final Color COLOR = new Color(0, 101, 112);
    private final Border outsideBorder = BorderFactory.createLineBorder(new Color(189, 186, 184), 1);
    private final Border insideBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    private final Border outsideBorderAfter = BorderFactory.createLineBorder(COLOR, 2);
    private final Border BORDER_AFTER = BorderFactory.createCompoundBorder(outsideBorderAfter, insideBorder);
    private final Border BORDER_BEFORE = BorderFactory.createCompoundBorder(outsideBorder, insideBorder);

    //variaveis painel esquerdo
    private final JPanel leftPanel;
    private final JLabel iconLabel;
    private final JButton homeButton;
    private final JButton lotButton;
    private final JButton clientButton;
    public static JButton userButton;
    private final JButton carButton;
    private final JButton logoutButton;
    URL homeIconURL = ldr.getResource("images/principal/leftpanel/homeIcon.png");
    private final ImageIcon homeIcon = new ImageIcon(homeIconURL);
    URL spotIconURL = ldr.getResource("images/principal/leftpanel/spot.png");
    private final ImageIcon spotIcon = new ImageIcon(spotIconURL);
    URL clientIconURL = ldr.getResource("images/principal/leftpanel/client.png");
    private final ImageIcon clientIcon = new ImageIcon(clientIconURL);
    URL usersIconURL = ldr.getResource("images/principal/leftpanel/user.png");
    private final ImageIcon usersIcon = new ImageIcon(usersIconURL);
    URL carsIconURL = ldr.getResource("images/principal/leftpanel/car.png");
    private final ImageIcon carsIcon = new ImageIcon(carsIconURL);
    URL logoutIconURL = ldr.getResource("images/principal/leftpanel/logout.png");
    private final ImageIcon logoutIcon = new ImageIcon(logoutIconURL);

    //HOME
    private final JPanel rightPanelHome;
    private JLabel availableLotLabel;
    private final JLabel textAvailableLots = new JLabel("LOT's AVAILABLE");
    private JLabel textAvailableLotNumber;
    private final DefaultTableModel funcionarioTableModel = new DefaultTableModel();
    private JTable funcionarioTable;

    //SPOTS
    private final JPanel rightPanelLot;
    private JLabel lotTextTitle;
    private JTable lotTable;
    private JButton refreshLotButton;
    private JButton createLotButton;
    private JButton deleteLotButton;
    private final DefaultTableModel lotTableModel = new DefaultTableModel();
    private JComboBox listIdLot;

    //CLIENTS
    JSeparator separador;
    private final JPanel rightPanelCostumer;
    private JLabel firstNameCostumerLabel;
    private JLabel lastNameCostumerLabel;
    private JLabel contactCostumerLabel;
    private JLabel selectCostumerID;
    private JLabel genderCostumerLabel;
    private JTextField firstNameCostumerField;
    private JTextField lastNameCostumerField;
    private JTextField contactCostumerField;
    private JComboBox listCostumer;
    private JComboBox genderCostumerComboBox;
    private final DefaultTableModel costumerTableModel = new DefaultTableModel();
    private JTable costumerTable;
    private JButton addCostumerButton;
    private JButton deleteCostumerButton;
    private JButton editCostumerButton;

    //USERS
    private final JPanel rightPanelUsers;
    private JLabel usernameIconUsersLabel;
    private JLabel passwordIconUsersLabel;
    private JComboBox typeOfUser;
    private JComboBox listIdEmployee;
    private JLabel usersTextLabel;
    private JTextField usernameUsersField;
    private JTextField showPasswordUsersField;
    private JButton createUsersButton;
    private JButton deleteUsersButton;
    private JButton showAndHidePasswordUsersButton;
    private JPasswordField passwordUsersField;
    private ImageIcon eyeshow;
    private final DefaultTableModel loginTableModel = new DefaultTableModel();
    private JTable loginTable;

    //CARS
    JSeparator separadorCars;
    private final JPanel rightPanelCars;
    private JLabel matriculaCarsLabel;
    private JLabel corCarsLabel;
    private JLabel marcaCarsLabel;
    private JLabel modeloCarsLabel;
    private JLabel anoCarsLabel;
    private JLabel getProprietarioIDCarsLabel;
    private JLabel amountPaidCarsLabel;
    private JLabel selectCarsIDLabel;
    private JLabel getLotIDLabel;
    private JTextField matriculaCarsField;
    private JTextField corCarsField;
    private JTextField marcaCarsField;
    private JTextField modeloCarsField;
    private JTextField anoCarsField;
    private JTextField amountPaidCarsField;
    private JComboBox getProprietarioIDCarsField;
    private JComboBox listMatriculaCars;
    private JComboBox getLotIDCarsComboBox;
    private final DefaultTableModel carsTableModel = new DefaultTableModel();
    private JTable carsTable;
    private JButton addCarsButton;
    private JButton deleteCarsButton;
    private JButton editCarsButton;

    public PrincipalWindow() {
        setTitle("Parking Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE); // quando clicado no "X" da janela, fecha  completamente
        setSize(950, 700);   // dimensões da janela
        setLocationRelativeTo(null);    //centraliza a janela
        // define o layout null, todos itens serão colocados manualmente sem nenhum layout pre-definido
        setResizable(false);
        
        
        URL programIconURL = ldr.getResource("images/programIcon.png");
        ImageIcon programIcon = new ImageIcon(programIconURL);
        setIconImage(programIcon.getImage());

        //container que conterá os JPanels
        Container container = getContentPane();
        container.setLayout(null);
        container.setBackground(COLOR);

        //configurando os JPanels que conterão os itens
        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 250, 700);
        leftPanel.setBackground(COLOR);

        rightPanelHome = new JPanel();
        rightPanelHome.setLayout(null);
        rightPanelHome.setBounds(250, 20, 700, 660);
        rightPanelHome.setBackground(Color.white);
        rightPanelHome.setVisible(true);

        rightPanelCostumer = new JPanel();
        rightPanelCostumer.setLayout(null);
        rightPanelCostumer.setBounds(250, 20, 700, 660);
        rightPanelCostumer.setBackground(Color.white);
        rightPanelCostumer.setVisible(false);

        rightPanelLot = new JPanel();
        rightPanelLot.setLayout(null);
        rightPanelLot.setBounds(250, 20, 700, 660);
        rightPanelLot.setBackground(Color.white);
        rightPanelLot.setVisible(false);

        rightPanelCars = new JPanel();
        rightPanelCars.setLayout(null);
        rightPanelCars.setBounds(250, 20, 700, 660);
        rightPanelCars.setBackground(Color.white);
        rightPanelCars.setVisible(false);

        rightPanelUsers = new JPanel();
        rightPanelUsers.setLayout(null);
        rightPanelUsers.setBounds(250, 20, 700, 660);
        rightPanelUsers.setBackground(Color.white);
        rightPanelUsers.setVisible(false);

        //Labels da JPanel esquerda da parte direita----------------------------------------------------------------------------
        URL iconURL = ldr.getResource("images/principal/leftpanel/principal icon.png");
        ImageIcon icon = new ImageIcon(iconURL);
        Image iconI = icon.getImage();
        Image imgScale = iconI.getScaledInstance(220, 220, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        iconLabel = new JLabel();
        iconLabel.setBorder(BorderFactory.createLineBorder(Color.white, 1));
        iconLabel.setBounds(15, 20, 220, 220);
        iconLabel.setIcon(scaledIcon);

        homeButton = new JButton("HOME", homeIcon);
        homeButton.setBounds(15, 270, 220, 45);
        homeButton.setForeground(Color.white);
        homeButton.setBackground(COLOR);
        homeButton.setFocusable(false);
        homeButton.setBorder(null);
        homeButton.setIconTextGap(10);
        homeButton.setFont(new Font("Arial", Font.BOLD, 30));

        lotButton = new JButton("LOTS", spotIcon);
        lotButton.setBounds(15, 330, 220, 45);
        lotButton.setForeground(Color.white);
        lotButton.setBackground(COLOR);
        lotButton.setFocusable(false);
        lotButton.setBorder(null);
        lotButton.setIconTextGap(15);
        lotButton.setFont(new Font("Arial", Font.BOLD, 30));

        clientButton = new JButton("CLIENT", clientIcon);
        clientButton.setBounds(15, 390, 220, 45);
        clientButton.setForeground(Color.white);
        clientButton.setBackground(COLOR);
        clientButton.setFocusable(false);
        clientButton.setBorder(null);
        clientButton.setIconTextGap(15);
        clientButton.setFont(new Font("Arial", Font.BOLD, 30));

        userButton = new JButton("USER", usersIcon);
        userButton.setBounds(15, 450, 220, 45);
        userButton.setForeground(Color.white);
        userButton.setBackground(COLOR);
        userButton.setFocusable(false);
        userButton.setBorder(null);
        userButton.setIconTextGap(15);
        userButton.setFont(new Font("Arial", Font.BOLD, 30));

        carButton = new JButton("CARS", carsIcon);
        carButton.setBounds(15, 510, 220, 45);
        carButton.setForeground(Color.white);
        carButton.setBackground(COLOR);
        carButton.setFocusable(false);
        carButton.setBorder(null);
        carButton.setIconTextGap(15);
        carButton.setFont(new Font("Arial", Font.BOLD, 30));

        logoutButton = new JButton("Log Out", logoutIcon);
        logoutButton.setBounds(15, 600, 220, 45);
        logoutButton.setForeground(Color.white);
        logoutButton.setBackground(COLOR);
        logoutButton.setFocusable(false);
        logoutButton.setBorder(null);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 30));

        //LEFTPANEL
        homeButton.addActionListener(this);
        lotButton.addActionListener(this);
        clientButton.addActionListener(this);
        userButton.addActionListener(this);
        carButton.addActionListener(this);
        logoutButton.addActionListener(this);

        homeButton.addMouseListener(this);
        lotButton.addMouseListener(this);
        clientButton.addMouseListener(this);
        userButton.addMouseListener(this);
        carButton.addMouseListener(this);
        logoutButton.addMouseListener(this);

        //SPOT PANEL
        //adicionando componentes aos painéis-------------------------------------------------------------------
        leftPanel.add(iconLabel);
        leftPanel.add(homeButton);
        leftPanel.add(clientButton);
        leftPanel.add(lotButton);
        leftPanel.add(userButton);
        leftPanel.add(carButton);
        leftPanel.add(logoutButton);

        //HOME
        //SPOTS
        //adicionando os JPanels ao container---------------------------------------------------------------------
        container.add(leftPanel);
        container.add(rightPanelHome);
        container.add(rightPanelLot);
        container.add(rightPanelCostumer);
        container.add(rightPanelCars);
        container.add(rightPanelUsers);

        homeWindow();
        LotWindow();
        clientWindow();
        carsWindow();
        usersWindow();
        setVisible(true);
    }

    private void homeWindow() {
        //adicionando labels ao painel direito HOME
        URL vehicleURL = ldr.getResource("images/principal/home/vehicles.png");
        ImageIcon vehicle = new ImageIcon(vehicleURL);
       
        JLabel funcionariosText = new JLabel("Funcionarios");
        funcionariosText.setBounds(220, 270, 250, 50);
        funcionariosText.setFont(new Font("Times New Roman", Font.BOLD, 30));
        funcionariosText.setVerticalAlignment(JLabel.CENTER);
        funcionariosText.setForeground(COLOR);
        funcionariosText.setHorizontalAlignment(JLabel.CENTER);
        
        availableLotLabel = new JLabel(vehicle);
        availableLotLabel.setBounds(320, 70, 50, 50);
        textAvailableLotNumber = new JLabel("0");
        textAvailableLotNumber.setBounds(320, 110, 50, 50);
        textAvailableLotNumber.setFont(new Font("Arial", Font.BOLD, 20));
        textAvailableLotNumber.setVerticalAlignment(JLabel.CENTER);
        textAvailableLotNumber.setHorizontalAlignment(JLabel.CENTER);
        textAvailableLots.setBounds(290, 140, 150, 50);
        showAvailableLots();

        Object[] columns = {"ID", "NOME", "APELIDO", "CARGO"};
        funcionarioTableModel.setColumnIdentifiers(columns);

        funcionarioTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        funcionarioTable.setModel(funcionarioTableModel);
        funcionarioTable.setRowSelectionAllowed(false);
        funcionarioTable.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane barraRolagem = new JScrollPane(funcionarioTable);
        barraRolagem.setBorder(BorderFactory.createLineBorder(COLOR, 1));
        barraRolagem.setBounds(20, 340, 640, 250);

        showFuncionarios();

       
        rightPanelHome.add(funcionariosText);
        rightPanelHome.add(availableLotLabel);
        rightPanelHome.add(textAvailableLotNumber);
        rightPanelHome.add(textAvailableLots);
        rightPanelHome.add(barraRolagem);
    }

    private void LotWindow() {

        lotTextTitle = new JLabel("PARKING LOTS");
        lotTextTitle.setBounds(20, 20, 640, 100);
        lotTextTitle.setForeground(COLOR);
        lotTextTitle.setVerticalAlignment(JLabel.CENTER);
        lotTextTitle.setHorizontalAlignment(JLabel.CENTER);

        lotTextTitle.setFont(new Font("Arial", Font.BOLD, 60));

        //tabela
        Object[] columns = {"ID", "LICENSE PLATE", "STATUS"};
        lotTableModel.setColumnIdentifiers(columns);

        lotTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        lotTable.setModel(lotTableModel);
        lotTable.setRowSelectionAllowed(false);
        lotTable.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane barraRolagem = new JScrollPane(lotTable);
        barraRolagem.setBorder(BorderFactory.createLineBorder(COLOR, 1));
        barraRolagem.setBounds(20, 340, 640, 250);

        //botoes SPOT
        refreshLotButton = new JButton("REFRESH LOTS");
        refreshLotButton.setFocusable(false);
        refreshLotButton.setBackground(COLOR);
        refreshLotButton.setForeground(Color.white);
        refreshLotButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshLotButton.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2, true));
        refreshLotButton.setBounds(20, 280, 640, 40);

        createLotButton = new JButton("CREATE NEW LOT");
        createLotButton.setFocusable(false);
        createLotButton.setBackground(COLOR);
        createLotButton.setForeground(Color.white);
        createLotButton.setFont(new Font("Arial", Font.BOLD, 14));
        createLotButton.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2, true));
        createLotButton.setBounds(20, 160, 640, 40);

        deleteLotButton = new JButton("DELETE LAST LOT");
        deleteLotButton.setFocusable(false);
        deleteLotButton.setBackground(COLOR);
        deleteLotButton.setForeground(Color.white);
        deleteLotButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteLotButton.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2, true));
        deleteLotButton.setBounds(20, 220, 640, 40);

        //menu drop down 
        listIdLot = new JComboBox();
        listIdLot.setFocusable(false);
        listIdLot.setBorder(null);
        listIdLot.setBackground(Color.white);
        listIdLot.setForeground(Color.black);
        listIdLot.setFont(new Font("Arial", Font.BOLD, 14));
        listIdLot.setBounds(20, 20, 120, 40);
        SlotDAO teste = new SlotDAO();
        for (Slot slot : teste.read()) {
            listIdLot.addItem(String.valueOf(slot.getIdSlot()));
        }
        listIdLot.setVisible(false);
        showLots();

        //eventos
        refreshLotButton.addActionListener(this);
        createLotButton.addActionListener(this);
        deleteLotButton.addActionListener(this);

        rightPanelLot.add(lotTextTitle);
        rightPanelLot.add(barraRolagem);
        rightPanelLot.add(listIdLot);
        rightPanelLot.add(refreshLotButton);
        rightPanelLot.add(createLotButton);
        rightPanelLot.add(deleteLotButton);
    }

    private void clientWindow() {
        separador = new JSeparator(SwingConstants.VERTICAL);
        separador.setBounds(335, 30, 10, 250);
        separador.setBackground(Color.darkGray);

        firstNameCostumerLabel = new JLabel("Nome");
        firstNameCostumerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        firstNameCostumerLabel.setHorizontalAlignment(JLabel.CENTER);
        firstNameCostumerLabel.setBounds(20, 40, 100, 30);

        lastNameCostumerLabel = new JLabel("Apelido");
        lastNameCostumerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        lastNameCostumerLabel.setHorizontalAlignment(JLabel.CENTER);
        lastNameCostumerLabel.setBounds(20, 80, 100, 30);

        contactCostumerLabel = new JLabel("Contacto");
        contactCostumerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        contactCostumerLabel.setHorizontalAlignment(JLabel.CENTER);
        contactCostumerLabel.setBounds(20, 120, 100, 30);

        genderCostumerLabel = new JLabel("Sexo");
        genderCostumerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        genderCostumerLabel.setHorizontalAlignment(JLabel.CENTER);
        genderCostumerLabel.setBounds(20, 160, 100, 30);

        selectCostumerID = new JLabel("ID");
        selectCostumerID.setToolTipText("ID Costumer");
        selectCostumerID.setFont(new Font("Arial", Font.BOLD, 20));
        selectCostumerID.setHorizontalAlignment(JLabel.CENTER);
        selectCostumerID.setBounds(350, 40, 50, 30);
        selectCostumerID.setBorder(BORDER_AFTER);

        firstNameCostumerField = new JTextField();
        firstNameCostumerField.setFont(new Font("Arial", Font.PLAIN, 16));
        firstNameCostumerField.setForeground(Color.gray);
        firstNameCostumerField.setBounds(125, 40, 200, 30);
        firstNameCostumerField.setBorder(BORDER_AFTER);

        lastNameCostumerField = new JTextField();
        lastNameCostumerField.setFont(new Font("Arial", Font.PLAIN, 16));
        lastNameCostumerField.setForeground(Color.gray);
        lastNameCostumerField.setBounds(125, 80, 200, 30);
        lastNameCostumerField.setBorder(BORDER_AFTER);

        contactCostumerField = new JTextField();
        contactCostumerField.setFont(new Font("Arial", Font.PLAIN, 16));
        contactCostumerField.setForeground(Color.gray);
        contactCostumerField.setBounds(125, 120, 200, 30);
        contactCostumerField.setBorder(BORDER_AFTER);

        String[] sexo = {"Male", "Female"};
        genderCostumerComboBox = new JComboBox(sexo);
        genderCostumerComboBox.setSelectedItem(sexo[0]);
        genderCostumerComboBox.setBounds(125, 160, 200, 30);
        genderCostumerComboBox.setFont(new Font("Arial", Font.BOLD, 16));
        genderCostumerComboBox.setBorder(null);
        genderCostumerComboBox.setFocusable(false);

        listCostumer = new JComboBox();
        listCostumersID(listCostumer);
        listCostumer.setBounds(410, 40, 200, 30);
        listCostumer.setFont(new Font("Arial", Font.BOLD, 16));
        listCostumer.setBorder(null);
        listCostumer.setFocusable(false);

        Object[] columns = {"ID", "NOME", "APELIDO", "SEXO", "CONTACTO"};
        costumerTableModel.setColumnIdentifiers(columns);

        costumerTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        costumerTable.setModel(costumerTableModel);
        costumerTable.setRowSelectionAllowed(false);
        costumerTable.setFont(new Font("Arial", Font.PLAIN, 14));

        TableColumnModel columnModel = costumerTable.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(50);
        columnModel.getColumn(1).setMinWidth(100);
        columnModel.getColumn(2).setMinWidth(100);
        columnModel.getColumn(3).setMaxWidth(50);
        columnModel.getColumn(4).setMinWidth(100);

        costumerTable.setColumnModel(columnModel);

        JScrollPane barraRolagem = new JScrollPane(costumerTable);
        barraRolagem.setBorder(BorderFactory.createLineBorder(COLOR, 1));
        barraRolagem.setBounds(20, 380, 650, 250);

        showClients();

        addCostumerButton = new JButton("ADD");
        addCostumerButton.setFocusable(false);
        addCostumerButton.setBackground(COLOR);
        addCostumerButton.setForeground(Color.white);
        addCostumerButton.setFont(new Font("Arial", Font.BOLD, 14));
        addCostumerButton.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2, true));
        addCostumerButton.setBounds(120, 220, 170, 50);

        editCostumerButton = new JButton("EDIT");
        editCostumerButton.setFocusable(false);
        editCostumerButton.setBackground(COLOR);
        editCostumerButton.setForeground(Color.white);
        editCostumerButton.setFont(new Font("Arial", Font.BOLD, 14));
        editCostumerButton.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2, true));
        editCostumerButton.setBounds(350, 150, 300, 50);

        deleteCostumerButton = new JButton("DELETE");
        deleteCostumerButton.setFocusable(false);
        deleteCostumerButton.setBackground(COLOR);
        deleteCostumerButton.setForeground(Color.white);
        deleteCostumerButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteCostumerButton.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2, true));
        deleteCostumerButton.setBounds(350, 80, 300, 50);

        //eventos
        addCostumerButton.addActionListener(this);
        editCostumerButton.addActionListener(this);
        deleteCostumerButton.addActionListener(this);

        rightPanelCostumer.add(separador);
        rightPanelCostumer.add(addCostumerButton);
        rightPanelCostumer.add(editCostumerButton);
        rightPanelCostumer.add(deleteCostumerButton);
        rightPanelCostumer.add(barraRolagem);
        rightPanelCostumer.add(firstNameCostumerLabel);
        rightPanelCostumer.add(lastNameCostumerLabel);
        rightPanelCostumer.add(contactCostumerLabel);
        rightPanelCostumer.add(genderCostumerLabel);
        rightPanelCostumer.add(genderCostumerComboBox);
        rightPanelCostumer.add(selectCostumerID);
        rightPanelCostumer.add(listCostumer);
        rightPanelCostumer.add(firstNameCostumerField);
        rightPanelCostumer.add(lastNameCostumerField);
        rightPanelCostumer.add(contactCostumerField);
        rightPanelCostumer.add(contactCostumerField);
        rightPanelCostumer.add(contactCostumerField);
    }

    private void carsWindow() {
        separadorCars = new JSeparator(SwingConstants.VERTICAL);
        separadorCars.setBounds(335, 30, 10, 250);
        separadorCars.setBackground(Color.darkGray);

        matriculaCarsLabel = new JLabel("Matricula");
        matriculaCarsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        matriculaCarsLabel.setHorizontalAlignment(JLabel.CENTER);
        matriculaCarsLabel.setBounds(20, 40, 100, 30);

        corCarsLabel = new JLabel("Cor");
        corCarsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        corCarsLabel.setHorizontalAlignment(JLabel.CENTER);
        corCarsLabel.setBounds(20, 80, 100, 30);

        marcaCarsLabel = new JLabel("Marca");
        marcaCarsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        marcaCarsLabel.setHorizontalAlignment(JLabel.CENTER);
        marcaCarsLabel.setBounds(20, 120, 100, 30);

        modeloCarsLabel = new JLabel("Modelo");
        modeloCarsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        modeloCarsLabel.setHorizontalAlignment(JLabel.CENTER);
        modeloCarsLabel.setBounds(20, 160, 100, 30);

        anoCarsLabel = new JLabel("Ano");
        anoCarsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        anoCarsLabel.setHorizontalAlignment(JLabel.CENTER);
        anoCarsLabel.setBounds(20, 200, 100, 30);

        getProprietarioIDCarsLabel = new JLabel("ID Owner");
        getProprietarioIDCarsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        getProprietarioIDCarsLabel.setHorizontalAlignment(JLabel.CENTER);
        getProprietarioIDCarsLabel.setBounds(20, 240, 100, 30);

        amountPaidCarsLabel = new JLabel("PRICE");
        amountPaidCarsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        amountPaidCarsLabel.setHorizontalAlignment(JLabel.CENTER);
        amountPaidCarsLabel.setBounds(20, 280, 100, 30);

        getLotIDLabel = new JLabel("ID LOT");
        getLotIDLabel.setFont(new Font("Arial", Font.BOLD, 20));
        getLotIDLabel.setHorizontalAlignment(JLabel.CENTER);
        getLotIDLabel.setBounds(20, 320, 100, 30);

        selectCarsIDLabel = new JLabel("ID");
        selectCarsIDLabel.setToolTipText("ID Costumer");
        selectCarsIDLabel.setFont(new Font("Arial", Font.BOLD, 20));
        selectCarsIDLabel.setHorizontalAlignment(JLabel.CENTER);
        selectCarsIDLabel.setBounds(350, 40, 50, 30);
        selectCarsIDLabel.setBorder(BORDER_AFTER);

        matriculaCarsField = new JTextField();
        matriculaCarsField.setFont(new Font("Arial", Font.PLAIN, 16));
        matriculaCarsField.setForeground(Color.gray);
        matriculaCarsField.setBounds(125, 40, 200, 30);
        matriculaCarsField.setBorder(BORDER_AFTER);

        corCarsField = new JTextField();
        corCarsField.setFont(new Font("Arial", Font.PLAIN, 16));
        corCarsField.setForeground(Color.gray);
        corCarsField.setBounds(125, 80, 200, 30);
        corCarsField.setBorder(BORDER_AFTER);

        marcaCarsField = new JTextField();
        marcaCarsField.setFont(new Font("Arial", Font.PLAIN, 16));
        marcaCarsField.setForeground(Color.gray);
        marcaCarsField.setBounds(125, 120, 200, 30);
        marcaCarsField.setBorder(BORDER_AFTER);

        modeloCarsField = new JTextField();
        modeloCarsField.setFont(new Font("Arial", Font.PLAIN, 16));
        modeloCarsField.setForeground(Color.gray);
        modeloCarsField.setBounds(125, 160, 200, 30);
        modeloCarsField.setBorder(BORDER_AFTER);

        anoCarsField = new JTextField();
        anoCarsField.setFont(new Font("Arial", Font.PLAIN, 16));
        anoCarsField.setForeground(Color.gray);
        anoCarsField.setBounds(125, 200, 200, 30);
        anoCarsField.setBorder(BORDER_AFTER);

        amountPaidCarsField = new JTextField();
        amountPaidCarsField.setFont(new Font("Arial", Font.PLAIN, 16));
        amountPaidCarsField.setForeground(Color.gray);
        amountPaidCarsField.setBounds(125, 280, 200, 30);
        amountPaidCarsField.setBorder(BORDER_AFTER);

        getProprietarioIDCarsField = new JComboBox();
        getProprietarioIDCarsField.setBounds(125, 240, 200, 30);
        getProprietarioIDCarsField.setFont(new Font("Arial", Font.BOLD, 16));
        getProprietarioIDCarsField.setBorder(null);
        getProprietarioIDCarsField.setFocusable(false);
        getProprietarioIDCarsField.removeAllItems();
        ClienteDAO clienteDAO = new ClienteDAO();
        for (Cliente clienteS : clienteDAO.read()) {
            getProprietarioIDCarsField.addItem(String.valueOf(clienteS.getIdCliente()));
        }

        getLotIDCarsComboBox = new JComboBox();
        getLotIDCarsComboBox.setBounds(125, 320, 200, 30);
        getLotIDCarsComboBox.setFont(new Font("Arial", Font.BOLD, 16));
        getLotIDCarsComboBox.setBorder(null);
        getLotIDCarsComboBox.setFocusable(false);

        showLotID();

        listMatriculaCars = new JComboBox();
        listCarsID(listMatriculaCars);
        listMatriculaCars.setBounds(410, 40, 200, 30);
        listMatriculaCars.setFont(new Font("Arial", Font.BOLD, 16));
        listMatriculaCars.setBorder(null);
        listMatriculaCars.setFocusable(false);

        Object[] columns = {"MATRICULA", "COR", "MARCA", "MODELO", "ANO", "PROPRIETARIO", "AMOUNT_PAID"};
        carsTableModel.setColumnIdentifiers(columns);

        carsTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        carsTable.setModel(carsTableModel);
        carsTable.setRowSelectionAllowed(false);
        carsTable.setFont(new Font("Arial", Font.PLAIN, 14));

        TableColumnModel columnModel = carsTable.getColumnModel();
        columnModel.getColumn(0).setMinWidth(80);
        columnModel.getColumn(1).setMinWidth(100);
        columnModel.getColumn(2).setMinWidth(100);
        columnModel.getColumn(3).setMaxWidth(100);
        columnModel.getColumn(4).setMinWidth(50);
        columnModel.getColumn(5).setMinWidth(100);
        columnModel.getColumn(6).setMinWidth(100);

        carsTable.setColumnModel(columnModel);

        JScrollPane barraRolagem = new JScrollPane(carsTable);
        barraRolagem.setBorder(BorderFactory.createLineBorder(COLOR, 1));
        barraRolagem.setBounds(20, 380, 650, 250);

        showCars();

        addCarsButton = new JButton("ADD");
        addCarsButton.setFocusable(false);
        addCarsButton.setBackground(COLOR);
        addCarsButton.setForeground(Color.white);
        addCarsButton.setFont(new Font("Arial", Font.BOLD, 14));
        addCarsButton.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2, true));
        addCarsButton.setBounds(350, 300, 170, 50);

        editCarsButton = new JButton("EDIT");
        editCarsButton.setFocusable(false);
        editCarsButton.setBackground(COLOR);
        editCarsButton.setForeground(Color.white);
        editCarsButton.setFont(new Font("Arial", Font.BOLD, 14));
        editCarsButton.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2, true));
        editCarsButton.setBounds(350, 150, 300, 50);

        deleteCarsButton = new JButton("DELETE");
        deleteCarsButton.setFocusable(false);
        deleteCarsButton.setBackground(COLOR);
        deleteCarsButton.setForeground(Color.white);
        deleteCarsButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteCarsButton.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2, true));
        deleteCarsButton.setBounds(350, 80, 300, 50);

        //eventos
        addCarsButton.addActionListener(this);
        editCarsButton.addActionListener(this);
        deleteCarsButton.addActionListener(this);

        rightPanelCars.add(separadorCars);
        rightPanelCars.add(addCarsButton);
        rightPanelCars.add(editCarsButton);
        rightPanelCars.add(deleteCarsButton);
        rightPanelCars.add(barraRolagem);
        rightPanelCars.add(matriculaCarsLabel);
        rightPanelCars.add(corCarsLabel);
        rightPanelCars.add(marcaCarsLabel);
        rightPanelCars.add(modeloCarsLabel);
        rightPanelCars.add(anoCarsLabel);
        rightPanelCars.add(getProprietarioIDCarsLabel);
        rightPanelCars.add(amountPaidCarsLabel);
        rightPanelCars.add(getLotIDLabel);
        rightPanelCars.add(getLotIDCarsComboBox);
        rightPanelCars.add(selectCarsIDLabel);
        rightPanelCars.add(listMatriculaCars);
        rightPanelCars.add(matriculaCarsField);
        rightPanelCars.add(corCarsField);
        rightPanelCars.add(marcaCarsField);
        rightPanelCars.add(modeloCarsField);
        rightPanelCars.add(anoCarsField);
        rightPanelCars.add(getProprietarioIDCarsField);
        rightPanelCars.add(amountPaidCarsField);
    }

    private void usersWindow() {
        //configurando os textos
        usersTextLabel = new JLabel("USERs");
        usersTextLabel.setBounds(200, 40, 260, 65);
        usersTextLabel.setFocusable(true);
        usersTextLabel.setFont(new Font("Times New Roman", Font.BOLD, 60));
        usersTextLabel.setForeground(COLOR);
        usersTextLabel.setVerticalAlignment(JLabel.CENTER);
        usersTextLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel idEmployeeUsersLabel = new JLabel("ID");
        idEmployeeUsersLabel.setBounds(5, 420, 50, 40);
        idEmployeeUsersLabel.setFocusable(true);
        idEmployeeUsersLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        idEmployeeUsersLabel.setVerticalAlignment(JLabel.CENTER);
        idEmployeeUsersLabel.setHorizontalAlignment(JLabel.CENTER);

        URL userIconURL = ldr.getResource("images/login/user.png");
        ImageIcon userIcon = new ImageIcon(userIconURL);
        usernameIconUsersLabel = new JLabel(userIcon);
        usernameIconUsersLabel.setSize(500, 500);
        usernameIconUsersLabel.setBounds(5, 270, 40, 40);

        URL passIconURL = ldr.getResource("images/login/password.png");
        ImageIcon passIcon = new ImageIcon(passIconURL);
        passwordIconUsersLabel = new JLabel(passIcon);
        passwordIconUsersLabel.setSize(500, 500);
        passwordIconUsersLabel.setBounds(5, 345, 40, 40);

        //configurando os campos username e password
        usernameUsersField = new JTextField();
        usernameUsersField.setBounds(50, 270, 300, 40);
        usernameUsersField.setText("Username");
        usernameUsersField.setBorder(BORDER_BEFORE);
        usernameUsersField.setForeground(Color.gray);
        usernameUsersField.setFont(new Font("Arial", Font.BOLD, 12));

        passwordUsersField = new JPasswordField();
        passwordUsersField.setBounds(50, 345, 300, 40);
        passwordUsersField.setText("Insert your password here!");
        passwordUsersField.setBorder(BORDER_BEFORE);
        passwordUsersField.setForeground(Color.gray);
        passwordUsersField.setFont(new Font("Arial", Font.BOLD, 12));

        showPasswordUsersField = new JTextField();
        showPasswordUsersField.setBounds(50, 345, 300, 40);
        showPasswordUsersField.setText("Insert your password here!");
        showPasswordUsersField.setBorder(BORDER_BEFORE);
        showPasswordUsersField.setVisible(false);
        showPasswordUsersField.setForeground(Color.gray);
        showPasswordUsersField.setFont(new Font("Arial", Font.BOLD, 12));

        //configurando os botões
        createUsersButton = new JButton("SAVE");
        createUsersButton.setBackground(COLOR);
        createUsersButton.setForeground(Color.white);
        createUsersButton.setFont(new Font("Arial", Font.BOLD, 18));
        createUsersButton.setFocusable(false);
        createUsersButton.setBorder(BORDER_AFTER);
        createUsersButton.setBounds(25, 500, 150, 50);

        deleteUsersButton = new JButton("DELETE");
        deleteUsersButton.setBackground(COLOR);
        deleteUsersButton.setForeground(Color.white);
        deleteUsersButton.setFont(new Font("Arial", Font.BOLD, 18));
        deleteUsersButton.setFocusable(false);
        deleteUsersButton.setBorder(BORDER_AFTER);
        deleteUsersButton.setBounds(200, 500, 150, 50);

        URL eyeshowURL = ldr.getResource("images/login/eyeshow.png");
        eyeshow = new ImageIcon(eyeshowURL);
        showAndHidePasswordUsersButton = new JButton("Show", eyeshow);
        showAndHidePasswordUsersButton.setBorder(null);
        showAndHidePasswordUsersButton.setBackground(Color.white);
        showAndHidePasswordUsersButton.setForeground(COLOR);
        showAndHidePasswordUsersButton.setFocusable(false);
        showAndHidePasswordUsersButton.setFont(new Font("Arial", Font.BOLD, 14));
        showAndHidePasswordUsersButton.setBounds(250, 320, 100, 25);

        String[] userType = {"Standard User", "Administrator"};
        typeOfUser = new JComboBox(userType);
        typeOfUser.setFocusable(false);
        typeOfUser.setBounds(50, 210, 300, 40);
        typeOfUser.setBorder(null);
        typeOfUser.setBackground(Color.white);
        typeOfUser.setForeground(Color.gray);
        typeOfUser.setFont(new Font("Arial", Font.BOLD, 14));

        listIdEmployee = new JComboBox();
        listIdEmployee.setFocusable(false);
        listIdEmployee.setBounds(50, 420, 300, 40);
        listIdEmployee.setBorder(null);
        listIdEmployee.setBackground(Color.white);
        listIdEmployee.setForeground(Color.gray);
        listIdEmployee.setFont(new Font("Arial", Font.BOLD, 14));
        listEmployeeID(listIdEmployee);

        Object[] columns = {"USERNAME", "TIPO", "ID_FUNCIONARIO"};
        loginTableModel.setColumnIdentifiers(columns);

        loginTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loginTable.setModel(loginTableModel);
        loginTable.setRowSelectionAllowed(false);
        loginTable.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane barraRolagem = new JScrollPane(loginTable);
        barraRolagem.setBorder(BorderFactory.createLineBorder(COLOR, 1));
        barraRolagem.setBounds(360, 210, 300, 350);

        showLogin();

        rightPanelUsers.add(usernameIconUsersLabel);
        rightPanelUsers.add(passwordIconUsersLabel);
        rightPanelUsers.add(showAndHidePasswordUsersButton);
        rightPanelUsers.add(usersTextLabel);
        rightPanelUsers.add(showPasswordUsersField);
        rightPanelUsers.add(usernameUsersField);
        rightPanelUsers.add(passwordUsersField);
        rightPanelUsers.add(typeOfUser);
        rightPanelUsers.add(createUsersButton);
        rightPanelUsers.add(listIdEmployee);
        rightPanelUsers.add(idEmployeeUsersLabel);
        rightPanelUsers.add(barraRolagem);
        rightPanelUsers.add(deleteUsersButton);

        //adicionando eventos quando clicado nos campos e botoes
        showAndHidePasswordUsersButton.addActionListener(this);
        createUsersButton.addActionListener(this);
        deleteUsersButton.addActionListener(this);
        usernameUsersField.addFocusListener(this);
        passwordUsersField.addFocusListener(this);
        showPasswordUsersField.addFocusListener(this);

    }
//--------------------------------------------------------------------------------

    @Override
    public void actionPerformed(ActionEvent e) {
        //LEFT PANEL----------------------------------------------------------------------
        if (e.getSource() == homeButton) {
            rightPanelLot.setVisible(false);
            rightPanelCostumer.setVisible(false);
            rightPanelCars.setVisible(false);
            rightPanelUsers.setVisible(false);
            rightPanelHome.setVisible(true);
        }

        if (e.getSource() == lotButton) {
            rightPanelHome.setVisible(false);
            rightPanelCars.setVisible(false);
            rightPanelCostumer.setVisible(false);
            rightPanelUsers.setVisible(false);
            rightPanelLot.setVisible(true);

        }

        if (e.getSource() == clientButton) {
            rightPanelHome.setVisible(false);
            rightPanelCars.setVisible(false);
            rightPanelLot.setVisible(false);
            rightPanelUsers.setVisible(false);
            rightPanelCostumer.setVisible(true);

        }
        if (e.getSource() == userButton) {
            rightPanelHome.setVisible(false);
            rightPanelCars.setVisible(false);
            rightPanelLot.setVisible(false);
            rightPanelCostumer.setVisible(false);
            rightPanelUsers.setVisible(true);

        }

        if (e.getSource() == carButton) {
            rightPanelHome.setVisible(false);
            rightPanelLot.setVisible(false);
            rightPanelUsers.setVisible(false);
            rightPanelCostumer.setVisible(false);
            rightPanelCars.setVisible(true);

        }

        if (e.getSource() == logoutButton) {
            new LoginWindow();
            dispose();
        }

        //SPOT PANEL------------------------------------------------------------------------------------------
        if (e.getSource() == refreshLotButton) {
            showLots();
            showAvailableLots();

            SlotDAO teste = new SlotDAO();
            //drpo down list
            listIdLot.removeAllItems();
            for (Slot slot : teste.read()) {
                listIdLot.addItem(String.valueOf(slot.getIdSlot()));
            }
        }

        if (e.getSource() == createLotButton) {
            createLot();
            showLots();
            showAvailableLots();
        }

        if (e.getSource() == deleteLotButton) {
            deleteLot();
            showLots();
            showAvailableLots();
        }

        //CLIENTE-------------------------------------------------------------------------------------------------------
        if (e.getSource() == addCostumerButton) {
            createClient();
            showClients();
            listCostumersID(listCostumer);
            getProprietarioIDCarsField.removeAllItems();
            ClienteDAO clienteDAO = new ClienteDAO();
            for (Cliente clienteS : clienteDAO.read()) {
                getProprietarioIDCarsField.addItem(String.valueOf(clienteS.getIdCliente()));
            }
        }

        if (e.getSource() == deleteCostumerButton) {
            deleteClient();
            showClients();
            listCostumersID(listCostumer);
            getProprietarioIDCarsField.removeAllItems();
            ClienteDAO clienteDAO = new ClienteDAO();
            for (Cliente clienteS : clienteDAO.read()) {
                getProprietarioIDCarsField.addItem(String.valueOf(clienteS.getIdCliente()));
            }
        }

        if (e.getSource() == editCostumerButton) {
            updateClient();
            showClients();
            listCostumersID(listCostumer);
            getProprietarioIDCarsField.removeAllItems();
            ClienteDAO clienteDAO = new ClienteDAO();
            for (Cliente clienteS : clienteDAO.read()) {
                getProprietarioIDCarsField.addItem(String.valueOf(clienteS.getIdCliente()));
            }
        }

        //USERS
        if (e.getSource() == showAndHidePasswordUsersButton) {
            if (showAndHidePasswordUsersButton.getText().equalsIgnoreCase("Show")) {
                URL eyeshowURL = ldr.getResource("images/login/eyehide.png");
                eyeshow = new ImageIcon(eyeshowURL);
                showAndHidePasswordUsersButton.setText("Hide");
                showAndHidePasswordUsersButton.setIcon(eyeshow);

                passwordUsersField.setVisible(false);
                showPasswordUsersField.setVisible(true);
                showPasswordUsersField.setText(String.valueOf(passwordUsersField.getPassword()));

            } else if (showAndHidePasswordUsersButton.getText().equalsIgnoreCase("Hide")) {
                URL eyeshowURL = ldr.getResource("images/login/eyeshow.png");
                eyeshow = new ImageIcon(eyeshowURL);
                showAndHidePasswordUsersButton.setText("Show");
                showAndHidePasswordUsersButton.setIcon(eyeshow);

                showPasswordUsersField.setVisible(false);
                passwordUsersField.setVisible(true);

                passwordUsersField.setText(showPasswordUsersField.getText());
            }

        }
        if (e.getSource() == createUsersButton) {
            createLogin();
            listEmployeeID(listIdEmployee);
            showLogin();
        }

        if (e.getSource() == deleteUsersButton) {
            deleteLogin();
            listEmployeeID(listIdEmployee);
            showLogin();
        }

        //CARS
        if (e.getSource() == addCarsButton) {
            createCars();
            showCars();
            showAvailableLots();
            showLotID();
            listCarsID(listMatriculaCars);
        }

        if (e.getSource() == deleteCarsButton) {
            deleteCars();
            showCars();
            showAvailableLots();
            showLotID();
            listCarsID(listMatriculaCars);
        }

        if (e.getSource() == editCarsButton) {
            updateCars();
            showCars();
            showAvailableLots();
            showLotID();
            listCarsID(listMatriculaCars);
        }
    }

    //MOUSELISTENER--------------------------------------------------------------------------------------------------------
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

        if (e.getSource() == homeButton) {
            homeButton.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
            homeButton.setOpaque(true);
        }

        if (e.getSource() == lotButton) {
            lotButton.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
            lotButton.setOpaque(true);
        }

        if (e.getSource() == clientButton) {
            clientButton.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
            clientButton.setOpaque(true);
        }

        if (e.getSource() == userButton) {
            userButton.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
            userButton.setOpaque(true);
        }

        if (e.getSource() == carButton) {
            carButton.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
            carButton.setOpaque(true);
        }

        if (e.getSource() == logoutButton) {
            logoutButton.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
            logoutButton.setOpaque(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

        if (e.getSource() == homeButton) {
            homeButton.setBorder(null);
            homeButton.setOpaque(false);
        }

        if (e.getSource() == lotButton) {
            lotButton.setBorder(null);
            lotButton.setOpaque(false);
        }

        if (e.getSource() == clientButton) {
            clientButton.setBorder(null);
            clientButton.setOpaque(false);
        }

        if (e.getSource() == userButton) {
            userButton.setBorder(null);
            userButton.setOpaque(false);
        }

        if (e.getSource() == carButton) {
            carButton.setBorder(null);
            carButton.setOpaque(false);
        }

        if (e.getSource() == logoutButton) {
            logoutButton.setBorder(null);
            logoutButton.setOpaque(false);
        }
    }

    //FOCUSLISTENER--------------------------------------------------------------------------------------------------------------
    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == usernameUsersField) {
            usernameUsersField.setBorder(BORDER_AFTER);
            usernameUsersField.setForeground(Color.BLACK);

            if (usernameUsersField.getText().equalsIgnoreCase("Username")) {
                usernameUsersField.setText("");
            }
        }

        if (e.getSource() == passwordUsersField) {
            passwordUsersField.setBorder(BORDER_AFTER);
            passwordUsersField.setForeground(Color.BLACK);

            String GET_PASSWORD = String.valueOf(passwordUsersField.getPassword());
            if (GET_PASSWORD.equalsIgnoreCase("Insert your password here!")) {
                passwordUsersField.setText("");
            }
        }

        if (e.getSource() == showPasswordUsersField) {
            showPasswordUsersField.setBorder(BORDER_AFTER);
            showPasswordUsersField.setForeground(Color.BLACK);

            String GET_PASSWORD = String.valueOf(passwordUsersField.getPassword());
            if (GET_PASSWORD.equalsIgnoreCase("Insert your password here!")) {
                passwordUsersField.setText("");
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == usernameUsersField) {
            usernameUsersField.setBorder(BORDER_BEFORE);
            usernameUsersField.setText(usernameUsersField.getText());
        }

        if (e.getSource() == passwordUsersField) {
            passwordUsersField.setBorder(BORDER_BEFORE);
            passwordUsersField.setText(String.valueOf(passwordUsersField.getPassword()));
        }

        if (e.getSource() == showPasswordUsersField) {
            showPasswordUsersField.setBorder(BORDER_BEFORE);
            showPasswordUsersField.setText(showPasswordUsersField.getText());
        }
    }

    //METODOS CRIADOS POR MIM
    private void showFuncionarios() {
        funcionarioTableModel.setNumRows(0);
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        for (Funcionario funcionario : funcionarioDAO.read()) {
            funcionarioTableModel.addRow(new Object[]{
                funcionario.getIdFuncionario(),
                funcionario.getNome(),
                funcionario.getApelido(),
                funcionario.getCargo()
            });
        }
    }

    private void showLots() {
        lotTableModel.setNumRows(0);
        SlotDAO lugares = new SlotDAO();

        for (Slot slot : lugares.read()) {
            lotTableModel.addRow(new Object[]{
                slot.getIdSlot(),
                slot.getCarroMatricula(),
                slot.getStatus()
            });
        }
    }

    private void createLot() {
        Slot slot = new Slot();
        slot.setStatus("vacant");

        SlotDAO slotDAO = new SlotDAO();
        slotDAO.create(slot, listIdLot);
    }

    private void deleteLot() {
        SlotDAO slotDAO = new SlotDAO();
        slotDAO.delete(listIdLot);
    }

    private void showClients() {
        costumerTableModel.setNumRows(0);
        ClienteDAO client = new ClienteDAO();

        for (Cliente cliente : client.read()) {
            costumerTableModel.addRow(new Object[]{
                cliente.getIdCliente(),
                cliente.getNome(),
                cliente.getApelido(),
                cliente.getSexo(),
                cliente.getContacto()
            });
        }
    }

    private void createClient() {

        String nome = this.firstNameCostumerField.getText();
        String apelido = this.lastNameCostumerField.getText();
        String contacto = this.contactCostumerField.getText();
        String sexo = String.valueOf(this.genderCostumerComboBox.getSelectedItem());

        try {
            if (!nome.isEmpty() && nome.length() < 45 && !apelido.isEmpty() && apelido.length() < 45 && !contacto.isEmpty() && contacto.length() < 45) {
                Cliente cliente = new Cliente();
                cliente.setNome(nome);
                cliente.setApelido(apelido);
                cliente.setContacto(contacto);
                if (sexo.equalsIgnoreCase("male")) {
                    cliente.setSexo("M");
                } else if (sexo.equalsIgnoreCase("female")) {
                    cliente.setSexo("F");
                }

                ClienteDAO clienteDAO = new ClienteDAO();

                clienteDAO.create(cliente, listCostumer);
            } else {
                JOptionPane.showMessageDialog(null, "Ops! Verifique as entradas! demasiado longo ou vazio", "erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Ops! Verifique as entradas" + e, "erro", JOptionPane.ERROR_MESSAGE);
        }

        this.firstNameCostumerField.setText("");
        this.lastNameCostumerField.setText("");
        this.contactCostumerField.setText("");
    }

    private void updateClient() {

        String nome = firstNameCostumerField.getText();
        String apelido = lastNameCostumerField.getText();
        String contacto = contactCostumerField.getText();
        String gender = String.valueOf(genderCostumerComboBox.getSelectedItem());
        int id = Integer.parseInt(String.valueOf(listCostumer.getSelectedItem()));

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setApelido(apelido);
        cliente.setContacto(contacto);
        if (gender.equalsIgnoreCase("male")) {
            cliente.setSexo("M");
        } else if (gender.equalsIgnoreCase("female")) {
            cliente.setSexo("F");
        }
        cliente.setIdCliente(id);

        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.update(cliente);

        firstNameCostumerField.setText("");
        lastNameCostumerField.setText("");
        contactCostumerField.setText("");

    }

    private void deleteClient() {
        ClienteDAO slotDAO = new ClienteDAO();
        slotDAO.delete(listCostumer);
    }

    private void createCars() {

        String matricula = this.matriculaCarsField.getText();
        String cor = this.corCarsField.getText();
        String marca = this.marcaCarsField.getText();
        String modelo = this.modeloCarsField.getText();
        String ano = this.anoCarsField.getText();
        String amountPaid = this.amountPaidCarsField.getText();
        try {
            if (!matricula.isEmpty() && matricula.length() < 45 && !cor.isEmpty() && cor.length() < 45 && !marca.isEmpty()
                    && marca.length() < 45 && !modelo.isEmpty() && modelo.length() < 45
                    && !ano.isEmpty() && ano.length() < 45 && !amountPaid.isEmpty() && amountPaid.length() < 45) {

                Carro carro = new Carro();
                carro.setMatricula(matricula);
                carro.setCor(cor);
                carro.setMarca(marca);
                carro.setModelo(modelo);
                carro.setAno(Integer.parseInt(ano));
                carro.setProprietario(Integer.parseInt(String.valueOf(getProprietarioIDCarsField.getSelectedItem())));
                carro.setAmountPaid(amountPaid);

                CarroDAO carroDAO = new CarroDAO();
                carroDAO.create(carro);

                Slot slot = new Slot();
                slot.setCarroMatricula(matricula);

                SlotDAO slotDAO = new SlotDAO();
                slotDAO.updateMatricula(slot, getLotIDCarsComboBox, carro);

            } else {
                JOptionPane.showMessageDialog(null, "Ops! Verifique as entradas! demasiado longo ou vazio", "erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Ops! Verifique as entradas" + e, "erro", JOptionPane.ERROR_MESSAGE);
        }

        this.matriculaCarsField.setText("");
        this.corCarsField.setText("");
        this.marcaCarsField.setText("");
        this.anoCarsField.setText("");
        this.amountPaidCarsField.setText("");

    }

    private void showCars() {
        carsTableModel.setNumRows(0);
        CarroDAO carro = new CarroDAO();

        for (Carro carrox : carro.read()) {
            carsTableModel.addRow(new Object[]{
                carrox.getMatricula(),
                carrox.getCor(),
                carrox.getMarca(),
                carrox.getModelo(),
                carrox.getAno(),
                carrox.getProprietario(),
                carrox.getAmountPaid()
            });
        }
    }

    private void updateCars() {

        String matricula = String.valueOf(listMatriculaCars.getSelectedItem());
        String cor = corCarsField.getText();
        String marca = marcaCarsField.getText();
        String modelo = modeloCarsField.getText();
        int ano = Integer.parseInt(anoCarsField.getText());
        int proprietario = Integer.parseInt(String.valueOf(getProprietarioIDCarsField.getSelectedItem()));
        String amount = amountPaidCarsField.getText();

        Carro carro = new Carro();
        carro.setMatricula(matricula);
        carro.setCor(cor);
        carro.setMarca(marca);
        carro.setModelo(modelo);
        carro.setAno(ano);
        carro.setProprietario(proprietario);
        carro.setAmountPaid(amount);

        CarroDAO carroDAO = new CarroDAO();
        carroDAO.update(carro);

        matriculaCarsField.setText("");
        corCarsField.setText("");
        marcaCarsField.setText("");
        modeloCarsField.setText("");
        anoCarsField.setText("");
        amountPaidCarsField.setText("");
    }

    private void deleteCars() {
        CarroDAO carroDAO = new CarroDAO();
        carroDAO.delete(listMatriculaCars);

    }

    private void showLogin() {
        loginTableModel.setNumRows(0);
        LoginDAO loginDAO = new LoginDAO();

        for (Login login : loginDAO.read()) {
            loginTableModel.addRow(new Object[]{
                login.getUsername(),
                login.getTipo(),
                login.getIdFuncionario(),});
        }
    }

    private void createLogin() {
        Funcionario funcionario = new Funcionario();
        funcionario.setIdFuncionario(Integer.parseInt(String.valueOf(listIdEmployee.getSelectedItem())));

        LoginDAO loginDAO = new LoginDAO();
        loginDAO.create(funcionario, typeOfUser, usernameUsersField, passwordUsersField);

        usernameUsersField.setText("");
        passwordUsersField.setText("");
    }

    private void deleteLogin() {
        LoginDAO loginDAO = new LoginDAO();
        loginDAO.delete(usernameUsersField);

        usernameUsersField.setText("");
        passwordUsersField.setText("");

    }

    private void showAvailableLots() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String SELECT = "SELECT COUNT(idslot) AS idcount from slot WHERE status=?";
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(SELECT);
            statement.setString(1, "vacant");

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                textAvailableLotNumber.setText(String.valueOf(resultSet.getString("idcount")));
            }

        } catch (Exception e) {
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
    }

    private void showLotID() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String SELECT = "SELECT idslot from slot WHERE status=?";
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(SELECT);
            statement.setString(1, "vacant");

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                getLotIDCarsComboBox.addItem(resultSet.getString("idslot"));
            }

        } catch (Exception e) {
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
    }

    private void listCostumersID(JComboBox list) {
        list.removeAllItems();
        ClienteDAO clienteDAO = new ClienteDAO();
        for (Cliente clienteS : clienteDAO.read()) {
            list.addItem(String.valueOf(clienteS.getIdCliente()));
        }
    }

    private void listCarsID(JComboBox list) {
        list.removeAllItems();
        CarroDAO carroDAO = new CarroDAO();
        for (Carro carro : carroDAO.read()) {
            list.addItem(String.valueOf(carro.getMatricula()));
        }
    }

    private void listEmployeeID(JComboBox list) {
        list.removeAllItems();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        for (Funcionario funcionario : funcionarioDAO.read()) {
            list.addItem(String.valueOf(funcionario.getIdFuncionario()));
        }
    }

}