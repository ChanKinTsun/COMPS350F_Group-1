import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.awt.image.BufferedImage;

import javax.mail.*;
import javax.mail.internet.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;



public class HomePageUI extends JFrame {
    private JLabel lblTitle;
    private JTabbedPane tabbedPane;
    private JPanel profilePanel;
    private JPanel contentPanel;
    private JPanel communicationPanel;
    private JPanel studentContactPanel;
    private JPanel studyMaterialsPanel;


     
    private JTextField txtStudentName;
    private JTextField txtStudentID;
    private JTextField txtstudentpassword;
    private JTextField txtSex;
    private JTextField txtBirthday;
    private JTextField txtEmail;
    private JTextField txtPhoneNumber;
    private JButton btnModify;
    private JButton btnSave;

    private JLabel lblCGPA;
    private JTable table;

    private JTextArea messageTextArea;
    private JTextArea messageHistoryTextArea;

    private static String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    private void sendEmail(String studentName, String studentEmail, String recipientEmail, String message) {
         
        String host = "localhost";
        String username = "root";
        String password = "root";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");

         
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(studentEmail));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            emailMessage.setSubject("New message from student: " + studentName);
            emailMessage.setText(message);

             
            Transport.send(emailMessage);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private boolean validateInput() {
        String sex = txtSex.getText();
        String birthday = txtBirthday.getText();
        String email = txtEmail.getText();
        String phoneNumber = txtPhoneNumber.getText();
    
        if (sex.isEmpty() || birthday.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            JOptionPane.showMessageDialog(HomePageUI.this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
         
        if (!sex.equalsIgnoreCase("Male") && !sex.equalsIgnoreCase("Female")) {
            JOptionPane.showMessageDialog(HomePageUI.this, "Invalid sex. Please enter 'Male' or 'Female'.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
         
        if (!isValidDate(birthday)) {
            JOptionPane.showMessageDialog(HomePageUI.this, "Invalid birthday. Please enter a valid date in YYYY-MM-DD format.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
         
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(HomePageUI.this, "Invalid email address. Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
         
        if (!isValidPhoneNumber(phoneNumber)) {
            JOptionPane.showMessageDialog(HomePageUI.this, "Invalid phone number. Please enter a valid 8-digit phone number.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
         
        return true;
    }
    
    private boolean isValidDate(String date) {
         
         
        String pattern = "\\d{4}-\\d{2}-\\d{2}";
        return date.matches(pattern);
    }
    
    private boolean isValidEmail(String email) {
         
         
        return email.contains("@");
    }
    
    private boolean isValidPhoneNumber(String phoneNumber) {
         
         
        String pattern = "\\d{8}";
        return phoneNumber.matches(pattern);
    }

    private void calculateCGPA() {
         
        TableModel model = table.getModel();
        int rowCount = model.getRowCount();
    
         
        double totalCredits = 0;
        double totalWeightedPoints = 0;
    
         
        for (int i = 0; i < rowCount; i++) {
            String grade = model.getValueAt(i, 1).toString();
            double credits = 5;
    
             
            double weightedPoints;
            if (grade.equals("A+")) {
                weightedPoints = 4.3;
            } else if (grade.equals("A")) {
                weightedPoints = 4.0;
            } else if (grade.equals("A-")) {
                weightedPoints = 3.7;
            } else if (grade.equals("B+")) {
                weightedPoints = 3.3;
            } else if (grade.equals("B")) {
                weightedPoints = 3.0;
            } else if (grade.equals("B-")) {
                weightedPoints = 2.7;
            } else if (grade.equals("C+")) {
                weightedPoints = 2.3;
            } else if (grade.equals("C")) {
                weightedPoints = 2.0;
            } else if (grade.equals("C-")) {
                weightedPoints = 1.7;
            } else if (grade.equals("D")) {
                weightedPoints = 1.0;
            } else {
                weightedPoints = 0.0;
            }
    
             
            totalCredits += credits;
            totalWeightedPoints += credits * weightedPoints;
        }
    
         
        double cGPA = totalWeightedPoints / totalCredits;
    
         
        lblCGPA.setText("cGPA: " + String.format("%.2f", cGPA));
    }

    public class MarqueeLabel extends JLabel implements ActionListener {
        private Timer timer;
        private int scrollSpeed;
        private int scrollDirection;
    
        public MarqueeLabel(String text) {
            super(text);
            setOpaque(true);
            setBackground(Color.YELLOW);
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(new Font("Arial", Font.BOLD, 16));
            scrollSpeed = 3;
            scrollDirection = 1;
            timer = new Timer(30, this);
            timer.start();
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
            String labelText = getText();
            int textWidth = getFontMetrics(getFont()).stringWidth(labelText);
            int labelWidth = getWidth();
    
            if (scrollDirection == 1) {
                setLocation(getX() - scrollSpeed, getY());
                if (getX() <= -textWidth) {
                    setLocation(labelWidth, getY());
                }
            } else {
                setLocation(getX() + scrollSpeed, getY());
                if (getX() >= labelWidth) {
                    setLocation(-textWidth, getY());
                }
            }
        }
    }

    public HomePageUI(String username) {
         
        setTitle("S350F Group 1");
        setSize(640, 480);

         
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

         
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        int x = (screenWidth - windowWidth) / 2;
        int y = (screenHeight - windowHeight) / 2;

         
        setLocation(x, y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel hkmutitlePanel = new JPanel(new BorderLayout());
        hkmutitlePanel.setBackground(Color.WHITE);

        MarqueeLabel marqueeLabel = new MarqueeLabel("Signal No. 8 Northwest Gale or Storm is now in effect. Please stay in a safe place.");
        hkmutitlePanel.add(marqueeLabel, BorderLayout.NORTH);

        lblTitle = new JLabel("<html><center>HKMU<br>Student Academic Record Management System</center></html>");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        hkmutitlePanel.add(lblTitle, BorderLayout.CENTER);

        Timer timer = new Timer(15000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                marqueeLabel.setVisible(false);
            }
        });
        timer.setRepeats(false);
        timer.start();

        add(hkmutitlePanel, BorderLayout.NORTH);

         
        tabbedPane = new JTabbedPane();
        profilePanel = new JPanel();
        profilePanel.setLayout(new GridLayout(8, 2));

         
        JLabel lblStudentName = new JLabel("Student Name:");
        txtStudentName = new JTextField("Student 1");
        txtStudentName.setEditable(false);  
        profilePanel.add(lblStudentName);
        profilePanel.add(txtStudentName);

        JLabel lblStudentID = new JLabel("Student ID:");
        txtStudentID = new JTextField("12345678");
        txtStudentID.setEditable(false);  
        profilePanel.add(lblStudentID);
        profilePanel.add(txtStudentID);

        JLabel lblstudentpassword = new JLabel("Student Password:");
        txtstudentpassword = new JTextField("123456");
        txtstudentpassword.setEditable(false);
        profilePanel.add(lblstudentpassword);
        profilePanel.add(txtstudentpassword);
        

        JLabel lblSex = new JLabel("Sex:");
        txtSex = new JTextField("Male");
        txtSex.setEditable(false);  
        profilePanel.add(lblSex);
        profilePanel.add(txtSex);

        JLabel lblBirthday = new JLabel("Birthday:");
        txtBirthday = new JTextField("2002-01-01");
        txtBirthday.setEditable(false);  
        profilePanel.add(lblBirthday);
        profilePanel.add(txtBirthday);

        JLabel lblEmail = new JLabel("Email Address:");
        txtEmail = new JTextField("s1234567@live.hkmu.edu.hk");
        txtEmail.setEditable(false);  
        profilePanel.add(lblEmail);
        profilePanel.add(txtEmail);

        JLabel lblPhoneNumber = new JLabel("Phone Number:");
        txtPhoneNumber = new JTextField("88888888");
        txtPhoneNumber.setEditable(false);  
        profilePanel.add(lblPhoneNumber);
        profilePanel.add(txtPhoneNumber);

        btnModify = new JButton("Modify");
        btnModify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 
                txtstudentpassword.setEditable(true);
                txtSex.setEditable(true);
                txtBirthday.setEditable(true);
                txtEmail.setEditable(true);
                txtPhoneNumber.setEditable(true);
            }
        });
        btnModify.setPreferredSize(new Dimension(100, 30));  
        profilePanel.add(btnModify);

         
        btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                txtStudentName.setEditable(false);
                txtStudentID.setEditable(false);
                txtstudentpassword.setEditable(false);
                txtSex.setEditable(false);
                txtBirthday.setEditable(false);
                txtEmail.setEditable(false);
                txtPhoneNumber.setEditable(false);
                } 
            }
        });
        btnModify.setPreferredSize(new Dimension(100, 30));  
        profilePanel.add(btnSave);
    
 
contentPanel = new JPanel();
contentPanel.setLayout(new BorderLayout());

         
JPanel searchPanel = new JPanel();
searchPanel.setLayout(new FlowLayout());

         
JLabel lblSearch = new JLabel("Search:");
JTextField txtSearch = new JTextField(20);
searchPanel.add(lblSearch);
searchPanel.add(txtSearch);

         
JLabel lblFilter = new JLabel("Filter:");
String[] filterOptions = {"All", "2022 Autumn Term", "2023 Spring Term", "2023 Autumn Term"};
JComboBox<String> filterComboBox = new JComboBox<>(filterOptions);
searchPanel.add(lblFilter);
searchPanel.add(filterComboBox);

 
JButton btnSearch = new JButton("Search");
JButton btnFilter = new JButton("Filter");
searchPanel.add(btnSearch);
searchPanel.add(btnFilter);

 
String[] columnNames = {"Subject", "Grade", "Term"};
Object[][] data = {
    {"COMPUTER ARCHITECTURE", "A", "2022 Autumn Term"},
    {"COMPUTER NETWORKING", "B", "2022 Autumn Term"},
    {"DIGITAL SIGNAL PROCESSING", "C", "2022 Autumn Term"},
    {"ADVANCED COMPUTER DESIGN", "D", "2022 Autumn Term"},
    {"OPERATING SYSTEMS", "A", "2023 Spring Term"},
    {"MOBILE APPLICATION PROGRAMMING", "B", "2023 Spring Term"},
    {"ROUTING & SWITCHING TECH", "C", "2023 Spring Term"},
    {"MULTIMEDIA TECHNOLOGIES", "D", "2023 Spring Term"},
    {"COMMUNICATION SYSTEMS", "A", "2023 Autumn Term"},
    {"SOFTWARE ENGINEERING", "B", "2023 Autumn Term"},
    {"QUALITY MANAGEMENT FOR SCIENCE AND TECHNOLOGY", "C", "2023 Autumn Term"},
};
DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
table = new JTable(tableModel);
table.setEnabled(false);
JScrollPane scrollPane = new JScrollPane(table);
contentPanel.add(searchPanel, BorderLayout.NORTH);
contentPanel.add(scrollPane, BorderLayout.CENTER);

 
btnSearch.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String searchText = txtSearch.getText().trim();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        if (searchText.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        }
    }
});

