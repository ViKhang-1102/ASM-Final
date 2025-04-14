package com.btec.assignmentFinal.khanghv;

import java.util.ArrayList;
import java.io.*;

public class StudentController {

    private ArrayList<Student> studentList;
    private int idCounter;

    public StudentController() {
        studentList = new ArrayList<>();
        idCounter = 1;
    }

    private String generateAutoId() {
        String id = String.format("%02d", idCounter);
        idCounter++;
        return id;
    }

    public void addStudent(String name, String course, String gender, String className, String major,
            String subject1, String subject2, String subject3, double score1, double score2, double score3) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (score1 < 0 || score1 > 10 || score2 < 0 || score2 > 10 || score3 < 0 || score3 > 10) {
            throw new IllegalArgumentException("Score must be between 0 and 10");
        }
        String newId = generateAutoId();
        Student student = new Student(newId, name, course, gender, className, major, subject1, subject2, subject3, score1, score2, score3);
        studentList.add(student);
    }

    public void updateStudent(int index, Student updatedStudent) {
        if (index < 0 || index >= studentList.size()) {
            throw new IllegalArgumentException("Invalid student index");
        }
        updatedStudent = new Student(studentList.get(index).getId(), updatedStudent.getName(), updatedStudent.getCourse(),
                updatedStudent.getGender(), updatedStudent.getClassName(), updatedStudent.getMajor(),
                updatedStudent.getSubject1(), updatedStudent.getSubject2(), updatedStudent.getSubject3(),
                updatedStudent.getScore1(), updatedStudent.getScore2(), updatedStudent.getScore3());
        studentList.set(index, updatedStudent);
    }

    public void deleteStudent(int index) {
        if (index < 0 || index >= studentList.size()) {
            throw new IllegalArgumentException("Invalid student index");
        }
        studentList.remove(index);
        for (int i = 0; i < studentList.size(); i++) {
            studentList.get(i).setId(String.format("%02d", i + 1));
        }
        idCounter = studentList.size() + 1;
    }

    public Student getStudent(int index) {
        if (index < 0 || index >= studentList.size()) {
            throw new IllegalArgumentException("Invalid student index");
        }
        return studentList.get(index);
    }

    public ArrayList<Student> getAllStudents() {
        return studentList;
    }

    public ArrayList<Student> searchStudents(String query) {
        ArrayList<Student> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for (Student s : studentList) {
            if (s.getId().toLowerCase().contains(lowerQuery) || s.getName().toLowerCase().contains(lowerQuery)) {
                results.add(s);
            }
        }
        return results;
    }

    public ArrayList<Student> filterByGrade(String grade) {
        ArrayList<Student> results = new ArrayList<>();
        for (Student s : studentList) {
            if (s.getGrade().equalsIgnoreCase(grade)) {
                results.add(s);
            }
        }
        return results;
    }

    public void sortStudents(String sortCriterion, boolean ascending) {
        if (studentList.isEmpty() || studentList.size() == 1) {
            return;
        }
        switch (sortCriterion) {
            case "Name":
                mergeSort(0, studentList.size() - 1, "Name", ascending);
                break;
            case "AverageScore":
                mergeSort(0, studentList.size() - 1, "AverageScore", ascending);
                break;
            case "ID":
                mergeSort(0, studentList.size() - 1, "ID", ascending);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort criterion");
        }
    }

    private void mergeSort(int left, int right, String criterion, boolean ascending) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(left, mid, criterion, ascending);
            mergeSort(mid + 1, right, criterion, ascending);
            merge(left, mid, right, criterion, ascending);
        }
    }

    private void merge(int left, int mid, int right, String criterion, boolean ascending) {
        ArrayList<Student> temp = new ArrayList<>();
        int i = left;
        int j = mid + 1;

        while (i <= mid && j <= right) {
            int comparison = compareStudents(studentList.get(i), studentList.get(j), criterion);
            if (ascending) {
                if (comparison <= 0) {
                    temp.add(studentList.get(i++));
                } else {
                    temp.add(studentList.get(j++));
                }
            } else {
                if (comparison > 0) {
                    temp.add(studentList.get(i++));
                } else {
                    temp.add(studentList.get(j++));
                }
            }
        }

        while (i <= mid) {
            temp.add(studentList.get(i++));
        }
        while (j <= right) {
            temp.add(studentList.get(j++));
        }

        for (int k = 0; k < temp.size(); k++) {
            studentList.set(left + k, temp.get(k));
        }
    }

    private int compareStudents(Student s1, Student s2, String criterion) {
        if (criterion.equals("Name")) {
            return s1.getName().compareToIgnoreCase(s2.getName());
        } else if (criterion.equals("AverageScore")) {
            return Double.compare(s1.getAverageScore(), s2.getAverageScore());
        } else if (criterion.equals("ID")) {
            return s1.getId().compareTo(s2.getId());
        }
        throw new IllegalArgumentException("Invalid sort criterion");
    }

    public void saveToFile(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("ID,Name,Course,Gender,Class,Major,Subject1,Score1,Subject2,Score2,Subject3,Score3,Average,Grade");
            writer.newLine();

            for (Student s : studentList) {
                String line = String.format("%s,%s,%s,%s,%s,%s,%s,%.2f,%s,%.2f,%s,%.2f,%.2f,%s",
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
                        s.getAverageScore(),
                        s.getGrade());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Error saving students to file: " + e.getMessage());
        }
    }

    public void loadFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine();

            studentList.clear();
            idCounter = 1;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 14) {
                    continue;
                }

                try {
                    String id = parts[0];
                    String name = parts[1];
                    String course = parts[2];
                    String gender = parts[3];
                    String className = parts[4];
                    String major = parts[5];
                    String subject1 = parts[6];
                    double score1 = Double.parseDouble(parts[7]);
                    String subject2 = parts[8];
                    double score2 = Double.parseDouble(parts[9]);
                    String subject3 = parts[10];
                    double score3 = Double.parseDouble(parts[11]);

                    Student student = new Student(id, name, course, gender, className, major, subject1, subject2, subject3, score1, score2, score3);
                    studentList.add(student);

                    try {
                        int currentIdNum = Integer.parseInt(id);
                        idCounter = Math.max(idCounter, currentIdNum + 1);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }

            for (int i = 0; i < studentList.size(); i++) {
                studentList.get(i).setId(String.format("%02d", i + 1));
            }
            idCounter = studentList.size() + 1;
        } catch (IOException e) {
            throw new IOException("Error loading students from file: " + e.getMessage());
        }
    }

}
