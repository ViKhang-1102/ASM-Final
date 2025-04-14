package com.btec.assignmentFinal.khanghv;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.IOException;
import java.awt.Font;

public class Main extends javax.swing.JFrame {

    private StudentController controller = new StudentController();
    private DefaultTableModel tableModel; 

    public Main() {
        String[] columnNames = {"ID", "Name", "Course", "Gender", "Class", "Major",
            "Subject1", "Score1", "Subject2", "Score2", "Subject3", "Score3", "Average", "Grade"};
        tableModel = new DefaultTableModel(columnNames, 0);
        initComponents(); 

        try {
            controller.loadFromFile("students.txt");
            refreshStudentList(controller.getAllStudents()); 
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        initCustomComponents(); 
    }

    private void initCustomComponents() {
        txtID.setEnabled(false); 
        updateSubjects(); 

        tblTable.setRowHeight(20); 
        tblTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
        tblTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblTable.getColumnModel().getColumn(1).setPreferredWidth(160);
        tblTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblTable.getColumnModel().getColumn(3).setPreferredWidth(65);
        tblTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        tblTable.getColumnModel().getColumn(5).setPreferredWidth(50);
        tblTable.getColumnModel().getColumn(6).setPreferredWidth(80);
        tblTable.getColumnModel().getColumn(7).setPreferredWidth(50);
        tblTable.getColumnModel().getColumn(8).setPreferredWidth(80);
        tblTable.getColumnModel().getColumn(9).setPreferredWidth(50);
        tblTable.getColumnModel().getColumn(10).setPreferredWidth(80);
        tblTable.getColumnModel().getColumn(11).setPreferredWidth(50);
        tblTable.getColumnModel().getColumn(12).setPreferredWidth(62);
        tblTable.getColumnModel().getColumn(13).setPreferredWidth(80);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblTable.setDefaultRenderer(Object.class, centerRenderer);
        tblTable.setFont(new Font("Arial", Font.PLAIN, 14)); 

        cboMajor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateSubjects(); 
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtName.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Name cannot be empty");
                    }
                    if (txtClass.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Class cannot be empty");
                    }
                    if (txtSubject1.getText().trim().isEmpty() || txtSubject2.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Subject 1 and Subject 2 cannot be empty");
                    }
                    if (txtScore1.getText().trim().isEmpty() || txtScore2.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Score 1 and Score 2 cannot be empty");
                    }
                    if (txtScore3.isVisible() && txtScore3.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Score 3 cannot be empty when visible");
                    }

                    double score1 = Double.parseDouble(txtScore1.getText().trim());
                    double score2 = Double.parseDouble(txtScore2.getText().trim());
                    double score3 = txtScore3.isVisible() ? Double.parseDouble(txtScore3.getText().trim()) : 0;

                    if (score1 < 0 || score1 > 10 || score2 < 0 || score2 > 10 || score3 < 0 || score3 > 10) {
                        throw new IllegalArgumentException("Scores must be between 0 and 10");
                    }

                    String name = txtName.getText().trim();
                    String course = (String) cboCourse.getSelectedItem();
                    String gender = (String) cboGender.getSelectedItem();
                    String className = txtClass.getText().trim();
                    String major = (String) cboMajor.getSelectedItem();
                    String sub1 = txtSubject1.getText().trim();
                    String sub2 = txtSubject2.getText().trim();
                    String sub3 = txtSubject3.getText().trim();

                    controller.addStudent(name, course, gender, className, major, sub1, sub2, sub3, score1, score2, score3);
                    refreshStudentList(controller.getAllStudents());
                    clearFields(); 
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid number format. Please enter valid numbers for scores.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = tblTable.getSelectedRow();
                if (index == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a student to update.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    if (txtName.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Name cannot be empty");
                    }
                    if (txtClass.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Class cannot be empty");
                    }
                    if (txtSubject1.getText().trim().isEmpty() || txtSubject2.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Subjects cannot be empty");
                    }
                    if (txtScore1.getText().trim().isEmpty() || txtScore2.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Score 1 and Score 2 cannot be empty");
                    }
                    if (txtScore3.isVisible() && txtScore3.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Score 3 cannot be empty when visible");
                    }

                    double score1 = Double.parseDouble(txtScore1.getText().trim());
                    double score2 = Double.parseDouble(txtScore2.getText().trim());
                    double score3 = txtScore3.isVisible() ? Double.parseDouble(txtScore3.getText().trim()) : 0;

                    if (score1 < 0 || score1 > 10 || score2 < 0 || score2 > 10 || score3 < 0 || score3 > 10) {
                        throw new IllegalArgumentException("Scores must be between 0 and 10");
                    }

                    String id = controller.getStudent(index).getId();
                    String name = txtName.getText().trim();
                    String course = (String) cboCourse.getSelectedItem();
                    String gender = (String) cboGender.getSelectedItem();
                    String className = txtClass.getText().trim();
                    String major = (String) cboMajor.getSelectedItem();
                    String sub1 = txtSubject1.getText().trim();
                    String sub2 = txtSubject2.getText().trim();
                    String sub3 = txtSubject3.getText().trim();

                    Student updatedStudent = new Student(id, name, course, gender, className, major, sub1, sub2, sub3, score1, score2, score3);
                    controller.updateStudent(index, updatedStudent); 
                    refreshStudentList(controller.getAllStudents()); 
                    clearFields();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid number format. Please enter valid numbers for scores.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = tblTable.getSelectedRow();
                if (index == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a student to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this student?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.deleteStudent(index); 
                    refreshStudentList(controller.getAllStudents()); 
                    clearFields(); 
                }
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = txtSearch.getText().trim().toLowerCase();
                if (query.isEmpty()) {
                    refreshStudentList(controller.getAllStudents());
                } else {
                    ArrayList<Student> results = controller.searchStudents(query);
                    if (results.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No students found matching: " + query, "Search Result", JOptionPane.INFORMATION_MESSAGE);
                    }
                    refreshStudentList(results); 
                }
            }
        });

        btnFilterSort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (controller.getAllStudents().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No students to filter/sort!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String option = (String) cboFilterSort.getSelectedItem();
                if (option.startsWith("Grade: ")) {
                    String grade = option.replace("Grade: ", "");
                    ArrayList<Student> filteredStudents = controller.filterByGrade(grade);
                    if (filteredStudents.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No students found with grade: " + grade, "Filter Result", JOptionPane.INFORMATION_MESSAGE);
                    }
                    refreshStudentList(filteredStudents); 
                } else {
                    String criterion;
                    boolean ascending;
                    if (option.equals("Name (Asc)")) {
                        criterion = "Name";
                        ascending = true;
                    } else if (option.equals("Name (Desc)")) {
                        criterion = "Name";
                        ascending = false;
                    } else if (option.equals("AverageScore (Asc)")) {
                        criterion = "AverageScore";
                        ascending = true;
                    } else {
                        criterion = "AverageScore";
                        ascending = false;
                    }
                    controller.sortStudents(criterion, ascending); 
                    refreshStudentList(controller.getAllStudents()); 
                }
            }
        });

        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields(); 
                txtSearch.setText("");
                controller.sortStudents("ID", true); 
                refreshStudentList(controller.getAllStudents()); 
                cboFilterSort.setSelectedIndex(0); 
            }
        });

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (controller.getAllStudents().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No students to save!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    controller.saveToFile("students.txt");
                    JOptionPane.showMessageDialog(null, "Students saved to students.txt successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        tblTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int index = tblTable.getSelectedRow();
                    if (index >= 0) {
                        Student student = controller.getStudent(index);
                        txtID.setText(student.getId());
                        txtName.setText(student.getName());
                        cboCourse.setSelectedItem(student.getCourse());
                        cboGender.setSelectedItem(student.getGender());
                        txtClass.setText(student.getClassName());
                        cboMajor.setSelectedItem(student.getMajor());
                        txtSubject1.setText(student.getSubject1());
                        txtSubject2.setText(student.getSubject2());
                        txtSubject3.setText(student.getSubject3());
                        txtScore1.setText(String.valueOf(student.getScore1()));
                        txtScore2.setText(String.valueOf(student.getScore2()));
                        txtScore3.setText(String.valueOf(student.getScore3()));
                        updateSubjects(); 
                    }
                }
            }
        });
    }

    private void updateSubjects() {
        String major = (String) cboMajor.getSelectedItem();
        switch (major) {
            case "IT":
                setSubjects("HTML", "CSS", "Math", true); 
                break;
            case "Biz":
                setSubjects("Marketing", "Sale", "", false);
                break;
            case "GD":
                setSubjects("Color", "PTS", "AI", true); 
                break;
        }
    }

    private void setSubjects(String s1, String s2, String s3, boolean showThird) {
        txtSubject1.setText(s1);
        txtSubject2.setText(s2);
        txtSubject3.setText(s3);
        txtSubject3.setVisible(showThird); 
        txtScore3.setVisible(showThird);
        if (!showThird) {
            txtScore3.setText("0"); 
        }
    }

    private void clearFields() {
        txtID.setText("");
        txtName.setText("");
        txtClass.setText("");
        cboCourse.setSelectedIndex(0);
        cboGender.setSelectedIndex(0);
        cboMajor.setSelectedIndex(0);
        cboFilterSort.setSelectedIndex(0);
        txtScore1.setText("");
        txtScore2.setText("");
        txtScore3.setText("");
        txtSearch.setText("");
    }

    private void refreshStudentList(ArrayList<Student> students) {
        tableModel.setRowCount(0); 
        for (Student s : students) {
            Object[] row = {
                s.getId(),
                s.getName(),
                s.getCourse(),
                s.getGender(),
                s.getClassName(),
                s.getMajor(),
                s.getSubject1(),
                s.getScore1(),
                s.getSubject2(),
                s.getScore2(),
                s.getSubject3(),
                s.getScore3(),
                String.format("%.2f", s.getAverageScore()),
                s.getGrade()
            };
            tableModel.addRow(row);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtClass = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtSubject1 = new javax.swing.JTextField();
        txtSubject2 = new javax.swing.JTextField();
        txtSubject3 = new javax.swing.JTextField();
        txtScore2 = new javax.swing.JTextField();
        txtScore1 = new javax.swing.JTextField();
        txtScore3 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        cboMajor = new javax.swing.JComboBox<>();
        cboGender = new javax.swing.JComboBox<>();
        btnDelete = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        cboFilterSort = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        btnFilterSort = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTable = new javax.swing.JTable();
        cboCourse = new javax.swing.JComboBox<>();
        btnClear = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("Student Management Program");
        jLabel1.setPreferredSize(new java.awt.Dimension(213, 20));

        jLabel2.setText("Name:");

        jLabel3.setText("Gender:");

        jLabel4.setText("Course:");

        jLabel5.setText("Class:");

        jLabel6.setText("Major:");

        jLabel7.setText("ID:");

        jLabel8.setText("Subject:");

        jLabel9.setText("Score:");

        btnAdd.setText("Add");

        btnUpdate.setText("Update");

        cboMajor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "IT", "Biz", "GD" }));

        cboGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female", "Other" }));

        btnDelete.setText("Delete");

        jLabel10.setText("Search by name, ID:");

        btnSearch.setText("Search");

        cboFilterSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name (Asc)", "Name (Desc)", "AverageScore (Asc)", "AverageScore (Desc)", "Grade: Distinction", "Grade: Merit", "Grade: Pass", "Grade: Fail", " ", " ", " " }));
        cboFilterSort.setToolTipText("");

        jLabel11.setText("Filter, Sort:");

        btnFilterSort.setText("Filter, Sort ");

        tblTable.setModel(tableModel);
        jScrollPane1.setViewportView(tblTable);

        cboCourse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "K5", "K6", "K7" }));

        btnClear.setText("Clear");

        btnSave.setText("Save");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboGender, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtClass)
                                .addComponent(txtName)
                                .addComponent(txtID)
                                .addComponent(cboMajor, 0, 183, Short.MAX_VALUE))
                            .addComponent(cboCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtSubject1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(txtScore1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtSubject2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtScore2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtSubject3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtScore3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cboFilterSort, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnFilterSort, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 60, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cboGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cboCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cboMajor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(btnUpdate)
                                            .addComponent(btnClear)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAdd)
                                        .addComponent(btnSave)))
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtSubject1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtScore1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboFilterSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnFilterSort))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtSubject2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtScore2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3)
                                .addComponent(jLabel10)
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtSubject3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtScore3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel11)
                                .addGap(102, 102, 102)))
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true); 
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFilterSort;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboCourse;
    private javax.swing.JComboBox<String> cboFilterSort;
    private javax.swing.JComboBox<String> cboGender;
    private javax.swing.JComboBox<String> cboMajor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTable;
    private javax.swing.JTextField txtClass;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtScore1;
    private javax.swing.JTextField txtScore2;
    private javax.swing.JTextField txtScore3;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSubject1;
    private javax.swing.JTextField txtSubject2;
    private javax.swing.JTextField txtSubject3;
    // End of variables declaration//GEN-END:variables
}