btnFilter.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String selectedFilter = (String) filterComboBox.getSelectedItem();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        if (selectedFilter.equals("All")) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter(selectedFilter, 2));
        }
    }
});



JPanel cgpaPanel = new JPanel();
lblCGPA = new JLabel("cGPA: ");
contentPanel.add(lblCGPA, BorderLayout.SOUTH);
JButton btnCalculateCGPA = new JButton("Calculate cGPA");
btnCalculateCGPA.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        calculateCGPA();
    }
});
cgpaPanel.add(lblCGPA);
cgpaPanel.add(btnCalculateCGPA);
contentPanel.add(cgpaPanel, BorderLayout.SOUTH);


communicationPanel = new JPanel();
communicationPanel.setLayout(new BorderLayout());

 
messageHistoryTextArea = new JTextArea();
messageHistoryTextArea.setEditable(false);
JScrollPane historyScrollPane = new JScrollPane(messageHistoryTextArea);
communicationPanel.add(historyScrollPane, BorderLayout.CENTER);

 
JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        messageTextArea = new JTextArea();
        JScrollPane inputScrollPane = new JScrollPane(messageTextArea);
        inputPanel.add(inputScrollPane, BorderLayout.CENTER);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = messageTextArea.getText();
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                String role = "Student 1";  
                messageHistoryTextArea.append(timestamp + " " + role + ": " + message + "\n");
                messageTextArea.setText("");  
            }
        });
