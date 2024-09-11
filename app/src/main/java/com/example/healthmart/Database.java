package com.example.healthmart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "healthmart";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    public Database(@Nullable Context context,@Nullable String name,@Nullable SQLiteDatabase.CursorFactory factory,int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USERNAME + " TEXT , " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
//        onCreate(sqLiteDatabase);
    }

    // Method to insert a user into the database
    public boolean insertUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, contentValues);
        db.close();
        return result != -1;  // If insert is successful, result will not be -1
    }
    public int login(String username,String password){
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        String str[] = new String[2];
        str[0] = username;
        str[1] = password;
        Cursor c = db.rawQuery("Select * from users where username=? and password=?",str);
        if(c.moveToFirst()){
            result = 1;
        }
        return  result;
    }

}

