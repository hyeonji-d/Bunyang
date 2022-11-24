package com.example.bunyang.login;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.bunyang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignActivity extends AppCompatActivity {
    private EditText email_join;
    private EditText pwd_join;
    private Button btn;

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시단 데이터베이스
    private EditText mEtEmail, mEtpwd, mEtname; // 회원가입 입력필드
    private Button mBtnRegistr; // 회원가입 버튼

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_sign);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("bunyang");

        mEtEmail = findViewById(R.id.sign_up_email);
        mEtpwd = findViewById(R.id.sign_up_pwd);
        mEtname = findViewById(R.id.sign_up_name);
        mBtnRegistr = findViewById(R.id.sign_up_btn);

        mBtnRegistr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 처리 시작
                String strname = mEtname.getText().toString();
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtpwd.getText().toString();

                if ("".equals(strEmail) || "".equals(strPwd)) {
                    Toast.makeText(SignActivity.this, "이름, 이메일, 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

//파이어베이스 인증
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(SignActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);
                            account.setName(strname);

// stValue() : 데이터베이스 삽입
                            mDatabaseRef.child("User").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(SignActivity.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignActivity.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}
