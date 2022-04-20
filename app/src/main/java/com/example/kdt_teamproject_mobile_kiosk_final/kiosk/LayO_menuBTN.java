package com.example.kdt_teamproject_mobile_kiosk_final.kiosk;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kdt_teamproject_mobile_kiosk_final.R;
import com.example.kdt_teamproject_mobile_kiosk_final.model.DTO_Cart_Download;

public class LayO_menuBTN extends LinearLayout {

    ImageView imgView;
    TextView nameView;
    TextView priceView;
    DTO_Cart_Download dto;

    public LayO_menuBTN(Context context, DTO_Cart_Download dto) {
        super(context);

        imgView = new ImageView(context);
        nameView = new TextView(context);
        priceView = new TextView(context);
        this.dto = dto;

        setLayout();
    }

    private void setLayout() {
        LayoutParams params_lay = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        LayoutParams params_img = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        LayoutParams params_name = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        LayoutParams params_price = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        this.setLayoutParams(params_lay);
        params_lay.setMargins(30, 0, 0, 20);
        params_lay.width = 350;
        params_lay.height = 500;
        this.setBackgroundResource(R.drawable.btn_default);

        imgView.setImageResource(R.drawable.americano);
        params_img.width = 300;
        params_img.height = 300;

        nameView.setText(dto.getMenuName());
        nameView.setTextSize(30);
        nameView.setTextColor(Color.WHITE);
        nameView.setTypeface(null, Typeface.BOLD);
        nameView.setBackgroundColor(Color.parseColor("#ffc0cb"));
        nameView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        params_name.width = 350;
        params_name.height = 90;


        priceView.setText(dto.getMenuPrice() + "원");
        priceView.setTextSize(30);
        priceView.setTextColor(Color.parseColor("#ffc0cb"));
        priceView.setTypeface(null, Typeface.BOLD);
        priceView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        params_price.width = 350;
        params_price.height = 70;

        this.setOrientation(LinearLayout.VERTICAL);
        this.addView(imgView, params_img);
        this.addView(nameView, params_name);
        this.addView(priceView, params_price);
    }

    public DTO_Cart_Download getDto() {
        return dto;
    }
}
