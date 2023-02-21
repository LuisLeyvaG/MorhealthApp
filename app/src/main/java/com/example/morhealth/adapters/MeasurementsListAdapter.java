package com.example.morhealth.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.morhealth.R;
import com.example.morhealth.domain.Measurement;
import com.example.morhealth.domain.WaterAmount;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MeasurementsListAdapter extends BaseAdapter implements Serializable {

    private List<Measurement> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public MeasurementsListAdapter(List<Measurement> list, Context context) {

        this.list = list;
        this.context = context;

        if( context != null) {
            layoutInflater = LayoutInflater.from(context);
        }
    }

    public void addNewMeasurement(Measurement measurement) {
        list.add(measurement);
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
        TextView tvMetric = null;
        TextView tvValue = null;
        TextView tvDate = null;
        TextView tvTime = null;
        view = layoutInflater.inflate(R.layout.measurement_item_view, null);
        //tvMetric = view.findViewById(R.id.tvMetric);
        tvValue = view.findViewById(R.id.tvValue);
        tvDate = view.findViewById(R.id.tvDate);
        tvTime = view.findViewById(R.id.tvTime);

        Measurement measurement = list.get(i);

        String value = String.valueOf(measurement.getValue());
        Date measurementDate = measurement.getDate_time();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String date = dateFormat.format(measurementDate);
        String time = timeFormat.format(measurementDate);
        /*String metric = "Métrica";
        switch (measurement.getMetric_id()) {
            case 0:
                metric = context.getResources().getString(R.string.heart_rate);
            case 1:
                metric = context.getResources().getString(R.string.day_steps);
            case 2:
                metric = context.getResources().getString(R.string.dream_time);
            case 3:
                metric = context.getResources().getString(R.string.water);
            case 4:
                metric = context.getResources().getString(R.string.calories);
            case 5:
                metric = context.getResources().getString(R.string.weight);
        }
        tvMetric.setText(metric);*/
        tvValue.setText(value);
        tvDate.setText(date);
        tvTime.setText(time);
        return view;
    }

}
