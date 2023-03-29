package entity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class Student {
    private long id ;
    private String name ;
    private String surname ;
    private String middleName ;
    private String telephone ;
    private String email ;
    private int groupID ;
    private String photoPath;
    private List<Attendance> attendanceList;
    private List<Grade> gradeList;

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getGroup() {
        return groupID;
    }

    public void setGroupID(int group) {
        groupID=group;
    }

    private int age;

    public void addAttendance(Attendance attendance) {
        attendanceList.add(attendance);
    }

    public void addGrade(Grade grade) {
        gradeList.add(grade);
    }

    public Student(int id, String name, String surname, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email="default email";
    }
    public Student(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

