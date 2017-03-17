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
import com.example.mrides.Notification.MatchingMessagingService;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
import com.example.mrides.controller.Subject;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class InboxActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RemoteMessage notification;
    private RequestHandler requestHandler = new RequestHandler();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
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
        mAdapter = new InboxAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        notification = (RemoteMessage) (getIntent().getParcelableExtra("NOTIFICATION"));
        if(notification!=null)
        ((InboxAdapter) mAdapter).setViewComponents(notification.getData());

    }

}