inputPanel.add(sendButton, BorderLayout.SOUTH);

communicationPanel.add(inputPanel, BorderLayout.SOUTH);

 
messageHistoryTextArea.append(getTimestamp() + " Student 2: Hello, Any one here?\n");
messageHistoryTextArea.append(getTimestamp() + " Student 1: What can I help you?\n");
messageHistoryTextArea.append(getTimestamp() + " Student 2: I have a question about the assignment.\n");

 
studentContactPanel = new JPanel();
studentContactPanel.setLayout(new GridLayout(5, 2));

 
JLabel lblStudentName1 = new JLabel("Name:");
JTextField txtStudentName = new JTextField();
studentContactPanel.add(lblStudentName1);
studentContactPanel.add(txtStudentName);

JLabel lblEmail2 = new JLabel("Email:");
JTextField txtEmail = new JTextField();
studentContactPanel.add(lblEmail2);
studentContactPanel.add(txtEmail);

JLabel lblRecipient = new JLabel("Recipient:");
String[] recipients = {"Teacher", "Admin"};  
JComboBox<String> recipientComboBox = new JComboBox<>(recipients);
studentContactPanel.add(lblRecipient);
studentContactPanel.add(recipientComboBox);

JLabel lblMessage = new JLabel("Message:");
JTextArea txtMessage = new JTextArea();
JScrollPane messageScrollPane = new JScrollPane(txtMessage);
studentContactPanel.add(lblMessage);
studentContactPanel.add(messageScrollPane);

