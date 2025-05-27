package src.Model;

import java.sql.Date;

public class UserModel {
    private String id;
    private Date date;
    private String fullName;
    private String gender; // 'M' or 'F'
    private int age;
    private String phone;

    // Constructor
    public UserModel(String id, Date date, String fullName, String gender, int age, String phone) {
        this.id = id;
        this.date = date;
        this.fullName = fullName;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // toString method
    @Override
    public String toString() {
        return "Passenger ID: " + id + "\n" +
               "Date: " + date + "\n" +
               "Name: " + fullName + "\n" +
               "Gender: " + gender + "\n" +
               "Age: " + age + "\n" +
               "Phone: " + phone;
    }
}
