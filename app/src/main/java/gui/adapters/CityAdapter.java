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

import cli.organization.data.geo.City;


public class CityAdapter extends ArrayAdapter<City> {

    String text;

    public CityAdapter(Context context, List<City> objects) {
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

        City city = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        if(Locale.getDefault().getLanguage().equals("es")) {
            text = city.getNameSpanish();
        }else if(Locale.getDefault().getLanguage().equals("fr")){
            text=city.getNameFrench();
        }else if(Locale.getDefault().getLanguage().equals("eu")) {
            text = city.getNameBasque();
        }else if(Locale.getDefault().getLanguage().equals("ca")){
            text=city.getNameCatalan();
        }else if(Locale.getDefault().getLanguage().equals("nl")) {
            text = city.getNameDutch();
        }else if(Locale.getDefault().getLanguage().equals("gl")){
            text=city.getNameGalician();
        }else if(Locale.getDefault().getLanguage().equals("de")) {
            text = city.getNameGerman();
        }else if(Locale.getDefault().getLanguage().equals("it")){
            text=city.getNameItalian();
        }else if(Locale.getDefault().getLanguage().equals("pt")) {
            text = city.getNamePortuguese();
        }else{
            text= city.getNameEnglish();
        }
        textView.setText(text);

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
    @Override
    public City getItem(int position) {
        return super.getItem(position);
    }

    public String getText(){
        return text;
    }

}
