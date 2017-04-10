package com.example.mrides.Notification;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrides.Invitation.Invitation;
import com.example.mrides.R;

import java.util.ArrayList;
import java.util.List;

/**
 * The adapter is the link between the data model we want to show and the UI.
 * This class tells the UI how to show past notifications which have not been declined or accepted
 * yet.
 *
 */
public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder>{

    private List<Notification> notifications = new ArrayList<>();
    protected static Context inboxContext;

    public InboxAdapter(Context inboxContext,List<Notification> notifications) {
        this.inboxContext = inboxContext;
        this.notifications = notifications;
    }

    /**
     * This method is called whenever a new instance of ViewHolder is created.
     * @param parent
     * @param viewType
     * @return Returns the view component. In this case it is a list item (contains textview and
     * profile picture)
     */

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_inbox_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(view, notifications.get(viewType));
        return vh;
    }

    //TODO this method only adds 1 item to the inbox. That item is the notification message
    // Therefore the position variable is not used. In the future we need to implement a
    // script to get all the inbox messages

    /**
     * This method is called when the data needs to be bound to the view. In this case the
     * data are past notifications which have not yet been declined or accepted.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setInvitation(notifications.get(position));
        UserProfileListener listener = new UserProfileListener(notifications.get(position),inboxContext);
        holder.itemMessage.setOnClickListener(listener);
        holder.profilePicture.setOnClickListener(listener);
    }

    /**
     * Returns the count of items to be added to the inbox.
     * @return
     */
    //TODO number of elements which need to be shown for now 1 however as the inbox grows
    //TODO more the item list needs to be representative of the number of inbox values
    @Override
    public int getItemCount() {
        return notifications.size();
    }

    /**
     * Provide a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder
     */

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView itemMessage;
        TextView itemMessageType;
        ImageView profilePicture;
        View v;
        public ViewHolder(View v, Notification notification) {
            super(v);
            //v.setOnClickListener(this);
            profilePicture = (ImageView) itemView.findViewById(R.id.inbox_profile_pic);
            itemMessage = (TextView) itemView.findViewById(R.id.item_title);
            itemMessageType = (TextView) itemView.findViewById(R.id.message_type);
            //this.invitation = invitation;
        }
        public void setInvitation(Notification notification) {
            this.itemMessageType.setText(R.string.route_invitation);
            this.itemMessage.setText(inboxContext.getString(R.string.invite_from)+ " " +
                    notification.getFirstName());
        }

    }
}
