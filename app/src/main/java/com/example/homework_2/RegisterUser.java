package com.example.homework_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterUser extends AppCompatActivity {

    DBHelper db=new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        final EditText id=findViewById(R.id.id_txt);
        final EditText pwd=findViewById(R.id.password_txt);
        final EditText pwd2=findViewById(R.id.password_txt2);
        Button btn= findViewById(R.id.log_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pwd.getText().toString().equals(pwd2.getText().toString())) {
                    if (db.chk_user(id.getText().toString())) {
                        if (!db.chk_account(id.getText().toString())) {
                            String role;
                            if(db.chk_user_stu(id.getText().toString()))
                            {
                                role="Student";
                            }
                            else{
                                role=db.getDesignation(id.getText().toString());
                            }
                            db.insert_user(id.getText().toString(),pwd.getText().toString(),role);
                            //Intent intent = new Intent(RegisterUser.this, Login_Activity.class);
                            //startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterUser.this, "Already Registered!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterUser.this, "Unidentified ID!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(RegisterUser.this, "Enter Same Password in both boxes!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}