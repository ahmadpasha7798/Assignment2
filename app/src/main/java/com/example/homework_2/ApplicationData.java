package com.example.homework_2;


public class ApplicationData {
    public int num;
    public String S_date;
    public String Roll_no;
    public String C_sec;
    public String N_sec;
    public String Reason;
    public String comment;
    public String status;

    @Override
    public String toString() {
        return  num + "-" + Roll_no+"     "+status ;
    }
}
