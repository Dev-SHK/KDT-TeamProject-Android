package com.example.kdt_teamproject_mobile_kiosk_final.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kdt_teamproject_mobile_kiosk_final.ModeSelectActivity;
import com.example.kdt_teamproject_mobile_kiosk_final.R;
import com.example.kdt_teamproject_mobile_kiosk_final.admin.branch_management.Branch_Management;
import com.example.kdt_teamproject_mobile_kiosk_final.admin.menu_management.AddMenuMain;
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
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        userAccount = LoginActivity.userAccount; // LoginActivity 클래스에서 생성한 객체 userAccount에 넣은 데이터를 userAccount로 가져옴
        emailID = userAccount.getEpEmailID(); // 위에서 담은 userAccount에서 epEmailID만 가져와 emailID에 넣음

        FirebaseFirestore firestore = FirebaseFirestore.getInstance(); // 파이어베이스의 파이어스토어를 사용하기 위한 firestore 객체 선언
        DocumentReference reference = firestore.collection("Enterprise_Users").document(emailID);

        onOff = true;

        mContext = getBaseContext();

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
    }

//    public void checkSelfPermission() {
//        String temp = "";
//
//        // 파일 읽기 권한 확인
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
//        }
//
//        // 파일 쓰기 권한 확인
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
//        }
//
//        if (!TextUtils.isEmpty(temp)) {
//            //권한 요청
//            ActivityCompat.requestPermissions(this, temp.trim().split(" "), 1);
//        } else {
//            //모두 허용 상태
//            Toast.makeText(this, "권한을 모두 허용합니다.", Toast.LENGTH_SHORT).show();
//        }
//    }

    //권한에 대한 응답이 있을때 작동하는 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //권한을 허용 했을 경우
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // 동의
                    Log.d("MainActivity", "권한 허용 : " + permissions[i]);
                }
            }
        }
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
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                        .add(R.id.fragment_container, AddMenuMain.class, null).commit();
                break;
            case 3:
                Branch_Management branch_management = new Branch_Management();
                transaction.replace(R.id.fragment_container, branch_management);
                transaction.commit();
                break;
            case 4:
                break;
            case 5:
                EditAccount editUserInfo = new EditAccount();
                transaction.replace(R.id.fragment_container, editUserInfo);
                transaction.commit();
                break;
            case 6:
                break;
        }
    }

}