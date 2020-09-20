package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserSignInActivity extends AppCompatActivity {
    private TextView userType;
    private EditText userEmailEt, userPasswordEt, confirmPasswordEt;
    private Button signInBt;
    private ProgressBar progressBar;
    private String type;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in);
        inItView();
        Intent intent = getIntent();
        type = intent.getStringExtra("user");
        if (type.equals("admin")) {
            userType.setText("Admin");
        } else if (type.equals("admin")) {
            userType.setText("Employee");
        }
        clickEvents();
    }

    private void clickEvents() {
        if (type.equals("admin")) {
            signInBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String email = userEmailEt.getText().toString().trim();
                    String password = userPasswordEt.getText().toString().trim();
                    String confirmPassword = confirmPasswordEt.getText().toString().trim();

                    if (email.isEmpty()) {
                        Toast.makeText(UserSignInActivity.this, "Email is required!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (password.isEmpty()) {
                        Toast.makeText(UserSignInActivity.this, "Password is Required!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (confirmPassword.isEmpty()) {
                        Toast.makeText(UserSignInActivity.this, "Please confirm password!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (password.equals(confirmPassword)) {
                        progressBar.setVisibility(View.VISIBLE);

                        if(email.equalsIgnoreCase("admin@gmail.com")){
                            firebaseAuth.signInWithEmailAndPassword(email,password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            progressBar.setVisibility(View.GONE);
                                            if(task.isSuccessful()){
                                                finish();
                                                Intent intent = new Intent(UserSignInActivity.this, AdminControllerActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UserSignInActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }///admin condition if close
                    }//confirm condition if close
                }///viewClick
            });//listenner
        }//if close1
    }///clickEvents


    private void inItView() {
        userType = findViewById(R.id.demoUser);
        userEmailEt = findViewById(R.id.etUserEmail);
        userPasswordEt = findViewById(R.id.etUserPassword);
        confirmPasswordEt = findViewById(R.id.etConfirmPassword);
        signInBt = findViewById(R.id.btnSignIn);
        progressBar = findViewById(R.id.progressBar);
    }
}