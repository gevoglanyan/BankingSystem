import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class Interface extends JFrame {

    private JTabbedPane tabbedPane;
    private JTextArea resultArea;

    public Interface() {
        setTitle("Banking System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        tabbedPane = new JTabbedPane();

        addAccountManagementTab();
        addTransactionManagementTab();
        addLoanManagementTab();
        addEmployeeManagementTab();

        resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private JPanel createGridBagPanel() {
        return new JPanel(new GridBagLayout());
    }

    private JButton createButton(String text) {
        return new JButton(text);
    }

    private JTextField createTextField() {
        return new JTextField(15);
    }

    private void addAccountManagementTab() {
        JPanel panel = createGridBagPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create Account

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Customer ID for Account Creation:"), gbc);
        gbc.gridx = 1;
        JTextField customerIDField = createTextField();
        panel.add(customerIDField, gbc);
        gbc.gridx = 2;
        JButton createAccountBtn = createButton("Create Account");
        createAccountBtn.addActionListener(e -> {
            try {
                int customerID = Integer.parseInt(customerIDField.getText());
                int result = DatabaseQuery.createAccount(customerID, "Savings");
                resultArea.setText(result > 0 ? "Account Created Successfully" : "Failed to Create Account");
            } catch (SQLException | NumberFormatException ex) {
                displayError("Error Creating Account", ex);
            }
        });
        panel.add(createAccountBtn, gbc);

        // Close Account

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Account ID for Closure:"), gbc);
        gbc.gridx = 1;
        JTextField accountIDField = createTextField();
        panel.add(accountIDField, gbc);
        gbc.gridx = 2;
        JButton closeAccountBtn = createButton("Close Account");
        closeAccountBtn.addActionListener(e -> {
            try {
                int accountID = Integer.parseInt(accountIDField.getText());
                int result = DatabaseQuery.closeAccount(accountID);
                resultArea.setText(result > 0 ? "Account Closed Successfully" : "Failed to Close Account");
            } catch (SQLException | NumberFormatException ex) {
                displayError("Error Closing Account", ex);
            }
        });
        panel.add(closeAccountBtn, gbc);

        tabbedPane.addTab("Account Management", panel);
    }

    private void addTransactionManagementTab() {
        JPanel panel = createGridBagPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Transaction

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Sender Account ID:"), gbc);
        gbc.gridx = 1;
        JTextField senderIDField = createTextField();
        panel.add(senderIDField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Receiver Account ID:"), gbc);
        gbc.gridx = 1;
        JTextField receiverIDField = createTextField();
        panel.add(receiverIDField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Transaction Amount:"), gbc);
        gbc.gridx = 1;
        JTextField transactionAmountField = createTextField();
        panel.add(transactionAmountField, gbc);
        gbc.gridx = 2;
        JButton transactionBtn = createButton("Validate Transaction");
        transactionBtn.addActionListener(e -> {
            try {
                int senderID = Integer.parseInt(senderIDField.getText());
                int receiverID = Integer.parseInt(receiverIDField.getText());
                int amount = Integer.parseInt(transactionAmountField.getText());
                int result = DatabaseQuery.createTransaction(senderID, receiverID, amount);
                resultArea.setText(result > 0 ? "Transaction Successful" : "Transaction Failed");
            } catch (SQLException | NumberFormatException ex) {
                displayError("Error Validating Transaction", ex);
            }
        });
        panel.add(transactionBtn, gbc);

        tabbedPane.addTab("Transaction Management", panel);
    }

    private void addLoanManagementTab() {
        JPanel panel = createGridBagPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Approve Loan

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Customer ID for Loan:"), gbc);
        gbc.gridx = 1;
        JTextField loanCustomerIDField = createTextField();
        panel.add(loanCustomerIDField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Loan Amount:"), gbc);
        gbc.gridx = 1;
        JTextField loanAmountField = createTextField();
        panel.add(loanAmountField, gbc);
        gbc.gridx = 2;
        JButton approveLoanBtn = createButton("Approve Loan");
        approveLoanBtn.addActionListener(e -> {
            try {
                int customerID = Integer.parseInt(loanCustomerIDField.getText());
                int loanAmount = Integer.parseInt(loanAmountField.getText());
                boolean approved = DatabaseQuery.approveLoan(customerID, loanAmount);
                resultArea.setText(approved ? "Loan Approved" : "Loan Denied");
            } catch (SQLException | NumberFormatException ex) {
                displayError("Error Approving Loan", ex);
            }
        });
        panel.add(approveLoanBtn, gbc);

        tabbedPane.addTab("Loan Management", panel);
    }

    private void addEmployeeManagementTab() {
        JPanel panel = createGridBagPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Transfer Employee
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Employee ID:"), gbc);
        gbc.gridx = 1;
        JTextField employeeIDField = createTextField();
        panel.add(employeeIDField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("New Branch ID:"), gbc);
        gbc.gridx = 1;
        JTextField branchIDField = createTextField();
        panel.add(branchIDField, gbc);
        gbc.gridx = 2;
        JButton transferEmployeeBtn = createButton("Transfer Employee");
        transferEmployeeBtn.addActionListener(e -> {
            try {
                int employeeID = Integer.parseInt(employeeIDField.getText());
                int newBranchID = Integer.parseInt(branchIDField.getText());
                DatabaseQuery.transferEmployee(employeeID, newBranchID);
                resultArea.setText("Employee Transferred Successfully");
            } catch (SQLException | NumberFormatException ex) {
                displayError("Error Transferring Employee", ex);
            }
        });
        panel.add(transferEmployeeBtn, gbc);

        tabbedPane.addTab("Employee Management", panel);
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