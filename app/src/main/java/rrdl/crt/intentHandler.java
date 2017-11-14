package rrdl.crt;

import android.app.Activity;
import android.content.Intent;

class intentHandler {

    void start(Activity a, Class e){
        Intent i = new Intent(a,e);
        a.startActivity(i);
    }

    intentHandler(){}
    void killIntent(Activity a){
        Intent i = new Intent(a,MainActivity.class);
        a.startActivity(i);
        a.finish();
    }
    void Exit(){
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

}
