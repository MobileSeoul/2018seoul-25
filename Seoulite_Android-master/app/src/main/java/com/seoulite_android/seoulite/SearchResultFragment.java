package com.seoulite_android.seoulite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchResultFragment extends Fragment {
    DbHelper myDbHelper;
    SQLiteDatabase myDb;
    Cursor searchCursor;
    @BindView(R.id.total_reault)
    TextView mTotalResult;
    @BindView(R.id.search_result_list)
    ListView mResultListView;
    SearchResultCurosrAdapter mSearchResultCursorAdapter;
    String msearched;
    String searched;
    MainActivity activity;

    public SearchResultFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //데이터 베이스 연결
        myDbHelper = new DbHelper(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        ButterKnife.bind(this, view);
        myDb = myDbHelper.getReadableDatabase();
        if(savedInstanceState ==null){
            gettigSearching();
        }
        String uppersearched = searched.toUpperCase();
        String finalSearched = StringReplace(uppersearched);
        Log.d("regex후:", finalSearched);
        searchCursor = myDb.rawQuery("SELECT * FROM AGENCIES Where Upper(adr_gu_en) LIKE '%"+finalSearched+"%' OR Upper(agnc_nm_en) LIKE" +
                "'%"+finalSearched+"%' order by adr_gu_en", null);
        mSearchResultCursorAdapter = new SearchResultCurosrAdapter(getActivity(), searchCursor);
        searchCursor.getCount();

        int mToalNum = searchCursor.getCount();
        if(mToalNum<1){
            Toast.makeText(getActivity(), "Result not found. Please Try Again.", Toast.LENGTH_SHORT).show();
        }
        mTotalResult.setText("Result:  "+String.valueOf(mToalNum));

        mResultListView.setAdapter(mSearchResultCursorAdapter);

        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView mResultItemId = view.findViewById(R.id.result_agency_Id);
                String selectedItemId = mResultItemId.getText().toString();

                AgencyInfoFragment agencyInfoFragment = new AgencyInfoFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putString("agcId", selectedItemId);
                agencyInfoFragment.setArguments(bundle2);
                getFragmentManager().beginTransaction().replace(R.id.main_container, agencyInfoFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void gettigSearching(){
        Bundle bundle = getArguments();
        if(bundle != null){
            searched = bundle.getString("searching");
        }else{
            Toast.makeText(activity, "Can't get data. Try again.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    public static String StringReplace(String str){
        String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
        str = str.replaceAll(match, "");
        str =  str.replaceAll("\\s{2,}", " ").trim();

        return str;
    }


}
