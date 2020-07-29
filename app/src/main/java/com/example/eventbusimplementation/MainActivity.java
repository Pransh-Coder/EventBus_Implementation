package com.example.eventbusimplementation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent = new Intent(MainActivity.this, EventService.class);
        Log.d("onCreate", "before StartService");
        startService(serviceIntent);
        Log.d("onCreate", "after startservice" );
    }
    //Subscribe -  respond to the posted event by publisher
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventService eventService){
        Log.e("onMessageEvent", ""+eventService.resultVal);
        Toast.makeText(this, ""+eventService.getResultVal()+" <-->"+eventService.getResult_code(), Toast.LENGTH_SHORT).show();
    }


    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void doThis(IntentServiceResult intentServiceResult) {
        Toast.makeText(this, intentServiceResult.getResultValue(), Toast.LENGTH_SHORT).show();
    }*/
    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }*/
}