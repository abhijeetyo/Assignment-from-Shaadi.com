package com.example.shaadi.utilities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.shaadi.data.ProfileContract.ProfileEntry;
import com.example.shaadi.utilities.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class SyncTask {

    public static final String LOG_TAG = SyncTask.class.getSimpleName();

    synchronized public static void syncProfile(Context context, JSONObject jsonresponse){
        try {

            ContentValues[] profileValues = JsonUtils.getProfileContentValuesFromJson(context,jsonresponse);
            Log.i(LOG_TAG, "profileValues Set");

             if (profileValues != null && profileValues.length !=0) {
                 //Get a handle on the ContentResolver to delete and insert data
                 ContentResolver oContentResolver = context.getContentResolver();

                 // Delete old profiles data because we don't need to keep multiple time's data.
                 oContentResolver.delete(
                         ProfileEntry.CONTENT_URI,
                         null,
                         null);


                 oContentResolver.bulkInsert(
                         ProfileEntry.CONTENT_URI,
                         profileValues);

                 Log.i(LOG_TAG, "profileValues are bulkInserted");

             }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    synchronized public static void updateAcceptanceId(Context context, ContentValues contentValues, String selections, String[] selectionArgs){

        if (contentValues != null) {

            ContentResolver oContentResolver = context.getContentResolver();

            oContentResolver.update(
                    ProfileEntry.CONTENT_URI,
                    contentValues,
                    selections,
                    selectionArgs);

            Log.i(LOG_TAG, "profileValues are Inserted");

        }

    }

}
