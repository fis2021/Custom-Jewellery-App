package org.loose.fis.cja.model;

import org.dizitart.no2.objects.Id;

import java.util.Objects;

public class User {
    @Id
    private String lastName;
    private String firstName;
    private String address;
    private String phone;
    private String sex;
    private String username;
    private String password;
    private String role;
    private static String currentUser;

    public User(String lastName, String firstName, String address, String phone, String sex, String username, String password, String role) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.phone = phone;
        this.sex = sex;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public static String getCurrentUser(){
        return currentUser;
    }

    public static void setCurrentUser(String user){
        currentUser=user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(lastName, user.lastName) && Objects.equals(firstName, user.firstName) && Objects.equals(address, user.address) && Objects.equals(phone, user.phone) && Objects.equals(sex, user.sex) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, address, phone, sex, username, password, role);
    }
}