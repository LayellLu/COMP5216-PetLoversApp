package com.example.comp5216_petloversapp;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class EditProfileActivity extends AppCompatActivity {
    private Button bt_save;
    private EditText et_userName;
    private TextView tv_userName;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private String userName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bt_save = findViewById(R.id.bt_save);
        et_userName = findViewById(R.id.edit_user_name);
        tv_userName = findViewById(R.id.userName);
        setUserName();

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditUserName(et_userName.getText().toString());
                tv_userName.setText(setUserName());
                finish();
            }
        });
    }

    private void EditUserName(String username) {

        db.collection("users").document(mAuth.getCurrentUser().getUid()).update("userName", username).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditProfileActivity.this, "User Name Edit Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfileActivity.this, "User Name Edit Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String setUserName() {
        try {
            db.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    userName = task.getResult().getData().get("userName").toString();
                    et_userName.setText(userName);
                }
            });

        }catch (Exception e) {
            System.out.println("No fucking name");
        }

        return userName;
    }

}
