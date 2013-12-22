package es.vicmonmena.jobper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import es.vicmonmena.jobper.database.util.DBConstants;

/**
 * 
 * @author vicmonmena
 *
 */
public class JobperDatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = "DBConstantsDatabaseHelper";
    
	public JobperDatabaseHelper(Context context){
		super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate");
		db.execSQL("CREATE TABLE " + DBConstants.JOB_TABLE_NAME + " (" +
			DBConstants._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
			DBConstants.JOB_ID + " INTEGER NOT NULL, " +
			DBConstants.TITLE + " TEXT NOT NULL, " +
			DBConstants.UPDATE_AT + " TEXT NOT NULL, " +
			DBConstants.SALARY_MIN + " TEXT, " +
			DBConstants.SALARY_MAX + " TEXT, " +
			DBConstants.LOCATION + " TEXT " + ");"
			);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade");
		if (oldVersion < newVersion){
			db.execSQL("DROP TABLE IF EXISTS " + DBConstants.JOB_TABLE_NAME + ";");
			onCreate(db);
		}
		
	}
}
