package com.example.kdt_teamproject_mobile_kiosk_final.admin.menu_management;

import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kdt_teamproject_mobile_kiosk_final.R;
import com.example.kdt_teamproject_mobile_kiosk_final.admin.LoginActivity;
import com.example.kdt_teamproject_mobile_kiosk_final.model.EnterpriseUserAccount;
import com.example.kdt_teamproject_mobile_kiosk_final.model.MenuList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddMenuMain extends Fragment {

    Button mainBtn, sideBtn, etcBtn, insertBtn, modifyPageBtn;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    List<MenuList> mainList;
    List<MenuList> sideList;
    List<MenuList> etcList;
    MenuItemPage mainFrag;
    static MenuList menuList;
    static String imgPath;
    String strMain = "메인";
    String strSide = "사이드";
    String strEtc = "기타";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_add_menu_main, container, false);

        mainBtn = v.findViewById(R.id.mainBtn);
        sideBtn = v.findViewById(R.id.sideBtn);
        etcBtn = v.findViewById(R.id.etcBtn);
        insertBtn = v.findViewById(R.id.insertBtn);
        modifyPageBtn = v.findViewById(R.id.modifyPageBtn);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        AddMenuPage appendFrag = new AddMenuPage(getContext(), menuList);
        transaction.replace(R.id.fragmentBoard02, appendFrag);
        transaction.commitAllowingStateLoss();

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMenuPage appendFrag = new AddMenuPage(getContext(), menuList);
                RViewChange(appendFrag, null);
            }
        });

        modifyPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditMenuPage modifyFrag = new EditMenuPage(getContext(), menuList);
                RViewChange(null, modifyFrag);
            }
        });

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAll(strMain);
            }
        });

        sideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAll(strSide);
            }
        });

        etcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAll(strEtc);
            }
        });

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        화면을 돌리기 위해 다시 실행을 하기 때문에 Activity가 두번 돌아감
        return v;
    }

//    public void selectGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.CONTENT_TYPE);
//        intent.setType("image/*");
//        startActivityForResult(intent, GALLERY_CODE);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            sendPicture(data.getData()); //갤러리에서 가져오기
        }
    }

    private void sendPicture(Uri imgUri) {
        imgPath = getRealPathFromURI(imgUri); // path 경로
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);//경로를 통해 비트맵으로 전환
        try {
            if (getChildFragmentManager().findFragmentById(R.id.fragmentBoard02) != null) {
                AddMenuPage appendFrag = (AddMenuPage) getChildFragmentManager().findFragmentById(R.id.fragmentBoard02);
                appendFrag.changeAppendFragImg(bitmap, exifDegree);
                appendFrag.changeAppendFragImgText(imgPath);
                appendFrag.appendFragState = false;
            }
        } catch (ClassCastException c) {
            c.printStackTrace();
        }
        try {
            if (getChildFragmentManager().findFragmentById(R.id.fragmentBoard02) != null) {
                EditMenuPage modifyFrag = (EditMenuPage) getChildFragmentManager().findFragmentById(R.id.fragmentBoard02);
                modifyFrag.changeModifyFragImg(bitmap, exifDegree);
                modifyFrag.changeModifyFragImgText(imgPath);
                modifyFrag.modifyFragState = false;
            }
        } catch (ClassCastException c) {
            c.printStackTrace();
        }
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public Bitmap rotate(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

    public void selectAll(String btnType) {
        List<MenuList> menuLists = new ArrayList<>();

        EnterpriseUserAccount userAccount = LoginActivity.userAccount;
        String emailId = userAccount.getEpEmailID();

        firestore.collection("Enterprise_Users").document(emailId).collection("MenuList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            menuLists.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String menuNum = (String) document.getData().get("Menu_Num");
                                String menuName = (String) document.getData().get("MenuName");
                                String menuPrice = (String) document.getData().get("MenuPrice");
                                String menuDetail = (String) document.getData().get("MenuDetail");
                                String menuCG = (String) document.getData().get("MenuCG");
                                Boolean stockState = (Boolean) document.getData().get("StockState");
                                String optSize01 = (String) document.getData().get("OptSize01");
                                String optPrice01 = (String) document.getData().get("OptPrice01");
                                String optSize02 = (String) document.getData().get("OptSize02");
                                String optPrice02 = (String) document.getData().get("OptPrice02");
                                String optSize03 = (String) document.getData().get("OptSize03");
                                String optPrice03 = (String) document.getData().get("OptPrice03");
                                String optKind01 = (String) document.getData().get("OptKind01");
                                String optPrice04 = (String) document.getData().get("OptPrice04");
                                String optKind02 = (String) document.getData().get("OptKind02");
                                String optPrice05 = (String) document.getData().get("OptPrice05");
                                String imgPath = (String) document.getData().get("Img_Path");

                                menuLists.add(new MenuList(menuNum, menuName, menuPrice, menuDetail, menuCG, stockState, optSize01, optPrice01, optSize02, optPrice02, optSize03, optPrice03, optKind01, optPrice04, optKind02, optPrice05, imgPath));
                            }
                            mainList = new ArrayList<>();
                            sideList = new ArrayList<>();
                            etcList = new ArrayList<>();
                            for (int i = 0; i < menuLists.size(); i++) {
                                if (btnType.equals(strMain)) {
                                    if (menuLists.get(i).getMenuCG().equals(strMain)) {
                                        mainList.add(menuLists.get(i));
                                        mainFrag = new MenuItemPage(getContext(), mainList);
                                    }
                                } else if (btnType.equals(strSide)) {
                                    if (menuLists.get(i).getMenuCG().equals(strSide)) {
                                        sideList.add(menuLists.get(i));
                                        mainFrag = new MenuItemPage(getContext(), sideList);
                                    }
                                } else {
                                    if (menuLists.get(i).getMenuCG().equals(strEtc)) {
                                        etcList.add(menuLists.get(i));
                                        mainFrag = new MenuItemPage(getContext(), etcList);
                                    }
                                }
                            }

                            if (isAdded()) {
                                FragmentManager childFragmentManager = getChildFragmentManager();
                                childFragmentManager.beginTransaction().replace(R.id.fragmentBoard01, mainFrag).commit();
                                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                                ft.detach(mainFrag).attach(mainFrag).commit();
                            }
                        } else {
                            Log.d("DocSnippets", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void SelectItemGet(MenuList dto) {
        menuList = new MenuList(dto.getMenuNum(), dto.getMenuName(), dto.getMenuPrice(), dto.getMenuDetail(), dto.getMenuCG(), dto.getStockState(), dto.getOptSize01(),
                dto.getOptPrice01(), dto.getOptSize02(), dto.getOptPrice02(), dto.getOptSize03(), dto.getOptPrice03(), dto.getOptKind01(), dto.getOptPrice04(), dto.getOptKind02(), dto.getOptPrice05(), dto.getImgPath());
    }

    public void RViewChange(AddMenuPage appendFrag, EditMenuPage modifyFrag) {
        if (!isAdded()) return;
        if (appendFrag == null) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentBoard02, modifyFrag);
            transaction.commitAllowingStateLoss();
        } else {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentBoard02, appendFrag);
            transaction.commitAllowingStateLoss();
        }
    }


}