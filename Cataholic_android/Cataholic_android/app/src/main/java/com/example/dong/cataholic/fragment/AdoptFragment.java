package com.example.dong.cataholic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dong.cataholic.Common;
import com.example.dong.cataholic.MainActivity;
import com.example.dong.cataholic.model.StatusBean;
import com.example.dong.cataholic.R;
import com.example.dong.cataholic.model.CatBean;
import com.example.dong.cataholic.model.MemberBean;
import com.example.dong.cataholic.webtask.AdoptTask;
import com.example.dong.cataholic.model.CatMemberBean;
import com.example.dong.cataholic.webtask.MyTaskParams;
import com.github.nisrulz.sensey.Sensey;

import java.io.Serializable;

public class AdoptFragment extends Fragment implements Serializable {

    MainActivity mainActivity;
    ShopFragment shopFragment;
    WorkFragment workFragment;
    Bundle bundle;
    StatusBean statusBean;

    EditText etAdoptName, etAdoptNarrative;
    Button btAdoptCat;
    private AdoptTask adoptTask;
    MemberBean memberBean;
    CatBean catBean;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adopt, container, false);
        Sensey.getInstance().init(mainActivity);
        etAdoptName = view.findViewById(R.id.etAdoptName);
        etAdoptNarrative = view.findViewById(R.id.etAdoptNarrative);
        btAdoptCat = view.findViewById(R.id.btAdoptCat);

        bundle = getArguments();
        if (bundle != null) {
            workFragment = (WorkFragment) bundle.get("workFragment");
            statusBean = (StatusBean) bundle.get("nowStatus");
            memberBean = (MemberBean) bundle.get("memberBean");
        }


        btAdoptCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAdoptName.getText().toString() != null) {
                    catBean = new CatBean();
                    catBean.setCatName(etAdoptName.getText().toString());
                    catBean.setCatNarrative(etAdoptNarrative.getText().toString());
                    catBean.setImageId("cat003");
                    catBean.setCatPrice(20);
                    catBean.setCatInterest(1);
                    catBean.setCatDuration(15);
                    catBean.setCatSpeed(5);
                    catBean.setCatUnitMoney(5);
                    catBean.setMemberKey(memberBean.getMemberKey());

                    CatMemberBean catMemberBean = new CatMemberBean(memberBean, catBean);

                    if (Common.networkConnected(mainActivity)) {
                        String url = Common.URL + "/adopt.do";

                        try {

                            MyTaskParams myTaskParams = new MyTaskParams(url, catMemberBean);

                            adoptTask = new AdoptTask();
                            memberBean = adoptTask.execute(myTaskParams).get();

                        } catch (Exception e) {
                            Log.e("adopt", e.toString());
                        }
                        if (memberBean == null) {
                            Common.showToast(mainActivity, "No adopt");
                        } else {
                            goShop(memberBean);
                        }
                    } else {
                        Common.showToast(mainActivity, "No Network Connection");
                    }

                }
            }

            private void goShop(MemberBean memberBean) {
                shopFragment = new ShopFragment();
                bundle.putSerializable("workFragment", workFragment);
                bundle.putSerializable("nowStatus", statusBean);
                bundle.putSerializable("memberBean", memberBean);
                shopFragment.setArguments(bundle);
                mainActivity.switchFragment(shopFragment);

            }
        });

        return view;
    }


}
