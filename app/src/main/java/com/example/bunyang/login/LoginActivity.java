package com.example.bunyang.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bunyang.MainActivity;
import com.example.bunyang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    Button button, sign;
    public static AppCompatActivity loginActivity;
    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시단 데이터베이스
    private EditText mEtEmail, mEtpwd;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.context = this;

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("bunyang");

        mEtEmail = findViewById(R.id.email_edit);
        mEtpwd = findViewById(R.id.password_edit);

        loginActivity = LoginActivity.this;

        button = (Button) findViewById(R.id.login);
        sign = (Button) findViewById(R.id.sign);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //이메일 요청
                String strPwd = mEtpwd.getText().toString();
                String strEmail = mEtEmail.getText().toString();
//                String strEmail = "123@naver.com";
//                String strPwd = "11111111";
                if ("".equals(strEmail) || "".equals(strPwd)) {
                    Toast.makeText(LoginActivity.this, "이메일과 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //로그인 성공
                            mDatabaseRef.child("User").child(mFirebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Log.e("asd", "" + snapshot);
                                    String name = "";
                                    String emailId = "";
                                    for (DataSnapshot item : snapshot.getChildren()) {
                                        String str = "" + item.getValue();
                                        if (item.getKey().equals("name")) {
                                            name = (String) item.getValue();
                                        }
                                        if (item.getKey().equals("emailId")) {
                                            emailId = (String) item.getValue();
                                        }

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("name", "" + name);
                                        intent.putExtra("emailId", "" + emailId);
                                        startActivity(intent);
                                        finish(); // 액티비티 파괴
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            Toast.makeText(LoginActivity.this, "로그인성공", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "로그인실패", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignActivity.class);
                startActivity(intent);
            }
        });


    }


}

