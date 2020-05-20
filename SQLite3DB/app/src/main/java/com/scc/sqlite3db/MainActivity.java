package com.scc.sqlite3db;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  RecyclerView recyclerView;
  Button addButton;

  DatabaseHelper dbHelper;
  ArrayList<String> record_id, name, description, price, rating, date_created, date_modified;
  CustomAdapter customAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    addButton = findViewById(R.id.deleteButton);
    recyclerView = findViewById(R.id.recyclerView);

    dbHelper = new DatabaseHelper(MainActivity.this);

    record_id = new ArrayList<>();
    name = new ArrayList<>();
    description = new ArrayList<>();
    price = new ArrayList<>();
    rating = new ArrayList<>();
    date_created = new ArrayList<>();
    date_modified = new ArrayList<>();


    getData();
    customAdapter = new CustomAdapter(MainActivity.this,this, record_id, name, description, price, rating, date_created, date_modified);
    recyclerView.setAdapter(customAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == 1){
      recreate();
    }
  }

  void getData(){
    Cursor cursor = dbHelper.readAllData();
    if(cursor.getCount() == 0){
      Toast.makeText(this, "No data to display.", Toast.LENGTH_SHORT).show();
    }else{
      while (cursor.moveToNext()){
        record_id.add(cursor.getString(0));
        name.add(cursor.getString(1));
        description.add(cursor.getString(2));
        price.add(cursor.getString(3));
        rating.add(cursor.getString(4));
        date_created.add(cursor.getString(5));
        date_modified.add(cursor.getString(6));
      }

      Toast.makeText(this, "Items: " + record_id.size(), Toast.LENGTH_SHORT).show();
    }
  }
  public void addButtonClicked(View view){
    Intent intent = new Intent(MainActivity.this, Add.class);
    startActivity(intent);
  }



}
