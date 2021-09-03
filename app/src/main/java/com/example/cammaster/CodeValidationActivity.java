package com.example.cammaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class CodeValidationActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");

    EditText inputUsername;
    Button verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_validation);

        verifyButton=findViewById(R.id.loginButton);
        inputUsername=findViewById(R.id.inputUsername);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCredentials())
                {
                    startActivity(new Intent(CodeValidationActivity.this, CodeVerifcationActivity.class));
                }
            }
        });
    }

    private boolean checkCredentials() {
        String username=inputUsername.getText().toString();

        if(username.isEmpty()) {
            showError(inputUsername, "Field can't be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            showError(inputUsername, "Username is not valid");
            return false;
        }
        else {
            return true;
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}