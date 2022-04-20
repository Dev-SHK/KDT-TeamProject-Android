package com.example.kdt_teamproject_mobile_kiosk_final.kiosk;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.kdt_teamproject_mobile_kiosk_final.R;
import com.example.kdt_teamproject_mobile_kiosk_final.model.DTO_Cart_Download;

import java.util.List;

public class Frag_side extends Fragment {

    private static final int DIALOG_REQUEST_CODE = 1234;

    Context context;
    GridLayout grdLay;
    DTO_Cart_Download dto;
    List<DTO_Cart_Download> list;

    public Frag_side(Context context, List<DTO_Cart_Download> list) {
        this.context = context;
        this.list = list;
        System.out.println(list);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_side, viewGroup, false);
        grdLay = (GridLayout) view.findViewById(R.id.grid_side);
        grdLay.setColumnCount(4);

        for (int i = 0; i < list.size(); i++) {
            final int index;
            dto = new DTO_Cart_Download(
                    list.get(i).getMenuName(),
                    list.get(i).getMenuPrice(),
                    list.get(i).getMenuDetail(),
                    list.get(i).getOptKind01(),
                    list.get(i).getOptKind02(),
                    list.get(i).getOptPrice01(),
                    list.get(i).getOptPrice02(),
                    list.get(i).getOptPrice03(),
                    list.get(i).getOptPrice04(),
                    list.get(i).getOptPrice05(),
                    list.get(i).getOptSize01(),
                    list.get(i).getOptSize02(),
                    list.get(i).getOptSize03()
            );

            LayO_menuBTN buttons = new LayO_menuBTN(context, dto);
            index = i;

            buttons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    show(list.get(index));
                    System.out.println("<< 프래그 사이드 >>  : " + list.get(index));
                }
            });
            grdLay.addView(buttons);
        }
        return view;
    }

    void show(DTO_Cart_Download dtoCart) {
        DialogFragment newFrag = new Dialog_menu(context, dtoCart);
        newFrag.setTargetFragment(this, DIALOG_REQUEST_CODE);
        newFrag.show(getFragmentManager(), "dialog");
    }


    // 선생님 코드
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == DIALOG_REQUEST_CODE) {
//            if(resultCode == Activity.RESULT_OK) {
//                String opt0101 = data.getExtras().getString("1");
//                String opt0102 = data.getExtras().getString("2");
//                String opt0201 = data.getExtras().getString("3");
//                String opt0202 = data.getExtras().getString("4");
//                String opt0203 = data.getExtras().getString("5");
//
//                Toast.makeText(getActivity(), "성공!", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}