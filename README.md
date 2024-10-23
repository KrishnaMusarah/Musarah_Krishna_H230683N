KRISHNA MUSARAH H230683N

Registration Form
Class Definition:
the class extendss JDialog, making it a modal dialog that blocks input to other windows until it is closed.
User Interface Elements
several JTextField components are used to capture input: tfreg for registration number, tfgender for gender, tfprogram for program, tfemail for email and tfname for name. Two JPasswordField components(pfpassword and confirmpassword) are used for password entry. Two buttons btnregister and btncancel for submiting the form and closing the dialog, respectively. A JPanel the RegisterPanel holds the UI components.
Constructor:
the constructor intializes the dialog's properties, including the title, size, modality, and location. Action listeners are added to the buttons. the btnregister button triggers the registeruser() method when clicked. the btn cancel button disposes of the dialog.
User Registration Logic:
The registeruser() method handles the registration process. it retrieves user input from the txt fields. it checks for empty ields and validates that the password and confirmation password match. if validation passes, it calls aadUserToDatabase() to attempt to store the user information in the user information in the database. if registration is successful, the dialog closes; otherwise, it shows an error message.
Database Interaction:
The addUseToDatabase() method manages thr connection to the MySQL database. it loads the MySQL JDBC driver and establishes a connection using DriverManager.A prepared statement is crated to insert the user's data into the users table. if the insertion is successful, a new User object is created with the entered details. the method handles exceptions for driver loading, connection issues, and general errors
Main Method:
The main method creates an instance of the RegistrationForm and displays it. After the dialog is closed, it checks whether the registration was successful and prints a message accordingly.

LoginForm
Class Definition:
The class extends JDialog, making it a modal dialog that prevents interaction with other windows until it is closed.
User Interface Elements:
The dialog contains a JPanel named LoginPanel which holds:
A JTextField (tfemail) for the user to enter their email address.
A JPasswordField (pfpassword) for the user to enter their password securely.
Two buttons:
btnOK for submitting the login form.
btnCancel for closing the dialog without logging in.
Constructor:
The constructor sets various properties of the dialog, such as the title, minimum size, modality, and the default close operation.
Action listeners are added to the buttons:
The btnOK button triggers the authentication process when clicked.
The btnCancel button disposes of the dialog, allowing the user to exit without logging in.
User Authentication Logic:
When btnOK is clicked, the action listener retrieves the entered email and password.
It calls the getAuthenticatedUser() method, passing the email and password for verification.
If the user is successfully authenticated (i.e., a matching record is found in the database), the dialog closes. If authentication fails, an error message is displayed using a JOptionPane.
Database Interaction:
The getAuthenticatedUser() method manages the connection to the MySQL database:
It establishes a connection using JDBC with the specified database URL, username, and password.
A prepared statement is created to query the users table, searching for a record that matches the provided email and password.
If a matching user is found, a new User object is instantiated, and its properties are populated with data from the database.
Finally, the database connection and statement are closed.
Main Method:
The main method creates an instance of LoginForm and displays the login dialog.
After the dialog is closed, it checks if the user was successfully authenticated and prints the user details (registration number, name, email, program, and gender) to the console.
If authentication was canceled, it prints a corresponding message.

Security Aspects Achieved by the project
Password Handling:
Use of JPasswordField: The use of a JPasswordField for password input ensures that the password is not displayed as plain text, providing a basic level of security against shoulder surfing.
Parameterized Queries:
Prepared Statements: The use of prepared statements in the SQL queries (PreparedStatement) helps prevent SQL injection attacks. By using placeholders (?) for user input, the risk of malicious SQL code being executed is significantly reduced.
Data Validation:
Input Validation: The code checks that essential fields (like email and password) are not empty before proceeding with authentication. This helps mitigate the risk of processing incomplete or invalid data.
Error Handling:
User Feedback: The system provides user feedback through JOptionPane dialogs when authentication fails. This helps users understand that their credentials are invalid without exposing sensitive information.
Modal Dialog:
Blocking Input: The use of a modal dialog prevents users from interacting with other parts of the application until they have completed the login process. This is a good user experience practice that can also help prevent accidental data exposure.

The details for the users I registered:
Email: krishnamusarah@gmail.com  password: mkayruvah
Email: luceeyah@gmail.com        password:lucy

