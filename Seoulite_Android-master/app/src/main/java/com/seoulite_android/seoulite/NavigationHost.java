package com.seoulite_android.seoulite;

import android.support.v4.app.Fragment;

public interface NavigationHost {

    void replaceFragment(Fragment fragment, boolean addToBackstack);
}
