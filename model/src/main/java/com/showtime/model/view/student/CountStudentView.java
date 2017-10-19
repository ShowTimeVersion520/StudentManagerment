package com.showtime.model.view.student;

public class CountStudentView {
    private Long count;
    private String grade;
    private String className;


    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public CountStudentView(Long count, String grade, String className) {
        this.count = count;
        this.grade = grade;
        this.className = className;
    }

    public CountStudentView(Long count, String grade) {
        this.count = count;
        this.grade = grade;
    }

    public CountStudentView() {
    }
}
