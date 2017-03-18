package com.example.mrides.controller;

import com.example.mrides.Activity.ActivityObserver;


public interface Subject {
    public void attach(ActivityObserver observerToAdd);
    public void detach(ActivityObserver observerToRemove);
    public void Notify(String response);

}
