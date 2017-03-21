package com.example.mrides.Notification;


import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrides.Activity.ActivityObserver;
import com.example.mrides.Activity.CreateRouteActivity;
import com.example.mrides.Activity.InboxActivity;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;

import java.util.Map;

/**
 * The adapter is the link between the data model we want to show and the UI.
 * This class tells the UI how to show past notifications which have not been declined or accepted
 * yet.
 *
 */
public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> implements
        View.OnClickListener, ActivityObserver{

    private Map<String,String> responseBody;
    private Context inboxContext;
    private Dialog dialog;
    private RequestHandler requestHandler = new RequestHandler();

    public InboxAdapter(Context inboxContext){
        this.inboxContext = inboxContext;
    }

    public void setViewComponents(Map<String,String> body){
        this.responseBody = body;
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
        ViewHolder vh = new ViewHolder(view);
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
        holder.itemMessage.setText(inboxContext.getString(R.string.invited_to_route)
                + " " + responseBody.get("loggedInUserDisplayName"));
        holder.itemMessage.setOnClickListener(this);
        holder.profilePciture.setOnClickListener(this);
    }



    /**
     * Returns the count of items to be added to the inbox.
     * @return
     */
    //TODO number of elements which need to be shown for now 1 however as the inbox grows
    //TODO more the item list needs to be representative of the number of inbox values
    @Override
    public int getItemCount() {
        return 1;
    }

    /**
     * Either an inbox element, accept button or cancel button can be selected.
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_title:
                createDiolgue();
                break;
            case R.id.accept:
                informDriverOfInvite();
                break;
            case R.id.buttonCancel:
                dialog.cancel();
                break;
        }
    }

    //TODO a get post request needs to be sent to the server to inform the driver that the passenger
    //has accepted the ride
    private void informDriverOfInvite() {
        requestHandler.attach(this);
        System.out.println("Notification.: "+ responseBody.get("driverEmail"));
        System.out.println("Notification.: "+ responseBody.get("passengerEmail"));
        requestHandler.httpPostStringRequest("http://"+inboxContext.getString(R.string.web_server_ip)+
                "/add_passenger_to_route.php",
                responseBody,"application/x-www-form-urlencoded; charset=UTF-8", inboxContext);
        Toast.makeText(inboxContext, inboxContext.getString(R.string.invite_accepted),
                Toast.LENGTH_SHORT).show();
        dialog.hide();
    }

    //TODO we need to create a user profile page. Right now a diologue box is shown.
    private void createDiolgue() {
        dialog = new Dialog(inboxContext);
        dialog.setTitle(inboxContext.getString(R.string.invited_to_route));
        dialog.setContentView(R.layout.userprofile_dialog_layout);
        dialog.show();

        TextView textViewFullName = (TextView) dialog.findViewById(R.id.textViewFirstName);
        textViewFullName.setText(responseBody.get("loggedInUserFirstName"));

        TextView textViewEmail = (TextView) dialog.findViewById(R.id.textViewEmail);
        textViewEmail.setText(responseBody.get("loggedInUserEmail"));

        ImageView imageViewProfile = (ImageView) dialog.findViewById(R.id.imageViewProfile);
        //new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
        //       .execute();
        imageViewProfile.setImageResource(R.drawable.sample_profile_image);

        Button buttonInvite = (Button) dialog.findViewById(R.id.buttonInvite);
        //TODO button id needs to be changed. diologue is also used in createrouteActivity
        buttonInvite.setId(R.id.accept);
        buttonInvite.setText(R.string.accept);
        buttonInvite.setOnClickListener(this);

        Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(this);
    }

    @Override
    public void Update(String response) {
        requestHandler.detach(this);
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
