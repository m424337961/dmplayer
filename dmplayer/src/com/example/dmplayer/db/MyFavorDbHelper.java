package com.example.dmplayer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyFavorDbHelper extends SQLiteOpenHelper{

	public MyFavorDbHelper(Context context) {
		super(context, "myfavor.db", null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table myfavor (_id integer primary key autoincrement,"
				+ "song varchar(20), artist varchar(20), path varchar(100))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}


}
