package com.example.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText email, password;
    Button login;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        db = new DatabaseHelper(this);
        email = findViewById(R.id.mail);
        password = findViewById(R.id.passwd);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String pass = password.getText().toString();

                boolean mailPassCheck = db.checkLogin(mail, pass);
                if (mailPassCheck == true)
                    Toast.makeText(getApplicationContext(), "Successfully login", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getApplicationContext(), "Ivalid login credentials.", Toast.LENGTH_SHORT).show();
                    email.setText(null);
                    password.setText(null);
                }
            }
        });
    }
}
