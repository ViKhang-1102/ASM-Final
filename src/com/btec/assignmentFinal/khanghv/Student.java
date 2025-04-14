package com.btec.assignmentFinal.khanghv;

public class Student {

    private String id;
    private String name;
    private String course;
    private String gender;
    private String className;
    private String major;
    private String subject1;
    private String subject2;
    private String subject3;
    private double score1;
    private double score2;
    private double score3;
    private double averageScore;

    public Student(String id, String name, String course, String gender, String className, String major,
            String subject1, String subject2, String subject3, double score1, double score2, double score3) {

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (course == null || course.trim().isEmpty()) {
            throw new IllegalArgumentException("Course cannot be empty");
        }
        if (score1 < 0 || score1 > 10 || score2 < 0 || score2 > 10 || score3 < 0 || score3 > 10) {
            throw new IllegalArgumentException("Scores must be between 0 and 10");
        }

        this.id = id;
        this.name = name;
        this.course = course;
        this.gender = gender != null ? gender : "";
        this.className = className != null ? className : "";
        this.major = major != null ? major : "";
        this.subject1 = subject1 != null ? subject1 : "";
        this.subject2 = subject2 != null ? subject2 : "";
        this.subject3 = subject3 != null ? subject3 : "";
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        calculateAverageScore();
    }

    private void calculateAverageScore() {
        if (major.equals("IT")) {
            averageScore = (score1 + score2 * 2 + score3) / 4.0;
        } else if (major.equals("Biz")) {
            averageScore = (score1 * 2 + score2) / 3.0;
        } else if (major.equals("GD")) {
            averageScore = (score1 + score2 * 2 + score3) / 4.0;
        } else {
            if (subject3.isEmpty()) {
                averageScore = (score1 + score2) / 2.0;
            } else {
                averageScore = (score1 + score2 + score3) / 3.0;
            }
        }
    }

    public String getGrade() {
        if (averageScore < 6.5) {
            return "Fail";
        } else if (averageScore <= 7.9) {
            return "Pass";
        } else if (averageScore <= 8.9) {
            return "Merit";
        } else {
            return "Distinction";
        }
    }

    @Override
    public String toString() {
        return id + " - " + name + " - " + course + " - " + gender + " - " + className + " - " + major
                + " - " + subject1 + "(" + score1 + ")" + " - " + subject2 + "(" + score2 + ")"
                + (subject3.isEmpty() ? "" : " - " + subject3 + "(" + score3 + ")")
                + " - Avg: " + String.format("%.2f", averageScore) + " - Grade: " + getGrade();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public String getGender() {
        return gender;
    }

    public String getClassName() {
        return className;
    }

    public String getMajor() {
        return major;
    }

    public String getSubject1() {
        return subject1;
    }

    public String getSubject2() {
        return subject2;
    }

    public String getSubject3() {
        return subject3;
    }

    public double getScore1() {
        return score1;
    }

    public double getScore2() {
        return score2;
    }

    public double getScore3() {
        return score3;
    }

    public double getAverageScore() {
        return averageScore;
    }
}

