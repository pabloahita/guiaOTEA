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
        if(evaluatorTeam.getIdEvaluatorTeam()==-1){
            text="<b>"+evaluatorTeam.getEmailProfessional()+"</b>";
        }
        else{
            if(Locale.getDefault().getLanguage().equals("es")){
                text= "<b>Persona TEA: </b><i>"+evaluatorTeam.getPatient_name()+"</i>,<b> Familiar: </b><i>"+evaluatorTeam.getRelative_name()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text= "<b>Personne TSA: </b><i>"+evaluatorTeam.getPatient_name()+"</i>,<b> Membre de la famille: </b><i>"+evaluatorTeam.getRelative_name()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("eu")){
                text= "<b>TEA pertsona: </b><i>"+evaluatorTeam.getPatient_name()+"</i>,<b> Familia-kide: </b><i>"+evaluatorTeam.getRelative_name()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text= "<b>Persona TEA: </b><i>"+evaluatorTeam.getPatient_name()+"</i>,<b> Familiar: </b><i>"+evaluatorTeam.getRelative_name()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("nl")){
                text= "<b>Persoon ASS: </b><i>"+evaluatorTeam.getPatient_name()+"</i>,<b> Familielid: </b><i>"+evaluatorTeam.getRelative_name()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text= "<b>Persoa TEA: </b><i>"+evaluatorTeam.getPatient_name()+"</i>,<b> Familiar: </b><i>"+evaluatorTeam.getRelative_name()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("de")){
                text= "<b>Person ASS: </b><i>"+evaluatorTeam.getPatient_name()+"</i>,<b> Familienmitglied: </b><i>"+evaluatorTeam.getRelative_name()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text= "<b>Persona DSA: </b><i>"+evaluatorTeam.getPatient_name()+"</i>,<b> Familiare: </b><i>"+evaluatorTeam.getRelative_name()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("pt")){
                text= "<b>Pessoa TEA: </b><i>"+evaluatorTeam.getPatient_name()+"</i>,<b> Familiar: </b><i>"+evaluatorTeam.getRelative_name()+"</i>";
            }else{//Default: English
                text= "<b>ASD person: </b><i>"+evaluatorTeam.getPatient_name()+"</i>,<b> Family member: </b><i>"+evaluatorTeam.getRelative_name()+"</i>";
            }
        }
        textView.setText(Html.fromHtml(text,0));

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
    @Override
    public EvaluatorTeam getItem(int position) {
        return super.getItem(position);
    }

    public String getText(){
        return text;
    }
}
