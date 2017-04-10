package com.example.mrides.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.mrides.Notification.InboxAdapter;
import com.example.mrides.Notification.Notification;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
import com.example.mrides.userDomain.UserSerializer;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InboxActivity extends AppCompatActivity implements ActivityObserver{

    private RecyclerView mRecyclerView;
    private RemoteMessage notification;
    private RequestHandler requestHandler = new RequestHandler();
    private List<Notification> notifications = new ArrayList<>();

    /**
     * When the inbox is created the inbox gets a list of past notifications, inorder
     * to populate the inbox. Additionally when a notification is recieved and the user
     * selects the notification, the inbox will add an additional notification to the inbox.
     *
     * @param savedInstanceState
     */
    @Override //TODO a script is needed to retrieve past notifications that have not been accepted yet
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        getInboxData(); //TODO call this method to get the list of inbox data

    }

    private void getInboxData() {
        requestHandler.attach(this);
        Map<String,String> userInfo = UserSerializer.getParameters(RequestHandler.getUser());
        requestHandler.httpPostStringRequest("http://"+this.getString(R.string.web_server_ip)+
                        "/getNotifications.php",userInfo,
                RequestHandler.URLENCODED, this);
    }

    @Override
    public void Update(String response) {
        handlepopulateInboxResponse(response);
        RecyclerView.Adapter mAdapter = new InboxAdapter(this, notifications);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void handlepopulateInboxResponse(String response) {
        try {
            JSONArray invites = new JSONArray(response);
            for(int index =0;index<invites.length();index++) {
                JSONObject inviteJson = invites.getJSONObject(index);
                Notification invite = new Notification(inviteJson.getString("email"),
                        inviteJson.getString("rating"),inviteJson.getString("profile_picture"),
                        inviteJson.getString("first_name"),inviteJson.getString("last_name"));
                notifications.add(invite);
            }
        } catch (JSONException e) {
            Log.e("InboxActivity", e.getMessage());
        }
    }
}
