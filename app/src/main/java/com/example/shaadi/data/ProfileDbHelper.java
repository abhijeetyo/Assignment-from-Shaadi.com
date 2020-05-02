package com.example.shaadi.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shaadi.data.ProfileContract.ProfileEntry;

public class ProfileDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "profile.db";
    private static final int DATABASE_VERSION = 1;

    public ProfileDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_PROFILE_TABLE =
                "CREATE TABLE " + ProfileEntry.TABLE_NAME + " (" +

                        ProfileEntry._ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        ProfileEntry.COLUMN_PROFILE_USERNAME + " VARCHAR(100) NOT NULL, "    +

                        ProfileEntry.COLUMN_PROFILE_IMAGE + " VARCHAR(300), "       +

                        ProfileEntry.COLUMN_PROFILE_GENDER + " VARCHAR(10) NOT NULL, "       +

                        ProfileEntry.COLUMN_PROFILE_NAME_TITLE + " VARCHAR(5) NOT NULL, "    +

                        ProfileEntry.COLUMN_PROFILE_FIRST_NAME + " VARCHAR(50) NOT NULL, "   +

                        ProfileEntry.COLUMN_PROFILE_LAST_NAME + " VARCHAR(50) NOT NULL, "    +

                        ProfileEntry.COLUMN_PROFILE_AGE + " INTEGER NOT NULL, "              +

                        ProfileEntry.COLUMN_PROFILE_CITY + " VARCHAR(50) NOT NULL, "         +

                        ProfileEntry.COLUMN_PROFILE_STATE + " VARCHAR(50) NOT NULL, "        +

                        ProfileEntry.COLUMN_PROFILE_ACCEPTANCE_ID + " INTEGER DEFAULT -1, "        +


                        " UNIQUE (" + ProfileEntry.COLUMN_PROFILE_USERNAME + ") ON CONFLICT REPLACE);";


        sqLiteDatabase.execSQL(SQL_CREATE_PROFILE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ProfileEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
