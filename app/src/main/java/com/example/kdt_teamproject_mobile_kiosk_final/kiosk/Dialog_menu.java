package com.example.kdt_teamproject_mobile_kiosk_final.kiosk;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.kdt_teamproject_mobile_kiosk_final.R;
import com.example.kdt_teamproject_mobile_kiosk_final.model.DTO_Cart_Download;
import com.example.kdt_teamproject_mobile_kiosk_final.model.DTO_Cart_Upload;

public class Dialog_menu extends DialogFragment {

    DTO_Cart_Download dto;
    static TextView dlg_name, dlg_price, dlg_detail ,dlg_optKind, dlg_optSize, totalPrice;
    static int count;
    static String cart_name, cart_price, cart_detail, cart_Kind, cart_Size;
    static Context context;
    DTO_Cart_Upload cart02 = new DTO_Cart_Upload();
    static Button Btn_ordering;

    public Dialog_menu(Context context, DTO_Cart_Download dto) {
        this.dto = dto;
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dlgView = inflater.inflate(R.layout.dialog_menu, null);
        builder.setView(dlgView);

        final Button Opt_Kind01 = (Button) dlgView.findViewById(R.id.Opt_Kind01);
        final Button Opt_Kind02 = (Button) dlgView.findViewById(R.id.Opt_Kind02);
        final Button Opt_Size01 = (Button) dlgView.findViewById(R.id.Opt_Size01);
        final Button Opt_Size02 = (Button) dlgView.findViewById(R.id.Opt_Size02);
        final Button Opt_Size03 = (Button) dlgView.findViewById(R.id.Opt_Size03);
        totalPrice = (TextView) ((KioskMainActivity)context).findViewById(R.id.totalPrice);

        Btn_ordering = (Button) dlgView.findViewById(R.id.btn_ordering);
        final Button Btn_canceling = (Button) dlgView.findViewById(R.id.btn_canceling);

        dlg_name = dlgView.findViewById(R.id.dialog_name);
        dlg_price = dlgView.findViewById(R.id.dialog_price);
        dlg_detail = dlgView.findViewById(R.id.dialog_detail);
        dlg_optKind = dlgView.findViewById(R.id.dialog_optKind);
        dlg_optSize = dlgView.findViewById(R.id.dialog_optSize);

        dlg_name.setText(dto.getMenuName());
        dlg_price.setText("기본 " + String.valueOf(dto.getMenuPrice()) + "원");
        dlg_detail.setText(dto.getMenuDetail());

        Opt_Kind01.setText(dto.getOptKind01()+ "(+" +dto.getOptPrice04() + ")");
        Opt_Kind02.setText(dto.getOptKind02()+ "(+" +dto.getOptPrice05() + ")");
        Opt_Size01.setText(dto.getOptSize01()+ "(+" +dto.getOptPrice01() + ")");
        Opt_Size02.setText(dto.getOptSize02()+ "(+" +dto.getOptPrice02() + ")");
        Opt_Size03.setText(dto.getOptSize03()+ "(+" +dto.getOptPrice03() + ")");

        // 디폴트 버튼 Background Option
        Opt_Kind01.setBackgroundResource(R.drawable.btn_default);
        Opt_Kind02.setBackgroundResource(R.drawable.btn_default);
        Opt_Size01.setBackgroundResource(R.drawable.btn_default);
        Opt_Size02.setBackgroundResource(R.drawable.btn_default);
        Opt_Size03.setBackgroundResource(R.drawable.btn_default);

        // 디폴트 옵션, 사이즈 (클릭없이 주문담기 클릭시 오류없이 진행하기 위해)
        dlg_optKind.setText(dto.getOptKind01()+ "(+" + dto.getOptPrice04() + ")");
        dlg_optSize.setText(dto.getOptSize01()+ "(+" + dto.getOptPrice01() + ")");

        // 버튼 터치 이벤트들
        // 종류(Kind)
        Opt_Kind01.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dlg_optKind.setText(dto.getOptKind01()+ "(+" + dto.getOptPrice04() + ")");
                dlg_optKind.setVisibility(View.VISIBLE);
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Opt_Kind01.setBackgroundResource(R.drawable.btn_dialog_selected);
                    Opt_Kind01.setTextColor(Color.WHITE);
                    Opt_Kind02.setBackgroundResource(R.drawable.btn_default);
                    Opt_Kind02.setTextColor(Color.BLACK);
                }
                return false;
            }
        });
        Opt_Kind02.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dlg_optKind.setText(dto.getOptKind02()+ "(+" + dto.getOptPrice05() + ")");
                dlg_optKind.setVisibility(View.VISIBLE);
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Opt_Kind01.setBackgroundResource(R.drawable.btn_default);
                    Opt_Kind01.setTextColor(Color.BLACK);
                    Opt_Kind02.setBackgroundResource(R.drawable.btn_dialog_selected);
                    Opt_Kind02.setTextColor(Color.WHITE);
                }
                return false;
            }
        });

        // 사이즈(Size)
        Opt_Size01.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dlg_optSize.setText(dto.getOptSize01()+ "(+" + dto.getOptPrice01() + ")");
                dlg_optSize.setVisibility(View.VISIBLE);
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Opt_Size01.setBackgroundResource(R.drawable.btn_dialog_selected);
                    Opt_Size01.setTextColor(Color.WHITE);
                    Opt_Size02.setBackgroundResource(R.drawable.btn_default);
                    Opt_Size02.setTextColor(Color.BLACK);
                    Opt_Size03.setBackgroundResource(R.drawable.btn_default);
                    Opt_Size03.setTextColor(Color.BLACK);
                }
                return false;
            }
        });
        Opt_Size02.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dlg_optSize.setText(dto.getOptSize02()+ "(+" + dto.getOptPrice02() + ")");
                dlg_optSize.setVisibility(View.VISIBLE);
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Opt_Size01.setBackgroundResource(R.drawable.btn_default);
                    Opt_Size01.setTextColor(Color.BLACK);
                    Opt_Size02.setBackgroundResource(R.drawable.btn_dialog_selected);
                    Opt_Size02.setTextColor(Color.WHITE);
                    Opt_Size03.setBackgroundResource(R.drawable.btn_default);
                    Opt_Size03.setTextColor(Color.BLACK);
                }
                return false;
            }
        });
        Opt_Size03.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dlg_optSize.setText(dto.getOptSize03()+ "(+" + dto.getOptPrice03() + ")");
                dlg_optSize.setVisibility(View.VISIBLE);
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Opt_Size01.setBackgroundResource(R.drawable.btn_default);
                    Opt_Size01.setTextColor(Color.BLACK);
                    Opt_Size02.setBackgroundResource(R.drawable.btn_default);
                    Opt_Size02.setTextColor(Color.BLACK);
                    Opt_Size03.setBackgroundResource(R.drawable.btn_dialog_selected);
                    Opt_Size03.setTextColor(Color.WHITE);
                }
                return false;
            }
        });

        // 다이얼로그 주문담기 버튼
        Btn_ordering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 주문 내역에 들어갈 녀석들
                // 금액(형변환) <- 기본금액 + 종류금액 + 사이즈금액
                long cart_long_price = Long.valueOf(dlg_price.getText().toString().replaceAll("[^0-9]", ""));
                long cart_long_kind_price = Long.valueOf(dlg_optKind.getText().toString().replaceAll("[^0-9]", ""));
                long cart_long_size_price = Long.valueOf(dlg_optSize.getText().toString().replaceAll("[^0-9]", ""));

                // 내용 텍스트
                cart_name = dlg_name.getText().toString();
                cart_price = dlg_price.getText().toString();
                cart_detail = dlg_detail.getText().toString();
                cart_Kind = dlg_optKind.getText().toString();
                cart_Size = dlg_optSize.getText().toString();

                ((KioskMainActivity)context).total = cart_long_price + cart_long_kind_price + cart_long_size_price;
                count = 1;

                // DTO와 Adapter로 쏴주는 녀석
                cart02 = new DTO_Cart_Upload(cart_name, cart_detail, cart_Kind, cart_Size, cart_long_kind_price, cart_long_size_price, ((KioskMainActivity)context).total);
                Toast.makeText(context, "메뉴가 담겼습니다.", Toast.LENGTH_SHORT).show();
                ((KioskMainActivity)context).mAdapter.add(0, cart02);

                // 합계 금액
                ((KioskMainActivity)context).sum += ((KioskMainActivity)context).total;
                totalPrice.setText("합계 : " + ((KioskMainActivity)context).sum + "원");
                totalPrice.setTextSize(40);
                totalPrice.setTextColor(Color.parseColor("#ffc0cb"));
                totalPrice.setTypeface(null, Typeface.BOLD);
                totalPrice.setPadding(0, 20, 50, 0);
                totalPrice.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                dismiss();
            }
        });

        // 다이얼로그 취소 버튼
        Btn_canceling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "주문 취소 되었습니다.", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        return builder.create();
    }

    // DialogFragment에서 Dialog Box의 크기를 지정
    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = 1300;
        params.height = 700;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams)params);
    }

    // 합계 금액에 대해 이곳저곳에서 쓰일 필요가 있어 퍼블릭 스태틱으로 메소드 만듦
    public static void setTotalPrice(int sum) {
        totalPrice.setText("합계 : " + sum + "원");
    }
}
