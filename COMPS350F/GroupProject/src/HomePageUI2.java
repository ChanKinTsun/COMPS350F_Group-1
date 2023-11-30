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
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.image.BufferedImage;
public class HomePageUI2 extends JFrame {
    private JLabel lblWelcome;
    private JTabbedPane tabbedPane;
    private JPanel profilePanel;
    private JPanel gradesPanel;

     
    private JTextField txtStudentName;
    private JTextField txtStudentID;
    private JTextField txtteacherpassword;
    private JTextField txtSex;
    private JTextField txtBirthday;
    private JTextField txtEmail;
    private JTextField txtPhoneNumber;
    private JButton btnModify;
    private JButton btnSave;
    

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

    private boolean validateInput() {
        String sex = txtSex.getText();
        String birthday = txtBirthday.getText();
        String email = txtEmail.getText();
        String phoneNumber = txtPhoneNumber.getText();
    
        if (sex.isEmpty() || birthday.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            JOptionPane.showMessageDialog(HomePageUI2.this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
         
        if (!sex.equalsIgnoreCase("Male") && !sex.equalsIgnoreCase("Female")) {
            JOptionPane.showMessageDialog(HomePageUI2.this, "Invalid sex. Please enter 'Male' or 'Female'.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
         
        if (!isValidDate(birthday)) {
            JOptionPane.showMessageDialog(HomePageUI2.this, "Invalid birthday. Please enter a valid date in YYYY-MM-DD format.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
         
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(HomePageUI2.this, "Invalid email address. Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
         
        if (!isValidPhoneNumber(phoneNumber)) {
            JOptionPane.showMessageDialog(HomePageUI2.this, "Invalid phone number. Please enter a valid 8-digit phone number.", "Error", JOptionPane.ERROR_MESSAGE);
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

    public HomePageUI2(String username) {
         
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

         
        JLabel lblStudentName = new JLabel("Teacher Name:");
        txtStudentName = new JTextField("Teacher 1");
        txtStudentName.setEditable(false);  
        profilePanel.add(lblStudentName);
        profilePanel.add(txtStudentName);

        JLabel lblStudentID = new JLabel("Teacher ID:");
        txtStudentID = new JTextField("87654321");
        txtStudentID.setEditable(false);  
        profilePanel.add(lblStudentID);
        profilePanel.add(txtStudentID);
        
        JLabel lblteacherpassword = new JLabel("Teacher Password:");
        txtteacherpassword = new JTextField("123456");
        txtteacherpassword.setEditable(false);
        profilePanel.add(lblteacherpassword);
        profilePanel.add(txtteacherpassword);

        JLabel lblSex = new JLabel("Sex:");
        txtSex = new JTextField("Male");
        txtSex.setEditable(false);  
        profilePanel.add(lblSex);
        profilePanel.add(txtSex);

        JLabel lblBirthday = new JLabel("Birthday:");
        txtBirthday = new JTextField("1997-01-01");
        txtBirthday.setEditable(false);  
        profilePanel.add(lblBirthday);
        profilePanel.add(txtBirthday);

        JLabel lblEmail = new JLabel("Email Address:");
        txtEmail = new JTextField("t8765432@live.hkmu.edu.hk");
        txtEmail.setEditable(false);  
        profilePanel.add(lblEmail);
        profilePanel.add(txtEmail);

        JLabel lblPhoneNumber = new JLabel("Phone Number:");
        txtPhoneNumber = new JTextField("99999999");
        txtPhoneNumber.setEditable(false);  
        profilePanel.add(lblPhoneNumber);
        profilePanel.add(txtPhoneNumber);

         
        btnModify = new JButton("Modify");
        btnModify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 
                txtteacherpassword.setEditable(true);
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
                txtteacherpassword.setEditable(false);
                txtStudentName.setEditable(false);
                txtStudentID.setEditable(false);
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
                    String newGrade = JOptionPane.showInputDialog(HomePageUI2.this, "Enter new grade:");
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
        studentList1 = new JList<>(studentListModel1);
        leftPanel.add(new JScrollPane(studentList1), BorderLayout.CENTER);
        inboxPanel.add(leftPanel, BorderLayout.WEST);
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        JPanel rightTopPanel = new JPanel();
        rightTopPanel.setLayout(new BorderLayout());
        JTextArea txtQuestion = new JTextArea();
        txtQuestion.setEditable(false);
        txtQuestion.append(" Student 1: Hello teacher, May I ask about the assignment Question 1a?\n");
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
studyMaterialsPanel.add(btnAddFile, BorderLayout.SOUTH);

 
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

        int result = JOptionPane.showConfirmDialog(HomePageUI2.this, panel, "Add Course", JOptionPane.OK_CANCEL_OPTION);
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
                JOptionPane.showMessageDialog(HomePageUI2.this, "There is already a course scheduled at the same time.", "Error", JOptionPane.ERROR_MESSAGE);
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
        student1Node.add(new DefaultMutableTreeNode("Grade:"));
        student1Node.add(new DefaultMutableTreeNode("assignment 2.docx"));
        student1Node.add(new DefaultMutableTreeNode("Grade:"));
        root2.add(student1Node);

        DefaultMutableTreeNode student2Node = new DefaultMutableTreeNode("Student 2");
        student2Node.add(new DefaultMutableTreeNode("assignment 1.docx"));
        student2Node.add(new DefaultMutableTreeNode("Grade:"));
        student2Node.add(new DefaultMutableTreeNode("assignment 2.docx"));
        student2Node.add(new DefaultMutableTreeNode("Grade:"));
        root2.add(student2Node);

        DefaultMutableTreeNode student3Node = new DefaultMutableTreeNode("Student 3");
        student3Node.add(new DefaultMutableTreeNode("assignment 1.docx"));
        student3Node.add(new DefaultMutableTreeNode("Grade:"));
        student3Node.add(new DefaultMutableTreeNode("assignment 2.docx"));
        student3Node.add(new DefaultMutableTreeNode("Grade:"));
        root2.add(student3Node);

        DefaultMutableTreeNode student4Node = new DefaultMutableTreeNode("Student 4");
        student4Node.add(new DefaultMutableTreeNode("assignment 1.docx"));
        student4Node.add(new DefaultMutableTreeNode("Grade:"));
        student4Node.add(new DefaultMutableTreeNode("assignment 2.docx"));
        student4Node.add(new DefaultMutableTreeNode("Grade:"));
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
                 if (selectedNode2 != null && selectedNode2.getUserObject().equals("Grade:")) {
                     
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

         
        tabbedPane.addTab("Profile", profilePanel);
        tabbedPane.addTab("Grading System", gradesPanel);
        tabbedPane.addTab("Student Inbox", inboxPanel);
        tabbedPane.addTab("Study Materials", studyMaterialsPanel);
        tabbedPane.addTab("Course Manage", CourseManagePanel);
        tabbedPane.addTab("Grading Assignment", gradingassignmentJPanel);
        tabbedPane.addTab("Online Class", OnlineClassPanel);
        add(tabbedPane, BorderLayout.CENTER);

         
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(HomePageUI2.this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
                 
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