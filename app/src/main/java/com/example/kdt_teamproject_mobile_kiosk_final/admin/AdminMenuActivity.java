package com.example.kdt_teamproject_mobile_kiosk_final.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kdt_teamproject_mobile_kiosk_final.ModeSelectActivity;
import com.example.kdt_teamproject_mobile_kiosk_final.R;
import com.example.kdt_teamproject_mobile_kiosk_final.admin.branch_management.Branch_Management;
import com.example.kdt_teamproject_mobile_kiosk_final.admin.menu_management.AddMenuMainActivity;
import com.example.kdt_teamproject_mobile_kiosk_final.admin.orderList.OrderListActivity;
import com.example.kdt_teamproject_mobile_kiosk_final.model.EnterpriseUserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminMenuActivity extends AppCompatActivity {

    private ActionBar actionBar;
    Toolbar toolbar;
    NavigationView navigation_view;
    DrawerLayout main_drawer_layout;
    TextView loginInfo, home, orderList, menuManagement, storeManagement, settings, editUserInfo, qrCreate;
    EnterpriseUserAccount userAccount;
    String emailID, userName;
    Button btnLogout;
    boolean onOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        userAccount = LoginActivity.userAccount; // LoginActivity 클래스에서 생성한 객체 userAccount에 넣은 데이터를 userAccount로 가져옴
        emailID = userAccount.getEpEmailID(); // 위에서 담은 userAccount에서 epEmailID만 가져와 emailID에 넣음

        FirebaseFirestore firestore = FirebaseFirestore.getInstance(); // 파이어베이스의 파이어스토어를 사용하기 위한 firestore 객체 선언
        DocumentReference reference = firestore.collection("Enterprise_Users").document(emailID);

        onOff = true;

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        actionBar = getSupportActionBar();
//        // 툴바 활성화
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        // 햄버거 버튼 이미지 불러오기
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
//        // 툴바에 적힐 제목
////        actionBar.setTitle("Mobile KIOSK");
//        actionBar.setHomeButtonEnabled(true);

        // 로그인 정보
        loginInfo = findViewById(R.id.loginInfo);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() { // reference의 데이터를 가져오고, 가져오기가 성공할 때 리스너(인터페이스)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) { // 성공하면
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult(); // DocumentSnapshot은 Document를 읽어오는 클래스
                    userName = snapshot.get("User_Name").toString(); // snapshot 객체 하위 User_Name 키의 값을 toString으로 반환

                    loginInfo.setText(String.format("안녕하세요.\n%s님", userName));
                }
            }
        });
        loginInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOff) {
                    btnLogout.setVisibility(View.VISIBLE);
                    onOff = false;
                } else {
                    btnLogout.setVisibility(View.GONE);
                    onOff = true;
                }
            }
        });


        // 로그아웃 버튼
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() { // btnLogout 클릭 시
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(); // FirebaseAuth는 파이어베이스 Authentication을 사용하기 위한 클래스
                firebaseAuth.signOut(); // 로그아웃 메서드
                startActivity(new Intent(AdminMenuActivity.this, LoginActivity.class)); // AdminMainActivity에서 LoginActivity로 전환
                Toast.makeText(AdminMenuActivity.this, "로그아웃 되었어요. 이용해주셔서 고맙습니다", Toast.LENGTH_SHORT).show(); // 토스트 출력
            }
        });

        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMenuActivity.this, ModeSelectActivity.class);
                startActivity(intent);
                Toast.makeText(AdminMenuActivity.this, "홈으로 이동합니다", Toast.LENGTH_SHORT).show();
            }
        });

        // 주문내역
        orderList = findViewById(R.id.orderList);
        orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentView(1);
            }
        });

        // 메뉴관리
        menuManagement = findViewById(R.id.menuManagement);
        menuManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentView(2);
            }
        });


        // 지점관리
        storeManagement = findViewById(R.id.storeManagement);
        storeManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentView(3);
            }
        });

        // 회원정보 수정
        editUserInfo = findViewById(R.id.editUserInfo);
        editUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentView(5);
            }
        });

        // QR코드 생성
//        qrCreate = findViewById(R.id.qrCreate);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Toast.makeText(AdminMenuActivity.this, "햄버거 버튼 누름", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fragmentView(int i) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (i) {
            case 1:
                OrderListActivity orderList = new OrderListActivity();
                transaction.replace(R.id.fragment_container, orderList);
                transaction.commit();
                break;
            case 2:
//                AddMenuMainActivity menuMainActivity = new AddMenuMainActivity();
//                transaction.replace(R.id.fragment_container, menuMainActivity);
//                transaction.commit();
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                        .add(R.id.fragment_container, AddMenuMainActivity.class, null).commit();
                break;
            case 3:
//                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
//                        .add(R.id.fragment_container, Branch_Management.class, null).commit();
                Branch_Management branch_management = new Branch_Management();
                transaction.replace(R.id.fragment_container, branch_management);
                transaction.commit();
                break;
            case 4:
                break;
            case 5:
                EditAccountActivity editUserInfo = new EditAccountActivity();
                transaction.replace(R.id.fragment_container, editUserInfo);
                transaction.commit();
                break;
            case 6:
                break;

        }
    }

}