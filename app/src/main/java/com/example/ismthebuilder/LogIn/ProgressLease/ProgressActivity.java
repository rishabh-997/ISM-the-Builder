package com.example.ismthebuilder.LogIn.ProgressLease;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ismthebuilder.LogIn.Constructor.Available.AvailableList;
import com.example.ismthebuilder.R;

public class ProgressActivity extends AppCompatActivity
{
    AvailableList model;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        model=(AvailableList) getIntent().getExtras().getSerializable("project");
        Toast.makeText(this, model.getName(), Toast.LENGTH_SHORT).show();
    }
}
