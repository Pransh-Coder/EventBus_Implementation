package com.example.eventbusimplementation;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class EventService extends IntentService {

    int result_code;
    String resultVal;
    List<ProfilePojo> profilePojoList = new ArrayList<>();

    public EventService() {
        super("service");
    }

    public EventService(int result_code, String resultVal) {
        super("My EventService");
        this.result_code = result_code;
        this.resultVal = resultVal;
    }

    public EventService(List<ProfilePojo> profilePojoList) {
        super("list");
        this.profilePojoList = profilePojoList;
    }

    public int getResult_code() {
        return result_code;
    }

    public String getResultVal() {
        return resultVal;
    }
    //Publisher - are responsible for posting events in response to some type of state change
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("onHandleIntent", "inside");
        EventBus.getDefault().post(new EventService(Activity.RESULT_OK,"Pransh"));
    }

}
