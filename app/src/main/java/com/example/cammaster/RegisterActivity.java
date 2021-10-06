package com.example.cammaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

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
            createUser(inputUsername, inputPassword);
        }
    }

    private void createUser(EditText email, EditText password){
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userId = user.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    User users = new User(email.getText().toString());
                    mDatabase.child("users").child(userId).setValue(users);
                    Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }else{
                    Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}

