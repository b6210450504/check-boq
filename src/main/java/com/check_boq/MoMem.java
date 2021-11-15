package com.check_boq;

public class MoMem {
    String Mem_Name ;

    public MoMem(String mem_Name) {
        Mem_Name = mem_Name;
    }

    public String getMem_Name() {
        return Mem_Name;
    }

    public void setMem_Name(String mem_Name) {
        Mem_Name = mem_Name;
    }

    @Override
    public String toString() {
        return "MoMem{" +
                "Mem_Name='" + Mem_Name + '\'' +
                '}';
    }
}
