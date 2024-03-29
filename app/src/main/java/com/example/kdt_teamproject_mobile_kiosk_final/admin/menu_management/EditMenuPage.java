package com.example.kdt_teamproject_mobile_kiosk_final.admin.menu_management;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kdt_teamproject_mobile_kiosk_final.R;
import com.example.kdt_teamproject_mobile_kiosk_final.admin.AdminMenuActivity;
import com.example.kdt_teamproject_mobile_kiosk_final.admin.LoginActivity;
import com.example.kdt_teamproject_mobile_kiosk_final.model.EnterpriseUserAccount;
import com.example.kdt_teamproject_mobile_kiosk_final.model.MenuList;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditMenuPage extends Fragment {

    Spinner categorySpinner;
    Context context;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Button modifyBtn, deleteBtn, upLoadBtn;
    EditText insertMenuNum, insertMenuN, insertMenuP, insertMenuD, optEditSize01_1, optEditPrice01_2, optEditSize02_1, optEditPrice02_2,
            optEditSize03_1, optEditPrice03_2, optEditKind04_1, optEditPrice04_2, optEditKind05_1, optEditPrice05_2;
    Switch optSwitch01, optSwitch02, optSwitch03, optSwitch04, optSwitch05, stateSwitch;
    LinearLayout optLayout01_01, optLayout01_02, optLayout02_01, optLayout02_02, optLayout03_01, optLayout03_02, optLayout04_01,
            optLayout04_02, optLayout05_01, optLayout05_02;
    View view;
    AddMenuMain mainActivity;
    MenuList menuList;
    Boolean modifyFragState = false;
    ImageView imageView;
    TextView imgPathTxt;
    String strMain = "메인";
    String strSide = "사이드";
    String strEtc = "기타";
    AdminMenuActivity menuActivity;

    public EditMenuPage(Context context, MenuList menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    public EditMenuPage() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_edit_menu_page, container, false);
        categorySpinner = view.findViewById(R.id.categorySpinner);
        modifyBtn = view.findViewById(R.id.modifyBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);
        upLoadBtn = view.findViewById(R.id.upLoadBtn);
        insertMenuNum = view.findViewById(R.id.insertMenuNum);
        insertMenuN = view.findViewById(R.id.insertNewMenuN);
        insertMenuP = view.findViewById(R.id.insertNewMenuP);
        insertMenuD = view.findViewById(R.id.insertNewDetail);
        optEditSize01_1 = view.findViewById(R.id.optEditSize01_1);
        optEditPrice01_2 = view.findViewById(R.id.optEditPrice01_2);
        optEditSize02_1 = view.findViewById(R.id.optEditSize02_1);
        optEditPrice02_2 = view.findViewById(R.id.optEditPrice02_2);
        optEditSize03_1 = view.findViewById(R.id.optEditSize03_1);
        optEditPrice03_2 = view.findViewById(R.id.optEditPrice03_2);
        optEditKind04_1 = view.findViewById(R.id.optEditKind04_1);
        optEditPrice04_2 = view.findViewById(R.id.optEditPrice04_2);
        optEditKind05_1 = view.findViewById(R.id.optEditKind05_1);
        optEditPrice05_2 = view.findViewById(R.id.optEditPrice05_2);
        stateSwitch = view.findViewById(R.id.stateSwitch);
        optSwitch01 = view.findViewById(R.id.optSwitch01);
        optSwitch02 = view.findViewById(R.id.optSwitch02);
        optSwitch03 = view.findViewById(R.id.optSwitch03);
        optSwitch04 = view.findViewById(R.id.optSwitch04);
        optSwitch05 = view.findViewById(R.id.optSwitch05);
        optLayout01_01 = view.findViewById(R.id.optLayout01_01);
        optLayout01_02 = view.findViewById(R.id.optLayout01_02);
        optLayout02_01 = view.findViewById(R.id.optLayout02_01);
        optLayout02_02 = view.findViewById(R.id.optLayout02_02);
        optLayout03_01 = view.findViewById(R.id.optLayout03_01);
        optLayout03_02 = view.findViewById(R.id.optLayout03_02);
        optLayout04_01 = view.findViewById(R.id.optLayout04_01);
        optLayout04_02 = view.findViewById(R.id.optLayout04_02);
        optLayout05_01 = view.findViewById(R.id.optLayout05_01);
        optLayout05_02 = view.findViewById(R.id.optLayout05_02);
        imageView = view.findViewById(R.id.imageView);
        imgPathTxt = view.findViewById(R.id.imgPathTxt);


        mainActivity = new AddMenuMain();
        menuActivity = new AdminMenuActivity();

        final String[] element = {"-선택-", strMain, strSide, strEtc};
        ArrayAdapter adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, element);
        categorySpinner.setAdapter(adapter);

        int selectionNum = 0;
        switch (menuList.getMenuCG()) {
            case "메인":
                selectionNum = 1;
                break;
            case "사이드":
                selectionNum = 2;
                break;
            case "기타":
                selectionNum = 3;
                break;
        }

        if (!menuList.getOptSize01().equals("")) {
            optSwitch01.setChecked(true);
            optLayout01_01.setVisibility(View.VISIBLE);
            optLayout01_02.setVisibility(View.VISIBLE);
        }
        if (!menuList.getOptSize02().equals("")) {
            optSwitch02.setChecked(true);
            optLayout02_01.setVisibility(View.VISIBLE);
            optLayout02_02.setVisibility(View.VISIBLE);
        }
        if (!menuList.getOptSize03().equals("")) {
            optSwitch03.setChecked(true);
            optLayout03_01.setVisibility(View.VISIBLE);
            optLayout03_02.setVisibility(View.VISIBLE);
        }
        if (!menuList.getOptKind01().equals("")) {
            optSwitch04.setChecked(true);
            optLayout04_01.setVisibility(View.VISIBLE);
            optLayout04_02.setVisibility(View.VISIBLE);
        }
        if (!menuList.getOptKind02().equals("")) {
            optSwitch05.setChecked(true);
            optLayout05_01.setVisibility(View.VISIBLE);
            optLayout05_02.setVisibility(View.VISIBLE);
        }
        Bitmap bitmap = BitmapFactory.decodeFile(menuList.getImgPath());
        insertMenuNum.setText(menuList.getMenuNum());
        insertMenuN.setText(menuList.getMenuName());
        insertMenuP.setText(menuList.getMenuPrice());
        insertMenuD.setText(menuList.getMenuDetail());
        categorySpinner.setSelection(selectionNum);
        stateSwitch.setChecked(menuList.getStockState());
        optEditSize01_1.setText(menuList.getOptSize01());
        optEditPrice01_2.setText(menuList.getOptPrice01());
        optEditSize02_1.setText(menuList.getOptSize02());
        optEditPrice02_2.setText(menuList.getOptPrice02());
        optEditSize03_1.setText(menuList.getOptSize03());
        optEditPrice03_2.setText(menuList.getOptPrice03());
        optEditKind04_1.setText(menuList.getOptKind01());
        optEditPrice04_2.setText(menuList.getOptPrice04());
        optEditKind05_1.setText(menuList.getOptKind02());
        optEditPrice05_2.setText(menuList.getOptPrice05());
        imageView.setImageBitmap(bitmap);
        imgPathTxt.setText(menuList.getImgPath());

        EnterpriseUserAccount userAccount = LoginActivity.userAccount;
        String emailId = userAccount.getEpEmailID();

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("Enterprise_Users").document(emailId)
                        .collection("MenuList").document((menuList.getMenuNum())).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        switch (menuList.getMenuCG()) {
                            case "메인":
                                mainActivity.selectAll(strMain);
                                break;
                            case "사이드":
                                mainActivity.selectAll(strSide);
                                break;
                            case "기타":
                                mainActivity.selectAll(strEtc);
                                break;
                        }
                        AddMenuPage appendFrag = new AddMenuPage(context, menuList);
                        mainActivity.RViewChange(appendFrag, null);
                    }
                });
            }
        });

        upLoadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyFragState = true;
