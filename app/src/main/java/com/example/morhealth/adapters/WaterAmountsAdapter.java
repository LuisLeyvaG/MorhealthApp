package com.example.morhealth.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.morhealth.R;
import com.example.morhealth.domain.WaterAmount;

import java.io.Serializable;
import java.util.List;

public class WaterAmountsAdapter extends BaseAdapter implements Serializable {

    private List<WaterAmount> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public WaterAmountsAdapter(List<WaterAmount> list, Context context) {

        this.list = list;
        this.context = context;

        if( context != null) {
            layoutInflater = LayoutInflater.from(context);
        }
    }

    public void addNewWaterAmount(WaterAmount waterAmount) {
        list.add(waterAmount);
        notifyDataSetChanged();
    }

    public boolean isEmptyOrNull( )
    {
        return list == null || list.size() == 0;
    }

    @Override
    public int getCount() {
        if(isEmptyOrNull()) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        if(isEmptyOrNull()) {
            return null;
        }
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView label = null;
        TextView value = null;
        ImageView imageView = null;
        LinearLayout linearLayout = null;
        view = layoutInflater.inflate(R.layout.water_amount_view, null );
        label = view.findViewById(R.id.water_amount_label);
        value = view.findViewById(R.id.water_amount);
        imageView = view.findViewById(R.id.water_amount_icon);
        linearLayout = view.findViewById(R.id.lyWaterAmount);
        label.setText(String.valueOf(list.get(i).getLabel() + ":"));
        value.setText(String.valueOf(list.get(i).getValue() + " ml"));
        imageView.setImageResource(list.get(i).getIcon());
        return view;
    }

}
