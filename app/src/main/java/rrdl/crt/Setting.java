package rrdl.crt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

public class Setting extends Fragment {

    private CheckBox en;
    private CheckBox ar;
    private static String lang = "";


    public Setting(){
    }
    public static Setting newInstance() {
        Setting fragment = new Setting();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View v =  inflater.inflate(R.layout.activity_setting, container, false);
         SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
         lang = preferences.getString("l",lang);
         en = (CheckBox) v.findViewById(R.id.english);
         ar = (CheckBox) v.findViewById(R.id.arabic);
         en.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (ar.isChecked()){
                     ar.setChecked(false);}
                     lang = ""; // 0 english
                 setLanguage(lang);

             }
         });
         ar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (en.isChecked()){
                     en.setChecked(false);}
                 lang = "ar"; // 1 arabic
                 setLanguage(lang);
             }
         });
        if(lang.equals("")){
            en.setChecked(true);
            ar.setChecked(false);}
        if(lang.equals("ar")){
            ar.setChecked(true);
            en.setChecked(false);}
        return v;
    }
    public String getLang(){
        return lang;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
    }


    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("l",lang);
        editor.apply();
    }


    //change language

    private void setLanguage(String language){
        if(language.equals("")){en.setChecked(true);}
        if(language.equals("ar")){ar.setChecked(true);}
        Intent refreshIntent=new Intent(this.getContext(),MainActivity.class);
        startActivity(refreshIntent);
    }

}
