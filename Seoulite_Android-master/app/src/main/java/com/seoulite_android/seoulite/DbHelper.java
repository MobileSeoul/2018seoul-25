package com.seoulite_android.seoulite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*import java.util.ArrayList;
import java.util.Collection;*/

// TODO: Log.d 삭제
public class DbHelper extends SQLiteOpenHelper{
    private static final String TAG = "DbHelper";

    private Context mContext;

    private static final String DB_NAME = "Agencies.db";
    private static final int DB_VERSION = 1;

    private static final String SQL_CREATE_AGENCIES = "CREATE TABLE AGENCIES (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, agnc_nm_kr TEXT, agnc_nm_en TEXT, " +
            "own_kr TEXT, own_en TEXT, phone TEXT, fax TEXT, adr_gu_kr TEXT, adr_dt_kr TEXT," +
            "adr_gu_en TEXT, adr_dt_en TEXT, lang_en NUMBER, lang_cn NUMBER, lang_jp NUMBER, " +
            "lang_etc TEXT)";

    private static final String SQL_CREATE_FAVORITES = "CREATE TABLE FAVORITES (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, is_district INTEGER, is_agency INTEGER, memo TEXT)";
    //todo: foreign key??
        /*
            // Set up the location column as a foreign key to location table.
            // https://gerardnico.com/android/sqlite
            "FOREIGN KEY ('is_agency') REFERENCES 'AGENCIES' ('_id')"
        */

