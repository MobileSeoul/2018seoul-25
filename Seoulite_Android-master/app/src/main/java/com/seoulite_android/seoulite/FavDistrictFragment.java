package com.seoulite_android.seoulite;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link FavDistrictFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// */
public class FavDistrictFragment extends Fragment {
//
//    private OnFragmentInteractionListener mListener;
private static final String TAG = "FavDistrictFragment";

    @BindView(R.id.btn_fav_find_district) Button mFindDistrictButton;
    private ArrayList<FavVO> mFavDistrictList = new ArrayList<>();
    RecyclerView mRecyclerView;
    FavCardRecyclerViewAdapter mAdapter;
    BroadcastReceiver mReceiver;

    public FavDistrictFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_district, container, false);
        ButterKnife.bind(this, view);

        DbHelper dbHelper = new DbHelper(getContext());


        Cursor cursor = dbHelper.getReadableDatabase()
                .query("FAVORITES", null, "is_district=?", new String[] {"1"}, null, null, null);
        if (mFavDistrictList.size() != cursor.getCount()) {
            while (cursor.moveToNext()) {
                mFavDistrictList.add(new FavVO(cursor.getInt(0), // id
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
        mAdapter = new FavCardRecyclerViewAdapter(mFavDistrictList);
        mRecyclerView.setAdapter(mAdapter);
        ViewCompat.setNestedScrollingEnabled(mRecyclerView, false);


        Log.d(TAG, "onCreateView invoked");


        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mReceiver);
        Log.d(TAG, "onPause invoked");
    }

    @Override
    public void onResume() {
        super.onResume();
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getBooleanExtra("isUpdate", false)) {
                    Log.d(TAG, "onReceive: broadcastreceiver - isupdate");
                    mFavDistrictList.clear();
                    Cursor cursor = new DbHelper(getContext()).getReadableDatabase()
                            .query("FAVORITES", null, "is_district=?", new String[] {"1"}, null, null, null);
                    if (mFavDistrictList.size() != cursor.getCount()) {
                        while (cursor.moveToNext()) {
                            mFavDistrictList.add(new FavVO(cursor.getInt(0), // id
                                    cursor.getString(1), // name
                                    cursor.getInt(2), // is_district
                                    cursor.getInt(3), // is_agency
                                    cursor.getString(4))); // memo
                        }
                    }
                    cursor.close();
                    mAdapter.notifyItemChanged(intent.getIntExtra("position", 0));
                } else {
                    mFavDistrictList.clear();
                    Cursor cursor = new DbHelper(getContext()).getReadableDatabase()
                            .query("FAVORITES", null, "is_district=?", new String[] {"1"}, null, null, null);
                    while (cursor.moveToNext()) {
                        mFavDistrictList.add(new FavVO(cursor.getInt(0), // id
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
        Log.d(TAG, "onResume invoked");
    }


    @OnClick(R.id.btn_fav_find_district)
    public void moveToDistrictSelection() {
        Toast.makeText(getContext(), "Btn Clicked", Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).replaceFragment(new DistrictSelectionLivingInfoFragment(), true);
    }

}
