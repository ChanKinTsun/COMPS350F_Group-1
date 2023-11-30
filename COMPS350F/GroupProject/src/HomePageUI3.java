import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.image.BufferedImage;

public class HomePageUI3 extends JFrame {
    private JLabel lblWelcome;
    private JTabbedPane tabbedPane;
    private JPanel profilePanel;
    private JPanel gradesPanel;
    private JTextField txtStudentName;
    private JTextField txtStudentID;
    private JTextField txtadminpassword;
    private JTextField txtSex;
    private JTextField txtBirthday;
    private JTextField txtEmail;
    private JTextField txtPhoneNumber;
    private JButton btnModify;
    private JButton btnSave;

    
    
    private JComboBox<String> departmentComboBox;
    private JTextArea contactInfoTextArea;
    private JList<String> studentList;
    private JList<String> studentList1;
    private DefaultListModel<String> studentListModel;
    private DefaultListModel<String> studentListModel1;
    private JTable gradesTable;
    private JScrollPane tableScrollPane;
    private JButton btnModifyGrades;
    private JLabel lblCGPA;
    private TableModel model;
    private String[][] gradesData = {
            {"Subject 1", "-"},
            {"Subject 2", "-"},
            {"Subject 3", "-"},
            {"Subject 4", "-"}
        };
    private DefaultTableModel gradesTableModel;
    private Map<String, String> studentQuestions;

    private JTable editprofileTable;
    private DefaultTableModel editprofileTableModel;

    class MyListCellRenderer extends DefaultListCellRenderer {
    private DefaultListModel<String> model;
    private Map<String, Double> studentCgpaMap;

    

    public MyListCellRenderer(DefaultListModel<String> model, Map<String, Double> studentCgpaMap) {
        this.model = model;
        this.studentCgpaMap = studentCgpaMap;
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        String studentName = model.getElementAt(index);
        Double cgpa = studentCgpaMap.get(studentName);
        if (cgpa != null && cgpa < 1.7) {
            c.setForeground(Color.RED);
        } else {
            c.setForeground(Color.BLACK);
        }
        return c;
    }
}
    private Map<String, Double> studentCgpaMap = new HashMap<>();

    private String getStudentsData() {
        StringBuilder studentdata = new StringBuilder();
        studentdata.append("Student Academic Record\n");
        studentdata.append("\n");
        studentdata.append("Student 1 - ID: 12345678\n");
        studentdata.append("\n");
        studentdata.append("Course - Grade: \n");
        studentdata.append(String.format("COMPUTER ARCHITECTURE - A") + "\n");
        studentdata.append(String.format("COMPUTER NETWORKING - B") + "\n");
        studentdata.append(String.format("DIGITAL SIGNAL PROCESSING - C") + "\n");
        studentdata.append(String.format("ADVANCED COMPUTER DESIGN - D") + "\n");
        studentdata.append(String.format("OPERATING SYSTEMS - A") + "\n");
        studentdata.append(String.format("MOBILE APPLICATION PROGRAMMING - B") + "\n");
        studentdata.append(String.format("ROUTING & SWITCHING TECH - C") + "\n");
        studentdata.append(String.format("MULTIMEDIA TECHNOLOGIES - D") + "\n");
        studentdata.append(String.format("COMMUNICATION SYSTEM - A") + "\n");
        studentdata.append(String.format("SOFTWARE ENGINEERING - B") + "\n");
        studentdata.append(String.format("QUALITY MANAGEMENT FOR SCIENCE AND TECHNOLOGY - C") + "\n");

        studentdata.append("\n");
        studentdata.append("cGPA : 2.64\n");
        return studentdata.toString();
    }

