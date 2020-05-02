package com.example.shaadi.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class ProfileContract {

    public static final String CONTENT_AUTHORITY = "com.example.shaadi.data.ProfileProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PROFILE = "profile";


    public static final class ProfileEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PROFILE)
                .build();

        public static final String TABLE_NAME = "Profile";

        public static final String COLUMN_PROFILE_USERNAME ="Username";

        public static final String COLUMN_PROFILE_GENDER = "Gender";

        public static final String COLUMN_PROFILE_IMAGE = "Image";

        public static final String COLUMN_PROFILE_NAME_TITLE ="Name_Title";

        public static final String COLUMN_PROFILE_FIRST_NAME = "First_Name";

        public static final String COLUMN_PROFILE_LAST_NAME ="Last_Name";

        public static final String COLUMN_PROFILE_AGE = "Age";

        public static final String COLUMN_PROFILE_CITY ="City";

        public static final String COLUMN_PROFILE_STATE ="State";

        public static final String COLUMN_PROFILE_ACCEPTANCE_ID = "Acceptance_Value"; // default is -1 to show that user has not accepted(1) or declined(0) yet

        public static Uri buildProfileUriWithProfileUsername(String profileUsername) {
            return CONTENT_URI.buildUpon()
                    .appendPath(profileUsername)
                    .build();
        }

        public static String getSqlSelectForAllProfile(){
            return ProfileEntry.COLUMN_PROFILE_USERNAME;
        }

    }

}
