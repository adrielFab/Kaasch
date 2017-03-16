package com.example.mrides.Activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrides.Notification.InboxAdapter;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;

public class InboxActivity extends AppCompatActivity implements ActivityObserver{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private RequestHandler requestHandler = new RequestHandler();
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


    }

    @Override
    public void responseReceived(String response) {
        // specify an adapter (see also next example)
        mAdapter = new InboxAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }
}
