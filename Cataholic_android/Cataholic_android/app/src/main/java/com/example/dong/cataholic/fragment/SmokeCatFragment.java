package com.example.dong.cataholic.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dong.cataholic.Common;
import com.example.dong.cataholic.MainActivity;
import com.example.dong.cataholic.model.MemberBean;
import com.example.dong.cataholic.model.StatusBean;
import com.example.dong.cataholic.R;
import com.example.dong.cataholic.model.CatBean;
import com.example.dong.cataholic.webtask.MyTaskParams;
import com.example.dong.cataholic.webtask.ShopCatTask;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class SmokeCatFragment extends Fragment implements Serializable {
    private RecyclerView rvCats;
    private ShopCatTask shopCatTask;
    private Button btSmokeBack;
    MainActivity mainActivity;
    Long income;
    StatusBean statusBean;
    Bundle bundle;
    WorkFragment workFragment;
    ShopFragment shopFragment;
    TextView tvIncome;
    TextView tvPressure;
    int pressureStatus = 0;
    MemberBean memberBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smokecat, container,false);

        btSmokeBack = view.findViewById(R.id.btSmokeBack);
        mainActivity = (MainActivity) getActivity();
        tvIncome = mainActivity.findViewById(R.id.tvIncome);
        tvPressure = mainActivity.findViewById(R.id.tvPressure);


        bundle = getArguments();
        if (bundle != null) {
            workFragment = (WorkFragment) bundle.get("workFragment");
            statusBean = (StatusBean) bundle.get("nowStatus");
            memberBean = (MemberBean) bundle.get("memberBean");
            income = statusBean.getIncome();
            pressureStatus = statusBean.getPressureStatus();
        }

        btSmokeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopFragment = new ShopFragment();
                bundle.putSerializable("workFragment",workFragment);
                bundle.putSerializable("nowStatus", statusBean);
                bundle.putSerializable("memberBean", memberBean);
                shopFragment.setArguments(bundle);
                mainActivity.switchFragment(shopFragment);
            }
        });


        rvCats = view.findViewById(R.id.rvSmokeCat);
        rvCats.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "/catShop.do";
            List<CatBean> catList = null;
            try {
                MyTaskParams myTaskParams = new MyTaskParams(url, null,mainActivity);
                shopCatTask = new ShopCatTask();

                catList = Arrays.asList(shopCatTask.execute(myTaskParams).get());
            } catch (Exception e) {
                Log.e("SmokeCat", e.toString());
            }
            if (catList == null || catList.isEmpty()) {
                Common.showToast(getActivity(), "No Cats Found");
            } else {
                rvCats.setAdapter(new CatAdapter(getActivity(), catList));            }
        } else {
            Common.showToast(getActivity(), "No Network Connection");
        }


        tvIncome.setText("收入： " + income + "元");
        tvPressure.setText("壓力： " + pressureStatus);

        return view;
    }

    private class CatAdapter extends RecyclerView.Adapter {
        private LayoutInflater layoutInflater;
        private List<CatBean> catList;

        public CatAdapter(Context context, List<CatBean> catList) {
            layoutInflater = LayoutInflater.from(context);
            this.catList = catList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.item_view_smokecat, parent, false);
            return new CatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            final CatBean catBean = catList.get(position);
            CatViewHolder catViewHolder = (CatViewHolder) holder;
            catViewHolder.imageView.setImageResource(getResId(catBean.getImageId(), R.drawable.class));
            catViewHolder.tvSmokeName.setText(catBean.getCatName());
            catViewHolder.tvCatGenerate.setText("工作效率："+String.valueOf(catBean.getCatSpeed()));
            catViewHolder.tvCatMoney.setText("工作品質："+String.valueOf(catBean.getCatUnitMoney()));
            catViewHolder.tvCatEffectTime.setText("藥效時間："+String.valueOf(catBean.getCatDuration()));
            catViewHolder.tvCatPrice.setText(String.valueOf(catBean.getCatPrice())+"元");
            catViewHolder.cvSmokeCat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (income != null & income >= catBean.getCatPrice()) {
                        bundle = getArguments();
                        if (bundle != null) {
//                            smokeCatFragment = (SmokeCatFragment) bundle.get("smokeCatFragment");
                            workFragment = (WorkFragment) bundle.get("workFragment");
                            statusBean = (StatusBean) bundle.get("nowStatus");
                            income = statusBean.getIncome();
                        }
                        bundle.putSerializable("updateWork",true);
                        bundle.putSerializable("smokeCat",catBean);
                        bundle.putSerializable("nowStatus", statusBean);
//                        bundle.putSerializable("smokeCatFragment",smokeCatFragment);
                        bundle.putSerializable("workFragment",workFragment);

                        workFragment.setArguments(bundle);
                        mainActivity.switchFragment(workFragment);
                        mainActivity.setTitle("Work");
                    }else {
                        Toast.makeText(mainActivity, "金額不足", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return catList.size();
        }

        private class CatViewHolder extends RecyclerView.ViewHolder {
            CardView cvSmokeCat;
            ImageView imageView;
            TextView tvSmokeName,tvCatGenerate,tvCatMoney,tvCatEffectTime, tvCatPrice;
            public CatViewHolder(View view) {
                super(view);
                cvSmokeCat = view.findViewById(R.id.cvSmokeCat);
                imageView = view.findViewById(R.id.ivSmokeCat);
                tvSmokeName = view.findViewById(R.id.tvSmokeName);
                tvCatGenerate = view.findViewById(R.id.tvCatGenerate);
                tvCatMoney = view.findViewById(R.id.tvCatMoney);
                tvCatEffectTime = view.findViewById(R.id.tvCatEffectTime);
                tvCatPrice = view.findViewById(R.id.tvCatPrice);
            }
        }

    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
