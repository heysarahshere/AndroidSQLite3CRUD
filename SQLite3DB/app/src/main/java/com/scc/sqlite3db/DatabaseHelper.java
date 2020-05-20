package com.scc.sqlite3db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

class DatabaseHelper extends SQLiteOpenHelper {

  private Context context;
  private static final String DATABASE_NAME = "CrudLibrary.db";
  private static final int DATABASE_VERSION = 1;

  private static final String TABLE_NAME = "crud_library";

  private static final String COLUMN_ID = "record_id";
  private static final String COLUMN_TITLE = "name";
  private static final String COLUMN_DESCRIPTION = "description";
  private static final String COLUMN_PRICE = "price";
  private static final String COLUMN_RATING = "rating";
  private static final String COLUMN_CREATED = "date_created";
  private static final String COLUMN_MODIFIED = "date_modified";


  DatabaseHelper(@Nullable Context context) {
    super(context, DATABASE_NAME,null, DATABASE_VERSION);
    this.context = context;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String query = "CREATE TABLE " + TABLE_NAME +
        " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
        COLUMN_TITLE + " TEXT, " +
        COLUMN_DESCRIPTION + " TEXT, " +
        COLUMN_PRICE + " TEXT, " +
        COLUMN_RATING + " TEXT, " +
        COLUMN_CREATED + " TEXT, " +
        COLUMN_MODIFIED + " TEXT);";

    db.execSQL(query);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    onCreate(db);
  }

  void addRecord(String name, String desc, String price, String rating){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues cv = new ContentValues();

//    get current date
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Calendar c = Calendar.getInstance();
    String date = sdf.format(c.getTime());
//    String date = "bad";

   // user input
    cv.put(COLUMN_TITLE, name);
    cv.put(COLUMN_DESCRIPTION, desc);
    cv.put(COLUMN_PRICE, price);
    cv.put(COLUMN_RATING, rating);

   // dates
    cv.put(COLUMN_CREATED, date);
    cv.put(COLUMN_MODIFIED, date);

    long result = db.insert(TABLE_NAME,null, cv);
    if(result == -1) {
      Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
    } else {Toast.makeText(context, "Succeeded.", Toast.LENGTH_SHORT).show();}

  }

  Cursor readAllData(){
    String query = "SELECT * FROM " + TABLE_NAME;
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = null;
    if(db != null){
      cursor = db.rawQuery(query, null);
    }
    return cursor;
  }


  void deleteRecord(String record_id){
    SQLiteDatabase db = this.getWritableDatabase();
    long result = db.delete(TABLE_NAME, "record_id=?", new String[]{record_id});
    if(result == -1){
      Toast.makeText(context, "Delete failed.", Toast.LENGTH_SHORT).show();
    }else{
      Toast.makeText(context, "Delete succeeded.", Toast.LENGTH_SHORT).show();
    }
  }

  void updateData(String record_id, String name, String desc, String price, String rating, String date_created, String date_modified){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues cv = new ContentValues();
    cv.put(COLUMN_TITLE, name);
    cv.put(COLUMN_DESCRIPTION, desc);
    cv.put(COLUMN_PRICE, price);
    cv.put(COLUMN_RATING, rating);
    cv.put(COLUMN_CREATED, date_created);
    cv.put(COLUMN_MODIFIED, date_modified);

    long result = db.update(TABLE_NAME, cv, "record_id=?", new String[]{record_id});
    if(result == -1){
      Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
    }else {
      Toast.makeText(context, "Succeeded.", Toast.LENGTH_SHORT).show();
    }
  }
}
