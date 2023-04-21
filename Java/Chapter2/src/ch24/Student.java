package ch24;

import java.util.ArrayList;

public class Student {
    
    private int studentId;
    private String studentName;

    ArrayList<Subject> subjectList;

    Student(int studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;

        subjectList = new ArrayList<>();
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void addSubject(String name, int point) {
        Subject subject = new Subject();
        subject.setName(name);
        subject.setScorePoints(point);

        subjectList.add(subject);
    }

    public void showStudentInfo() {
        int total = 0;
        for(Subject subject : subjectList) {
            total += subject.getScorePoints();
            System.out.println(studentName + "학생의 " + subject.getName() + "과목의 성적은 " + subject.getScorePoints() + "점 입니다.");
        }
        System.out.println(studentName + "학생의 총점은 " + total + "점 입니다.");
    }
}

