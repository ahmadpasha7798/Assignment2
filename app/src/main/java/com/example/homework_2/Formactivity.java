package com.example.homework_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Formactivity extends AppCompatActivity {
    String[] strarr={"A","B","C"};
    String C_Selected="A";
    String N_Selected="A";
    private Calendar calender;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formactivity);
        final TextView S_date=findViewById(R.id.Date);
        calender = calender.getInstance();
        dateFormat=new SimpleDateFormat("dd/MM/YYYY");
        S_date.setText(dateFormat.format(calender.getTime()));

        ArrayAdapter adapter=new ArrayAdapter<String>(Formactivity.this, R.layout.support_simple_spinner_dropdown_item, strarr);
        final Spinner C_sec=findViewById(R.id.C_sec);
        C_sec.setAdapter(adapter);
        C_sec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                C_Selected =strarr[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                C_Selected=strarr[1];
            }
        });

        final Spinner N_sec=findViewById(R.id.N_sec);
        N_sec.setAdapter(adapter);
        N_sec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                N_Selected =strarr[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                N_Selected=strarr[1];
            }
        });

        final TextView num=findViewById(R.id.Application);
        final Intent intent=getIntent();
        int n=intent.getIntExtra("num", 0);
        num.setText(Integer.toString(n));
        Button btn=findViewById(R.id.Submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Description=findViewById(R.id.Description);
                EditText Roll_no=findViewById(R.id.Roll_no);
                if(Roll_no.getText().toString().isEmpty())
                {
                    Toast.makeText(Formactivity.this, "Enter Roll Number!", Toast.LENGTH_SHORT).show();
                    Roll_no.setError("Enter Roll No.!");
                }
                else if(C_Selected==N_Selected)
                {
                    Toast.makeText(Formactivity.this, "Please Select Valid Section!", Toast.LENGTH_SHORT).show();
                    N_sec.setPrompt("Select Different Section!");
                    C_sec.setPrompt("Select Different Section!");
                }
                else if(Description.getText().toString().isEmpty())
                {
                    Toast.makeText(Formactivity.this, "Enter Description!", Toast.LENGTH_SHORT).show();
                    Description.setError("Enter Description Here!");
                }
                else
                {
                    Intent act=new Intent();
                    act.putExtra("Roll_no", Roll_no.getText().toString());
                    act.putExtra("Date", S_date.getText().toString());
                    act.putExtra("C_sec", C_Selected);
                    act.putExtra("N_sec", N_Selected);
                    act.putExtra("Reason", Description.getText().toString());
                    setResult(RESULT_OK,act);
                    finish();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent act=new Intent();
        setResult(RESULT_CANCELED,act);
        finish();
    }


}