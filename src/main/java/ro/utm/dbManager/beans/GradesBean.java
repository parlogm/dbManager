package ro.utm.dbManager.beans;

import java.io.Serializable;

public class GradesBean implements Serializable {

    private long id;
    private String crdName;
    private String crdCode;
    private long stdId;
    private String grade;

    public GradesBean() {

    }

    public GradesBean(long id, String crdName, String crdCode, long stdId, String grade) {
        this.id = id;
        this.crdName = crdName;
        this.crdCode = crdCode;
        this.stdId = stdId;
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public long getStdId() {
        return stdId;
    }

    public void setStdId(long stdId) {
        this.stdId = stdId;
    }

    public String getCrdCode() {
        return crdCode;
    }

    public void setCrdCode(String crdCode) {
        this.crdCode = crdCode;
    }

    public String getCrdName() {
        return crdName;
    }

    public void setCrdName(String crdName) {
        this.crdName = crdName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
