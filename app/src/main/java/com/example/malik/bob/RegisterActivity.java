package com.example.malik.bob;

import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.malik.bob.DB.UserDB;
import com.example.malik.bob.Objects.User;

public class RegisterActivity extends AppCompatActivity {

    EditText name,pass1,pass2,number;
    Button bouton;
    UserDB userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name=(EditText)findViewById(R.id.email);
        pass1=(EditText)findViewById(R.id.pass);
        pass2=(EditText)findViewById(R.id.pass2);
        number=(EditText)findViewById(R.id.number);

        userDB=UserDB.get();

        bouton=(Button)findViewById(R.id.register);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pass1.getText().toString().equals(pass2.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"passwords do not match",Toast.LENGTH_SHORT).show();
                    pass1.setText("");
                    pass2.setText("");
                }else {
                    User user = new User(name.getText().toString(),pass1.getText().toString());
                    user.setNumber(number.getText().toString());
                    userDB.addUser(user);
                    Toast.makeText(RegisterActivity.this,"User registered, you can now login",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
