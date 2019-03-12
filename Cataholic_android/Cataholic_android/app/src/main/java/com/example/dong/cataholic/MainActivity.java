package com.example.dong.cataholic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.dong.cataholic.fragment.ManageCatsFragment;
import com.example.dong.cataholic.fragment.RegisterFragment;
import com.example.dong.cataholic.model.AllCannedBean;
import com.example.dong.cataholic.model.CannedBean;
import com.example.dong.cataholic.model.CatMemberBean;
import com.example.dong.cataholic.model.MemberBean;
import com.example.dong.cataholic.model.StatusBean;
import com.example.dong.cataholic.webtask.FeedTask;
import com.example.dong.cataholic.webtask.LoadTask;
import com.example.dong.cataholic.webtask.LoginTask;
import com.example.dong.cataholic.webtask.MyTaskParams;
import com.example.dong.cataholic.fragment.WorkFragment;
import com.example.dong.cataholic.webtask.SaveTask;
import com.example.dong.cataholic.webtask.ShopCatTask;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<SmallCat> cats;
    ArrayList<SmallCat> nullCats;
    WorkFragment workFragment;
    Fragment temp;
    StatusBean statusBean;
    Thread t;

    LinearLayout lldata, llLogin;
    EditText etName, etPassword;
    Button btLogin, btRegister;
    LoginTask loginTask;
    ShopCatTask shopCatTask;


    MemberBean memberBean = null;
    AllCannedBean allCannedBean;
    boolean reStartFragment = false;


    long beginTime;
    long income = 50; // max=999,999,999,999元
    long saveTime = 0;
    boolean smoke = false;
    int pressureStatus = 0; // max=21600 6*60*60單位(共6小時)
    int oGenerateSpeed = 10; // 240秒/顆 max=60秒/顆 初始狀態
    int oMoney = 10;// 10元 max=100元 初始狀態
    int AEffectTime = 0; // 30分鐘 max=6*60*60(6小時)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        System.out.println("onCreate");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            MemberBean memberBean = (MemberBean) bundle.get("memberBean");
            if (memberBean != null) {
                etName.setText(memberBean.getMemberId());
                etPassword.setText(memberBean.getMemberPassword());
            }
        }

        if (allCannedBean == null) {
            allCannedBean = new AllCannedBean();
            CannedBean cannedBean01 = new CannedBean("藥效強化", 5, 5, 0, 0, 0);
            CannedBean cannedBean02 = new CannedBean("速度強化", 5, 0, 5, 0, 0);
            CannedBean cannedBean03 = new CannedBean("質量強化", 5, 0, 0, 5, 0);
            allCannedBean.setCannedBean01(cannedBean01);
            allCannedBean.setCannedBean02(cannedBean02);
            allCannedBean.setCannedBean03(cannedBean03);
        }


        lldata = findViewById(R.id.lldata);
        llLogin = findViewById(R.id.llLogin);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llLogin.setVisibility(View.GONE);

                RegisterFragment registerFragment = new RegisterFragment();
                temp = registerFragment;

                FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.content, registerFragment);
                fragmentTransaction.commit();

                switchFragment(registerFragment);

            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean inputOK = false;
                MyTaskParams loginTaskParams = null;
                MyTaskParams catsTaskParams = null;

                if (etName.getText().toString() != null && etPassword.getText().toString() != null) {
                    inputOK = true;
                    String url = Common.URL + "/login.do";
                    String url2 = Common.URL + "/catShop.do";
                    MemberBean memberBeanToLogin = new MemberBean();
                    memberBeanToLogin.setMemberId(etName.getText().toString());
                    memberBeanToLogin.setMemberPassword(etPassword.getText().toString());
                    loginTaskParams = new MyTaskParams(url, memberBeanToLogin, MainActivity.this);
                    catsTaskParams = new MyTaskParams(url2, null, MainActivity.this);
                }


                if (inputOK && Common.networkConnected(MainActivity.this)) {

                    try {
                        loginTask = new LoginTask();
                        memberBean = loginTask.execute(loginTaskParams).get();

                        shopCatTask = new ShopCatTask();
                        shopCatTask.execute(catsTaskParams);

                        if (memberBean == null) {
                            Common.showToast(MainActivity.this, "No Login");
                        } else {
                            goWork(memberBean);
                        }
                    } catch (Exception e) {
                        Log.e("login", e.toString());
                    }
                } else {
                    Common.showToast(MainActivity.this, "No Network Connection");

                }
            }

            private void goWork(MemberBean memberBean) {
                lldata.setVisibility(View.VISIBLE);
                llLogin.setVisibility(View.GONE);
                beginTime = System.currentTimeMillis() / 1000;

                workFragment = new WorkFragment();
                temp = workFragment;

                statusBean = load(memberBean);
                if (statusBean == null){
                    statusBean = new StatusBean(null, beginTime, income, saveTime, smoke, pressureStatus, oGenerateSpeed, oMoney, AEffectTime, cats, nullCats, null,memberBean.getMemberKey());
                }

                FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.content, workFragment);
                fragmentTransaction.commit();

                Bundle bundle = new Bundle();
                bundle.putSerializable("updateWork", true);
                bundle.putSerializable("memberBean", memberBean);
                bundle.putSerializable("nowStatus", statusBean);
                bundle.putSerializable("workFragment", workFragment);
                bundle.putSerializable("allCannedBean", allCannedBean);
                workFragment.setArguments(bundle);
                switchFragment(workFragment);
                setTitle("Work");
            }
        });

    }

    public void switchFragment(Fragment fragment) {
        if (fragment != temp) {
            if (fragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().hide(temp)
                        .show(fragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().hide(temp)
                        .add(R.id.content, fragment).commit();

            }
            temp = fragment;
        }
    }
    public StatusBean load(MemberBean memberBean) {
        if (Common.networkConnected(MainActivity.this)) {
            String url = Common.URL + "/load.do";
            try {
//                System.out.println("load");
                MyTaskParams myTaskParams = new MyTaskParams(url, memberBean, MainActivity.this);

                LoadTask loadTask= new LoadTask();
                StatusBean statusBean = loadTask.execute(myTaskParams).get();
                if (statusBean != null){
                    System.out.println("load OK");
                    return statusBean;
                }else {
                    System.out.println("No load");
                    return null;
                }

            } catch (Exception e) {
                Log.e("load", e.toString());
            }
        } else {
            Common.showToast(MainActivity.this, "No Network Connection");
        }
        return null;
    }


    public void save(StatusBean statusBean) {
        if (Common.networkConnected(MainActivity.this)) {
            String url = Common.URL + "/save.do";
            try {
//                System.out.println("save");
                MyTaskParams myTaskParams = new MyTaskParams(url, statusBean, MainActivity.this);

                SaveTask saveTask= new SaveTask();
                Integer i = saveTask.execute(myTaskParams).get();
                if (i != null){
                    System.out.println("save OK");
                }else {
                    System.out.println("No save");
                }

            } catch (Exception e) {
                Log.e("save", e.toString());
            }
        } else {
            Common.showToast(MainActivity.this, "No Network Connection");
        }
    }

    public Thread getThreadByName(String threadName) {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals(threadName)) return t;
        }
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart");
        if (reStartFragment == true) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, temp)
                    .commit();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            StatusBean statusBean = (StatusBean) bundle.get("nowStatus");
            if (statusBean != null) {
                save(statusBean);
            }
        }

        reStartFragment = true;
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
    }
}