    private boolean validateInput() {
        String sex = txtSex.getText();
        String birthday = txtBirthday.getText();
        String email = txtEmail.getText();
        String phoneNumber = txtPhoneNumber.getText();
    
        if (sex.isEmpty() || birthday.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            JOptionPane.showMessageDialog(HomePageUI3.this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!sex.equalsIgnoreCase("Male") && !sex.equalsIgnoreCase("Female")) {
            JOptionPane.showMessageDialog(HomePageUI3.this, "Invalid sex. Please enter 'Male' or 'Female'.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isValidDate(birthday)) {
            JOptionPane.showMessageDialog(HomePageUI3.this, "Invalid birthday. Please enter a valid date in YYYY-MM-DD format.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(HomePageUI3.this, "Invalid email address. Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        if (!isValidPhoneNumber(phoneNumber)) {
            JOptionPane.showMessageDialog(HomePageUI3.this, "Invalid phone number. Please enter a valid 8-digit phone number.", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void updateGradesTable(String studentName) {
        
        if (studentName.equals("Student 1")) {
            gradesData = new String[][]{
                {"COMPUTER ARCHITECTURE", "A+"},
                {"COMPUTER NETWORKING", "B+"},
                {"DIGITAL SIGNAL PROCESSING", "C+"},
                {"ADVANCED COMPUTER DESIGN", "D+"}
            };
        } else if (studentName.equals("Student 2")) {
            gradesData = new String[][]{
                {"COMPUTER ARCHITECTURE", "D"},
                {"COMPUTER NETWORKING", "C-"},
                {"DIGITAL SIGNAL PROCESSING", "B-"},
                {"ADVANCED COMPUTER DESIGN", "A"}
            };
        } else if (studentName.equals("Student 3")) {
            gradesData = new String[][]{
                {"COMPUTER ARCHITECTURE", "F"},
                {"COMPUTER NETWORKING", "F"},
                {"DIGITAL SIGNAL PROCESSING", "D-"},
                {"ADVANCED COMPUTER DESIGN", "C"}
            };
        } else {
            gradesData = new String[][]{
                {"Subject 1", "A"},
                {"Subject 2", "A"},
                {"Subject 3", "A"},
                {"Subject 4", "A"}
            };
        }
    
        gradesTableModel.setDataVector(gradesData, new String[]{"Subject", "Grade"});
        
    }

    private void calculateCGPA() {
        model = gradesTable.getModel();
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
        String selectedStudent = studentList.getSelectedValue();
    if (selectedStudent != null) {
        studentCgpaMap.put(selectedStudent, cGPA);
    }
    lblCGPA.setText("cGPA: " + String.format("%.2f", cGPA));
}   

    private void openEditDialog(String name, String id, String studentorteacher, String sex, String birthday, String email, String phoneNumber) {
        JDialog editDialog3 = new JDialog(this, "Edit Profile", true);
        editDialog3.setSize(400, 300);
        editDialog3.setLayout(new GridLayout(8, 2));

        JLabel nameLabel = new JLabel("Please Enter Name: ");
        JTextField nameField = new JTextField(name);

        JLabel idLabel = new JLabel("Please Enter ID:");
        JTextField idField = new JTextField(id);

        JLabel subjectLabel = new JLabel("Teacher/Student: ");
        JComboBox<String> subjectComboBox = new JComboBox<>(new String[] {"Teacher", "Student"});
        subjectComboBox.setSelectedItem(studentorteacher);

        JLabel sexLabel = new JLabel("Sex: ");
        JTextField sexField = new JTextField(sex);

        JLabel birthdayLabel = new JLabel("Birthday: ");
        JTextField birthdayField = new JTextField(birthday);

        JLabel emailLabel = new JLabel("Email Address: ");
        JTextField emailField = new JTextField(email);

        JLabel phoneNumberLabel = new JLabel("Phone Number: ");
        JTextField phoneNumberField = new JTextField(phoneNumber);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().isEmpty() || idField.getText().isEmpty() || subjectComboBox.getSelectedItem() == null ||
                        sexField.getText().isEmpty() || birthdayField.getText().isEmpty() || emailField.getText().isEmpty() ||
                        phoneNumberField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(editDialog3, "Please Input Correct Information.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    editprofileTableModel.setValueAt(nameField.getText(), editprofileTable.getSelectedRow(), 0);
                    editprofileTableModel.setValueAt(idField.getText(), editprofileTable.getSelectedRow(), 1);
                    editprofileTableModel.setValueAt(subjectComboBox.getSelectedItem(), editprofileTable.getSelectedRow(), 2);
                    editprofileTableModel.setValueAt(sexField.getText(), editprofileTable.getSelectedRow(), 3);
                    editprofileTableModel.setValueAt(birthdayField.getText(), editprofileTable.getSelectedRow(), 4);
                    editprofileTableModel.setValueAt(emailField.getText(), editprofileTable.getSelectedRow(), 5);
                    editprofileTableModel.setValueAt(phoneNumberField.getText(), editprofileTable.getSelectedRow(), 6);
                    editDialog3.dispose();
                }
            }
        });

        editDialog3.add(nameLabel);
        editDialog3.add(nameField);
        editDialog3.add(idLabel);
        editDialog3.add(idField);
        editDialog3.add(subjectLabel);
        editDialog3.add(subjectComboBox);
        editDialog3.add(sexLabel);
        editDialog3.add(sexField);
        editDialog3.add(birthdayLabel);
        editDialog3.add(birthdayField);
        editDialog3.add(emailLabel);
        editDialog3.add(emailField);
        editDialog3.add(phoneNumberLabel);
        editDialog3.add(phoneNumberField);
        editDialog3.add(new JLabel());
        editDialog3.add(saveButton);
        editDialog3.setLocationRelativeTo(null);
        editDialog3.setVisible(true);
    }

    private void openAddDialog() {
        JDialog addDialog5 = new JDialog(this, "Add New Profile", true);
        addDialog5.setSize(400, 300);
        addDialog5.setLayout(new GridLayout(8, 2));

        JLabel nameLabel = new JLabel("Please Enter Name: ");
        JTextField nameField = new JTextField();

        JLabel idLabel = new JLabel("Please Enter ID: ");
        JTextField idField = new JTextField();

        JLabel subjectLabel = new JLabel("Teacher/Student: ");
        JComboBox<String> subjectComboBox = new JComboBox<>(new String[]{"Teacher", "Student"});

        JLabel sexLabel = new JLabel("Sex: ");
        JTextField sexField = new JTextField();

        JLabel birthdayLabel = new JLabel("Birthday: ");
        JTextField birthdayField = new JTextField();

        JLabel emailLabel = new JLabel("Email Address: ");
        JTextField emailField = new JTextField();

        JLabel phoneNumberLabel = new JLabel("Phone Number: ");
        JTextField phoneNumberField = new JTextField();

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().isEmpty() || idField.getText().isEmpty() || subjectComboBox.getSelectedItem() == null ||
                        sexField.getText().isEmpty() || birthdayField.getText().isEmpty() || emailField.getText().isEmpty() ||
                        phoneNumberField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(addDialog5, "Please Input Correct Information.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] rowData = {nameField.getText(), idField.getText(), subjectComboBox.getSelectedItem().toString(),
                            sexField.getText(), birthdayField.getText(), emailField.getText(), phoneNumberField.getText()};
                    editprofileTableModel.addRow(rowData);
                    addDialog5.dispose();
                }
            }
        });

        addDialog5.add(nameLabel);
        addDialog5.add(nameField);
        addDialog5.add(idLabel);
        addDialog5.add(idField);
        addDialog5.add(subjectLabel);
        addDialog5.add(subjectComboBox);
        addDialog5.add(sexLabel);
        addDialog5.add(sexField);
        addDialog5.add(birthdayLabel);
        addDialog5.add(birthdayField);
        addDialog5.add(emailLabel);
        addDialog5.add(emailField);
        addDialog5.add(phoneNumberLabel);
        addDialog5.add(phoneNumberField);
        addDialog5.add(new JLabel());
        addDialog5.add(saveButton);
        addDialog5.setLocationRelativeTo(null);
        addDialog5.setVisible(true);
    }

