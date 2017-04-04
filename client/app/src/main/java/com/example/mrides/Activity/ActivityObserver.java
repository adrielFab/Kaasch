package com.example.mrides.Activity;


import java.util.Map;

@FunctionalInterface
public interface ActivityObserver {

    /**
     * When an Activity initiates a request, it passes a method call to
     * the requestHandler to initiate a GET/POST request on behalf of the Activity.
     * The activity then gets notified when the response is availible.
     *
     * @param response A string response formated in a json string returned from the request handler
     */
    public void Update(String response);
}
