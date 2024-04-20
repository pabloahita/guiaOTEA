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
        String textBegin="";
        String textEnd="";
        if(center.getIdCenter()==-1){
            textBegin="<b>";
            textEnd="</b>";
        }else{
            textBegin="<i>";
            textEnd="</i>";
        }
        if(Locale.getDefault().getLanguage().equals("es")){
            text=textBegin+center.getDescriptionSpanish()+textEnd;
        }else if(Locale.getDefault().getLanguage().equals("fr")){
            text=textBegin+center.getDescriptionFrench()+textEnd;
        }else if(Locale.getDefault().getLanguage().equals("eu")){
            text=textBegin+center.getDescriptionBasque()+textEnd;
        }else if(Locale.getDefault().getLanguage().equals("ca")){
            text=textBegin+center.getDescriptionCatalan()+textEnd;
        }else if(Locale.getDefault().getLanguage().equals("nl")){
            text=textBegin+center.getDescriptionDutch()+textEnd;
        }else if(Locale.getDefault().getLanguage().equals("gl")){
            text=textBegin+center.getDescriptionGalician()+textEnd;
        }else if(Locale.getDefault().getLanguage().equals("de")){
            text=textBegin+center.getDescriptionGerman()+textEnd;
        }else if(Locale.getDefault().getLanguage().equals("it")){
            text=textBegin+center.getDescriptionItalian()+textEnd;
        }else if(Locale.getDefault().getLanguage().equals("pt")){
            text=textBegin+center.getDescriptionPortuguese()+textEnd;
        }else{ //Valor por defecto
            text=textBegin+center.getDescriptionEnglish()+textEnd;
        }
        textView.setText(Html.fromHtml(text,0));

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
    @Override
    public Center getItem(int position) {
        return super.getItem(position);
    }

    public String getText(){
        return text;
    }
}
