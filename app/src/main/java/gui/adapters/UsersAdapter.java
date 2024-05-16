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

import cli.user.User;


public class UsersAdapter extends ArrayAdapter<User> {
    String text;

    public UsersAdapter(Context context, List<User> objects) {
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

        User user = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        if(position==0){//For auxiliar spinners
            text="<b>"+user.getFirst_name()+"</b>";
        }
        else {
            User title=getItem(0);
            text = "<b>"+title.getFirst_name()+": </b><i>"+user.getFirst_name() + " " + user.getLast_name() + "</i>";
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

        User user = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);
        if(position==0){//For auxiliar spinners
            text="<b>"+user.getFirst_name()+"</b>";
        }
        else {
            text = "<i>"+user.getFirst_name() + " " + user.getLast_name() + " <b>" + user.getEmailUser() + "</b></i>";
        }
        textView.setText(Html.fromHtml(text,0));

        return view;
    }
    @Override
    public User getItem(int position) {
        return super.getItem(position);
    }

    public String getText(){
        return text;
    }
}
