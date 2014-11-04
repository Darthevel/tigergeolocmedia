package com.tigergeolocmedia;

import android.provider.BaseColumns;

public class UserContract {

    // To prevent someone from accidentally instantiating the    // contract class, give it an empty and/or private constructor.
    private UserContract() {}

    /* Inner class that defines the table contents */
    public static abstract class Users implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_USER_ID = "userid";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_EMAIL = "email";
    }
}
