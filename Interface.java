import org.jdatepicker.impl.*;
import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class Interface extends JFrame {
    private JTabbedPane tabbedPane;
    private JTextArea resultArea;
    private JTable resultTable;

    public Interface() {
        setTitle("Banking System Interface");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        tabbedPane = new JTabbedPane();
        resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);

        initCreateCustomerPanel();
        addATMMaintenanceTab();
        addCardManagementTab();
        addSalaryCalculationTab();
        addTransactionValidationTab();
        addTransactionQueryTab();

        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        resultScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
    }

    private void initCreateCustomerPanel() {
        JPanel createCustomerPanel = createGridBagPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        Dimension textFieldSize = new Dimension(300, 25);
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        createCustomerPanel.add(new JLabel("First Name"), gbc);
    
        gbc.gridx = 1;
        JTextField firstNameField = new JTextField();
        firstNameField.setPreferredSize(textFieldSize);
        createCustomerPanel.add(firstNameField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        createCustomerPanel.add(new JLabel("Last Name"), gbc);
    
        gbc.gridx = 1;
        JTextField lastNameField = new JTextField();
        lastNameField.setPreferredSize(textFieldSize);
        createCustomerPanel.add(lastNameField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 2;
        createCustomerPanel.add(new JLabel("Address"), gbc);
    
        gbc.gridx = 1;
        JTextField addressField = new JTextField();
        addressField.setPreferredSize(textFieldSize);
        createCustomerPanel.add(addressField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 3;
        createCustomerPanel.add(new JLabel("Email"), gbc);
    
        gbc.gridx = 1;
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(textFieldSize);
        createCustomerPanel.add(emailField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 4;
        createCustomerPanel.add(new JLabel("Phone Number"), gbc);
    
        gbc.gridx = 1;
        JTextField phoneNumberField = new JTextField();
        phoneNumberField.setPreferredSize(textFieldSize);
        createCustomerPanel.add(phoneNumberField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton submitButton = new JButton("Create Customer");
    
        JTable resultTable = new JTable(); 
        JScrollPane resultScrollPane = new JScrollPane(resultTable);
        resultScrollPane.setPreferredSize(new Dimension(700, 300)); 
        resultScrollPane.setBorder(BorderFactory.createEmptyBorder());
    
        submitButton.addActionListener(e -> {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String address = addressField.getText().trim();
            String email = emailField.getText().trim();
            String phoneNumber = phoneNumberField.getText().trim();
        
            if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                JOptionPane.showMessageDialog(createCustomerPanel, "All Fields Must be Filled.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            try {
                int result = DatabaseQuery.createCustomerQuery(firstName, lastName, address, email, phoneNumber);
                if (result > 0) {
                    JOptionPane.showMessageDialog(createCustomerPanel, "Customer Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        
                    DefaultTableModel tableModel = DatabaseQuery.getCustomerDetails(email);
        
                    int columnIndex = tableModel.findColumn("accountID");
                    
                    if (columnIndex != -1) {
                        tableModel.setColumnCount(tableModel.getColumnCount() - 1);
                        for (int i = columnIndex; i < tableModel.getColumnCount(); i++) {
                            for (int j = 0; j < tableModel.getRowCount(); j++) {
                                tableModel.setValueAt(tableModel.getValueAt(j, i + 1), j, i);
                            }
                        }
                    }
        
                    resultTable.setModel(tableModel);
        
                    firstNameField.setText("");
                    lastNameField.setText("");
                    addressField.setText("");
                    emailField.setText("");
                    phoneNumberField.setText("");
                } else {
                    JOptionPane.showMessageDialog(createCustomerPanel, "Failed to Create Customer. Email Might Already Exist.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                showError("Error Creating Customer", ex);
            }
        });
        
    
        createCustomerPanel.add(submitButton, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        createCustomerPanel.add(resultScrollPane, gbc);
    
        tabbedPane.addTab("Create Customer", createCustomerPanel);
    }
    
    private void addATMMaintenanceTab() {
        JPanel panel = createGridBagPanel();
    
        JLabel branchIDLabel = new JLabel("Branch ID");
        JTextField branchIDField = new JTextField(15);
        JButton fetchButton = new JButton("Fetch ATMs");
    
        resultTable = new JTable();
        JScrollPane resultScrollPane = new JScrollPane(resultTable);
        resultScrollPane.setPreferredSize(new Dimension(600, 200));
    
        resultScrollPane.setBorder(BorderFactory.createEmptyBorder());
    
        addToGridBag(panel, branchIDLabel, 0, 0);
        addToGridBag(panel, branchIDField, 0, 1);
        addToGridBag(panel, fetchButton, 0, 2);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(resultScrollPane, gbc);
    
        fetchButton.addActionListener(e -> {
            try {
                int branchID = Integer.parseInt(branchIDField.getText());
                DefaultTableModel tableModel = DatabaseQuery.getATMsRequiringMaintenance(branchID);
                if (tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(panel, "No ATMs Require Maintenance for Branch ID " + branchID);
                } else {
                    resultTable.setModel(tableModel);
                }
            } catch (SQLException | NumberFormatException ex) {
                showError("Error Fetching ATMs Requiring Maintenance", ex);
            } finally {
                branchIDField.setText(""); 
            }
        });
    
        tabbedPane.addTab("ATM Maintenance", panel);
    }
    
    private void addCardManagementTab() {
        JPanel panel = createGridBagPanel();
    
        JLabel accountIDLabel = new JLabel("Account ID");
        JTextField accountIDField = new JTextField(15);
        JButton fetchButton = new JButton("Fetch Expiring Cards");
    
        JTable resultTable = new JTable();
        JScrollPane resultScrollPane = new JScrollPane(resultTable);
        resultScrollPane.setBorder(BorderFactory.createEmptyBorder());
        resultScrollPane.setPreferredSize(new Dimension(700, 300)); 
    
        fetchButton.addActionListener(e -> {
            String input = accountIDField.getText();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please Enter an Account ID.");
                return;
            }
    
            try {
                int accountID = Integer.parseInt(input);
                DefaultTableModel tableModel = DatabaseQuery.getCardsExpiringSoon(accountID);
                if (tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(panel, "No Cards are Expiring Soon for Account ID " + accountID);
                } else {
                    resultTable.setModel(tableModel); 
                }
            } catch (SQLException | NumberFormatException ex) {
                showError("Error Fetching Expiring Cards", ex);
            } finally {
                accountIDField.setText(""); 
            }
        });
    
        addToGridBag(panel, accountIDLabel, 0, 0);
        addToGridBag(panel, accountIDField, 0, 1);
        addToGridBag(panel, fetchButton, 0, 2);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(resultScrollPane, gbc);
    
        tabbedPane.addTab("Card Management", panel);
    }
    
    private void addSalaryCalculationTab() {
        JPanel panel = createGridBagPanel();
    
        JLabel employeeIDLabel = new JLabel("Employee ID");
        JTextField employeeIDField = new JTextField(15);
        JLabel incrementFactorLabel = new JLabel("Increment Factor");
        JTextField incrementFactorField = new JTextField(15);
        JButton calculateButton = new JButton("Calculate Salary");
    
        JTable resultTable = new JTable();
        JScrollPane resultScrollPane = new JScrollPane(resultTable);
        resultScrollPane.setBorder(BorderFactory.createEmptyBorder());
        resultScrollPane.setPreferredSize(new Dimension(600, 200));
    
        calculateButton.addActionListener(e -> {
            try {
                int employeeID = Integer.parseInt(employeeIDField.getText());
                double incrementFactor = Double.parseDouble(incrementFactorField.getText());
    

                DefaultTableModel tableModel = DatabaseQuery.calculateSalaryIncrement(employeeID, incrementFactor);
    
                if (tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(panel, "No Employee Found with ID: " + employeeID, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    resultTable.setModel(tableModel); 
                    JOptionPane.showMessageDialog(panel, "Salary Updated Successfully.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid Input. Please Enter Valid Numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                showError("Error Calculating Salary", ex);
            } finally {
                employeeIDField.setText("");
                incrementFactorField.setText("");
            }
        });

        addToGridBag(panel, employeeIDLabel, 0, 0);
        addToGridBag(panel, employeeIDField, 0, 1);
        addToGridBag(panel, incrementFactorLabel, 0, 2);
        addToGridBag(panel, incrementFactorField, 0, 3);
        addToGridBag(panel, calculateButton, 0, 4);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH; 
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(resultScrollPane, gbc);
    
        tabbedPane.addTab("Salary Calculation", panel);
    }
    
    private void addTransactionValidationTab() {
        JPanel panel = createGridBagPanel();
    
        JLabel senderIDLabel = new JLabel("Sender ID");
        JTextField senderIDField = new JTextField(15);
        JLabel receiverIDLabel = new JLabel("Receiver ID");
        JTextField receiverIDField = new JTextField(15);
        JLabel amountLabel = new JLabel("Amount");
        JTextField amountField = new JTextField(15);
        JButton validateButton = new JButton("Validate Transaction");
    
        JTable resultTable = new JTable();
        JScrollPane resultScrollPane = new JScrollPane(resultTable);
        resultScrollPane.setBorder(BorderFactory.createEmptyBorder());
        resultScrollPane.setPreferredSize(new Dimension(600, 200));
    
        validateButton.addActionListener(e -> {
            try {
                int senderID = Integer.parseInt(senderIDField.getText());
                int receiverID = Integer.parseInt(receiverIDField.getText());
                int amount = Integer.parseInt(amountField.getText());
    
                DefaultTableModel tableModel = DatabaseQuery.createTransactionAndGetDetails(senderID, receiverID, amount);
    
                if (tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(panel, "Transaction Failed.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    resultTable.setModel(tableModel); 
                    JOptionPane.showMessageDialog(panel, "Transaction Successful.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid Input. Please Enter Valid Numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                showError("Error Validating Transaction", ex);
            } finally {
                senderIDField.setText("");
                receiverIDField.setText("");
                amountField.setText("");
            }
        });
    
        addToGridBag(panel, senderIDLabel, 0, 0);
        addToGridBag(panel, senderIDField, 0, 1);
        addToGridBag(panel, receiverIDLabel, 0, 2);
        addToGridBag(panel, receiverIDField, 0, 3);
        addToGridBag(panel, amountLabel, 0, 4);
        addToGridBag(panel, amountField, 0, 5);
        addToGridBag(panel, validateButton, 0, 6);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(resultScrollPane, gbc);
    
        tabbedPane.addTab("Transaction Validation", panel);
    }
    
    private void addTransactionQueryTab() {
        JPanel panel = createGridBagPanel();

        JLabel startDateLabel = new JLabel("Start Date");
        JLabel endDateLabel = new JLabel("End Date");

        UtilDateModel startModel = new UtilDateModel();
        UtilDateModel endModel = new UtilDateModel();

        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        JDatePanelImpl startDatePanel = new JDatePanelImpl(startModel, properties);
        JDatePanelImpl endDatePanel = new JDatePanelImpl(endModel, properties);

        JDatePickerImpl startDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
        JDatePickerImpl endDatePicker = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());

        JButton queryButton = new JButton("Query Transactions");

        JTable resultTable = new JTable();
        JScrollPane resultScrollPane = new JScrollPane(resultTable);
        resultScrollPane.setBorder(BorderFactory.createEmptyBorder());
        resultScrollPane.setPreferredSize(new Dimension(600, 200));

        queryButton.addActionListener(e -> {
            java.util.Date startDate = (java.util.Date) startDatePicker.getModel().getValue();
            java.util.Date endDate = (java.util.Date) endDatePicker.getModel().getValue();

            if (startDate == null || endDate == null) {
                JOptionPane.showMessageDialog(panel, "Please Select Both Start and End Dates.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Timestamp startTimestamp = new Timestamp(startDate.getTime());
                Timestamp endTimestamp = new Timestamp(endDate.getTime());

                DefaultTableModel tableModel = DatabaseQuery.transactionsBetweenDates(startTimestamp, endTimestamp);

                if (tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(panel, "No Transactions Found for the Selected Date Range.");
                } else {
                    resultTable.setModel(tableModel);
                }
            } catch (SQLException ex) {
                showError("Error Fetching Transactions Between Dates", ex);
            }
        });

        addToGridBag(panel, startDateLabel, 0, 0);
        addToGridBag(panel, startDatePicker, 0, 1);
        addToGridBag(panel, endDateLabel, 0, 2);
        addToGridBag(panel, endDatePicker, 0, 3);
        addToGridBag(panel, queryButton, 0, 4);
        addToGridBag(panel, resultScrollPane, 0, 5);

        tabbedPane.addTab("Transaction Queries", panel);
    }

    private JPanel createGridBagPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }

    private void addToGridBag(JPanel panel, Component component, int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
    }

    /* 

    No Need (Was Giving Problems, Switched to Table)

    private void displayResultSet(ResultSet rs) throws SQLException {
        StringBuilder sb = new StringBuilder();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        for (int i = 1; i <= columnCount; i++) {
            sb.append(metaData.getColumnName(i)).append("\t");
        }
        sb.append("\n");
        
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                sb.append(rs.getString(i)).append("\t");
            }
            sb.append("\n");
        }
        resultArea.setText(sb.toString());
    }

    */
    
    private void showError(String message, Exception e) {
        resultArea.setText(message + "\n" + e.getMessage());
        e.printStackTrace();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interface().setVisible(true);
        });
    }

    public class DateLabelFormatter extends AbstractFormatter {

        private static final String DATE_PATTERN = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
}