package rrdl.crt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener,Donation.OnFragmentInteractionListener,Notification.OnFragmentInteractionListener{

    private TextView mTextMessage;
    Button btn1;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fm=getSupportFragmentManager();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fm.beginTransaction().replace(R.id.content,new BlankFragment()).commit();

                    return true;
                case R.id.navigation_dashboard:
                    fm.beginTransaction().replace(R.id.content,new Donation()).commit();
                    Intent i2=new Intent(getApplicationContext(),asphyxiation.class);
                    startActivity(i2);
                    return true;
                case R.id.navigation_notifications:
                    fm.beginTransaction().replace(R.id.content,new Notification()).commit();
                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().add(R.id.content,new BlankFragment()).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
