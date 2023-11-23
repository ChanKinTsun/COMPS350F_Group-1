import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel studentPanel;
    private JPanel teacherPanel;
    private JPanel adminPanel;
    
    private JLabel lblStudentUsername;
    private JLabel lblStudentPassword;
    private JTextField txtStudentUsername;
    private JPasswordField txtStudentPassword;
    private JButton btnStudentLogin;
    
    private JLabel lblTeacherUsername;
    private JLabel lblTeacherPassword;
    private JTextField txtTeacherUsername;
    private JPasswordField txtTeacherPassword;
    private JButton btnTeacherLogin;
    
    private JLabel lblAdminUsername;
    private JLabel lblAdminPassword;
    private JTextField txtAdminUsername;
    private JPasswordField txtAdminPassword;
    private JButton btnAdminLogin;

    public LoginUI() {
         
        setTitle("S350F Group 1");
        
         
        tabbedPane = new JTabbedPane();
        
         
        studentPanel = new JPanel();
        lblStudentUsername = new JLabel("Username:");
        lblStudentPassword = new JLabel("Password:");
        txtStudentUsername = new JTextField(20);
        txtStudentPassword = new JPasswordField(20);
        btnStudentLogin = new JButton("Login");
        studentPanel.setLayout(new GridLayout(3, 2));
        studentPanel.add(lblStudentUsername);
        studentPanel.add(txtStudentUsername);
        studentPanel.add(lblStudentPassword);
        studentPanel.add(txtStudentPassword);
        studentPanel.add(new JLabel());  
        studentPanel.add(btnStudentLogin);
        tabbedPane.addTab("Student", studentPanel);

        
        
         
        teacherPanel = new JPanel();
        lblTeacherUsername = new JLabel("Username:");
        lblTeacherPassword = new JLabel("Password:");
        txtTeacherUsername = new JTextField(20);
        txtTeacherPassword = new JPasswordField(20);
        btnTeacherLogin = new JButton("Login");
        teacherPanel.setLayout(new GridLayout(3, 2));
        teacherPanel.add(lblTeacherUsername);
        teacherPanel.add(txtTeacherUsername);
        teacherPanel.add(lblTeacherPassword);
        teacherPanel.add(txtTeacherPassword);
        teacherPanel.add(new JLabel());  
        teacherPanel.add(btnTeacherLogin);
        tabbedPane.addTab("Teacher", teacherPanel);
        
         
        adminPanel = new JPanel();
        lblAdminUsername = new JLabel("Username:");
        lblAdminPassword = new JLabel("Password:");
        txtAdminUsername = new JTextField(20);
        txtAdminPassword = new JPasswordField(20);
        btnAdminLogin = new JButton("Login");
        adminPanel.setLayout(new GridLayout(3, 2));
        adminPanel.add(lblAdminUsername);
        adminPanel.add(txtAdminUsername);
        adminPanel.add(lblAdminPassword);
        adminPanel.add(txtAdminPassword);
        adminPanel.add(new JLabel());  
        adminPanel.add(btnAdminLogin);
        tabbedPane.addTab("Admin", adminPanel);
        
         
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);

         
        setSize(400, 300);
        
         
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        
         
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        int x = (screenWidth - windowWidth) / 2;
        int y = (screenHeight - windowHeight) / 2;
        
         
        setLocation(x, y);
        
         
        btnStudentLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtStudentUsername.getText();
                String password = new String(txtStudentPassword.getPassword());
                
                 
                if (validateLogin(username, password)) {
                     
                    HomePageUI homePageUI = new HomePageUI(username);
                    homePageUI.setVisible(true);
                    
                     
                    setVisible(false);   
                    dispose();   
                } else {
                     
                    JOptionPane.showMessageDialog(LoginUI.this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
                 
                txtStudentUsername.setText("");
                txtStudentPassword.setText("");
            }
        });
        
        btnTeacherLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtTeacherUsername.getText();
                String password = new String(txtTeacherPassword.getPassword());
                
                 
                if (validateLogin(username, password)) {
                     
                    HomePageUI2 homePageUI2 = new HomePageUI2(username);
                    homePageUI2.setVisible(true);
                    
                     
                    setVisible(false);   
                    dispose();   
                } else {
                     
                    JOptionPane.showMessageDialog(LoginUI.this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
                 
                txtTeacherUsername.setText("");
                txtTeacherPassword.setText("");

            }
        });
        
        btnAdminLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtAdminUsername.getText();
                String password = new String(txtAdminPassword.getPassword());
                
                 
                if (validateLogin(username, password)) {
                     
                    HomePageUI3 homePageUI3 = new HomePageUI3(username);
                    homePageUI3.setVisible(true);
                    
                     
                    setVisible(false);   
                    dispose();   
                } else {
                     
                    JOptionPane.showMessageDialog(LoginUI.this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
                 
                txtAdminUsername.setText("");
                txtAdminPassword.setText("");
            }
        });
        
         
        setSize(300, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private boolean validateLogin(String username, String password) {
         
        if (username.equals("student") && password.equals("123456")) {
            return true;
        } else if (username.equals("teacher") && password.equals("123456")) {
            return true;
        } else if (username.equals("admin") && password.equals("123456")) {
            return true;
        }
        
        return false;
    }

    public static void main(String[] args) {
         
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginUI();
            }
        });
    }
}