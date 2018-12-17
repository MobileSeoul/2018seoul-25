package com.seoulite_android.seoulite;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LanguageSettingFragment extends Fragment{
    MainActivity activity;
    Button langSettingEn;
    Button langSettingKr;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity=null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_language_setting, container, false);

        langSettingEn = (Button) view.findViewById(R.id.btn_language_setting_en);
        langSettingKr = (Button) view.findViewById(R.id.btn_language_setting_kr);

        langSettingEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLocale("en");
                refreshFragment();
            }
        });
        langSettingKr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLocale("ko");
                refreshFragment();
            }
        });
        return view;
    }

    private void changeLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        activity.getBaseContext().getResources().updateConfiguration(config,
                activity.getBaseContext().getResources().getDisplayMetrics());
        //activity.getApplicationContext().getResources().updateConfiguration(config,null);
        /*for test
        System.out.println("getDisplayLanguage : " + locale.getDisplayLanguage());
        System.out.println("getLanguage : " + locale.getLanguage());
        System.out.println("getDisplayCountry : " + locale.getDisplayCountry());
        System.out.println("getCountry : " + locale.getCountry());
        */
    }

    private void refreshFragment(){
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new LanguageSettingFragment(), null)
                .commit();
    }

}