//                menuActivity.checkSelfPermission();
//                mainActivity.selectGallery();
            }
        });

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> menu = new HashMap<>();

                if (insertMenuNum.getText().toString().equals("") | insertMenuN.getText().toString().equals("") |
                        insertMenuP.getText().toString().equals("") | categorySpinner.getSelectedItem().toString().equals("-선택-")) {
                    Toast.makeText(context, "필수항목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    String menuNum = insertMenuNum.getText().toString();
                    String menuName = insertMenuN.getText().toString();
                    String menuPrice = insertMenuP.getText().toString();
                    String menuDetail = insertMenuD.getText().toString();
                    String menuCG = categorySpinner.getSelectedItem().toString();
                    Boolean stockState = stateSwitch.isChecked();
                    String optSize01 = optEditSize01_1.getText().toString();
                    String optPrice01 = optEditPrice01_2.getText().toString();
                    String optSize02 = optEditSize02_1.getText().toString();
                    String optPrice02 = optEditPrice02_2.getText().toString();
                    String optSize03 = optEditSize03_1.getText().toString();
                    String optPrice03 = optEditPrice03_2.getText().toString();
                    String optKind01 = optEditKind04_1.getText().toString();
                    String optPrice04 = optEditPrice04_2.getText().toString();
                    String optKind02 = optEditKind05_1.getText().toString();
                    String optPrice05 = optEditPrice05_2.getText().toString();
                    String imgPath = imgPathTxt.getText().toString();


                    menu.put("Menu_Num", menuNum);
                    menu.put("MenuName", menuName);
                    menu.put("MenuPrice", menuPrice);
                    menu.put("MenuDetail", menuDetail);
                    menu.put("MenuCG", menuCG);
                    menu.put("StockState", stockState);
                    menu.put("OptSize01", optSize01);
                    menu.put("OptPrice01", optPrice01);
                    menu.put("OptSize02", optSize02);
                    menu.put("OptPrice02", optPrice02);
                    menu.put("OptSize03", optSize03);
                    menu.put("OptPrice03", optPrice03);
                    menu.put("OptKind01", optKind01);
                    menu.put("OptPrice04", optPrice04);
                    menu.put("OptKind02", optKind02);
                    menu.put("OptPrice05", optPrice05);
                    menu.put("Img_Path", imgPath);

                    Log.d("Menu DB => ", menu.toString());

                    menuList = new MenuList(menuNum, menuName, menuPrice, menuDetail, menuCG,
                            stockState, optSize01, optPrice01, optSize02, optPrice02, optSize03,
                            optPrice03, optKind01, optPrice04, optKind02, optPrice05, imgPath);
                    firestore.collection("Enterprise_Users").document(emailId)
                            .collection("MenuList").document((menuList.getMenuNum())).set(menu).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "수정완료", Toast.LENGTH_SHORT).show();
                            switch (menuCG) {
                                case "메인":
                                    mainActivity.selectAll(strMain);
                                    break;
                                case "사이드":
                                    mainActivity.selectAll(strSide);
                                    break;
                                case "기타":
                                    mainActivity.selectAll(strEtc);
                                    break;
                            }
                            AddMenuPage appendFrag = new AddMenuPage(context, menuList);
                            mainActivity.RViewChange(appendFrag, null);
                        }
                    });
                }
            }
        });

        optSwitch01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (optSwitch01.isChecked()) {
                    optLayout01_01.setVisibility(View.VISIBLE);
                    optLayout01_02.setVisibility(View.VISIBLE);
                } else {
                    optLayout01_01.setVisibility(View.GONE);
                    optLayout01_02.setVisibility(View.GONE);
                }
            }
        });

        optSwitch02.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (optSwitch02.isChecked()) {
                    optLayout02_01.setVisibility(View.VISIBLE);
                    optLayout02_02.setVisibility(View.VISIBLE);
                } else {
                    optLayout02_01.setVisibility(View.GONE);
                    optLayout02_02.setVisibility(View.GONE);
                }
            }
        });

        optSwitch03.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (optSwitch03.isChecked()) {
                    optLayout03_01.setVisibility(View.VISIBLE);
                    optLayout03_02.setVisibility(View.VISIBLE);
                } else {
                    optLayout03_01.setVisibility(View.GONE);
                    optLayout03_02.setVisibility(View.GONE);
                }
            }
        });

        optSwitch04.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (optSwitch04.isChecked()) {
                    optLayout04_01.setVisibility(View.VISIBLE);
                    optLayout04_02.setVisibility(View.VISIBLE);
                } else {
                    optLayout04_01.setVisibility(View.GONE);
                    optLayout04_02.setVisibility(View.GONE);
                }
            }
        });

        optSwitch05.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (optSwitch05.isChecked()) {
                    optLayout05_01.setVisibility(View.VISIBLE);
                    optLayout05_02.setVisibility(View.VISIBLE);
                } else {
                    optLayout05_01.setVisibility(View.GONE);
                    optLayout05_02.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

    public void changeModifyFragImg(Bitmap img, int exifDegree) {
        imageView.setImageBitmap(mainActivity.rotate(img, exifDegree));
    }

    public void changeModifyFragImgText(String path) {
        imgPathTxt.setText(path);
    }
}