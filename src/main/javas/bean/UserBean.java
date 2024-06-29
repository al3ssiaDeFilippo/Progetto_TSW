package main.javas.bean;

import java.sql.Date;

public class UserBean {
    private static final long serialVersionUID = 1L;

    //Attributi della tabella 'product'
    int idUser;
    String username;
    String name;
    String surname;
    Date birthDate;
    String email;
    String password;
    //Inizio Modifiche Qui
    String salt;
    //Fine Modifiche Qui
    String TelNumber;
    boolean admin;

    public UserBean() {
        idUser = -1;
        username = "";
        name = "";
        surname = "";
        birthDate = null;
        email = "";
        password = "";
        //Inizio Modifiche Qui
        salt = "";
        //Fine Modifiche Qui
        TelNumber = "";
        admin = false;
    }

    public boolean getAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
    public String getTelNumber() { return TelNumber; }
    public void setTelNumber(String telNumber) { TelNumber = telNumber; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    //Inizio Modifiche Qui
    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }
    //Fine Modifiche Qui
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public int getIdUser() { return idUser; }

    public void setIdUser(int idUser) { this.idUser = idUser; }

    @Override
    public String toString() {
        return "UserBean{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", TelNumber=" + TelNumber +
                ", admin='" + admin + '\'' +
                '}';
    }
}
