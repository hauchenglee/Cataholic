package com.example.dong.cataholic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dong.cataholic.Common;
import com.example.dong.cataholic.MainActivity;
import com.example.dong.cataholic.model.StatusBean;
import com.example.dong.cataholic.R;
import com.example.dong.cataholic.model.AllCannedBean;
import com.example.dong.cataholic.model.CannedBean;
import com.example.dong.cataholic.model.CatBean;
import com.example.dong.cataholic.model.MemberBean;
import com.example.dong.cataholic.model.CatMemberBean;
import com.example.dong.cataholic.webtask.FeedTask;
import com.example.dong.cataholic.webtask.MyTaskParams;
import com.github.nisrulz.sensey.Sensey;

import java.io.Serializable;

import static com.example.dong.cataholic.fragment.SmokeCatFragment.getResId;

public class DetailCatFragment extends Fragment implements Serializable {

    MainActivity mainActivity;
    WorkFragment workFragment;
    Bundle bundle;
    StatusBean statusBean;

    ImageView ivDetailImage;
    TextView tvDetailName, tvDetailInterest;
    EditText tvDetailNarrative;
    ProgressBar pbSpeed, pbUnitMoney, pbDuration;
    ImageButton btDetailFeed, btDetailBack;
    CardView cvFeedCanned;
    LinearLayout llFeedCanned01,llFeedCanned02,llFeedCanned03;
    TextView tvFeedCanned01,tvFeedCanned02,tvFeedCanned03;
    Button btFeedCanned;

    MemberBean memberBean;
    CatBean catBean;

