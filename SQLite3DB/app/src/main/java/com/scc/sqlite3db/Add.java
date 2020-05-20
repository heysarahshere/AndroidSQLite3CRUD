package com.scc.sqlite3db;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Add extends AppCompatActivity {
  EditText editTextName, editTextDesc, editTextPrice, editTextRating;
  Button addRecordButton;
  String nameInput, descInput, priceInput, ratingInput;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add);

    Activity activity;

    editTextName = findViewById(R.id.textName);
    editTextDesc = findViewById(R.id.textDesc);
    editTextPrice = findViewById(R.id.textPrice);
    editTextRating = findViewById(R.id.textRating);
    addRecordButton = findViewById(R.id.deleteButton);
  }

  public void addRecordButtonClicked(View view){


    String decPrice = editTextPrice.getText().toString();
    String intRating = editTextRating.getText().toString();
    String decRegex = "\\d*\\.{1}\\d{2}";
    String intRegex = "\\d+";

    if(decPrice.matches(decRegex) || decPrice.matches(intRegex)){
      if(intRating.matches("[1-5]" )){
        DatabaseHelper dbHelper = new DatabaseHelper(Add.this);
        nameInput = editTextName.getText().toString();
        descInput = editTextDesc.getText().toString();
        priceInput = editTextPrice.getText().toString();
        ratingInput = editTextRating.getText().toString();

        dbHelper.addRecord(nameInput, descInput, priceInput, ratingInput);

        Intent intent = new Intent(Add.this, MainActivity.class);
        startActivityForResult(intent, 1);
      } else {
        Toast.makeText(this, "Enter a valid rating (1-5).", Toast.LENGTH_SHORT).show();
      }
    } else {
      Toast.makeText(this, "Enter a valid price.", Toast.LENGTH_SHORT).show();
    }
  }
}
