package ro.utm.dbManager.beans;

import java.io.Serializable;

public class StudentBean implements Serializable {

    private long id;
    private String firstName;
    private String lastName;
    private String eMail;
    private String studentRegNr;
    private String studentYear;
    private String studentGroup;
    private String phone;

    public StudentBean() {

    }

    public StudentBean(long id, String firstName, String lastName, String e_mail, String studentRegNr, String studentYear,
                       String studentGroup, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = e_mail;
        this.studentRegNr = studentRegNr;
        this.studentYear = studentYear;
        this.studentGroup = studentGroup;
        this.phone = phone;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudentRegNr() {
        return studentRegNr;
    }

    public void setStudentRegNr(String studentRegNr) {
        this.studentRegNr = studentRegNr;
    }

    public String getStudentYear() {
        return studentYear;
    }

    public void setStudentYear(String studentYear) {
        this.studentYear = studentYear;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    @Override
    public String toString()
    {
        return "StudentBean{" + "id=" + id + ", First name=" + firstName + ", Last name=" + lastName + ", E-mail=" + eMail + '}';
    }
}
