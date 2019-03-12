package com.example.dong.cataholic.fragment;

import android.content.Intent;
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
import com.example.dong.cataholic.R;
import com.example.dong.cataholic.model.MemberBean;
import com.example.dong.cataholic.webtask.MyTaskParams;
import com.example.dong.cataholic.webtask.RegisterTask;
import com.github.nisrulz.sensey.Sensey;

import java.io.Serializable;

public class RegisterFragment extends Fragment implements Serializable {

    MainActivity mainActivity;

    EditText etId, etPassword, etPassword2, etDisplayName;
    Button btRegister;
    private RegisterTask registerTask;
    MemberBean memberBean;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        Sensey.getInstance().init(mainActivity);
        btRegister = view.findViewById(R.id.btRegister);
        etId = view.findViewById(R.id.etId);
        etPassword = view.findViewById(R.id.etPassword);
        etPassword2 = view.findViewById(R.id.etPassword2);
        etDisplayName = view.findViewById(R.id.etDisplayName);

        btRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (etId != null){
                    memberBean = new MemberBean();
                    memberBean.setMemberId(etId.getText().toString());
                    memberBean.setMemberPassword(etPassword.getText().toString());
                    memberBean.setMemberDisplayName(etDisplayName.getText().toString());

                    if (Common.networkConnected(mainActivity)) {
                        String url = Common.URL + "/register.do";

                        try {
                            MyTaskParams myTaskParams = new MyTaskParams(url, memberBean, mainActivity);
                            registerTask = new RegisterTask();
                            memberBean = registerTask.execute(myTaskParams).get();

                        } catch (Exception e) {
                            Log.e("register", e.toString());
                        }
                        if (memberBean == null) {
                            Common.showToast(mainActivity, "No register");
                        } else {
                            goLogin(memberBean);
                        }
                    } else {
                        Common.showToast(mainActivity, "No Network Connection");
                    }

                }
            }

            private void goLogin(MemberBean memberBean) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("memberBean",memberBean);
                intent.putExtra("bundle",bundle);
                startActivity(intent);

            }
        });



        return view;
    }


}
