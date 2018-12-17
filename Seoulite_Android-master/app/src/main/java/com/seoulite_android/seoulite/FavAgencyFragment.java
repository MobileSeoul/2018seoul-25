package com.seoulite_android.seoulite;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavAgencyFragment extends Fragment {
    //private String sql = "select * from FAVORITES where is_agency is not null";

    @BindView(R.id.btn_fav_find_agency) Button mFindAgencyButton;

    private ArrayList<FavVO> mFavAgencyList = new ArrayList<>();
    RecyclerView mRecyclerView;
    FavCardRecyclerViewAdapter mAdapter;
    BroadcastReceiver mReceiver;


    public FavAgencyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_agency, container, false);
        ButterKnife.bind(this, view);

        DbHelper dbHelper = new DbHelper(getContext());
        Cursor cursor = dbHelper.getReadableDatabase()
                .query("FAVORITES", null, "is_district=?", new String[] {"0"}, null, null, null);

        if (mFavAgencyList.size() != cursor.getCount()) {
            while (cursor.moveToNext()) {
                mFavAgencyList.add(new FavVO(cursor.getInt(0), // id
                        cursor.getString(1), // name
                        cursor.getInt(2), // is_district
                        cursor.getInt(3), // is_agency
                        cursor.getString(4))); // memo
            }
        }
        cursor.close();

        // Set up the recycler view
        mRecyclerView = view.findViewById(R.id.fav_district_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
        mAdapter = new FavCardRecyclerViewAdapter(mFavAgencyList);
        mRecyclerView.setAdapter(mAdapter);
        ViewCompat.setNestedScrollingEnabled(mRecyclerView, false);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getBooleanExtra("isUpdate", false)) {
                    mFavAgencyList.clear();
                    Cursor cursor = new DbHelper(getContext()).getReadableDatabase()
                            .query("FAVORITES", null, "is_agency!=?", new String[] {"0"}, null, null, null);
                    if (mFavAgencyList.size() != cursor.getCount()) {
                        while (cursor.moveToNext()) {
                            mFavAgencyList.add(new FavVO(cursor.getInt(0), // id
                                    cursor.getString(1), // name
                                    cursor.getInt(2), // is_district
                                    cursor.getInt(3), // is_agency
                                    cursor.getString(4))); // memo
                        }
                    }
                    cursor.close();
                    mAdapter.notifyItemChanged(intent.getIntExtra("position", 0));
                } else {
                    mFavAgencyList.clear();
                    Cursor cursor = new DbHelper(getContext()).getReadableDatabase()
                            .query("FAVORITES", null, "is_agency!=?", new String[] {"0"}, null, null, null);
                    while (cursor.moveToNext()) {
                        mFavAgencyList.add(new FavVO(cursor.getInt(0), // id
                                cursor.getString(1), // name
                                cursor.getInt(2), // is_district
                                cursor.getInt(3), // is_agency
                                cursor.getString(4))); // memo
                    }
                    cursor.close();
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
        getActivity().registerReceiver(mReceiver, new IntentFilter("DB_CHANGED"));
    }

    // TODO: Make this Btn Work. This is temporary !
    @OnClick(R.id.btn_fav_find_agency)
    public void moveToAgencySelection() {
        Toast.makeText(getContext(), "Btn Clicked", Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).replaceFragment(new DistrictSelectionFragment(), true);
    }
}
