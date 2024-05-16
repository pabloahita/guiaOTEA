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

import com.fundacionmiradas.indicatorsevaluation.DataBinderMapperImpl;
import com.fundacionmiradas.indicatorsevaluation.R;

import java.util.List;
import java.util.Locale;

import cli.indicators.IndicatorsEvaluation;
import cli.organization.data.EvaluatorTeam;
import misc.DateFormatter;
import otea.connection.controller.EvaluatorTeamsController;

public class IndicatorsEvaluationAdapter extends ArrayAdapter<IndicatorsEvaluation> {

    String text;
    public IndicatorsEvaluationAdapter(@NonNull Context context, List<IndicatorsEvaluation> objects) {
        super(context, 0,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.spinner_item_layout, parent, false);
        }

        IndicatorsEvaluation indicatorsEvaluation = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        if(position==0){
            text="<b>"+indicatorsEvaluation.getEvaluationType()+"</b>";
        }
        else{
            IndicatorsEvaluation title=getItem(0);
            String evalDate=DateFormatter.timeStampToStrDate(indicatorsEvaluation.getEvaluationDate());
            if(Locale.getDefault().getLanguage().equals("es")){
                text= "<b>"+title.getEvaluationType()+"</b>: <ul><li><b>Fecha de inicio de evaluación de indicadores:</b><i>"+ evalDate +"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text= "<b>"+title.getEvaluationType()+"</b>: <ul><li><b>Date de début de l'évaluation des indicateurs:</b><i>"+ evalDate +"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("eu")){
                text= "<b>"+title.getEvaluationType()+"</b>: <ul><li><b>Adierazleak ebaluatzeko hasiera data:</b><i>"+ evalDate +"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text= "<b>"+title.getEvaluationType()+"</b>: <ul><li><b>Data d'inici de l'avaluació dels indicadors:</b><i>"+ evalDate +"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("nl")){
                text= "<b>"+title.getEvaluationType()+"</b>: <ul><li><b>Startdatum van de evaluatie van indicatoren:</b><i>"+ evalDate +"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text= "<b>"+title.getEvaluationType()+"</b>: <ul><li><b>Data de inicio da avaliación dos indicadores:</b><i>"+ evalDate +"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("de")){
                text= "<b>"+title.getEvaluationType()+"</b>: <ul><li><b>Startdatum der Bewertung von Indikatoren:</b><i>"+ evalDate +"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text= "<b>"+title.getEvaluationType()+"</b>: <ul><li><b>Data di inizio della valutazione degli indicatori:</b><i>"+ evalDate +"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("pt")){
                text= "<b>"+title.getEvaluationType()+"</b>: <ul><li><b>Data de início da avaliação dos indicadores:</b><i>"+ evalDate +"</i></li></ul>";
            }else{//Default: English
                text= "<b>"+title.getEvaluationType()+"</b>: <ul><li><b>Start date of indicators evaluation:</b><i>"+ evalDate +"</i></li></ul>";
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

        IndicatorsEvaluation indicatorsEvaluation = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        if(position==0){
            text="<b>"+indicatorsEvaluation.getEvaluationType()+"</b>";
        }
        else{
            String evalDate=DateFormatter.timeStampToStrDate(indicatorsEvaluation.getEvaluationDate());
            if(Locale.getDefault().getLanguage().equals("es")){
                text= "<ul><li><b>Fecha de inicio de evaluación de indicadores:</b><i>"+ evalDate +"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text= "<ul><li><b>Date de début de l'évaluation des indicateurs:</b><i>"+ evalDate +"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("eu")){
                text= "<ul><li><b>Adierazleak ebaluatzeko hasiera data:</b><i>"+ evalDate +"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text= "<ul><li><b>Data d'inici de l'avaluació dels indicadors:</b><i>"+ evalDate +"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("nl")){
                text= "<ul><li><b>Startdatum van de evaluatie van indicatoren:</b><i>"+ evalDate +"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text= "<ul><li><b>Data de inicio da avaliación dos indicadores:</b><i>"+ evalDate +"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("de")){
                text= "<ul><li><b>Startdatum der Bewertung von Indikatoren:</b><i>"+ evalDate +"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text= "<ul><li><b>Data di inizio della valutazione degli indicatori:</b><i>"+ evalDate +"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("pt")){
                text= "<ul><li><b>Data de início da avaliação dos indicadores:</b><i>"+ evalDate +"</i></li></ul>";
            }else{//Default: English
                text= "<ul><li><b>Start date of indicators evaluation:</b><i>"+ evalDate +"</i></li></ul>";
            }
        }
        textView.setText(Html.fromHtml(text,0));

        return view;
    }
    @Override
    public IndicatorsEvaluation getItem(int position) {
        return super.getItem(position);
    }

    public String getText(){
        return text;
    }
}
