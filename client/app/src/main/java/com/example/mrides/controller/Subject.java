package com.example.mrides.controller;

import com.example.mrides.Activity.ActivityObserver;

import java.util.ArrayList;
import java.util.Map;

import DirectionModel.IPersistanceObject;

public interface Subject {
    public void attach(ActivityObserver observerToAdd);
    public void detach(ActivityObserver observerToRemove);
    public void Notify(String response);

}