JLabel lblSend = new JLabel("");
JButton btnSend = new JButton("Send");
btnSend.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
         
        String studentName = txtStudentName.getText();
        String email = txtEmail.getText();
        String recipient = recipientComboBox.getSelectedItem().toString();
        String message = txtMessage.getText();

         
        String recipientEmail;
        if (recipient.equals("Teacher")) {
            recipientEmail = "teacher@live.hkmu.edu.hk";
        } else {
            recipientEmail = "admin@live.hkmu.edu.hk";
        }

         
        sendEmail(studentName, email, recipientEmail, message);

         
        txtStudentName.setText("");
        txtEmail.setText("");
        txtMessage.setText("");

        JOptionPane.showMessageDialog(HomePageUI.this, "Email sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
});
studentContactPanel.add(lblSend);
studentContactPanel.add(btnSend);

 
JPanel courseSchedulePanel = new JPanel();
courseSchedulePanel.setLayout(new BorderLayout());

 
DefaultTableModel courseTableModel = new DefaultTableModel();
courseTableModel.addColumn("Time");
courseTableModel.addColumn("Monday");
courseTableModel.addColumn("Tuesday");
courseTableModel.addColumn("Wednesday");
courseTableModel.addColumn("Thursday");
courseTableModel.addColumn("Friday");

 
courseTableModel.addRow(new Object[]{"8:00 - 9:30", "COMP S350F", "ELEC S304F", "", "", "ELEC S411F"});
courseTableModel.addRow(new Object[]{"9:30 - 11:00", "", "TC S319F", "", "COMP S350F", ""});
courseTableModel.addRow(new Object[]{"11:00 - 12:30", "", "", "ELEC S337F", "", "ELEC S304F"});
courseTableModel.addRow(new Object[]{"13:30 - 15:00", "", "COMP S267F", "", "COMP S350F", "TC S319F"});
courseTableModel.addRow(new Object[]{"15:00 - 16:30", "ELEC S304F", "", "COMP S350F", "", ""});

 
JTable courseTable = new JTable(courseTableModel);
courseTable.setRowHeight(50);

 
JScrollPane courseScrollPane = new JScrollPane(courseTable);

 
courseSchedulePanel.add(courseScrollPane, BorderLayout.CENTER);

 
studyMaterialsPanel = new JPanel();
studyMaterialsPanel.setLayout(new BorderLayout());

 
JLabel lblStudyMaterials = new JLabel("Study Materials");
lblStudyMaterials.setFont(new Font("Arial", Font.BOLD, 18));
lblStudyMaterials.setHorizontalAlignment(SwingConstants.CENTER);
studyMaterialsPanel.add(lblStudyMaterials, BorderLayout.NORTH);

 
DefaultMutableTreeNode root = new DefaultMutableTreeNode("Courses");

 
DefaultMutableTreeNode SENode = new DefaultMutableTreeNode("COMP S350F");
SENode.add(new DefaultMutableTreeNode("COMP S350F Course Introduction.pdf"));
SENode.add(new DefaultMutableTreeNode("COMP S350F CH1.pdf"));
root.add(SENode);

