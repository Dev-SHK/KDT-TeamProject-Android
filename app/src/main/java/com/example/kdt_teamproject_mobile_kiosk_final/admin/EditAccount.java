package com.example.kdt_teamproject_mobile_kiosk_final.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kdt_teamproject_mobile_kiosk_final.R;
import com.example.kdt_teamproject_mobile_kiosk_final.model.EnterpriseUserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditAccount extends Fragment {

    EditText edtTxtEmail, edtTxtPwd, edtTxtConfirmPwd, edtTxtUserName, edtTxtUserBirthday, edtTxtEpName, edtTxtEpRegiNum, edtTxtPhoneNum, edtTxtPostNum, edtTxtAddress, edtTxtStartDate;
    Button btnSubmit, btnLeave;
    String editUserName, editEpName, editPhoneNum, editAddress, editUserBirthday, editRegiNum, editPostNum, editStartDate, matchPwd, matchRegiNum;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_account);
        View v = inflater.inflate(R.layout.activity_edit_account, container, false);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance(); // 파이어베이스의 파이어스토어를 쓰기 위해 firestore를 생성
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // 파이어베이스의 Authentication 안에 현재 사용자를 가져오기 위한 코드

        edtTxtEmail = v.findViewById(R.id.edtTxtEmail);
        edtTxtPwd = v.findViewById(R.id.edtTxtPwd);
        edtTxtConfirmPwd = v.findViewById(R.id.edtTxtConfirmPwd);
        edtTxtUserName = v.findViewById(R.id.edtTxtUserName);
        edtTxtUserBirthday = v.findViewById(R.id.edtTxtUserBirthday);
        edtTxtEpName = v.findViewById(R.id.edtTxtEpName);
        edtTxtEpRegiNum = v.findViewById(R.id.edtTxtEpRegiNum);
        edtTxtStartDate = v.findViewById(R.id.edtTxtStartDate);
        edtTxtPhoneNum = v.findViewById(R.id.edtTxtPhoneNum);
        edtTxtPostNum = v.findViewById(R.id.edtTxtPostNum);
        edtTxtAddress = v.findViewById(R.id.edtTxtAddress);

        btnSubmit = v.findViewById(R.id.btnSubmit);
        btnLeave = v.findViewById(R.id.btnLeave);

        EnterpriseUserAccount userAccount = LoginActivity.userAccount;
        String emailId = userAccount.getEpEmailID();
        String pwd = userAccount.getEpPwd();

        DocumentReference documentReference = firestore.collection("Enterprise_Users").document(emailId);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();

                    editUserName = snapshot.get("User_Name").toString();
                    editUserBirthday = snapshot.get("User_Birthday").toString();
                    editEpName = snapshot.get("Enterprise_Name").toString();
                    editRegiNum = snapshot.get("Register_Number").toString();
                    editStartDate = snapshot.get("Start_Date").toString();
                    editPhoneNum = snapshot.get("Phone_Number").toString();
                    editPostNum = snapshot.get("Post_Number").toString();
                    editAddress = snapshot.get("Address").toString();


                    edtTxtEmail.setText(emailId); // EditText edtTxtEmail에 값 입력
                    edtTxtPwd.setText(pwd);
                    edtTxtConfirmPwd.setText(pwd);
                    edtTxtUserName.setText(editUserName);
                    edtTxtUserBirthday.setText(editUserBirthday);
                    edtTxtEpName.setText(editEpName);
                    edtTxtEpRegiNum.setText(editRegiNum);
                    edtTxtStartDate.setText(editStartDate);
                    edtTxtPhoneNum.setText(editPhoneNum);
                    edtTxtPostNum.setText(editPostNum);
                    edtTxtAddress.setText(editAddress);
                    Toast.makeText(getContext(), "데이터를 가져왔어요", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "데이터를 가져올 수 없어요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> userData = new HashMap<>(); // 파이어스토어는 키와 값으로 데이터를 입력해야 하기 때문에 Map을 사용하여 키와 값을 입력
                userData.put("Email_ID", emailId); // userData에 키와 값을 입력
                userData.put("User_Name", edtTxtUserName.getText().toString());
                userData.put("Enterprise_Name", edtTxtEpName.getText().toString());
                userData.put("User_Birthday", Integer.parseInt(edtTxtUserBirthday.getText().toString()));
                userData.put("Register_Number", Integer.parseInt(edtTxtEpRegiNum.getText().toString()));
                userData.put("Start_Date", Integer.parseInt(edtTxtStartDate.getText().toString()));
                userData.put("Phone_Number", edtTxtPhoneNum.getText().toString());
                userData.put("Post_Number", Integer.parseInt(edtTxtPostNum.getText().toString()));
                userData.put("Address", edtTxtAddress.getText().toString());


                // 파이어스토어의 컬렉션 하위 도큐먼츠에 userData 데이터를 넣음
                firestore.collection("Enterprise_Users").document(emailId).set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) { // 성공 시
                        Toast.makeText(getContext(), "데이터가 입력되었어요", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(getContext(), AdminMainActivity.class));
                    }
                });

            }
        });

        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popupV = getLayoutInflater().inflate(R.layout.activity_membership_withdrawal, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


                Button btnWithdrawal = popupV.findViewById(R.id.btnWithdrawal);
                btnWithdrawal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText etRegiNum = popupV.findViewById(R.id.etRegiNum);
                        EditText etPwd = popupV.findViewById(R.id.etPwd);

                        matchRegiNum = etRegiNum.getText().toString();
                        matchPwd = etPwd.getText().toString();
                        if (isValidEpRegiNum() && isValidEpPwd()) {
                            if (editRegiNum.equals(matchRegiNum) && pwd.equals(matchPwd)) {
                                // 파이어스토어의 컬렉션 하위 도큐먼츠의 데이터를 삭제
                                firestore.collection("Enterprise_Users").document(emailId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) { // 성공 시
                                        Toast.makeText(getContext(), "회원 데이터가 정상적으로 삭제되었습니다", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                // 파이어베이스의 Authentication에 있는 유저를 삭제
                                firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) { // 성공 시
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "회원 탈퇴가 정상적으로 이루어졌습니다", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getContext(), LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getContext(), "입력한 내용이 일치하지 않아요. 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builder.setView(popupV);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return v;
    }


    private boolean isValidEpRegiNum() {
        if (matchRegiNum.isEmpty()) {
            Toast.makeText(getContext(), "사업자번호를 적어주세요", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidEpPwd() {
        if (matchPwd.isEmpty()) {
            Toast.makeText(getContext(), "비밀번호를 적어주세요", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}