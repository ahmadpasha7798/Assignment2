package com.example.homework_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class StaffApplicationViewer extends AppCompatActivity {

    String[] strarr = {"A", "B", "C"};

    int chk(String a)
    {
        for(int i=0;i<3;i++)
        {
            if(a.equals(strarr[i])) {
                return i ;
            }
        }
        return 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_application_viewer);

        final EditText S_date = findViewById(R.id.Date);
        final Intent intent = getIntent();
        int n = intent.getIntExtra("num", 0);
        final TextView num = findViewById(R.id.Application);
        num.setText(Integer.toString(n));
        S_date.setText(intent.getStringExtra("Date"));
        S_date.setEnabled(false);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, strarr);
        final Spinner C_sec = findViewById(R.id.C_sec);
        C_sec.setAdapter(adapter);
        C_sec.setSelection(chk(intent.getStringExtra("C_sec")));
        C_sec.setEnabled(false);

        final Spinner N_sec = findViewById(R.id.N_sec);
        N_sec.setAdapter(adapter);
        N_sec.setSelection(chk(intent.getStringExtra("N_sec")));
        N_sec.setEnabled(false);

        final EditText Description = findViewById(R.id.Description);
        final EditText Roll_no = findViewById(R.id.Roll_no);
        Description.setText( intent.getStringExtra("Reason"));
        Roll_no.setText(intent.getStringExtra("Roll_no"));
        Description.setEnabled(false);
        Roll_no.setEnabled(false);
        final EditText comment=findViewById(R.id.comment_txt);
        comment.setText(intent.getStringExtra("comment"));
        Button approvebtn = findViewById(R.id.submit);
        approvebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent act = new Intent();
                    act.putExtra("Roll_no", Roll_no.getText().toString());
                    act.putExtra("Date", S_date.getText().toString());
                    act.putExtra("C_sec", C_sec.getSelectedItem().toString());
                    act.putExtra("N_sec", N_sec.getSelectedItem().toString());
                    act.putExtra("Reason", Description.getText().toString());
                    act.putExtra("comment",comment.getText().toString());
                    act.putExtra("status","Approved");
                    setResult(RESULT_OK, act);
                    finish();
            }
        });

        Button Disapprove = findViewById(R.id.Disapprove);
        Disapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent act = new Intent();
                act.putExtra("Roll_no", Roll_no.getText().toString());
                act.putExtra("Date", S_date.getText().toString());
                act.putExtra("C_sec", C_sec.getSelectedItem().toString());
                act.putExtra("N_sec", N_sec.getSelectedItem().toString());
                act.putExtra("Reason", Description.getText().toString());
                act.putExtra("comment",comment.getText().toString());
                act.putExtra("status","Disapproved");
                setResult(RESULT_OK, act);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent act = new Intent();
        setResult(RESULT_CANCELED, act);
        finish();
    }
}
