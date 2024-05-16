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
import java.util.Locale;

import cli.organization.data.geo.Country;

public class PhoneCodeAdapter extends ArrayAdapter<Country> {

    String text;

    public PhoneCodeAdapter(Context context, List<Country> objects) {
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

        if(position==0){
            text="-";
        }
        else{
            text=country.getFlag();
        }
        textView.setText(text);
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.spinner_item_layout, parent, false);
        }

        Country country = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        if(position>0){
            String flag=country.getFlag();
            String ax="";
            if(Locale.getDefault().getLanguage().equals("es")) {
                ax = "<i>" + country.getNameSpanish()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                ax = "<i>" + country.getNameFrench()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("eu")) {
                ax = "<i>" + country.getNameBasque()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                ax = "<i>" + country.getNameCatalan()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("nl")) {
                ax = "<i>" + country.getNameDutch()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                ax = "<i>" + country.getNameGalician()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("de")) {
                ax = "<i>" + country.getNameGerman()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                ax = "<i>" + country.getNameItalian()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("pt")) {
                ax = "<i>" + country.getNamePortuguese()+"</i>";
            }else{
                ax = "<i>" + country.getNameEnglish()+"</i>";
            }
            text=flag+" "+ax;
        }else{
            if(Locale.getDefault().getLanguage().equals("es")) {
                text = "<b>" + country.getNameSpanish()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text = "<b>" + country.getNameFrench()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("eu")) {
                text = "<b>" + country.getNameBasque()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text = "<b>" + country.getNameCatalan()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("nl")) {
                text = "<b>" + country.getNameDutch()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text = "<b>" + country.getNameGalician()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("de")) {
                text = "<b>" + country.getNameGerman()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text = "<b>" + country.getNameItalian()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("pt")) {
                text = "<b>" + country.getNamePortuguese()+"</b>";
            }else{
                text = "<b>" + country.getNameEnglish()+"</b>";
            }
        }
        textView.setText(Html.fromHtml(text));

        return view;
    }
    @Override
    public Country getItem(int position) {
        return super.getItem(position);
    }

    public String getText(){
        return text;
    }
}
