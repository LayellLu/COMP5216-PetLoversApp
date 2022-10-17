package com.example.comp5216_petloversapp;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_email;
    private EditText et_password;
    private EditText et_confirm;
    private EditText et_username;
    private Button btn_register;
    private Button btn_return;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirm = findViewById(R.id.et_confirm);
        btn_register = findViewById(R.id.btn_register);
        btn_return = findViewById(R.id.btn_return);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btn_register.setOnClickListener(v -> createUser());
        btn_return.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    // Create a user in Firebase Auth
    private void createUser() {
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        String confirm = et_confirm.getText().toString();
        String username = et_username.getText().toString();

        if (TextUtils.isEmpty(email.trim())
                || TextUtils.isEmpty(password.trim())
                || TextUtils.isEmpty(confirm.trim())
                || TextUtils.isEmpty(username.trim())) {
            Toast.makeText(RegisterActivity.this, "Please fill in every content",
                    Toast.LENGTH_LONG).show();
        } else if (password.trim().length() < 6) {
            Toast.makeText(RegisterActivity.this, "The length of the password must over 6",
                    Toast.LENGTH_LONG).show();
        } else if (!TextUtils.equals(password.trim(), confirm.trim())) {
            Toast.makeText(RegisterActivity.this, "Password entries are inconsistent, please re-enter",
                    Toast.LENGTH_LONG).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Please enter correct email!");
            Toast.makeText(RegisterActivity.this, "Please enter correct email",
                    Toast.LENGTH_LONG).show();
        } else {
            progressDialog.setMessage("Please wait while registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            registerUser(username, email, password);
        }
    }

    private void registerUser(String username,
                              String email,
                              String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, task -> {
            progressDialog.dismiss();

            if (task.isSuccessful()) {
                Toast.makeText(this, "Registering user successfully", Toast.LENGTH_SHORT).show();
                userInfo = new UserInfo(email, username);
                Log.d("user", userInfo.toString());

                db.collection("users").document(mAuth.getUid()).set(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User added successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                sendVerificationEmail();
            } else {
                Toast.makeText(this, "Registering failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendVerificationEmail() {
        FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            // Restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                        }
                    }
                });
    }
}