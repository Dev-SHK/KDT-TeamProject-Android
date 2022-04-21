package com.example.kdt_teamproject_mobile_kiosk_final.kiosk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kdt_teamproject_mobile_kiosk_final.R;
import com.example.kdt_teamproject_mobile_kiosk_final.admin.LoginActivity;
import com.example.kdt_teamproject_mobile_kiosk_final.kiosk.payment.QrScan;
import com.example.kdt_teamproject_mobile_kiosk_final.model.DTO_Cart_Download;
import com.example.kdt_teamproject_mobile_kiosk_final.model.EnterpriseUserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KioskMainActivity extends AppCompatActivity {
    ListView orderListLayout;

    Button mainBtn, sideBtn, etcBtn, cancelBtn, orderBtn, qrBtn, creditBtn;
    FirebaseFirestore fb = FirebaseFirestore.getInstance();
    List<DTO_Cart_Download> list = new ArrayList<>();
    Adapter_Cart mAdapter = null;

    View dialogView;

    String img_Path, menuNum, menu_CG, menuName, menuPrice, menuDetail, optKind01, optKind02, optPrice01, optPrice02, optPrice03, optPrice04;
    String optPrice05, optSize01, optSize02, optSize03;
    Boolean stockState;
    String strMain = "메인";
    String strSide = "사이드";
    String strEtc = "기타";

    TextView totalPrice, tvEmpty;
    int sum = 0;
    long total;
    int orderNum = 0;

    EnterpriseUserAccount userAccount = LoginActivity.userAccount;
    String emailId = userAccount.getEpEmailID();

    String payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiosk_main);

        mainBtn = findViewById(R.id.leftTopMain);
        sideBtn = findViewById(R.id.leftTopSide);
        etcBtn = findViewById(R.id.leftTopEtc);
        totalPrice = findViewById(R.id.totalPrice);
        cancelBtn = findViewById(R.id.btn_clear);
        orderBtn = findViewById(R.id.orderBtn);

        mAdapter = new Adapter_Cart(this);
        orderListLayout = (ListView) findViewById(R.id.orderListLayout);
        orderListLayout.setAdapter(mAdapter);

        tvEmpty = findViewById(R.id.tvEmpty);

        orderListLayout.setEmptyView(tvEmpty);

        selectAll(strMain);

        mainBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                selectAll(strMain);
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mainBtn.setBackgroundResource(R.drawable.btn_dialog_selected);
                    mainBtn.setTextColor(Color.WHITE);
                    sideBtn.setBackgroundResource(R.drawable.btn_default);
                    sideBtn.setTextColor(Color.BLACK);
                    etcBtn.setBackgroundResource(R.drawable.btn_default);
                    etcBtn.setTextColor(Color.BLACK);
                }
                return false;
            }
        });


        sideBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                selectAll(strSide);
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mainBtn.setBackgroundResource(R.drawable.btn_default);
                    mainBtn.setTextColor(Color.BLACK);
                    sideBtn.setBackgroundResource(R.drawable.btn_dialog_selected);
                    sideBtn.setTextColor(Color.WHITE);
                    etcBtn.setBackgroundResource(R.drawable.btn_default);
                    etcBtn.setTextColor(Color.BLACK);
                }
                return false;
            }
        });


        etcBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                selectAll(strEtc);
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mainBtn.setBackgroundResource(R.drawable.btn_default);
                    mainBtn.setTextColor(Color.BLACK);
                    sideBtn.setBackgroundResource(R.drawable.btn_default);
                    sideBtn.setTextColor(Color.BLACK);
                    etcBtn.setBackgroundResource(R.drawable.btn_dialog_selected);
                    etcBtn.setTextColor(Color.WHITE);
                }
                return false;
            }
        });

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mAdapter.getCount() == 0) {
                    orderBtn.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "먼저 주문을 해주세요.", Toast.LENGTH_SHORT).show();
                    orderBtn.setEnabled(true);
                } else {

                    dialogView = View.inflate(KioskMainActivity.this, R.layout.activity_paychoice, null);

                    AlertDialog.Builder choiceDlg = new AlertDialog.Builder(KioskMainActivity.this);
                    AlertDialog alertDialog_choice = choiceDlg.create();
                    alertDialog_choice.setView(dialogView);
                    alertDialog_choice.show();


                    qrBtn = dialogView.findViewById(R.id.qrBtn);
                    creditBtn = dialogView.findViewById(R.id.creditBtn);


                    qrBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog_choice.dismiss();

                            payment = "QR결제";
                            Intent intent = new Intent(KioskMainActivity.this, QrScan.class);
                            startActivity(intent);
                        }

                    });

                    creditBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog_choice.dismiss();

                            payment = "신용카드";
                            dialogView = View.inflate(KioskMainActivity.this, R.layout.activity_cardpay, null);

                            AlertDialog.Builder cardDlg = new AlertDialog.Builder(KioskMainActivity.this);
                            AlertDialog alertDialog_card = cardDlg.create();
                            alertDialog_card.setTitle("카드결제");
                            alertDialog_card.setView(dialogView);
                            alertDialog_card.show();

                            ImageView creditimg;


                            creditimg = dialogView.findViewById(R.id.creditimg);

                            creditimg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog_card.dismiss();

                                    LocalDate localDate = LocalDate.now();
                                    LocalTime localTime = LocalTime.now();

                                    int hour = localTime.getHour();
                                    int min = localTime.getMinute();
                                    int sec = localTime.getSecond();

                                    String currentTime = String.format("%s %d:%d:%d", localDate, hour, min, sec);

                                    dialogView = View.inflate(KioskMainActivity.this, R.layout.activity_order_finish, null);

                                    AlertDialog.Builder completeDlg = new AlertDialog.Builder(KioskMainActivity.this);
                                    completeDlg.setTitle("결제완료");
                                    completeDlg.setView(dialogView);
                                    TextView people;
                                    people = dialogView.findViewById(R.id.people);
                                    people.setText(String.format("결제가 완료되었습니다. 대기번호는 %d번 입니다.", orderNum));
                                    completeDlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Map<String, Object> dataMap = new HashMap<>();
                                            dataMap.put("OrderState", "정상");
                                            dataMap.put("OrderNum", String.valueOf(orderNum));
                                            dataMap.put("OrderDateTime", currentTime);
                                            dataMap.put("OrderPayment", payment);
                                            dataMap.put("TotalPrice", totalPrice.getText().toString());
                                            fb.collection("Enterprise_Users").document(emailId).collection("OrderList")
                                                    .document(currentTime).set(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.i("ORDER : ", "주문 성공");
                                                    Dialog_menu.totalPrice.setText("");
                                                }
                                            });

                                            orderNum++;
                                        }
                                    });
                                    completeDlg.show();
                                    mAdapter.clear();
                                    sum = 0;

                                }
                            });
                        }
                    });
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter.getCount() == 0) {
                    orderBtn.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "현재 목록이 비어 있어요.", Toast.LENGTH_SHORT).show();
                    orderBtn.setEnabled(true);
                } else {
                    Toast.makeText(getApplicationContext(), "전체 삭제 되었어요.", Toast.LENGTH_SHORT).show();
                    mAdapter.clear();
                    sum = 0;
                    Dialog_menu.setTotalPrice(sum);
                }
            }
        });

    }

    public void selectAll(String btnType) {

        fb.collection("Enterprise_Users").document(emailId).collection("MenuList")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) { // task는 경로를 담는다? 쿼리는 담는다?
                if (task.isSuccessful()) {
                    list.clear(); // 초기화를 시켜주어 증식 방지
                    for (QueryDocumentSnapshot doc : task.getResult()) { // QueryDocumentSnapshot..?
                        String menuCG = doc.getData().get("MenuCG").toString();
                        if (btnType.equals(strMain) && menuCG.equals(strMain)) {
                            img_Path = doc.getData().get("Img_Path").toString();
                            menuNum = doc.getData().get("Menu_Num").toString();
                            menu_CG = doc.getData().get("MenuCG").toString();
                            menuName = doc.getData().get("MenuName").toString();
                            menuPrice = doc.getData().get("MenuPrice").toString();
                            menuDetail = doc.getData().get("MenuDetail").toString();
                            optKind01 = doc.getData().get("OptKind01").toString();
                            optKind02 = doc.getData().get("OptKind02").toString();
                            optPrice01 = doc.getData().get("OptPrice01").toString();
                            optPrice02 = doc.getData().get("OptPrice02").toString();
                            optPrice03 = doc.getData().get("OptPrice03").toString();
                            optPrice04 = doc.getData().get("OptPrice04").toString();
                            optPrice05 = doc.getData().get("OptPrice05").toString();
                            optSize01 = doc.getData().get("OptSize01").toString();
                            optSize02 = doc.getData().get("OptSize02").toString();
                            optSize03 = doc.getData().get("OptSize03").toString();
                            stockState = (Boolean) doc.getData().get("StockStage");

                            list.add(new DTO_Cart_Download(menuNum, menu_CG, img_Path, menuName, menuPrice, menuDetail, optKind01, optKind02,
                                    optPrice01, optPrice02, optPrice03, optPrice04, optPrice05, optSize01, optSize02, optSize03, stockState));

                        } else if (btnType.equals(strSide) && menuCG.equals(strSide)) {
                            img_Path = doc.getData().get("Img_Path").toString();
                            menuNum = doc.getData().get("Menu_Num").toString();
                            menu_CG = doc.getData().get("MenuCG").toString();
                            menuName = doc.getData().get("MenuName").toString();
                            menuPrice = doc.getData().get("MenuPrice").toString();
                            menuDetail = doc.getData().get("MenuDetail").toString();
                            optKind01 = doc.getData().get("OptKind01").toString();
                            optKind02 = doc.getData().get("OptKind02").toString();
                            optPrice01 = doc.getData().get("OptPrice01").toString();
                            optPrice02 = doc.getData().get("OptPrice02").toString();
                            optPrice03 = doc.getData().get("OptPrice03").toString();
                            optPrice04 = doc.getData().get("OptPrice04").toString();
                            optPrice05 = doc.getData().get("OptPrice05").toString();
                            optSize01 = doc.getData().get("OptSize01").toString();
                            optSize02 = doc.getData().get("OptSize02").toString();
                            optSize03 = doc.getData().get("OptSize03").toString();
                            stockState = (Boolean) doc.getData().get("StockStage");

                            list.add(new DTO_Cart_Download(menuNum, menu_CG, img_Path, menuName, menuPrice, menuDetail, optKind01, optKind02,
                                    optPrice01, optPrice02, optPrice03, optPrice04, optPrice05, optSize01, optSize02, optSize03, stockState));

                        } else if (btnType.equals(strEtc) && menuCG.equals(strEtc)) {
                            img_Path = doc.getData().get("Img_Path").toString();
                            menuNum = doc.getData().get("Menu_Num").toString();
                            menu_CG = doc.getData().get("MenuCG").toString();
                            menuName = doc.getData().get("MenuName").toString();
                            menuPrice = doc.getData().get("MenuPrice").toString();
                            menuDetail = doc.getData().get("MenuDetail").toString();
                            optKind01 = doc.getData().get("OptKind01").toString();
                            optKind02 = doc.getData().get("OptKind02").toString();
                            optPrice01 = doc.getData().get("OptPrice01").toString();
                            optPrice02 = doc.getData().get("OptPrice02").toString();
                            optPrice03 = doc.getData().get("OptPrice03").toString();
                            optPrice04 = doc.getData().get("OptPrice04").toString();
                            optPrice05 = doc.getData().get("OptPrice05").toString();
                            optSize01 = doc.getData().get("OptSize01").toString();
                            optSize02 = doc.getData().get("OptSize02").toString();
                            optSize03 = doc.getData().get("OptSize03").toString();
                            stockState = (Boolean) doc.getData().get("StockStage");

                            list.add(new DTO_Cart_Download(menuNum, menu_CG, img_Path, menuName, menuPrice, menuDetail, optKind01, optKind02,
                                    optPrice01, optPrice02, optPrice03, optPrice04, optPrice05, optSize01, optSize02, optSize03, stockState));
                        }

                        if (menuCG.equals(strMain) || menuCG.equals(strSide) || menuCG.equals(strEtc)) {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            Frag_main fragment1 = new Frag_main(KioskMainActivity.this, list);
                            transaction.replace(R.id.leftFrame, fragment1);
                            transaction.commitAllowingStateLoss();
                        }

                    }
                } else {
                    Log.d("Error : ", "Getting selectAll Method = ", task.getException());
                    Toast.makeText(getApplicationContext(), "자료 업로드 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}