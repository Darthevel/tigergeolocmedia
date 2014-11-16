package com.tigergeolocmedia.dba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tigergeolocmedia.dba.HistoContrat.Historics;

public class TigerBaseSQLite extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TigerHistoric.db";
    
    private static final String SQL_CREATE_HISTORICS =
    		"CREATE TABLE " + Historics.TABLE_NAME +
    		" ( " + Historics._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + Historics.COLUMN_NAME_HISTORIC_ID + " TEXT, " +
    		Historics.COLUMN_NAME_NAME + " TEXT, " + Historics.COLUMN_NAME_DESCRIPTION + " TEXT, " +
    		Historics.COLUMN_NAME_TYPE + " TEXT, " + Historics.COLUMN_NAME_PATH + " TEXT );";
    
    private static final String SQL_DELETE_HISTORICS = "DROP TABLE IF EXISTS " + Historics.TABLE_NAME;

	public TigerBaseSQLite(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_HISTORICS);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_HISTORICS);
		onCreate(db);
	}

}
