package com.tigergeolocmedia.dba;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tigergeolocmedia.HistoricElement;
import com.tigergeolocmedia.Media;
import com.tigergeolocmedia.Media.MediaType;
import com.tigergeolocmedia.dba.HistoContrat.Historics;

public class HistoCrud {

	private TigerBaseSQLite tigerBaseSQLite;
	private SQLiteDatabase db;
	
	public void create(Context context, Media media){
		tigerBaseSQLite = new TigerBaseSQLite(context);
		db = tigerBaseSQLite.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Historics.COLUMN_NAME_NAME, media.getName());
		values.put(Historics.COLUMN_NAME_DESCRIPTION, media.getDescription());
		values.put(Historics.COLUMN_NAME_TYPE, media.getType().toString());
		values.put(Historics.COLUMN_NAME_PATH, media.getPath());
		
		db.insert(Historics.TABLE_NAME, null, values);
		db.close();
	}
	
	// Read method.
	public List<Media> selectAll(Context context){
		List<Media> medias = new ArrayList<Media>();
		String selectQuery = "SELECT  * FROM " + Historics.TABLE_NAME;
		tigerBaseSQLite = new TigerBaseSQLite(context);
		db = tigerBaseSQLite.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()){
			do{
				Media media = new Media();
				media.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Historics.COLUMN_NAME_HISTORIC_ID)));
				media.setName(cursor.getString(cursor.getColumnIndexOrThrow(Historics.COLUMN_NAME_NAME)));
				media.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(Historics.COLUMN_NAME_DESCRIPTION)));
				media.setType(MediaType.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(Historics.COLUMN_NAME_TYPE))));
				media.setPath(cursor.getString(cursor.getColumnIndexOrThrow(Historics.COLUMN_NAME_PATH)));
				medias.add(media);
			} while(cursor.moveToNext());
		}
		return medias;
	}
	
	public void update(Context context, int id, Media media){
		tigerBaseSQLite = new TigerBaseSQLite(context);
		db = tigerBaseSQLite.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Historics.COLUMN_NAME_NAME, media.getName());
		values.put(Historics.COLUMN_NAME_DESCRIPTION, media.getDescription());
		values.put(Historics.COLUMN_NAME_TYPE, media.getType().toString());
		
		db.update(Historics.TABLE_NAME, values, Historics.COLUMN_NAME_HISTORIC_ID + " = " + id, null);
		db.close();
	}
	
	public void deleteById(Context context, int id){
		tigerBaseSQLite = new TigerBaseSQLite(context);
		db = tigerBaseSQLite.getWritableDatabase();
		db.delete(Historics.TABLE_NAME, Historics.COLUMN_NAME_HISTORIC_ID + " = " + id, null);
		db.close();
	}
}
