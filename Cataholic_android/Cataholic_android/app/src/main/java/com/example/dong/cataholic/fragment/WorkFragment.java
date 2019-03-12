package com.example.dong.cataholic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dong.cataholic.model.AllCannedBean;
import com.example.dong.cataholic.MainActivity;
import com.example.dong.cataholic.model.MemberBean;
import com.example.dong.cataholic.model.StatusBean;
import com.example.dong.cataholic.R;
import com.example.dong.cataholic.SmallCat;
import com.example.dong.cataholic.model.CatBean;
import com.github.nisrulz.sensey.Sensey;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class WorkFragment extends Fragment implements Serializable {
    List<GifImageView> gifImageViews;
    List<SmallCat> cats; //聚集的貓數量 max=60隻
    List<SmallCat> nullCats; //未聚集的貓數量 max=60隻
    RelativeLayout touchview;
    Thread t;
    StatusBean statusBean;
    StatusBean saveStatus;

    TextView tvIncome, tvPressure, nowTime;
    Button btSmokeCat;
    MainActivity mainActivity;
    GifImageView gifCat001, gifCat002, gifCat003, gifCat004, gifCat005, gifCat006, gifCat007, gifCat008, gifCat009;

    ShopFragment shopFragment;
    WorkFragment workFragment;
    Bundle bundle;
    MemberBean memberBean;
    AllCannedBean allCannedBean;

    long sNowTime;
    long income; // max=999,999,999,999元
    boolean smoke;
    long saveTime;
    int catsMax = 6;
    int oGenerateSpeed = 10;
    int pGenerateSpeed = 15; //壓力值滿的速度
    int generateSpeed; // 現況
    int oMoney = 10;
    int pMoney = 5; //壓力值滿的單位金額
    int money;// 現況
    int pressureStatus = 0; // max=21600 6*60*60單位(共6小時)
    int pressureSpeed = 1; // +1單位/秒
    int pressureMax = 300;
    int AEffectTime; // 30分鐘 max=6*60*60(6小時)
    long beginTime;
    boolean updateWork;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        System.out.println("Work onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        Sensey.getInstance().init(mainActivity);
        System.out.println("Work onCreateView");
        gifCat001 = view.findViewById(R.id.smallCat001);
        gifCat002 = view.findViewById(R.id.smallCat002);
        gifCat003 = view.findViewById(R.id.smallCat003);
        gifCat004 = view.findViewById(R.id.smallCat004);
        gifCat005 = view.findViewById(R.id.smallCat005);
        gifCat006 = view.findViewById(R.id.smallCat006);
        gifCat007 = view.findViewById(R.id.smallCat007);
        gifCat008 = view.findViewById(R.id.smallCat008);
        gifCat009 = view.findViewById(R.id.smallCat009);

        gifImageViews = new ArrayList<>();
        gifImageViews.add(gifCat001);
        gifImageViews.add(gifCat002);
        gifImageViews.add(gifCat003);
        gifImageViews.add(gifCat004);
        gifImageViews.add(gifCat005);
        gifImageViews.add(gifCat006);
        gifImageViews.add(gifCat007);
        gifImageViews.add(gifCat008);
        gifImageViews.add(gifCat009);


        tvIncome = mainActivity.findViewById(R.id.tvIncome);
        tvPressure = mainActivity.findViewById(R.id.tvPressure);
        touchview = view.findViewById(R.id.touchview);
        nowTime = view.findViewById(R.id.nowTime);
        btSmokeCat = view.findViewById(R.id.btSmokeCat);

        btSmokeCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusBean = new StatusBean(null, beginTime, income, saveTime, smoke, pressureStatus, oGenerateSpeed, oMoney, AEffectTime, cats, nullCats, null, memberBean.getMemberKey());
                shopFragment = new ShopFragment();

                bundle.remove("nowStatus");
                bundle.putSerializable("nowStatus", statusBean);
                bundle.putSerializable("workFragment", workFragment);
                bundle.putSerializable("memberBean", memberBean);
                bundle.putSerializable("allCannedBean", allCannedBean);
                shopFragment.setArguments(bundle);
                mainActivity.switchFragment(shopFragment);
            }
        });

        touchview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                for (int i = 0; i < touchview.getChildCount(); i++) {
                    GifImageView touchCat = (GifImageView) touchview.getChildAt(i);

                    if (touchCat.getVisibility() == View.VISIBLE) {
                        if (checkInterSection(touchCat, event.getRawX(), event.getRawY())) {
                            int smallCatId = 0;
                            for (SmallCat smallCat : cats) {
                                if (smallCat.getSmallCatId() == touchCat.getId()) {
                                    smallCatId = smallCat.getSmallCatId();
                                    smallCat.setSmallCatVisibility(View.INVISIBLE);
                                    touchCat.setVisibility(smallCat.getSmallCatVisibility());
                                    income += smallCat.getSmallCatMoney();
                                    nullCats.add(smallCat);
                                }
                            }
                            for (SmallCat smallCat : nullCats) {
                                if (smallCat.getSmallCatId() == smallCatId) {
                                    cats.remove(smallCat);
                                }
                            }

                        }
                    }
                }
                return false;
            }
        });
        updateViewData();

        return view;
    }

    private void updateViewData() {
        t = mainActivity.getThreadByName("workThread");
        if (t == null) {
            t = new Thread("workThread") {
                @Override
                public void run() {
                    try {
                        while (!interrupted()) {
                            Thread.sleep(1000);
                            mainActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateData();
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                    }
                }
            };
            t.start();
        }
    }

    private void updateData() {

        bundle = getArguments();
        if (bundle != null) {
            updateWork = (boolean) bundle.get("updateWork");
            if (updateWork == true) {
                bundle.putSerializable("updateWork", false);
                allCannedBean = (AllCannedBean) bundle.get("allCannedBean");
                workFragment = (WorkFragment) bundle.get("workFragment");
                CatBean catBean = (CatBean) bundle.get("smokeCat");
                StatusBean statusBeanStart = (StatusBean) bundle.get("nowStatus");
                memberBean = (MemberBean) bundle.get("memberBean");
                allCannedBean = (AllCannedBean) bundle.get("allCannedBean");

                System.out.println("statusBeanStart: "+statusBeanStart.getPressureStatus());

                if (statusBeanStart != null) {
                    beginTime = statusBeanStart.getBeginTime();
                    income = statusBeanStart.getIncome(); // max=999,999,999,999元
                    pressureStatus = statusBeanStart.getPressureStatus();
                    saveTime = statusBeanStart.getSaveTime();
                    smoke = statusBeanStart.getSmoke();
//                    pressureStatus = nowStatusStart.getPressureStatus(); // max=21600 6*60*60單位(共6小時)
                    generateSpeed = statusBeanStart.getGenerateSpeed(); // 240秒/顆 max=60秒/顆 初始狀態
                    money = statusBeanStart.getMoney();// 10元 max=100元 初始狀態
                    AEffectTime = statusBeanStart.getEffectTime();
                    nullCats = statusBeanStart.getNullSmallCats();
                    if (nullCats == null) {
                        nullCats = new ArrayList<>();
                        nullCats.add(new SmallCat(0, R.id.smallCat001, View.INVISIBLE));
                        nullCats.add(new SmallCat(0, R.id.smallCat002, View.INVISIBLE));
                        nullCats.add(new SmallCat(0, R.id.smallCat003, View.INVISIBLE));
                        nullCats.add(new SmallCat(0, R.id.smallCat004, View.INVISIBLE));
                        nullCats.add(new SmallCat(0, R.id.smallCat005, View.INVISIBLE));
                        nullCats.add(new SmallCat(0, R.id.smallCat006, View.INVISIBLE));
                        nullCats.add(new SmallCat(0, R.id.smallCat007, View.INVISIBLE));
                        nullCats.add(new SmallCat(0, R.id.smallCat008, View.INVISIBLE));
                        nullCats.add(new SmallCat(0, R.id.smallCat009, View.INVISIBLE));
                    }
                    cats = statusBeanStart.getSmallCats();
                    if (cats == null) {
                        cats = new ArrayList<>();
                    } else {
                        for (SmallCat smallCat : cats) {
                            for (GifImageView giv : gifImageViews) {
                                if (smallCat.getSmallCatId() == giv.getId()) {
                                    giv.setVisibility(smallCat.getSmallCatVisibility());
                                }
                            }
                        }
                    }
                }

                if (catBean != null) {
                    saveTime = System.currentTimeMillis() / 1000;
                    generateSpeed -= catBean.getCatSpeed();
                    if (generateSpeed < 1) generateSpeed = 1;
                    money += catBean.getCatUnitMoney();
                    AEffectTime = catBean.getCatDuration();
                    smoke = true;
                    pressureStatus = 0;
                    income -= catBean.getCatPrice();
                }

            }
        }
        sNowTime = System.currentTimeMillis() / 1000; //每秒更新現在時間

        if (smoke & (saveTime + AEffectTime <= sNowTime)) {
            smoke = false;
            generateSpeed = oGenerateSpeed;
            money = oMoney;
        }

        if (pressureStatus < pressureMax) {
            pressureStatus += pressureSpeed; //每秒增加單位壓力
        } else {
            generateSpeed = pGenerateSpeed;
            money = pMoney;
        }

        if ((sNowTime - beginTime) % generateSpeed == 0) { //判斷是否聚集一隻貓
            if (cats.size() < gifImageViews.size()) { //判斷聚集最大值不再聚集

                int i = nullCats.size();
                int randomNullCat = (int) (Math.random() * nullCats.size());

                SmallCat sc = nullCats.get(randomNullCat);
                int smallCatId = sc.getSmallCatId();
                sc.setSmallCatMoney(money);
                sc.setSmallCatVisibility(View.VISIBLE);

                for (SmallCat smallCat : nullCats) {
                    if (smallCat.getSmallCatId() == smallCatId) {
                        cats.add(smallCat);
                    }
                }
                for (SmallCat smallCat : cats) {
                    if (smallCat.getSmallCatId() == smallCatId) {
                        nullCats.remove(smallCat);
                    }
                }
            }
        }

        for (SmallCat smallCat : cats) {
            for (GifImageView giv : gifImageViews) {
                if (smallCat.getSmallCatId() == giv.getId()) {
                    giv.setVisibility(smallCat.getSmallCatVisibility());
                }
            }
        }
        String catData = "";

        for (SmallCat data : cats) {
            catData += data.getSmallCatMoney() + ", ";
        }

        int ttt = (int) (AEffectTime - (sNowTime - saveTime));
        if (ttt < 0) ttt = 0;

        nowTime.setText(
                "聚集速度" + generateSpeed + "秒/隻\n" +
                        "藥效時間:" + ttt + "\n" +
                        "顯示狀態" + catData + "\n");

        tvIncome.setText("收入： " + income + "元");
        tvPressure.setText("壓力： " + pressureStatus);


        if (saveStatus == null){
            saveStatus = new StatusBean(null, beginTime, income, saveTime, smoke, pressureStatus, oGenerateSpeed, oMoney, AEffectTime, cats, nullCats, sNowTime, memberBean.getMemberKey());
        }else {
            saveStatus.setIncome(income);
            saveStatus.setSaveTime(saveTime);
            saveStatus.setSmoke(smoke);
            saveStatus.setPressureStatus(pressureStatus);
            saveStatus.setEffectTime(AEffectTime);
            saveStatus.setSmallCats(cats);
            saveStatus.setNullSmallCats(nullCats);
            saveStatus.setEndTime(sNowTime);
        }
        bundle.putSerializable("nowStatus", saveStatus);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        mainActivity.setIntent(intent);
    }

    private boolean checkInterSection(GifImageView view, float rawX, float rawY) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        int width = view.getWidth();
        int height = view.getHeight();
        //Check the intersection of point with rectangle achieved
        boolean b1 = rawX < x;
        boolean b2 = rawX > x + width;
        boolean b3 = rawY < y;
        boolean b4 = rawY > y + height;
        boolean bb = (b1 || b2 || b3 || b4);

        return (!bb);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        gifImageViews.clear();
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("Work onPause");
    }
}
