package com.tigergeolocmedia;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
	
    private List<Person> myDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
    	
        // each data item is just a string in this case
        @InjectView(R.id.recyclerViewName) TextView nameTextView;
        @InjectView(R.id.recyclerViewEmail) TextView emailTextView;
        @InjectView(R.id.recyclerViewId) TextView idTextView;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }
    }
    
 // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerAdapter(List myDataset) {
    	this.myDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.recyclerview_viewholder, parent, false);

       
        // set the view's size, margins, paddings and layout parameters

        
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
    	Person user = myDataset.get(position);
    	
        holder.nameTextView.setText(user.getLogin());
        holder.emailTextView.setText(user.getEmail());
        holder.idTextView.setText(String.valueOf(user.getId()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myDataset.size();
    }
}