DefaultMutableTreeNode CSNode = new DefaultMutableTreeNode("ELEC S304F");
CSNode.add(new DefaultMutableTreeNode("Communication Systems.pdf"));
CSNode.add(new DefaultMutableTreeNode("ABC.pdf"));
CSNode.add(new DefaultMutableTreeNode("DEF2.pdf"));
root.add(CSNode);

DefaultMutableTreeNode QMNode = new DefaultMutableTreeNode("TC S319F");
QMNode.add(new DefaultMutableTreeNode("ABC1.pdf"));
QMNode.add(new DefaultMutableTreeNode("DEF2.pdf"));
QMNode.add(new DefaultMutableTreeNode("GHI3.pdf"));
root.add(QMNode);

 
JTree materialsTree = new JTree(root);
materialsTree.setFont(new Font("Arial", Font.PLAIN, 14));
materialsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

 
JScrollPane treeScrollPane = new JScrollPane(materialsTree);
studyMaterialsPanel.add(treeScrollPane, BorderLayout.CENTER);

 
JPanel contentPanel1 = new JPanel();
contentPanel1.setLayout(new BorderLayout());

 
JLabel contentLabel = new JLabel();
contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
contentLabel.setVerticalAlignment(SwingConstants.CENTER);

 
JScrollPane contentScrollPane = new JScrollPane(contentLabel);

 
materialsTree.addTreeSelectionListener(new TreeSelectionListener() {
    public void valueChanged(TreeSelectionEvent event) {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) materialsTree.getLastSelectedPathComponent();
        if (selectedNode != null && !selectedNode.isRoot()) {
             
            String selectedMaterial = selectedNode.toString();
            if (selectedMaterial != null) {
                 
                loadMaterialContent(selectedMaterial);
            }
        }
    }

    private String loadMaterialContent(String materialName) {
     
    try {
        String path = ClassLoader.getSystemResource("").getPath();
        File file = new File(path+"COMP S350F Course Introduction.pdf");
        PDDocument doc1 = Loader.loadPDF(file);

         
        PDFRenderer pdfRenderer = new PDFRenderer(doc1);
        BufferedImage image = pdfRenderer.renderImage(0);  

         
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));

         
        contentLabel.setIcon(new ImageIcon(image));

         
        contentPanel1.revalidate();
        contentPanel1.repaint();

         
        return "Content loaded successfully.";
    } catch (IOException e) {
        e.printStackTrace();
        return "Failed to load content.";
    }
}
});

 
studyMaterialsPanel.add(treeScrollPane, BorderLayout.WEST);
studyMaterialsPanel.add(contentScrollPane, BorderLayout.CENTER);

JPanel enrollmentPanel = new JPanel();
enrollmentPanel.setLayout(new BorderLayout());

 
JPanel searchPanel1 = new JPanel();
searchPanel1.setLayout(new FlowLayout());

JLabel searchLabel1 = new JLabel("Search:");
JTextField searchTextField = new JTextField(20);
searchPanel1.add(searchLabel1);
searchPanel1.add(searchTextField);

JLabel filterLabel1 = new JLabel("Filter:");
String[] filterOptions1 = {"All", "Enrolled", "Not Enrolled"};
JComboBox<String> filterComboBox1 = new JComboBox<>(filterOptions1);
searchPanel1.add(filterLabel1);
searchPanel1.add(filterComboBox1);

enrollmentPanel.add(searchPanel1, BorderLayout.NORTH);

 
String[] columnNames1 = {"Course", "Status"};
Object[][] data1 = {
    {"Software Engineering", "Enrolled"},
    {"Communication Systems", "Enrolled"},
    {"Physics 1", "Not Enrolled"}
};
DefaultTableModel tableModel1 = new DefaultTableModel(data1, columnNames1);
JTable enrollmentTable1 = new JTable(tableModel1);
JScrollPane scrollPane1 = new JScrollPane(enrollmentTable1);
enrollmentPanel.add(scrollPane1, BorderLayout.CENTER);

 
JButton addButton1 = new JButton("Add Course");
JButton submitButton1 = new JButton("Submit");
JPanel buttonPanel1 = new JPanel();
buttonPanel1.add(addButton1);
buttonPanel1.add(submitButton1);
enrollmentPanel.add(buttonPanel1, BorderLayout.SOUTH);

 
JPanel scholarshipPanel = new JPanel();
scholarshipPanel.setLayout(new BorderLayout());

 
JLabel titleLabel = new JLabel("Apply Scholarship");
titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
scholarshipPanel.add(titleLabel, BorderLayout.NORTH);

 
JPanel formPanel = new JPanel();
formPanel.setLayout(new GridBagLayout());
GridBagConstraints c = new GridBagConstraints();
c.gridx = 0;
c.gridy = 0;
c.anchor = GridBagConstraints.WEST;
c.insets = new Insets(5, 5, 5, 5);

