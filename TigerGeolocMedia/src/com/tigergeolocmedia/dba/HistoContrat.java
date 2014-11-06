package com.tigergeolocmedia.dba;


import android.provider.BaseColumns;

public final class HistoContrat {

	private HistoContrat() {}
	public static abstract class Historics implements BaseColumns {
		public static final String TABLE_NAME = "historics";
		public static final String COLUMN_NAME_HISTORIC_ID = "historicid";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		public static final String COLUMN_NAME_TYPE = "type";
	}

}
