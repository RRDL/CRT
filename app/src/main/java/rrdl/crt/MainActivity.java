package rrdl.crt;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

import br.com.goncalves.pugnotification.notification.PugNotification;
import rrdl.crt.SocketService.LocalBinder;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener,Notification.OnFragmentInteractionListener,Setting.OnFragmentInteractionListener,About.OnFragmentInteractionListener,CrtInfo.OnFragmentInteractionListener,Donation.MessageSender {


    FragmentManager fm ;
    private String Language;
    SocketService myService ;
    Boolean isBound= false;
    Locale mylocale;
    Setting s;
    Boolean registred = false;
    String message;

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
        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm=getSupportFragmentManager();
        fm.beginTransaction().add(R.id.content,new BlankFragment()).commit();
        Intent i = new Intent(this,SocketService.class);
        bindService(i,myConnection,Context.BIND_AUTO_CREATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.e("Brodcast","Broadcast intent onCreate invoked");
        if(savedInstanceState == null){
        if(!registred){
            LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                    new IntentFilter("my-event"));
            Log.e("Registre","Broadcast intent  register");
            registred = true;
        }}

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Brodcast","Broadcast intent onStart invoked");

    }

    private ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBound = true;
            LocalBinder binder = (LocalBinder) service;
            myService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
            isBound = false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("Broadcast","BroadcastReceiver called");
            // Extract data included in the Intent
            message = intent.getStringExtra("message");
            Log.e("Recived : ",message+ " MainActivity.onrecieve");
            //Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            PugNotification.with(context)
                    .load()
                    .title("New Message Recieved")
                    .message(message)
                    .smallIcon(R.drawable.croissant)
                    .largeIcon(R.drawable.croissant)
                    .click(MainActivity.class)
                    .sound(defaultRingtoneUri)
                    .lights(Color.RED, 1, 1)
                    .simple()
                    .build();
                Log.e("Notification","Created");
               // fm.beginTransaction().replace(R.id.content,new Donation()).commitAllowingStateLoss();
                Donation.receiveMessage(message);
                Log.e("MainActivity","just call recieve method");
        }
    };

    @Override
    protected void onPause() {
        SharedPreferences sharedPreferences = getSharedPreferences("lang", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("l",Language);
        editor.apply();
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            (new intentHandler()).Exit();
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }*/

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
    public void setLanguage(String language){
        mylocale = new Locale(language);
        Resources resources=getResources();
        DisplayMetrics dm=resources.getDisplayMetrics();
        Configuration conf= resources.getConfiguration();
        conf.locale= mylocale;
        resources.updateConfiguration(conf,dm);
        getBaseContext().getResources().updateConfiguration(conf,
                getBaseContext().getResources().getDisplayMetrics());
    }


    @Override
    public void sendMessage(String message) {
        myService.sendMessage(message);
    }
    interface main {
        public void recieve(String s);
    }


}
