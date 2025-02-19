import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog{
    private JPanel LoginPanel;
    private JTextField tfemail;
    private JPasswordField pfpassword;
    private JButton btnOK;
    private JButton btnCancel;

    public LoginForm(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(LoginPanel);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfemail.getText();
                String password = String.valueOf(pfpassword.getPassword());

                user = getAuthenticatedUser(email,password);

                if (user != null){
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog( LoginForm.this,
                            "Email or Password Invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
    public User user;
    private User getAuthenticatedUser(String email, String password){
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost:3306/sign";
        final String USERNAME = "root";
        final String PASSWORD = "";

       try{
           Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
           //Connected to database successfully

           Statement stmt = conn.createStatement();
           String sql = "SELECT * FROM users WHERE email=? AND password=?";
           PreparedStatement preparedStatement = conn.prepareStatement(sql);
           preparedStatement.setString(1, email);
           preparedStatement.setString(2, password);

           ResultSet resultSet = preparedStatement.executeQuery();

           if (resultSet.next()){
               user = new User ();
               user.registration_number = resultSet.getString("registration_number");
               user.name = resultSet.getString("name");
               user.email = resultSet.getString("email");
               user.program = resultSet.getString("program");
               user.gender = resultSet.getString("gender");
               user.password = resultSet.getString("password");
           }

           stmt.close();
           conn.close();

       }catch(Exception e){
           e.printStackTrace();
       }


            return user;
    }

    public static void main(String []args){
        LoginForm loginForm = new LoginForm(null);
        User user = loginForm.user;
        if (user!= null) {
            System.out.println("Successful Authentication of: " + user.name);
            System.out.println("            Registration Number: " + user.registration_number);
            System.out.println("            Email: " + user.email);
            System.out.println("            Program: " + user.program);
            System.out.println("            Gender: " + user.gender);
        }
        else {
            System.out.println("Authentication Canceled");
        }
    }
}
