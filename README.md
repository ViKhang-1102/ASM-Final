# Introduce myseft 
Name: Huynh Vi Khang
ID: BC00429
Class: SE07201
Major: IT
ID Subject: 7430
Subject: Data Structures & Algorithms
Study & Work: BTEC FPT Can Tho

# Introduction to Student Management System
The Student Management System is a program made with Java and uses Java Swing to create a simple user interface. This system helps users manage student information in an easy and clear way. It has basic functions like adding, editing, deleting, searching, and sorting students. To add a student, the user types information like ID, name, gender, age, hometown, major, and scores. The `addStudent()` function saves this data into an `ArrayList<Student>`. Users can also update or remove a student by selecting a row in the table and using the `updateStudent()` or `deleteStudent()` functions. The search function allows users to find students by name or ID with the `searchStudents(String keyword)` function. The system can sort students by name, GPA, or ID using Merge Sort, which is fast and supports sorting in both increasing and decreasing order with the `cboSort`. Students are also grouped into grades like Distinction, Merit, Pass, or Fail, based on their average score using the `filterStudentsByGrade()` function. All student data is shown in a table using `JTable` and `DefaultTableModel`, and the `updateTable()` function helps keep the table updated. The program also shows error messages or confirmation messages using `JOptionPane`, for example when the user enters wrong or missing data, or wants to delete a student. The student list is saved in an `ArrayList<Student>`, which makes it easy to add, change, or delete data. The system is built using object-oriented programming with classes like `Student`, `StudentController`, and `Main`. The interface is simple and easy to use, good for students, teachers, or anyone without much technical knowledge.
