package com.example.kdt_teamproject_mobile_kiosk_final.admin.branch_management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kdt_teamproject_mobile_kiosk_final.R;

import java.util.ArrayList;

public class Base_Adapter extends BaseAdapter {
    Context mContext = null;
    ArrayList<StoreManagement_DTO> mData = null;
    LayoutInflater mLayoutInflater = null; // Inflater 정적으로 존재하는 XML 파일을 java코드에서 접근하여 실제 객체로 만들어서 사용하는 것

    public Base_Adapter(Context context, ArrayList<StoreManagement_DTO> data) {
        this.mContext = context;
        this.mData = data;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    // Arraylist를 이용해서 Admin 안에 있는 갯수
    @Override
    public int getCount() {
        return mData.size();
    }

    // Arraylist를 이용해서 Admin 안에 있는 아이템
    @Override
    public StoreManagement_DTO getItem(int position) {
        return mData.get(position);
    }

    // Arraylist를 이용해서 Admin 안에 있는 아이템 아이디
    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {

        TextView tvStoreName_lv;
        TextView tvStoreContact_lv;
        TextView tvStoreAddress_lv;
    }


    // convertview는 매번 Inflating 작업을 하면 매우 무거워지기 때문에 getView메소드 내의 ConvertView 활용
    // 스크롤이 내려가면서 맨위에 있던 아이템을 화면에서 사라지고 다른 새로운 아이템이 구성해야 될 때 뷰를 새롭게 Inflating 하기보다는
    // 기존 view를 다시 사용할때 쓴다.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemLayout = convertView;
        ViewHolder viewHolder = null;

        // viewHolder는 Inflate를 최소하기 위해서 뷰를 재활용하는데, 이때 각 뷰의 내용을 업데이트하기 위해 findviewById를 매번 호출해야한다.
        // Inflate는 xml로 쓰여있는 view의 정의를 실제 view객체로 만드는 것을 말한다.

        // 1. 어댑터뷰가 재사용할 뷰를 넘겨주지 않은 경우에만 새로운 뷰를 생성한다.
        // ====================================================================
        if (itemLayout == null) {
            itemLayout = mLayoutInflater.inflate(R.layout.activity_list_view, null); //인플레이션

            // View Holder를 생성하고 사용할 자식뷰를 찾아 View Holder에 참조시킨다.
            // 생성된 View Holder는 아이템에 설정해 두고, 다음에 아이템 재사용시
            // 참조한다.
            // ----------------------------------------------------------------
            viewHolder = new ViewHolder();


            viewHolder.tvStoreName_lv = itemLayout.findViewById(R.id.tvStoreName_lv);
            viewHolder.tvStoreContact_lv = itemLayout.findViewById(R.id.tvStoreContact_lv);
            viewHolder.tvStoreAddress_lv = itemLayout.findViewById(R.id.tvStoreAddress_lv);

            itemLayout.setTag(viewHolder);
            // ----------------------------------------------------------------
        } else {
            // 재사용 아이템에는 이전에 View Holder 객체를 설정해 두었다.
            // 그러므로 설정된 View Holder 객체를 이용해서 findViewById 함수를
            // 사용하지 않고 원하는 뷰를 참조할 수 있다.
            viewHolder = (ViewHolder) itemLayout.getTag();
        }
        // ====================================================================

        // 2. 지점명, 전화번호, 주소 데이터를 참조하여 레이아웃을 갱신한다.
        // ====================================================================

        viewHolder.tvStoreName_lv.setText(mData.get(position).getStoreName()); //변수에 데이터를 넣어주고
        viewHolder.tvStoreContact_lv.setText(mData.get(position).getStorePhone());
        viewHolder.tvStoreAddress_lv.setText(mData.get(position).getStoreAddress());
        // ====================================================================

        return itemLayout; //그 레이아웃을 보내준다. 뷰홀더랑 아이템레이아웃이 가르키는 곳이 동일하다.
    }

    // Arraylist를 이용해서 Admin안에 있는 데이터를 추가 Branch_Management 에서 mAdapter.add(mData.size(),temp); add 부분에서 사용
    public void add(int index, StoreManagement_DTO addData) {
        mData.add(index, addData);
        notifyDataSetChanged(); //norify 공지, 공지후 리스트뷰에게 넘겨준다.
    }

    public void refresh() {
        this.notifyDataSetChanged();
    }

}