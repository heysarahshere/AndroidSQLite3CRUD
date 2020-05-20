package com.scc.sqlite3db;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
  private Context context;
  Activity activity;
  private ArrayList record_id, name, description, price, rating, date_created, date_modified;
//  int position;

  CustomAdapter(Activity activity, Context context, ArrayList record_id, ArrayList name, ArrayList description, ArrayList price, ArrayList rating, ArrayList date_created, ArrayList date_modified) {
    this.activity = activity;
    this.context = context;
    this.record_id = record_id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.rating = rating;
    this.date_created = date_created;
    this.date_modified = date_modified;
  }

  @NonNull
  @Override
  public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.view_row, parent, false);
    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, final int position) {
//    this.position = position;

    holder.nameTextView.setText(String.valueOf(name.get(position)));
    holder.descTextView.setText(String.valueOf(description.get(position)));
    holder.mainLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(context, Show.class);
        intent.putExtra("record_id", String.valueOf(record_id.get(position)));
        intent.putExtra("name", String.valueOf(name.get(position)));
        intent.putExtra("desc", String.valueOf(description.get(position)));
        intent.putExtra("price", String.valueOf(price.get(position)));
        intent.putExtra("rating", String.valueOf(rating.get(position)));
        intent.putExtra("date_created", String.valueOf(date_created.get(position)));
        intent.putExtra("date_modified", String.valueOf(date_modified.get(position)));
        activity.startActivityForResult(intent, 1);
      }
    });
  }

  @Override
  public int getItemCount() {
    return name.size();
  }
  class MyViewHolder extends RecyclerView.ViewHolder {
    TextView nameTextView, descTextView;
    LinearLayout mainLayout;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      nameTextView = itemView.findViewById(R.id.nameTextView);
      descTextView = itemView.findViewById(R.id.descTextView);
      mainLayout = itemView.findViewById(R.id.mainLayout);
    }
  }
}
