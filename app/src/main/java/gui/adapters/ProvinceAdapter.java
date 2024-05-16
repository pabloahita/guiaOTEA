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

        if(position==0){
            if(province.getIdProvince()==-2){
                if(Locale.getDefault().getLanguage().equals("es")) {
                    text = "<b>"+province.getNameSpanish()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    text="<b>"+province.getNameFrench()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("eu")) {
                    text = "<b>"+province.getNameBasque()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    text="<b>"+province.getNameCatalan()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("nl")) {
                    text = "<b>"+province.getNameDutch()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    text="<b>"+province.getNameGalician()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("de")) {
                    text = "<b>"+province.getNameGerman()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    text="<b>"+province.getNameItalian()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("pt")) {
                    text = "<b>"+province.getNamePortuguese()+"</b>";
                }else{
                    text= "<b>"+province.getNameEnglish()+"</b>";
                }
            }
            else{
                if(Locale.getDefault().getLanguage().equals("es")) {
                    text = "<b>Provincia: </b><i>"+province.getNameSpanish()+"</i>";
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    text="<b>Province: </b><i>"+province.getNameFrench()+"</i>";
                }else if(Locale.getDefault().getLanguage().equals("eu")) {
                    text = "<b>Probintzia: </b><i>"+province.getNameBasque()+"</i>";
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    text="<b>Província: </b><i>"+province.getNameCatalan()+"</i>";
                }else if(Locale.getDefault().getLanguage().equals("nl")) {
                    text = "<b>Provincie: </b><i>"+province.getNameDutch()+"</b>";
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    text="<b>Provincia: </b><i>"+province.getNameGalician()+"</i>";
                }else if(Locale.getDefault().getLanguage().equals("de")) {
                    text = "<b>Provinz: </b><i>"+province.getNameGerman()+"</i>";
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    text="<b>Provincia: </b><i>"+province.getNameItalian()+"</i>";
                }else if(Locale.getDefault().getLanguage().equals("pt")) {
                    text = "<b>Província: </b><i>"+province.getNamePortuguese()+"</i>";
                }else{
                    text= "<b>Province: </b><i>"+province.getNameEnglish()+"</i>";
                }
            }
        }else{
            Province title=getItem(0);
            if(Locale.getDefault().getLanguage().equals("es")) {
                text = "<b>"+title.getNameSpanish()+": </b><i>"+province.getNameSpanish()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text="<b>"+title.getNameFrench()+": </b><i>"+province.getNameFrench()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("eu")) {
                text = "<b>"+title.getNameBasque()+": </b><i>"+province.getNameBasque()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text="<b>"+title.getNameCatalan()+": </b><i>"+province.getNameCatalan()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("nl")) {
                text = "<b>"+title.getNameDutch()+": </b><i>"+province.getNameDutch()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text="<b>"+title.getNameGalician()+": </b><i>"+province.getNameGalician()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("de")) {
                text = "<b>"+title.getNameGerman()+": </b><i>"+province.getNameGerman()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text="<b>"+title.getNameItalian()+": </b><i>"+province.getNameItalian()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("pt")) {
                text = "<b>"+title.getNamePortuguese()+": </b><i>"+province.getNamePortuguese()+"</i>";
            }else{
                text= "<b>"+title.getNameEnglish()+": </b><i>"+province.getNameEnglish()+"</i>";
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

        Province province = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);

        if(position==0 && province.getIdProvince()==-2){
            if(Locale.getDefault().getLanguage().equals("es")) {
                text = "<b>"+province.getNameSpanish()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text="<b>"+province.getNameFrench()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("eu")) {
                text = "<b>"+province.getNameBasque()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text="<b>"+province.getNameCatalan()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("nl")) {
                text = "<b>"+province.getNameDutch()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text="<b>"+province.getNameGalician()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("de")) {
                text = "<b>"+province.getNameGerman()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text="<b>"+province.getNameItalian()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("pt")) {
                text = "<b>"+province.getNamePortuguese()+"</b>";
            }else{
                text= "<b>"+province.getNameEnglish()+"</b>";
            }
        }else{
            if(Locale.getDefault().getLanguage().equals("es")) {
                text = "<i>"+province.getNameSpanish()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text="<i>"+province.getNameFrench()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("eu")) {
                text = "<i>"+province.getNameBasque()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text="<i>"+province.getNameCatalan()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("nl")) {
                text = "<i>"+province.getNameDutch()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text="<i>"+province.getNameGalician()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("de")) {
                text = "<i>"+province.getNameGerman()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text="<i>"+province.getNameItalian()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("pt")) {
                text = "<i>"+province.getNamePortuguese()+"</i>";
            }else{
                text= "<i>"+province.getNameEnglish()+"</i>";
            }
        }
        textView.setText(Html.fromHtml(text,0));

        return view;
    }
    @Override
    public Province getItem(int position) {
        return super.getItem(position);
    }

    public String getText(){
        return text;
    }

}