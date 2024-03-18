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
            text=evaluatorTeam.getEmailProfessional();
        }
        else{
            if(Locale.getDefault().getLanguage().equals("es")){
                text= "Persona TEA: "+evaluatorTeam.getPatient_name()+", Familiar:"+evaluatorTeam.getRelative_name();
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text= "Personne TSA: "+evaluatorTeam.getPatient_name()+", Membre de la famille"+evaluatorTeam.getRelative_name();
            }else if(Locale.getDefault().getLanguage().equals("eu")){
                text= "TEA pertsona: "+evaluatorTeam.getPatient_name()+", Familia-kide:"+evaluatorTeam.getRelative_name();
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text= "Persona TEA: "+evaluatorTeam.getPatient_name()+", Familiar:"+evaluatorTeam.getRelative_name();
            }else if(Locale.getDefault().getLanguage().equals("nl")){
                text= "Persoon ASS: "+evaluatorTeam.getPatient_name()+", Familielid:"+evaluatorTeam.getRelative_name();
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text= "Persoa TEA: "+evaluatorTeam.getPatient_name()+", Familiar:"+evaluatorTeam.getRelative_name();
            }else if(Locale.getDefault().getLanguage().equals("de")){
                text= "Person ASS: "+evaluatorTeam.getPatient_name()+", Familienmitglied:"+evaluatorTeam.getRelative_name();
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text= "Persona DSA: "+evaluatorTeam.getPatient_name()+", Familiare:"+evaluatorTeam.getRelative_name();
            }else if(Locale.getDefault().getLanguage().equals("pt")){
                text= "Pessoa TEA: "+evaluatorTeam.getPatient_name()+", Familiar:"+evaluatorTeam.getRelative_name();
            }else{//Default: English
                text= "ASD person: "+evaluatorTeam.getPatient_name()+", Family member:"+evaluatorTeam.getRelative_name();
            }
        }
        textView.setText(text);

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
