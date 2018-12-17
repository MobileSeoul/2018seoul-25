package com.seoulite_android.seoulite;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class SearchResultCurosrAdapter extends CursorAdapter {
    Cursor cursor;
    TextView mResultDistrict;
    View mDivider;
    TextView mResultNum;
    private static final int VIEW_TYPE_GROUP_START = 0;
    private static final int VIEW_TYPE_GROUP_CONT = 1;
    private static final int VIEW_TYPE_COUNT = 2;
    LayoutInflater mInflater ;
    int msetAgencyNum;
    int stratAgencyNum;

    public SearchResultCurosrAdapter(Context context, Cursor c){
        super(context, c, 0);
        mInflater = LayoutInflater.from(context);
    }



    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_GROUP_START;
        }
        cursor = getCursor();
        cursor.moveToPosition(position);
        boolean newGroup = isNewGroup(cursor, position); //현재와 다음이 같은 구이면 true 아니면 false

        if (newGroup) {
            return VIEW_TYPE_GROUP_START;
        } else {
            return VIEW_TYPE_GROUP_CONT;
        }
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_searchresult, parent, false);
        return view;
       // return LayoutInflater.from(context).inflate(R.layout.item_searchresult,parent,false);
    }

    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {
        //xml에서 뷰 가져오기
        //mResultNum = view.findViewById(R.id.result_agency_number);  //이름앞에 번호 123
        TextView mResultName = view.findViewById(R.id.result_agency_name);  //결과 에이전시의 이름
        ImageView mResultEng = view.findViewById(R.id.result_agency_eng);
        ImageView mResultCh = view.findViewById(R.id.result_agency_ch);
        ImageView mResultJp = view.findViewById(R.id.result_agency_jp);
        ImageView mResultPort = view.findViewById(R.id.result_agency_portuguese);
        ImageView mResultRussian = view.findViewById(R.id.result_agency_russian);
        ImageView mResultSpain = view.findViewById(R.id.result_agency_spainish);
        TextView mResultId = view.findViewById(R.id.result_agency_Id);   //아이템 클릭시
        mResultDistrict = view.findViewById(R.id.result_agency_district);
        mDivider = view.findViewById(R.id.result_agency_divider);
        TextView mResultPhone = view.findViewById(R.id.result_agency_phone);

        final int mcount = cursor.getPosition();
        final String mAgencyName = cursor.getString(2);
        int mEngExist = cursor.getInt(11);
        int mChExist = cursor.getInt(12);
        int mJpExist = cursor.getInt(13);
        String mEtcExist = cursor.getString(14);
        final String mdistrict = cursor.getString(9);
        int AgencyId = cursor.getInt(0);
        String mPhoneNum = cursor.getString(5);


        SpannableString content = new SpannableString(mdistrict);
        content.setSpan(new UnderlineSpan(), 0, mdistrict.length(), 0);
        mResultDistrict.setText(content);
        mResultId.setText(String.valueOf(AgencyId));
        mResultName.setText(mAgencyName);
        mResultPhone.setText(mPhoneNum);

        if (mEngExist != 1) {
            mResultEng.setVisibility(View.GONE);
        } else {
            mResultEng.setVisibility(View.VISIBLE);
        }
        if (mChExist != 1) {
            mResultCh.setVisibility(View.GONE);
        } else {
            mResultCh.setVisibility(View.VISIBLE);
        }
        if (mJpExist != 1) {
            mResultJp.setVisibility(View.GONE);
        } else {
            mResultJp.setVisibility(View.VISIBLE);
        }
        if (mEtcExist.equals("러시아어")) {
            mResultRussian.setVisibility(View.VISIBLE);
        } else if (mEtcExist.equals("포르투갈어")) {
            mResultPort.setVisibility(View.VISIBLE);
        } else if (mEtcExist.equals("스페인어")) {
            mResultSpain.setVisibility(View.VISIBLE);
        } else {
            mResultRussian.setVisibility(View.GONE);
            mResultPort.setVisibility(View.GONE);
            mResultSpain.setVisibility(View.GONE);
        }

        int nViewType;
        if (mcount == 0) {
            nViewType = VIEW_TYPE_GROUP_START;
        } else {
            boolean newGroup = isNewGroup(cursor, mcount);
            if (newGroup) {
                nViewType = VIEW_TYPE_GROUP_START;
            } else {
                nViewType = VIEW_TYPE_GROUP_CONT;
            }
        }

        if (nViewType == VIEW_TYPE_GROUP_START) {
            mResultDistrict.setVisibility(View.VISIBLE);
            mDivider.setVisibility(View.VISIBLE);
            if(getCursor().getPosition()==0){
                mDivider.setVisibility(View.GONE);
            }
        }else {
            mResultDistrict.setVisibility(View.GONE);
            mDivider.setVisibility(View.GONE);
        }

        Log.d("현재 포지션 ", String.valueOf(mcount));
        mResultDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
               // String chosenDistrict = mResultDistrict.getText().toString();
                if(mdistrict != null){
                    AgencyByDistrictFragment fragment = new AgencyByDistrictFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("districtName", mdistrict);
                    fragment.setArguments(bundle);

                    FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                   // manager.beginTransaction().add(R.id.main_container, fragment).commit();
                    manager.beginTransaction().replace(R.id.main_container, fragment)
                            .addToBackStack(null)
                            .commit();
                   // mainActivity.replaceFragment(fragment, false);
                }
            }
        });
    }


    private boolean isNewGroup(Cursor cursor, int position) {
            String currentDistrict = cursor.getString(9);
            cursor.moveToPosition(position - 1);
            String compareDistrict = cursor.getString(9);
            cursor.moveToPosition(position);
            while (!currentDistrict.equals(compareDistrict)) {
                return true;
            }
            return false;
        }
}

