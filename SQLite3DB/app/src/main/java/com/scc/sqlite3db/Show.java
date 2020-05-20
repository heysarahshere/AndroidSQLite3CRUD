package com.scc.sqlite3db;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Show extends AppCompatActivity {
  TextView textId, textName, textDesc, textPrice, textRating, textCreated, textModified;
  Button editButton, deleteButton;
  String record_id, name, desc, price, rating, date_created, date_modified;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show);
    textId = findViewById(R.id.textId);
    textName = findViewById(R.id.textName);
    textDesc = findViewById(R.id.textDesc);
    textPrice = findViewById(R.id.textPrice);
    textRating = findViewById(R.id.textRating);
    textCreated = findViewById(R.id.textCreated);
    textModified = findViewById(R.id.textModified);
    deleteButton = findViewById(R.id.deleteButton);
    editButton = findViewById(R.id.editButton);

    getAndSetIntentData();
    ActionBar ab = getSupportActionBar();
    if (ab != null) {
      ab.setTitle(name);
    }


  }
  void getAndSetIntentData(){
    if(getIntent().hasExtra("record_id") && getIntent().hasExtra("name") &&
        getIntent().hasExtra("desc") && getIntent().hasExtra("price") &&
        getIntent().hasExtra("rating") && getIntent().hasExtra("date_created") && getIntent().hasExtra("date_modified"))
      {
        //Get
        record_id = getIntent().getStringExtra("record_id");
        name = getIntent().getStringExtra("name");
        desc = getIntent().getStringExtra("desc");
        price = getIntent().getStringExtra("price");
        rating = getIntent().getStringExtra("rating");
        date_created = getIntent().getStringExtra("date_created");
        date_modified = getIntent().getStringExtra("date_modified");
        //Set
        textId.setText(record_id);
        textName.setText(name);
        textDesc.setText(desc);
        textPrice.setText(price);
        textRating.setText(rating);
        textCreated.setText(date_created);
        textModified.setText(date_modified);

      }else{
        Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
      }
  }
  public void editButtonClicked(View view){
    Intent intent = new Intent(this, Update.class);
    intent.putExtra("idToUpdate", textId.getText().toString());
    intent.putExtra("nameToUpdate", textName.getText().toString());
    intent.putExtra("descToUpdate", textDesc.getText().toString());
    intent.putExtra("priceToUpdate", textPrice.getText().toString());
    intent.putExtra("ratingToUpdate", textRating.getText().toString());
    intent.putExtra("createdToUpdate", textCreated.getText().toString());
    startActivity(intent);

  }
  public void deleteButtonClicked(View view){
    confirmDialog();
  }

  void confirmDialog(){
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Confirmation");
    builder.setMessage("Are you sure you want to delete " + name + "?");
    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        DatabaseHelper dbHelper = new DatabaseHelper(Show.this);
        dbHelper.deleteRecord(record_id);

        Intent intent = new Intent(Show.this, MainActivity.class);
        startActivityForResult(intent, 1);
      }
    });
    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {

      }
    });
    builder.create().show();
  }
}
