package ro.utm.dbManager.beans;

import java.io.Serializable;

public class CreditsBean implements Serializable {

    private long id;
    private String crdName;
    private String crdNr;
    private String crdCode;

    public CreditsBean() {

    }

    public CreditsBean(long id, String crdName, String crdNr, String crdCode) {
        this.id = id;
        this.crdName = crdName;
        this.crdNr = crdNr;
        this.crdCode = crdCode;
    }

    public String getCrdCode() {
        return crdCode;
    }

    public void setCrdCode(String crdCode) {
        this.crdCode = crdCode;
    }

    public String getCrdNr() {
        return crdNr;
    }

    public void setCrdNr(String crdNr) {
        this.crdNr = crdNr;
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
