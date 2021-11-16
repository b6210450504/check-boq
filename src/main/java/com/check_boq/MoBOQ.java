package com.check_boq;

public class MoBOQ {
    int BO_GroupID ;
    int BO_ID ;
    String BO_ProjName ;
    String BO_Member ;
    String BO_Materials ;
    long BO_Amount ;
    int BO_Period ;

    public MoBOQ() {
    }

    public MoBOQ(int BO_GroupID, String BO_ProjName, String BO_Member, String BO_Materials, long BO_Amount, int BO_Period) {
        this.BO_GroupID = BO_GroupID;
        this.BO_ProjName = BO_ProjName;
        this.BO_Member = BO_Member;
        this.BO_Materials = BO_Materials;
        this.BO_Amount = BO_Amount;
        this.BO_Period = BO_Period;
    }

    public MoBOQ(int BO_GroupID, int BO_ID, String BO_ProjName, String BO_Member, String BO_Materials, long BO_Amount, int BO_Period) {
        this.BO_GroupID = BO_GroupID;
        this.BO_ID = BO_ID;
        this.BO_ProjName = BO_ProjName;
        this.BO_Member = BO_Member;
        this.BO_Materials = BO_Materials;
        this.BO_Amount = BO_Amount;
        this.BO_Period = BO_Period;
    }

    public int getBO_GroupID() {
        return BO_GroupID;
    }

    public void setBO_GroupID(int BO_GroupID) {
        this.BO_GroupID = BO_GroupID;
    }

    public int getBO_ID() {
        return BO_ID;
    }

    public void setBO_ID(int BO_ID) {
        this.BO_ID = BO_ID;
    }

    public String getBO_ProjName() {
        return BO_ProjName;
    }

    public void setBO_ProjName(String BO_ProjName) {
        this.BO_ProjName = BO_ProjName;
    }

    public String getBO_Member() {
        return BO_Member;
    }

    public void setBO_Member(String BO_Member) {
        this.BO_Member = BO_Member;
    }

    public String getBO_Materials() {
        return BO_Materials;
    }

    public void setBO_Materials(String BO_MaterialsID) {
        this.BO_Materials = BO_MaterialsID;
    }

    public long getBO_Amount() {
        return BO_Amount;
    }

    public void setBO_Amount(long BO_Amount) {
        this.BO_Amount = BO_Amount;
    }

    public int getBO_Period() {
        return BO_Period;
    }

    public void setBO_Period(int BO_Period) {
        this.BO_Period = BO_Period;
    }

    @Override
    public String toString() {
        return "MoBOQ{" +
                "BO_GroupID=" + BO_GroupID +
                ", BO_ID=" + BO_ID +
                ", BO_ProjName='" + BO_ProjName + '\'' +
                ", BO_Member='" + BO_Member + '\'' +
                ", BO_MaterialsID='" + BO_Materials + '\'' +
                ", BO_Amount=" + BO_Amount +
                ", BO_Period=" + BO_Period +
                '}';
    }
}