    private class DepartmentComboBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selectedDepartment = (String) departmentComboBox.getSelectedItem();
            if (selectedDepartment.equals("Development and Alumni Affairs Office")) {
                contactInfoTextArea.setText("Departments: Development and Alumni Affairs Office\nTel: (852) 2768 6366\nEmail: development@hkmu.edu.hk");
            } else if (selectedDepartment.equals("Information Technology Office")) {
                contactInfoTextArea.setText("Departments: Information Technology Office\nTel: (852) 2711 2100\nEmail: itohelp@hkmu.edu.hk");
            } else if (selectedDepartment.equals("Office for Advancement of Learning and Teaching")) {
                contactInfoTextArea.setText("Departments: Office for Advancement of Learning and Teaching\nTel: (852) 2768 6454\nEmail: olemaster@hkmu.edu.hk");
            }
        }
    }

    private class ContactButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selectedDepartment = (String) departmentComboBox.getSelectedItem();
            if (selectedDepartment.equals("Development and Alumni Affairs Office")) {
                JOptionPane.showMessageDialog(HomePageUI3.this, "Calling to Development and Alumni Affairs Office");
            } else if (selectedDepartment.equals("Information Technology Office")) {
                JOptionPane.showMessageDialog(HomePageUI3.this, "Calling to Information Technology Office");
            } else if (selectedDepartment.equals("Office for Advancement of Learning and Teaching")) {
                JOptionPane.showMessageDialog(HomePageUI3.this, "Calling to Office for Advancement of Learning and Teaching");
            }
        }
    }

    public HomePageUI3(String username) {
        setTitle("Home Page");
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

        lblWelcome = new JLabel("Welcome, " + username);
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblWelcome, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        profilePanel = new JPanel();
        profilePanel.setLayout(new GridLayout(8, 2));

        JLabel lblStudentName = new JLabel("Admin Staff Name:");
        txtStudentName = new JTextField("admin");
        txtStudentName.setEditable(false);
        profilePanel.add(lblStudentName);
        profilePanel.add(txtStudentName);

        JLabel lblStudentID = new JLabel("Admin Staff ID:");
        txtStudentID = new JTextField("001");
        txtStudentID.setEditable(false);
        profilePanel.add(lblStudentID);
        profilePanel.add(txtStudentID);

        JLabel lbladminpassword = new JLabel("Admin Password:");
        txtadminpassword = new JTextField("123456");
        txtadminpassword.setEditable(false);
        profilePanel.add(lbladminpassword);
        profilePanel.add(txtadminpassword);

        JLabel lblSex = new JLabel("Sex:");
        txtSex = new JTextField("Male");
        txtSex.setEditable(false);
        profilePanel.add(lblSex);
        profilePanel.add(txtSex);

        JLabel lblBirthday = new JLabel("Birthday:");
        txtBirthday = new JTextField("1975-01-01");
        txtBirthday.setEditable(false);
        profilePanel.add(lblBirthday);
        profilePanel.add(txtBirthday);

        JLabel lblEmail = new JLabel("Email Address:");
        txtEmail = new JTextField("admin@live.hkmu.edu.hk");
        txtEmail.setEditable(false);
        profilePanel.add(lblEmail);
        profilePanel.add(txtEmail);

        JLabel lblPhoneNumber = new JLabel("Phone Number:");
        txtPhoneNumber = new JTextField("66666666");
        txtPhoneNumber.setEditable(false);
        profilePanel.add(lblPhoneNumber);
        profilePanel.add(txtPhoneNumber);

        btnModify = new JButton("Modify");
        btnModify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtadminpassword.setEditable(true);
                txtSex.setEditable(true);
                txtBirthday.setEditable(true);
                txtEmail.setEditable(true);
                txtPhoneNumber.setEditable(true);
            }
        });
        profilePanel.add(btnModify);

        btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                txtStudentName.setEditable(false);
                txtStudentID.setEditable(false);
                txtadminpassword.setEditable(false);
                txtSex.setEditable(false);
                txtBirthday.setEditable(false);
                txtEmail.setEditable(false);
                txtPhoneNumber.setEditable(false);
                } 
            }
        });
        profilePanel.add(btnSave);

        gradesPanel = new JPanel();
        gradesPanel.setLayout(new BorderLayout());

        studentListModel = new DefaultListModel<>();
        studentListModel.addElement("Student 1");
        studentListModel.addElement("Student 2");
        studentListModel.addElement("Student 3");
        studentListModel.addElement("Student 4");
        studentListModel.addElement("Student 5");
        studentList = new JList<>(studentListModel);
        studentList.setCellRenderer(new MyListCellRenderer(studentListModel, studentCgpaMap));
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                String selectedStudent = studentList.getSelectedValue();
                if (selectedStudent != null) {
                    updateGradesTable(selectedStudent);
                    calculateCGPA();
                }
            }
        });

        gradesTableModel = new DefaultTableModel(gradesData, new String[]{"Subject", "Grade"});
        gradesTable = new JTable(gradesTableModel);

        JPanel cgpaPanel = new JPanel();
        lblCGPA = new JLabel("cGPA: ");
        cgpaPanel.add(lblCGPA);
        gradesPanel.add(cgpaPanel, BorderLayout.EAST);
        JButton btnCalculateCGPA = new JButton("Calculate cGPA");
        cgpaPanel.add(btnCalculateCGPA);
        cgpaPanel.setLayout(new BoxLayout(cgpaPanel, BoxLayout.Y_AXIS));
        btnCalculateCGPA.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            calculateCGPA();
        }
        });
        
        btnModifyGrades = new JButton("Modify Grades");
        btnModifyGrades.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = gradesTable.getSelectedRow();
                if (selectedRow != -1) {
                    String newGrade = JOptionPane.showInputDialog(HomePageUI3.this, "Enter new grade:");
                    if (newGrade != null) {
                        gradesTable.setValueAt(newGrade, selectedRow, 1);
                    }
                }
            }
        });

        gradesPanel.add(new JScrollPane(studentList), BorderLayout.WEST);
        gradesTable = new JTable(gradesTableModel);
        tableScrollPane = new JScrollPane(gradesTable);
        gradesPanel.add(tableScrollPane, BorderLayout.CENTER);
        gradesPanel.add(btnModifyGrades, BorderLayout.SOUTH);

        JPanel inboxPanel = new JPanel();
        inboxPanel.setLayout(new BorderLayout());
        
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        studentListModel1 = new DefaultListModel<>();
        studentListModel1.addElement("Student 1");
        studentListModel1.addElement("Student 2");
        studentListModel1.addElement("Student 3");
        studentListModel1.addElement("Student 4");
        studentListModel1.addElement("Student 5");
        studentListModel1.addElement("Teacher 1");
        studentListModel1.addElement("Teacher 2");
        studentListModel1.addElement("Teacher 3");
        studentList1 = new JList<>(studentListModel1);
        leftPanel.add(new JScrollPane(studentList1), BorderLayout.CENTER);
        inboxPanel.add(leftPanel, BorderLayout.WEST);
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        JPanel rightTopPanel = new JPanel();
        rightTopPanel.setLayout(new BorderLayout());
        JTextArea txtQuestion = new JTextArea();
        txtQuestion.setEditable(false);
        txtQuestion.append(" Student 1: Hello admin, Can you help me reset password?\n");
        rightTopPanel.add(new JScrollPane(txtQuestion), BorderLayout.CENTER);
        rightPanel.add(rightTopPanel, BorderLayout.CENTER);
        
        JPanel rightBottomPanel = new JPanel();
        rightBottomPanel.setLayout(new BorderLayout());
        JTextArea txtAnswer = new JTextArea();
        rightBottomPanel.add(new JScrollPane(txtAnswer), BorderLayout.CENTER);
        JButton btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedStudent1 = studentList1.getSelectedValue();
                String question = txtQuestion.getText();
                String answer = txtAnswer.getText();
                
                if (selectedStudent1 != null && !question.isEmpty() && !answer.isEmpty()) {
                    String existingQuestions = studentQuestions.getOrDefault(selectedStudent1, "");
                    String updatedQuestions = existingQuestions + "\n\nQuestion: " + question + "\nAnswer: " + answer;
                    studentQuestions.put(selectedStudent1, updatedQuestions);
                    txtQuestion.setText("");
                    txtAnswer.setText("");
                }
            }
        });
        rightBottomPanel.add(btnSend, BorderLayout.SOUTH);
        rightPanel.add(rightBottomPanel, BorderLayout.SOUTH);

        inboxPanel.add(rightPanel, BorderLayout.CENTER);

        JPanel studyMaterialsPanel = new JPanel();
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

        JPanel addanddelPanel = new JPanel(new BorderLayout());

        JButton btnAddFile = new JButton("Add File");
        btnAddFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) materialsTree.getLastSelectedPathComponent();
                if (selectedNode != null && !selectedNode.isRoot()) {
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(studyMaterialsPanel);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        DefaultMutableTreeNode newFileNode = new DefaultMutableTreeNode(selectedFile.getName());
                        selectedNode.add(newFileNode);
                        ((DefaultTreeModel) materialsTree.getModel()).reload(selectedNode);
                    }
                }
            }
        });
        addanddelPanel.add(btnAddFile, BorderLayout.WEST);

        JButton btnDeleteFile = new JButton("Delete File");
        btnDeleteFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) materialsTree.getLastSelectedPathComponent();
                if (selectedNode != null && !selectedNode.isRoot()) {
                    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
                    if (parentNode != null) {
                        parentNode.remove(selectedNode);
                        ((DefaultTreeModel) materialsTree.getModel()).reload(parentNode);
                    }
                }
            }
        });
        addanddelPanel.add(btnDeleteFile, BorderLayout.EAST);
        studyMaterialsPanel.add(addanddelPanel, BorderLayout.SOUTH);

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

        
        JPanel CourseManagePanel = new JPanel();
        CourseManagePanel.setLayout(new BorderLayout());

        DefaultTableModel coursetableModel = new DefaultTableModel(new Object[]{"Course Name", "Week", "Time"}, 0);

        JTable courseTable = new JTable(coursetableModel);
        JScrollPane coursestableScrollPane = new JScrollPane(courseTable);

        coursetableModel.addRow(new Object[]{"COMP S350F","Monday","8:00 - 9:30"});
        coursetableModel.addRow(new Object[]{"ELEC S304F","Tuesday","9:30 - 11:00"});
        coursetableModel.addRow(new Object[]{"ELEC S411F","Thursday","13:30 - 15:00"});
        coursetableModel.addRow(new Object[]{"TC S319F","Friday","15:00 - 16:30"});

        JButton btnAddCourse = new JButton("Add Course");
        btnAddCourse.addActionListener(e -> {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel lblCourseName = new JLabel("Course Name:");
        JTextField txtCourseName = new JTextField();
        panel.add(lblCourseName);
        panel.add(txtCourseName);

        JLabel lblCourseWeek = new JLabel("Week");
        String[] WeekOptions = {"Monday","Tuesday","Wednesday","Thursday","Friday"};
        JComboBox<String> cmbCourseWeek = new JComboBox<>(WeekOptions);
        panel.add(lblCourseWeek);
        panel.add(cmbCourseWeek);

        JLabel lblCourseTime = new JLabel("Course Time:");
        String[] timeOptions = {"8:00 - 9:30", "9:30 - 11:00", "11:00 - 12:30", "13:30 - 15:00", "15:00 - 16:30"};
        JComboBox<String> cmbCourseTime = new JComboBox<>(timeOptions);
        panel.add(lblCourseTime);
        panel.add(cmbCourseTime);

        int result = JOptionPane.showConfirmDialog(HomePageUI3.this, panel, "Add Course", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String courseName = txtCourseName.getText();
                String courseTime = cmbCourseTime.getSelectedItem().toString();
                String courseWeek = cmbCourseWeek.getSelectedItem().toString();
            if (!courseName.isEmpty() && !courseTime.isEmpty() && !courseWeek.isEmpty()) {
            boolean hasOverlap = false;
            for (int i = 0; i < coursetableModel.getRowCount(); i++) {
                String existingWeek = coursetableModel.getValueAt(i, 1).toString();
                String existingTime = coursetableModel.getValueAt(i, 2).toString();
                if (existingWeek.equals(courseWeek) && existingTime.equals(courseTime)) {
                    hasOverlap = true;
                    break;
                }
            }

            if (hasOverlap) {
                JOptionPane.showMessageDialog(HomePageUI3.this, "There is already a course scheduled at the same time.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                coursetableModel.addRow(new Object[]{courseName, courseWeek, courseTime});
            }
            }
            }
        });

        JButton btnDeleteCourse = new JButton("Delete Course");
        btnDeleteCourse.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow != -1) {
                coursetableModel.removeRow(selectedRow);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAddCourse);
        buttonPanel.add(btnDeleteCourse);

        CourseManagePanel.add(coursestableScrollPane, BorderLayout.CENTER);
        CourseManagePanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel gradingassignmentJPanel = new JPanel();
        gradingassignmentJPanel.setLayout(new BorderLayout());

        JLabel lblgrading = new JLabel("Grading Student Assignment");
        lblgrading.setFont(new Font("Arial", Font.BOLD, 18));
        lblgrading.setHorizontalAlignment(SwingConstants.CENTER);
        gradingassignmentJPanel.add(lblgrading, BorderLayout.NORTH);

        DefaultMutableTreeNode root2 = new DefaultMutableTreeNode("COMP S350F");

        DefaultMutableTreeNode student1Node = new DefaultMutableTreeNode("Student 1");
        student1Node.add(new DefaultMutableTreeNode("assignment 1.docx"));
        student1Node.add(new DefaultMutableTreeNode("Grade: A"));
        student1Node.add(new DefaultMutableTreeNode("assignment 2.docx"));
        student1Node.add(new DefaultMutableTreeNode("Grade: A"));
        root2.add(student1Node);

        DefaultMutableTreeNode student2Node = new DefaultMutableTreeNode("Student 2");
        student2Node.add(new DefaultMutableTreeNode("assignment 1.docx"));
        student2Node.add(new DefaultMutableTreeNode("Grade: A"));
        student2Node.add(new DefaultMutableTreeNode("assignment 2.docx"));
        student2Node.add(new DefaultMutableTreeNode("Grade: A"));
        root2.add(student2Node);

        DefaultMutableTreeNode student3Node = new DefaultMutableTreeNode("Student 3");
        student3Node.add(new DefaultMutableTreeNode("assignment 1.docx"));
        student3Node.add(new DefaultMutableTreeNode("Grade: A"));
        student3Node.add(new DefaultMutableTreeNode("assignment 2.docx"));
        student3Node.add(new DefaultMutableTreeNode("Grade: A"));
        root2.add(student3Node);

        DefaultMutableTreeNode student4Node = new DefaultMutableTreeNode("Student 4");
        student4Node.add(new DefaultMutableTreeNode("assignment 1.docx"));
        student4Node.add(new DefaultMutableTreeNode("Grade: A"));
        student4Node.add(new DefaultMutableTreeNode("assignment 2.docx"));
        student4Node.add(new DefaultMutableTreeNode("Grade: A"));
        root2.add(student4Node);

        JTree materialsTree2 = new JTree(root2);
        materialsTree2.setFont(new Font("Arial", Font.PLAIN, 14));
        materialsTree2.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        JScrollPane treeScrollPane2 = new JScrollPane(materialsTree2);
        gradingassignmentJPanel.add(treeScrollPane2, BorderLayout.CENTER);

        materialsTree2.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
                public void valueChanged(TreeSelectionEvent e) {
                 DefaultMutableTreeNode selectedNode2 = (DefaultMutableTreeNode) materialsTree2.getLastSelectedPathComponent();
                 if (selectedNode2 != null && selectedNode2.getUserObject().equals("Grade: A")) {
                    
                    JComboBox<String> gradeComboBox = new JComboBox<>(new String[]{"A+","A","A-", "B+", "B","B-", "C+","C", "C-","D+","D","D-"});

                    int result = JOptionPane.showConfirmDialog(gradingassignmentJPanel, gradeComboBox, "Enter Grade", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                        String grade = (String) gradeComboBox.getSelectedItem();
                        selectedNode2.setUserObject("Grade: " + grade);
                        materialsTree2.updateUI();
                        }
                    }
                }
        });
        materialsTree2.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode3 = (DefaultMutableTreeNode) materialsTree2.getLastSelectedPathComponent();
                if (selectedNode3 != null && selectedNode3.isLeaf()) {
                    String fileName = (String) selectedNode3.getUserObject();
                    if (fileName.endsWith(".docx")) { 
                        String path2 = ClassLoader.getSystemResource("").getPath();
                        File file2 = new File(path2 + fileName);
                        if (file2.exists()) {
                            try {
                                Desktop.getDesktop().open(file2);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            System.out.println("File not found: " + fileName);
                        }
                    }
                }
            }
        });

        JPanel OnlineClassPanel = new JPanel();
        OnlineClassPanel.setLayout(new BorderLayout());

        JLabel onlineclasstitLabel = new JLabel("Online Class");
        onlineclasstitLabel.setFont(new Font("Arial", Font.BOLD, 18));
        onlineclasstitLabel.setHorizontalAlignment(SwingConstants.CENTER);
        OnlineClassPanel.add(onlineclasstitLabel, BorderLayout.NORTH);

        JLabel courseLabel = new JLabel("Course:");
        JComboBox<String> courseComboBox = new JComboBox<>(new String[]{"COMP S350F", "ELEC S304F", "TC S319F"});
        JPanel coursePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        coursePanel.add(courseLabel);
        coursePanel.add(courseComboBox);
        

        JLabel lessonLabel = new JLabel("Lesson:");
        JComboBox<String> lessonComboBox = new JComboBox<>(new String[]{"Lesson 1", "Lesson 2", "Lesson 3"});
        JPanel lessonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lessonPanel.add(lessonLabel);
        lessonPanel.add(lessonComboBox);
        

        JLabel topicLabel = new JLabel("Topic:");
        JComboBox<String> topicComboBox = new JComboBox<>(new String[]{"Topic 1", "Topic 2", "Topic 3"});
        JPanel topicPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topicPanel.add(topicLabel);
        topicPanel.add(topicComboBox);
        

        JLabel materialsLabel = new JLabel("Materials:");
        JList<String> materialsList = new JList<>(new String[]{"COMP S350F Course Introduction.pdf", "COMP S350F CH1.pdf"});
        JScrollPane materialsScrollPane = new JScrollPane(materialsList);
        JPanel materialsPanel = new JPanel(new BorderLayout());
        materialsPanel.add(materialsLabel, BorderLayout.NORTH);
        materialsPanel.add(materialsScrollPane, BorderLayout.CENTER);

        JButton startClassButton = new JButton("Start Class");
        startClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        String selectedCourse = (String) courseComboBox.getSelectedItem();
        String selectedSession = (String) lessonComboBox.getSelectedItem();
        String selectedTopic = (String) topicComboBox.getSelectedItem();
        String selectedMaterial = materialsList.getSelectedValue();
        
        JOptionPane.showMessageDialog(null, "Start class: " + selectedCourse + " - " + selectedSession + " - " + selectedTopic + "\nSelected material: " + selectedMaterial);
        }
        });
        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel2.add(startClassButton);
        
    

        OnlineClassPanel.add(coursePanel, BorderLayout.WEST);
        OnlineClassPanel.add(lessonPanel, BorderLayout.CENTER);
        OnlineClassPanel.add(topicPanel, BorderLayout.EAST);
        OnlineClassPanel.add(materialsPanel, BorderLayout.SOUTH);
        JPanel materialsAndButtonPanel = new JPanel(new BorderLayout());
        materialsAndButtonPanel.add(materialsPanel, BorderLayout.CENTER);
        materialsAndButtonPanel.add(buttonPanel2, BorderLayout.SOUTH);

        OnlineClassPanel.add(materialsAndButtonPanel, BorderLayout.SOUTH);

        JPanel editprofileJPanel = new JPanel();
        editprofileJPanel.setLayout(new BorderLayout());

        JPanel searchPanel5 = new JPanel();
        searchPanel5.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JLabel searchLabel = new JLabel("Search ID: ");
        JTextField searchTextField = new JTextField(10);

        JButton searchButton5 = new JButton("Search");
        searchButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchID = searchTextField.getText();
                if (searchID.isEmpty()) {
                    JOptionPane.showMessageDialog(HomePageUI3.this, "Please enter an ID to search.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    boolean found = false;
                    for (int i = 0; i < editprofileTableModel.getRowCount(); i++) {
                        String id = (String) editprofileTableModel.getValueAt(i, 1);
                        if (id.equals(searchID)) {
                            editprofileTable.setRowSelectionInterval(i, i);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(HomePageUI3.this, "ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        searchPanel5.add(searchLabel);
        searchPanel5.add(searchTextField);
        searchPanel5.add(searchButton5);

        editprofileJPanel.add(searchPanel5, BorderLayout.NORTH);



        editprofileTableModel = new DefaultTableModel();
        editprofileTableModel.addColumn("Name");
        editprofileTableModel.addColumn("ID");
        editprofileTableModel.addColumn("Student/Teacher");
        editprofileTableModel.addColumn("Sex");
        editprofileTableModel.addColumn("Birthday");
        editprofileTableModel.addColumn("Email Address");
        editprofileTableModel.addColumn("Phone Number");

        editprofileTableModel.addRow(new Object[]{"Student 1", "12345678", "Student","Male","2002-01-01","s1234567@live.hkmu.edu.hk","88888888"});
        editprofileTableModel.addRow(new Object[]{"Student 2", "12345688", "Student","Male","2002-02-02","s1234568@live.hkmu.edu.hk","11111111"});
        editprofileTableModel.addRow(new Object[]{"Student 3", "12345698", "Student","Male","2002-03-03","s1234569@live.hkmu.edu.hk","22222222"});
        editprofileTableModel.addRow(new Object[]{"Teacher 1", "87654321", "Teacher","Male","1997-01-01","t8765432@live.hkmu.edu.hk","99999999"});
        editprofileTableModel.addRow(new Object[]{"Teacher 2", "88654321", "Teacher","Male","1997-01-01","t8865432@live.hkmu.edu.hk","77777777"});
        editprofileTableModel.addRow(new Object[]{"Teacher 3", "89654321", "Teacher","Male","1997-01-01","t8965432@live.hkmu.edu.hk","66666666"});

        editprofileTable = new JTable(editprofileTableModel);
        JScrollPane scrollPane5 = new JScrollPane(editprofileTable);

        JButton editButton3 = new JButton("Edit");
        editButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow3 = editprofileTable.getSelectedRow();
                if (selectedRow3 != -1) {
                    String name = (String) editprofileTableModel.getValueAt(selectedRow3, 0);
                    String id = (String) editprofileTableModel.getValueAt(selectedRow3, 1);
                    String subject = (String) editprofileTableModel.getValueAt(selectedRow3, 2);
                    String sex = (String) editprofileTableModel.getValueAt(selectedRow3, 3);
                    String birthday = (String) editprofileTableModel.getValueAt(selectedRow3, 4);
                    String email = (String) editprofileTableModel.getValueAt(selectedRow3, 5);
                    String phoneNumber = (String) editprofileTableModel.getValueAt(selectedRow3, 6);
                    openEditDialog(name, id, subject, sex, birthday, email, phoneNumber);
                } else {
                    JOptionPane.showMessageDialog(HomePageUI3.this, "Please choose one record to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton addButton3 = new JButton("Add");
        addButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddDialog();
            }
        });

        JButton deleteButton3 = new JButton("Delete");
        deleteButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = editprofileTable.getSelectedRow();
                if (selectedRow != -1) {
                editprofileTableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(HomePageUI3.this, "Please choose one record to del.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

        JPanel buttonPanel5 = new JPanel();
        buttonPanel5.add(editButton3);
        buttonPanel5.add(addButton3);
        buttonPanel5.add(deleteButton3);

        editprofileJPanel.add(scrollPane5, BorderLayout.CENTER);
        editprofileJPanel.add(buttonPanel5, BorderLayout.SOUTH);

        JPanel CurriculumPlanningPanel = new JPanel();
        CurriculumPlanningPanel.setLayout(new BorderLayout());

        JLabel CurriculumPlanningtit = new JLabel("Curriculum Planning");
        CurriculumPlanningtit.setFont(new Font("Arial", Font.BOLD, 24));
        CurriculumPlanningtit.setHorizontalAlignment(JLabel.CENTER);
        CurriculumPlanningPanel.add(CurriculumPlanningtit, BorderLayout.NORTH);

        JTable curriculumTable = new JTable();

        DefaultTableModel tableModel6 = new DefaultTableModel();
        tableModel6.addColumn("Courses Name");
        tableModel6.addColumn("Courses Number");
        tableModel6.addColumn("Credit");

        tableModel6.addRow(new Object[]{"Software Engineering", "COMP S350F", 5});
        tableModel6.addRow(new Object[]{"Communication Systems", "ELEC S304F", 5});
        tableModel6.addRow(new Object[]{"Electronic And Computer Engineering Project", "ELEC S411F", 5});
        tableModel6.addRow(new Object[]{"Quality Management For Science And Technology", "TC S319F", 5});

        curriculumTable.setModel(tableModel6);


        JScrollPane tableScrollPane = new JScrollPane(curriculumTable);
        CurriculumPlanningPanel.add(tableScrollPane, BorderLayout.CENTER);

        JButton addButton6 = new JButton("Add");
        JButton editButton6 = new JButton("Edit");
        JButton deleteButton6 = new JButton("Delete");
        JPanel buttonPanel6 = new JPanel();
        buttonPanel6.add(editButton6);
        buttonPanel6.add(addButton6);
        buttonPanel6.add(deleteButton6);
        CurriculumPlanningPanel.add(buttonPanel6, BorderLayout.SOUTH);

        JPanel AdminLiaisonPanel = new JPanel();
        AdminLiaisonPanel.setLayout(new BorderLayout());

        JLabel AdminLiaisontitleLabel = new JLabel("Admin Liaison");
        AdminLiaisontitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        AdminLiaisontitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        AdminLiaisonPanel.add(AdminLiaisontitleLabel, BorderLayout.NORTH);

        departmentComboBox = new JComboBox<>();
        departmentComboBox.addItem("Development and Alumni Affairs Office");
        departmentComboBox.addItem("Information Technology Office");
        departmentComboBox.addItem("Office for Advancement of Learning and Teaching");
        departmentComboBox.addActionListener(new DepartmentComboBoxListener());
        AdminLiaisonPanel.add(departmentComboBox,BorderLayout.NORTH);

        contactInfoTextArea = new JTextArea();
        contactInfoTextArea.setEditable(false);
        JScrollPane contactInfoScrollPane = new JScrollPane(contactInfoTextArea);
        AdminLiaisonPanel.add(contactInfoScrollPane, BorderLayout.CENTER);

        JButton contactButton9 = new JButton("Make a telephone Call");
        contactButton9.addActionListener(new ContactButtonListener());
        AdminLiaisonPanel.add(contactButton9, BorderLayout.SOUTH);

        JPanel ReportPanel = new JPanel();
        ReportPanel.setLayout(new BorderLayout());

        JLabel generatereporttitLabel = new JLabel("Generate Report");
        generatereporttitLabel.setFont(new Font("Arial", Font.BOLD, 18));
        generatereporttitLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ReportPanel.add(generatereporttitLabel, BorderLayout.NORTH);

        JLabel lblstudentnamereport = new JLabel("Student Name:");
        String[] whichstudent = {"Student 1", "Student 2","Student 3"};
        JComboBox<String> whichstudentComboBox = new JComboBox<>(whichstudent);
        JPanel genreportPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        genreportPanel.add(lblstudentnamereport);
        genreportPanel.add(whichstudentComboBox);

        JButton generatereportbButton = new JButton("Generate Report");
        generatereportbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String studentsData = getStudentsData();
                    
                JTextArea textArea = new JTextArea(studentsData);
                textArea.setLineWrap(true);
                textArea.setEditable(false);
                JScrollPane scrollPane9 = new JScrollPane(textArea);
                scrollPane9.setPreferredSize(new Dimension(500, 500));
        
                JButton saveButton9 = new JButton("Save as PDF");
                saveButton9.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            PDDocument document = new PDDocument();
                            PDPage page = new PDPage(PDRectangle.A4);
                            document.addPage(page);
        
                            PDPageContentStream contentStream = new PDPageContentStream(document, page);
                            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                            contentStream.beginText();
                            contentStream.newLineAtOffset(50, 700);

                            String[] lines = studentsData.split("\n");
                            
                            for (String line : lines) {
                                contentStream.newLineAtOffset(0, -15);
                                contentStream.showText(line);
                            }
                            contentStream.endText();
                            contentStream.close();
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setDialogTitle("Save as PDF");
                            int userSelection = fileChooser.showSaveDialog(null);
                            if (userSelection == JFileChooser.APPROVE_OPTION) {
                                File fileToSave = fileChooser.getSelectedFile();
                                if (!fileToSave.getAbsolutePath().endsWith(".pdf")) {
                                    fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
                                }
                                document.save(fileToSave);
                                document.close();
                                JOptionPane.showMessageDialog(null, "Report saved as PDF.");
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error saving report as PDF: " + ex.getMessage());
                        }
                    }
                });
                JPanel panel0 = new JPanel(new BorderLayout());
                panel0.add(scrollPane9, BorderLayout.CENTER);
                panel0.add(saveButton9, BorderLayout.SOUTH);

                JFrame frame = new JFrame("Student Report");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(panel0);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
            
        });
        
        JPanel allgeneratereportPanel = new JPanel(new BorderLayout());
        allgeneratereportPanel.add(genreportPanel,BorderLayout.NORTH);
        allgeneratereportPanel.add(generatereportbButton,BorderLayout.SOUTH);

        ReportPanel.add(allgeneratereportPanel,BorderLayout.CENTER);

        tabbedPane.addTab("Profile", profilePanel);
        tabbedPane.addTab("Edit Profile", editprofileJPanel);
        tabbedPane.addTab("Grading System", gradesPanel);
        tabbedPane.addTab("Inbox", inboxPanel);
        tabbedPane.addTab("Curriculum Planning", CurriculumPlanningPanel);
        tabbedPane.addTab("Study Materials", studyMaterialsPanel);
        tabbedPane.addTab("Course Manage", CourseManagePanel);
        tabbedPane.addTab("Grading Assignment", gradingassignmentJPanel);
        tabbedPane.addTab("Online Class", OnlineClassPanel);
        tabbedPane.addTab("Liaison", AdminLiaisonPanel);
        tabbedPane.addTab("Generate Report", ReportPanel);
        add(tabbedPane, BorderLayout.CENTER);

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(HomePageUI3.this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
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