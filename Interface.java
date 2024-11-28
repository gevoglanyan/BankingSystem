import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("unused")
public class Interface extends JFrame {

    private JTabbedPane tabbedPane;
    private JTextArea resultArea;

    public Interface() {
        setTitle("Banking System Database");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        tabbedPane = new JTabbedPane();

        initCustomerPanel();
        initAccountPanel();
        initTransactionPanel();
        initLoanPanel();
        initBranchPanel();

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(700, 150));

        setLayout(new BorderLayout(5, 5));
        add(tabbedPane, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private JPanel createGridBagPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return panel;
    }

    private JButton createSmallButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 11));
        btn.setMargin(new Insets(2, 5, 2, 5));
        return btn;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(15);
        field.setFont(new Font("Arial", Font.PLAIN, 11));
        return field;
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

        String[] labels = {"First Name:", "Last Name:", "Address:", "Phone:", "Email:"};
        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            customerPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            fields[i] = createTextField();
            customerPanel.add(fields[i], gbc);
        }

        gbc.gridx = 2;
        gbc.gridy = labels.length + 1;
        JButton createCustomerBtn = createSmallButton("Create Customer");
        createCustomerBtn.addActionListener(e -> {
            try {
                int result = DatabaseQuery.createCustomerQuery(
                    fields[0].getText(), 
                    fields[1].getText(), 
                    fields[2].getText(), 
                    fields[4].getText(), 
                    fields[3].getText()
                );
                if (result > 0) {
                    resultArea.setText("Customer created successfully!");
                } else {
                    resultArea.setText("Failed to create customer.");
                }
            } catch (SQLException ex) {
                displayError("Error creating customer", ex);
            }
        });
        customerPanel.add(createCustomerBtn, gbc);

        tabbedPane.addTab("Customers", customerPanel);
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
        JButton getAccountBtn = createSmallButton("Get Account");
        getAccountBtn.addActionListener(e -> {
            try {
                int customerID = Integer.parseInt(customerIDField.getText());
                ResultSet rs = DatabaseQuery.getAccountByCustomerID(customerID);
                displayResultSet(rs);
            } catch (NumberFormatException | SQLException ex) {
                displayError("Error retrieving account", ex);
            }
        });
        accountPanel.add(getAccountBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        accountPanel.add(new JLabel("Min Balance:"), gbc);
        
        gbc.gridx = 1;
        JTextField balanceField = createTextField();
        accountPanel.add(balanceField, gbc);

        gbc.gridx = 2;
        JButton getByBalanceBtn = createSmallButton("Accounts Above Balance");
        getByBalanceBtn.addActionListener(e -> {
            try {
                int balance = Integer.parseInt(balanceField.getText());
                ResultSet rs = DatabaseQuery.getAccountByBalance(balance);
                displayResultSet(rs);
            } catch (NumberFormatException | SQLException ex) {
                displayError("Error retrieving accounts", ex);
            }
        });
        accountPanel.add(getByBalanceBtn, gbc);

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
            } catch (NumberFormatException | SQLException ex) {
                displayError("Error retrieving transactions", ex);
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
        JTextField customerIDLoanField = createTextField();
        loanPanel.add(customerIDLoanField, gbc);

        gbc.gridx = 2;
        JButton getLoansBtn = createSmallButton("Get Loans");
        getLoansBtn.addActionListener(e -> {
            try {
                int customerID = Integer.parseInt(customerIDLoanField.getText());
                ResultSet rs = DatabaseQuery.getLoansFromCustomer(customerID);
                displayResultSet(rs);
            } catch (NumberFormatException | SQLException ex) {
                displayError("Error retrieving loans", ex);
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
            } catch (NumberFormatException | SQLException ex) {
                displayError("Error retrieving employees", ex);
            }
        });
        branchPanel.add(getEmployeesBtn, gbc);

        tabbedPane.addTab("Branch", branchPanel);
    }

    private void displayResultSet(ResultSet rs) {
        try {
            resultArea.setText("");

            if (rs == null || !rs.isBeforeFirst()) {
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
            resultArea.append("-".repeat(header.length()) + "\n");

            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    row.append(String.format("%-20s", rs.getString(i)));
                }
                resultArea.append(row.toString() + "\n");
            }
        } catch (SQLException e) {
            displayError("Error displaying results", e);
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
                    "Failed to connect to database:\n" + e.getMessage(), 
                    "Database Connection Error", 
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }   
}