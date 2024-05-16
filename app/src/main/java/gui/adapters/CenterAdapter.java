package gui.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.util.List;
import java.util.Locale;

import cli.organization.data.Center;

public class CenterAdapter extends ArrayAdapter<Center> {

    String text;

    public CenterAdapter(Context context, List<Center> objects) {
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

        Center center = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        if(position==0){
            if(Locale.getDefault().getLanguage().equals("es")){
                text="<b>"+center.getDescriptionSpanish()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text="<b>"+center.getDescriptionFrench()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("eu")){
                text="<b>"+center.getDescriptionBasque()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text="<b>"+center.getDescriptionCatalan()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("nl")){
                text="<b>"+center.getDescriptionDutch()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text="<b>"+center.getDescriptionGalician()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("de")){
                text="<b>"+center.getDescriptionGerman()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text="<b>"+center.getDescriptionItalian()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("pt")){
                text="<b>"+center.getDescriptionPortuguese()+"</b>";
            }else{ //Valor por defecto
                text="<b>"+center.getDescriptionEnglish()+"</b>";
            }
        }else{
            Center title=getItem(0);
            if(Locale.getDefault().getLanguage().equals("es")){
                text="<b>"+title.getDescriptionSpanish()+": </b><i>"+center.getDescriptionSpanish()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text="<b>"+title.getDescriptionFrench()+": </b><i>"+center.getDescriptionFrench()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("eu")){
                text="<b>"+title.getDescriptionBasque()+": </b><i>"+center.getDescriptionBasque()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text="<b>"+title.getDescriptionCatalan()+": </b><i>"+center.getDescriptionCatalan()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("nl")){
                text="<b>"+title.getDescriptionDutch()+": </b><i>"+center.getDescriptionDutch()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text="<b>"+title.getDescriptionGalician()+": </b><i>"+center.getDescriptionGalician()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("de")){
                text="<b>"+title.getDescriptionGerman()+": </b><i>"+center.getDescriptionGerman()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text="<b>"+title.getDescriptionItalian()+": </b><i>"+center.getDescriptionItalian()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("pt")){
                text="<b>"+title.getDescriptionPortuguese()+": </b><i>"+center.getDescriptionPortuguese()+"</i>";
            }else{ //Valor por defecto
                text="<b>"+title.getDescriptionEnglish()+": </b><i>"+center.getDescriptionEnglish()+"</i>";
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

        Center center = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        if(position==0){
            if(Locale.getDefault().getLanguage().equals("es")){
                text="<b>"+center.getDescriptionSpanish()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text="<b>"+center.getDescriptionFrench()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("eu")){
                text="<b>"+center.getDescriptionBasque()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text="<b>"+center.getDescriptionCatalan()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("nl")){
                text="<b>"+center.getDescriptionDutch()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text="<b>"+center.getDescriptionGalician()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("de")){
                text="<b>"+center.getDescriptionGerman()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text="<b>"+center.getDescriptionItalian()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("pt")){
                text="<b>"+center.getDescriptionPortuguese()+"</b>";
            }else{ //Valor por defecto
                text="<b>"+center.getDescriptionEnglish()+"</b>";
            }
        }else{
            if(Locale.getDefault().getLanguage().equals("es")){
                text="<i>"+center.getDescriptionSpanish()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text="<i>"+center.getDescriptionFrench()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("eu")){
                text="<i>"+center.getDescriptionBasque()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text="<i>"+center.getDescriptionCatalan()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("nl")){
                text="<i>"+center.getDescriptionDutch()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text="<i>"+center.getDescriptionGalician()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("de")){
                text="<i>"+center.getDescriptionGerman()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text="<i>"+center.getDescriptionItalian()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("pt")){
                text="<i>"+center.getDescriptionPortuguese()+"</i>";
            }else{ //Valor por defecto
                text="<i>"+center.getDescriptionEnglish()+"</i>";
            }
        }

        textView.setText(Html.fromHtml(text,0));

        return view;
    }
    @Override
    public Center getItem(int position) {
        return super.getItem(position);
    }

    public String getText(){
        return text;
    }
}
