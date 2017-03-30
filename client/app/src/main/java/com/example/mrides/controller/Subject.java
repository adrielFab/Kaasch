package com.example.mrides.controller;

import com.example.mrides.Activity.ActivityObserver;


public interface Subject {

    /**
     * This method is designed to attah observers to a list of observers on the subject class
     * @param observerToAdd The observer to add to the concrete subject
     */
    public void attach(ActivityObserver observerToAdd);

    /**
     * When an observer no longer needs to listen to a subject, the subject needs to remove
     * the obsever reference from the class array
     * @param observerToRemove This is the observer to remove from the list of observers
     */
    public void detach(ActivityObserver observerToRemove);

    /**
     * Tis method notifies all observers that the state of the subject has changed.
     * @param response
     */
    public void Notify(String response);

}
