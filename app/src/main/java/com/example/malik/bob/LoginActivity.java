package com.example.malik.bob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malik.bob.DB.UserDB;
import com.example.malik.bob.Objects.User;

public class LoginActivity extends AppCompatActivity {
    EditText mail,password;
    Button login;
    UserDB userDb;
    TextView newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mail=(EditText)findViewById(R.id.input_email);
        password=(EditText)findViewById(R.id.input_password);
        login=(Button)findViewById(R.id.login);
        newUser=(TextView)findViewById(R.id.register);


        userDb=UserDB.get();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=mail.getText().toString();
                String pass=password.getText().toString();
                if(userDb.authenticate(new User(name,pass))){
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("name",name);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,"authentication failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


    }
}