JLabel nameLabel = new JLabel("Name:");
formPanel.add(nameLabel, c);
c.gridx++;
JLabel nameTextField = new JLabel("Student 1");
formPanel.add(nameTextField, c);

c.gridx = 0;
c.gridy++;
JLabel gpaLabel = new JLabel("GPA:");
formPanel.add(gpaLabel, c);
c.gridx++;
JLabel gpaTextField = new JLabel("2.64");
formPanel.add(gpaTextField, c);

c.gridx = 0;
c.gridy++;
JLabel essayLabel = new JLabel("Essay:");
formPanel.add(essayLabel, c);
c.gridx++;
JButton uploadButton0 = new JButton("Upload");
formPanel.add(uploadButton0, c);

c.gridx = 0;
c.gridy++;
JLabel recommendationLabel = new JLabel("Recommendation Letter:");
formPanel.add(recommendationLabel, c);
c.gridx++;
JButton uploadButton1 = new JButton("Upload");
formPanel.add(uploadButton1, c);

c.gridx = 0;
c.gridy++;
JLabel transcriptLabel = new JLabel("Transcript:");
formPanel.add(transcriptLabel, c);
c.gridx++;
JButton uploadButton2 = new JButton("Upload");
formPanel.add(uploadButton2, c);

scholarshipPanel.add(formPanel, BorderLayout.CENTER);

 
JButton submitButton = new JButton("Submit");
JPanel buttonPanel = new JPanel();
buttonPanel.add(submitButton);
scholarshipPanel.add(buttonPanel, BorderLayout.SOUTH);

JPanel submitassignmentPanel = new JPanel();
submitassignmentPanel.setLayout(new BorderLayout());

JLabel submitassignmentLabel = new JLabel("Submit Assignment");
submitassignmentLabel.setFont(new Font("Arial", Font.BOLD, 18));
submitassignmentLabel.setHorizontalAlignment(SwingConstants.CENTER);
submitassignmentPanel.add(submitassignmentLabel, BorderLayout.NORTH);

JLabel submitassignment1Label = new JLabel("Your Assignment:");
JButton uploadButton3 = new JButton("Upload");
JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
submitPanel.add(submitassignment1Label);
submitPanel.add(uploadButton3);

JButton submitButton3 = new JButton("Submit");

JPanel submitsubmitPanel = new JPanel(new BorderLayout());
submitsubmitPanel.add(submitPanel,BorderLayout.NORTH);
submitsubmitPanel.add(submitButton3, BorderLayout.SOUTH);

submitassignmentPanel.add(submitsubmitPanel, BorderLayout.CENTER);

uploadButton3.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            submitassignment1Label.setText("Your Assignment: " + fileName);
        }
    }
});

submitButton3.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
         
         
        JOptionPane.showMessageDialog(null, "Submission successful!");
         
        submitassignment1Label.setText("Your Assignment:");
    }
});




         
        tabbedPane.addTab("Profile", profilePanel);
        tabbedPane.addTab("Academic Record", contentPanel);
        tabbedPane.addTab("Course Schedule", courseSchedulePanel);
        tabbedPane.addTab("Study Materials", studyMaterialsPanel);
        tabbedPane.addTab("Submit Assignment", submitassignmentPanel);
        tabbedPane.addTab("Course Enrollment", enrollmentPanel);
        tabbedPane.addTab("Discussion Area", communicationPanel);
        tabbedPane.addTab("Contact", studentContactPanel);
        tabbedPane.add("Apply Scholarship", scholarshipPanel);
        add(tabbedPane, BorderLayout.CENTER);

         
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(HomePageUI.this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
                 
                if (choice == JOptionPane.YES_OPTION) {
                    
                    LoginUI loginUI = new LoginUI();
                    loginUI.setVisible(true);

                    setVisible(false);   
                    dispose();   
                }
            }
        });
        add(btnLogout, BorderLayout.SOUTH);
    }
}