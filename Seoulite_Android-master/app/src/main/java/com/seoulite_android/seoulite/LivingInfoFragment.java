package com.seoulite_android.seoulite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LivingInfoFragment extends Fragment {
    ViewGroup view;
    MainActivity activity;

    DbHelper dbHelper;
    DistrictVO district;

    String distName;
    private boolean favorite_check = true;

    /*ImageView mapPopupImage;*/
    @BindView(R.id.living_info_map_image) ImageView living_info_map_image;
    @BindView(R.id.living_info_district_image) ImageView living_info_district_image;
    @BindView(R.id.living_info_favorite_star) ImageView living_info_favorite_star;
    @BindView(R.id.btn_living_info_moveToAgencies) Button mMoveToAgencies;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_living_info, container, false);

        ButterKnife.bind(this, view);
        //getting agency id from previous fragment.
        if(savedInstanceState ==null) {
            gettingDistName();
        }

        getDistrictInfo(distName); // db에서 전 화면에서 받아온 id를 이용해 sql select
        setTextViews(); //textview들을 setting

        changeImage(distName);
        changeDistrictImage(distName);

        checkIsFavorite(distName); //Favorite인지 아닌지 체크
        matchingFavoriteStar(favorite_check);

        mMoveToAgencies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgencyByDistrictFragment fragment = new AgencyByDistrictFragment();
                Bundle bundle = new Bundle();
                bundle.putString("districtName", distName);
                fragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.main_container, fragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity=null;
    }

    private void gettingDistName(){
        Bundle bundle = getArguments();
        if(bundle != null){
            distName = bundle.getString("districtName");
        }else{
            Toast.makeText(activity, "Can't get data. Try again.", Toast.LENGTH_LONG).show();
        }
    }

    private void getDistrictInfo(String distName){// db에서 전 화면에서 받아온 id를 이용해 select 후 agencyVO에 저장
        dbHelper = new DbHelper(activity);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sqlDistrict = "SELECT * FROM DISTRICTS where dist_en = '"+ distName+"'";
        Cursor cursor = db.rawQuery(sqlDistrict, null);
        while (cursor.moveToNext()) {
            district = new DistrictVO(cursor.getString(1), cursor.getString(2),
                    cursor.getInt(3), cursor.getInt(4), cursor.getFloat(5),
                    cursor.getInt(6), cursor.getInt(7), cursor.getString(8),
                    cursor.getString(9), cursor.getString(10), cursor.getString(11));
        } //cursor.getString(0) : return id
        cursor.close();
        db.close();
    }

    private void setTextViews(){
        TextView textviewDistrictName = view.findViewById(R.id.text_living_info_district_name);
        textviewDistrictName.setText(district.getDistrictEn());
        TextView textviewNearby = view.findViewById(R.id.text_living_info_nearby);
        textviewNearby.setText(district.getDistNearEn());
        TextView textviewFeatures = view.findViewById(R.id.text_living_info_features);
        textviewFeatures.setText(district.getFeatsEn());

        TextView textviewTotalPop = view.findViewById(R.id.text_living_info_totalPop);
        textviewTotalPop.setText(String.valueOf(district.getTotalPop()));
        TextView textviewForeignPop= view.findViewById(R.id.text_living_info_foreignPop);
        textviewForeignPop.setText(String.valueOf(district.getForeignPop()));
        TextView textviewForeignerRatio = view.findViewById(R.id.text_living_info_foreigner_ratio);
        textviewForeignerRatio.setText(String.valueOf(district.getForeignRate()));
        TextView textviewAverageFee = view.findViewById(R.id.text_living_info_average_fee);
        textviewAverageFee.setText(String.valueOf(district.getAvgRent()));
        TextView textviewRank = view.findViewById(R.id.text_living_info_rank);
        textviewRank.setText(String.valueOf(district.getRentRank()));


    }

    private void changeImage (String distName) {
        switch (distName) {
            case "Gangnam-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.gangnamgu));
                break;
            case "Gangdong-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.gangdonggu));
                break;
            case "Gangbuk-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.gangbukgu));
                break;
            case "Gangseo-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.gangseogu));
                break;
            case "Gwangjin-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.gwangjingu));
                break;
            case "Guro-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.gurogu));
                break;
            case "Geumcheon-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.geumcheongu));
                break;
            case "Nowon-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.nowongu));
                break;
            case "Dobong-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.dobonggu));
                break;
            case "Dongdaemun-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.dongdaemungu));
                break;
            case "Dongjak-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.dongjakgu));
                break;
            case "Seodaemun-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.seodaemungu));
                break;
            case "Seocho-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.seochogu));
                break;
            case "Sungdong-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.seongdonggu));
                break;
            case "Sungbuk-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.seongbukgu));
                break;
            case "Songpa-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.songpagu));
                break;
            case "Yangcheon-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.yangcheongu));
                break;
            case "Yeongdeungpo-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.yeongdeungpogu));
                break;
            case "Yongsan-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.yongsangu));
                break;
            case "Eunpyeong-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.eunpyeonggu));
                break;
            case "Jongno-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.jongnogu));
                break;
            case "Jung-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.junggu));
                break;
            case "Jungnang-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.jungnanggu));
                break;
            case "Gwanak-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.gwanakgu));
                break;
            case "Mapo-gu":
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.mapogu));
                break;
            default:
                living_info_map_image.setImageDrawable(getResources().getDrawable(R.drawable.agencyinfo_fax));
                break;
        }
    }

    private void changeDistrictImage(String distName) {
        switch (distName) {
            case "Gangnam-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_gangnamgu));
                break;
            case "Gangdong-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_gangdonggu));
                break;
            case "Gangbuk-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_gangbukgu));
                break;
            case "Gangseo-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_gangseogu));
                break;
            case "Gwangjin-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_gwangjingu));
                break;
            case "Guro-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_gurogu));
                break;
            case "Geumcheon-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_geumcheongu));
                break;
            case "Nowon-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_nowongu));
                break;
            case "Dobong-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_dobonggu));
                break;
            case "Dongdaemun-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_dongdaemungu));
                break;
            case "Dongjak-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_dongjakgu));
                break;
            case "Seodaemun-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_seodaemungu));
                break;
            case "Seocho-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_seochogu));
                break;
            case "Seongdong-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_sungdonggu));
                break;
            case "Seongbuk-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_sungbukgu));
                break;
            case "Songpa-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_songpa));
                break;
            case "Yangcheon-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_yangcheongu));
                break;
            case "Yeongdeungpo-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_yeongdeungpogu));
                break;
            case "Yongsan-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_yongsangu));
                break;
            case "Eunpyeong-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_eunpyeonggu));
                break;
            case "Jongno-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_jongnogu));
                break;
            case "Jung-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_junggu));
                break;
            case "Jungnang-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_jungnanggu));
                break;
            case "Gwanak-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_gwanakgu));
                break;
            case "Mapo-gu":
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.living_info_ci_mapogu));
                break;
            default:
                living_info_district_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_x_shape));
                break;
        }
    }

    private void checkIsFavorite(String distName){// db에서 전 화면에서 받아온 String를 이용해 favorite select
        dbHelper = new DbHelper(activity);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sqlFavorite = "SELECT * FROM FAVORITES where name = '"+ distName +"' and is_agency = 0;";
        Cursor cursor = db.rawQuery(sqlFavorite, null);
        if(cursor.getCount()==1){
            favorite_check = true;
            cursor.moveToNext();
        }else{
            favorite_check = false;
        }
        cursor.close();
        db.close();
    }

    private void matchingFavoriteStar(boolean check){
        if(check) living_info_favorite_star.setImageResource(R.drawable.agencyinfo_star_on);
        else living_info_favorite_star.setImageResource(R.drawable.agencyinfo_star_off);
    }

    private void favoriteDb(String mode, String distName){
        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        String sql;
        if(mode.equals("insert")){
            ContentValues values = new ContentValues();
            values.put("name", district.getDistrictEn());
            values.put("is_district", 1);
            values.put("is_agency", 0);
            values.put("memo", "");
            db.insert("FAVORITES", null, values);
        }else if(mode.equals("delete")){
            sql = "DELETE FROM FAVORITES WHERE name = '"+distName+"';";
            db.execSQL(sql);
        }
        db.close();
    }

    @OnClick(R.id.linearlayout_livingInfo_favorite)
    void favoriteInteraction(){
        if(favorite_check){ // 있는데 눌렀다 -> 삭제
            favorite_check = false;
            favoriteDb("delete", distName);
            matchingFavoriteStar(favorite_check);
        } else { // 추가
            favorite_check = true;
            favoriteDb("insert", distName);
            matchingFavoriteStar(favorite_check);
            showAddFavoriteAlert();
        }
    }

    private void showAddFavoriteAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Notification");
        builder.setMessage("District is successfully added to your favorites!"+
                ""+
                "Would you like to check your favorite?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                (activity).replaceFragment(new FavoritesFragment(), true);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
