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

import cli.organization.data.geo.Region;

public class RegionAdapter extends ArrayAdapter<Region> {

    String text;

    public RegionAdapter(Context context, List<Region> objects) {
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

        Region region = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);

        if(position==0){
            if(Locale.getDefault().getLanguage().equals("es")) {
                text = "<b>"+region.getNameSpanish()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text="<b>"+region.getNameFrench()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("eu")) {
                text = "<b>"+region.getNameBasque()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text="<b>"+region.getNameCatalan()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("nl")) {
                text = "<b>"+region.getNameDutch()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text="<b>"+region.getNameGalician()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("de")) {
                text = "<b>"+region.getNameGerman()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text="<b>"+region.getNameItalian()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("pt")) {
                text = "<b>"+region.getNamePortuguese()+"</b>";
            }else{
                text= "<b>"+region.getNameEnglish()+"</b>";
            }
        }else{
            Region title=getItem(0);
            if(Locale.getDefault().getLanguage().equals("es")) {
                text = "<b>"+title.getNameSpanish()+": </b><i>"+region.getNameSpanish()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text="<b>"+title.getNameFrench()+": </b><i>"+region.getNameFrench()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("eu")) {
                text = "<b>"+title.getNameBasque()+": </b><i>"+region.getNameBasque()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text="<b>"+title.getNameCatalan()+": </b><i>"+region.getNameCatalan()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("nl")) {
                text = "<b>"+title.getNameDutch()+": </b><i>"+region.getNameDutch()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text="<b>"+title.getNameGalician()+": </b><i>"+region.getNameGalician()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("de")) {
                text = "<b>"+title.getNameGerman()+": </b><i>"+region.getNameGerman()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text="<b>"+title.getNameItalian()+": </b><i>"+region.getNameItalian()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("pt")) {
                text = "<b>"+title.getNamePortuguese()+": </b><i>"+region.getNamePortuguese()+"</i>";
            }else{
                text= "<b>"+title.getNameEnglish()+": </b><i>"+region.getNameEnglish()+"</i>";
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

        Region region = getItem(position);
        TextView textView = view.findViewById(android.R.id.text1);

        if(position==0){
            if(Locale.getDefault().getLanguage().equals("es")) {
                text = "<b>"+region.getNameSpanish()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text="<b>"+region.getNameFrench()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("eu")) {
                text = "<b>"+region.getNameBasque()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text="<b>"+region.getNameCatalan()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("nl")) {
                text = "<b>"+region.getNameDutch()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text="<b>"+region.getNameGalician()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("de")) {
                text = "<b>"+region.getNameGerman()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text="<b>"+region.getNameItalian()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("pt")) {
                text = "<b>"+region.getNamePortuguese()+"</b>";
            }else{
                text= "<b>"+region.getNameEnglish()+"</b>";
            }
        }else{
            if(Locale.getDefault().getLanguage().equals("es")) {
                text = "<i>"+region.getNameSpanish()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                text="<i>"+region.getNameFrench()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("eu")) {
                text = "<i>"+region.getNameBasque()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                text="<i>"+region.getNameCatalan()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("nl")) {
                text = "<i>"+region.getNameDutch()+"</b>";
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                text="<i>"+region.getNameGalician()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("de")) {
                text = "<i>"+region.getNameGerman()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("it")){
                text="<i>"+region.getNameItalian()+"</i>";
            }else if(Locale.getDefault().getLanguage().equals("pt")) {
                text = "<i>"+region.getNamePortuguese()+"</i>";
            }else{
                text= "<i>"+region.getNameEnglish()+"</i>";
            }
        }
        textView.setText(Html.fromHtml(text,0));

        return view;
    }
    @Override
    public Region getItem(int position) {
        return super.getItem(position);
    }

    public String getText(){
        return text;
    }

}