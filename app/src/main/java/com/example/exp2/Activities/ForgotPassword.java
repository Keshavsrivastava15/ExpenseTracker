package com.example.exp2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exp2.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity{

    private EditText emailEditText;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText=(EditText) findViewById(R.id.email_fog);
        Button resetPass = (Button) findViewById(R.id.reset_fog);
        progressBar=(ProgressBar) findViewById(R.id.progressBar2);
        auth=FirebaseAuth.getInstance();
        TextView banner = (TextView) findViewById(R.id.banner_forgot);
        banner.setOnClickListener(view -> startActivity(new Intent(ForgotPassword.this, LoginActivity.class)));


        resetPass.setOnClickListener(view -> resetPassword());
    }

    private void resetPassword() {
        String email=emailEditText.getText().toString().trim();
        if(email.isEmpty()){
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Enter valid Email Address!");
            emailEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.GONE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(ForgotPassword.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(ForgotPassword.this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }
}