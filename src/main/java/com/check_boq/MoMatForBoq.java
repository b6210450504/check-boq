package com.check_boq;

public class MoMatForBoq {
    int Mat_ID ;
    String Mat_Name ;
    int Mat_Price ;
    int Mat_Qty ;
    long Mat_Total ;

    public MoMatForBoq(int mat_ID, String mat_Name, int mat_Price, int mat_Qty) {
        Mat_ID = mat_ID;
        Mat_Name = mat_Name;
        Mat_Price = mat_Price;
        Mat_Qty = mat_Qty;
        Mat_Total = mat_Price * mat_Qty ;
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

    public int getMat_Qty() {
        return Mat_Qty;
    }

    public void setMat_Qty(int mat_Qty) {
        Mat_Qty = mat_Qty;
    }

    public long getMat_Total() {
        return Mat_Total;
    }

    public void setMat_Total(long mat_Total) {
        Mat_Total = mat_Total;
    }

    @Override
    public String toString() {
        return "MoMatForBoq{" +
                "Mat_ID=" + Mat_ID +
                ", Mat_Name='" + Mat_Name + '\'' +
                ", Mat_Price=" + Mat_Price +
                ", Mat_Qty=" + Mat_Qty +
                ", Mat_Total=" + Mat_Total +
                '}';
    }
}
