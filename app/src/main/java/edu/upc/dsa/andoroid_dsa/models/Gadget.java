package edu.upc.dsa.andoroid_dsa.models;

public class Gadget {
    String idGadget;
    int cost;
    String description;
    String unityShape;
    //int imagenGadget;

    public Gadget(){}

    public Gadget(String idGadget, int cost, String description, String unityShape) {
        this.idGadget = idGadget;
        this.cost = cost;
        this.description = description;
        this.unityShape = unityShape;
    }

    public String getIdGadget() {
        return this.idGadget;
    }

    public void setId(String idGadget) {
        this.idGadget = idGadget;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnityShape() {
        return unityShape;
    }

    public void setUnityShape(String unityShape) {
        this.unityShape = unityShape;
    }
}
