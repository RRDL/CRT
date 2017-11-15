package rrdl.crt;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Locale;

import rrdl.crt.SocketService.LocalBinder;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener,Donation.OnFragmentInteractionListener,Notification.OnFragmentInteractionListener,Setting.OnFragmentInteractionListener,About.OnFragmentInteractionListener,CrtInfo.OnFragmentInteractionListener{


    FragmentManager fm ;
    private Locale mylocale;
    private String Language;
    private Setting s;
    SocketService myService ;
    Boolean isBound= false;

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
        fm=getSupportFragmentManager();
        fm.beginTransaction().add(R.id.content,new BlankFragment()).commit();
        Intent i = new Intent(this,SocketService.class);
        bindService(i,myConnection,Context.BIND_AUTO_CREATE);
    }

    public void sendMessage(String msg){
        myService.sendMessage(msg);
        Toast.makeText(getApplicationContext(),"SENT : "+msg,Toast.LENGTH_LONG).show();
    }
    private ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalBinder binder = (LocalBinder) service;
            myService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            (new intentHandler()).Exit();
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.settings) {
            fm.beginTransaction().replace(R.id.content, new Setting()).commit();

            return true;
        }
        if (id == R.id.about){
            fm.beginTransaction().replace(R.id.content, new About()).commit();
        }
        if (id == R.id.crt){
            fm.beginTransaction().replace(R.id.content, new CrtInfo()).commit();
        }
        if (id == R.id.exit){
            finish();
            (new intentHandler()).Exit();

        }

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
