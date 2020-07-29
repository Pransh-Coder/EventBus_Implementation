package com.example.eventbusimplementation.NetworkingCalls;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eventbusimplementation.EventService;
import com.example.eventbusimplementation.ProfilePojo;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NetworkingCalls  {

    private Context context;
    private RequestQueue requestQueue;
    Activity activity;

    List<ProfilePojo> profilePojoList = new ArrayList<>();

    public NetworkingCalls(Context context, Activity activity) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(this.context);
        this.activity = activity;
    }

    private void addToQueue(StringRequest request) {
        request.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(30),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    public List<ProfilePojo> showCertificateList(){

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Fetching Certificates");
        dialog.setTitle("Please Wait!!");
        dialog.setCancelable(false);
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, "http://worldtpm.dx.am/api/getCerificates.php?u=3", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    dialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);  // converting string response into Jsonobject
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    //final RecyclerAdapterProfile recyclerAdapterProfile = new RecyclerAdapterProfile(context , profilePojoList, activity); // had declared recyclerview here to bring it in scope

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);

                        final ProfilePojo profilePojo = new ProfilePojo();

                        profilePojo.setName(data.getString("Name"));
                        profilePojo.setIssueDate(data.getString("Date"));
                        profilePojo.setPercent(data.getString("Percentage"));
                        profilePojo.setCertificateUrl(data.getString("CertificateURL"));

                        profilePojoList.add(profilePojo);
                        //recyclerAdapterProfile.notifyDataSetChanged();
                    }
                    //recyclerView.setAdapter(recyclerAdapterProfile);
                    EventBus.getDefault().post(new EventService(profilePojoList));
                    Log.e("NetworkingCalls","Event Fired "+profilePojoList.size());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();

            }
        });
        addToQueue(request);

        return profilePojoList;

    }
}
