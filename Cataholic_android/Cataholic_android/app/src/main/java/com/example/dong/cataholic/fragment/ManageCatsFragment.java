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

import com.example.dong.cataholic.Common;
import com.example.dong.cataholic.MainActivity;
import com.example.dong.cataholic.model.StatusBean;
import com.example.dong.cataholic.R;
import com.example.dong.cataholic.model.AllCannedBean;
import com.example.dong.cataholic.model.CatBean;
import com.example.dong.cataholic.model.MemberBean;
import com.example.dong.cataholic.webtask.ManageTask;
import com.example.dong.cataholic.webtask.MyTaskParams;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static com.example.dong.cataholic.fragment.SmokeCatFragment.getResId;

public class ManageCatsFragment extends Fragment implements Serializable {
    private RecyclerView rvMCats;
    private ManageTask manageTask;
    private Button btManageBack;
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
    AllCannedBean allCannedBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_cats, container, false);

        btManageBack = view.findViewById(R.id.btManageBack);
        mainActivity = (MainActivity) getActivity();
        tvIncome = mainActivity.findViewById(R.id.tvIncome);
        tvPressure = mainActivity.findViewById(R.id.tvPressure);


        bundle = getArguments();
        if (bundle != null) {
            workFragment = (WorkFragment) bundle.get("workFragment");
            statusBean = (StatusBean) bundle.get("nowStatus");
            memberBean = (MemberBean) bundle.get("memberBean");
            allCannedBean = (AllCannedBean) bundle.get("allCannedBean");
            income = statusBean.getIncome();
            pressureStatus = statusBean.getPressureStatus();
        }

        btManageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopFragment = new ShopFragment();
                bundle.putSerializable("workFragment", workFragment);
                bundle.putSerializable("nowStatus", statusBean);
                bundle.putSerializable("memberBean", memberBean);
                bundle.putSerializable("allCannedBean", allCannedBean);
                shopFragment.setArguments(bundle);
                mainActivity.switchFragment(shopFragment);
            }
        });


        rvMCats = view.findViewById(R.id.rvManageCat);
        rvMCats.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "/manage.do";
            List<CatBean> catList = null;
            try {
                MyTaskParams myTaskParams = new MyTaskParams(url, memberBean);
                manageTask = new ManageTask();
                catList = Arrays.asList(manageTask.execute(myTaskParams).get());

            } catch (Exception e) {
                Log.e("ManageCats", e.toString());
            }
            if (catList == null || catList.isEmpty()) {
                Common.showToast(getActivity(), "No Cats Found");
            } else {
                rvMCats.setAdapter(new CatAdapter(getActivity(), catList));
            }
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
            catViewHolder.tvCatGenerate.setText("工作效率：" + String.valueOf(catBean.getCatSpeed()));
            catViewHolder.tvCatMoney.setText("工作品質：" + String.valueOf(catBean.getCatUnitMoney()));
            catViewHolder.tvCatEffectTime.setText("藥效時間：" + String.valueOf(catBean.getCatDuration()));
            catViewHolder.tvCatPrice.setText(String.valueOf(catBean.getCatPrice()) + "元");
            catViewHolder.cvSmokeCat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    bundle.putSerializable("manageCats", catBean);
                    bundle.putSerializable("nowStatus", statusBean);
                    bundle.putSerializable("memberBean", memberBean);
                    bundle.putSerializable("workFragment", workFragment);
                    bundle.putSerializable("allCannedBean", allCannedBean);

                    DetailCatFragment detailCatFragment = new DetailCatFragment();
                    detailCatFragment.setArguments(bundle);
                    mainActivity.switchFragment(detailCatFragment);


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
            TextView tvSmokeName, tvCatGenerate, tvCatMoney, tvCatEffectTime, tvCatPrice;

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


}
