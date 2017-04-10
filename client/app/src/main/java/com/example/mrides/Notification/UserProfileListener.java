package com.example.mrides.Notification;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrides.Activity.ActivityObserver;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;

import java.util.HashMap;
import java.util.Map;

public class UserProfileListener implements View.OnClickListener, ActivityObserver{

    private Notification notification;
    private Dialog dialog;
    private Context inboxContext;
    private RequestHandler requestHandler = new RequestHandler();

    public UserProfileListener(Notification notification, Context inboxContext) {
        this.notification = notification;
        this.inboxContext = inboxContext;
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
                changePassengerStatusToConfirmed();
                break;
            case R.id.buttonCancel:
                dialog.cancel();
                break;
            default:
                break;
        }
    }

    /**
     * This method creates the user profile diologue
     */
    private void createDiolgue() {
        dialog = new Dialog(inboxContext);
        dialog.setTitle(inboxContext.getString(R.string.invited_to_route));
        dialog.setContentView(R.layout.userprofile_dialog_layout);
        dialog.show();

        TextView textViewFullName = (TextView) dialog.findViewById(R.id.textViewFirstName);
        textViewFullName.setText(notification.getFirstName());

        TextView textViewEmail = (TextView) dialog.findViewById(R.id.textViewEmail);
        textViewEmail.setText(notification.getDriverEmail());

        ImageView imageViewProfile = (ImageView) dialog.findViewById(R.id.imageViewProfile);
        //new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
        imageViewProfile.setImageResource(R.drawable.sample_profile_image);

        Button buttonInvite = (Button) dialog.findViewById(R.id.buttonInvite);
        buttonInvite.setId(R.id.accept);
        buttonInvite.setText(R.string.accept);
        buttonInvite.setOnClickListener(this);

        Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(this);
    }

    /**
     * This method is called when the user confirms a route invitation from the diologue
     */
    private void changePassengerStatusToConfirmed() {
        requestHandler.attach(this);
        Map<String, String> responseBody = new HashMap<>();
        String passengerWhoConfrimedEmail = RequestHandler.getUser().getEmail();
        String driverEmail = notification.getDriverEmail();
        responseBody.put("driverEmail", driverEmail);
        responseBody.put("passengerEmail", passengerWhoConfrimedEmail);
        requestHandler.httpPostStringRequest("http://"+inboxContext.getString(R.string.web_server_ip)+
                        "/add_passenger_to_route.php",
                responseBody,RequestHandler.URLENCODED, inboxContext);
        Toast.makeText(inboxContext, inboxContext.getString(R.string.invite_accepted),
                Toast.LENGTH_SHORT).show();
        dialog.hide();
    }

    @Override
    public void Update(String response) {

    }
}
