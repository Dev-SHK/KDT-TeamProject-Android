package com.example.kdt_teamproject_mobile_kiosk_final.kiosk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.kdt_teamproject_mobile_kiosk_final.R;
import com.example.kdt_teamproject_mobile_kiosk_final.model.DTO_Cart_Upload;

import java.util.ArrayList;
import java.util.List;


public class Adapter_Cart extends BaseAdapter {
    Context mContext = null;
    List<DTO_Cart_Upload> mData = null;
    LayoutInflater mLayoutInflater = null;
    DTO_Cart_Upload data = null;

    public Adapter_Cart(Context context)
    {
        mContext = context;
        mData = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView mNameTv;
        TextView mDetailTv;
        TextView mOptTv;
        TextView mPriceTv;
        Button mBtn_X;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemLayout = convertView;
        ViewHolder viewHolder = null;

        if (itemLayout == null) {
            itemLayout = mLayoutInflater.inflate(R.layout.layout_cart_test, null);

            viewHolder = new ViewHolder();

            viewHolder.mNameTv = (TextView) itemLayout.findViewById(R.id.test_left_name);
            viewHolder.mDetailTv = (TextView) itemLayout.findViewById(R.id.test_left_detail);
            viewHolder.mOptTv = (TextView) itemLayout.findViewById(R.id.test_left_opts);
            viewHolder.mPriceTv = (TextView) itemLayout.findViewById(R.id.test_cart_price);
            viewHolder.mBtn_X = (Button) itemLayout.findViewById(R.id.cancel_button_cart);

            itemLayout.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) itemLayout.getTag();
        }

        viewHolder.mNameTv.setText(mData.get(position).getOrderName());
        viewHolder.mDetailTv.setText(mData.get(position).getOrderDetail());
        viewHolder.mOptTv.setText(mData.get(position).getOrderOpt01() + " " + mData.get(position).getOrderOpt02());
        viewHolder.mPriceTv.setText(mData.get(position).getTotalPrice() + "원");
        viewHolder.mBtn_X.setBackgroundResource(R.drawable.btn_x);

        viewHolder.mBtn_X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newPrice = (int) (((KioskMainActivity)mContext).sum - mData.get(position).getTotalPrice());
                ((KioskMainActivity)mContext).sum = newPrice;
                Dialog_menu.setTotalPrice(newPrice);
                delete(position);
            }
        });

        return itemLayout;
    }

    // 장바구니 담기(추가)
    public void add (int index, DTO_Cart_Upload dto02) {
        this.data = dto02;
        mData.add(dto02);
        notifyDataSetChanged();
    }

    // 장바구니 특정 메뉴 삭제
    public void delete(int index) {
        mData.remove(index);
        notifyDataSetChanged();
    }

    // 장바구니 전체 삭제
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }
}
