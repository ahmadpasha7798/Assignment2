package com.example.homework_2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class StaffList extends AppCompatActivity {
    List<ApplicationData> stu_data=new ArrayList<ApplicationData>();
    ArrayAdapter daysAdapter;
    DBHelper db=new DBHelper(this);
    int index=0;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_list);
        Intent intent=getIntent();
        String id=intent.getStringExtra("Username");
        //getActionBar().setTitle(id);
        role=db.getRole(id);
        stu_data=db.Get_list_staff(role);

        daysAdapter= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, stu_data);
        ListView lst=findViewById(R.id.lst);
        lst.setAdapter(daysAdapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent act=new Intent(StaffList.this , StaffApplicationViewer.class);
                act.putExtra("num", stu_data.get(position).num);
                index=stu_data.get(position).num;
                act.putExtra("Roll_no", stu_data.get(position).Roll_no);
                act.putExtra("Date", stu_data.get(position).S_date);
                act.putExtra("C_sec", stu_data.get(position).C_sec);
                act.putExtra("N_sec", stu_data.get(position).N_sec);
                act.putExtra("Reason", stu_data.get(position).Reason);
                act.putExtra("comment",stu_data.get(position).comment);

                startActivityForResult(act,1);
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
                obj.comment=data.getStringExtra("comment");
                String status=data.getStringExtra("status");
                if(status.equals("Approved")&&role.equals("Coordinator"))
                {
                    obj.status="Under Processing";
                }
                else if(status.equals("Disapproved"))
                {
                    obj.status="Disapproved";
                }
                else
                {
                    obj.status="Approved";
                    db.Update_section(obj.Roll_no,obj.N_sec);
                }
                db.update_application(obj);
                stu_data=db.Get_list_staff(role);
                daysAdapter= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, stu_data);
                ListView lst=findViewById(R.id.lst);
                lst.setAdapter(daysAdapter);
            }
        }

    }
}
