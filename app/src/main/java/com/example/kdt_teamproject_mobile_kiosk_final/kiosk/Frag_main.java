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

public class Frag_main extends Fragment {

    private static final int DIALOG_REQUEST_CODE = 1234;

    Context context;
    GridLayout grdLay;
    DTO_Cart_Download dto;
    List<DTO_Cart_Download> list;

    public Frag_main(Context context, List<DTO_Cart_Download> list) {
        this.context = context;
        this.list = list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, viewGroup, false);
        grdLay = (GridLayout) view.findViewById(R.id.grid_main);
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
                    System.out.println("<< 프래그 메인 >>  : " + list.get(index));
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
}