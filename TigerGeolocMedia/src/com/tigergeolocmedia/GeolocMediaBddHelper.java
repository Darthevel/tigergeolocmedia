package com.tigergeolocmedia;

import com.tigergeolocmedia.UserContract.Users;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GeolocMediaBddHelper extends SQLiteOpenHelper {

	// Bump this for each change in the schema
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MediaGeoLoc.db";
    
    private Context context;
    
	private static final String SQL_CREATE_USERS =
		    "CREATE TABLE " + Users.TABLE_NAME + " (" +
		    Users._ID + " INTEGER PRIMARY KEY," +
		    Users.COLUMN_NAME_USER_ID + " TEXT," +
		    Users.COLUMN_NAME_USERNAME + " TEXT," +
		    Users.COLUMN_NAME_EMAIL + " TEXT" +
		    " );";
    
	private static final String SQL_DELETE_USERS = "DROP TABLE IF EXISTS " +
			Users.TABLE_NAME;


    public GeolocMediaBddHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database upgrade policy is to discard the data
        db.execSQL(SQL_DELETE_USERS);
        onCreate(db);
    }
    
    public void saveUser(int id, String username, String email)
    {
		SQLiteDatabase writableDB = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put(Users.COLUMN_NAME_USER_ID, id);
		values.put(Users.COLUMN_NAME_USERNAME, username);
		values.put(Users.COLUMN_NAME_EMAIL, email);
		
		// Insert, the primary key value of the new row is returned
		long newRowId = writableDB.insert(
		         Users.TABLE_NAME,
		         null, // nullColumnHack
		         values);
    }
    
    public Person getUser(String selection, String[] selectionArgs)
    {
    	Person user = new Person();
    	
		SQLiteDatabase readableDB = this.getReadableDatabase();
		
		String[] projection = {
			    Users._ID,
			    Users.COLUMN_NAME_USER_ID,
			    Users.COLUMN_NAME_USERNAME,
			    Users.COLUMN_NAME_EMAIL,
			    };

		Cursor c = readableDB.query(
			    Users.TABLE_NAME,      // The table to query
			    projection,            // The columns to return, null = ALL
			    selection,             // WHERE clause, using ?s for values
			    selectionArgs,         // The values for the WHERE clause
			    null,               // GROUP BY clause (rows grouping)
			    null,                // HAVING clause (group filtering)
			    null              // ORDER BY clause
			    );

		c.moveToFirst();
		while (! c.isAfterLast()) {	
		user.setId(Long.valueOf(c.getLong(c.getColumnIndexOrThrow(Users._ID))).intValue());
	    user.setUsername(c.getString(c.getColumnIndexOrThrow(Users.COLUMN_NAME_USERNAME)));
	    user.setEmail(c.getString(c.getColumnIndexOrThrow(Users.COLUMN_NAME_EMAIL)));
	    c.moveToNext();
		}
		c.close();
		
    	return user;
    }
}
