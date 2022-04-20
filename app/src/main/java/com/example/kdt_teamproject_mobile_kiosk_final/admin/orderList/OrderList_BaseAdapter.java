package com.example.kdt_teamproject_mobile_kiosk_final.admin.orderList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kdt_teamproject_mobile_kiosk_final.R;
import com.example.kdt_teamproject_mobile_kiosk_final.model.OrderList_DTO;

import java.util.ArrayList;

public class OrderList_BaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<OrderList_DTO> orderList_dtos;
    LayoutInflater layoutInflater;

    public OrderList_BaseAdapter(Context mContext, ArrayList<OrderList_DTO> orderList_dtos) {
        this.context = mContext;
        this.orderList_dtos = orderList_dtos;
        this.layoutInflater = LayoutInflater.from(context);
    }

    static class TextViewClass {
        TextView tvOrderStatus, tvOrderNumber, tvOrderDateTime, tvPayment, tvTotalPrice;
    }

    @Override
    public int getCount() {
        return orderList_dtos.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList_dtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        TextViewClass viewClass;

        if (view == null) {
            viewClass = new TextViewClass();

            view = layoutInflater.inflate(R.layout.activity_order_list_base_adapter, null);

            viewClass.tvOrderStatus = view.findViewById(R.id.tvOrderStatus);
            viewClass.tvOrderNumber = view.findViewById(R.id.tvOrderNumber);
            viewClass.tvOrderDateTime = view.findViewById(R.id.tvOrderDateTime);
            viewClass.tvPayment = view.findViewById(R.id.tvPayment);
            viewClass.tvTotalPrice = view.findViewById(R.id.tvTotalPrice);

            view.setTag(viewClass);
        } else {
            viewClass = (TextViewClass) view.getTag();
        }

        viewClass.tvOrderStatus.setText(orderList_dtos.get(position).getOrderState());
        viewClass.tvOrderNumber.setText(orderList_dtos.get(position).getOrderNum());
        viewClass.tvOrderDateTime.setText(orderList_dtos.get(position).getOrderDateTime());
        viewClass.tvPayment.setText(orderList_dtos.get(position).getOrderPayment());
        viewClass.tvTotalPrice.setText(orderList_dtos.get(position).getTotalPrice());

        return view;
    }

    public void inputData(OrderList_DTO dto) {
        orderList_dtos.add(0, dto);
        notifyDataSetChanged();
    }
}