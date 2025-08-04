package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdatePatient extends JFrame implements ActionListener {

    JTextField tfId, tfDisease, tfRoom, tfDeposit;
    JButton search, update, delete, back;
    JLabel nameLabel;

    UpdatePatient() {
        setLayout(null);

        JLabel lblId = new JLabel("Enter Patient ID:");
        lblId.setBounds(30, 30, 150, 30);
        add(lblId);

        tfId = new JTextField();
        tfId.setBounds(180, 30, 200, 30);
        add(tfId);

        search = new JButton("Search");
        search.setBounds(400, 30, 100, 30);
        search.addActionListener(this);
        add(search);

        nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(30, 80, 400, 30);
        add(nameLabel);

        JLabel lblDisease = new JLabel("Disease:");
        lblDisease.setBounds(30, 130, 100, 30);
        add(lblDisease);

        tfDisease = new JTextField();
        tfDisease.setBounds(180, 130, 200, 30);
        add(tfDisease);

        JLabel lblRoom = new JLabel("Room No:");
        lblRoom.setBounds(30, 180, 100, 30);
        add(lblRoom);

        tfRoom = new JTextField();
        tfRoom.setBounds(180, 180, 200, 30);
        add(tfRoom);

        JLabel lblDeposit = new JLabel("Deposit:");
        lblDeposit.setBounds(30, 230, 100, 30);
        add(lblDeposit);

        tfDeposit = new JTextField();
        tfDeposit.setBounds(180, 230, 200, 30);
        add(tfDeposit);

        update = new JButton("Update");
        update.setBounds(50, 300, 100, 30);
        update.addActionListener(this);
        add(update);

        delete = new JButton("Delete");
        delete.setBounds(170, 300, 100, 30);
        delete.addActionListener(this);
        add(delete);

        back = new JButton("Back");
        back.setBounds(290, 300, 100, 30);
        back.addActionListener(this);
        add(back);

        setSize(550, 400);
        setLocation(400, 250);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        String id = tfId.getText();

        try {
            conn c = new conn();

            if(ae.getSource() == search){
                ResultSet rs = c.statement.executeQuery("SELECT * FROM patient_info WHERE Number = '"+id+"'");
                if(rs.next()){
                    nameLabel.setText("Name: " + rs.getString("Name"));
                    tfDisease.setText(rs.getString("Patient_Disease"));
                    tfRoom.setText(rs.getString("Room_Number"));
                    tfDeposit.setText(rs.getString("Deposit"));
                } else {
                    JOptionPane.showMessageDialog(null, "Patient not found!");
                }
            } else if(ae.getSource() == update){
                String q = "UPDATE patient_info SET Disease = '"+tfDisease.getText()+"', Room_No = '"+tfRoom.getText()+"', Deposit = '"+tfDeposit.getText()+"' WHERE ID_Number = '"+id+"'";
                c.statement.executeUpdate(q);
                JOptionPane.showMessageDialog(null, "Details Updated!");
            } else if(ae.getSource() == delete){
                String q = "DELETE FROM patient_info WHERE ID_Number = '"+id+"'";
                c.statement.executeUpdate(q);
                JOptionPane.showMessageDialog(null, "Patient Deleted!");
            } else if(ae.getSource() == back){
                setVisible(false);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UpdatePatient();
    }
}
