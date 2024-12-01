import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Interface extends JFrame {

    private JTabbedPane tabbedPane;
    private JTextArea resultArea;

    public Interface() {
        setTitle("Banking System Database");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        tabbedPane = new JTabbedPane();

        // Different Panels 

        initCreateCustomerPanel();
        initCustomerPanel();
        initAccountPanel();
        initTransactionPanel();
        initLoanPanel();
        initBranchPanel();
        initATMPanel();
        initCardPanel();
        initAdminPanel();

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private JPanel createGridBagPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        return panel;
    }

    private JButton createSmallButton(String text) {
        return new JButton(text);
    }

    private JTextField createTextField() {
        return new JTextField(15);
    }

    private void initCustomerPanel() {
        JPanel customerPanel = createGridBagPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        gbc.gridx = 0;
        gbc.gridy = 0;
        customerPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        JTextField emailField = createTextField();
        customerPanel.add(emailField, gbc);

        gbc.gridx = 2;
        JButton getByEmailBtn = createSmallButton("Get Customer");
        getByEmailBtn.addActionListener(e -> {
            try {
                ResultSet rs = DatabaseQuery.getCustomerByEmail(emailField.getText());
                displayResultSet(rs);
            } catch (SQLException ex) {
                displayError("Error retrieving customer", ex);
            }
        });
        customerPanel.add(getByEmailBtn, gbc);

        tabbedPane.addTab("Customers", customerPanel);
    }

    private void initCreateCustomerPanel() {
        JPanel createCustomerPanel = createGridBagPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        Dimension textFieldSize = new Dimension(300, 25);
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        createCustomerPanel.add(new JLabel("First Name:"), gbc);
    
        gbc.gridx = 1;
        JTextField firstNameField = createTextField();
        firstNameField.setPreferredSize(textFieldSize);
        createCustomerPanel.add(firstNameField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        createCustomerPanel.add(new JLabel("Last Name:"), gbc);
    
        gbc.gridx = 1;
        JTextField lastNameField = createTextField();
        lastNameField.setPreferredSize(textFieldSize);
        createCustomerPanel.add(lastNameField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 2;
        createCustomerPanel.add(new JLabel("Address:"), gbc);
    
        gbc.gridx = 1;
        JTextField addressField = createTextField();
        addressField.setPreferredSize(textFieldSize);
        createCustomerPanel.add(addressField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 3;
        createCustomerPanel.add(new JLabel("Email:"), gbc);
    
        gbc.gridx = 1;
        JTextField emailField = createTextField();
        emailField.setPreferredSize(textFieldSize);
        createCustomerPanel.add(emailField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 4;
        createCustomerPanel.add(new JLabel("Phone Number:"), gbc);
    
        gbc.gridx = 1;
        JTextField phoneNumberField = createTextField();
        phoneNumberField.setPreferredSize(textFieldSize);
        createCustomerPanel.add(phoneNumberField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        
        JButton submitButton = new JButton("Create Customer");
        
        submitButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();

            // Resets Fields After Account Creation
    
            try {
                int result = DatabaseQuery.createCustomerQuery(firstName, lastName, address, email, phoneNumber);
                if (result != -1) {
                    resultArea.setText("Customer Created Successfully!");
                    firstNameField.setText("");
                    lastNameField.setText("");
                    addressField.setText("");
                    emailField.setText("");
                    phoneNumberField.setText("");
                } else {
                    resultArea.setText("Failed to Create Customer. Email Might Already Exist.");
                }
            } catch (SQLException ex) {
                displayError("Error Creating Customer", ex);
                firstNameField.setText("");
                lastNameField.setText("");
                addressField.setText("");
                emailField.setText("");
                phoneNumberField.setText("");
            }
        });
        createCustomerPanel.add(submitButton, gbc);
    
        tabbedPane.addTab("Create Customer", createCustomerPanel);
    }
    
    private void initAccountPanel() {
        JPanel accountPanel = createGridBagPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        gbc.gridx = 0;
        gbc.gridy = 0;
        accountPanel.add(new JLabel("Customer ID:"), gbc);

        gbc.gridx = 1;
        JTextField customerIDField = createTextField();
        accountPanel.add(customerIDField, gbc);

        gbc.gridx = 2;
        JButton getAccountBtn = createSmallButton("Get Accounts");
        getAccountBtn.addActionListener(e -> {
            try {
                int customerID = Integer.parseInt(customerIDField.getText());
                ResultSet rs = DatabaseQuery.getAccountByCustomerID(customerID);
                displayResultSet(rs);
            } catch (SQLException | NumberFormatException ex) {
                displayError("Error Retrieving Accounts", ex);
            }
        });
        accountPanel.add(getAccountBtn, gbc);

        tabbedPane.addTab("Accounts", accountPanel);
    }

    private void initTransactionPanel() {
        JPanel transactionPanel = createGridBagPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        gbc.gridx = 0;
        gbc.gridy = 0;
        transactionPanel.add(new JLabel("Account Number:"), gbc);

        gbc.gridx = 1;
        JTextField accountNumField = createTextField();
        transactionPanel.add(accountNumField, gbc);

        gbc.gridx = 2;
        JButton getTransactionsBtn = createSmallButton("Get Transactions");
        getTransactionsBtn.addActionListener(e -> {
            try {
                int accountNum = Integer.parseInt(accountNumField.getText());
                ResultSet rs = DatabaseQuery.transactionsRelatedToAccount(accountNum);
                displayResultSet(rs);
            } catch (SQLException | NumberFormatException ex) {
                displayError("Error Retrieving Transactions", ex);
            }
        });
        transactionPanel.add(getTransactionsBtn, gbc);

        tabbedPane.addTab("Transactions", transactionPanel);
    }

    private void initLoanPanel() {
        JPanel loanPanel = createGridBagPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        gbc.gridx = 0;
        gbc.gridy = 0;
        loanPanel.add(new JLabel("Customer ID:"), gbc);

        gbc.gridx = 1;
        JTextField customerIDField = createTextField();
        loanPanel.add(customerIDField, gbc);

        gbc.gridx = 2;
        JButton getLoansBtn = createSmallButton("Get Loans");
        getLoansBtn.addActionListener(e -> {
            try {
                int customerID = Integer.parseInt(customerIDField.getText());
                ResultSet rs = DatabaseQuery.getLoansFromCustomer(customerID);
                displayResultSet(rs);
            } catch (SQLException | NumberFormatException ex) {
                displayError("Error Retrieving Loans", ex);
            }
        });
        loanPanel.add(getLoansBtn, gbc);

        tabbedPane.addTab("Loans", loanPanel);
    }

    private void initBranchPanel() {
        JPanel branchPanel = createGridBagPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        gbc.gridx = 0;
        gbc.gridy = 0;
        branchPanel.add(new JLabel("Branch ID:"), gbc);

        gbc.gridx = 1;
        JTextField branchIDField = createTextField();
        branchPanel.add(branchIDField, gbc);

        gbc.gridx = 2;
        JButton getEmployeesBtn = createSmallButton("Get Employees");
        getEmployeesBtn.addActionListener(e -> {
            try {
                int branchID = Integer.parseInt(branchIDField.getText());
                ResultSet rs = DatabaseQuery.getEmployeeFromBranch(branchID);
                displayResultSet(rs);
            } catch (SQLException | NumberFormatException ex) {
                displayError("Error Retrieving Employees", ex);
            }
        });
        branchPanel.add(getEmployeesBtn, gbc);

        tabbedPane.addTab("Branches", branchPanel);
    }

    private void initATMPanel() {
        JPanel atmPanel = createGridBagPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        gbc.gridx = 0;
        gbc.gridy = 0;
        atmPanel.add(new JLabel("Branch ID:"), gbc);

        gbc.gridx = 1;
        JTextField branchIDField = createTextField();
        atmPanel.add(branchIDField, gbc);

        gbc.gridx = 2;
        JButton getATMsBtn = createSmallButton("Get ATMs");
        getATMsBtn.addActionListener(e -> {
            try {
                int branchID = Integer.parseInt(branchIDField.getText());
                ResultSet rs = DatabaseQuery.getATMFromBranch(branchID);
                displayResultSet(rs);
            } catch (SQLException | NumberFormatException ex) {
                displayError("Error Retrieving ATMs", ex);
            }
        });
        atmPanel.add(getATMsBtn, gbc);

        tabbedPane.addTab("ATMs", atmPanel);
    }

    private void initCardPanel() {
        JPanel cardPanel = createGridBagPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        gbc.gridx = 0;
        gbc.gridy = 0;
        cardPanel.add(new JLabel("Account ID:"), gbc);

        gbc.gridx = 1;
        JTextField accountIDField = createTextField();
        cardPanel.add(accountIDField, gbc);

        gbc.gridx = 2;
        JButton getCardsBtn = createSmallButton("Get Cards");
        getCardsBtn.addActionListener(e -> {
            try {
                int accountID = Integer.parseInt(accountIDField.getText());
                ResultSet rs = DatabaseQuery.getCardFromAccountID(accountID);
                displayResultSet(rs);
            } catch (SQLException | NumberFormatException ex) {
                displayError("Error Retrieving Cards", ex);
            }
        });
        cardPanel.add(getCardsBtn, gbc);

        tabbedPane.addTab("Cards", cardPanel);
    }

    private void initAdminPanel() {
        JPanel adminPanel = createGridBagPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        gbc.gridx = 0;
        gbc.gridy = 0;
        adminPanel.add(new JLabel("Custom Query:"), gbc);

        gbc.gridx = 1;
        JTextField queryField = createTextField();
        adminPanel.add(queryField, gbc);

        gbc.gridx = 2;
        JButton executeQueryBtn = createSmallButton("Execute Query");
        executeQueryBtn.addActionListener(e -> {
            try {
                String query = queryField.getText();
                ResultSet rs = DatabaseQuery.executeCustomQuery(query); 
                displayResultSet(rs);
            } catch (SQLException ex) {
                displayError("Error Executing Query", ex);
            }
        });
        adminPanel.add(executeQueryBtn, gbc);

        tabbedPane.addTab("Admin", adminPanel);
    }

    private void displayResultSet(ResultSet rs) {
        try {
            resultArea.setText(""); 
    
            if (rs == null) {
                resultArea.setText("No Results Found.");
                return;
            }
    
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            StringBuilder header = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                header.append(String.format("%-20s", metaData.getColumnName(i)));
            }
            resultArea.append(header.toString() + "\n");
    
            StringBuilder separator = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                separator.append("--------------------");
            }
            resultArea.append(separator.toString() + "\n");
    
            boolean hasRows = false;
            
            while (rs.next()) {
                hasRows = true;
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = rs.getString(i);
                    row.append(String.format("%-20s", columnValue != null ? columnValue : "NULL"));
                }
                resultArea.append(row.toString() + "\n");
            }
    
            if (!hasRows) {
                resultArea.setText("No Results Found.");
            }
        } catch (SQLException e) {
            displayError("SQL Exception While Displaying Results: " + e.getMessage(), e);
        } catch (Exception e) {
            displayError("Unexpected Error: " + e.getMessage(), e);
        }
    }
    
    private void displayError(String message, Exception e) {
        resultArea.setText(message + "\n" + e.getMessage());
        e.printStackTrace();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Connection conn = DatabaseConnection.getConnection();

                Interface frame = new Interface();
                frame.setVisible(true);

                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        DatabaseConnection.closeConnection(conn);
                        System.exit(0);
                    }
                });

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,
                        "Failed to Connect to Database:\n" + e.getMessage(),
                        "Database Connection Error",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}