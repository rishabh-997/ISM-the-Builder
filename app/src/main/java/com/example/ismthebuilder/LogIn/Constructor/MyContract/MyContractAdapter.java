package com.example.ismthebuilder.LogIn.Constructor.MyContract;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ismthebuilder.LogIn.Constructor.Available.AvailableList;
import com.example.ismthebuilder.R;

import java.util.List;

public class MyContractAdapter extends RecyclerView.Adapter<MyContractAdapter.ViewHolder> {
    Context context;
    List<AvailableList> arraylist;
    MyContractAdapter.onNoteClickListener listener;

    public MyContractAdapter(Context context, List<AvailableList> arraylist, MyContractAdapter.onNoteClickListener listener) {
        this.context = context;
        this.arraylist = arraylist;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyContractAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_available,parent,false);
        return new MyContractAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyContractAdapter.ViewHolder holder, int position) {
        AvailableList model = arraylist.get(position);
        holder.name.setText(model.getName());
        holder.assigned.setVisibility(View.GONE);
        holder.phase.setText(model.getPhase()+" phase");
        holder.budget.setText("To be completed in days : "+model.getBudget());
        holder.claim.setText("Update Progress");
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        MyContractAdapter.onNoteClickListener onNoteClickListener;
        TextView location, target, assigned, phase, budget, name, claim;

        public ViewHolder(@NonNull View itemView, MyContractAdapter.onNoteClickListener onNoteClickListener)
        {
            super(itemView);
            this.onNoteClickListener = onNoteClickListener;

            location = itemView.findViewById(R.id.location);
            target = itemView.findViewById(R.id.target);
            assigned = itemView.findViewById(R.id.assigned);
            phase = itemView.findViewById(R.id.phase);
            budget = itemView.findViewById(R.id.budget);
            name = itemView.findViewById(R.id.name);
            claim = itemView.findViewById(R.id.claim);

            claim.setOnClickListener(view -> onNoteClickListener.seeProgress(getAdapterPosition()));
            location.setOnClickListener(view -> onNoteClickListener.location(getAdapterPosition()));
        }
    }

    public interface onNoteClickListener {
        void seeProgress(int pos);
        void location(int pos);
    }
}
