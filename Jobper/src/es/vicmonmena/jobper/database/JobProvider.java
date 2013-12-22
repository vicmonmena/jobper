package es.vicmonmena.jobper.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import es.vicmonmena.jobper.database.util.DBConstants;

/**
 * 
 * @author vicmonmena
 *
 */
public class JobProvider extends ContentProvider {

	/**
	 * TAG para mensajes de LOG.
	 */
	private static final String TAG = "JobProvider";
	
	private static final String AUTHORITY = "es.vicmonmena.jobper.job";
	
	public static final Uri CONTENT_URI = 
			Uri.parse("content://" + AUTHORITY + "/jobs");

	private static final int URI_JOBS = 1;
	private static final int URI_JOBS_ITEM = 2;
	
	private static final UriMatcher mUriMatcher;
	
	static {
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI(AUTHORITY, "jobs", URI_JOBS);
		mUriMatcher.addURI(AUTHORITY, "jobs/#", URI_JOBS_ITEM);
	}
	
	private JobperDatabaseHelper dbHelper;
	
	@Override
	public boolean onCreate() {
		dbHelper = new JobperDatabaseHelper(getContext());
		return true;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		int match = mUriMatcher.match(uri);

		SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
		qBuilder.setTables(DBConstants.JOB_TABLE_NAME);
		
		switch (match){
			case URI_JOBS:
				//nada
				break;
			case URI_JOBS_ITEM:
				String id = uri.getPathSegments().get(1);
				qBuilder.appendWhere(DBConstants.JOB_ID + "=" + id);
				break;
			default:
				Log.w(TAG, "Uri didn't match: " + uri);
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		Cursor c = qBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}
	
	@Override
	public String getType(Uri uri) {
		int match = mUriMatcher.match(uri);
		
		switch (match){
		case URI_JOBS:
			return "vnd.android.cursor.dir/vnd.es.vicmonmena.jobper.jobs";
		case URI_JOBS_ITEM:
			return "vnd.android.cursor.item/vnd.es.vicmonmena.jobper.jobs";
		default:
			Log.w(TAG, "Uri didn't match: " + uri);
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int numAffectedRows = db.delete(DBConstants.JOB_TABLE_NAME, selection, selectionArgs);
		if (numAffectedRows > 0){
		}
		return numAffectedRows;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		long id = db.insert(DBConstants.JOB_TABLE_NAME, null, values);
		
		Uri result = null;
		
		if (id >= 0){
			result = ContentUris.withAppendedId(CONTENT_URI, id);
		}
		
		return result;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
}
