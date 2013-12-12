package es.vicmonmena.jobper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import es.vicmonmena.jobper.util.Jobper;

public class JobperDatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = "JobperDatabaseHelper";
    
	public JobperDatabaseHelper(Context context){
		super(context, Jobper.DATABASE_NAME, null, Jobper.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate");
		db.execSQL("CREATE TABLE " + Jobper.JOB_TABLE_NAME + " (" +
			Jobper._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
			Jobper.JOB_ID + " INTEGER NOT NULL, " +
			Jobper.TITLE + " TEXT NOT NULL, " +
			Jobper.UPDATE_AT + " TEXT NOT NULL, " +
			Jobper.SALARY_MIN + " TEXT, " +
			Jobper.SALARY_MAX + " TEXT " + ");"
			);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade");
		if (oldVersion < newVersion){
			db.execSQL("DROP TABLE IF EXISTS " + Jobper.JOB_TABLE_NAME + ";");
			onCreate(db);
		}
		
	}
}
