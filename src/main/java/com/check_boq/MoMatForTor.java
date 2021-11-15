package com.check_boq;

public class MoMatForTor  {
    private String Mat_Name ;
    private int Mat_Qty ;

    public MoMatForTor(String mat_Name, int mat_Qty) {
        Mat_Name = mat_Name;
        Mat_Qty = mat_Qty;
    }

    public String getMat_Name() {
        return Mat_Name;
    }

    public void setMat_Name(String mat_Name) {
        Mat_Name = mat_Name;
    }

    public int getMat_Qty() {
        return Mat_Qty;
    }

    public void setMat_Qty(int mat_Qty) {
        Mat_Qty = mat_Qty;
    }
}
