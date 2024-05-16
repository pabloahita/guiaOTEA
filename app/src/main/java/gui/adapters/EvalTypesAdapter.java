package gui.adapters;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.util.List;

import cli.organization.data.Center;

public class EvalTypesAdapter extends ArrayAdapter<String> {
    String text;

    public EvalTypesAdapter(Context context, List<String> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.spinner_item_layout, parent, false);
        }

        String string = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        if(position==0){
            text="<b>"+string+"</b>";
        }else{
            text="<b>"+getItem(0)+": </b><i>"+string+"</i>";
        }
        textView.setText(Html.fromHtml(text,0));

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.spinner_item_layout, parent, false);
        }

        String string = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        if(position==0){
            text="<b>"+string+"</b>";
        }else{
            text="<i>"+string+"</i>";
        }
        textView.setText(Html.fromHtml(text,0));

        return view;
    }
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    public String getText(){
        return text;
    }
}
