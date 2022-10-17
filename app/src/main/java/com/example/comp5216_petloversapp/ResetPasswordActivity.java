package com.example.comp5216_petloversapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText et_email;
    private Button btn_reset;
    private Button btn_return;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        toolbar = findViewById(R.id.toolbar);

        et_email = findViewById(R.id.et_email);
        btn_reset = findViewById(R.id.btn_reset);
        btn_return = findViewById(R.id.btn_return);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        toolbar.setTitle("Forgot password");

        btn_return.setOnClickListener(v -> {
            startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
            finish();
        });

        btn_reset.setOnClickListener(view -> {
            String email = et_email.getText().toString();
            if (TextUtils.isEmpty(email) &&
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                et_email.setError("Please enter correct email");
                Toast.makeText(ResetPasswordActivity.this,
                        "Please enter correct the email",
                        Toast.LENGTH_LONG).show();
            } else {
                progressDialog.setMessage("Please wait while sending email...");
                progressDialog.setTitle("Reset Password");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                mAuth.sendPasswordResetEmail(et_email.getText().toString())
                        .addOnCompleteListener(task -> {
                            progressDialog.dismiss();

                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this,
                                        "Mail has been sent, please check your inbox!",
                                        Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ResetPasswordActivity.this,
                                        LoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(ResetPasswordActivity.this,
                                        task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}