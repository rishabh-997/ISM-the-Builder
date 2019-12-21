package com.example.ismthebuilder.LogIn.Constructor.Available;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ismthebuilder.LogIn.SharedPref.SharedPref;
import com.example.ismthebuilder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AvailableFragment extends Fragment implements AvailableAdapter.onNoteClickListener
{
    SharedPref sharedPref;
    @BindView(R.id.available_recycler)
    RecyclerView recyclerView;

    List<AvailableList> list = new ArrayList<>();
    AvailableAdapter adapter;

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("Contract");


    public AvailableFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_available, container, false);
        sharedPref=new SharedPref(getContext());
        ButterKnife.bind(this,view);

        adapter = new AvailableAdapter(getContext(),list,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    String latitude, longitude, target, assigned_to, phase, budget;
                    latitude = ds.child("latitude").getValue().toString();
                    longitude = ds.child("longitude").getValue().toString();
                    target = ds.child("target").getValue().toString();
                    assigned_to = ds.child("assigned_to").getValue().toString();
                    phase = ds.child("phase").getValue().toString();
                    budget = ds.child("budget").getValue().toString();
                    String name= ds.child("name").getValue().toString();

                    AvailableList model = new AvailableList(latitude,longitude,target,assigned_to,phase,budget,name, ds.getKey());
                    list.add(model);
                }
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void claim(int pos) {
        databaseReference.child(list.get(pos).getKey()).child("assigned_to").setValue(sharedPref.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "Assigned to You", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void location(int pos) {
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Float.parseFloat(list.get(pos).getLatitude()), Float.parseFloat(list.get(pos).getLongitude()));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        getContext().startActivity(intent);
    }
}
