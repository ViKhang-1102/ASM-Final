# Introduce myseft 
Name: Huynh Vi Khang
ID: BC00429
Class: SE07201
Major: IT
ID Subject: 7430
Subject: Data Structures & Algorithms
Study & Work: BTEC FPT Can Tho

# Introduction to Student Management System
Student Management System is an application developed in Java programming language with a graphical interface (GUI) using Java Swing. The goal of the system is to support users in managing student lists in an intuitive, convenient and effective way. The system includes all the basic functions such as:
    Add student: Process data input from fields such as ID, Full name, Gender, Age, Hometown, Major and subject scores. The addStudent() function is used to save data to the ArrayList<Student> list.
    Update and delete student: Allows users to select a row in the table and edit or delete the corresponding student. Functions such as updateStudent() and deleteStudent() are applied.
    Search: Search students by name or ID using linear search, implemented in the searchStudents(String keyword) function.
    Sorting: The system supports sorting students by name, GPA, or ID using the Merge Sort algorithm, ensuring stable performance O(n log n). Optional ascending or descending sorting via cboSort.
    Filtering by academic grade: Based on the average score, students are classified into levels: Distinction, Merit, Pass, or Fail. This filtering is done via the filterStudentsByGrade() function.
    Displaying data using JTable: Student data is displayed using DefaultTableModel, making it easy to update the table when there are changes. The updateTable() function is responsible for synchronizing data between the list and the table.
    Saving and loading data: The system supports reading and writing data from the students.txt file. The saveToFile() and loadFromFile() functions are responsible for this function, helping users not lose data when closing the application.
    Error handling and notifications: Use JOptionPane to display error messages or confirm operations such as incorrect input, missing data, exceeding the limit, or deleting students.
The entire student list is saved using ArrayList<Student>, ensuring flexibility when adding, editing, and deleting data. The system is designed with clear object orientation with classes such as Student, StudentController, and Main (main interface). The interface is intuitive, easy to operate, suitable for students, teachers, or non-professional users.
