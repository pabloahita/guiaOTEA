package gui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.util.List;
import java.util.Locale;

import cli.organization.data.geo.Region;

public class RegionAdapter extends ArrayAdapter<Region> {

    String text;

    public RegionAdapter(Context context, List<Region> objects) {
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

        Region region = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        if(Locale.getDefault().getLanguage().equals("es")) {
            text = region.getNameSpanish();
        }else if(Locale.getDefault().getLanguage().equals("fr")){
            text=region.getNameFrench();
        }else if(Locale.getDefault().getLanguage().equals("eu")) {
            text = region.getNameBasque();
        }else if(Locale.getDefault().getLanguage().equals("ca")){
            text=region.getNameCatalan();
        }else if(Locale.getDefault().getLanguage().equals("nl")) {
            text = region.getNameDutch();
        }else if(Locale.getDefault().getLanguage().equals("gl")){
            text=region.getNameGalician();
        }else if(Locale.getDefault().getLanguage().equals("de")) {
            text = region.getNameGerman();
        }else if(Locale.getDefault().getLanguage().equals("it")){
            text=region.getNameItalian();
        }else if(Locale.getDefault().getLanguage().equals("pt")) {
            text = region.getNamePortuguese();
        }else{
            text= region.getNameEnglish();
        }
        textView.setText(text);

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
    @Override
    public Region getItem(int position) {
        return super.getItem(position);
    }

    public String getText(){
        return text;
    }

}