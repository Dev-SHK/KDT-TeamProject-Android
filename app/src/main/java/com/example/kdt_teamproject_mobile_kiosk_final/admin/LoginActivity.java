package com.example.kdt_teamproject_mobile_kiosk_final.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kdt_teamproject_mobile_kiosk_final.ModeSelectActivity;
import com.example.kdt_teamproject_mobile_kiosk_final.R;
import com.example.kdt_teamproject_mobile_kiosk_final.RegisterActivity;
import com.example.kdt_teamproject_mobile_kiosk_final.model.EnterpriseUserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText edtTxtEmail, edtTxtPwd;
    Button btnLogin, btnRegister;
    String strEmail, strPwd;
    public static EnterpriseUserAccount userAccount; // DTO 역할을 하는 EnterpriseUserAccount 클래스 객체 선언
    FirebaseAuth firebaseAuth; // 파이어베이스 Authentication을 사용하기 위한 클래스
    FirebaseUser firebaseUser; // 파이어베이스 Authentication에서 사용자를 가져오기 위한 클래스


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edtTxtEmail = findViewById(R.id.edtTxtEmail);
        edtTxtPwd = findViewById(R.id.edtTxtPwd);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        firebaseAuth = FirebaseAuth.getInstance(); // firebaseAuth를 초기화
        firebaseUser = firebaseAuth.getCurrentUser(); // 현재 파이어베이스 Authentication을 사용하고 있는 유저를 가져와서 firebaseUser로 담음

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strEmail = edtTxtEmail.getText().toString();
                strPwd = edtTxtPwd.getText().toString();

                userAccount = new EnterpriseUserAccount(); // DTO 역할
                userAccount.setEpEmailID(strEmail); // DTO의 epEmailID에 strEmail 데이터 입력
                userAccount.setEpPwd(strPwd);

                if (isValidEmail() && isValidPwd()) {
                    // 파이어베이스의 Authentication을 이용하여 로그인 처리하는 메서드
                    firebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) { // 성공 시
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "로그인 되었어요", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, ModeSelectActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "로그인 되지 않았어요. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidEmail() {
        if (strEmail.isEmpty()) {
            Toast.makeText(getApplicationContext(), "이메일을 적어주세요", Toast.LENGTH_SHORT).show();
            edtTxtEmail.setFocusable(true);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidPwd() {
        if (strPwd.isEmpty()) {
            Toast.makeText(getApplicationContext(), "비밀번호를 적어주세요", Toast.LENGTH_SHORT).show();
            edtTxtPwd.setFocusable(true);
            return false;
        } else {
            return true;
        }
    }
}