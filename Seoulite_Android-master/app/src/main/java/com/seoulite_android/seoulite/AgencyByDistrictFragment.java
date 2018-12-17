package com.seoulite_android.seoulite;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Layout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class AgencyByDistrictFragment extends Fragment {
    MainActivity activity;
    private static final String TAG = "AgencyByDistrictFrag";


    @BindView(R.id.text_agencylist_district_name)
    TextView mDistrictNameTextView;
    @BindView(R.id.list_agency_by_district)
    ListView mAgencyListView;
    @BindView(R.id.btn_living_info)
    Button btn_living_info;
    @BindView(R.id.btn_language_filter)
    Button btn_language_filter;
    @BindView(R.id.map_image) ImageView map_image;
    LinearLayout mlayout;
    Bundle bundle;
    String districtName;
    AgencyListCursorAdapter mcursorAdapter;
    Cursor cursor;

    Dialog dialog;
    DbHelper mDbHelper;
    SQLiteDatabase mDb;

    LinearLayout layout;


    //체크박스
    CheckBox engCheckBox;
    CheckBox chCheckBox;
    CheckBox jpCheckBox;
    CheckBox etcCheckBox;

    String langSql;
    Button btn_checkBox_lang;
    Cursor langCursor;

    @Override
    @SuppressWarnings("deprecation")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        mDbHelper = new DbHelper(getActivity()); //context 보통 mainActivity를 넣음.
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agency_by_district, container, false);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView: ");
        //Button btnSelectLan = view.findViewById(R.id.btn_select_language);;
        layout = (LinearLayout)view.findViewById(R.id.language_filter_page);
        layout.setVisibility(GONE);
        engCheckBox = view.findViewById(R.id.select_eng);
        chCheckBox = view.findViewById(R.id.select_ch);
        jpCheckBox = view.findViewById(R.id.select_jp);
        etcCheckBox =view.findViewById(R.id.select_etc);
        btn_checkBox_lang = view.findViewById(R.id.btn_select_language);
        ImageView mSetInvisible = view.findViewById(R.id.language_invisible);
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            districtName = args.getString("districtName");
            if (districtName == null) {
                Log.d(TAG, "setDistrirctName: districtName is null.");
            }
            mDistrictNameTextView.setText(districtName);
            final String selected = mDistrictNameTextView.getText().toString();
            Log.d("선택된 구이름", selected);

            changeImage(districtName);
            mDb = mDbHelper.getReadableDatabase();
            cursor = mDb.rawQuery("SELECT * FROM AGENCIES WHERE adr_gu_en='" + selected + "' order by agnc_nm_en", null);
            // final AgencyListCursorAdapter mcursorAdapter = new AgencyListCursorAdapter(getActivity(), cursor);
            mcursorAdapter = new AgencyListCursorAdapter(getActivity(), cursor);
            mcursorAdapter.notifyDataSetChanged();
            mAgencyListView.setAdapter(mcursorAdapter);


            mSetInvisible.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (!engCheckBox.isChecked() && !chCheckBox.isChecked() && !jpCheckBox.isChecked()&& !etcCheckBox.isChecked()) {
                    }else if(!engCheckBox.isChecked() && !chCheckBox.isChecked() && !jpCheckBox.isChecked() && etcCheckBox.isChecked()){
                        etcCheckBox.toggle();
                    }else if (!engCheckBox.isChecked() && !chCheckBox.isChecked() && jpCheckBox.isChecked()&& !etcCheckBox.isChecked()){
                        jpCheckBox.toggle();
                    }else if(!engCheckBox.isChecked() && chCheckBox.isChecked() && !jpCheckBox.isChecked()&& !etcCheckBox.isChecked()){
                        chCheckBox.toggle();
                    }else if(engCheckBox.isChecked() && !chCheckBox.isChecked() && !jpCheckBox.isChecked()&& !etcCheckBox.isChecked()){
                        engCheckBox.toggle();
                    }else if(!engCheckBox.isChecked() && !chCheckBox.isChecked() && jpCheckBox.isChecked()&& etcCheckBox.isChecked()){
                        jpCheckBox.toggle(); etcCheckBox.toggle();
                    }else  if(!engCheckBox.isChecked() && chCheckBox.isChecked() && !jpCheckBox.isChecked()&& etcCheckBox.isChecked()){
                        chCheckBox.toggle(); etcCheckBox.toggle();
                    }else if(engCheckBox.isChecked() && !chCheckBox.isChecked() && !jpCheckBox.isChecked()&& etcCheckBox.isChecked()){
                        engCheckBox.toggle(); etcCheckBox.toggle();
                    }else  if(engCheckBox.isChecked() && !chCheckBox.isChecked() && jpCheckBox.isChecked()&& !etcCheckBox.isChecked()){
                        engCheckBox.toggle();  jpCheckBox.toggle();
                    }else if(!engCheckBox.isChecked() && chCheckBox.isChecked() && jpCheckBox.isChecked()&& !etcCheckBox.isChecked()){
                        chCheckBox.toggle();  jpCheckBox.toggle();
                    }else if(engCheckBox.isChecked() && chCheckBox.isChecked() && !jpCheckBox.isChecked()&& !etcCheckBox.isChecked()) {
                        engCheckBox.toggle();  chCheckBox.toggle();
                    }else  if(!engCheckBox.isChecked() && chCheckBox.isChecked() && jpCheckBox.isChecked()&& etcCheckBox.isChecked()){
                        chCheckBox.toggle(); jpCheckBox.toggle(); etcCheckBox.toggle();
                    }else if(engCheckBox.isChecked() && !chCheckBox.isChecked() && jpCheckBox.isChecked()&& etcCheckBox.isChecked()){
                        engCheckBox.toggle(); jpCheckBox.toggle(); etcCheckBox.toggle();
                    }else if(engCheckBox.isChecked() && chCheckBox.isChecked() && !jpCheckBox.isChecked()&& etcCheckBox.isChecked()){
                        engCheckBox.toggle(); chCheckBox.toggle(); etcCheckBox.toggle();
                    }else  if(engCheckBox.isChecked() && chCheckBox.isChecked() && jpCheckBox.isChecked()&& !etcCheckBox.isChecked()){
                        engCheckBox.toggle(); chCheckBox.toggle(); jpCheckBox.toggle();
                    }else{
                        engCheckBox.toggle(); chCheckBox.toggle(); etcCheckBox.toggle(); jpCheckBox.toggle();
                    }
                    layout.setVisibility(GONE);
                }
            });

            btn_living_info.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (layout.getVisibility() == GONE) {
                        btn_living_info.setClickable(true);
                        LivingInfoFragment fragment = new LivingInfoFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("districtName", selected);
                        fragment.setArguments(bundle);

                        //getFragmentManager().beginTransaction().add(R.id.main_container, fragment).commit();
                        getFragmentManager().beginTransaction().replace(R.id.main_container, fragment)
                                .addToBackStack(null)
                                .commit();
                        //((MainActivity) getActivity()).replaceFragment(fragment, false);
                        btn_living_info.setClickable(true);
                    } else {
                        btn_living_info.setClickable(false);
                    }
                }
            });


            mAgencyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView id = view.findViewById(R.id.agency_id);
                    String selectedAgcId = id.getText().toString();

                    Log.d("선택된 에이전시 이름", selectedAgcId);

                    AgencyInfoFragment agencyInfoFragment = new AgencyInfoFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("agcId", selectedAgcId);
                    agencyInfoFragment.setArguments(bundle2);

                    getFragmentManager().beginTransaction().replace(R.id.main_container, agencyInfoFragment)
                            .addToBackStack(null)
                            .commit();
                    //((MainActivity)getActivity()).replaceFragment(new AgencyInfoFragment(), false);

                }
            });

            btn_language_filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(layout.getVisibility() == View.VISIBLE){
                        layout.setVisibility(View.GONE);
                    } else{
                        layout.setVisibility(View.VISIBLE);
                        layout.bringToFront();
                    }

                }
            });


            btn_checkBox_lang.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    //모든 언어를 선택하지 않았을 경우
                    if (!engCheckBox.isChecked() && !chCheckBox.isChecked() && !jpCheckBox.isChecked()&& !etcCheckBox.isChecked()) {
                        langSql = "";
                    }else if(!engCheckBox.isChecked() && !chCheckBox.isChecked() && !jpCheckBox.isChecked() && etcCheckBox.isChecked()){
                        langSql =  " AND (lang_etc = '포르투갈어' OR lang_etc = '러시아어' OR lang_etc = '스페인어')";
                        etcCheckBox.toggle();
                    }else if (!engCheckBox.isChecked() && !chCheckBox.isChecked() && jpCheckBox.isChecked()&& !etcCheckBox.isChecked()){
                        langSql = "AND lang_jp = 1";
                        jpCheckBox.toggle();
                    }else if(!engCheckBox.isChecked() && chCheckBox.isChecked() && !jpCheckBox.isChecked()&& !etcCheckBox.isChecked()){
                        langSql = "AND lang_cn = 1";
                        chCheckBox.toggle();
                    }else if(engCheckBox.isChecked() && !chCheckBox.isChecked() && !jpCheckBox.isChecked()&& !etcCheckBox.isChecked()){
                        langSql = "AND lang_en = 1";  //한개만 선택됨
                        engCheckBox.toggle();
                    }else if(!engCheckBox.isChecked() && !chCheckBox.isChecked() && jpCheckBox.isChecked()&& etcCheckBox.isChecked()){
                        langSql = "AND (lang_jp = 1 OR  (lang_etc = '포르투갈어' OR lang_etc = '러시아어' OR lang_etc = '스페인어'))";
                        jpCheckBox.toggle();
                        etcCheckBox.toggle();
                    }else  if(!engCheckBox.isChecked() && chCheckBox.isChecked() && !jpCheckBox.isChecked()&& etcCheckBox.isChecked()){
                        langSql = "AND (lang_cn = 1 OR  (lang_etc = '포르투갈어' OR lang_etc = '러시아어' OR lang_etc = '스페인어'))";
                        chCheckBox.toggle();
                        etcCheckBox.toggle();
                    }else if(engCheckBox.isChecked() && !chCheckBox.isChecked() && !jpCheckBox.isChecked()&& etcCheckBox.isChecked()){
                        langSql = "AND (lang_en = 1 OR  (lang_etc = '포르투갈어' OR lang_etc = '러시아어' OR lang_etc = '스페인어'))";
                        engCheckBox.toggle();
                        etcCheckBox.toggle();
                    }else  if(engCheckBox.isChecked() && !chCheckBox.isChecked() && jpCheckBox.isChecked()&& !etcCheckBox.isChecked()){
                        langSql = "AND (lang_en = 1 OR lang_jp = 1)";
                        engCheckBox.toggle();
                        jpCheckBox.toggle();
                    }else if(!engCheckBox.isChecked() && chCheckBox.isChecked() && jpCheckBox.isChecked()&& !etcCheckBox.isChecked()){
                        langSql = "AND (lang_cn = 1 OR lang_jp =1)";
                        chCheckBox.toggle();
                        jpCheckBox.toggle();
                    }else if(engCheckBox.isChecked() && chCheckBox.isChecked() && !jpCheckBox.isChecked()&& !etcCheckBox.isChecked()) {
                        langSql = "AND (lang_en = 1 OR lang_cn = 1)"; //2개선택
                        engCheckBox.toggle();
                        chCheckBox.toggle();
                    }else  if(!engCheckBox.isChecked() && chCheckBox.isChecked() && jpCheckBox.isChecked()&& etcCheckBox.isChecked()){
                        langSql = "AND (lang_cn = 1 OR lang_JP = 1 OR lang_etc = '포르투갈어' OR lang_etc = '러시아어' OR lang_etc = '스페인어')";
                        chCheckBox.toggle(); jpCheckBox.toggle(); etcCheckBox.toggle();
                    }else if(engCheckBox.isChecked() && !chCheckBox.isChecked() && jpCheckBox.isChecked()&& etcCheckBox.isChecked()){
                        langSql = "AND (lang_en = 1 OR lang_jp = 1 OR lang_etc = '포르투갈어' OR lang_etc = '러시아어' OR lang_etc = '스페인어')";
                        engCheckBox.toggle(); jpCheckBox.toggle(); etcCheckBox.toggle();
                    }else if(engCheckBox.isChecked() && chCheckBox.isChecked() && !jpCheckBox.isChecked()&& etcCheckBox.isChecked()){
                        langSql = "AND (lang_en = 1 OR lang_cn = 1 OR lang_etc = '포르투갈어' OR lang_etc = '러시아어' OR lang_etc = '스페인어')";
                        engCheckBox.toggle(); chCheckBox.toggle(); etcCheckBox.toggle();
                    }else  if(engCheckBox.isChecked() && chCheckBox.isChecked() && jpCheckBox.isChecked()&& !etcCheckBox.isChecked()){
                        langSql = "AND (lang_en = 1 OR lang_cn =1 OR lang_jp = 1)";
                        engCheckBox.toggle(); chCheckBox.toggle(); jpCheckBox.toggle();
                    }else{
                        langSql = "AND (lang_en = 1 OR lang_cn =1 OR lang_jp = 1 OR lang_etc = '포르투갈어' OR lang_etc = '러시아어' OR lang_etc = '스페인어')";
                        engCheckBox.toggle(); chCheckBox.toggle(); etcCheckBox.toggle(); jpCheckBox.toggle();
                    }
                    //toggle()

                    Log.d("새로운 sql: ", langSql);
                    //커서 업데이트 하기
                    langCursor = mDb.rawQuery("SELECT * FROM AGENCIES WHERE adr_gu_en='" + selected + "'" +langSql+" order by agnc_nm_en", null);
                    cursor = mcursorAdapter.swapCursor(langCursor);
                    mcursorAdapter.notifyDataSetChanged();
                    layout.setVisibility(GONE);
                    btn_living_info.setClickable(true);
                }
            });
            mAgencyListView.setAdapter(mcursorAdapter);
        }

        return view;
    }


    //구에 따라 이미지 다르게 보여줌.
    private void changeImage(String districtName) {
        switch (districtName) {
            case "Gangnam-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.gangnamgu));
                break;
            case "Gangdong-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.gangdonggu));
                break;
            case "Gangbuk-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.gangbukgu));
                break;
            case "Gangseo-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.gangseogu));
                break;
            case "Gwangjin-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.gwangjingu));
                break;
            case "Guro-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.gurogu));
                break;
            case "Geumcheon-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.geumcheongu));
                break;
            case "Nowon-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.nowongu));
                break;
            case "Dobong-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.dobonggu));
                break;
            case "Dongdaemun-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.dongdaemungu));
                break;
            case "Dongjak-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.dongjakgu));
                break;
            case "Seodaemun-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.seodaemungu));
                break;
            case "Seocho-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.seochogu));
                break;
            case "Seongdong-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.seongdonggu));
                break;
            case "Seongbuk-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.seongbukgu));
                break;
            case "Songpa-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.songpagu));
                break;
            case "Yangcheon-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.yangcheongu));
                break;
            case "Yeongdeungpo-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.yeongdeungpogu));
                break;
            case "Yongsan-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.yongsangu));
                break;
            case "Eunpyeong-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.eunpyeonggu));
                break;
            case "Jongno-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.jongnogu));
                break;
            case "Jung-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.junggu));
                break;
            case "Jungnang-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.jungnanggu));
                break;
            case "Gwanak-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.gwanakgu));
                break;
            case "Mapo-gu":
                map_image.setImageDrawable(getResources().getDrawable(R.drawable.mapogu));
                break;
        }
    }

    Context context;



    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }
}
