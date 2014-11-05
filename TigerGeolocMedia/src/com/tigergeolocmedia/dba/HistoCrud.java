package com.tigergeolocmedia.dba;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tigergeolocmedia.HistoricElement;
import com.tigergeolocmedia.dba.HistoContrat.Historics;

public class HistoCrud {

	private TigerBaseSQLite tigerBaseSQLite;
	private SQLiteDatabase db;
	
	
	
	public void create(Context context, HistoricElement historic){
		tigerBaseSQLite = new TigerBaseSQLite(context);
		db = tigerBaseSQLite.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Historics.COLUMN_NAME_NAME, historic.getName());
		values.put(Historics.COLUMN_NAME_DESCRIPTION, historic.getDescription());
		values.put(Historics.COLUMN_NAME_TYPE, historic.getType().toString());
		
		
	}
}
