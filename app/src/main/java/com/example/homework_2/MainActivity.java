package com.example.homework_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Student> stu_data=new ArrayList<Student>();
    ArrayAdapter daysAdapter;
    DBHelper db=new DBHelper(this);
    int A_num=0;
    int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        String id=intent.getStringExtra("Username");
        String role=db.getRole(id);
        if(role.equals("Staff")){
            stu_data=db.Get_list();
        }
        else
        {
            stu_data=db.Get_list(id);
        }
        A_num=db.Get_App_num();
        daysAdapter= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, stu_data);
        ListView lst=findViewById(R.id.lst);
        lst.setAdapter(daysAdapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent act=new Intent(MainActivity.this , UpdateForm.class);
                act.putExtra("num", stu_data.get(position).num);
                index=stu_data.get(position).num;
                act.putExtra("Roll_no", stu_data.get(position).Roll_no);
                act.putExtra("Date", stu_data.get(position).S_date);
                act.putExtra("C_sec", stu_data.get(position).C_sec);
                act.putExtra("N_sec", stu_data.get(position).N_sec);
                act.putExtra("Reason", stu_data.get(position).Reason);

                startActivityForResult(act,1);
            }
        });

        FloatingActionButton btn=findViewById(R.id.log_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act=new Intent(MainActivity.this , Formactivity.class);
                act.putExtra("num", A_num+1);
                startActivityForResult(act,0);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Student obj = new Student();
                obj.num=index;
                obj.Roll_no = data.getStringExtra("Roll_no");
                obj.S_date = data.getStringExtra("Date");
                obj.C_sec = data.getStringExtra("C_sec");
                obj.N_sec = data.getStringExtra("N_sec");
                obj.Reason = data.getStringExtra("Reason");
                db.update_application(obj);
                stu_data=db.Get_list();
                daysAdapter= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, stu_data);
                ListView lst=findViewById(R.id.lst);
                lst.setAdapter(daysAdapter);
            }
        }
        else if(requestCode==0)
        {
            if (resultCode == RESULT_OK) {
                A_num++;

                Student obj = new Student();
                obj.num=A_num;
                assert data != null;
                obj.Roll_no = data.getStringExtra("Roll_no");
                obj.S_date = data.getStringExtra("Date");
                obj.C_sec = data.getStringExtra("C_sec");
                obj.N_sec = data.getStringExtra("N_sec");
                obj.Reason = data.getStringExtra("Reason");
                db.insert_Application(obj);
                //stu_data.add(obj);
                //daysAdapter.notifyDataSetChanged();
                stu_data=db.Get_list();
                daysAdapter= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, stu_data);
                ListView lst=findViewById(R.id.lst);
                lst.setAdapter(daysAdapter);

            }
        }
    }
}
