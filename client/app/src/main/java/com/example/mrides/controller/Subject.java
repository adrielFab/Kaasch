package com.example.mrides.controller;

import com.example.mrides.Activity.ActivityObserver;

import java.util.ArrayList;

/**
 * Created by Deez Nuts on 3/4/2017.
 */

public interface Subject {
    ArrayList<ActivityObserver> observers = new ArrayList<>();

    public void attach(ActivityObserver observerToAdd);
    public void detach(ActivityObserver observerToRemove);
    public void Notify();

}
