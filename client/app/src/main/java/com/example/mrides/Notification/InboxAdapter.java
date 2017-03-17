package com.example.mrides.Notification;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrides.Activity.InboxActivity;
import com.example.mrides.R;

import java.util.Map;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder>{

    private Map<String,String> responseBody;
    private Context inboxContext;

    public InboxAdapter(Context inboxContext){
        this.inboxContext = inboxContext;
    }

    public void setViewComponents(Map<String,String> body){
        this.responseBody = body;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_inbox_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //TODO this method only adds 1 item to the inbox. That item is the notification message
    // Therefore the position variable is not used. In the future we need to implement a
    // script to get all the inbox messages
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemMessage.setText(inboxContext.getString(R.string.invited_to_route)
                + " " + responseBody.get("loggedInUserEmail"));
    }

    //number of elements which need to be shown for now 1 however as the inbox grows
    //more the item list needs to be representative of the number of inbox values
    @Override
    public int getItemCount() {
        return 1;
    }

    /**
     * Provide a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder
     */

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView itemMessage;
        ImageView profilePciture;
        View v;
        public ViewHolder(View v) {
            super(v);
            profilePciture = (ImageView) itemView.findViewById(R.id.inbox_profile_pic);
            itemMessage = (TextView) itemView.findViewById(R.id.item_title);
        }
    }
}
