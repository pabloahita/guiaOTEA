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

import cli.organization.data.EvaluatorTeam;


public class EvaluatorTeamsAdapter extends ArrayAdapter<EvaluatorTeam> {

    String text;

    public EvaluatorTeamsAdapter(Context context, List<EvaluatorTeam> objects) {
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

        EvaluatorTeam evaluatorTeam = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        if(position==0){
            text="<b>"+evaluatorTeam.getEmailProfessional()+"</b>";
        }
        else{
            EvaluatorTeam title=getItem(0);
            if(Locale.getDefault().getLanguage().equals("es")){
                text= "<b>"+title.getEmailProfessional()+"</b>: <ul><li><b>Persona TEA: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Familiar: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text= "<b>"+title.getEmailProfessional()+"</b>: <ul><li><b>Personne TSA: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Membre de la famille: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("eu")){
                text= "<b>"+title.getEmailProfessional()+"</b>: <ul><li><b>TEA pertsona: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Familia-kide: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text= "<b>"+title.getEmailProfessional()+"</b>: <ul><li><b>Persona TEA: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Familiar: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("nl")){
                text= "<b>"+title.getEmailProfessional()+"</b>: <ul><li><b>Persoon ASS: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Familielid: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text= "<b>"+title.getEmailProfessional()+"</b>: <ul><li><b>Persoa TEA: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Familiar: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("de")){
                text= "<b>"+title.getEmailProfessional()+"</b>: <ul><li><b>Person ASS: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Familienmitglied: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text= "<b>"+title.getEmailProfessional()+"</b>: <ul><li><b>Persona DSA: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Familiare: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("pt")){
                text= "<b>"+title.getEmailProfessional()+"</b>: <ul><li><b>Pessoa TEA: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Familiar: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else{//Default: English
                text= "<b>"+title.getEmailProfessional()+"</b>: <ul><li><b>ASD person: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Family member: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
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

        EvaluatorTeam evaluatorTeam = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        if(position==0){
            text="<b>"+evaluatorTeam.getEmailProfessional()+"</b>";
        }
        else{
            if(Locale.getDefault().getLanguage().equals("es")){
                text= "<ul><li><b>Persona TEA: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Familiar: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text= "<ul><li><b>Personne TSA: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Membre de la famille: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("eu")){
                text= "<ul><li><b>TEA pertsona: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Familia-kide: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text= "<ul><li><b>Persona TEA: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Familiar: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("nl")){
                text= "<ul><li><b>Persoon ASS: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Familielid: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text= "<ul><li><b>Persoa TEA: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Familiar: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("de")){
                text= "<ul><li><b>Person ASS: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Familienmitglied: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text= "<ul><li><b>Persona DSA: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Familiare: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else if(Locale.getDefault().getLanguage().equals("pt")){
                text= "<ul><li><b>Pessoa TEA: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Familiar: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }else{//Default: English
                text= "<ul><li><b>ASD person: </b><i>"+evaluatorTeam.getPatient_name()+"</i></li><li><b> Family member: </b><i>"+evaluatorTeam.getRelative_name()+"</i></li></ul>";
            }
        }
        textView.setText(Html.fromHtml(text,0));

        return view;
    }
    @Override
    public EvaluatorTeam getItem(int position) {
        return super.getItem(position);
    }

    public String getText(){
        return text;
    }
}