    AllCannedBean allCannedBean;
    CannedBean cannedBean01,cannedBean02,cannedBean03;
    FeedTask feedTask;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catdetail, container, false);
        Sensey.getInstance().init(mainActivity);

        ivDetailImage = view.findViewById(R.id.ivDetailImage);
        tvDetailName = view.findViewById(R.id.tvDetailName);
        tvDetailInterest = view.findViewById(R.id.tvDetailInterest);
        tvDetailNarrative = view.findViewById(R.id.tvDetailNarrative);
        pbDuration = view.findViewById(R.id.pbDuration);
        pbUnitMoney = view.findViewById(R.id.pbUnitMoney);
        pbSpeed = view.findViewById(R.id.pbSpeed);
        btDetailFeed = view.findViewById(R.id.btDetailFeed);
        btDetailBack = view.findViewById(R.id.btDetailBack);
        cvFeedCanned = view.findViewById(R.id.cvFeedCanned);
        llFeedCanned01 = view.findViewById(R.id.llFeedCanned01);
        llFeedCanned02 = view.findViewById(R.id.llFeedCanned02);
        llFeedCanned03 = view.findViewById(R.id.llFeedCanned03);
        tvFeedCanned01 = view.findViewById(R.id.tvFeedCanned01);
        tvFeedCanned02 = view.findViewById(R.id.tvFeedCanned02);
        tvFeedCanned03 = view.findViewById(R.id.tvFeedCanned03);
        btFeedCanned = view.findViewById(R.id.btFeedCanned);

        btFeedCanned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvFeedCanned.setVisibility(View.GONE);
            }
        });


        bundle = getArguments();
        if (bundle != null) {
            workFragment = (WorkFragment) bundle.get("workFragment");
            statusBean = (StatusBean) bundle.get("nowStatus");
            memberBean = (MemberBean) bundle.get("memberBean");
            catBean = (CatBean) bundle.get("manageCats");
            allCannedBean = (AllCannedBean) bundle.get("allCannedBean");
        }

        btDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.networkConnected(mainActivity)) {
                    String url = Common.URL + "/feed.do";

                    try {
                        CatMemberBean catMemberBean = new CatMemberBean(memberBean,catBean);
                        MyTaskParams myTaskParams = new MyTaskParams(url, catMemberBean);

                        feedTask = new FeedTask();
                        memberBean = feedTask.execute(myTaskParams).get();

                    } catch (Exception e) {
                        Log.e("adopt", e.toString());
                    }
                    if (memberBean == null) {
                        Common.showToast(mainActivity, "No update");
                    } else {
                        bundle.putSerializable("workFragment",workFragment);
                        bundle.putSerializable("nowStatus", statusBean);
                        bundle.putSerializable("memberBean", memberBean);
                        bundle.putSerializable("allCannedBean", allCannedBean);
                        ManageCatsFragment manageCatsFragment = new ManageCatsFragment();
                        manageCatsFragment.setArguments(bundle);
                        mainActivity.switchFragment(manageCatsFragment);
                    }
                } else {
                    Common.showToast(mainActivity, "No Network Connection");
                }
            }
        });

        ivDetailImage.setImageResource(getResId(catBean.getImageId(), R.drawable.class));
        tvDetailName.setText(catBean.getCatName());
        tvDetailInterest.setText(catBean.getCatInterest().toString()+"元 / 小時");
        tvDetailNarrative.setText(catBean.getCatNarrative());
        pbDuration.setProgress(catBean.getCatDuration());
        pbUnitMoney.setProgress(catBean.getCatUnitMoney());
        pbSpeed.setProgress(catBean.getCatSpeed());

        cannedBean01 = allCannedBean.getCannedBean01();
        cannedBean02 = allCannedBean.getCannedBean02();
        cannedBean03 = allCannedBean.getCannedBean03();

        tvFeedCanned01.setText("數量："+cannedBean01.getAmount());
        tvFeedCanned02.setText("數量："+cannedBean02.getAmount());
        tvFeedCanned03.setText("數量："+cannedBean03.getAmount());

        btDetailFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvFeedCanned.setVisibility(View.VISIBLE);
            }
        });

        llFeedCanned01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cannedBean01.getAmount() != 0){
                    cannedBean01.setAmount(cannedBean01.getAmount()-1);
                    tvFeedCanned01.setText("數量："+cannedBean01.getAmount());
                    allCannedBean.setCannedBean01(cannedBean01);

                    catBean.setCatDuration(catBean.getCatDuration()+cannedBean01.getCannedDuration());
                    catBean.setCatSpeed(catBean.getCatSpeed()+cannedBean01.getCannedSpeed());
                    catBean.setCatUnitMoney(catBean.getCatUnitMoney()+cannedBean01.getCannedUnitMoney());

                    pbDuration.setProgress(catBean.getCatDuration());
                    pbUnitMoney.setProgress(catBean.getCatUnitMoney());
                    pbSpeed.setProgress(catBean.getCatSpeed());

                    cvFeedCanned.setVisibility(View.GONE);
                }else {
                    Common.showToast(getActivity(), "罐頭數量不足");
                }
            }
        });
        llFeedCanned02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cannedBean02.getAmount() != 0){
                    cannedBean02.setAmount(cannedBean02.getAmount()-1);
                    tvFeedCanned02.setText("數量："+cannedBean02.getAmount());
                    allCannedBean.setCannedBean02(cannedBean02);

                    catBean.setCatDuration(catBean.getCatDuration()+cannedBean02.getCannedDuration());
                    catBean.setCatSpeed(catBean.getCatSpeed()+cannedBean02.getCannedSpeed());
                    catBean.setCatUnitMoney(catBean.getCatUnitMoney()+cannedBean02.getCannedUnitMoney());

                    pbDuration.setProgress(catBean.getCatDuration());
                    pbUnitMoney.setProgress(catBean.getCatUnitMoney());
                    pbSpeed.setProgress(catBean.getCatSpeed());

                    cvFeedCanned.setVisibility(View.GONE);
                }else {
                    Common.showToast(getActivity(), "罐頭數量不足");
                }
            }
        });
        llFeedCanned03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cannedBean03.getAmount() != 0){
                    cannedBean03.setAmount(cannedBean03.getAmount()-1);
                    tvFeedCanned03.setText("數量："+cannedBean03.getAmount());
                    allCannedBean.setCannedBean01(cannedBean03);

                    catBean.setCatDuration(catBean.getCatDuration()+cannedBean03.getCannedDuration());
                    catBean.setCatSpeed(catBean.getCatSpeed()+cannedBean03.getCannedSpeed());
                    catBean.setCatUnitMoney(catBean.getCatUnitMoney()+cannedBean03.getCannedUnitMoney());

                    pbDuration.setProgress(catBean.getCatDuration());
                    pbUnitMoney.setProgress(catBean.getCatUnitMoney());
                    pbSpeed.setProgress(catBean.getCatSpeed());

                    cvFeedCanned.setVisibility(View.GONE);
                }else {
                    Common.showToast(getActivity(), "罐頭數量不足");
                }
            }
        });



        return view;
    }


}
