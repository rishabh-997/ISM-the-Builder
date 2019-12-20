package com.example.ismthebuilder.LogIn.Constructor.Available;

import java.io.Serializable;

public class AvailableList implements Serializable
{
    private String latitude, longitude, target, assigned_to, phase, budget, name, key;

    public AvailableList(String latitude, String longitude, String target, String assigned_to, String phase, String budget, String name, String key) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.target = target;
        this.assigned_to = assigned_to;
        this.phase = phase;
        this.budget = budget;
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTarget() {
        return target;
    }

    public String getAssigned_to() {
        return assigned_to;
    }

    public String getPhase() {
        return phase;
    }

    public String getBudget() {
        return budget;
    }
}
