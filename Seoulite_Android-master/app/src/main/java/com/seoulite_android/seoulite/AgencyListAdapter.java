package com.seoulite_android.seoulite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AgencyListAdapter extends BaseAdapter {

    private List<AgencyEntry> mAgencyList;

    public AgencyListAdapter(List<AgencyEntry> agencyList) {
        this.mAgencyList = agencyList;
    }

    @Override
    public int getCount() {
        return mAgencyList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAgencyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_agencylist, parent, false);
        }

//        TextView numTextView = convertView.findViewById(R.id.agencylist_number);
//        TextView nameTextView = convertView.findViewById(R.id.agencylist_name);
//        TextView langTextview = convertView.findViewById(R.id.agencylist_language);
////        ImageView favoriteIcon = convertView.findViewById(R.id.agencylist_favorite);
//
//        AgencyEntry agency = mAgencyList.get(position);
//        numTextView.setText(String.valueOf(position + 1));
//        nameTextView.setText(agency.getName());
//        langTextview.setText(agency.getLanguage());

        return convertView;
    }
}
