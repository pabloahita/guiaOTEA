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
        if(user.getEmailUser().equals("-1")){//For auxiliar spinners
            text=user.getFirst_name();
        }
        else {
            text = user.getFirst_name() + ", " + user.getLast_name() + " (" + user.getEmailUser() + ")";
        }
        textView.setText(text);

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
    @Override
    public User getItem(int position) {
        return super.getItem(position);
    }

    public String getText(){
        return text;
    }
}
