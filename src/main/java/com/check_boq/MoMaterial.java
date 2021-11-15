package com.check_boq;

public class MoMaterial {
    int Mat_ID ;
    String Mat_Name ;
    int Mat_Price ;

    public MoMaterial() {
    }

    public MoMaterial(String mat_Name, int mat_Price) {
        Mat_Name = mat_Name;
        Mat_Price = mat_Price;
    }

    public MoMaterial(int mat_ID, String mat_Name, int mat_Price) {
        Mat_ID = mat_ID;
        Mat_Name = mat_Name;
        Mat_Price = mat_Price;
    }

    public int getMat_ID() {
        return Mat_ID;
    }

    public void setMat_ID(int mat_ID) {
        Mat_ID = mat_ID;
    }

    public String getMat_Name() {
        return Mat_Name;
    }

    public void setMat_Name(String mat_Name) {
        Mat_Name = mat_Name;
    }

    public int getMat_Price() {
        return Mat_Price;
    }

    public void setMat_Price(int mat_Price) {
        Mat_Price = mat_Price;
    }

    @Override
    public String toString() {
        return "MoMaterial{" +
                "Mat_ID=" + Mat_ID +
                ", Mat_Name='" + Mat_Name + '\'' +
                ", Mat_Price=" + Mat_Price +
                '}';
    }
}
