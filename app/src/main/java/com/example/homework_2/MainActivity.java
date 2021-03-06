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
    List<ApplicationData> stu_data=new ArrayList<ApplicationData>();
    ArrayAdapter daysAdapter;
    DBHelper db=new DBHelper(this);
    int A_num=0;
    int index=0;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        id=intent.getStringExtra("Username");
        //getActionBar().setTitle(id);
        stu_data=db.Get_list(id);
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
                act.putExtra("comment",stu_data.get(position).comment);
                act.putExtra("status",stu_data.get(position).status);

                startActivityForResult(act,1);
            }
        });

        FloatingActionButton btn=findViewById(R.id.log_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act=new Intent(MainActivity.this , Formactivity.class);
                act.putExtra("num", A_num+1);
                act.putExtra("Username",id);
                startActivityForResult(act,0);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                ApplicationData obj = new ApplicationData();
                obj.num=index;
                obj.Roll_no = data.getStringExtra("Roll_no");
                obj.S_date = data.getStringExtra("Date");
                obj.C_sec = data.getStringExtra("C_sec");
                obj.N_sec = data.getStringExtra("N_sec");
                obj.Reason = data.getStringExtra("Reason");
                obj.comment="No comment";
                obj.status="Pending";
                db.update_application(obj);
                stu_data=db.Get_list(id);
                daysAdapter= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, stu_data);
                ListView lst=findViewById(R.id.lst);
                lst.setAdapter(daysAdapter);
            }
        }
        else if(requestCode==0)
        {
            if (resultCode == RESULT_OK) {
                A_num++;

                ApplicationData obj = new ApplicationData();
                obj.num=A_num;
                assert data != null;
                obj.Roll_no = data.getStringExtra("Roll_no");
                obj.S_date = data.getStringExtra("Date");
                obj.C_sec = data.getStringExtra("C_sec");
                obj.N_sec = data.getStringExtra("N_sec");
                obj.Reason = data.getStringExtra("Reason");
                obj.comment="No comment";
                obj.status="Pending";
                db.insert_Application(obj);
                //stu_data.add(obj);
                //daysAdapter.notifyDataSetChanged();
                stu_data=db.Get_list(id);
                daysAdapter= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, stu_data);
                ListView lst=findViewById(R.id.lst);
                lst.setAdapter(daysAdapter);

            }
        }
    }
}
