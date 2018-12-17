package com.seoulite_android.seoulite;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AgencyInfoFragment extends Fragment implements OnMarkerClickListener, OnMapReadyCallback {
    ViewGroup rootView;
    MainActivity activity;
    SupportMapFragment mapFragment;

    private Geocoder geocoder;
    private GoogleMap mMap;
    private Marker mAgency;

    private double lat;
    private double lon;
    private String agencyAddress;
    //todo: favorite check

    private boolean favorite_check = true;

    private int agencyId;

    //db test
    DbHelper dbHelper;
    AgencyVO agency;

    Snackbar snackBar;

    @BindView(R.id.agencyinfo_favorite_star) ImageView favorite_star;
    @BindView(R.id.agencyinfo_second_tel_layout) LinearLayout second_tel_layout;
    @BindView(R.id.imageview_agencyinfo_us) ImageView us_flag;
    @BindView(R.id.imageview_agencyinfo_china) ImageView cn_flag;
    @BindView(R.id.imageview_agencyinfo_japan) ImageView jp_flag;
    @BindView(R.id.imageview_agencyinfo_spain) ImageView sp_flag;
    @BindView(R.id.imageview_agencyinfo_portugal) ImageView pt_flag;
    @BindView(R.id.imageview_agencyinfo_russia) ImageView ru_flag;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        snackBar.dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_agency_info, container, false);
        //getLanguage

        ButterKnife.bind(this, rootView);

        //getting agency id from previous fragment.
        if(savedInstanceState ==null) {
            gettingAgencyId();
        }

        getAgencyInfo(agencyId); // db에서 전 화면에서 받아온 id를 이용해 sql select

        setTextViews(); //textview들을 setting
        setFlags(); // falg setting

        createSnackbar();//snackbar
        checkIsFavorite(agencyId); //Favorite인지 아닌지 체크
        matchingFavoriteStar(favorite_check);

        setAddressLatLng(agencyAddress); // 주소를 google map parameter에 맞추기.

        //google maps
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.agencyinfo_map);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapFragment.getMapAsync(this);
    }

    @OnClick(R.id.linearlaout_agencyinfo_favorite)
    void favoriteInteraction(){
        if(favorite_check){ // 있는데 눌렀다 -> 삭제
            favorite_check = false;
            favoriteDb("delete", agencyId);
            matchingFavoriteStar(favorite_check);
        } else { // 추가
            favorite_check = true;
            favoriteDb("insert",agencyId);
            matchingFavoriteStar(favorite_check);
            showAddFavoriteAlert();
        }
    }

    private void gettingAgencyId(){
        Bundle bundle = getArguments();
        if(bundle != null){
            String stragcId = bundle.getString("agcId");
            agencyId = Integer.parseInt(stragcId);
        }else{
            Toast.makeText(activity, "Can't get data. Try again.", Toast.LENGTH_LONG).show();
        }
    }


    private void getAgencyInfo(int id){// db에서 전 화면에서 받아온 id를 이용해 select 후 agencyVO에 저장
        dbHelper = new DbHelper(activity);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sqlAgency = "SELECT * FROM AGENCIES where _id = "+ id+";";
        Cursor cursor = db.rawQuery(sqlAgency, null);
        while (cursor.moveToNext()) {
            agency = new AgencyVO(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),
                    cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11),
                    cursor.getInt(12), cursor.getInt(13), cursor.getString(14));
        } //cursor.getString(0) : return id
        cursor.close();
        db.close();
    }

    private void setTextViews(){ //textview들을 setting

        TextView textviewAgencyName = (TextView) rootView.findViewById(R.id.textview_agencyinfo_agencyName);
        TextView textviewAgencyAddress = (TextView) rootView.findViewById(R.id.textview_agencyinfo_address);
        TextView textviewOwner = (TextView) rootView.findViewById(R.id.textview_agencyinfo_representative);


        agencyAddress = agency.getAdrDtEn();

        //set agencyName
        textviewAgencyName.setText(agency.getAgncNmEn());

        //set agencyAddress
        textviewAgencyAddress.setText(agencyAddress);

        //set ownerName
        textviewOwner.setText(agency.getOwnEn());

        //set fax
        TextView textviewFax = (TextView) rootView.findViewById(R.id.textview_agencyinfo_fax);
        textviewFax.setText(agency.getFax());

        TextView textviewPhone1 = (TextView) rootView.findViewById(R.id.textview_agencyinfo_phone1);
        String phone[] = getPhoneNum();
        textviewPhone1.setText(phone[0]);
        if(phone.length == 2){ //번호가 2개 있다.
            second_tel_layout.setVisibility(View.VISIBLE);
            TextView textviewPhone2 = (TextView) rootView.findViewById(R.id.textview_agencyinfo_phone2);
            textviewPhone2.setText(phone[1]);
        }
    }

    private String[] getPhoneNum(){ //phone num 갯수에 따라 달라짐.
        String phoneNum = agency.getPhone();
        String[] array = {"1"};

        if(phoneNum.contains(",")){
            array= phoneNum.split(",");
            /*
            array[0] = array[0].trim();
            array[1] = array[1].trim();
            */
        } else {
            array[0] = agency.getPhone();
        }
        return array;
    }

    private void setFlags(){ // 국기 설정
        int en = agency.getLangEn();
        int cn = agency.getLangCn();
        int jp = agency.getLangJp();
        String etc = agency.getLangEtc();
        if(en != 1){
            us_flag.setVisibility(View.GONE);
        }
        if(cn != 1){
            cn_flag.setVisibility(View.GONE);
        }
        if(jp != 1){
            jp_flag.setVisibility(View.GONE);
        }
        if(etc.equals("Russian")){
            ru_flag.setVisibility(View.VISIBLE);
        } else if(etc.equals("Portuguese")){
            pt_flag.setVisibility(View.VISIBLE);
        } else if(etc.equals("Spanish")){
            sp_flag.setVisibility(View.VISIBLE);
        }
    }

    //Snackbar
    private void createSnackbar(){
        String warning = null;
        warning = "Please call the agency before the visit in case of the address is changed.";

        snackBar = Snackbar.make(activity.findViewById(android.R.id.content), warning, Snackbar.LENGTH_LONG);

        snackBar.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackBar.dismiss();
            }
        });
        snackBar.setDuration(Snackbar.LENGTH_INDEFINITE);
        View snackBarView = snackBar.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#cc323232"));
        snackBar.setActionTextColor(Color.parseColor("#009a9b"));
        snackBar.show();
    }

    private void matchingFavoriteStar(boolean check){
        if(check) favorite_star.setImageResource(R.drawable.agencyinfo_star_on);
        else favorite_star.setImageResource(R.drawable.agencyinfo_star_off);
    }

    private void showAddFavoriteAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Notification");
        builder.setMessage("Agency is successfully added to your favorites!"+
                ""+
                "Would you like to check your favorite?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //(activity).replaceFragment(new FavoritesFragment(), false);
             /*   activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, new FavoritesFragment(), null)
                        .addToBackStack(null)
                        .commit();*/

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


    //Geocoding을 통해 Map을 위한 lat, lng 설정
    private void setAddressLatLng(String agencyAddress){
        geocoder = new Geocoder(activity);
        List<Address> list = null;
        String str = agencyAddress;
        try{
            list = geocoder.getFromLocationName(str,10);
        } catch (IOException e){
            e.printStackTrace();
            Log.e("test","입출력 오류 - 서버에서 주소 변환시 에러 발생");
        }

        if(list != null){
            if(list.size() == 0){
                Toast.makeText(activity, "해당되는 주소 정보는 없습니다.", Toast.LENGTH_LONG).show();
            }else{
                Address addr = list.get(0);
                lat = addr.getLatitude();
                lon = addr.getLongitude();
            }
        }
    }

    private void checkIsFavorite(int agencyId){// db에서 전 화면에서 받아온 id를 이용해 favorite select
        dbHelper = new DbHelper(activity);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sqlFavorite = "SELECT * FROM FAVORITES where is_agency = "+ agencyId+" and is_district = 0;";
        Cursor cursor = db.rawQuery(sqlFavorite, null);
        if(cursor.getCount()==1){
            favorite_check = true;
            cursor.moveToNext();
            //Toast.makeText(activity, "즐겨찾기 등록된 agency name:"+cursor.getString(1), Toast.LENGTH_LONG).show();
        }else{
            favorite_check = false;
        }
        cursor.close();
        db.close();
    }

    private void favoriteDb(String mode, int id){
        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        String sql;
        if(mode.equals("insert")){
            ContentValues values = new ContentValues();
            values.put("name", agency.getAgncNmEn());
            values.put("is_district", 0);
            values.put("is_agency", id);
            values.put("memo", "");
            db.insert("FAVORITES", null, values);
        }else if(mode.equals("delete")){
            sql = "DELETE FROM FAVORITES WHERE is_agency = "+id+";";
            db.execSQL(sql);
        }
        db.close();
    }


    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        addMarkerToMap();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lon)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        mMap.setOnMarkerClickListener(this);
    }

    private void addMarkerToMap() {
        mAgency = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat,lon))
                .title("Click")
                .snippet("to open Google Maps"));
    }


    @Override
    public boolean onMarkerClick(final Marker marker) {
        if(marker.equals(mAgency)){
            Uri agencyGoogleMap = Uri.parse("geo:0,0?q="+lat+","+lon+"("+agency.getAgncNmEn()+")");
            Intent intent = new Intent(Intent.ACTION_VIEW, agencyGoogleMap);
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
        }
        return false;
    }
}
