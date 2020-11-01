package com.example.earthquake;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RowAdapter extends ArrayAdapter<Row> {
    List<Row> rows = new ArrayList<Row>();
    public RowAdapter(Activity context, ArrayList<Row> rows)
    {
        super(context,0,rows);
        this.rows=rows;
    }
    private int getColor(float magnitude) {
        int m=(int) magnitude;
        int res=0;
        switch (m)
        {
            case 1:
                res= R.color.magnitude1;
                break;
            case 2:
                res= R.color.magnitude2;
                break;
            case 3:
                res= R.color.magnitude3;
                break;
            case 4:
                res= R.color.magnitude4;
                break;
            case 5:
                res= R.color.magnitude5;
                break;
            case 6:
                res= R.color.magnitude6;
                break;
            case 7:
                res= R.color.magnitude7;
                break;
            case 8:
                res= R.color.magnitude8;
                break;
            case 9:
                res= R.color.magnitude9;
                break;
            case 10:
                res= R.color.magnitude10plus;
                break;

        }
        return ContextCompat.getColor(getContext(),res);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowitem=convertView;
        if(rowitem==null)
        {
            rowitem= LayoutInflater.from(getContext()).inflate(R.layout.listitem,parent,false);
        }
        Row curr=getItem(position);
        TextView m=rowitem.findViewById(R.id.magnitude_text_view);
        TextView pr=rowitem.findViewById(R.id.primary_location);
        TextView offset= rowitem.findViewById(R.id.offset);
        TextView d=rowitem.findViewById(R.id.date_text_view);
        TextView t=rowitem.findViewById(R.id.time_text_view);
        t.setText(curr.getTime());
        DecimalFormat formatter = new DecimalFormat("0.0");
        String magOutput=formatter.format(curr.getMagnitude());
        m.setText(magOutput);
        d.setText(curr.getDate());
        String loc=curr.getLocation();
        if (loc.contains(" of "))
        {
            String off,prim;
            String[] stringArray=new String[2];
            stringArray = loc.split("of");
            off=stringArray[0]+" of";
            prim=stringArray[1];
            pr.setText(prim);
            offset.setText(off);
        }
        else
        {
            pr.setText(loc);
            offset.setText("Near the");
        }
        GradientDrawable magnitudecircle=(GradientDrawable) m.getBackground();
        magnitudecircle.setColor(getColor(curr.getMagnitude()));
        return rowitem;
    }

    @Nullable
    @Override
    public Row getItem(int position) {
        return rows.get(position);
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
}
