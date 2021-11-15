package com.check_boq;

public class MoMaterial {
    String Mat_ID ;
    String Mat_Name ;
    float Mat_Price ;

    public MoMaterial() {
    }

    public MoMaterial(String mat_ID, String mat_Name, float mat_Price) {
        Mat_ID = mat_ID;
        Mat_Name = mat_Name;
        Mat_Price = mat_Price;
    }

    public String getMat_ID() {
        return Mat_ID;
    }

    public void setMat_ID(String mat_ID) {
        Mat_ID = mat_ID;
    }

    public String getMat_Name() {
        return Mat_Name;
    }

    public void setMat_Name(String mat_Name) {
        Mat_Name = mat_Name;
    }

    public float getMat_Price() {
        return Mat_Price;
    }

    public void setMat_Price(float mat_Price) {
        Mat_Price = mat_Price;
    }

    @Override
    public String toString() {
        return "MoMaterial{" +
                "Mat_ID='" + Mat_ID + '\'' +
                ", Mat_Name='" + Mat_Name + '\'' +
                ", Mat_Price='" + Mat_Price + '\'' +
                '}';
    }
}
