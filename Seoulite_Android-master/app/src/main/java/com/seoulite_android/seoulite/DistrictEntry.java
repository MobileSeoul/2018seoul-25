package com.seoulite_android.seoulite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DistrictEntry {
    private static List<String> mDistrictList;

    public static List<String> getDistrictList() {
        if (mDistrictList != null) {
            return mDistrictList;
        }
        mDistrictList = new ArrayList<>();

        String[] districts = {"Gangnam-gu", "Gangdong-gu", "Gangbuk-gu", "Gangseo-gu",
                "Gwanak-gu", "Gwangjin-gu", "Guro-gu", "Geumcheon-gu",
                "Nowon-gu", "Dobong-gu", "Dongdaemun-gu", "Dongjak-gu",
                "Mapo-gu", "Seodaemun-gu", "Seocho-gu", "Seongdong-gu",
                "Seongbuk-gu", "Songpa-gu", "Yangcheon-gu", "Yeongdeungpo-gu",
                "Yongsan-gu", "Eunpyeong-gu", "Jongno-gu", "Jung-gu", "Jungnang-gu"};

        Arrays.sort(districts);
        mDistrictList = Arrays.asList(districts);

        return mDistrictList;
    }
}
