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

public class RegisterActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");

    TextView loginHere;
    Button registerButton;
    private EditText inputUsername, inputPassword, inputConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        loginHere=findViewById(R.id.loginHere);
        registerButton=findViewById(R.id.loginButton);
        inputUsername=findViewById(R.id.inputUsername);
        inputPassword=findViewById(R.id.inputPassword);
        inputConfirmPassword=findViewById(R.id.inputConfirmPassword);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });

        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void checkCredentials() {
        String username=inputUsername.getText().toString();
        String password=inputPassword.getText().toString();
        String confirmPassword=inputConfirmPassword.getText().toString();

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
        else if (confirmPassword.isEmpty()){
            showError(inputConfirmPassword, "Field can't be empty");
        }
        else if (!confirmPassword.equals(password)){
            showError(inputConfirmPassword, "Password does not match");
        }
        else {
            Toast.makeText(this, "Call_Register_Method", Toast.LENGTH_SHORT).show();
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}