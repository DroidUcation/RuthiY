package com.ruthiy.care2car.utils.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruthiy.care2car.R;
import com.ruthiy.care2car.activities.ViewRequestActivity;
import com.ruthiy.care2car.entities.Request;

import java.util.List;

/**
 * Created by Ruthi.Y on 7/15/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Request> requestsList;
    static Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView requestStatus, requestStDate, category, requestKey;

        public ViewHolder(View view) {
            super(view);
            requestStatus = (TextView) view.findViewById(R.id.requestStatus);
            requestStDate = (TextView) view.findViewById(R.id.requestStDate);
            category = (TextView) view.findViewById(R.id.category);
            requestKey = (TextView) view.findViewById(R.id.requestKey);
        }

        @Override
        public void onClick(View v) {
            String requestKey = this.requestKey.toString();

            Intent intent = new Intent(context, ViewRequestActivity.class /*RequestDetailsActivity.class*/);
            intent.putExtra(/*"selected_item"*/"request", requestKey); //Pass selected recipe to RecipeDetailsActivity
            context.startActivity(intent); }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Request> requestsList, Context context) {
        this.requestsList = requestsList;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType)  {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_list_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Request request = requestsList.get(position);
        holder.category.setText(request.getCategoryId());
        holder.requestStatus.setText(request.getRequestStatusId());
        holder.requestStDate.setText(request.getRequestStDate().toString());
        holder.requestKey.setText(request.getRequestKey().toString());
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return requestsList.size();
    }
}