import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationForm extends JDialog {
    private JTextField tfreg;
    private JTextField tfgender;
    private JTextField tfprogram;
    private JTextField tfemail;
    private JTextField tfname;
    private JPasswordField pfpassword;
    private JPasswordField pfconfirmpassword;
    private JButton btnregister;
    private JButton btncancel;
    private JPanel RegisterPanel;


    public RegistrationForm(JFrame parent) {
        super(parent);
        setTitle("Create a new account");
        setContentPane(RegisterPanel);
        setMinimumSize(new Dimension(490, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnregister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registeruser();
            }
        });
        btncancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void registeruser() {
        String registration_number = tfreg.getText();
        String name = tfname.getText();
        String email = tfemail.getText();
        String program = tfprogram.getText();
        String gender = tfgender.getText();
        String password = String.valueOf(pfpassword.getPassword());
        String confirmpassword = String.valueOf(pfconfirmpassword.getPassword());

        if (registration_number.isEmpty() || name.isEmpty() || email.isEmpty() || program.isEmpty() || gender.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirmpassword)) {
            JOptionPane.showMessageDialog(this,
                    "Confirm Password does not match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = addUserToDatabase(registration_number,name,email,program,gender,password);
        if (user != null){
            dispose();
        }
        else{
            JOptionPane.showMessageDialog( this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public User user;
    private User addUserToDatabase(String registration_number, String name, String email, String program, String gender, String password) {
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/sign";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                 PreparedStatement preparedStatement = conn.prepareStatement(
                         "INSERT INTO users (registration_number, name, email, program, gender, password) VALUES (?, ?, ?, ?, ?,?)")) {

                preparedStatement.setString(1, registration_number);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, program);
                preparedStatement.setString(5, gender);
                preparedStatement.setString(6, password);

                // Insert row into the table
                int addedRows = preparedStatement.executeUpdate();
                if (addedRows > 0) {
                    user = new User();
                    user.registration_number = registration_number;
                    user.name = name;
                    user.email = email;
                    user.program = program;
                    user.gender = gender;
                    user.password = password;
                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "MySQL JDBC Driver not found.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Unexpected error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return user;
    }

    public static void main(String[] args) {
        RegistrationForm myform = new RegistrationForm(null);
        User user = myform.user;
        if (user != null) {
            System.out.println("Successful registration of:" + user.name);
        }
        else{
            System.out.println("Registration canceled");
        }

    }
}