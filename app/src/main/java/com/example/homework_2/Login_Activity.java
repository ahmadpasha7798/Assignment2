package com.example.homework_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login_Activity extends AppCompatActivity {

    DBHelper db=new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        final EditText username=findViewById(R.id.id_txt);
        final EditText pwd=findViewById(R.id.password_txt);
        Button btn= findViewById(R.id.log_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.authorize(username.getText().toString(),pwd.getText().toString()))
                {
                    Intent intent;
                    String role=db.getRole(username.getText().toString());
                    if(role.equals("Student"))
                        intent=new Intent(Login_Activity.this,MainActivity.class);
                    else
                        intent=new Intent(Login_Activity.this,StaffList.class);
                    intent.putExtra("Username",username.getText().toString());
                    startActivity(intent);
                    finish();

                }
                else{
                    Toast.makeText(Login_Activity.this,"User id or password is wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView not_reg=findViewById(R.id.not_registered);
        not_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login_Activity.this,RegisterUser.class);
                startActivity(intent);
            }
        });
    }
}