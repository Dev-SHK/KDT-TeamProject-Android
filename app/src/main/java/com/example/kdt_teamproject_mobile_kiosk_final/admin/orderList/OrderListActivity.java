package com.example.kdt_teamproject_mobile_kiosk_final.admin.orderList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kdt_teamproject_mobile_kiosk_final.R;
import com.example.kdt_teamproject_mobile_kiosk_final.admin.LoginActivity;
import com.example.kdt_teamproject_mobile_kiosk_final.model.EnterpriseUserAccount;
import com.example.kdt_teamproject_mobile_kiosk_final.model.OrderList_DTO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OrderListActivity extends Fragment {

    static String orderState, orderNum, orderDateTime, orderPayment, totalPrice;
    OrderList_BaseAdapter baseAdapter;
    ArrayList<OrderList_DTO> list_dtos;
    EnterpriseUserAccount userAccount = LoginActivity.userAccount;
    String emailId = userAccount.getEpEmailID();
    ListView orderListLayout_Admin;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_order_list, container, false);

        list_dtos = new ArrayList<>();
        baseAdapter = new OrderList_BaseAdapter(getContext(), list_dtos);

        orderListLayout_Admin = v.findViewById(R.id.orderListLayout_Admin);
        orderListLayout_Admin.setAdapter(baseAdapter);

        firestoreImportData();

        return v;
    }

    public void firestoreImportData() {
        list_dtos.clear();
        firestore.collection("Enterprise_Users").document(emailId)
                .collection("OrderList").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot snapshot : task.getResult()) {
                    OrderList_DTO dto = new OrderList_DTO();
                    orderState = (String) snapshot.getData().get("OrderState");
                    orderNum = (String) snapshot.getData().get("OrderNum");
                    orderDateTime = (String) snapshot.getData().get("OrderDateTime");
                    orderPayment = (String) snapshot.getData().get("OrderPayment");
                    totalPrice = (String) snapshot.getData().get("TotalPrice");

                    dto.setOrderState(orderState);
                    dto.setOrderNum(orderNum);
                    dto.setOrderDateTime(orderDateTime);
                    dto.setOrderPayment(orderPayment);
                    dto.setTotalPrice(totalPrice);

                    baseAdapter.inputData(dto);

                }
            }
        });
    }
}