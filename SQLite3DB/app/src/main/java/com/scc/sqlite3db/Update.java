package com.scc.sqlite3db;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Update extends AppCompatActivity {

  EditText updateTextName, updateTextDesc, updateTextPrice, updateTextRating;
  Button saveChangesButton;
  String record_id, name, desc, price, rating, date_created, date_modified, newName, newDesc, newPrice, newRating, newModified;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update);

    updateTextName = findViewById(R.id.updateTextName);
    updateTextDesc = findViewById(R.id.updateTextDesc);
    updateTextPrice = findViewById(R.id.updateTextPrice);
    updateTextRating = findViewById(R.id.updateTextRating);
    saveChangesButton = findViewById(R.id.saveChangesButton);

    getAndSetIntentDataToUpdate();
    ActionBar ab = getSupportActionBar();
    if (ab != null) {
      ab.setTitle(name);
    }
  }

  public void updateButtonClicked(View view){
    String decPrice = updateTextPrice.getText().toString();
    String intRating = updateTextRating.getText().toString();
    if(decPrice.matches("\\d*\\.?\\d+")){
      if(intRating.matches("[1-5]" )){
        newName = updateTextName.getText().toString();
        newDesc = updateTextDesc.getText().toString();
        newPrice = updateTextPrice.getText().toString();
        newRating = updateTextRating.getText().toString();
        DatabaseHelper dbHelper = new DatabaseHelper(Update.this);
        // set mod date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());
        newModified = date;

        // update record
        dbHelper.updateData(record_id, newName, newDesc, newPrice, newRating, date_created, newModified);
        // flash toast and send back to show page
        Intent intent = new Intent(this, Show.class);
        intent.putExtra("record_id", record_id);
        intent.putExtra("name", newName);
        intent.putExtra("desc", newDesc);
        intent.putExtra("price", newPrice);
        intent.putExtra("rating", newRating);
        intent.putExtra("date_created", date_created);
        intent.putExtra("date_modified", newModified);
        startActivity(intent);
      } else {
        Toast.makeText(this, "Enter a valid rating (1-5).", Toast.LENGTH_SHORT).show();
      }
    } else {
      Toast.makeText(this, "Enter a valid price.", Toast.LENGTH_SHORT).show();
    }

  }

  void getAndSetIntentDataToUpdate(){
    if(getIntent().hasExtra("idToUpdate") && getIntent().hasExtra("nameToUpdate") &&
        getIntent().hasExtra("descToUpdate") && getIntent().hasExtra("priceToUpdate") &&
        getIntent().hasExtra("ratingToUpdate") && getIntent().hasExtra("createdToUpdate"))
    {
      //Get
      record_id = getIntent().getStringExtra("idToUpdate");
      name = getIntent().getStringExtra("nameToUpdate");
      desc = getIntent().getStringExtra("descToUpdate");
      price = getIntent().getStringExtra("priceToUpdate");
      rating = getIntent().getStringExtra("ratingToUpdate");
      date_created = getIntent().getStringExtra("createdToUpdate");
      //Set
      updateTextName.setText(name);
      updateTextDesc.setText(desc);
      updateTextPrice.setText(price);
      updateTextRating.setText(rating);

    }else{
      Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
    }
  }
}
