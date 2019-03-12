package com.example.dong.cataholic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.dong.cataholic.MainActivity;
import com.example.dong.cataholic.model.StatusBean;
import com.example.dong.cataholic.R;
import com.example.dong.cataholic.model.AllCannedBean;
import com.example.dong.cataholic.model.MemberBean;
import com.github.nisrulz.sensey.Sensey;

import java.io.Serializable;


public class ShopFragment extends Fragment implements Serializable {

    MainActivity mainActivity;
    Button btSmoktCat, btAdopt, btBuyFood, btManage, btShopBack;
    Bundle bundle;
    WorkFragment workFragment;
    SmokeCatFragment smokeCatFragment;
    AdoptFragment adoptFragment;
    ManageCatsFragment manageCatsFragment;
    AllCannedBean allCannedBean;

    StatusBean statusBean;
    int pressureStatus = 0;
    Long income;
    MemberBean memberBean;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        Sensey.getInstance().init(mainActivity);
        btSmoktCat = view.findViewById(R.id.btSmoktCat);
        btAdopt = view.findViewById(R.id.btAdopt);
        btBuyFood = view.findViewById(R.id.btBuyFood);
        btManage = view.findViewById(R.id.btManage);
        btShopBack = view.findViewById(R.id.btShopBack);

        bundle = getArguments();
        if (bundle != null) {
            workFragment = (WorkFragment) bundle.get("workFragment");
            statusBean = (StatusBean) bundle.get("nowStatus");
            memberBean = (MemberBean) bundle.get("memberBean");
            allCannedBean = (AllCannedBean) bundle.get("allCannedBean");
            income = statusBean.getIncome();
            pressureStatus = statusBean.getPressureStatus();
        }

        btBuyFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyCannedFragment buyCannedFragment= new BuyCannedFragment();
                bundle.putSerializable("nowStatus", statusBean);
                bundle.putSerializable("memberBean", memberBean);
                bundle.putSerializable("workFragment", workFragment);
                bundle.putSerializable("buyCannedFragment", buyCannedFragment);
                buyCannedFragment.setArguments(bundle);
                mainActivity.switchFragment(buyCannedFragment);
            }
        });

        btManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageCatsFragment = new ManageCatsFragment();
                bundle.putSerializable("nowStatus", statusBean);
                bundle.putSerializable("memberBean", memberBean);
                bundle.putSerializable("workFragment", workFragment);
                bundle.putSerializable("allCannedBean", allCannedBean);
                manageCatsFragment.setArguments(bundle);
                mainActivity.switchFragment(manageCatsFragment);
            }
        });

        btAdopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adoptFragment = new AdoptFragment();
                bundle.putSerializable("nowStatus", statusBean);
                bundle.putSerializable("memberBean", memberBean);
                bundle.putSerializable("workFragment", workFragment);
                adoptFragment.setArguments(bundle);
                mainActivity.switchFragment(adoptFragment);
            }
        });


        btSmoktCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smokeCatFragment = new SmokeCatFragment();
                bundle.putSerializable("nowStatus", statusBean);
                bundle.putSerializable("workFragment", workFragment);
                smokeCatFragment.setArguments(bundle);
                mainActivity.switchFragment(smokeCatFragment);
            }
        });

        btShopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putSerializable("workFragment",workFragment);
                bundle.putSerializable("nowStatus", statusBean);
                workFragment.setArguments(bundle);
                mainActivity.switchFragment(workFragment);
            }
        });





        return view;
    }


}
