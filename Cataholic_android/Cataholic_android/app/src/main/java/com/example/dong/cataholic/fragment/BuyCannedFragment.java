package com.example.dong.cataholic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dong.cataholic.MainActivity;
import com.example.dong.cataholic.model.StatusBean;
import com.example.dong.cataholic.R;
import com.example.dong.cataholic.model.AllCannedBean;
import com.example.dong.cataholic.model.CannedBean;
import com.example.dong.cataholic.model.MemberBean;

import java.io.Serializable;

public class BuyCannedFragment extends Fragment implements Serializable {
    private Button btCannedBack;
    MainActivity mainActivity;
    Long income;
    StatusBean statusBean;
    Bundle bundle;
    WorkFragment workFragment;
    ShopFragment shopFragment;
    TextView tvIncome, tvPressure;
    TextView tvCanned01Amount, tvCanned02Amount, tvCanned03Amount;
    int pressureStatus = 0;
    MemberBean memberBean;
    CardView cvCanned01, cvCanned02, cvCanned03;

    AllCannedBean allCannedBean;
    CannedBean cannedBean01,cannedBean02,cannedBean03;

    BuyCannedFragment buyCannedFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buyforcat, container, false);

        btCannedBack = view.findViewById(R.id.btCannedBack);
        mainActivity = (MainActivity) getActivity();
        tvIncome = mainActivity.findViewById(R.id.tvIncome);
        tvPressure = mainActivity.findViewById(R.id.tvPressure);
        cvCanned01 = view.findViewById(R.id.cvCanned01);
        cvCanned02 = view.findViewById(R.id.cvCanned02);
        cvCanned03 = view.findViewById(R.id.cvCanned03);
        tvCanned01Amount = view.findViewById(R.id.tvCanned01Amount);
        tvCanned02Amount = view.findViewById(R.id.tvCanned02Amount);
        tvCanned03Amount = view.findViewById(R.id.tvCanned03Amount);


        bundle = getArguments();
        if (bundle != null) {
            workFragment = (WorkFragment) bundle.get("workFragment");
            statusBean = (StatusBean) bundle.get("nowStatus");
            memberBean = (MemberBean) bundle.get("memberBean");
            allCannedBean = (AllCannedBean) bundle.get("allCannedBean");
            buyCannedFragment = (BuyCannedFragment) bundle.get("buyCannedFragment");
            income = statusBean.getIncome();
            pressureStatus = statusBean.getPressureStatus();
        }

        if (allCannedBean != null){
            cannedBean01 = allCannedBean.getCannedBean01();
            cannedBean02 = allCannedBean.getCannedBean02();
            cannedBean03 = allCannedBean.getCannedBean03();
            tvCanned01Amount.setText("持有數量：" + cannedBean01.getAmount());
            tvCanned02Amount.setText("持有數量：" + cannedBean02.getAmount());
            tvCanned03Amount.setText("持有數量：" + cannedBean03.getAmount());
        }else {
            allCannedBean = new AllCannedBean();
            cannedBean01 = new CannedBean("藥效強化", 5, 5, 0, 0, 0);
            cannedBean02 = new CannedBean("速度強化", 5, 0, 5, 0, 0);
            cannedBean03 = new CannedBean("質量強化", 5, 0, 0, 5, 0);
            allCannedBean.setCannedBean01(cannedBean01);
            allCannedBean.setCannedBean02(cannedBean02);
            allCannedBean.setCannedBean03(cannedBean03);
        }

        btCannedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopFragment = new ShopFragment();
                bundle.putSerializable("updateWork", true);
                bundle.putSerializable("allCannedBean", allCannedBean);
                bundle.putSerializable("workFragment", workFragment);
                bundle.putSerializable("nowStatus", statusBean);
                bundle.putSerializable("memberBean", memberBean);
                shopFragment.setArguments(bundle);
                mainActivity.switchFragment(shopFragment);
            }
        });

        cvCanned01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cannedBean01.setAmount(cannedBean01.getAmount()+1);
                allCannedBean.setCannedBean01(cannedBean01);
                if (income != null & income >= cannedBean01.getCannedPrice()) {
                    income -= cannedBean01.getCannedPrice();
                    statusBean.setIncome(income);
                    statusBean.setPressureStatus(pressureStatus);
                    tvCanned01Amount.setText("持有數量：" + cannedBean01.getAmount());

                    bundle.putSerializable("updateWork", true);
                    bundle.putSerializable("allCannedBean", allCannedBean);
                    bundle.putSerializable("workFragment", workFragment);
                    bundle.putSerializable("nowStatus", statusBean);
                    bundle.putSerializable("memberBean", memberBean);
                    bundle.putSerializable("buyCannedFragment", buyCannedFragment);
                    buyCannedFragment.setArguments(bundle);
                    mainActivity.switchFragment(buyCannedFragment);
                } else {
                    Toast.makeText(mainActivity, "金額不足", Toast.LENGTH_SHORT).show();
                }


            }
        });

        cvCanned02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cannedBean02.setAmount(cannedBean02.getAmount()+1);
                allCannedBean.setCannedBean02(cannedBean02);
                if (income != null & income >= cannedBean02.getCannedPrice()) {
                    income -= cannedBean02.getCannedPrice();
                    statusBean.setIncome(income);
                    statusBean.setPressureStatus(pressureStatus);
                    tvCanned02Amount.setText("持有數量：" + cannedBean02.getAmount());

                    bundle.putSerializable("updateWork", true);
                    bundle.putSerializable("allCannedBean", allCannedBean);
                    bundle.putSerializable("workFragment", workFragment);
                    bundle.putSerializable("nowStatus", statusBean);
                    bundle.putSerializable("memberBean", memberBean);
                    bundle.putSerializable("buyCannedFragment", buyCannedFragment);
                    buyCannedFragment.setArguments(bundle);
                    mainActivity.switchFragment(buyCannedFragment);
                } else {
                    Toast.makeText(mainActivity, "金額不足", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cvCanned03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cannedBean03.setAmount(cannedBean03.getAmount()+1);
                allCannedBean.setCannedBean03(cannedBean03);
                if (income != null & income >= cannedBean03.getCannedPrice()) {
                    income -= cannedBean03.getCannedPrice();
                    statusBean.setIncome(income);
                    statusBean.setPressureStatus(pressureStatus);
                    tvCanned03Amount.setText("持有數量：" + cannedBean03.getAmount());

                    bundle.putSerializable("updateWork", true);
                    bundle.putSerializable("allCannedBean", allCannedBean);
                    bundle.putSerializable("workFragment", workFragment);
                    bundle.putSerializable("nowStatus", statusBean);
                    bundle.putSerializable("memberBean", memberBean);
                    bundle.putSerializable("buyCannedFragment", buyCannedFragment);
                    buyCannedFragment.setArguments(bundle);
                    mainActivity.switchFragment(buyCannedFragment);
                } else {
                    Toast.makeText(mainActivity, "金額不足", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tvIncome.setText("收入： " + income + "元");
        tvPressure.setText("壓力： " + pressureStatus);

        return view;
    }


}
