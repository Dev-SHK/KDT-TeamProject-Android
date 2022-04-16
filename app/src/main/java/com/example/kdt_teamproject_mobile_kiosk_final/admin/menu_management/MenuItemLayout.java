package com.example.kdt_teamproject_mobile_kiosk_final.admin.menu_management;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kdt_teamproject_mobile_kiosk_final.R;
import com.example.kdt_teamproject_mobile_kiosk_final.model.MenuList;


public class MenuItemLayout extends LinearLayout {
    ImageView menu_img;
    TextView menu_N, menu_P;
    LinearLayout menuLayout;
    MenuList menuList;

    public MenuItemLayout(Context context) {
        super(context);
    }

    public MenuItemLayout(Context context, MenuList menuList) {
        super(context);
        menu_img = new ImageView(context);
        menu_N = new TextView(context,null, Typeface.BOLD);
        menu_P = new TextView(context,null, Typeface.BOLD);
        menuLayout = new LinearLayout(context);
        this.menuList = menuList;
        System.out.println("MenuItem => " + menuList);
        setLayout();
    }

    public void setLayout(){

        Bitmap bitmap = BitmapFactory.decodeFile(menuList.getImgPath());

        LayoutParams layoutMargin = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutMargin.setMargins(45, 20, 0, 0);
        layoutMargin.width = 360;
        layoutMargin.height = 440;
        LayoutParams imgtMargin = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imgtMargin.setMargins(20, 20, 20, 20);
        imgtMargin.width = 320;
        imgtMargin.height = 280;
        LayoutParams txtMargin = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txtMargin.setMargins(20, 2, 20, 0);
        txtMargin.width = 320;
        txtMargin.height = 60;

        menu_N.setText(menuList.getMenuName());
        menu_N.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        menu_P.setText(menuList.getMenuPrice());
        menu_P.setTextAlignment(TEXT_ALIGNMENT_CENTER);

        this.addView(menuLayout,new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundResource(R.drawable.border_line);
        this.setLayoutParams(layoutMargin);

        this.addView(menu_img, new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        menu_img.setImageBitmap(bitmap);
        menu_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        menu_img.setLayoutParams(imgtMargin);
        menu_img.setBackgroundResource(R.drawable.border_line);

        this.addView(menu_N, new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        menu_N.setLayoutParams(txtMargin);
        menu_N.setTypeface(Typeface.DEFAULT_BOLD);
        menu_N.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        menu_N.setTextSize(18);

        this.addView(menu_P ,new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        menu_P.setLayoutParams(txtMargin);
        menu_P.setTypeface(Typeface.DEFAULT_BOLD);
        menu_P.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        menu_P.setTextSize(18);
    }
}
