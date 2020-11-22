package com.example.homework_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Student.db";
    public static final String LOGIN_TABLE="Login_data";
    public static final String STUDENT_TABLE="Student_data";
    public static final String STAFF_TABLE="Staff";
    public static final String APPLICATION_TABLE="Application_data";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+LOGIN_TABLE+" (id text primary key, pwd text, role text);");
        db.execSQL("CREATE TABLE "+STUDENT_TABLE+" (rollno text primary key, name text,section text);");
        db.execSQL("CREATE TABLE "+STAFF_TABLE+" (id text primary key, name text,Designation text);");
        db.execSQL("CREATE TABLE "+APPLICATION_TABLE+" (S_date text, id integer primary key, rollno text,C_sec text,N_sec text, description text);");
        ContentValues contentValues=new ContentValues();
        contentValues.put("id","12345");
        contentValues.put("pwd", "12345");
        contentValues.put("role","Staff");
        db.insert(LOGIN_TABLE,null, contentValues);
        contentValues=new ContentValues();
        contentValues.put("id","17271519-002");
        contentValues.put("pwd", "12345");
        contentValues.put("role","Student");
        db.insert(LOGIN_TABLE,null, contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ LOGIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ STUDENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ STAFF_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ APPLICATION_TABLE);
        onCreate(db);
    }

    public void insert_user(String id, String pwd,String role){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("id",id);
        contentValues.put("pwd", pwd);
        contentValues.put("role",role);
        db.insert(LOGIN_TABLE,null, contentValues);
    }

    public String getRole(String id){
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor res=db.rawQuery("Select * From "+LOGIN_TABLE+" WhERE id='"+id+"' ;",null);
        if(res.getCount()>0) {
            res.moveToFirst();
            return res.getString(2);
        }
        return null;
    }
    public boolean authorize(String id, String pwd){
        SQLiteDatabase db =this.getReadableDatabase();
        //Cursor res=db.rawQuery("Select * From "+LOGIN_TABLE+" WhERE id=? AND pwd=?;",new String[]{id,pwd});
        Cursor res=db.query(LOGIN_TABLE,new String[]{"id","pwd","role"},"  id=? AND pwd=?",new String[]{id,pwd},null,null,null);
        String r=Integer.toString(res.getCount());
        Log.d("count",r);
        if(res.getCount()==1)
          return true;
        else
            return false;
    }

    public boolean chk_stu(String id)
    {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor res=db.rawQuery("Select * From "+STUDENT_TABLE+" WhERE rollno='"+id+"'  ;",null);
        if(res.getCount()>0)
            return true;
        else
            return false;
    }

    public void update_application(Student obj)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(APPLICATION_TABLE,"id=?",new String[]{Integer.toString(obj.num)});
        ContentValues contentValues=new ContentValues();
        contentValues.put("id",obj.num);
        contentValues.put("S_date",obj.S_date);
        contentValues.put("rollno",obj.Roll_no);
        contentValues.put("C_sec",obj.C_sec);
        contentValues.put("N_sec",obj.N_sec);
        contentValues.put("description",obj.Reason);
        db.insert(APPLICATION_TABLE,null, contentValues);
    }
    public void insert_Application(Student obj){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("id",obj.num);
        contentValues.put("S_date",obj.S_date);
        contentValues.put("rollno",obj.Roll_no);
        contentValues.put("C_sec",obj.C_sec);
        contentValues.put("N_sec",obj.N_sec);
        contentValues.put("description",obj.Reason);
        db.insert(APPLICATION_TABLE,null, contentValues);
    }

    public ArrayList<Student> Get_list(){
        ArrayList<Student> array_list=new ArrayList<Student>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+APPLICATION_TABLE,null);
        res.moveToFirst();
        while(res.isAfterLast()==false){
            Student obj=new Student();
            obj.S_date=res.getString(0);
            obj.num=res.getInt(1);
            obj.Roll_no=res.getString(2);
            obj.C_sec=res.getString(3);
            obj.N_sec=res.getString(4);
            obj.Reason=res.getString(5);

            array_list.add(obj);
            res.moveToNext();
        }

        return array_list;
    }

    public ArrayList<Student> Get_list(String roll) {
        ArrayList<Student> array_list = new ArrayList<Student>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + APPLICATION_TABLE+" WHERE rollno = '" + roll+"';", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            Student obj = new Student();
            obj.S_date = res.getString(0);
            obj.num = res.getInt(1);
            obj.Roll_no = res.getString(2);
            obj.C_sec = res.getString(3);
            obj.N_sec = res.getString(4);
            obj.Reason = res.getString(5);

            array_list.add(obj);
            res.moveToNext();
        }

        return array_list;
    }

    public int Get_App_num(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("SELECT MAX(id) FROM "+APPLICATION_TABLE,null);
        if(res.getCount()>0) {
            res.moveToFirst();
            return res.getInt(0);
        }
        return 0;
    }

}
