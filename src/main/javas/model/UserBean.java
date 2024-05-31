package main.javas.model;

import java.sql.Date;

public class UserBean {
    private static final long serialVersionUID = 1L;

    //Attributi della tabella 'product'
    int idUser;
    String username;
    String name;
    String surname;
    Date birthDate;
    String address;
    String email;
    String password;
    int TelNumber;
    String type;

    public UserBean() {
        idUser = -1;
        username = "";
        name = "";
        surname = "";
        birthDate = null;
        address = "";
        email = "";
        password = "";
        TelNumber = 0;
        type = "";
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public int getTelNumber() {
        return TelNumber;
    }

    public void setTelNumber(int telNumber) {
        TelNumber = telNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", TelNumber=" + TelNumber +
                ", type='" + type + '\'' +
                '}';
    }
}
