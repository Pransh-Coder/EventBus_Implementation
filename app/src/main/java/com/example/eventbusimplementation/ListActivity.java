package com.example.eventbusimplementation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.eventbusimplementation.NetworkingCalls.NetworkingCalls;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    NetworkingCalls networkingCalls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        networkingCalls= new NetworkingCalls(this,this);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent serviceIntent = new Intent(ListActivity.this, EventService.class);
        startService(serviceIntent);

        networkingCalls.showCertificateList();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventService eventService){

        RecyclerAdapterProfile recyclerAdapterProfile = new RecyclerAdapterProfile(this,eventService.profilePojoList,this);
        recyclerView.setAdapter(recyclerAdapterProfile);
        recyclerAdapterProfile.notifyDataSetChanged();      //Notify any registered observers that the data set has changed
    }

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
}