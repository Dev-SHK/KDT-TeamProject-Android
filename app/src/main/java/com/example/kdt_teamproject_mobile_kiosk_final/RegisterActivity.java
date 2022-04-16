package com.example.kdt_teamproject_mobile_kiosk_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kdt_teamproject_mobile_kiosk_final.admin.LoginActivity;
import com.example.kdt_teamproject_mobile_kiosk_final.model.EnterpriseUserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText edtTxtEmail, edtTxtPwd, edtTxtConfirmPwd, edtTxtUserName, edtTxtUserBirthday, edtTxtEpName, edtTxtEpRegiNum, edtTxtStartDate, edtTxtPhoneNum, edtTxtPostNum, edtTxtAddress;
    Button btnRegister, btnEpRegisterNumAuth;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String strEmail, strPwd, strConfirmPwd, strUserName, strEpName, strPhoneNum, strAddress, strBirthday, strEpRegiNum, strPostNum, strStartDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firebaseAuth.useAppLanguage(); // 앱 언어 설정

        edtTxtEmail = findViewById(R.id.edtTxtEmail);
        edtTxtPwd = findViewById(R.id.edtTxtPwd);
        edtTxtConfirmPwd = findViewById(R.id.edtTxtConfirmPwd);
        edtTxtUserName = findViewById(R.id.edtTxtUserName);
        edtTxtUserBirthday = findViewById(R.id.edtTxtUserBirthday);
        edtTxtEpName = findViewById(R.id.edtTxtEpName);
        edtTxtEpRegiNum = findViewById(R.id.edtTxtEpRegiNum);
        edtTxtStartDate = findViewById(R.id.edtTxtStartDate);
        edtTxtPhoneNum = findViewById(R.id.edtTxtPhoneNum);
        edtTxtPostNum = findViewById(R.id.edtTxtPostNum);
        edtTxtAddress = findViewById(R.id.edtTxtAddress);

        btnRegister = findViewById(R.id.btnRegister);
        btnEpRegisterNumAuth = findViewById(R.id.btnEpRegisterNumAuth);

        btnEpRegisterNumAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> userData = new HashMap<>();

                strEmail = edtTxtEmail.getText().toString(); // EditText edtTxtEmail에서 불러온 값을 변수 strEmail에 담음
                strPwd = edtTxtPwd.getText().toString();
                strConfirmPwd = edtTxtConfirmPwd.getText().toString();
                strUserName = edtTxtUserName.getText().toString();
                strBirthday = edtTxtUserBirthday.getText().toString();
                strEpName = edtTxtEpName.getText().toString();
                strEpRegiNum = edtTxtEpRegiNum.getText().toString();
                strStartDate = edtTxtStartDate.getText().toString();
                strPhoneNum = edtTxtPhoneNum.getText().toString();
                strPostNum = edtTxtPostNum.getText().toString();
                strAddress = edtTxtAddress.getText().toString();

                if (!strPwd.equals(strConfirmPwd)) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 맞지 않아요", Toast.LENGTH_SHORT).show();
                    edtTxtPwd.setText("");
                    edtTxtConfirmPwd.setText("");
                } else {
                    EnterpriseUserAccount userAccount = new EnterpriseUserAccount(); // DTO 역할을 하는 객체 생성 후
                    userAccount.setEpEmailID(strEmail); // DTO userAccount 객체의 epEmailID에 값을 넘겨 줌
                    userAccount.setEpPwd(strPwd);
                    userAccount.setEpUserName(strUserName);
                    userAccount.setEpUserBirthday(strBirthday);
                    userAccount.setEpCompanyName(strEpName);
                    userAccount.setEpRegisterNum(strEpRegiNum);
                    userAccount.setEpStartDate(strStartDate);
                    userAccount.setEpPhoneNum(strPhoneNum);
                    userAccount.setEpPostNum(strPostNum);
                    userAccount.setEdtTxtAddress(strAddress);

                    if (isValidEmail() && isValidPwd() && isValidUserName() && isValidUserBirthday() && isValidUserEpName() && isValidEpRegiNum() && isValidStartDate() && isValidPhoneNum() && isValidPostNum() && isValidAddress()) {
                        // 파이어베이스의 Authentication을 이용하여 회원가입을 하는 메서드 (아이디와 비밀번호를 처리하기 위해 값을 2개 넣어 줌)
                        firebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) { // 성공 시
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "회원가입에 성공했어요", Toast.LENGTH_SHORT).show(); // 성공 토스트 메시지를 띄움

                                    userData.put("Email_ID", userAccount.getEpEmailID()); // userAccount 객체로 넘긴 데이터를 가져와서 Map userData에 키와 값으로 입력
                                    userData.put("User_Name", userAccount.getEpUserName()); // 파이어스토어는 키와 값으로 이루어져 있으므로 데이터를 넘겨줄 때는 키와 값으로 구성되어야 함
                                    userData.put("User_Birthday", userAccount.getEpUserBirthday());
                                    userData.put("Enterprise_Name", userAccount.getEpCompanyName());
                                    userData.put("Register_Number", userAccount.getEpRegisterNum());
                                    userData.put("Start_Date", userAccount.getEpStartDate());
                                    userData.put("Phone_Number", userAccount.getEpPhoneNum());
                                    userData.put("Post_Number", userAccount.getEpPostNum());
                                    userData.put("Address", userAccount.getEdtTxtAddress());

                                    emailAuth();

                                    // 파이어스토어의 컬렉션 하위 도큐먼츠에 userData를 입력하는 메서드와 리스너
                                    firestore.collection("Enterprise_Users").document(strEmail).set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(RegisterActivity.this, "데이터가 입력되었어요", Toast.LENGTH_SHORT).show();

                                        }
                                    });


                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "회원가입에 실패했어요", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void emailAuth() {
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(RegisterActivity.this, firebaseUser.getEmail() + "로 인증 이메일을 보냈습니다", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(RegisterActivity.this, "인증 이메일을 보내지 못했어요", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean isValidEmail() {
        if (strEmail.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "이메일을 적어주세요", Toast.LENGTH_SHORT).show();
            edtTxtEmail.setFocusable(true);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidPwd() {
        if (strPwd.isEmpty() || strConfirmPwd.isEmpty()) {
            Toast.makeText(getApplicationContext(), "비밀번호를 적어주세요", Toast.LENGTH_SHORT).show();
            edtTxtPwd.setFocusable(true);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidUserName() {
        if (strUserName.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "대표자명을 적어주세요", Toast.LENGTH_SHORT).show();
            edtTxtUserName.setFocusable(true);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidUserBirthday() {
        if (strBirthday.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "생년월일을 적어주세요", Toast.LENGTH_SHORT).show();
            edtTxtUserBirthday.setFocusable(true);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidUserEpName() {
        if (strEpName.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "상호명을 적어주세요", Toast.LENGTH_SHORT).show();
            edtTxtEpName.setFocusable(true);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidEpRegiNum() {
        if (strEpRegiNum.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "사업자번호를 적어주세요", Toast.LENGTH_SHORT).show();
            edtTxtEpRegiNum.setFocusable(true);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidStartDate() {
        if (strStartDate.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "사업자번호를 적어주세요", Toast.LENGTH_SHORT).show();
            edtTxtStartDate.setFocusable(true);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidPhoneNum() {
        if (strPhoneNum.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "연락처를 적어주세요", Toast.LENGTH_SHORT).show();
            edtTxtPhoneNum.setFocusable(true);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidPostNum() {
        if (strPostNum.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "우편번호를 적어주세요", Toast.LENGTH_SHORT).show();
            edtTxtPostNum.setFocusable(true);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidAddress() {
        if (strAddress.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "주소를 적어주세요", Toast.LENGTH_SHORT).show();
            edtTxtAddress.setFocusable(true);
            return false;
        } else {
            return true;
        }
    }
}