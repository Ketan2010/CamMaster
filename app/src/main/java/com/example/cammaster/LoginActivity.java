package com.example.cammaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    TextView createhere, forgetpassword;
    Button loginButton;
    EditText inputUsername, inputPassword;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        createhere=findViewById(R.id.createHere);
        createhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        forgetpassword=findViewById(R.id.forgetPassword);
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CodeValidationActivity.class));
            }
        });

        loginButton=findViewById(R.id.loginButton);
        inputPassword=findViewById(R.id.inputPassword);
        inputUsername=findViewById(R.id.inputUsername);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });
    }

    private void checkCredentials() {
        String username=inputUsername.getText().toString();
        String password=inputPassword.getText().toString();

        if(username.isEmpty()) {
            showError(inputUsername, "Field can't be empty");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            showError(inputUsername, "Username is not valid");
        }
        else if(password.isEmpty()) {
            showError(inputPassword, "Field can't be empty");
        }
        else if(!PASSWORD_PATTERN.matcher(password).matches()) {
            showError(inputPassword, "Password is too weak");
        }
        else {
//            Toast.makeText(this, "Call_Register_Method", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LoginActivity.this, GalleryActivity.class);
            startActivity(i);
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}