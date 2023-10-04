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

import cli.organization.data.geo.Province;

public class ProvinceAdapter extends ArrayAdapter<Province> {

    String text;

    public ProvinceAdapter(Context context, List<Province> objects) {
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

        Province province = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        if(Locale.getDefault().getLanguage().equals("es")) {
            text = province.getNameSpanish();
        }else if(Locale.getDefault().getLanguage().equals("fr")){
            text=province.getNameFrench();
        }else if(Locale.getDefault().getLanguage().equals("eu")) {
            text = province.getNameBasque();
        }else if(Locale.getDefault().getLanguage().equals("ca")){
            text=province.getNameCatalan();
        }else if(Locale.getDefault().getLanguage().equals("nl")) {
            text = province.getNameDutch();
        }else if(Locale.getDefault().getLanguage().equals("gl")){
            text=province.getNameGalician();
        }else if(Locale.getDefault().getLanguage().equals("de")) {
            text = province.getNameGerman();
        }else if(Locale.getDefault().getLanguage().equals("it")){
            text=province.getNameItalian();
        }else if(Locale.getDefault().getLanguage().equals("pt")) {
            text = province.getNamePortuguese();
        }else{
            text= province.getNameEnglish();
        }
        textView.setText(text);

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
    @Override
    public Province getItem(int position) {
        return super.getItem(position);
    }

    public String getText(){
        return text;
    }

}