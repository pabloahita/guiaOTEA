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
        if(position==0){
            if(city.getIdCity()==-2){
                if(Locale.getDefault().getLanguage().equals("es")) {
                    text = "<b>"+city.getNameSpanish()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    text="<b>"+city.getNameFrench()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("eu")) {
                    text = "<b>"+city.getNameBasque()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    text="<b>"+city.getNameCatalan()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("nl")) {
                    text = "<b>"+city.getNameDutch()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    text="<b>"+city.getNameGalician()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("de")) {
                    text = "<b>"+city.getNameGerman()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    text="<b>"+city.getNameItalian()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("pt")) {
                    text = "<b>"+city.getNamePortuguese()+"</b>";
                }else{
                    text= "<b>"+city.getNameEnglish()+"</b>";
                }
            }
            else{
                if(Locale.getDefault().getLanguage().equals("es")) {
                    text = "<b>Ciudad: </b><i>"+city.getNameSpanish()+"</i>";
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    text="<b>Ville: </b><i>"+city.getNameFrench()+"</i>";
                }else if(Locale.getDefault().getLanguage().equals("eu")) {
                    text = "<b>Hiri: </b><i>"+city.getNameBasque()+"</i>";
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    text="<b>Ciutat: </b><i>"+city.getNameCatalan()+"</i>";
                }else if(Locale.getDefault().getLanguage().equals("nl")) {
                    text = "<b>Stad: </b><i>"+city.getNameDutch()+"</i>";
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    text="<b>Cidade: </b><i>"+city.getNameGalician()+"</i>";
                }else if(Locale.getDefault().getLanguage().equals("de")) {
                    text = "<b>Stadt: </b><i>"+city.getNameGerman()+"</i>";
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    text="<b>Città: </b><i>"+city.getNameItalian()+"</i>";
                }else if(Locale.getDefault().getLanguage().equals("pt")) {
                    text = "<b>Cidade: </b><i>"+city.getNamePortuguese()+"</i>";
                }else{
                    text= "<b>City: </b><i>"+city.getNameEnglish()+"</i>";
                }
            }
        }else{
            City title=getItem(0);
            if(Locale.getDefault().getLanguage().equals("es")) {
                text = "<b>"+title.getNameSpanish()+": </b><i>"+city.getNameSpanish()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text="<b>"+title.getNameFrench()+": </b><i>"+city.getNameFrench()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("eu")) {
                text = "<b>"+title.getNameBasque()+": </b><i>"+city.getNameBasque()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text="<b>"+title.getNameCatalan()+": </b><i>"+city.getNameCatalan()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("nl")) {
                text = "<b>"+title.getNameDutch()+": </b><i>"+city.getNameDutch()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text="<b>"+title.getNameGalician()+": </b><i>"+city.getNameGalician()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("de")) {
                text = "<b>"+title.getNameGerman()+": </b><i>"+city.getNameGerman()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text="<b>"+title.getNameItalian()+": </b><i>"+city.getNameItalian()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("pt")) {
                text = "<b>"+title.getNamePortuguese()+": </b><i>"+city.getNamePortuguese()+"</i>";
            }else{
                text= "<b>"+title.getNameEnglish()+": </b><i>"+city.getNameEnglish()+"</i>";
            }
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

        City city = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        if(position==0 && city.getIdCity()==-2){
            if(Locale.getDefault().getLanguage().equals("es")) {
                text = "<b>"+city.getNameSpanish()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text="<b>"+city.getNameFrench()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("eu")) {
                text = "<b>"+city.getNameBasque()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text="<b>"+city.getNameCatalan()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("nl")) {
                text = "<b>"+city.getNameDutch()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text="<b>"+city.getNameGalician()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("de")) {
                text = "<b>"+city.getNameGerman()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text="<b>"+city.getNameItalian()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("pt")) {
                text = "<b>"+city.getNamePortuguese()+"</b>";
            }else{
                text= "<b>"+city.getNameEnglish()+"</b>";
            }
        }else{
            if(Locale.getDefault().getLanguage().equals("es")) {
                text = "<i>"+city.getNameSpanish()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text="<i>"+city.getNameFrench()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("eu")) {
                text = "<i>"+city.getNameBasque()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text="<i>"+city.getNameCatalan()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("nl")) {
                text = "<i>"+city.getNameDutch()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text="<i>"+city.getNameGalician()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("de")) {
                text = "<i>"+city.getNameGerman()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text="<i>"+city.getNameItalian()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("pt")) {
                text = "<i>"+city.getNamePortuguese()+"</i>";
            }else{
                text= "<i>"+city.getNameEnglish()+"</i>";
            }
        }

        textView.setText(Html.fromHtml(text,0));

        return view;
    }
    @Override
    public City getItem(int position) {
        return super.getItem(position);
    }

    public String getText(){
        return text;
    }

}
