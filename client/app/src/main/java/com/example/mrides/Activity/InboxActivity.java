package com.example.mrides.Activity;

import android.app.Dialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrides.Notification.InboxAdapter;
import com.example.mrides.Notification.Invitation;
import com.example.mrides.Notification.MatchingMessagingService;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
import com.example.mrides.controller.Subject;
import com.example.mrides.userDomain.PassengerSerializer;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InboxActivity extends AppCompatActivity implements ActivityObserver{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RemoteMessage notification;
    private RequestHandler requestHandler = new RequestHandler();
    private List<Invitation> invitations = new ArrayList<>();

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
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        getInboxData(); //TODO call this method to get the list of inbox data
        mAdapter = new InboxAdapter(this,invitations);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getInboxData(){
        requestHandler.attach(this);
        Map<String,String> userInfo = PassengerSerializer.getParameters(RequestHandler.getUser());
        requestHandler.httpPostStringRequest("http://"+this.getString(R.string.web_server_ip)+
                        "/getNotifications.php",userInfo,
                "application/x-www-form-urlencoded; charset=UTF-8", this);
    }

    @Override
    public void Update(String response) {
        System.out.println(response);
        handlepopulateInboxResponse(response);
        mAdapter = new InboxAdapter(this,invitations);
        mRecyclerView.setAdapter(mAdapter);
        if(getIntent().getParcelableExtra("NOTIFICATION")!=null) {
            notification = (RemoteMessage) (getIntent().getParcelableExtra("NOTIFICATION"));
            ((InboxAdapter) mAdapter).setViewComponents(notification.getData());
        }
    }

    private void handlepopulateInboxResponse(String response){
        try {
            JSONArray invites = new JSONArray(response);
            for(int index =0;index<invites.length();index++){
                JSONObject inviteJson = invites.getJSONObject(index);
                Invitation invite = new Invitation(inviteJson.getString("email"),
                        inviteJson.getString("rating"),inviteJson.getString("profile_picture"),
                        inviteJson.getString("first_name"),inviteJson.getString("last_name"));
                invitations.add(invite);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
