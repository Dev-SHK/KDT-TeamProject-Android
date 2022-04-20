package com.example.kdt_teamproject_mobile_kiosk_final.admin.branch_management;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.kdt_teamproject_mobile_kiosk_final.R;
import com.example.kdt_teamproject_mobile_kiosk_final.admin.LoginActivity;
import com.example.kdt_teamproject_mobile_kiosk_final.model.EnterpriseUserAccount;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Branch_Management extends Fragment implements OnMapReadyCallback {

    EditText etStoreName, etStoreContact, etStoreAddress;
    String TAG = "branch_management";
    Button btnRegister_Branch, btnDel_Branch;

    // 구글 지도
    GoogleMap gMap;


    ListView mListView;
    Base_Adapter mAdapter;
    ArrayList<StoreManagement_DTO> mData;

    GroundOverlayOptions Mark;

    // Firestore 인스턴스(클래스의 현재 생성된 Object)를 초기화
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    EnterpriseUserAccount userAccount = LoginActivity.userAccount;
    String emailId = userAccount.getEpEmailID();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_branch_management, container, false);

        // GridView 와 EditText 연결
        // gv =  findViewById(R.id.gridView1);
        etStoreName = v.findViewById(R.id.etStoreName);
        etStoreContact = v.findViewById(R.id.etStoreContact);
        etStoreAddress = v.findViewById(R.id.etStoreAddress);

        btnRegister_Branch = v.findViewById(R.id.btnRegister_Branch);
        btnDel_Branch = v.findViewById(R.id.btnDel_Branch);

        // 위젯을 클래스로 연결
        SupportMapFragment mapFrag = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFrag != null) {
            mapFrag.getMapAsync(this);
        }

        mData = new ArrayList<>();

        mAdapter = new Base_Adapter(getContext(), mData);

        mListView = v.findViewById(R.id.ListView1);
        mListView.setAdapter(mAdapter);

        // onCreate가 시작하면 Firestore 목록에 있는 DB를 보여주기
        selectAll();

        // 등록버튼을 누르면 firestore 안에 리스트로 저장되고 주소를 경도 위도로 바꿔주면서 지도에 마커표시를 해준다.
        Geocoder geocoder = new Geocoder(getContext());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 위도 경도를 list로 받아와서 변환후에 지도에 줌으로 표시해줌
                List<Address> list;
                String str = mData.get(position).getStoreAddress();
                try {
                    list = geocoder.getFromLocationName(str, 1); // str 지역 이름 그리고 읽을 개수
                    Log.i("test", Double.toString(list.get(0).getLatitude())); // 위도
                    Log.i("test", Double.toString(list.get(0).getLongitude()));  // 경도
//                    if (gMap != null) {
                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude()), 15));

                    // 마커 표시
                    MarkerOptions MarkerOptions = new MarkerOptions();
                    MarkerOptions.position(new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude()));
                    gMap.addMarker(MarkerOptions);

                    Toast.makeText(getContext(), mData.get(position).getStoreAddress(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test", "입출력 오류 - 서버에서 주소변환 시 에러발생");
                    Toast.makeText(getContext(), "입출력 오류 - 서버에서 주소변환 시 에러발생", Toast.LENGTH_SHORT).show();
                }

                firestore.collection("Enterprise_Users").document(emailId).collection("Store").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            etStoreName.setText(mData.get(position).getStoreName());
                            etStoreName.setEnabled(false);
                            etStoreContact.setText(mData.get(position).getStorePhone());
                            etStoreAddress.setText(mData.get(position).getStoreAddress());
                        }
                    }
                });

                btnDel_Branch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firestore.collection("Enterprise_Users").document(emailId).collection("Store").document(mData.get(position).getStoreName()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), "정보가 삭제되었어요", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
//                mAdapter.notifyDataSetChanged();
//                mListView.invalidate();
//                mListView.refreshDrawableState();
            }
        });

        btnRegister_Branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAll();


                // EditText에 있는 글씨 얻어오기
                String data = etStoreName.getText().toString();
                String data1 = etStoreContact.getText().toString();
                String data2 = etStoreAddress.getText().toString();


                // 데이터 추가
                Map<String, Object> admin = new HashMap<>();
                admin.put("StoreName", data); // 지점명
                admin.put("StorePhone", data1); // 지점전화번호
                admin.put("StoreAddress", data2); // 지점주소

                // Firestore에 생성된 ID안에 지점명, 전화번호 , 주소 저장
                firestore.collection("Enterprise_Users").document(emailId).collection("Store").document(data).set(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "등록에 성공했어요", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                if (v.getId() == R.id.btnRegister_Branch) {
                    StoreManagement_DTO addData = new StoreManagement_DTO();
                    addData.setStoreName(etStoreName.getText().toString());
                    addData.setStorePhone(etStoreContact.getText().toString());
                    addData.setStoreAddress(etStoreAddress.getText().toString());

                    // mAdapter.add(mData.size(),addData);
                }
            }
        });

        StoreManagement_DTO management_dto = new StoreManagement_DTO();

        return v;
    }

    // 구글 지도
    @Override
    public void onMapReady(GoogleMap map) {
        gMap = map;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.getUiSettings().setZoomControlsEnabled(false);
        LatLng basic = new LatLng(37.5008, 127.0269); // 그린컴퓨터 아카데미 위도 경도
        gMap.addMarker(new MarkerOptions().position(basic).title("그린컴퓨터아카데미"));
        gMap.animateCamera(CameraUpdateFactory.newLatLng(basic));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(basic, 15));

    }

    // firestore에서 저장된 DB를 불러오는부분
    public void selectAll() {
        mData.clear();
        firestore.collection("Enterprise_Users").document(emailId).collection("Store").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String data3 = document.get("StoreName").toString(); // 정보를 담아주고
                        String data4 = document.get("StorePhone").toString();
                        String data5 = document.get("StoreAddress").toString();
                        StoreManagement_DTO temp = new StoreManagement_DTO();
                        temp.setStoreName(data3); // 지점명
                        temp.setStorePhone(data4); // 지점 전화번호
                        temp.setStoreAddress(data5); // 지점 주소

                        mAdapter.add(mData.size(), temp);
                    }
                }
            }
        });
    }
}