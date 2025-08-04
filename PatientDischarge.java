package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientDischarge extends JFrame {
    private JTextField patientIdField;
    private JTextArea patientDetailsArea;
    private JButton searchButton, dischargeButton;
    private JLabel statusLabel;

    public PatientDischarge() {
        // Frame settings
        setTitle("Patient Discharge");
        setSize(850, 550); // Size of the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create panel and set layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(200, 200, 255)); // Set background color as per NewPatient.java
        add(panel);

        // Panel for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        inputPanel.setBackground(new Color(200, 200, 255)); // Match NewPatient.java color
        panel.add(inputPanel, BorderLayout.NORTH);

        // Patient ID input field
        JLabel patientIdLabel = new JLabel("Enter Patient ID:");
        patientIdField = new JTextField();
        inputPanel.add(patientIdLabel);
        inputPanel.add(patientIdField);

        // Buttons
        searchButton = new JButton("Search Patient");
        dischargeButton = new JButton("Discharge Patient");
        dischargeButton.setEnabled(false); // Initially disable discharge button
        dischargeButton.setBackground(new Color(255, 100, 100)); // Red color for discharge button
        searchButton.setBackground(new Color(100, 200, 100)); // Green color for search button

        inputPanel.add(searchButton);
        inputPanel.add(dischargeButton);

        // Status label
        statusLabel = new JLabel("Status: ", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(statusLabel, BorderLayout.CENTER);

        // Text Area for displaying patient details
        patientDetailsArea = new JTextArea(8, 30);
        patientDetailsArea.setEditable(false); // Make it read-only
        JScrollPane scrollPane = new JScrollPane(patientDetailsArea);
        panel.add(scrollPane, BorderLayout.SOUTH);

        // Add ActionListener for the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPatient();
            }
        });

        // Add ActionListener for the discharge button
        dischargeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dischargePatient();
            }
        });
    }

    private void searchPatient() {
        String patientId = patientIdField.getText().trim();

        if (patientId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a patient ID.");
            return;
        }

        try {
            // Create connection to the database
            conn c = new conn(); // Ensure you have the connection instance
            String query = "SELECT * FROM patient_info WHERE ID = ?";
            PreparedStatement pst = c.connection.prepareStatement(query);
            pst.setString(1, patientId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Display patient details
                patientDetailsArea.setText("Patient ID: " + rs.getString("ID") + "\n" +
                        "Name: " + rs.getString("Name") + "\n" +
                        "Gender: " + rs.getString("Gender") + "\n" +
                        "Disease: " + rs.getString("Patient_Disease") + "\n" +
                        "Room: " + rs.getString("Room_Number") + "\n" +
                        "Check-in Time: " + rs.getString("Time") + "\n" +
                        "Deposit: " + rs.getString("Deposit"));
                dischargeButton.setEnabled(true); // Enable discharge button once patient is found
            } else {
                JOptionPane.showMessageDialog(this, "Patient not found.");
                patientDetailsArea.setText("");
                dischargeButton.setEnabled(false);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching patient data: " + ex.getMessage());
        }
    }

    private void dischargePatient() {
        String patientId = patientIdField.getText().trim();

        if (patientId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a patient ID.");
            return;
        }

        try {
            // Create connection to the database
            conn c = new conn(); // Ensure you have the connection instance
            String query = "UPDATE patient_info SET Status = 'Discharged' WHERE ID = ?";
            PreparedStatement pst = c.connection.prepareStatement(query);
            pst.setString(1, patientId);
            int result = pst.executeUpdate();

            if (result > 0) {
                statusLabel.setText("Patient " + patientId + " discharged successfully.");
                dischargeButton.setEnabled(false);
                patientDetailsArea.setText(""); // Clear patient details
            } else {
                JOptionPane.showMessageDialog(this, "Failed to discharge patient.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error discharging patient: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PatientDischarge frame = new PatientDischarge();
            frame.setVisible(true);
        });
    }
}
