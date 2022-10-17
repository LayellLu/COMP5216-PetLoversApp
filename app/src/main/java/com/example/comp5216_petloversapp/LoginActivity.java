package com.example.comp5216_petloversapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email;
    private EditText et_password;
    private Button btn_login;
    private Button btn_register;
    private Button btn_forgot;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_forgot = findViewById(R.id.btn_forgot);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(v -> {
            String email = et_email.getText().toString();
            String password = et_password.getText().toString();

            if (TextUtils.isEmpty(email) &&
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                et_email.setError("Please enter correct email");
            } else if (TextUtils.isEmpty(password)) {
                et_password.setError("Please enter proper password");
            } else {
                progressDialog.setMessage("Please wait while login...");
                progressDialog.setTitle("Login");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                loginUser(email, password);
            }
        });

        btn_register.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });

        btn_forgot.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            finish();
        });
    }

    // Sign in with email and password
    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            progressDialog.dismiss();

            if (!task.isSuccessful()) {
                Log.w("TAG", "signInWithEmail:failed", task.getException());
            } else {
                checkIfEmailVerified();
            }
        });
    }

    // Check whether the account has been activated
    private void checkIfEmailVerified() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user.isEmailVerified()) {
            Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Sorry, this account has not been activated!",
                    Toast.LENGTH_LONG).show();

            // Sign out
            FirebaseAuth.getInstance().signOut();

            overridePendingTransition(0, 0);
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
        }
    }
}