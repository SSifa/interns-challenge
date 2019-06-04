package com.example.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import static android.util.Patterns.EMAIL_ADDRESS;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    private EditText fname, lname, email, pNumber, password, confirmPassword;
    private Button submit, login;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        db = new DatabaseHelper(this);
        fname = findViewById(R.id.F_name);
        lname = findViewById(R.id.L_name);
        email = findViewById(R.id.email);
        pNumber = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        submit = findViewById(R.id.okay);
        login = findViewById(R.id.loginbtn);

        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(MainActivity.this, R.id.F_name, "[a-zA-Z\\s]+", R.string.fnameerr);
        awesomeValidation.addValidation(MainActivity.this, R.id.L_name, "[a-zA-Z\\s]+", R.string.lnameerr);
        awesomeValidation.addValidation(MainActivity.this, R.id.email, EMAIL_ADDRESS, R.string.emailerr);
        awesomeValidation.addValidation(MainActivity.this, R.id.phone_number, RegexTemplate.TELEPHONE, R.string.phoneerr);
        awesomeValidation.addValidation(MainActivity.this, R.id.password, regexPassword, R.string.passerr);
        awesomeValidation.addValidation(MainActivity.this, R.id.confirm_password, R.id.password, R.string.cnfpasserr);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = fname.getText().toString();
                String lName = lname.getText().toString();
                String mail = email.getText().toString();
                int pNum = Integer.parseInt(pNumber.getText().toString());
                String pass = password.getText().toString();

                if (awesomeValidation.validate()){
                    boolean checkmail = db.checkEmail(mail);
                    if (checkmail == true){
                        boolean insert = db.insert(mail, fName, lName, pNum, pass);
                        if (insert == true){
                            Toast.makeText(getApplicationContext(), "Registered successfully!", Toast.LENGTH_SHORT).show();
                            clearFields();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Email address already exists!", Toast.LENGTH_SHORT).show();
                        email.setText(null);
                        email.requestFocus();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Clear all fields after registering
    public void clearFields(){
        fname.setText(null);
        lname.setText(null);
        email.setText(null);
        pNumber.setText(null);
        password.setText(null);
        confirmPassword.setText(null);
    }
}
