package com.example.ismthebuilder.LogIn.ProgressLease;

public class ProgressModel
{
    String stage, description, url;

    public ProgressModel(String stage, String description, String url) {
        this.stage = stage;
        this.description = description;
        this.url = url;
    }

    public ProgressModel() {
    }

    public String getStage() {
        return stage;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
