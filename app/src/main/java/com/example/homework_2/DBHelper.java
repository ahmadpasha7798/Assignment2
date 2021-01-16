package com.example.homework_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Student.db";
    public static final String LOGIN_TABLE="Login_data";
    public static final String STUDENT_TABLE="Student_data";
    public static final String STAFF_TABLE="Staff_data";
    public static final String APPLICATION_TABLE="Application_data";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+LOGIN_TABLE+" (id text primary key, pwd text, role text);");
        db.execSQL("CREATE TABLE "+STUDENT_TABLE+" (rollno text primary key, name text,section text);");
        db.execSQL("CREATE TABLE "+STAFF_TABLE+" (id text primary key, name text,Designation text);");
        db.execSQL("CREATE TABLE "+APPLICATION_TABLE+" (S_date text, id integer primary key, rollno text,N_sec text, description text,staff_comment text,app_status text);");
        ContentValues contentValues=new ContentValues();
        contentValues.put("id","12345");
        contentValues.put("pwd", "12345");
        contentValues.put("role","Head");
        db.insert(LOGIN_TABLE,null, contentValues);

        contentValues=new ContentValues();
        contentValues.put("id","17271519-002");
        contentValues.put("pwd", "12345");
        contentValues.put("role","Student");
        db.insert(LOGIN_TABLE,null, contentValues);

        contentValues=new ContentValues();
        contentValues.put("id","12346");
        contentValues.put("name", "Test Staff");
        contentValues.put("Designation","Coordinator");
        db.insert(STAFF_TABLE,null, contentValues);

        contentValues=new ContentValues();
        contentValues.put("id","12347");
        contentValues.put("name", "Test Staff2");
        contentValues.put("Designation","Coordinator");
        db.insert(STAFF_TABLE,null, contentValues);

        contentValues=new ContentValues();
        contentValues.put("rollno","17271519-002");
        contentValues.put("name", "Ahmad Pasha");
        contentValues.put("section","B");
        db.insert(STUDENT_TABLE,null, contentValues);
        contentValues=new ContentValues();
        contentValues.put("rollno","17271519-006");
        contentValues.put("name", "Sofia Naseem");
        contentValues.put("section","B");
        db.insert(STUDENT_TABLE,null, contentValues);
        contentValues=new ContentValues();
        contentValues.put("rollno","17271519-008");
        contentValues.put("name", "Ubaid Mehmood");
        contentValues.put("section","B");
        db.insert(STUDENT_TABLE,null, contentValues);
        contentValues=new ContentValues();
        contentValues.put("rollno","17271519-010");
        contentValues.put("name", "Hafiz Suhaib");
        contentValues.put("section","B");
        db.insert(STUDENT_TABLE,null, contentValues);
        contentValues=new ContentValues();
        contentValues.put("rollno","17271519-011");
        contentValues.put("name", "Abdul Jameel");
        contentValues.put("section","B");
        db.insert(STUDENT_TABLE,null, contentValues);
        contentValues=new ContentValues();
        contentValues.put("rollno","17271519-014");
        contentValues.put("name", "Muhammad Qasim");
        contentValues.put("section","B");
        db.insert(STUDENT_TABLE,null, contentValues);
        contentValues=new ContentValues();
        contentValues.put("rollno","17271519-015");
        contentValues.put("name", "Bilal Ghafoor");
        contentValues.put("section","B");
        db.insert(STUDENT_TABLE,null, contentValues);
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

    public void update_application(ApplicationData obj)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(APPLICATION_TABLE,"id=?",new String[]{Integer.toString(obj.num)});
        ContentValues contentValues=new ContentValues();
        contentValues.put("id",obj.num);
        contentValues.put("S_date",obj.S_date);
        contentValues.put("rollno",obj.Roll_no);
        contentValues.put("N_sec",obj.N_sec);
        contentValues.put("description",obj.Reason);
        contentValues.put("staff_comment",obj.comment);
        contentValues.put("app_status",obj.status);
        db.insert(APPLICATION_TABLE,null, contentValues);
    }
    public void insert_Application(ApplicationData obj){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("id",obj.num);
        contentValues.put("S_date",obj.S_date);
        contentValues.put("rollno",obj.Roll_no);
        contentValues.put("N_sec",obj.N_sec);
        contentValues.put("description",obj.Reason);
        contentValues.put("staff_comment",obj.comment);
        contentValues.put("app_status",obj.status);
        db.insert(APPLICATION_TABLE,null, contentValues);
    }

    public ArrayList<ApplicationData> Get_list(String roll) {
        ArrayList<ApplicationData> array_list = new ArrayList<ApplicationData>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + APPLICATION_TABLE+" WHERE rollno = '" + roll+"';", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            ApplicationData obj=new ApplicationData();
            obj.S_date=res.getString(0);
            obj.num=res.getInt(1);
            obj.Roll_no=res.getString(2);
            Cursor res1=this.get_Studata(obj.Roll_no);
            res1.moveToFirst();
            obj.C_sec=res1.getString(2);
            obj.N_sec=res.getString(3);
            obj.Reason=res.getString(4);
            obj.comment=res.getString(5);
            obj.status=res.getString(6);

            array_list.add(obj);
            res.moveToNext();
        }

        return array_list;
    }

    public ArrayList<ApplicationData> Get_list_staff(String role) {
        ArrayList<ApplicationData> array_list = new ArrayList<ApplicationData>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;
        if(role.equals("Coordinator"))
            res= db.rawQuery("SELECT * FROM " + APPLICATION_TABLE+" WHERE app_status='Pending';", null);
        else
            res=db.rawQuery("SELECT * FROM " + APPLICATION_TABLE+" WHERE app_status='Under Processing';", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            ApplicationData obj=new ApplicationData();
            obj.S_date=res.getString(0);
            obj.num=res.getInt(1);
            obj.Roll_no=res.getString(2);
            Cursor res1=this.get_Studata(obj.Roll_no);
            res1.moveToFirst();
            obj.C_sec=res1.getString(2);
            obj.N_sec=res.getString(3);
            obj.Reason=res.getString(4);
            obj.comment=res.getString(5);
            obj.status=res.getString(6);

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

    public boolean chk_user(String id) {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor res=db.rawQuery("Select * From "+STUDENT_TABLE+" WhERE rollno='"+id+"'  ;",null);
        if(res.getCount()>0)
            return true;
        else
        {
            Cursor res1=db.rawQuery("Select * From "+STAFF_TABLE+" WHERE id='"+id+"';",null);
            int a=res1.getCount();
            if(res1.getCount()>0)
                return true;
            else
                return false;
        }
    }

    public boolean chk_account(String id) {
        SQLiteDatabase db =this.getReadableDatabase();
        //Cursor res=db.rawQuery("Select * From "+LOGIN_TABLE+" WhERE id=? AND pwd=?;",new String[]{id,pwd});
        Cursor res=db.query(LOGIN_TABLE,new String[]{"id","pwd","role"},"  id=?",new String[]{id},null,null,null);
        String r=Integer.toString(res.getCount());
        Log.d("count",r);
        if(res.getCount()==1)
            return true;
        else
            return false;
    }

    public boolean chk_user_stu(String id) {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor res=db.rawQuery("Select * From "+STUDENT_TABLE+" WhERE rollno='"+id+"'  ;",null);
        if(res.getCount()>0)
            return true;
        else
            return false;
    }


    public String getDesignation(String id) {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor res=db.rawQuery("Select * From "+STAFF_TABLE+" WhERE id='"+id+"' ;",null);
        if(res.getCount()>0) {
            res.moveToFirst();
            return res.getString(2);
        }
        return null;
    }

    public Cursor get_Studata(String id) {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor res=db.query(STUDENT_TABLE,new String[]{"rollno","name","section"},"  rollno=?",new String[]{id},null,null,null);
        return res;

    }

    public void Update_section(String roll_no, String n_sec) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=get_Studata(roll_no);
        res.moveToFirst();
        db.delete(STUDENT_TABLE,"rollno=?",new String[]{roll_no});
        ContentValues contentValues=new ContentValues();
        contentValues.put("rollno",roll_no);
        contentValues.put("name",res.getString(1));
        contentValues.put("section",n_sec);
        db.insert(STUDENT_TABLE,null, contentValues);
    }
}
