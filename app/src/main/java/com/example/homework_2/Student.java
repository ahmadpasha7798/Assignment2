package com.example.homework_2;

import java.sql.Date;

public class Student {
    public int num;
    public String S_date;
    public String Roll_no;
    public String C_sec;
    public String N_sec;
    public String Reason;

    @Override
    public String toString() {
        return  num + "-" + Roll_no ;
    }
}
