package rrdl.crt;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener,Donation.OnFragmentInteractionListener,Notification.OnFragmentInteractionListener,Setting.OnFragmentInteractionListener{


    FragmentManager fm =getSupportFragmentManager();;
    private Locale mylocale;
    String Language;
    Setting s;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.home:
                    fm.beginTransaction().replace(R.id.content,new BlankFragment()).commit();
                    return true;
                case R.id.chat:
                    fm.beginTransaction().replace(R.id.content,new Donation()).commit();
                    return true;
                case R.id.map:
                    fm.beginTransaction().replace(R.id.content,new Notification()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        s = new Setting();
        Language = s.getLang();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Language = preferences.getString("l",Language);
        setLanguage(Language);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().add(R.id.content,new BlankFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("lang", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("l",Language);
        editor.apply();
    }


    /*
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("lang", Context.MODE_PRIVATE);
        sharedPreferences.getString("lang",Language);
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.settings) {
            fm.beginTransaction().replace(R.id.content, new Setting()).commit();

            return true;
        }
        if (id == R.id.about){}
        if (id == R.id.crt){}
        if (id == R.id.exit){}

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void setLanguage(String language){
        mylocale=new Locale(language);
        Resources resources=getResources();
        DisplayMetrics dm=resources.getDisplayMetrics();
        Configuration conf= resources.getConfiguration();
        conf.locale=mylocale;
        resources.updateConfiguration(conf,dm);
        getBaseContext().getResources().updateConfiguration(conf,
                getBaseContext().getResources().getDisplayMetrics());
    }

}
