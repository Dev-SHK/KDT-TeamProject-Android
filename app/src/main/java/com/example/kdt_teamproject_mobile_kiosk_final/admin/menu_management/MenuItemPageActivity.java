package com.example.kdt_teamproject_mobile_kiosk_final.admin.menu_management;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.fragment.app.Fragment;

import com.example.kdt_teamproject_mobile_kiosk_final.R;
import com.example.kdt_teamproject_mobile_kiosk_final.model.MenuList;

import java.util.List;

public class MenuItemPageActivity extends Fragment {

    static Context context;
    static MenuList menuList;
    static List<MenuList> menuLists;
    MenuItemLayout menuItemLayout;
    GridLayout menuGridL;
    View view;
    Boolean clickable = false;

    public MenuItemPageActivity(Context context, List<MenuList> menuLists) {
        this.context = context;
        this.menuLists = menuLists;
    }

    public MenuItemPageActivity() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menu_item_page, container, false);
        menuGridL = (GridLayout) view.findViewById(R.id.menuGridL);
        menuGridL.setColumnCount(3);

        for (int i = 0; i < menuLists.size(); i++) {
            final int index;
            menuList = new MenuList(menuLists.get(i).getMenuNum(), menuLists.get(i).getMenuName(), menuLists.get(i).getMenuPrice(), menuLists.get(i).getMenuDetail(), menuLists.get(i).getMenuCG(),
                    menuLists.get(i).getStockState(), menuLists.get(i).getOptSize01(), menuLists.get(i).getOptPrice01(), menuLists.get(i).getOptSize02(),
                    menuLists.get(i).getOptPrice02(), menuLists.get(i).getOptSize03(), menuLists.get(i).getOptPrice03(), menuLists.get(i).getOptKind01(),
                    menuLists.get(i).getOptPrice04(), menuLists.get(i).getOptKind02(), menuLists.get(i).getOptPrice05(), menuLists.get(i).getImgPath());
            menuItemLayout = new MenuItemLayout(context, menuList);
            menuGridL.addView(menuItemLayout);
            menuItemLayout.setId(i);
            index = i;
            menuItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddMenuMainActivity mainActivity = new AddMenuMainActivity();

                    String menuNum = menuLists.get(index).getMenuNum();
                    String menuName = menuLists.get(index).getMenuName();
                    String menuPrice = menuLists.get(index).getMenuPrice();
                    String menuDetail = menuLists.get(index).getMenuDetail();
                    String menuCG = menuLists.get(index).getMenuCG();
                    Boolean stockState = menuLists.get(index).getStockState();
                    String optSize01 = menuLists.get(index).getOptSize01();
                    String optPrice01 = menuLists.get(index).getOptPrice01();
                    String optSize02 = menuLists.get(index).getOptSize02();
                    String optPrice02 = menuLists.get(index).getOptPrice02();
                    String optSize03 = menuLists.get(index).getOptSize03();
                    String optPrice03 = menuLists.get(index).getOptPrice03();
                    String optKind01 = menuLists.get(index).getOptKind01();
                    String optPrice04 = menuLists.get(index).getOptPrice04();
                    String optKind02 = menuLists.get(index).getOptKind02();
                    String optPrice05 = menuLists.get(index).getOptPrice05();
                    String imgPath = menuLists.get(index).getImgPath();
                    if (menuItemLayout.getId() == index){
                        if (!clickable) {
                            menuItemLayout.setBackgroundResource(R.drawable.itembutton_t);
                            clickable = true;
                        } else {
                            menuItemLayout.setBackgroundResource(R.drawable.itembutton_f);
                            clickable = false;
                        }
                    }
                    menuList = new MenuList(menuNum, menuName, menuPrice, menuDetail, menuCG, stockState, optSize01, optPrice01,
                            optSize02, optPrice02, optSize03, optPrice03, optKind01, optPrice04, optKind02, optPrice05, imgPath);
                    mainActivity.SelectItemGet(menuList);
                }
            });
        }
        return view;
    }

}
