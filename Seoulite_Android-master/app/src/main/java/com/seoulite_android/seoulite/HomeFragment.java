package com.seoulite_android.seoulite;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragment extends Fragment {

    // ViewPager widgets
    @BindView(R.id.image_viewpager) ViewPager mViewPager;
    @BindView(R.id.indicator) TabLayout mIndicator;

    Timer mTimer;

    List<Drawable> mImages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        // Create images array
        mImages = new ArrayList<>();
        // TODO: Temp Code, Needs Refactoring

        mImages.add(getResources().getDrawable(R.drawable.home_01));
        mImages.add(getResources().getDrawable(R.drawable.home_02));
        mImages.add(getResources().getDrawable(R.drawable.home_03));

        mViewPager.setAdapter(new SliderAdapter(getContext(), mImages));
        mIndicator.setupWithViewPager(mViewPager, true);


        return view;
    }

    @Override
    public void onDestroyView() {
        mTimer.cancel();
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Auto-slide timer
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);

    }

//    @OnClick(R.id.agencyinfo_test_textview)
//    void moveToAgencyInfoFragment(){
//        ((MainActivity)getActivity()).replaceFragment(new AgencyInfoFragment(), false);
//    }

    @OnClick(R.id.btn_home_search)
    void moveToDistrictSelectionFragment() {
        MainActivity.fragmentManager.beginTransaction()
                .replace(R.id.main_container, new DistrictSelectionFragment(), null)
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.btn_home_living_info)
    void moveToLivingInfoFragment() {
        MainActivity.fragmentManager.beginTransaction()
                .replace(R.id.main_container, new DistrictSelectionLivingInfoFragment(), null)
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.btn_home_favorites)
    void moveToFavoritesFragment() {
        MainActivity.fragmentManager.beginTransaction()
                .replace(R.id.main_container, new FavoritesFragment(), null)
                .addToBackStack(null)
                .commit();
    }


    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mViewPager.getCurrentItem() < mImages.size() - 1) {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    } else {
                        mViewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }


}
