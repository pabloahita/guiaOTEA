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

import cli.organization.data.geo.Country;

public class CountryAdapter extends ArrayAdapter<Country> {

    String text;

    public CountryAdapter(Context context, List<Country> objects) {
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

        Country country = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        text=country.getFlag();
        if(Locale.getDefault().getLanguage().equals("es")) {
            text = text + " " + country.getNameSpanish();
        }else if(Locale.getDefault().getLanguage().equals("fr")){
            text = text + " " + country.getNameFrench();
        }else if(Locale.getDefault().getLanguage().equals("eu")) {
            text = text + " " + country.getNameBasque();
        }else if(Locale.getDefault().getLanguage().equals("ca")){
            text = text + " " + country.getNameCatalan();
        }else if(Locale.getDefault().getLanguage().equals("nl")) {
            text = text + " " + country.getNameDutch();
        }else if(Locale.getDefault().getLanguage().equals("gl")){
            text = text + " " + country.getNameGalician();
        }else if(Locale.getDefault().getLanguage().equals("de")) {
            text = text + " " + country.getNameGerman();
        }else if(Locale.getDefault().getLanguage().equals("it")){
            text = text + " " + country.getNameItalian();
        }else if(Locale.getDefault().getLanguage().equals("pt")) {
            text = text + " " + country.getNamePortuguese();
        }else{
            text = text + " " + country.getNameEnglish();
        }
        textView.setText(text);

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
    @Override
    public Country getItem(int position) {
        return super.getItem(position);
    }

    public String getText(){
        return text;
    }

}