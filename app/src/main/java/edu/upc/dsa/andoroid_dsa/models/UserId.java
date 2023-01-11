package edu.upc.dsa.andoroid_dsa.models;

public class UserId {
    String idUser;

    public UserId(){};

    public UserId(String id) {
        this.setIdUser(id);
    }

    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}