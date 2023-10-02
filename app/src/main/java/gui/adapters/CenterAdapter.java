package gui.adapters;

import android.content.Context;
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
        if(Locale.getDefault().getLanguage().equals("es")){
            text=center.getDescriptionSpanish();
        }else if(Locale.getDefault().getLanguage().equals("fr")){
            text=center.getDescriptionFrench();
        }else if(Locale.getDefault().getLanguage().equals("eu")){
            text=center.getDescriptionBasque();
        }else if(Locale.getDefault().getLanguage().equals("ca")){
            text=center.getDescriptionCatalan();
        }else if(Locale.getDefault().getLanguage().equals("nl")){
            text=center.getDescriptionDutch();
        }else if(Locale.getDefault().getLanguage().equals("gl")){
            text=center.getDescriptionGalician();
        }else if(Locale.getDefault().getLanguage().equals("de")){
            text=center.getDescriptionGerman();
        }else if(Locale.getDefault().getLanguage().equals("it")){
            text=center.getDescriptionItalian();
        }else if(Locale.getDefault().getLanguage().equals("pt")){
            text=center.getDescriptionPortuguese();
        }else{ //Valor por defecto
            text=center.getDescriptionEnglish();
        }
        textView.setText(text);

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
