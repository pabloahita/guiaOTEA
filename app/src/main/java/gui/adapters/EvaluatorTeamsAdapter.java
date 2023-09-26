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
        text= evaluatorTeam.getPatient_name()+", "+evaluatorTeam.getRelative_name();
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