    private static final String SQL_CREATE_DISTRICTS = "CREATE TABLE DISTRICTS (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, dist_kr TEXT, dist_en, tot_pop INTEGER, fn_pop INTEGER, fn_rat INTEGER, avg_rent INTEGER, rent_rank INTEGER, dist_nr_kr TEXT, dist_nr_en TEXT, feats_kr TEXT, feats_en TEXT)";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_AGENCIES);
        Log.d(TAG, "onCreate: Database Table AGENCIES created.");
        db.execSQL(SQL_CREATE_FAVORITES);
        Log.d(TAG, "onCreate: Database Table FAVORITES created.");
        db.execSQL(SQL_CREATE_DISTRICTS);
        Log.d(TAG, "onCreate: Database Table DISTRICTS created");

        try {
            addAgencies(db);
            addDistricts(db);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int DB_VERSION, int newVersion) {
        if (newVersion == DB_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS AGENCIES");
            db.execSQL("DROP TABLE IF EXISTS FAVORITES");
            db.execSQL("DROP TABLE IF EXISTS DISTRICTS");
            onCreate(db);
        }
    }

    // District List Insert하는 메소드
    public void addDistricts(SQLiteDatabase db) throws IOException, JSONException {
        final String DIST_KR = "district_kr";
        final String DIST_EN = "district_en";
        final String TOT_POP = "total_pop";
        final String FN_POP = "foreign_pop";
        final String FN_RATE = "foreign_rate";
        final String AVG_RENT = "avg_rent";
        final String RENT_RANK = "rent_rank";
        final String DIST_NR_KR = "dist_near_kr";
        final String DIST_NR_EN = "dist_near_en";
        final String FEATS_KR = "feats_kr";
        final String FEATS_EN = "feats_en";

        String jsonStr = readJson(1);
        JSONArray jsonArray = new JSONArray(jsonStr);

        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject obj = jsonArray.getJSONObject(i);

            ContentValues values = new ContentValues();

            values.put("dist_kr", obj.getString(DIST_KR));
            values.put("dist_en", obj.getString(DIST_EN));
            values.put("tot_pop", obj.getString(TOT_POP));
            values.put("fn_pop", obj.getString(FN_POP));
            values.put("fn_rat", obj.getString(FN_RATE));
            values.put("avg_rent", obj.getString(AVG_RENT));
            values.put("rent_rank", obj.getString(RENT_RANK));
            values.put("dist_nr_kr", obj.getString(DIST_NR_KR));
            values.put("dist_nr_en", obj.getString(DIST_NR_EN));
            values.put("feats_kr", obj.getString(FEATS_KR));
            values.put("feats_en", obj.getString(FEATS_EN));

            db.insert("DISTRICTS", null, values);

            Log.d(TAG, "addDistricts: Inserted Successfully " + values);
        }

    }

    // Agency List Insert하는 메소드
    public void addAgencies(SQLiteDatabase db) throws IOException, JSONException {
        final String AGNC_NM_KR = "agency_name_kr";
        final String AGNC_NM_EN = "agency_name_en";
        final String OWN_KR = "owner_kr";
        final String OWN_EN = "owner_en";
        final String PHONE = "phone";
        final String FAX = "fax";
        final String ADR_GU_KR = "address_district_kr";
        final String ADR_DT_KR = "address_detail_kr";
        final String ADR_GU_EN = "address_district_en";
        final String ADR_DT_EN = "address_detail_en";
        final String LANG_EN = "lang_en";
        final String LANG_CN = "lang_cn";
        final String LANG_JP = "lang_jp";
        final String LANG_ETC = "lang_etc";


        String jsonStr = readJson(0);
        JSONArray jsonArray = new JSONArray(jsonStr);

        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject obj = jsonArray.getJSONObject(i);

            ContentValues values = new ContentValues();

            values.put("agnc_nm_kr", obj.getString(AGNC_NM_KR));
            values.put("agnc_nm_en", obj.getString(AGNC_NM_EN));
            values.put("own_kr", obj.getString(OWN_KR));
            values.put("own_en", obj.getString(OWN_EN));
            values.put("phone", obj.getString(PHONE));
            values.put("fax", obj.getString(FAX));
            values.put("adr_gu_kr", obj.getString(ADR_GU_KR));
            values.put("adr_dt_kr", obj.getString(ADR_DT_KR));
            values.put("adr_gu_en", obj.getString(ADR_GU_EN));
            values.put("adr_dt_en", obj.getString(ADR_DT_EN));
            values.put("lang_en", obj.getString(LANG_EN));
            values.put("lang_cn", obj.getString(LANG_CN));
            values.put("lang_jp", obj.getString(LANG_JP));
            values.put("lang_etc", obj.getString(LANG_ETC));

            db.insert("AGENCIES", null, values);

            Log.d(TAG, "addAgencies: Inserted Successfully " + values);
        }


    }

    // Json 파일을 읽어서 String으로 반환하는 메소드
    private String readJson(int type) throws IOException {

        InputStream is = null;
        StringBuilder sb = new StringBuilder();

        try {
            if (type == 0) { // AGENCIES
                is = mContext.getAssets().open("agency_list.json");
            } else if (type == 1) { // DISTRICTS
                is = mContext.getAssets().open("district_list.json");
            }
            if (is != null) {
                InputStreamReader reader = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(reader);
                String line = "";
                while ((line = br.readLine()) != null) sb.append(line);
            }
        } finally {
            if (is != null) is.close();
        }

        return sb.toString();
    }


    //현재 미사용
    /*public Collection<AgencyVO> getResults(){
        SQLiteDatabase db = getReadableDatabase();
        Collection list = new ArrayList();
        Cursor cursor = db.rawQuery("SELECT * FROM AGENCIES", null);
        AgencyVO agency;
        while (cursor.moveToNext()) {
            agency = new AgencyVO(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getInt(10),
                    cursor.getInt(11), cursor.getInt(12), cursor.getString(13));
            list.add(agency);
        }
        return list;
    }*/

    /**(영문) 구별 부동산 조회-현재미사용*/
    /*public Collection<AgencyVO> getAgenciesByAdrGuEn(String searchAdrGuEn){
        SQLiteDatabase db = getReadableDatabase();
        Collection list = new ArrayList();
        Cursor cursor = db.rawQuery("SELECT * FROM AGENCIES WHERE adr_gu_en='"+searchAdrGuEn+"'", null);
        AgencyVO agency;
        while (cursor.moveToNext()) {
            agency = new AgencyVO(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getInt(10),
                    cursor.getInt(11), cursor.getInt(12), cursor.getString(13));
            list.add(agency);
        }
        return list;
    }*/




}
